package com.imbalancer.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResourceMap {
	public static Map<String, Object> resourceMap = new HashMap<String, Object>();

	public static void addResource(Object obj) {
		System.out.println(obj.getClass().getName());
		resourceMap.put(obj.getClass().getName(), obj);
	}
	public static boolean containsResource(String key){
		return resourceMap.containsKey(key);
	}
	public static Object getResource(String key){
		return resourceMap.get(key);
	}
	public static Collection<Object> getValues() {
		return resourceMap.values();
	}
}
