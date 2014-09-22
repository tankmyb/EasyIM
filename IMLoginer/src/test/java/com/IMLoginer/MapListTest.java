package com.IMLoginer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

public class MapListTest {
	static Multimap<Integer,Integer> map = LinkedHashMultimap.create();
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<100;i++){
			list.add(i+1);
		}
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
		public void run() {
			map.put(key, value);
			if(map.get(key).size()==7){
				System.out.println(key);
			}
		}
		
	}
}
