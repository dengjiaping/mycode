package com.wufangnao.webInterface.mode;

import java.util.List;

public class W_CoordiateCollection {
	private boolean success;
	private String message;
	private List<W_CoordiateCollection_Coordiate> datasource;
	private int page;
	private int totalpage;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<W_CoordiateCollection_Coordiate> getDatasource() {
		return datasource;
	}
	public void setDatasource(List<W_CoordiateCollection_Coordiate> datasource) {
		this.datasource = datasource;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	

}
