package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.wufangnao.WuFangNaoApplication;
/**
 * 
 * @ClassName: TPropertyZoom 
 * @Description: TODO  获取屏幕中心点附近的楼盘信息
 * @author lrk
 * @date 2014-1-1 下午2:35:14 
 *
 */
public class TPropertyZoom extends TWebBase {

	public TPropertyZoom(Context context, Handler handler,
			double latitude_left, double longitude_left, double latitude_right,
			double longitude_right) {
		super(context, handler, "propertyzoom");
		jsonHelper.addParams("latitude1", latitude_left);
		jsonHelper.addParams("longitude1", longitude_left);
		jsonHelper.addParams("latitude2", latitude_right);
		jsonHelper.addParams("longitude2", longitude_right);
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
		
	}

}
