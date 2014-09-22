
package org.imclient.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.imclient.bean.SendTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imbase.Enum.LogTypeEnum;
import com.imbase.utils.DebugUtil;

public class ReSendScheduler {
	private static final  Logger orderLogger = LoggerFactory.getLogger(LogTypeEnum.ORDER.getValue());
	private static final  Logger logger = LoggerFactory.getLogger(ReSendScheduler.class);
	private final static ConcurrentHashMap<Integer, SendTimes> sendTimeMap = new ConcurrentHashMap<Integer, SendTimes>();
    private final static ConcurrentHashMap<Integer, Future<?>> scheduledFutures = new ConcurrentHashMap<Integer, Future<?>>();
    private final static ScheduledExecutorService executorService =Executors.newScheduledThreadPool(5);
    private  static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static byte[] b = new byte[1];
	//private  Condition done = lock.newCondition();

    public  static void cancel(Integer key) {
    	Lock readLock = lock.readLock();

		readLock.lock();
		try{
    	//synchronized (b) {
    		 Future<?> future = scheduledFutures.remove(key);
    	        if (future != null) {
    	        	logger.error(key+"==can=");
    	        	//orderLogger.info(key+"-"+System.currentTimeMillis());
    	            future.cancel(false);
    	        }
        removeSendTime(key);
    	//}
		}finally{
			readLock.unlock();
		}
    }
    public static void initSendTime(Integer key,String chatId){
    	sendTimeMap.put(key, new SendTimes(1, chatId));
    }
    public static SendTimes getSendTime(Integer key){
    	return sendTimeMap.get(key);
    }
    public static void putSendTme(Integer key,SendTimes value){
    	 sendTimeMap.put(key, value);
    }
    public static SendTimes removeSendTime(Integer key){
    	return sendTimeMap.remove(key);
    }
    public  static void schedule(final Integer key, final Runnable runnable, final long delay, final TimeUnit unit) {
    	Lock writeLock = lock.writeLock();

		writeLock.lock();
		try{
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	DebugUtil.print("==============");
        Future<?> future = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                	scheduledFutures.remove(key);
                	SendTimes sendTime = getSendTime(key);
                	if(sendTime!=null){
                		if(sendTime.getTime()<=2){
                      	  schedule(key,runnable,delay,unit);
                      	  sendTime.setTime(sendTime.getTime()+1);
                      	  putSendTme(key, sendTime);
                      	  //logger.info(key+"-=1-"+new Date());
          				}else {
          					//TODO 记录返回的ＩＤ，下次再发送判断是否真的发送失败。
          					DebugUtil.print(sendTime.getChatId()+"发送失败");
          					removeSendTime(key);
          				}
                	}
                }
                }
        }, delay, unit);
        scheduledFutures.putIfAbsent(key, future);
		//}
		}finally{
			writeLock.unlock();
		}
    		
    	
    }
}
