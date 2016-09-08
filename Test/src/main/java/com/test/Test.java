package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

	public static void main(String[] args) {
		
		List<User> users = new ArrayList<User>(100000);

		for(int i=1; i<=100000; i++){
			int id = i;
			String name = "name:【" + getRandomString() + "】";
			String location = "location:【" + getRandomString() + "】";
			String address = "address:【" + getRandomString() + "】";
			users.add(new User(id, name, location, address));
		}
		
		BlockingQueue<FilePart> queue = new LinkedBlockingQueue<FilePart>();
		SimpleFilePartProcess ps = new SimpleFilePartProcess();
		FilePartProducer<User> p1 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 1, users.subList(0, 10000));
		FilePartProducer<User> p2 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 2, users.subList(10000, 20000));
		FilePartProducer<User> p3 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 3, users.subList(20000, 30000));
		FilePartProducer<User> p4 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 4, users.subList(30000, 40000));
		FilePartProducer<User> p5 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 5, users.subList(40000, 50000));
		FilePartProducer<User> p6 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 6, users.subList(50000, 60000));
		FilePartProducer<User> p7 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 7, users.subList(60000, 70000));
		FilePartProducer<User> p8 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 8, users.subList(70000, 80000));
		FilePartProducer<User> p9 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 9, users.subList(80000, 90000));
		FilePartProducer<User> p10 = new FilePartProducer<User>(queue, "file.txt", "D:", ps, 10, users.subList(90000, 100000));
		System.out.println(System.currentTimeMillis());
		p2.start();
		p1.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		p7.start();
		p8.start();
		p9.start();
		p10.start();
		
		FilePartConsumer c1 = new FilePartConsumer(queue, 10, "D:", "file.txt");
		c1.start();
	}
	

	public static String getRandomString(){
		Random rd = new Random();
		int length = rd.nextInt(80) + 20;
		StringBuffer buff = new StringBuffer();
		for(int i=0; i<length; i++){
			buff.append((char)(65+rd.nextInt(26)));
		}
		return buff.toString();
	}
}
class User{
	private int id;
	private String name;
	private String location;
	private String address;
	
	public User(int id, String name, String location, String address) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.address = address;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", location=" + location
				+ ", address=" + address + "]";
	}
}