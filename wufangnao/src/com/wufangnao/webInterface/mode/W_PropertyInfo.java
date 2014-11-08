package com.wufangnao.webInterface.mode;

import java.io.Serializable;
import java.util.Date;
/**
 * 楼盘信息封装类
 * @author lrl
 *2013-12-10下午02:36:03
 */
public class W_PropertyInfo implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private int propertyID;
    private String longitude;
    private String latitude;
    private String  city;
    private String projjectName; //楼盘名称
    private String projectAdress; //地址
    private String newType;
    private String housePrice;
    private String houseType;
    private String decorationStatus;
    private String parkingSpace;
    private Date openTime;
    private Date checkinTime;
    private String numberOfHourse;
    private String areaCovered;
    private String greeningRate;
    private String volumeRate;   
    private String totalAreacovered;
    private  String areaYear;
    private  String poolRate;
    private  String propertyCost;
    private  String presellInf;
    private  String presalePermit;
    private  String traffic;
    private  String propertyCompany;
    private  String developers;
    private  String addressSales;
    private  String telephoneSales;
    private String hotlabeName;
    private String showcheckinTime;
    private String showopenTime;
    private  boolean favo;
    private String buyType;
    private String buildType;
    private int openTimeDay;
    private float areaCoverednum;
    private String citytown;
    
    
	public String getCitytown() {
		return citytown;
	}
	public void setCitytown(String citytown) {
		this.citytown = citytown;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}
	public void setOpenTimeDay(int openTimeDay) {
		this.openTimeDay = openTimeDay;
	}
	public void setAreaCoverednum(float areaCoverednum) {
		this.areaCoverednum = areaCoverednum;
	}
	public int getOpenTimeDay() {
		return openTimeDay;
	}
	public float getAreaCoverednum() {
		return areaCoverednum;
	}
	public String getBuyType() {
		return buyType;
	}
	public String getBuildType() {
		return buildType;
	}
	public String getShowcheckinTime() {
		return showcheckinTime;
	}
	public void setShowcheckinTime(String showcheckinTime) {
		this.showcheckinTime = showcheckinTime;
	}
	public String getShowopenTime() {
		return showopenTime;
	}
	public void setShowopenTime(String showopenTime) {
		this.showopenTime = showopenTime;
	}
	public int getPropertyID() {
		return propertyID;
	}
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProjjectName() {
		return projjectName;
	}
	public void setProjjectName(String projjectName) {
		this.projjectName = projjectName;
	}
	public String getProjectAdress() {
		return projectAdress;
	}
	public void setProjectAdress(String projectAdress) {
		this.projectAdress = projectAdress;
	}
	public String getNewType() {
		return newType;
	}
	public void setNewType(String newType) {
		this.newType = newType;
	}
	public String getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getDecorationStatus() {
		return decorationStatus;
	}
	public void setDecorationStatus(String decorationStatus) {
		this.decorationStatus = decorationStatus;
	}
	public String getParkingSpace() {
		return parkingSpace;
	}
	public void setParkingSpace(String parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getNumberOfHourse() {
		return numberOfHourse;
	}
	public void setNumberOfHourse(String numberOfHourse) {
		this.numberOfHourse = numberOfHourse;
	}
	public String getAreaCovered() {
		return areaCovered;
	}
	public void setAreaCovered(String areaCovered) {
		this.areaCovered = areaCovered;
	}
	public String getGreeningRate() {
		return greeningRate;
	}
	public void setGreeningRate(String greeningRate) {
		this.greeningRate = greeningRate;
	}
	public String getVolumeRate() {
		return volumeRate;
	}
	public void setVolumeRate(String volumeRate) {
		this.volumeRate = volumeRate;
	}
	public String getTotalAreacovered() {
		return totalAreacovered;
	}
	public void setTotalAreacovered(String totalAreacovered) {
		this.totalAreacovered = totalAreacovered;
	}
	public String getAreaYear() {
		return areaYear;
	}
	public void setAreaYear(String areaYear) {
		this.areaYear = areaYear;
	}
	public String getPoolRate() {
		return poolRate;
	}
	public void setPoolRate(String poolRate) {
		this.poolRate = poolRate;
	}
	public String getPropertyCost() {
		return propertyCost;
	}
	public void setPropertyCost(String propertyCost) {
		this.propertyCost = propertyCost;
	}
	public String getPresellInf() {
		return presellInf;
	}
	public void setPresellInf(String presellInf) {
		this.presellInf = presellInf;
	}
	public String getPresalePermit() {
		return presalePermit;
	}
	public void setPresalePermit(String presalePermit) {
		this.presalePermit = presalePermit;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getPropertyCompany() {
		return propertyCompany;
	}
	public void setPropertyCompany(String propertyCompany) {
		this.propertyCompany = propertyCompany;
	}
	public String getDevelopers() {
		return developers;
	}
	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	public String getAddressSales() {
		return addressSales;
	}
	public void setAddressSales(String addressSales) {
		this.addressSales = addressSales;
	}
	public String getTelephoneSales() {
		return telephoneSales;
	}
	public void setTelephoneSales(String telephoneSales) {
		this.telephoneSales = telephoneSales;
	}
	public String getHotlabeName() {
		return hotlabeName;
	}
	public void setHotlabeName(String hotlabeName) {
		this.hotlabeName = hotlabeName;
	}
	public boolean isFavo() {
		return favo;
	}
	public void setFavo(boolean favo) {
		this.favo = favo;
	}
	public void setData(W_PropertyInfo propertyInfo)
	{
		this. propertyID=propertyInfo.getPropertyID();
		this. longitude=propertyInfo.getLongitude();
		this. latitude=propertyInfo.getLatitude();
		this.  city=propertyInfo.getCity();
		this.projjectName=propertyInfo.getProjjectName();
		this. projectAdress=propertyInfo.getProjectAdress();
		this. newType=propertyInfo.getNewType();
		this. housePrice=propertyInfo.getHousePrice();
		this. houseType=propertyInfo.getHouseType();
		this. decorationStatus=propertyInfo.getDecorationStatus();
		this. parkingSpace=propertyInfo.getParkingSpace();
		this. openTime=propertyInfo.getOpenTime();
		this. checkinTime=propertyInfo.getCheckinTime();
		this. numberOfHourse=propertyInfo.getNumberOfHourse();
		this. areaCovered=propertyInfo.getAreaCovered();
		this. greeningRate=propertyInfo.getGreeningRate();
		this. volumeRate=propertyInfo.getVolumeRate();   
		this. totalAreacovered=propertyInfo.getTotalAreacovered();
		this.   areaYear=propertyInfo.getAreaYear();
		this.   poolRate=propertyInfo.getPoolRate();
		this.   propertyCost=propertyInfo.getPropertyCost();
		this.  presellInf=propertyInfo.getPresellInf();
		this.  presalePermit=getPresalePermit();
		this.  traffic=propertyInfo.getTraffic();
		this. propertyCompany=propertyInfo.getPropertyCompany();
		this. developers=propertyInfo.getDevelopers();
		this. addressSales=propertyInfo.getAddressSales();
		this. telephoneSales=propertyInfo.getTelephoneSales();
		this.hotlabeName=propertyInfo.getHotlabeName();
		this.showcheckinTime=propertyInfo.getShowcheckinTime();
		this. showopenTime=propertyInfo.getShowopenTime();
		this. favo=propertyInfo.isFavo();
	}
    
    
}
