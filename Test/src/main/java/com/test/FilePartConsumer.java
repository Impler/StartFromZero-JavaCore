package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

//import cn.com.sncfc.framework.core.trace.SimpleLogger;

public class FilePartConsumer  extends Thread {

//	private static SimpleLogger logger = SimpleLogger.getLogger(FilePartConsumer.class);

	// 队列中最大的FilePart个数，由记录条数一开始便确定下来
	private final Integer maxQueueNum;

	// 待合并文件队列
	private BlockingQueue<FilePart> queue;

	private PhysicFile targetFile;

	/*
	 * 待合并文件等待区
	 * 存放规则，往小的方向合并：
	 * 1 第一个待处理元素过来直接存入等待区
	 * 2 后面的待处理元素通过遍历与等待区中元素的Pointer属性比较，最终决定操作
	 * 	2.1 如果待处理元素的Pointer是等待区元素的前一个，将待处理元素前置到等待区元素前，移除当前等待区元素，插入合并后的处理元素，结束遍历。2,3 + 1 ==> 1,2,3
	 *  2.2 如果待处理元素的Pointer是等待区元素的后一个
	 *  	2.2.1 如果当前等待区元素的下一个正好是待处理元素Pointer属性的后一个，将此三者合并，等待区由两个元素变成一个，所以移除后一个，结束遍历。1,3 + 2 ==> 1,2,3
	 *  	2.2.2 如果当前等待区元素的下一个不是待处理元素Pointer属性的后一个，则将待处理元素合并到等待区元素后面，等待区结构和个数不变，结束遍历。1,3 + 2 ==>1,2,4
	 *  3 没有相邻元素，则添加到等待区
	 */
	private SortedSet<FilePart> buffPart = null;

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
//				logger.error("exception occurs while taking filepart from queue", e);
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

		System.out.println("doMerge start: buff-->" + this.buffPart);
		// 第一个FilePart过来，直接放进缓存区
		if (this.buffPart.isEmpty()) {
			buffPart.add(filePart);
			System.out.println("add first element " + filePart + " to buff-->" + buffPart);
		}
		// 后面的过来，根据Pointer找相邻的filePart，如果有，合并；否则放进缓存区
		else {
			// 中断迭代标识
			boolean hasMerged = false;
			Iterator<FilePart> it = buffPart.iterator();
			while (it.hasNext() && !hasMerged) {
				FilePart buff = it.next();
				if (buff.getPointer().getPrevIndex() == filePart.getPointer().getLastIndex()) {
					// 合并到当前元素前，不添加到buff
					mergeFilePart(filePart, buff);
					// 中断迭代标识
					hasMerged = true;
					// 合并文件后，filePart对象也会合并，这里保留合并后的元素，移除合并过的当前元素 buff
					it.remove();
					// 虽然在迭代时添加了新的元素，但后面迭代会被终止，所以并不会造成ConcurrentModificationException异常
					this.buffPart.add(filePart);
					System.out.println("remove->" + buff + ", current buff-->" + this.buffPart);
				} else if (buff.getPointer().getNextIndex() == filePart.getPointer().getFirstIndex()) {
					// 合并到当前元素后，不添加到buff, 额外获取下一个元素，如果是三相连，一起合并，减少文件IO操作
					if (it.hasNext()) {
						FilePart next = it.next();
						if (filePart.getPointer().getNextIndex() == next.getPointer().getFirstIndex()) {
							// 三相连，在当前元素和下一个元素之间插入
							mergeFilePart(buff, filePart, next);
							// 原理同上，移除合并过的当前元素next
							it.remove();
							this.buffPart.add(buff);
						} else {
							// 非三相连，只在当前元素后追加
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
		System.out.println("doMerge end: buff-->" + this.buffPart);
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
			System.out.println("after merge -->" + former);
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
}
