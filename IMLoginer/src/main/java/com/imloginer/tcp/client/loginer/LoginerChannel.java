package com.imloginer.tcp.client.loginer;

import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.imloginer.tcp.channel.EasyChannel;


public class LoginerChannel {
	private static final ConcurrentHashMap<String, EasyChannel> channels = new ConcurrentHashMap<String, EasyChannel>();
	private static final ConcurrentHashMap<String, String> channelIds = new ConcurrentHashMap<String, String>();

	public static void addChannel(String key,int type,ChannelHandlerContext ctx){
		channels.put(key, new EasyChannel(type,ctx));
		channelIds.put(ctx.channel().id().asLongText(), key);
	}
	public static EasyChannel remove(String channelId){
		String key = channelIds.get(channelId);
		if(StringUtils.isNotBlank(key)){
			return channels.remove(key);
		}
		return null;
	}
	public static EasyChannel getChannel(String address){
		return channels.get(address);
	}
	public static Collection<String> getKeys(){
		return channels.keySet();
	}
	public static Collection<EasyChannel> getValues(){
		return channels.values();
	}
}
