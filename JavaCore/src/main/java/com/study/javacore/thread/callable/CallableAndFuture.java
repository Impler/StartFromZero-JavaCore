package com.study.javacore.thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class CallableAndFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		Future<Integer> future = threadPool.submit(new SimpleCallable());
		
		System.out.println(future.get());
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " end");
		
	}
}