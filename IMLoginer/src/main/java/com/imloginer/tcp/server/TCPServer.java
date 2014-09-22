package com.imloginer.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.imbase.utils.DebugUtil;
import com.imloginer.redis.RedisService;
import com.imloginer.tcp.client.balance.BalanceClientThread;
import com.imloginer.udp.server.UDPServer;

public class TCPServer {

	public void init(Channel udpChannel,int port) throws InterruptedException{
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .option(ChannelOption.SO_SNDBUF,1024*1024)
		.option(ChannelOption.SO_RCVBUF,1024*1024)
             .channel(NioServerSocketChannel.class)
             //.handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new TCPServerInitializer(udpChannel,port));

            b.bind(port).sync();
        
	}
	public void shutdown(){
		//bossGroup.shutdownGracefully();
        //workerGroup.shutdownGracefully();
	}
	public static void main(String[] args) throws Exception {
		//for(int i=0;i<1000;i++){
		    int i=3;
			int tcpPport = 10000+i;
			int udpPort = 9008+i;
			DebugUtil.print1(tcpPport+"==="+udpPort);
			RedisService redisService = new RedisService();
			UDPServer server = new UDPServer();
	       final Channel ch = server.start(udpPort,redisService,tcpPport);
			new TCPServer().init(ch,tcpPport);
			new BalanceClientThread(tcpPport,"127.0.0.1",7005,ch);
			
		//}
		
	     /*new Thread(new Runnable() {
			
			public void run() {
				for(int i=1;i<=5;i++){
				try {
					Thread.sleep(15*i*1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				System.out.println("================================1===="+new Date());
				Collection<InetSocketAddress> coll = UDPLoginerChannel.getValues();
				System.out.println(coll);
				for(InetSocketAddress a:coll){
					ch.writeAndFlush(new DatagramPacket(
	    	                Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8),
	    	                a));
				}
				}
			}
		}).start();*/
	     
		//new UdpServer().init(udpPort);
	}
}
