package com.IMLoginer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapTest {
    private static byte[] b =new byte[1];
	private final static ConcurrentHashMap<Integer, Integer> idTimes = new ConcurrentHashMap<Integer, Integer>();
	public static void main(String[] args) throws InterruptedException {
		/*for(int i=0;i<100;i++){
			idTimes.put(i++, 1);
		}*/
		int size = 1000;
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
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
			synchronized (b) {
				idTimes.remove(i);
				if(idTimes.size()>0){
					System.out.println("======="+i);
				}
			}
			
			end.countDown();
		}
    	
    }
}
