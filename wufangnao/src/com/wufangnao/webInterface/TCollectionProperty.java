package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_MessageStatue;
/**
 * 用户收藏楼盘
 * @author lrk
 *2013-12-12下午01:54:18
 */
public class TCollectionProperty extends TWebBase {
	/**
	 * 
	 * @param context
	 * @param handler 解析json的handler类
	 * @param propertyID 收藏的楼盘ID
	 */
	public TCollectionProperty(Context context, Handler handler,int propertyID) {
		super(context, handler, "collectProperty");
		
		jsonHelper.addParams("propertyID", propertyID);
	
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
	}
	
	public static W_MessageStatue getParsJosn(String jsonResult)
	{
		Gson gson = new GsonBuilder().create();
		
		W_MessageStatue statue = gson.fromJson(jsonResult,
				new TypeToken<W_MessageStatue>(){}.getType());
		return statue;
	}
	

}
