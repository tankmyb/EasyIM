package com.imloginer.udp.server;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


public class UDPLoginerChannel {
	private static final ConcurrentHashMap<String, InetSocketAddress> channels = new ConcurrentHashMap<String, InetSocketAddress>();

	public static void addAddress(String key,InetSocketAddress address){
		channels.put(key, address);
	}
	public static InetSocketAddress getAddress(String key){
		return channels.get(key);
	}
	public static InetSocketAddress remove(String key){
			return channels.remove(key);
	}
	public static Collection<String> getKeys(){
		return channels.keySet();
	}
	public static Collection<InetSocketAddress> getValues(){
		return channels.values();
	}
}
