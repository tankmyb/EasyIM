package com.imloginer.udp.server;

import java.util.List;

import com.imloginer.bean.SubPack;

public class UDPPack {

	private String chatNo;
	private List<SubPack> list;
	public String getChatNo() {
		return chatNo;
	}
	public void setChatNo(String chatNo) {
		this.chatNo = chatNo;
	}
	public List<SubPack> getList() {
		return list;
	}
	public void setList(List<SubPack> list) {
		this.list = list;
	}
}
