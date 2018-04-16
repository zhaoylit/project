package com.web.common.pagination;

import java.util.List;

public class Page {
	private int pageNum = 1;// 每页的起始数
	private int numPerPage = 10;// 每页显示数据的终止数
	private String orderField;
	private String orderDirection;
	private int totalCount; // 总条数
	private int totalPage; // 总页数
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartItem() {
		if (pageNum == 1) {
			return 0;
		} else {
			return (pageNum - 1) * numPerPage;
		}
	}

	public int getTotalPage() {

		if (totalCount == 0) {
			return 0;
		}
		if (totalCount < numPerPage)
			return 1;
		else {
			int num = totalCount / numPerPage;
			if (totalCount % numPerPage != 0) {
				num++;
			}
			return num;
		}
	}
}
