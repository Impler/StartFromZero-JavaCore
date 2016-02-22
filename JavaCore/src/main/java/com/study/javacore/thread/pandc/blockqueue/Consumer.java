package com.study.javacore.thread.pandc.blockqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 * @author Impler
 * @date 2016-02-22
 */
public class Consumer implements Runnable {

	private String name;
	private BlockingQueue<String> queue;

	public Consumer(String name, BlockingQueue<String> queue) {
		super();
		this.name = name;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (queue.size() > 0) {
					System.out.println(name + "消费了一个产品: " + queue.take() + ", 剩" + queue.size());
				}else{
					System.out.println(name + ": 没有足够的产品，等待生产");
				}
				Thread.sleep(new Random().nextInt(10)*1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
