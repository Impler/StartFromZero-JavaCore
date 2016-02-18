package com.study.javacore.thread.callable;

import java.util.Random;
import java.util.concurrent.Callable;

public class SimpleCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println("Thread:" + Thread.currentThread().getName() + " execute callable --> call()");
		Thread.sleep(3000);
		return new Random().nextInt(100);
	}

}
