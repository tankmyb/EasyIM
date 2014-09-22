package com.imbalancer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imapi.bean.balance.req.LoginerConnOtherSuccReqBean;

public class SyncResponseFuture {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final ConcurrentHashMap<Integer, SyncResponseFuture> futures = new ConcurrentHashMap<Integer, SyncResponseFuture>();

	private final Lock lock = new ReentrantLock();

	private final Condition done = lock.newCondition();

	private LoginerConnOtherSuccReqBean response;

	public SyncResponseFuture(Integer seq) {
		futures.put(seq, this);
	}

	public Object get(long seq)  {
		lock.lock();
		try {
			// 可以增加超时机制
			while (!isDone()) {
				done.await(20000, TimeUnit.MILLISECONDS);
				if (null == response) {
					logger.error("sync req seq:{} await timeout", seq);
					futures.remove(seq);
					break;
				}

			}
		} catch (InterruptedException e) {
			if (null == response) {
				logger.error("sync req seq:{} await timeout", seq);
				futures.remove(seq);
			}
		} finally {
			lock.unlock();
		}
			return response;
	}

	public static void received(LoginerConnOtherSuccReqBean response) {
		SyncResponseFuture future = futures.remove(response.getSeq());
		if (future != null) {
			future.doReceived(response);
		}
	}

	private boolean isDone() {
		return this.response != null;
	}

	private void doReceived(LoginerConnOtherSuccReqBean response) {
		lock.lock();
		try {
			this.response = response;
			done.signal();
		} finally {
			lock.unlock();
		}

	}

}
