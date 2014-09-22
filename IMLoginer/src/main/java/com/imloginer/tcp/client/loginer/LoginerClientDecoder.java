package com.imloginer.tcp.client.loginer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.imapi.bean.loginer.LoginerRespBean;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;

public class LoginerClientDecoder extends ByteToMessageDecoder {
 
  

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
            return;
        }
        int dataLength = in.getInt(in.readerIndex());
        if (in.readableBytes() < dataLength + 4) {
            return;
        }
        in.skipBytes(4);
        byte type= in.getByte(in.readerIndex());
        DebugUtil.print1(type+"====");
        in.skipBytes(1);
        //业务
        if(type==(byte)0){
        	byte[] decoded = new byte[dataLength];
            in.readBytes(decoded);
            String msg = new String(decoded);//(4)
            out.add(JacksonUtil.resolve(msg, LoginerRespBean.class));
        }
      //聊天（不分包）
        else if(type==(byte)1){
            ByteBuf buf = Unpooled.buffer(dataLength+1+4);
        	buf.writeInt(dataLength);
        	buf.writeByte(1);
        	buf.writeBytes(in, in.readerIndex(), dataLength);
        	in.skipBytes(dataLength);
            out.add(buf);
        }
      //聊天（分包）
        else if(type==(byte)2){
        	ByteBuf buf = Unpooled.buffer(dataLength+4+4+4+1);
        	buf.writeInt(dataLength);
        	buf.writeByte(2);
        	buf.writeInt(in.getInt(in.readerIndex()));
        	in.skipBytes(4);
        	buf.writeInt(in.getInt(in.readerIndex()));
        	in.skipBytes(4);
        	buf.writeInt(in.getInt(in.readerIndex()));
        	in.skipBytes(4);
        	buf.writeBytes(in, in.readerIndex(), dataLength);
        	in.skipBytes(dataLength);
            out.add(buf);
        }
      
		
		
	}
}
