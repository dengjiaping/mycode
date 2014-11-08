package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.wufangnao.WuFangNaoApplication;
/**
 * 
 * @ClassName: TZoomLand 
 * @Description: TODO  获得经纬度范围内的地块信息
 * @author lrk
 * @date 2014-1-7 上午9:54:02 
 *
 */
public class TZoomLand extends TWebBase {

	public TZoomLand(Context context, Handler handler, double latitude_left, double longitude_left, double latitude_right,
			double longitude_right) {
		super(context, handler, "zoomLand");
		jsonHelper.addParams("latitude1", latitude_left);
		jsonHelper.addParams("longitude1", longitude_left);
		jsonHelper.addParams("latitude2", latitude_right);
		jsonHelper.addParams("longitude2", longitude_right);
		jsonHelper.addParams("userid", WuFangNaoApplication.getUserId());
	}

}
