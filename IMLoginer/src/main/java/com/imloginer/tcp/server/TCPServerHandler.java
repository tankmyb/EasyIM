package com.imloginer.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.util.Collection;
import java.util.Date;

import com.google.protobuf.InvalidProtocolBufferException;
import com.imapi.bean.loginer.LoginerReqBean;
import com.imapi.bean.loginer.LoginerRespBean;
import com.imapi.bean.loginer.req.LoginerConnOtherReqBean;
import com.imapi.common.LoginerReqType;
import com.imapi.common.LoginerRespType;
import com.imbase.utils.JacksonUtil;
import com.imloginer.tcp.channel.EasyChannel;
import com.imloginer.tcp.client.loginer.LoginerChannel;
import com.imloginer.udp.server.UDPLoginerChannel;

public class TCPServerHandler extends ChannelHandlerAdapter {
	int port;
	Channel udpChannel;
	   public TCPServerHandler(Channel udpChannel,int port){
		   this.port = port;
		   this.udpChannel = udpChannel;
	   }
	   @Override
		 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		  // System.out.println(new Date()+"=========messageReceived================"+msg);
		   if(msg instanceof LoginerReqBean){
			   LoginerReqBean reqBean =(LoginerReqBean)msg;
			   if(reqBean.getType()==LoginerReqType.APPLY_CONN){
		    	   LoginerConnOtherReqBean connReq = JacksonUtil.resolve(reqBean.getReq(), LoginerConnOtherReqBean.class);
		    	   String ipPort = connReq.getIpPort();
		    	   LoginerChannel.addChannel(ipPort, EasyChannel.SERVER_TYPE,ctx);
		    	   LoginerRespBean respBean = new LoginerRespBean();
		    	   respBean.setType(LoginerRespType.CONN_SUCC);
		    	   respBean.setResp(connReq.getIp()+":"+connReq.getPort());
		    	   ctx.writeAndFlush(respBean);
		    	   System.out.println("===============CONN_SUCC======="+new Date());
		       }else if(reqBean.getType()==LoginerReqType.UDP_SEND){
		    	   System.out.println("====================udp");
		    	   String dp = reqBean.getReq();
		    	   System.out.println(dp+"==s=="+udpChannel);
		    	   Collection<InetSocketAddress> coll = UDPLoginerChannel.getValues();
		    	   for(InetSocketAddress a:coll){
		    		   udpChannel.writeAndFlush(new DatagramPacket(
		   	                Unpooled.copiedBuffer(dp, CharsetUtil.UTF_8),
		   	                a));
		    	   }
		    	   //ctx.writeAndFlush(1111);
		       }
		   }else if(msg instanceof ByteBuf){
			   ByteBuf b = (ByteBuf)msg;
		    	   Collection<InetSocketAddress> coll = UDPLoginerChannel.getValues();
		    	   for(InetSocketAddress a:coll){
		    		   udpChannel.writeAndFlush(new DatagramPacket(b,a));
		    	   }
			}
	   }
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
    	cause.printStackTrace();
		System.out.println("=====Server====exceptionCaught=========="+cause.getCause());
		ctx.channel().close();
		//super.exceptionCaught(ctx, cause);
	}
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("=====Server====disconnect==========");
		System.out.println(LoginerChannel.getKeys());
		LoginerChannel.remove(ctx.channel().id().asLongText());
		System.out.println(LoginerChannel.getKeys());
		super.close(ctx, promise);
	}
  

}
