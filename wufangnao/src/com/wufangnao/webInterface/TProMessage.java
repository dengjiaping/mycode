package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_LocalProperty_Property;

/**
 * 
 * @ClassName: TProMessage
 * @Description: TODO 获得单个楼盘信息
 * @author lrk
 * @date 2013-12-20 下午1:56:17
 * 
 */
public class TProMessage extends TWebBase {

	public TProMessage(Context context, Handler handler, int propertyID) {
		super(context, handler, "proMessage");
		jsonHelper.addParams("propertyID", propertyID);
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());

	}

	public static W_LocalProperty_Property getParesJson(String jsonResult) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		W_LocalProperty_Property property = gson.fromJson(jsonResult,
				new TypeToken<W_LocalProperty_Property>() {
				}.getType());
		return property;
	}

}
