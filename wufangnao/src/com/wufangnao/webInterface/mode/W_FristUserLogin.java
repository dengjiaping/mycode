package com.wufangnao.webInterface.mode;
/**
 * 首次登录后返回的数据信息类
 * @author lrk
 *2013-12-9上午10:19:38
 */
public class W_FristUserLogin {
	
	private boolean usccess;
	
	private String message;
	
	private W_User datasource;
	
	private Integer page;
	
	private Integer totalpage;

	public boolean isUsccess() {
		return usccess;
	}

	public void setUsccess(boolean usccess) {
		this.usccess = usccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public W_User getDatasource() {
		return datasource;
	}

	public void setDatasource(W_User datasource) {
		this.datasource = datasource;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(Integer totalpage) {
		this.totalpage = totalpage;
	}
	
	

}
