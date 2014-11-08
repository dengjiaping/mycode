package com.wufangnao.webInterface;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Handler;

import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.constant.S_WebBaseInfo;

/**
 * 坐标收藏接口
 * 
 * @author lrk 2013-12-16下午02:11:54
 */
public class TCollectCoordinate extends TWebBase {
	/**
	 * 
	 * @param context
	 *            引用上下文对象
	 * @param handler
	 *            解析json的handler方法
	 * @param longitude
	 *            纬度坐标
	 * @param latitude
	 *            经度坐标
	 */
	public TCollectCoordinate(Context context, Handler handler,
			double latitude, double  longitude, String coordname,String cityName) {
		super(context, handler, "collectCoordinate");
		jsonHelper.addParams("latitude", latitude);
		jsonHelper.addParams("cityName", cityName);
		jsonHelper.addParams("longitude", longitude);
		String nameString = null;
		if (coordname != null) {
			try {
				nameString = new String(coordname.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		jsonHelper.addParams("coordname", nameString);
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < jsonHelper.getParams().size(); i++) {
			buffer.append(jsonHelper.getParams().get(i).getName());
			buffer.append("=");
			buffer.append(jsonHelper.getParams().get(i).getValue());
			buffer.append("&");
			
		}
	
	}

}
