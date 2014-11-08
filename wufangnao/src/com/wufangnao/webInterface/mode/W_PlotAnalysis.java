package com.wufangnao.webInterface.mode;

public class W_PlotAnalysis {

	private String success;
	private String message;
	private W_PlotAnalysis_Info datasource;
	private Integer page;
	private Integer totalpage;
	private Integer houseFlod;
	private Integer cityFlod;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public W_PlotAnalysis_Info getDatasource() {
		return datasource;
	}
	public void setDatasource(W_PlotAnalysis_Info datasource) {
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
	public Integer getHouseFlod() {
		return houseFlod;
	}
	public void setHouseFlod(Integer houseFlod) {
		this.houseFlod = houseFlod;
	}
	public Integer getCityFlod() {
		return cityFlod;
	}
	public void setCityFlod(Integer cityFlod) {
		this.cityFlod = cityFlod;
	}
	
}
