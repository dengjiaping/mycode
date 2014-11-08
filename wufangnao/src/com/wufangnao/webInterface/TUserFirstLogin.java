package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.webInterface.mode.W_FristUserLogin;

/**
 * 1.用户首次开启并使用搜房时，向后台发送信息，为用户生成唯一用户ID 当用户进行注册时是使用ID。 2.解析后台返回的JSON数据
 * 
 * @author Administrator 2013-12-9上午09:37:11
 */
public class TUserFirstLogin extends TWebBase {

	public TUserFirstLogin(Context context, Handler handler) {
		super(context, handler, "useradd");
	}
/**
 * 解析JSON 数据
 * @param JsonResult 需要解析的JSON数据
 * @return
 */
	public static W_FristUserLogin getParsJson(String JsonResult) {
		Gson gson = new GsonBuilder().create();

		W_FristUserLogin user = gson.fromJson(JsonResult, new TypeToken<W_FristUserLogin>() {
		}.getType());
		
		return  user;

	}

}
