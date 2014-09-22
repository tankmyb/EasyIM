package com.imloginer.tcp.client.loginer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Date;

import com.imapi.bean.balance.BalanceReqBean;
import com.imapi.bean.balance.req.LoginerConnOtherSuccReqBean;
import com.imapi.bean.loginer.LoginerReqBean;
import com.imapi.bean.loginer.LoginerRespBean;
import com.imapi.bean.loginer.req.LoginerConnOtherReqBean;
import com.imapi.common.BalanceReqType;
import com.imapi.common.LoginerReqType;
import com.imapi.common.LoginerRespType;
import com.imbase.utils.BaseUtil;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;
import com.imloginer.tcp.channel.EasyChannel;
import com.imloginer.tcp.client.balance.BalanceClientThread;
import com.imloginer.udp.server.UDPLoginerChannel;

public class LoginClientHandler extends
ChannelHandlerAdapter {
	Channel udpChannel;
	BalanceClientThread thread;
	Integer seq;
	private String ip;
	private Integer port;

	public LoginClientHandler(Channel udpChannel, BalanceClientThread thread,
			String ip, Integer port, int seq) {
		this.thread = thread;
		this.seq = seq;
		this.ip = ip;
		this.port = port;
		this.udpChannel = udpChannel;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		LoginerReqBean  reqBean = new LoginerReqBean();
		reqBean.setType(LoginerReqType.APPLY_CONN);
		LoginerConnOtherReqBean  connReq = new LoginerConnOtherReqBean();
		connReq.setIp(ip);
		connReq.setPort(port);
		connReq.setIpPort(BaseUtil.getHostIp() + ":" + thread.getSport());
		reqBean.setReq(JacksonUtil.getJsonStr(connReq));
		ctx.writeAndFlush(reqBean);
	}

	@Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof LoginerRespBean){
			LoginerRespBean respBean = (LoginerRespBean)msg;

			if (respBean.getType() == LoginerRespType.CONN_SUCC) {
				String str =respBean.getResp();
				DebugUtil.print1(str + "======str====="+new Date());
				boolean isEmpty = thread.remove(str);
				LoginerChannel.addChannel(str, EasyChannel.CLIENT_TYPE, ctx);
				if (isEmpty) {
					DebugUtil.print1("==========isEmpty=============");
					BalanceReqBean  reqBean = new BalanceReqBean();
					reqBean.setType(BalanceReqType.LOGIN_OTHER_SUCC);
					LoginerConnOtherSuccReqBean  connOtherSuccReq = new LoginerConnOtherSuccReqBean();
					connOtherSuccReq.setSeq(seq);
					reqBean.setReq(JacksonUtil.getJsonStr(connOtherSuccReq));
					// Thread.sleep(10000);
					thread.write(reqBean);

				}
			} else if (respBean.getType() == LoginerRespType.UDP_SEND) {
				DebugUtil.print1("====================udp");
				String dp = respBean.getResp();
				DebugUtil.print1(dp + "==c==" + udpChannel);
				Collection<InetSocketAddress> coll = UDPLoginerChannel.getValues();
				for (InetSocketAddress a : coll) {
					udpChannel.writeAndFlush(new DatagramPacket(Unpooled
							.copiedBuffer(dp, CharsetUtil.UTF_8), a));
				}
			}

		
		}else if(msg instanceof ByteBuf){
			   ByteBuf b = (ByteBuf)msg;
			   DebugUtil.print1(b.toString()+"====2===byteBuf");
		    	   Collection<InetSocketAddress> coll = UDPLoginerChannel.getValues();
		    	   for(InetSocketAddress a:coll){
		    		   udpChannel.writeAndFlush(new DatagramPacket(b,a));
		    	   }
			}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		DebugUtil.print1("=====client====exceptionCaught=========="
				+ cause.getCause());
		ctx.channel().close();
		// super.exceptionCaught(ctx, cause);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		DebugUtil.print1("=====client====disconnect==========");
		DebugUtil.print1(LoginerChannel.getKeys());
		LoginerChannel.remove(ctx.channel().id().asLongText());
		DebugUtil.print1(LoginerChannel.getKeys());
		super.close(ctx, promise);
	}
}
