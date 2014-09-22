package org.imclient.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.imclient.util.ReSendScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imbase.utils.DebugUtil;
import com.imbase.utils.SeqUtil;

public class UDPClient {
	private final Logger logger = LoggerFactory.getLogger("order");
	private Channel ch;
	private String host;
	private int port;

	public UDPClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_SNDBUF, 1024 * 1024 * 10)
				.option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 10)
				.handler(new ChannelInitializer<DatagramChannel>() {
					@Override
					public void initChannel(DatagramChannel ch)
							throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new UDPClientDecoder());
						p.addLast(new Handler(new InetSocketAddress(host, port)));
					}
				});

		ch = b.bind(0).sync().channel();

	}

	private void write(ByteBuf dp) {
		ch.writeAndFlush(new DatagramPacket(dp, new InetSocketAddress(host,
				port)));
	}

	public void req(String reqStr) {
		byte[] b = reqStr.getBytes();
		Integer len = b.length;
		final ByteBuf cb = Unpooled.buffer(len + 4);
		cb.writeInt(b.length);
		int oid = -1;
		cb.writeByte(3);
		oid = SeqUtil.incrementSeq();
		cb.writeInt(oid);
		cb.writeBytes(b);
		write(cb);
	}

	public void chat(String chatId, String chatNo, String str) {
		int maxLen = 512;
		byte[] b = str.getBytes();
		Integer len = b.length;
		if (len <= maxLen) {
			// System.out.println(len + "====len");
			final ByteBuf cb = Unpooled.buffer(len + 4);
			cb.writeInt(b.length);
				cb.writeByte(1);
				final int oid = SeqUtil.incrementSeq();
				cb.writeInt(oid);
				int chatNoLen = chatNo.length();
				cb.writeInt(chatNoLen);
				cb.writeBytes(chatNo.getBytes());


			cb.writeBytes(b);
			final ByteBuf bb = Unpooled.copiedBuffer(cb);
			ReSendScheduler.initSendTime(oid, chatId);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					resend(oid, bb);
				}
			}).start();
			
			write(cb);
			
			return;
		}
		int packNum = len / maxLen;
		if (len % maxLen != 0) {
			packNum++;
		}
		int pos = 0;
		// System.out.println("packNum:" + packNum);
		int sid = SeqUtil.incrementSeq();
		for (int i = 0; i < packNum; i++) {
			int length = maxLen;
			if (len - pos < length) {
				length = len - pos;
			}
			byte[] pack = new byte[length];

			System.arraycopy(b, pos, pack, 0, length);
			// final ByteBuf cb = Unpooled.buffer(maxLen);
			// System.out.println(length);
			final ByteBuf cb = Unpooled.buffer(length + 1 + 4 + 4 + 4 + 4
					+ chatNo.getBytes().length);
			cb.writeInt(pack.length);
			int oid = -1;
				cb.writeByte(2);
				oid = SeqUtil.incrementSeq();
				cb.writeInt(oid);
				int chatNoLen = chatNo.length();
				cb.writeInt(chatNoLen);
				cb.writeBytes(chatNo.getBytes());
			
			cb.writeInt(sid);// 序列号
			cb.writeInt(packNum);// 分包数
			cb.writeInt(i);// 顺序号
			cb.writeBytes(pack);
			write(cb);
			logger.info(oid + "");
			ReSendScheduler.initSendTime(oid, chatId);
			resend(oid, cb);
			pos += maxLen;
			if (pos > len) {
				pos = len;
			}
		}
	}

	public void resend(final int id, final ByteBuf cb) {
		ReSendScheduler.schedule(id, new Runnable() {
			@Override
			public void run() {
				DebugUtil.print("======================1");
				DebugUtil.print("");
				write(Unpooled.copiedBuffer(cb));

			}
		}, 3, TimeUnit.SECONDS);

	}
}
