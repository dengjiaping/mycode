package com.wufangnao.webInterface.mode;

import java.io.Serializable;
import java.util.List;
/**
 * 区域楼盘信息
 * @author lrk
 *2013-12-10下午03:30:09
 */
public class W_LocalProperty implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;

	private  boolean success;
	
	private  String message;
	
	private List<W_PropertyInfo> datasource;
	
	private long houseFlod;
	
	
	
	
	
	public long getHouseFlod() {
		return houseFlod;
	}

	public void setHouseFlod(long houseFlod) {
		this.houseFlod = houseFlod;
	}
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

	public List<W_PropertyInfo> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<W_PropertyInfo> datasource) {
		this.datasource = datasource;
	}
	
	

}
