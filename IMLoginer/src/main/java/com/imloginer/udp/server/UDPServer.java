package com.imloginer.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

import com.imloginer.redis.RedisService;



public class UDPServer {

	public Channel start(int port,final RedisService redisService,final int tcpPort) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup(16);
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioDatagramChannel.class)
		.option(ChannelOption.SO_SNDBUF,1024*1024)
		.option(ChannelOption.SO_RCVBUF,1024*1024)
		// .option(ChannelOption.SO_BROADCAST, true)
				.handler(new ChannelInitializer<DatagramChannel>() {
					
				    @Override
				    public void initChannel(DatagramChannel ch) throws Exception {
				        ChannelPipeline p = ch.pipeline();
				        p.addLast(new UDPServerDecoder());
				        //p.addLast(new IdleStateHandler(30,10,0));
				        p.addLast(new UDPServerHandler(redisService,tcpPort));
				    }
				});
		return b.bind(port).sync().channel();

	}


}
