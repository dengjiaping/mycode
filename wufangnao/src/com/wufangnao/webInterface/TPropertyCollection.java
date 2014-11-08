package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.webInterface.mode.W_PropertyCollection;
/**
 * 楼盘收藏接口
 * @ClassName: TPropertyCollection 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午11:05:38 
 *
 */
public class TPropertyCollection extends TWebBase {
	/**
	 * 
	 * @param context
	 * @param handler
	 */
	public TPropertyCollection(Context context, Handler handler
			) {
		super(context, handler, "showcollectPro");
		jsonHelper.addParams("userid",WuFangNaoApplication.getUserId());
		
	}
	
	public static W_PropertyCollection getParsJson(String jsonResult)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		W_PropertyCollection w_PropertyCollection = gson.fromJson(jsonResult,
				new TypeToken<W_PropertyCollection>(){}.getType());
		return w_PropertyCollection;
	}

}
