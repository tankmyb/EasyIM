package com.imbase.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;

import com.imbase.netty.codec.serializer.KryoSerializer;
import com.imbase.netty.codec.serializer.Serializer;

public class KryoDecoder extends ByteToMessageDecoder {
	private Serializer serializer = new KryoSerializer();

	public KryoDecoder() {
	}

	public KryoDecoder(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		//System.out.println("===1=====decode========"+new Date());
		if (in.readableBytes() < 4) {
			return;// (1)
		}
		//System.out.println("=====2===decode========"+new Date());
		int dataLength = in.getInt(in.readerIndex());
		if (in.readableBytes() < dataLength + 4) {
			return;// (2)
		}
		//System.out.println("====3====decode========"+new Date());
		in.skipBytes(4);// (3)
		byte[] decoded = new byte[dataLength];
		in.readBytes(decoded);
		Object msg;
		msg = serializer.deserialize(decoded);
		out.add(msg);
		//System.out.println("===4=====decode========"+new Date());
	}

}
