package com.study.javacore.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableAndMultiFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService threadPool = Executors.newSingleThreadExecutor();

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
