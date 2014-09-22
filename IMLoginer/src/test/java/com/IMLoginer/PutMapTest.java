package com.IMLoginer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutMapTest {
    private static byte[] b =new byte[1];
	//private final static ConcurrentHashMap<Integer, Integer> idTimes = new ConcurrentHashMap<Integer, Integer>();
    private final static Map<Integer, Integer> idTimes = new HashMap<Integer, Integer>();
	static AtomicInteger a = new AtomicInteger();
	public static void main(String[] args) throws InterruptedException {
		long s = System.currentTimeMillis();
		int size = 10000000;
		ExecutorService threadPool = Executors.newFixedThreadPool(200);
		CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(size);
		for(int i=0;i<size;i++){
			threadPool.execute(new Handler(start, end,i));
		}
		System.out.println("start");
        start.countDown();
        end.await();
        System.out.println("end");
        threadPool.shutdown();
        System.out.println(System.currentTimeMillis()-s+"============="+a);
	}
	static class Handler implements Runnable{
        private CountDownLatch start;
        private CountDownLatch end;
        private int i;
        public Handler(CountDownLatch start,CountDownLatch end,int i){
        	this.start = start;
        	this.end = end;
        	this.i = i;
        }
		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			//synchronized (b) {
				idTimes.put(i, i);
				if(idTimes.size()>500){
					a.incrementAndGet();
				}
			//}
			
			end.countDown();
		}
    	
    }
}
