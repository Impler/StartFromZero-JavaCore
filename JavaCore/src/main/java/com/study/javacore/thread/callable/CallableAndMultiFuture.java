package com.study.javacore.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author Impler
 * @date 2016-02-19
 */
public class CallableAndMultiFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		/*
		 * 将生产新的异步任务与使用已完成任务的结果分离开来的服务。生产者 submit 执行的任务。使用者 take 已完成的任务，并按照完成这些任务的顺序处理它们的结果。
		 */
		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
		
		Callable<Integer> callable = new SimpleCallable();

		cs.submit(callable);
		cs.submit(callable);
		cs.submit(callable);
		cs.submit(callable);
		
        System.out.println(cs.take().get());
        System.out.println(cs.take().get());
        System.out.println(cs.take().get());
        System.out.println(cs.take().get());
        
        System.out.println("Thread:" + Thread.currentThread().getName() + " end");
	}
}
