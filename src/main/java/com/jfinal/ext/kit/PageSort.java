package com.jfinal.ext.kit;

import org.apache.commons.lang.StringUtils;

public class PageSort {
	// -- 公共变量 --//
	protected String orderBy;
	protected String order;
	protected int pageNumber = 1;
	protected int pageSize = 10;

	// -- 构造函数 --//
	public PageSort() {
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		String sql = "";
		if(!StringUtils.isEmpty(this.getOrderBy()) && !StringUtils.isEmpty(this.getOrder())){
			StringBuilder sb = new StringBuilder();
			if(this.getOrderBy().indexOf(",")!=-1 && this.getOrder().indexOf(",")!=-1){
				//多个order by 值
				String[] ob = this.getOrderBy().split(",");
				String[] o = this.getOrder().split(",");
				if(ob.length==o.length){
					for(int i=0;i<ob.length;i++){
						sb.append(ob[i]).append(" ").append(o[i]).append(",");
					}
				}
				if(sb.toString().endsWith(",")){
					sb = new StringBuilder(sb.substring(0, sb.length()-1));
				}
			}
			if(this.getOrderBy().indexOf(",")==-1 && this.getOrder().indexOf(",")==-1){
				//单个order by 值
				sb.append(this.getOrderBy()).append(" ").append(this.getOrder());
			}
			if(!StringUtils.isEmpty(sb.toString())){
				sql = " ORDER BY "+sb;
			}
		}
		return sql;
	}

}
