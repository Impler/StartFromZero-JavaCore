package com.study.javacore.thread.pandc.blockqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
/**
 * 生产者
 * @author Impler
 * @date 2016-02-22
 */
public class Productor implements Runnable {
	private final int MAXNUM = 10;
	private BlockingQueue<String> queue;
	private String name;

	public Productor(String name, BlockingQueue<String> queue) {
		super();
		this.queue = queue;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (queue.size() >= MAXNUM) {
					System.out.println(name + ": 仓库已满，无需生产");
				} else {
					String pName = "P" + (int)(Math.random() * 1000);
					queue.add(pName);
					System.out.println(name + "生产了一个产品：" + pName + ", 共" + queue.size());
				}
				Thread.sleep(new Random().nextInt(10)*1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
