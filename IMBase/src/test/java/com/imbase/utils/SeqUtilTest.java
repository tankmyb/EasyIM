package com.imbase.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeqUtilTest {


    public static void main( String[] args ) throws InterruptedException
    {
    	long startTime = System.currentTimeMillis();
    	int size = 100000;
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
			SeqUtil.incrementSeq();
			end.countDown();
		}
    	
    }


}
