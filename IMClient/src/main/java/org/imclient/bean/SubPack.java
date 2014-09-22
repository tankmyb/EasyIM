package org.imclient.bean;


public class SubPack implements Comparable<SubPack>{

	private Integer seq;
	private byte[] data;
	
	public SubPack(){
		
	}
	public SubPack(Integer seq,byte[] data){
		this.seq = seq;
		this.data = data;
	}
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
	public int compareTo(SubPack o) {
		return this.getSeq().compareTo(o.getSeq());

	} 
}
