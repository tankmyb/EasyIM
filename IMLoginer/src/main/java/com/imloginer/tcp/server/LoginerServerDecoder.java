package com.imloginer.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imapi.bean.loginer.LoginerReqBean;
import com.imbase.utils.JacksonUtil;

public class LoginerServerDecoder extends ByteToMessageDecoder {
	private final Logger logger = LoggerFactory.getLogger("ws");

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < 4) {
			return;// (1)
		}
		int dataLength = in.getInt(in.readerIndex());
		if (in.readableBytes() < dataLength) {
			return;// (2)
		}
		in.skipBytes(4);// (3)
		byte type = in.getByte(in.readerIndex());
		// System.out.println(type+"====");
		in.skipBytes(1);// (3)
		// 业务
		if (type == (byte) 0) {
			byte[] decoded = new byte[dataLength];
			in.readBytes(decoded);
			String msg = new String(decoded);// (4)
			out.add(JacksonUtil.resolve(msg, LoginerReqBean.class));
		}
		// 聊天（不分包）
		else if (type == (byte) 1) {
			int headLen = 1 + 4;
			ByteBuf buf = Unpooled.buffer(dataLength);
			buf.writeInt(dataLength);
			buf.writeByte(1);
			int len = dataLength - headLen;
			buf.writeBytes(in, in.readerIndex(), len);
			in.skipBytes(len);
			logger.error(buf.toString(CharsetUtil.UTF_8));
			out.add(buf);
		}
		// 聊天（分包）
		else if (type == (byte) 2) {
			int headLen = 4 + 4 + 4 + 4 + 1;
			ByteBuf buf = Unpooled.buffer(dataLength);
			buf.writeInt(dataLength);
			buf.writeByte(2);
			buf.writeInt(in.getInt(in.readerIndex()));
			in.skipBytes(4);
			buf.writeInt(in.getInt(in.readerIndex()));
			in.skipBytes(4);
			buf.writeInt(in.getInt(in.readerIndex()));
			in.skipBytes(4);
			int len = dataLength - headLen;
			buf.writeBytes(in, in.readerIndex(), len);
			in.skipBytes(len);
			logger.error(buf.toString(CharsetUtil.UTF_8));
			out.add(buf);
		}

	}
}
