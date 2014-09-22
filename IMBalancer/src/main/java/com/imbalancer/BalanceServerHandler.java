package com.imbalancer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import com.google.protobuf.InvalidProtocolBufferException;
import com.imapi.bean.balance.BalanceReqBean;
import com.imbalancer.util.ServiceMap;

public class BalanceServerHandler extends
		SimpleChannelInboundHandler<BalanceReqBean> {

	@Override
	public void messageReceived(final ChannelHandlerContext ctx,
			BalanceReqBean reqBean) throws InvalidProtocolBufferException {
		/*
		 * try { Thread.sleep(100000); } catch (InterruptedException e) { //
		 * TODO 自动生成的 catch 块 e.printStackTrace(); }
		 */
		System.out.println("==============");
		ServiceMap.getService(reqBean.getType()).execute(ctx, reqBean);

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// System.out.println(ctx.channel().remoteAddress());
		super.channelActive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("=====client====exceptionCaught=========="
				+ cause.getCause());
		ctx.channel().close();
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("=====client====disconnect==========");
		System.out.println(ClientChannel.getValues());
		ClientChannel.removeClient(ctx.channel().id().asLongText());
		System.out.println(ClientChannel.getValues());
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
				System.out.println("11READER_IDLE 读超时");
				ctx.channel().close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				/* 写超时 */
				System.out.println("WRITER_IDLE 写");
				ctx.writeAndFlush((byte) 1);
			} else if (event.state() == IdleState.ALL_IDLE) {
				/* 总超时 */
				System.out.println("ALL_IDLE 总超时");
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		// 这里加入玩家的掉线处理
		ctx.close();

	}

}
