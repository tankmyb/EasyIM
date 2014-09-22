package com.imloginer.udp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imapi.bean.udp.UDPReqBean;
import com.imapi.common.UDPMainType;
import com.imbase.utils.DebugUtil;
import com.imbase.utils.JacksonUtil;
import com.imloginer.bean.FullChat;
import com.imloginer.bean.SubPack;

public class UDPServerDecoder extends MessageToMessageDecoder<DatagramPacket> {
	private final Logger logger = LoggerFactory.getLogger("debug");
	public static ConcurrentMap<Integer, List<SubPack>> map = new ConcurrentHashMap<Integer, List<SubPack>>();

	public void putSubPack(Integer key, Integer seq,ByteBuf buf) {
		List<SubPack> list = map.get(key);
		if (list == null) {
			list = new ArrayList<SubPack>();
		}
		SubPack subPack = new SubPack();
		subPack.setSeq(seq);
		subPack.setBuf(buf);
		list.add(subPack);
		map.putIfAbsent(key, list);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg,
			List<Object> out) throws Exception {

		ByteBuf in = msg.content();
		int dataLength = in.getInt(in.readerIndex());
		in.skipBytes(4);
		byte type = in.getByte(in.readerIndex());
		in.skipBytes(1);
		DebugUtil.print("=============type==========" + type);
		int oid= in.getInt(in.readerIndex());
		DebugUtil.print(oid+"===========oid");
		in.skipBytes(4);
		ByteBuf ackbuf = Unpooled.buffer(4 + 1 + 4);
		ackbuf.writeInt(4+4+1);
		ackbuf.writeByte(5);
		ackbuf.writeInt(oid);
		ctx.writeAndFlush(new DatagramPacket(ackbuf,
                msg.sender()));
		// 聊天（不分包）
		if (type == UDPMainType.FULL_CHAT) {
			int chatNoLen = in.getInt(in.readerIndex());
			DebugUtil.print1(chatNoLen);
			in.skipBytes(4);
			String chatNo = null;
			chatNo = in
					.toString(in.readerIndex(), chatNoLen, CharsetUtil.UTF_8);
			DebugUtil.print1(chatNo + "===chatNo");
			in.skipBytes(chatNoLen);
			
			ByteBuf buf = Unpooled.buffer(dataLength + 1 + 4);
			buf.writeInt(dataLength+4+1);
			buf.writeByte(1);
			buf.writeBytes(in, in.readerIndex(), dataLength);
			in.skipBytes(dataLength);
			//logger.debug("udp send:" + buf.toString(CharsetUtil.UTF_8));
			FullChat data = new FullChat();
			data.setChatNo(chatNo);
			data.setBuf(buf);
			out.add(data);
		}
		// 聊天（分包）
		else if (type == (byte) 2) {
			int chatNoLen = in.getInt(in.readerIndex());
			in.skipBytes(4);
			String chatNo = in.toString(in.readerIndex(), chatNoLen,
					CharsetUtil.UTF_8);
			DebugUtil.print1(chatNo + "===chatNo");
			in.skipBytes(chatNoLen);
			int sid = in.getInt(in.readerIndex());
			in.skipBytes(4);
			int totalNum = in.getInt(in.readerIndex());
			in.skipBytes(4);
			DebugUtil.print1("total:" + totalNum+"==sid:"+sid);
			int seq = in.getInt(in.readerIndex());
			DebugUtil.print1(seq);
			in.skipBytes(4);

			int headLen=  1 + 4+4+4+4;
			ByteBuf buf = Unpooled.buffer(dataLength + headLen);
			buf.writeInt(dataLength + headLen);
	        buf.writeByte(UDPMainType.SUB_CHAT);
	        buf.writeInt(sid);
	        buf.writeInt(totalNum);
	        buf.writeInt(seq);
			buf.writeBytes(in, in.readerIndex(), dataLength);
			in.skipBytes(dataLength);
			
			putSubPack(sid, seq, buf);
			DebugUtil.print1(sid + "==44=" + map.size() + "=" + seq);

			int size = map.get(sid).size();
			if (size == totalNum) {
				List<SubPack> list = map.get(sid);
				DebugUtil.print1(sid+"=====list.size()===========" + size);
				Collections.sort(list);
				UDPPack udpPack = new UDPPack();
				udpPack.setChatNo(chatNo);
				udpPack.setList(new ArrayList<SubPack>(list));
				out.add(udpPack);
				//logger.debug(udpPack.getList().get(0).getBuf().toString(CharsetUtil.UTF_8));
				list.clear();
				map.remove(sid);
			}
		}
		// 业务（不分包）
		else if (type == (byte) 3) {
			DebugUtil.print1(dataLength);
			byte[] decoded = new byte[dataLength];
			in.readBytes(decoded);
			String content = new String(decoded);
			DebugUtil.print1("udp send:" + content);
			UDPReqBean udpReqBean = JacksonUtil.resolve(content,
					UDPReqBean.class);
			udpReqBean.setSender(msg.sender());
			out.add(udpReqBean);

		}
		// 业务（分包）
		else if (type == (byte) 4) {/*
			int sid = in.getInt(in.readerIndex());
			in.skipBytes(4);
			int totalNum = in.getInt(in.readerIndex());
			in.skipBytes(4);
			System.out.println("total:" + totalNum);
			int seq = in.getInt(in.readerIndex());
			System.out.println(seq);
			in.skipBytes(4);

			System.out.println(dataLength);
			byte[] decoded = new byte[dataLength];
			in.readBytes(decoded);
			putPack(sid, seq, decoded, totalNum, type);
			System.out.println(sid + "==44=" + map.size() + "=" + seq);

			int size = map.get(sid).size();
			if (size == totalNum) {
				List<SubPack> list = map.get(sid);
				System.out.println("=====list.size()===========" + size);
				Collections.sort(list);
				ByteBuf buf = Unpooled.buffer();
				for (SubPack b : list) {
					buf.writeBytes(b.getData());
				}
				list.clear();
				map.remove(sid);
				System.out.println(buf.toString(CharsetUtil.UTF_8));
				UDPReqBean udpReqBean = JacksonUtil.resolve(
						buf.toString(CharsetUtil.UTF_8), UDPReqBean.class);
				udpReqBean.setSender(msg.sender());
				out.add(udpReqBean);

			}
		*/}

	}
}
