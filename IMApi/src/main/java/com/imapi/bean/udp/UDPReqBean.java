package com.imapi.bean.udp;

import java.net.InetSocketAddress;

public class UDPReqBean {

	private int type;
	private String req;
	private InetSocketAddress sender;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReq() {
		return req;
	}
	public void setReq(String req) {
		this.req = req;
	}
	
	public InetSocketAddress getSender() {
		return sender;
	}
	public void setSender(InetSocketAddress sender) {
		this.sender = sender;
	}
}
