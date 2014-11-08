package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.wufangnao.WuFangNaoApplication;

/**
 * 楼盘标签点击事件
 * @ClassName: TLabel 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-10 上午11:00:28 
 *
 */
public class TLabel extends TWebBase {
	/**
	 * 
	 * @param context
	 * @param handler
	 * @param propertyID //楼盘ID
	 * @param labelId //标签ID
	 */
	public TLabel(Context context, Handler handler,int propertyID,int labelId) {
		super(context, handler, "clickAdd");
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
		
		jsonHelper.addParams("propertyID", propertyID);
		
		jsonHelper.addParams("labelId", labelId);
		
	}

}
