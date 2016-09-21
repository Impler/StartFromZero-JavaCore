package com.study.javacore.thread.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier
 * 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * 
 * @author Impler
 * @date 2016-02-19
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {

		final int NUM = 3;

		ExecutorService threadPool = Executors.newCachedThreadPool();
		final CyclicBarrier barrier = new CyclicBarrier(NUM);

		for (int i = 0; i < NUM; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(new Random().nextInt(10) * 100);
						System.out.println("已有：" + barrier.getNumberWaiting() + "人到达集合点");
						System.out.println(Thread.currentThread().getName() + "正在前往集合点");
						Thread.sleep(new Random().nextInt(10) * 1000);
						System.out.println(Thread.currentThread().getName() + "已经达到集合点，正在等待其他人。。。");
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					// await后面的程序将会阻塞，直到最后一个线程执行到await()
					System.out.println("人员集合完毕，" + Thread.currentThread().getName() + "出发~~~");
				}
			};
			threadPool.execute(runnable);
		}
	}

}
