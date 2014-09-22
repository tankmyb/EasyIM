package com.imloginer.bean;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.imbase.utils.DebugUtil;

public class SubPack implements Comparable<SubPack>{

	private Integer seq;
	private ByteBuf buf;
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
	public ByteBuf getBuf() {
		return buf;
	}
	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}
	@Override
	public int compareTo(SubPack o) {
		return this.getSeq().compareTo(o.getSeq());

	} 
	public static void main(String[] args) {
		List<SubPack> list = new ArrayList<SubPack>();
		SubPack s1 = new SubPack();
		s1.setSeq(3);
		list.add(s1);
		SubPack s2 = new SubPack();
		s2.setSeq(4);
		list.add(s2);
		SubPack s3 = new SubPack();
		s3.setSeq(2);
		list.add(s3);
		SubPack s4 = new SubPack();
		s4.setSeq(1);
		list.add(s4);
		Collections.sort(list);
		for(SubPack s:list){
			DebugUtil.print(s.getSeq());
		}
		
	}
}
