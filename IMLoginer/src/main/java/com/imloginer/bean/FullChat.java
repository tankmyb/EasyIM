package com.imloginer.bean;

import io.netty.buffer.ByteBuf;

public class FullChat {
	private String chatNo;

	private ByteBuf buf;

	public ByteBuf getBuf() {
		return buf;
	}

	public void setBuf(ByteBuf buf) {
		this.buf = buf;
	}

	public String getChatNo() {
		return chatNo;
	}

	public void setChatNo(String chatNo) {
		this.chatNo = chatNo;
	}
}
