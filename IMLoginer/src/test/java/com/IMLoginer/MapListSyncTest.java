package com.IMLoginer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class MapListSyncTest {
	static int[] i = new int[1];
	static ConcurrentHashMap<Integer,List<Integer>> map = new ConcurrentHashMap<Integer, List<Integer>>();
	public  static void put(Integer key,Integer value){
		List<Integer> list = map.get(key);
		if(null==list){
			list = new ArrayList<Integer>();
		}
		list.add(value);
		map.put(key, list);
		
	}
	public static synchronized int size(Integer key){
		try{
			return map.get(key).size();
		}catch(Exception e){
			return 0;
		}
		
	}
	public static synchronized void remove(Integer key){
		//System.out.println(key+"=====================");
		map.remove(key);
	}
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
		for(int i=0;i<100;i++){
			for(int j=0;j<7;j++){
				threadPool.execute(new T(i,j));
			}
		
		}
	}
	static class T implements Runnable{

		private Integer key;
		private Integer value;
		public T(Integer key,Integer value){
			this.key = key;
			this.value=value;
		}
		@Override
		public  void run() {
			//synchronized(i){
				put(key, value);
				if(size(key)==7){
					System.out.println(key+"===="+map.get(key));
					remove(key);
				}
			//}
			
		}
		
	}
}
