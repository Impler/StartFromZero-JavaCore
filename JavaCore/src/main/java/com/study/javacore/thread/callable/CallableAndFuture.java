package com.study.javacore.thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Impler
 * @date 2016-02-19
 */
public class CallableAndFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		/*
		 * Executor接口执行已提交的 Runnable 任务的对象。此接口提供一种将任务提交与每个任务将如何运行的机制（包括线程使用的细节、调度等）分离开来的方法。
		 * 根据所使用的具体 Executor 类的不同，可能在新创建的线程中，现有的任务执行线程中，或者调用 execute() 的线程中执行任务，并且可能顺序或并发执行。
		 * ExecutorService接口提供了多个完整的异步任务执行框架。ExecutorService 管理任务的排队和安排，并允许受控制的关闭。
		 * ScheduledExecutorService 子接口及相关的接口添加了对延迟的和定期任务执行的支持。
		 */
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		
		Future<Integer> future = threadPool.submit(new SimpleCallable());
		
		System.out.println(future.get());
		
		System.out.println("Thread:" + Thread.currentThread().getName() + " end");
		
		threadPool.shutdown();
		
	}
}