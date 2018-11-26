package com.jadmin.vo.fundation.tool;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/** 
 * @Title:web框架
 * @Description:与具体ORM实现无关的分页参数及查询结果封装.
 * 注意所有序号从1开始.
 * @param <T> Page中记录的类型.
 * @Copyright:JAdmin (c) 2018年08月21日
 * 
 * @author:-jiujiya
 * @version:1.0 
 */
public class Page<T> implements Serializable{

    private static final long serialVersionUID = 134234256576756313L;

	/** 当前页 */
    public int pageNo = 1;
	/** 每页显示的条数 */
	public int pageSize = 10;
	
	/** 是否自动求总条数 */
	public boolean autoCount = true;
	
	/** args参数 */
	public String args;
	
	/** 分页时，显示其他的页数的第一个值 */
	public int pageFirst;
	
	/** 分页时，显示其他的页数的最后一个值 */
	public int pageEnd;

	/** 返回结果 */
	public List<T> result = Collections.emptyList();
	
	/** 总条数 */
	public long totalCount = -1;

	public int totalPages = -1;
	
	
	// 构造函数 //
	public Page() {
	}

	public Page(final int pageSize) {
		setPageSize(pageSize);
	}

	public Page(final int pageSize, final boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	public int getPageEnd() {
		if(pageNo+2>getTotalPages()){
			return getTotalPages();
		}
		return pageNo+2;
	}

	public int getPageFirst() {
		if(pageNo-2<1){
			return 1;
		}
		return pageNo-2;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	// 访问查询结果函数 //

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public int getTotalPages() {
		if (totalCount < 0)
			return totalPages;

	    totalPages = (int) (totalCount / pageSize);
		if (totalCount % pageSize > 0) {
			totalPages++;
		}
		return totalPages;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
}
