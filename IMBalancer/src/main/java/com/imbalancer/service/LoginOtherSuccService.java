package com.imbalancer.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

import com.imapi.bean.balance.BalanceReqBean;
import com.imapi.bean.balance.req.LoginerConnOtherSuccReqBean;
import com.imapi.common.BalanceReqType;
import com.imbalancer.SyncResponseFuture;
import com.imbalancer.annotation.ExecuteService;
import com.imbase.utils.JacksonUtil;

@ExecuteService(value=BalanceReqType.LOGIN_OTHER_SUCC)
public class LoginOtherSuccService implements IService{

	public void execute( ChannelHandlerContext ctx,
			BalanceReqBean reqBean) {

		final LoginerConnOtherSuccReqBean loginerConnOtherSuccReqBean = JacksonUtil
				.resolve(reqBean.getReq(),
						LoginerConnOtherSuccReqBean.class);
		System.out.println(new Date() + "====syncReq====="
				+ loginerConnOtherSuccReqBean.getSeq());
		SyncResponseFuture.received(loginerConnOtherSuccReqBean);
	
	}

	
}
