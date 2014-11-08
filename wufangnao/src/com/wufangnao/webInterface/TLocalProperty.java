package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_LocalProperty;

/**
 * 区域楼盘信息申请类. 通过经纬度向服务器申请该区域的楼盘信息
 * 
 * @author lrk 2013-12-9下午04:49:17
 */
public class TLocalProperty extends TWebBase {
	/**
	 * 申请楼盘信息
	 * 
	 * @param context
	 * @param handler
	 * @param latitude
	 *            经度
	 * @param longitude
	 *            纬度
	 */
	public TLocalProperty(Context context, Handler handler, double latitude,
			double longitude,String cityName) {
		super(context, handler, "propertyaction");
		jsonHelper.addParams("latitude", latitude);
		
		jsonHelper.addParams("longitude", longitude);
		
		jsonHelper.addParams("cityName", cityName);
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
		
	
		
	}
	public TLocalProperty(Context context, Handler handler, double latitude,
			double longitude) {
		super(context, handler, "propertyaction");
		jsonHelper.addParams("latitude", latitude);
	
		jsonHelper.addParams("longitude", longitude);
	
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
	
	
		
	}
	

	public static W_LocalProperty getPaserJsonResult(String jsonResult) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.create();

		W_LocalProperty w_LocalProperty = gson.fromJson(jsonResult,
				new TypeToken<W_LocalProperty>() {
				}.getType());
		return w_LocalProperty;
	}

}
