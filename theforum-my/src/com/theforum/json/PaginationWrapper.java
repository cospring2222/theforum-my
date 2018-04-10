package com.theforum.json;

/**
 * @author Uliana and David
 */
//pagination json model
public class PaginationWrapper {
	private int pageIndex;
	private int length;
	private int pageSize;

	public PaginationWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaginationWrapper(int pageIndex, int length, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.length = length;
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
