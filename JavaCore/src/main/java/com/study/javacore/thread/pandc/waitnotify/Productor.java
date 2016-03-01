package com.study.javacore.thread.pandc.waitnotify;

import java.util.LinkedList;
import java.util.Random;

/**
 * 生产者
 * 
 * @author Impler
 * @date 2016-03-01
 */
public class Productor implements Runnable {

	private String name;
	private LinkedList<String> storage;
	private final int MAXSIZE = 10;

	public Productor(String name, LinkedList<String> storage) {
		this.name = name;
		this.storage = storage;
	}

	@Override
	public void run() {
		try {
			while (true) {
				synchronized (storage) {
					if (storage.size() < MAXSIZE) {
						String pName = "P" + (int) (Math.random() * 1000);
						storage.add(pName);
						System.out.println("生产者：" + name + "生产了一个产品：" + pName + ", 共：" + storage.size() + "个");
						Thread.sleep(new Random().nextInt(10)*100);
						storage.notifyAll();
					} else {
						System.out.println("仓库已满，生产者：" + name + "可以休息一下");
						storage.wait();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
