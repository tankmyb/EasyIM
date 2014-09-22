package com.imapi.common;

public class BalanceReqType {

	/**
	 * 登录器启动连接均衡器
	 */
	public final static byte LOGINER_CONN=1;
	/**
	 * 登录器连接其它登录器成功后，再连接均衡器
	 */
	public final static byte LOGIN_OTHER_SUCC=2;
	
}
