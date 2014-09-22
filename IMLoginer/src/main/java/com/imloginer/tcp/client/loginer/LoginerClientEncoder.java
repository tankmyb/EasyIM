package com.imloginer.tcp.client.loginer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.imapi.bean.loginer.LoginerReqBean;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;
public class LoginerClientEncoder extends MessageToByteEncoder<Object> {
 
   int i=0;
	@Override
	protected void encode(ChannelHandlerContext ctx, Object reqBean, ByteBuf out)
			throws Exception {
		if(reqBean instanceof LoginerReqBean){
			String res = JacksonUtil.getJsonStr(reqBean);
	        byte[] data = res.getBytes();
	        int dataLength = data.length;
	        DebugUtil.print1(dataLength+"====dataLength");
	        out.writeInt(dataLength);
	        out.writeByte(0);
	        out.writeBytes(data);
		}else if(reqBean instanceof ByteBuf){
			out.writeBytes((ByteBuf)reqBean);
		}
		
		
	}
}