package com.imloginer.tcp.channel;

import io.netty.channel.ChannelHandlerContext;

public class EasyChannel {

	public static final int CLIENT_TYPE=1;
	public static final int SERVER_TYPE=2;
	private int type;
	private ChannelHandlerContext ctx;
	public EasyChannel(){
		
	}
	public EasyChannel(int type,ChannelHandlerContext ctx){
		this.type = type;
		this.ctx =ctx;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
}
