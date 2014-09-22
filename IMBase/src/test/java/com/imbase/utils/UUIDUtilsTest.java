package com.imbase.utils;

import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.imbase.utils.SeqUtilTest.Handler;

public class UUIDUtilsTest {


	private final static ConcurrentHashMap<String, Integer> idTimes = new ConcurrentHashMap<String, Integer>();
    public static void main( String[] args ) throws InterruptedException
    {
    	long startTime = System.currentTimeMillis();
    	int size = 1000000;
    	ExecutorService threadPool = Executors.newFixedThreadPool(100);
    	CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(size);
        for(int i=0;i<size;i++){
        	threadPool.execute(new Handler(start,end));
        }
        start.countDown();
        end.await();
        System.out.println(System.currentTimeMillis()-startTime);
        threadPool.shutdown();
        System.out.println(idTimes.size());
    }
    static class Handler implements Runnable{
        private CountDownLatch start;
        private CountDownLatch end;
        public Handler(CountDownLatch start,CountDownLatch end){
        	this.start = start;
        	this.end = end;
        }
		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			String s = UUIDUtils.uuid();
			if(idTimes.containsKey(s)){
				System.out.println("==================");
			}
			idTimes.put(s, 1);
			end.countDown();
		}
    	
    }




}
