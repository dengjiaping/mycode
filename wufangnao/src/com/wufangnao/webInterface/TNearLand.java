package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_NearLand;
/**
 * 
 * @ClassName: TNearLand 
 * @Description: TODO 申请地块信息
 * @author lrk
 * @date 2014-1-3 上午11:29:12 
 *
 */
public class TNearLand extends TWebBase {

	public TNearLand(Context context, Handler handler, double latitude,double longitude) {
		super(context, handler, "nearLand");
		jsonHelper.addParams("latitude", latitude);
		jsonHelper.addParams("longitude", longitude);
		jsonHelper.addParams("userid",WuFangNaoApplication.getUserId());
		
	}
	/**
	 * 解析返回的地块JSON数据
	 * @param jsonResult
	 * @return
	 */
	public static W_NearLand getParsingJson(String jsonResult)
	{
		Gson gson = new GsonBuilder().create();
		
		W_NearLand w_NearLand= gson.fromJson(jsonResult,new TypeToken<W_NearLand>(){}.getType());
		return w_NearLand;
	}

}
