package com.wufangnao.webInterface.mode;


public class W_CoordiateCollection_Coordiate {
	private String coordinateId;
	private String longitude;
	private String latitude;
	private String coordname;
	private String cityName;
	private W_User user;
	private boolean favo;
	 private String storetime;
	    
	
	public String getStoretime() {
		return storetime;
	}
	public void setStoretime(String storetime) {
		this.storetime = storetime;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityName() {
		return cityName;
	}
	public String getCoordinateId() {
		return coordinateId;
	}
	public void setCoordinateId(String coordinateId) {
		this.coordinateId = coordinateId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public W_User getUser() {
		return user;
	}
	public void setUser(W_User user) {
		this.user = user;
	}
	public boolean isFavo() {
		return favo;
	}
	public void setFavo(boolean favo) {
		this.favo = favo;
	}
	public String getCoordname() {
		return coordname;
	}
	public void setCoordname(String coordname) {
		this.coordname = coordname;
	}
	
	
}
