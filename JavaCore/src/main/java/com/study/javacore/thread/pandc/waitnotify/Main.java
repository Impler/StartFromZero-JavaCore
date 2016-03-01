package com.study.javacore.thread.pandc.waitnotify;

import java.util.LinkedList;


public class Main {
	public static void main(String[] args){
		LinkedList<String> storage = new LinkedList<String>();
		new Thread(new Consumer("C1", storage)).start();
		new Thread(new Productor("P1", storage)).start();
		new Thread(new Consumer("C2", storage)).start();
		new Thread(new Productor("P2", storage)).start();
		new Thread(new Consumer("C3", storage)).start();
	}
}
