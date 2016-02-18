package com.study.javacore.thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableAndFutureTask {

	public static void main(String[] args) throws InterruptedException, ExecutionException{
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " start");

		FutureTask<Integer> future = new FutureTask<Integer>(new SimpleCallable());
		
		new Thread(future).start();
		
		System.out.println(future.get());
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " end");
	}
}
