package com.study.javacore.thread.callable;

import java.util.Random;
import java.util.concurrent.Callable;
/**
 * 创建线程的两种方式：继承Thread和实现Runnable接口，但这两种方式都无法获取线程执行结果。
 * Callable的出现弥补了上述的缺陷，可以在任务执行完毕后得到执行结果。
 * @author Impler
 */
public class SimpleCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println("Thread:" + Thread.currentThread().getName() + " execute callable --> call()");
		Thread.sleep(3000);
		return new Random().nextInt(100);
	}

}
