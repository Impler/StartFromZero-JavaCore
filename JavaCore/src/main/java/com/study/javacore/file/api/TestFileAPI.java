package com.study.javacore.file.api;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

public class TestFileAPI {

	/**
	 * 验证 file.createNewFile()会不会创建不存在的目录
	 * 结论：不会，createNewFile() 只能在存在的目录下创建文件
	 */
	@Test
	public void testCreateNewFile(){
		File parent = new File("C:\\" + new Random().nextInt());
		System.out.println("确保目录不存在, 才能进行本测试: " + parent);
		if(parent.exists()){
			System.out.println("目录已存在，测试无法进行");
			return;
		}
		File file = new File(parent, "a.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.err.println(e);
			System.out.println("父目录不存在，createNewFile会抛异常");
			System.out.println("先创建父目录：" + parent + " : " + parent.mkdirs());
		}
		
		try {
			System.out.println("然后在创建文件：" + file + " : " + file.createNewFile());
		} catch (IOException e) {
			//这再报错就没救了
			System.err.println(e);
		}
	}
	

	/**
	 * 验证 s文件 renameTo t文件
	 * 结论：t文件所在目录必须存在，t文件不能存在
	 */
	@Test
	public void testRenameTo(){
		
		File parent = new File("C:\\" + new Random().nextInt());
		if(!parent.exists()){
			parent.mkdirs();
			System.out.println("创建目录：" + parent);
		}
		File sf = new File(parent, "a.txt");
		if(!sf.exists()){
			try {
				sf.createNewFile();
				System.out.println("创建文件：" + sf);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		
		// 1 同目录下操作
		// 1.1 不存在的文件
		File tf1 = new File(parent, "b.txt");
		Assert.assertFalse(tf1.exists());
		sf.renameTo(tf1);
		System.out.println(sf + "更名为：" + tf1 + ":" + tf1.exists());
		Assert.assertTrue(tf1.exists());
		
		//1.2 存在的文件
		File tf2 = new File(parent, "c.txt");
		try {
			tf2.createNewFile();
			System.out.println("创建文件：" + tf2);
		} catch (IOException e) {
			System.err.println(e);
		}
		Assert.assertTrue(tf2.exists());
		// 无法重命名为已存在的文件
		boolean result = tf1.renameTo(tf2);
		System.out.println(tf1 + "重命名为：" + tf2 + ":" + result);
		Assert.assertFalse(result);
		
		// 2 跨目录操作
		// 一个不存在的目录
		String path = parent.getAbsolutePath() + File.separator + "test";
		parent = new File(path);
		Assert.assertFalse(parent.exists());
		// 不存在的文件
		File tf3 = new File(parent, "d.txt");
		Assert.assertFalse(tf3.exists());
		// 操作不会成功
		tf1.renameTo(tf3);
		System.out.println(tf1 + "更名为：" + tf3 + ":" + tf3.exists());
		Assert.assertFalse(tf3.exists());
		
		parent.mkdirs();
		System.out.println("创建目录：" + parent);
		Assert.assertTrue(parent.exists());
		
		//操作成功
		tf1.renameTo(tf3);
		System.out.println(tf1 + "更名为：" + tf3 + ":" + tf3.exists());
		Assert.assertTrue(tf3.exists());
		
		// 3 跨盘符操作
		File file = new File("D:\\" + new Random().nextInt());
		Assert.assertFalse(file.exists());
		Assert.assertTrue(file.mkdir());
		System.out.println("创建目录：" + file);
		File tf4 = new File(file, "e.txt");
		Assert.assertFalse(tf4.exists());
		result = tf3.renameTo(tf4);
		System.out.println(tf3 + "更名为：" + tf4 + ":" + result);
		Assert.assertTrue(result);
	}

}
