package com.test;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class FilePart implements Comparable<FilePart> {

	// 物理文件
	private PhysicFile file;

	// 索引，用于合并文件时确定先后顺序
	private Pointer pointer;

	// 异常信息
	private Exception exp;

	public FilePart(String fileName, String fileLocation, int index) {
		this.file = new PhysicFile(fileName, fileLocation);
		this.pointer = new Pointer(index);
	}
	
	

	public FilePart(PhysicFile file, int index) {
		super();
		this.file = file;
		this.pointer = new Pointer(index);
	}



	public PhysicFile getFile() {
		return file;
	}

	public void setFile(PhysicFile file) {
		this.file = file;
	}

	public Pointer getPointer() {
		return pointer;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public boolean isSuccess() {
		return null == exp;
	}

	public Exception getExp() {
		return exp;
	}

	public void setExp(Exception exp) {
		this.exp = exp;
	}

	@Override
	public int compareTo(FilePart o) {
		// 比较此对象与指定对象的顺序。如果该对象小于、等于或大于指定对象，则分别返回负整数、零或正整数。
		if (this.getPointer().getLastIndex() < o.getPointer().getFirstIndex()) {
			return -1;
		} else if (this.getPointer().getFirstIndex() > o.getPointer()
				.getLastIndex()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	
	

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}





	/**
	 * filePart比较器
	 * 
	 * @author xiazhaoxu
	 * @date 2016年9月8日
	 */
	public static class FilePartComparator implements Comparator<FilePart> {
		@Override
		public int compare(FilePart o1, FilePart o2) {
			return o1.compareTo(o2);
		}
	}

	/**
	 * filePart指针
	 * 
	 * @author xiazhaoxu
	 * @date 2016年9月8日
	 */
	public class Pointer {

		private SortedSet<Integer> index;

		public Pointer(int index) {
			this.index = new TreeSet<Integer>();
			this.index.add(index);
		}

		public Pointer(SortedSet<Integer> index) {
			this.index = index;
		}

		public Set<Integer> getIndex() {
			return index;
		}

		public int getNextIndex() {
			return this.getLastIndex() + 1;
		}

		public int getPrevIndex() {
			return this.getFirstIndex() - 1;
		}

		public int getFirstIndex() {
			return this.index.first();
		}

		public int getLastIndex() {
			return this.index.last();
		}
		
		public void combine(Pointer pointer){
			this.index.addAll(pointer.getIndex());
			//TODO merge other info
		}

		@Override
		public String toString() {
			return "[" + index + "]";
		}
		
	}
	
	/**
	 * 合并part文件
	 * 
	 * @param index
	 */
	public void combine(Pointer other) {
		this.pointer.combine(other);
	}



	@Override
	public String toString() {
		return "FilePart [" + pointer + "]";
	}
	
}



