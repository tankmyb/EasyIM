package com.imbalancer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueThread implements Runnable {
	private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(
			40);

	public void offer(Runnable r) {
		queue.offer(r);
	}

	public void run() {
		while (true) {
			Runnable runnable;
			try {
				runnable = queue.take();
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
