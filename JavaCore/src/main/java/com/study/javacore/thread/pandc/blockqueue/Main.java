package com.study.javacore.thread.pandc.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 使用JDK1.5提供的BlockingQueue接口实现生产者-消费者模式
 * BlockingQueue是线程安全的，开发者不需要写复杂的wait-notify
 * @author Impler
 * @date 2016-02-22
 */
public class Main {

	public static void main(String[] args){
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		
		new Thread(new Consumer("C1", queue)).start();
		new Thread(new Productor("P1", queue)).start();
		new Thread(new Consumer("C2", queue)).start();
		new Thread(new Productor("P2", queue)).start();
		new Thread(new Consumer("C3", queue)).start();
	}
}
