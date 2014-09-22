package com.imbase.netty.codec;

import com.imbase.netty.codec.serializer.KryoSerializer;
import com.imbase.netty.codec.serializer.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoEncoder extends MessageToByteEncoder<Object>{
	private Serializer serializer = new KryoSerializer();

	public KryoEncoder() {
	}

	public KryoEncoder(Serializer serializer) {
		this.serializer = serializer;
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		//System.out.println("===========encode========="+ctx.channel().id().asLongText());
		byte[] encodedMessage = serializer.serialize(msg);
		out.writeInt(encodedMessage.length);
		//System.out.println(encodedMessage.length+"====encodedMessage.length");
		out.writeBytes(encodedMessage);
		
	}

}
