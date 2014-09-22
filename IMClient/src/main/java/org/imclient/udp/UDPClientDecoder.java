package org.imclient.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.imclient.bean.Ack;
import org.imclient.bean.SubPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.imbase.utils.DebugUtil;

public class UDPClientDecoder extends MessageToMessageDecoder<DatagramPacket> {
	private final Logger logger = LoggerFactory.getLogger("order");
	static Multimap<Integer,SubPack> map = HashMultimap.create();
	public void putPack(Integer key, Integer seq, byte[] b) {
		SubPack subClientPack = new SubPack();
		subClientPack.setSeq(seq);
		subClientPack.setData(b);
		map.put(key, subClientPack);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg,
			List<Object> out) throws Exception {
		ByteBuf in = msg.content();
		int dataLength = in.getInt(in.readerIndex());
		in.skipBytes(4);
		byte type = in.getByte(in.readerIndex());
		in.skipBytes(1);
		DebugUtil.print1(type + "==============");
		if (type == (byte) 1) {
			int len =dataLength-4-1;
			byte[] decoded = new byte[len];
			in.readBytes(decoded);
			out.add(new String(decoded));
		}else if (type == (byte) 3) {
			int len =dataLength-1;
			byte[] decoded = new byte[len];
			in.readBytes(decoded);
			out.add(new String(decoded));
		}else if (type == (byte) 2 || type == (byte) 4) {
			int sid = in.getInt(in.readerIndex());
			in.skipBytes(4);
			int totalNum = in.getInt(in.readerIndex());
			in.skipBytes(4);
			DebugUtil.print1("total:" + totalNum+"==="+sid);
			int seq = in.getInt(in.readerIndex());
			DebugUtil.print1(seq);
			in.skipBytes(4);
			byte[] decoded = new byte[dataLength-4-4-4-4-1];
			in.readBytes(decoded);
			putPack(sid, seq, decoded);
			DebugUtil.print1(sid + "==44=" + map.size() + "=" + seq);

			ByteBuf buf = Unpooled.buffer();
			Collection<SubPack> list = map.get(sid);
			if (list.size() == totalNum) {
				DebugUtil.print1("=====list.size()===========" + list.size());
				Collections.sort((List)list);
				for (SubPack b : list) {
					buf.writeBytes(b.getData());
				}
				list.clear();
				map.removeAll(sid);
				out.add(buf.toString(CharsetUtil.UTF_8));
				//logger.info(buf.toString(CharsetUtil.UTF_8));
			}
		} else if (type == (byte) 5) {
			int oid =in.getInt(in.readerIndex());
			in.skipBytes(4);
			Ack ack = new Ack();
			ack.setOid(oid);
			out.add(ack);
		}

	}
}
