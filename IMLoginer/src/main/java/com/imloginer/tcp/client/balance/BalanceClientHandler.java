package com.imloginer.tcp.client.balance;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Date;

import com.google.protobuf.InvalidProtocolBufferException;
import com.imapi.bean.balance.BalanceRespBean;
import com.imapi.bean.balance.resp.LoginnerConnRespBean;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;
import com.imloginer.tcp.client.loginer.LoginClientHandler;
import com.imloginer.tcp.client.loginer.LoginerClientDecoder;
import com.imloginer.tcp.client.loginer.LoginerClientEncoder;

public class BalanceClientHandler extends
		SimpleChannelInboundHandler<BalanceRespBean> {
	BalanceClientThread thread;
	Channel udpChannel;

	public BalanceClientHandler(Channel udpChannel, BalanceClientThread thread) {
		this.thread = thread;
		this.udpChannel = udpChannel;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx,
			BalanceRespBean respBean)
			throws InvalidProtocolBufferException {
		DebugUtil.print1("====================messageReceived");
		final LoginnerConnRespBean loginnerConnRespBean = JacksonUtil.resolve(respBean.getResp(),LoginnerConnRespBean.class);
		DebugUtil.print1(loginnerConnRespBean.getSeq()
				+ "=======================");

		if (loginnerConnRespBean.getSeq()!=-1) {
			Collection<String> list = loginnerConnRespBean.getConnList();
			thread.add(list);
			DebugUtil.print1(list
					+ "===================" + new Date());
			for (String str : list) {
				final String[] array = str.split(":");
				SocketAddress socketAddress = new InetSocketAddress(array[0],
						Integer.parseInt(array[1])); //
				DebugUtil.print1(socketAddress);
				EventLoopGroup group = new NioEventLoopGroup();
				Bootstrap b = new Bootstrap();
				b.group(group).option(ChannelOption.SO_KEEPALIVE, true)
						.channel(NioSocketChannel.class)
						.option(ChannelOption.SO_SNDBUF,1024*1024)
		.option(ChannelOption.SO_RCVBUF,1024*1024)
						.handler(new ChannelInitializer<SocketChannel>() {

							@Override
							public void initChannel(SocketChannel ch) {
								ChannelPipeline p = ch.pipeline();
								 //decoded
								p.addLast(new LoginerClientDecoder());
						        p.addLast(new LoginerClientEncoder());
								p.addLast(new LoginClientHandler(udpChannel,
										thread, array[0], Integer
												.parseInt(array[1]),
												loginnerConnRespBean.getSeq()));
							}
						});

				try {
					b.connect(socketAddress).sync().channel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		DebugUtil.print1("=`====client====exceptionCaught==========");
		cause.printStackTrace();
		ctx.channel().close();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		DebugUtil.print1("==1===client====close==========");
		super.close(ctx, promise);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		/* 心跳处理 */
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				/* 读超时 */
				// System.out.println("READER_IDLE 读超时");
				ctx.channel().close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				/* 写超时 */
				// System.out.println("WRITER_IDLE 写超时");
				ctx.writeAndFlush((byte) 1);
			} else if (event.state() == IdleState.ALL_IDLE) {
				/* 总超时 */
				System.out.println("ALL_IDLE 总超时");
			}
		}
	}
}
