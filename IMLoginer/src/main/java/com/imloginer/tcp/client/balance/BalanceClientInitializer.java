package com.imloginer.tcp.client.balance;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class BalanceClientInitializer extends ChannelInitializer<SocketChannel> {

	BalanceClientThread thread;
	Channel udpChannel;
	public BalanceClientInitializer(Channel udpChannel,BalanceClientThread thread){
		this.thread = thread;
		this.udpChannel = udpChannel;
	}
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        
        //decoded
        p.addLast(new BalanceClientDecoder());
        p.addLast(new BalanceClientEncoder());
        //p.addLast(new IdleStateHandler(30,10,0));
        p.addLast(new BalanceClientHandler(udpChannel,thread));
    }

}
