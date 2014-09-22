package com.imloginer.tcp.client.balance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.imapi.bean.balance.BalanceReqBean;
import com.imbase.utils.JacksonUtil;

public class BalanceClientEncoder extends MessageToByteEncoder<BalanceReqBean> {

	@Override
	protected void encode(ChannelHandlerContext ctx, BalanceReqBean reqBean,
			ByteBuf out) throws Exception {
		String res = JacksonUtil.getJsonStr(reqBean);
		byte[] data = res.getBytes();
		int dataLength = data.length;
		out.writeInt(dataLength);
		out.writeBytes(data);

	}
}