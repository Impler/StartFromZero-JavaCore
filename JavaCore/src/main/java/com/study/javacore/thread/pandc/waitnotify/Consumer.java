package com.study.javacore.thread.pandc.waitnotify;

import java.util.LinkedList;
import java.util.Random;

/**
 * 消费者
 * 
 * @author Impler
 * @date 2016-03-01
 */
public class Consumer implements Runnable {

	private String name;
	private LinkedList<String> storage;

	public Consumer(String name, LinkedList<String> storage) {
		this.name = name;
		this.storage = storage;
	}

	@Override
	public void run() {
		try {
			while (true) {
				synchronized (storage) {
					if (storage.size() > 0) {
						System.out.println("消费者：" + name + "消费了一个产品：" + storage.pop() + "，剩余：" + storage.size() + "个");
						Thread.sleep(new Random().nextInt(10)*100);
						storage.notifyAll();
					} else {
						System.out.println("没有足够的产品，消费者：" + name + "正在等待生产");
						storage.wait();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
