package com.imbalancer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.imbalancer.util.ResourceMap;
import com.imbalancer.util.ServiceMap;

public class BalanceServer {
	public EventLoopGroup workerGroup = new NioEventLoopGroup(10);

	public void init(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);

		
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
		// .handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new BalanceInitializer());

		b.bind(port).sync();

	}

	public static void main(String[] args) throws InterruptedException {
		final BalanceServer server = new BalanceServer();
		QueueThread queueThread = new QueueThread();
		new Thread(queueThread).start();
		ResourceMap.addResource(queueThread);
		ServiceMap.initService();
		System.out.println(ServiceMap.getValues());
		server.init(7005);
		/*
		 * new Thread(new Runnable() {
		 * 
		 * public void run() { while(true){ try { Thread.sleep(5000); } catch
		 * (InterruptedException e) { // TODO 自动生成的 catch 块 e.printStackTrace();
		 * } System.out.println("====================");
		 * System.out.println("====================");
		 * System.out.println("====================");
		 * //System.out.println(server.workerGroup.);
		 * System.out.println("====================");
		 * System.out.println("====================");
		 * System.out.println("===================="); }
		 * 
		 * } }).start();
		 */
	}
}
