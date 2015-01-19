package com.icebreak.util.lang.util;

import java.io.Serializable;

public class PageQuery implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 2114243624827533168L;
	
	/** 查询页数 */
	protected int pageNum = 0;
	
	/** 每页查询数量 */
	protected int pageSize = 0;
	
	/**
	 * 构建一个<code>PageQueryInfo.java</code>
	 */
	public PageQuery() {
		super();
	}
	
	/**
	 * 构建一个<code>PageQueryInfo.java</code>
	 * @param pageNum
	 * @param pageSize
	 */
	public PageQuery(int pageNum, int pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
