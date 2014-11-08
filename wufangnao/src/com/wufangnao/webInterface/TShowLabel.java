package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.webInterface.mode.W_ShowLabel;

public class TShowLabel extends TWebBase {
	/**
	 * 
	 * @param context
	 * @param handler
	 * @param page   标签当前页面
	 */
	public TShowLabel(Context context, Handler handler, int page) {
		super(context, handler, "labelshow");
		jsonHelper.addParams("page", page);
		jsonHelper.addParams("pageSize",30);
	}

	public static W_ShowLabel getJsonParsing(String jsonResult) {
		Gson gson = new GsonBuilder().create();

		W_ShowLabel showLabel = gson.fromJson(jsonResult,
				new TypeToken<W_ShowLabel>() {
				}.getType());
		
		return showLabel;
	}

}
