package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_CoordiateCollection;

/**
 * 
 * @ClassName: TShowCoordinate
 * @Description: 获取用户收藏坐标信息
 * @author lrk
 * @date 2013-12-17 上午10:12:49
 * 
 */
public class TShowCoordinate extends TWebBase {

	public TShowCoordinate(Context context, Handler handler) {
		super(context, handler, "showCoordinate");
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
	}

	public static W_CoordiateCollection getParsJson(String jsonResult) {
		Gson gson = new GsonBuilder().create();

		W_CoordiateCollection w_CoordiateCollection = gson.fromJson(jsonResult,
				new TypeToken<W_CoordiateCollection>() {
				}.getType());
		return  w_CoordiateCollection;
	}

}
