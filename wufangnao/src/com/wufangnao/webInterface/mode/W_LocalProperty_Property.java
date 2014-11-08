package com.wufangnao.webInterface.mode;

import java.io.Serializable;
/**
 * 区域楼盘信息
 * @author lrk
 *2013-12-10下午03:30:09
 */
public class W_LocalProperty_Property implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;

	private  boolean success;
	
	private  String message;
	
	private W_PropertyInfo datasource;

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

	public W_PropertyInfo getDatasource() {
		return datasource;
	}

	public void setDatasource(W_PropertyInfo datasource) {
		this.datasource = datasource;
	}
	
	

}
