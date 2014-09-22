package com.imloginer.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.imapi.bean.loginer.LoginerRespBean;
import com.imbase.utils.JacksonUtil;
public class LoginerServerEncoder extends MessageToByteEncoder<Object> {
 

	

	@Override
	protected void encode(ChannelHandlerContext ctx, Object respBean, ByteBuf out)
			throws Exception {
		if(respBean instanceof LoginerRespBean){
		String res = JacksonUtil.getJsonStr(respBean);
        byte[] data = res.getBytes();
        int dataLength = data.length;
        
        out.writeInt(dataLength);
        out.writeByte(0);
        out.writeBytes(data);
		}else if(respBean instanceof ByteBuf){
			out.writeBytes((ByteBuf)respBean);
		}
	}
}