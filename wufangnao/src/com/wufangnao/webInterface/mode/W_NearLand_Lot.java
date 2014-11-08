package com.wufangnao.webInterface.mode;

import java.io.Serializable;

/**
 * 
 * @ClassName: W_NearLand_Lot
 * @Description: TODO 地块基本信息
 * @author lrk
 * @date 2014-1-3 上午11:25:43
 * 
 */
public class W_NearLand_Lot implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	private int landid;
	private String landprice;
	private String landmaketime;
	private String landarea;
	private String latitude;
	private String longitude;
	private String createtime;
    private String landNo;
    private String landUse;
    private String landLoation;
    private String landBuildarea;
    private String landGrantee;
    private String landFloor;
    
	public String getLandNo() {
		return landNo;
	}

	public void setLandNo(String landNo) {
		this.landNo = landNo;
	}

	public String getLandUse() {
		return landUse;
	}

	public void setLandUse(String landUse) {
		this.landUse = landUse;
	}

	public String getLandLoation() {
		return landLoation;
	}

	public void setLandLoation(String landLoation) {
		this.landLoation = landLoation;
	}

	public String getLandBuildarea() {
		return landBuildarea;
	}

	public void setLandBuildarea(String landBuildarea) {
		this.landBuildarea = landBuildarea;
	}

	public String getLandGrantee() {
		return landGrantee;
	}

	public void setLandGrantee(String landGrantee) {
		this.landGrantee = landGrantee;
	}

	public String getLandFloor() {
		return landFloor;
	}

	public void setLandFloor(String landFloor) {
		this.landFloor = landFloor;
	}

	public int getLandid() {
		return landid;
	}

	public void setLandid(int landid) {
		this.landid = landid;
	}

	public String getLandprice() {
		return landprice;
	}

	public void setLandprice(String landprice) {
		this.landprice = landprice;
	}

	public String getLandmaketime() {
		return landmaketime;
	}

	public void setLandmaketime(String landmaketime) {
		this.landmaketime = landmaketime;
	}

	public String getLandarea() {
		return landarea;
	}

	public void setLandarea(String landarea) {
		this.landarea = landarea;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
