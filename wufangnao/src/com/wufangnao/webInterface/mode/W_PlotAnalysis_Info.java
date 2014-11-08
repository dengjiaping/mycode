package com.wufangnao.webInterface.mode;

public class W_PlotAnalysis_Info {
	/**
	 * 区近两年均价
	 */
	private Integer areaprice;
	/**
	 * 市的近两年均价
	 */
	private Integer	cityprice;
	/**
	 * 区近两年涨幅
	 */
	private double areaaoinum;
	/**
	 * 市的近两年涨幅
	 */
	private double cityaoinum;
	/**
	 * 区最高价
	 */
	private Integer areamaxprice;
	/**
	 * 区最低价
	 */
	private Integer areaminprice;
	public Integer getAreaprice() {
		return areaprice;
	}
	public void setAreaprice(Integer areaprice) {
		this.areaprice = areaprice;
	}
	public Integer getCityprice() {
		return cityprice;
	}
	public void setCityprice(Integer cityprice) {
		this.cityprice = cityprice;
	}
	public double getAreaaoinum() {
		return areaaoinum;
	}
	public void setAreaaoinum(double areaaoinum) {
		this.areaaoinum = areaaoinum;
	}
	public double getCityaoinum() {
		return cityaoinum;
	}
	public void setCityaoinum(double cityaoinum) {
		this.cityaoinum = cityaoinum;
	}
	public Integer getAreamaxprice() {
		return areamaxprice;
	}
	public void setAreamaxprice(Integer areamaxprice) {
		this.areamaxprice = areamaxprice;
	}
	public Integer getAreaminprice() {
		return areaminprice;
	}
	public void setAreaminprice(Integer areaminprice) {
		this.areaminprice = areaminprice;
	}
	

}
