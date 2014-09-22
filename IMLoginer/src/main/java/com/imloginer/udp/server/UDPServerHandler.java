package com.imloginer.udp.server;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imapi.bean.udp.UDPReqBean;
import com.imapi.common.UDPServiceType;
import com.imbase.utils.BaseUtil;
import com.imbase.utils.DebugUtil;
import com.imloginer.bean.FullChat;
import com.imloginer.bean.SubPack;
import com.imloginer.redis.RedisService;
import com.imloginer.tcp.channel.EasyChannel;
import com.imloginer.tcp.client.loginer.LoginerChannel;
 
// 该handler是InboundHandler类型
public class UDPServerHandler extends ChannelHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger("debug");

    private RedisService redisService;
    private int tcpPort;
    public UDPServerHandler(RedisService redisService,int tcpPort){
    	this.redisService = redisService;
    	this.tcpPort = tcpPort;
    }

	@Override
	 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DebugUtil.print1("=========11=====channel-read==============");
		
		if(msg instanceof UDPReqBean){
			UDPReqBean reqBean = (UDPReqBean)msg;
			if(reqBean.getType()==UDPServiceType.LOGIN){
				UDPLoginerChannel.addAddress(reqBean.getReq(),reqBean.getSender());
				 redisService.addLoginInfo(reqBean.getReq(), BaseUtil.getHostIp()+":"+tcpPort);
				
				 StringBuffer sb = new StringBuffer();
				 //for(int i=0;i<200;i++){
					 //sb.append(i).append(",");
				 //}
				 sb.append("success");
				 DebugUtil.print1(sb+"====");
				 List<ByteBuf> list = str2ByteBuf(sb.toString());
				 for(ByteBuf buf:list){
					 ctx.writeAndFlush(new DatagramPacket(
				                buf,
				                reqBean.getSender()));
				 }
				 
			}
		}
		else if(msg instanceof FullChat){
			FullChat reqBean = (FullChat)msg;
			DebugUtil.print1(reqBean+"========================="+reqBean.getChatNo());
			 /*if(response.equals("h")){
				 ctx.writeAndFlush(new DatagramPacket(
			                Unpooled.copiedBuffer("h", CharsetUtil.UTF_8),
			                reqBean.getSender()));
			 }*/
			 String address = redisService.getAddress(reqBean.getChatNo());
			 if(address.equals(BaseUtil.getHostIp()+":"+tcpPort)){
				 ByteBuf buf = reqBean.getBuf();
				 ctx.writeAndFlush(new DatagramPacket(
						 buf,
			                UDPLoginerChannel.getAddress(reqBean.getChatNo())));
			 }else {
				 EasyChannel ec = LoginerChannel.getChannel(address);
					ec.getCtx().writeAndFlush(reqBean.getBuf());
			 }
				
		}else if(msg instanceof UDPPack){
			UDPPack udpPack = (UDPPack)msg;
			
			String address = redisService.getAddress(udpPack.getChatNo());
			if(address.equals(BaseUtil.getHostIp()+":"+tcpPort)){
				List<SubPack> subList = udpPack.getList();
				for(SubPack sub:subList){
					ctx.writeAndFlush(new DatagramPacket(sub.getBuf(),
				                UDPLoginerChannel.getAddress(udpPack.getChatNo())));
				}
				 
			}else {
				EasyChannel ec = LoginerChannel.getChannel(address);
				DebugUtil.print1(ec.getType()+"=====type");
				List<SubPack> subList = udpPack.getList();
				for(SubPack sub:subList){
					//logger.debug("udp send:" + sub.getBuf().toString(CharsetUtil.UTF_8));
					ec.getCtx().writeAndFlush(sub.getBuf());
				}
			}
			
		}
		
		 
		 
		 
		 
			/* String chatNo= arr[1];
			 String message = arr[2];
			 String address = redisService.getAddress(chatNo);
			 if(address != null){
				 if(address.equals(BaseUtil.getHostIp()+":"+tcpPort)){
					 ctx.writeAndFlush(new DatagramPacket(
				                Unpooled.copiedBuffer(message, CharsetUtil.UTF_8),
				                UDPLoginerChannel.getAddress(chatNo)));
					 return;
				 }
				EasyChannel ec = LoginerChannel.getChannel(address);
				if(ec !=null){
					ChannelHandlerContext c = ec.getCtx();
		        	if(ec.getType()==EasyChannel.CLIENT_TYPE){
		        		LoginerReqBean  loginerReqBean = new LoginerReqBean();
		        		loginerReqBean.setType(LoginerReqType.UDP_SEND);
		        		loginerReqBean.setReq(message);
			        	System.out.println(c.channel().remoteAddress()+"====req");
			        	System.out.println(c.channel().localAddress()+"====req");
			        	c.writeAndFlush(reqBean);
		        	}else {
			        	LoginerRespBean respBean = new LoginerRespBean();
		        		respBean.setType(LoginerRespType.UDP_SEND);
		        		respBean.setResp(message);
			        	System.out.println(c.channel().remoteAddress()+"===resp");
			        	System.out.println(c.channel().localAddress()+"===resp");
			        	c.writeAndFlush(respBean);
		        	}
				}
			 }
		 }*/
        
        
		
	}
	public List<ByteBuf> str2ByteBuf(String str) {
		List<ByteBuf> list = new ArrayList<ByteBuf>();
		int maxLen = 512;
		byte[] b = str.getBytes();
		Integer len = b.length;
		if (len <= maxLen) {
			DebugUtil.print1(len + "====len");
			final ByteBuf cb = Unpooled.buffer(len+1);
			cb.writeInt(b.length+1);
			cb.writeByte(3);
			cb.writeBytes(b);
			list.add(cb);
			return list;
		}
		int packNum = len / maxLen;
		if (len % maxLen != 0) {
			packNum++;
		}
		int pos = 0;
		DebugUtil.print1("packNum:" + packNum);
		for (int i = 0; i < packNum; i++) {
			int length = maxLen;
			if (len - pos < length) {
				length = len - pos;
			}
			byte[] pack = new byte[length];

			System.arraycopy(b, pos, pack, 0, length);
			final ByteBuf cb = Unpooled.buffer(maxLen);
			cb.writeInt(pack.length);
			
				cb.writeByte(4);
			cb.writeInt(1);// 序列号
			cb.writeInt(packNum);// 分包数
			cb.writeInt(i);// 顺序号
			cb.writeBytes(pack);
			list.add(cb);
			pos += maxLen;
			if (pos > len) {
				pos = len;
			}
		}
		return list;
	}
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {

        /*心跳处理*/
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /*读超时*/
                System.out.println("udp server READER_IDLE 读超时");
                ctx.channel().close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/   
                System.out.println("udp server WRITER_IDLE 写超时");
                //ctx.writeAndFlush((byte)1);
                //evt.
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                System.out.println("ALL_IDLE 总超时");
            }
        }
    }
}
