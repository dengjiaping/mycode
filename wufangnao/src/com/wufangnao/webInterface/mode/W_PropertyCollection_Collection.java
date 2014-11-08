package com.wufangnao.webInterface.mode;

import java.io.Serializable;
import java.util.Date;

/**
 * 楼盘收藏信息类
 * @author lrk
 *2013-12-11下午03:21:31
 */
public class W_PropertyCollection_Collection implements Serializable{
	
	/** 
	* @Fields serialVersionUID : 唯一标示
	*/ 
	private static final long serialVersionUID = 1L;
	private int collectid;
    private W_PropertyInfo propertyID;
    private W_User userid;
    private boolean favo;
    private String storetime;
    
	

	public String getStoretime() {
		return storetime;
	}
	public void setStoretime(String storetime) {
		this.storetime = storetime;
	}
	public int getCollectid() {
		return collectid;
	}
	public void setCollectid(int collectid) {
		this.collectid = collectid;
	}
	public W_PropertyInfo getPropertyID() {
		return propertyID;
	}
	public void setPropertyID(W_PropertyInfo propertyID) {
		this.propertyID = propertyID;
	}
	public W_User getUserid() {
		return userid;
	}
	public void setUserid(W_User userid) {
		this.userid = userid;
	}
	public boolean isFavo() {
		return favo;
	}
	public void setFavo(boolean favo) {
		this.favo = favo;
	}
    
    

}
