package com.imbase.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseUtil {

	public static String getHostIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("query host ip error");
	}
}
