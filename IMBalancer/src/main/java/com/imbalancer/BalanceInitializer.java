package com.imbalancer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class BalanceInitializer extends ChannelInitializer<SocketChannel> {
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		// decoded
		p.addLast(new BalanceServerDecoder());
		p.addLast(new BalanceServerEncoder());
		// p.addLast(new IdleStateHandler(30,10,0));
		p.addLast(new BalanceServerHandler());
	}

}
