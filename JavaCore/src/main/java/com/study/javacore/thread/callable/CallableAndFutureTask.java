package com.study.javacore.thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 实现了Runnable和Future接口，所以可以作为参数，传递到Thread中，并将执行的结果记录在Future中
 * @author Impler
 * @date 2016-02-19
 */
public class CallableAndFutureTask {

	public static void main(String[] args) throws InterruptedException, ExecutionException{
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " start");

		FutureTask<Integer> future = new FutureTask<Integer>(new SimpleCallable());
		
		new Thread(future).start();
		
		System.out.println("获取异步执行结果。。。。。。(" + System.currentTimeMillis() + ")");
		//get()方法获取异步执行的结果，如果异步操作还没有完成，当前线程将会阻塞
		System.out.println("结果：" + future.get() + " (" + System.currentTimeMillis() + ")");
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " end");
	}
}
