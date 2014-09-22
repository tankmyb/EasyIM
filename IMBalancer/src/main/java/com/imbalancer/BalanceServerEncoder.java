package com.imbalancer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.imapi.bean.balance.BalanceRespBean;
import com.imbase.utils.JacksonUtil;

public class BalanceServerEncoder extends MessageToByteEncoder<BalanceRespBean> {

	@Override
	protected void encode(ChannelHandlerContext ctx, BalanceRespBean respBean,
			ByteBuf out) throws Exception {
		String res = JacksonUtil.getJsonStr(respBean);
		byte[] data = res.getBytes();
		int dataLength = data.length;
		out.writeInt(dataLength);
		out.writeBytes(data);

	}
}