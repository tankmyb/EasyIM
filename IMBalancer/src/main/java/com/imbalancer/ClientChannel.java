package com.imbalancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientChannel {
	private static final Map<String,String> clients = new HashMap<String,String>();
	
	public static void addClient(String channelId,String clientId){
		clients.put(channelId,clientId);
	}
	public static void removeClient(String channelId){
		clients.remove(channelId);
	}
	public static List<String> getValues(){
		return new ArrayList<String>(clients.values());
	}
	public static int size(){
		return clients.size();
	}
	
}
