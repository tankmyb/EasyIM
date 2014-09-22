package org.imclient.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imapi.bean.sync.SyncRespBean;
import com.imbase.exception.SyncRespTimeoutException;

public class SyncResponseFuture {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final ConcurrentHashMap<Integer, SyncResponseFuture> futures = new ConcurrentHashMap<Integer, SyncResponseFuture>();

	private final Lock lock = new ReentrantLock();

	private final Condition done = lock.newCondition();

	private SyncRespBean response;

	public SyncResponseFuture(int seq) {
		futures.put(seq, this);
	}

	public Object get(int seq) throws SyncRespTimeoutException {
		lock.lock();
		try {
			// 可以增加超时机制
			while (!isDone()) {
				done.await(4000, TimeUnit.MILLISECONDS);
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
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
		if (response != null) {
			return response.getResp();
		}
		throw new SyncRespTimeoutException("同步响应超时");
	}

	public static void received(SyncRespBean response) {
		SyncResponseFuture future = futures.remove(response.getSeqId());
		if (future != null) {
			future.doReceived(response);
		}
	}

	private boolean isDone() {
		return this.response != null;
	}

	private void doReceived(SyncRespBean response) {
		lock.lock();
		try {
			this.response = response;
			done.signal();
		} finally {
			lock.unlock();
		}

	}

}
