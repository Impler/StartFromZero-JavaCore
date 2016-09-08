package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import cn.com.sncfc.framework.core.trace.SimpleLogger;

public class FilePartProducer<E> extends Thread {

	private static SimpleLogger logger = SimpleLogger.getLogger(FilePartProcess.class);
	
	private BlockingQueue<FilePart> queue;

	private PhysicFile file;

	private FilePartProcess<E> processor;
	
	// 线程创建顺序
	private int order;

	private List<E> data;

	
	public FilePartProducer(BlockingQueue<FilePart> queue,
			String fileName, String fileLocation,
			FilePartProcess<E> processor, int order, List<E> data) {
		super();
		this.queue = queue;
		file = new PhysicFile(fileName, fileLocation);
		this.processor = processor;
		this.order = order;
		this.data = data;
	}

	@Override
	public void run() {
		
		FilePart filePart = new FilePart(getPartFileName(), this.file.getFileLocation(), this.order);
		
		File file = new File(filePart.getFile().getFilePhysicLocation());
		
		BufferedWriter writer = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i = 0; i < data.size(); i++) {
				String content = processor.getSingleData(data.get(i));
				if (!processor.hasEndLine()) {
					content += FilePartProcess.endLineFlag;
				}
				writer.write(content);
			}
			writer.flush();
		} catch (Exception e) {
			logger.error("exception occurs while creating part file", e);
			filePart.setExp(e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// 忽略
				logger.info("ignore close stream exception", e);
			}
		}
		try {
			queue.put(filePart);
		} catch (InterruptedException e) {
			logger.error("exception occurs while putting product into queue", e);
		}
	}

	private String getPartFileName() {
		return this.file.getFileName() + ".part" + order;
	}
}
