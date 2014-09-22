package com.imloginer.bean;


public class SubClientPack implements Comparable<SubClientPack>{

	private Integer seq;
	private byte[] data;
	
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Override
	public int compareTo(SubClientPack o) {
		return this.getSeq().compareTo(o.getSeq());

	} 
}
