package com.wufangnao.webInterface;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wufangnao.webInterface.mode.W_LocalProperty_Property;
import com.wufangnao.webInterface.mode.W_PlotAnalysis;
/**
 * 地块分析接口
 * @ClassName: TPlotAnalysis 
 * @Description: TODO  获取本区楼市的分析数据
 * @author lrk
 * @date 2014-3-10 下午4:20:28 
 *
 */
public class TPlotAnalysis extends TWebBase {
	/**
	 * 
	 * @param context 上下文对象
	 * @param handler 回调handler
	 * @param latitude 维度坐标
	 * @param longitude 经度坐标
	 * @param cityName  城市名称
	 * @param areaName  所在城市的辖区
	 */
	public TPlotAnalysis(Context context, Handler handler,double latitude,double longitude,String cityName,String areaName) {
		super(context, handler, "landCount");
		jsonHelper.addParams("latitude", latitude);
		jsonHelper.addParams("longitude", longitude);
		jsonHelper.addParams("cityName", cityName);
		jsonHelper.addParams("areaName", areaName);
	}
	
	public static W_PlotAnalysis getParesJson(String jsonResult)
	{

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		W_PlotAnalysis plotAnalysis = gson.fromJson(jsonResult,
				new TypeToken<W_PlotAnalysis>() {
				}.getType());
		return plotAnalysis;
	}

}
