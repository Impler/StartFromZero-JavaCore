package com.study.javacore.thread.CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。 用给定的计数 初始化 CountDownLatch。
 * 由于调用了countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。 之后，会释放所有等待的线程，await
 * 的所有后续调用都将立即返回。这种现象只出现一次——计数无法被重置。如果需要重置计数，请考虑使用 CyclicBarrier。
 * @author Impler
 * @date 2016-02-19
 */
public class CountDownLatchTest {

	public static void main(String[] args) {
		final int NUM = 10;

		ExecutorService threadPool = Executors.newCachedThreadPool();

		// 准备信号，等待所有运动员准备完毕，才能发出起跑命令
		CountDownLatch prepare = new CountDownLatch(NUM);
		// 起跑信号，发出起跑命令
		CountDownLatch begin = new CountDownLatch(1);
		// 结束信号，等待所有运动员跑步完毕，才算结束
		CountDownLatch end = new CountDownLatch(NUM);

		for (int i = 0; i < NUM; i++) {
			// 准备线程
			Runnable playersPrepare = new Runnable() {
				@Override
				public void run() {
					try {
						// 模拟准备时间
						Thread.sleep(new Random().nextInt(10) * 100);
						System.out.println(Thread.currentThread().getName() + "准备完毕");
						prepare.countDown();
						// ①等待所有人准备完毕
						int pCount = (int)prepare.getCount();
						prepare.await();
						// ②所有人准备完毕
						if(pCount == 0){
							// 发出开始口令
							begin.countDown();
							System.out.println("****开始。。。");
							// ④等待所有运动员跑步结束
							end.await();
							System.out.println("****结束。。。");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			threadPool.execute(playersPrepare);
			
			// 运动员线程
			Runnable players = new Runnable() {
				@Override
				public void run() {
					try {
						// ③直到begin计数器为0，即开始起跑时，才开始执行后面的程序
						begin.await();
						System.out.println("********" + Thread.currentThread().getName() + "起跑");
						// 模拟奔跑时间
						Thread.sleep(new Random().nextInt(10) * 1000);
						System.out.println("****************" + Thread.currentThread().getName() + "到达终点");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						// 计数器减一
						end.countDown();
					}
				}
			};
			threadPool.execute(players);
		}

		threadPool.shutdown();
	}
}
