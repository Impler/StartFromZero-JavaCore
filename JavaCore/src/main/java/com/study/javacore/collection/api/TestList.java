package com.study.javacore.collection.api;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.junit.Test;

public class TestList {

	/**
	 * 慎用sublist
	 * 结论：
	 * 如果在使用sublist方法时，
	 * 1 原list不再发生改变，则可以放心使用sublist对象，只不过，对sublist对象的操作，同样作用于原list
	 * 2 原list后面会改变，包括添加、删除、重新创建等，再引用sublist对象，将会抛出ConcurrentModificationException异常
	 * 解决办法：
	 * 尽量重新创建sublist的集合对象，如new ArrayList(list.subList(0, 1))，这样可以避免sublist与原list互相影响
	 */
	@Test
	public void testSubList(){
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		
		List<Integer> sub = list.subList(0, 1);
		System.out.println("list：" + list);
		System.out.println("sub list：" + sub);
		
		//sublist并没有从原list中分离，而是与原list元素指向同一个地址
		//所以这里向sublist天剑元素，原list中也相应的增加了
		sub.add(10);
		System.out.println("添加元素10");
		System.out.println("list：" + list);
		System.out.println("sub list：" + sub);
		
		//更改sublist，会影响到原list，但不会有异常
		sub.clear();
		System.out.println("sub.clear()");
		System.out.println("list：" + list);
		System.out.println("sub list：" + sub);
		
		//但是再改原list，再操作sublist就会引起ConcurrentModificationException异常
		list.clear();
		System.out.println("list.clear()");
		System.out.println("list：" + list);
		try{
			System.out.println(sub);
		}catch(ConcurrentModificationException e){
			System.out.println("引用sub时异常：" + e);
		}
	}
}
