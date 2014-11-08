package com.wufangnao.manger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/************检查Wifi是否开启*************/
public class CheckWifiConnection {
	private ConnectivityManager connManager;
	private NetworkInfo wifi;
	private NetworkInfo GPRS;
	public CheckWifiConnection(Context context)
	{
		connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		GPRS = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	}
	/**
	 * 检查WIFI是否可用
	 * @return true 有可用 false 没有
	 */
	public boolean checkWifi()
	{
		return wifi.isAvailable();
	}
	/**
	 * 检查GPRS是否可用
	 * @return true 可用，false 不可用
	 */
	public boolean checeGPRS()
	{
		return GPRS.isAvailable();
	}
	/**
	 * 检查是否有可用网络
	 * @return true 有可用，false 没有可用
	 */
	public static boolean isNetworkConnected(Context context) {  
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo network = connManager.getActiveNetworkInfo();  
	    if (network != null) {  
	    	return network.isAvailable();  
	    }  
	    return false;  
	}
}
