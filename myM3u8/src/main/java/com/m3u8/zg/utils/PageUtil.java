package com.m3u8.zg.utils;

import java.io.Serializable;

/**
 * 分页工具类
 * @Title PageUtil.java
 * @Description 
 * @Company: 软岛
 * @author zg
 * @date 2019年7月26日 下午3:49:37
 * @version V1.0
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected int pageOn;
	protected int rowCount;
	protected int pageCount;
	protected int row;
	protected int start;
	protected int end;
	protected int pageNumber = 10;

	public int getPageOn() {
		return pageOn;
	}

	public void setPageOn(int pageOn) {
		this.pageOn = pageOn;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public PageUtil(int pageOn, int row, int rowCount) {
		this.pageOn = pageOn;
		this.row = row;
		this.rowCount = rowCount;
		compute();
	}

	public PageUtil(int pageOn, int row) {
		this.pageOn = pageOn;
		this.row = row;
	}

	public PageUtil(int pageOn) {
		this.pageOn = pageOn;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void compute() {
		if (rowCount <= 0)
			return;
		if (row <= 0)
			row = 10;
		pageCount = rowCount % row != 0 ? rowCount / row + 1 : rowCount / row;
		if (pageOn > pageCount)
			pageOn = pageCount;
		if (pageOn < 1)
			pageOn = 1;
		start = (pageOn - 1) * row;
		end = pageOn * row;
		if (end > rowCount)
			end = rowCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setRowcountAndCompute(int rowCount) {
		this.rowCount = rowCount;
		compute();
	}

}
