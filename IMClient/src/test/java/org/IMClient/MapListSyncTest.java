package org.IMClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.imclient.bean.Pack;
import org.imclient.bean.SubPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapListSyncTest {
	private final static Logger logger = LoggerFactory.getLogger("order");
	static int[] i = new int[1];
	static AtomicInteger ai = new AtomicInteger();
	static Map<Integer, Integer> locks = new HashMap<Integer, Integer>();
	static List<Integer> lockKeys = new ArrayList<Integer>();
	static {
		for (int i = 0; i < 10000; i++) {
			Integer lockKey = new Integer(i);
			lockKeys.add(lockKey);
			locks.put(lockKey, new Integer(i));
		}
	}
    
	static ConcurrentHashMap<String, Pack> map = new ConcurrentHashMap<String, Pack>();
    public static Integer getLockKey(Object uid){
    	
    	int key = Math.abs(uid.hashCode());
    	Integer lockKey = lockKeys.get(key % lockKeys.size());  
		return locks.get(lockKey); 
    }
	public static boolean put(String key, SubPack value) {
		synchronized (getLockKey(key)) {
			Pack pack = map.get(key);
			if (null == pack) {
				pack = new Pack();
				pack.setSubLen(7);
			}
			pack.addSubPack(value);
			map.put(key, pack);
			return pack.isFull();
		}  
	}

	public static Pack remove(String key) {
		return map.remove(key);
	}

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		int size = 100000;
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		CountDownLatch cdl = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 7; j++) {
				SubPack sub = new SubPack();
				sub.setSeq(j);
				sub.setData((j + "").getBytes());
				threadPool.execute(new T(i + "", sub, cdl));
			}
		}
		cdl.await();
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(ai.intValue());
	}

	static class T implements Runnable {

		private String key;
		private SubPack value;
		CountDownLatch cdl;

		public T(String key, SubPack value, CountDownLatch cdl) {
			this.key = key;
			this.value = value;
			this.cdl = cdl;
		}

		@Override
		public void run() {
		 //synchronized(getLockKey(key)){
			boolean isFull = put(key, value);
			if (isFull) {
				// System.out.println(key+"===="+map.get(key));
				//synchronized (getLockKey(key)) {
				   //Pack pack = remove(key);
				  // if (null != pack) {
						//ByteBuf buf = Unpooled.buffer();
						//List<SubPack> list = pack.getSubList();
						//Collections.sort(list);
						//for (SubPack b : list) {
							//buf.writeBytes(b.getData());
						//}
						//logger.info(key+"===="+buf.toString(CharsetUtil.UTF_8));
						ai.incrementAndGet();
					//}
				//}
				
			}

			// }
			cdl.countDown();
		}

	}
}
