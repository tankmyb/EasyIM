package com.imbalancer.service;

import io.netty.channel.ChannelHandlerContext;

import com.imapi.bean.balance.BalanceReqBean;

public interface IService {

	public void execute( ChannelHandlerContext ctx,
			BalanceReqBean reqBean);
}
