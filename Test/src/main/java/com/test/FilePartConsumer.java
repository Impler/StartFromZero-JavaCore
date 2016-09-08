package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import cn.com.sncfc.framework.core.trace.SimpleLogger;

public class FilePartConsumer  extends Thread {

	private static SimpleLogger logger = SimpleLogger
			.getLogger(FilePartConsumer.class);

	// 队列中最大的FilePart个数，由记录条数一开始便确定下来
	private final Integer maxQueueNum;

	private BlockingQueue<FilePart> queue;

	private PhysicFile targetFile;

	protected SortedSet<FilePart> buffPart = null;

	public FilePartConsumer(BlockingQueue<FilePart> queue, Integer maxQueueNum,
			String targetFileLocation, String targetFileName) {
		super();
		this.queue = queue;
		this.maxQueueNum = maxQueueNum;
		this.targetFile = new PhysicFile(targetFileName, targetFileLocation);
		buffPart = new TreeSet<FilePart>(new FilePart.FilePartComparator());
	}

	@Override
	public void run() {
		// 记录处理的FilePart个数，
		int queueNum = 1;
		FilePart filePart = null;
		while (queueNum <= maxQueueNum) {
			try {
				System.out.println("开始");
				filePart = queue.take();
				if(null != filePart){
					doMerge(filePart);
					queueNum ++;
				}
				System.out.println("结束");
			} catch (InterruptedException e) {
				logger.error("exception occurs while taking filepart from queue", e);
			}
		}

		// TODO 通知处理完成
		System.out.println(System.currentTimeMillis() + "***********合并完成*********************");
		System.out.println(this.buffPart);
		settleTargetFile();
	}

	// 整理
	protected void settleTargetFile() {
		
	}

	/**
	 * 合并part文件
	 * 
	 * @param filePart
	 */
	protected void doMerge(FilePart filePart) {

		System.out.println("buff-->" + this.buffPart);
		// 第一个FilePart过来，直接放进缓存区
		if (this.buffPart.isEmpty()) {
			System.out.println("add to buff -->-->" + filePart);
			buffPart.add(filePart);
		}
		// 后面的过来，根据Pointer找相邻的filePart，如果有，合并；否则放进缓存区
		else {
			boolean hasMerged = false;
			Iterator<FilePart> it = buffPart.iterator();
			while (it.hasNext() && !hasMerged) {
				FilePart buff = it.next();
				if (buff.getPointer().getPrevIndex() == filePart.getPointer().getLastIndex()) {
					// 合并到当前元素前，不添加新的buff
					mergeFilePart(filePart, buff);
					hasMerged = true;
					buff = filePart;
					/*it.remove();
					this.buffPart.add(filePart);
					System.out.println("remove->" + buff + "-->" + this.buffPart);*/
					System.out.println("modify->" + this.buffPart);
				} else if (buff.getPointer().getNextIndex() == filePart.getPointer().getFirstIndex()) {
					// 合并到当前元素后，不添加新的buff, 额外获取下一个元素，如果是三相连，一起合并，减少文件IO操作
					if (it.hasNext()) {
						FilePart next = it.next();
						if (filePart.getPointer().getNextIndex() == next.getPointer().getFirstIndex()) {
							// 三相连，一起合并
							mergeFilePart(buff, filePart, next);
							next = buff;
							System.out.println("modify->->" + this.buffPart);
							/*it.remove();
							this.buffPart.add(buff);
							System.out.println("remove->" + next);*/
						} else {
							// 非三相连，只合并前两个
							mergeFilePart(buff, filePart);
						}
					} else {
						// 合并前两个
						mergeFilePart(buff, filePart);
					}
					hasMerged = true;
				}
			}
			// 没有相连的part文件，只能放入缓存（顺序是根据Pointer的自然排序）
			if (!hasMerged) {
				System.out.println("add to buff-->" + filePart);
				this.buffPart.add(filePart);
			}
		}
	}

	/**
	 * 合并多个文件
	 * @param former 目标合并文件
	 * @param latters 待合并文件
	 */
	protected void mergeFilePart(FilePart former, FilePart... latters) {
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			writer = new BufferedWriter(new FileWriter(former.getFile().getFilePhysicLocation(), true));
			for (int i = 0; i < latters.length; i++) {
				FilePart part = latters[i];
				System.out.println("merge : " + part + " to " + former);
				reader = new BufferedReader(new FileReader(part.getFile().getFilePhysicLocation()));
				String line = null;
				while (null != (line = reader.readLine())) {
					writer.write(line + FilePartProcess.endLineFlag);
				}
				writer.flush();
				former.combine(part.getPointer());
			}
			System.out.println("after merge -->" + this.buffPart);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		TreeSet<String> set = new TreeSet<String>();
		set.add("2");
		set.add("4");
		set.add("6");
		set.add("7");
		set.add("10");
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String i = it.next();
			if("7".equals(i)){
				it.remove();
				set.add("s");
				System.out.println(set);
			}
		}
		System.out.println(set);
	}
}
