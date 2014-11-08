package com.wufangnao.webInterface.mode;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName: W_NearLand 
 * @Description: TODO  地块接口返回JSON信息
 * @author lrk
 * @date 2014-1-3 上午11:24:30 
 *
 */
public class W_NearLand implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;
	private List<W_NearLand_Lot> datasource;
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

	public List<W_NearLand_Lot> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<W_NearLand_Lot> datasource) {
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
