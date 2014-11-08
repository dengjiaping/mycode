package com.wufangnao.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKGeocoderAddressComponent;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.activity.Activity_Index;
import com.wufangnao.activity.view.View_LocationView;
import com.wufangnao.combination.CenterToast;
import com.wufangnao.manger.CheckWifiConnection;

/**
 * 应用首页内容，关于无房恼的介绍
 * @author lrk
 *
 */
public class Fragment_Index extends Fragment {

	private WuFangNaoApplication app = null;
	private Fragment_LocalProperty f_localProperty = null;

	private MKSearch mSearch = null;
	private CenterToast toast = null;
	/**
	 * 定位的客户端
	 */
	private LocationClient lc_LocationClient = null;
	/**
	 * 定位回调事件
	 */
	private MyLocationListener Locationlistener = null;
	
	private View_LocationView v_LocationView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 实例化可视组件
		 */
		v_LocationView = new View_LocationView(getActivity());
		/***
		 * 得到application类的操作对象
		 */
		app = (WuFangNaoApplication) getActivity().getApplication();
		/**
		 * 启动百度地图管理类
		 */
		app.initEngineManager(getActivity());
		/**
		 *实例化百度地图定位方法
		 */
		mSearch = new MKSearch();
		/**
		 * 百度地图定位回调方法
		 */
		Locationlistener = new MyLocationListener();
		/**
		 * 启动定位
		 */
		getLocationData();
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/**
		 * 三秒后跳转到主页面
		 */
		handler.sendEmptyMessageDelayed(0,3000);
		return v_LocationView.getView();
	}
	/**
	 * 跳转到主界面
	 */
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) {
			
			if(getActivity() instanceof Activity_Index)
			{
				Activity_Index index = (Activity_Index) getActivity();
				Bundle bundle = new Bundle();
				bundle.putBoolean("isGeoCode",true);
				index.locationProperty(bundle);
			}
		};
	};

	/**
	 * 定位经纬度查询
	 * 
	 * @author lrk 2013-12-10上午09:39:14
	 */
	private class SearchLocalListener implements MKSearchListener {
		/**
		 * 返回地址信息搜索结果
		 */
		@Override
		public void onGetAddrResult(MKAddrInfo res, int errorCode) {
			if (errorCode != 0) {
				
				return;
			}
			
			if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
				// 反地理编码：通过坐标点检索详细地址及周边poi
			
				MKGeocoderAddressComponent kk = res.addressComponents;
				CenterToast textView = new CenterToast(getActivity(),"您当前位置："+res.strAddr);
				textView.setGravity(Gravity.TOP, 0, 0);
				textView.show();

			}

		}

		/**
		 * 返回公交车详情信息搜索结果
		 */
		@Override
		public void onGetBusDetailResult(MKBusLineResult res, int errorCode) {

		}

		/**
		 * 返回驾乘路线搜索结果
		 */
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {

		}

		/**
		 * 返回poi相信信息搜索的结果
		 */
		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		/**
		 * 返回poi搜索结果
		 */
		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {

		}

		/**
		 * 返回分享短串结果.
		 */
		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {

		}

		/**
		 * 返回联想词信息搜索结果
		 */
		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}

		/**
		 * 返回公交搜索结果
		 */
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {

		}

		/**
		 * 返回步行路线搜索结果
		 */
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {

		}

	}
	/**
	 * 定位方法
	 */
	public void getLocationData() {
		if (!CheckWifiConnection.isNetworkConnected(getActivity())) {
			CenterToast textView = new CenterToast(getActivity(),"网络不可用，请检查网络");
			textView.show();
			app.open_GPS = false;
			return;
		}
		app.open_GPS = true;
		if (lc_LocationClient == null) {
			lc_LocationClient = new LocationClient(getActivity());
			lc_LocationClient.registerLocationListener(Locationlistener);
			// 设置定位条件
			LocationClientOption locationOption = new LocationClientOption();
			locationOption.setOpenGps(true);
			locationOption.setCoorType("bd09ll");
			locationOption.setPriority(LocationClientOption.NetWorkFirst);
			locationOption.setAddrType("all");
			locationOption.setProdName("BaiduLocation");
			// locationOption.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
			locationOption.disableCache(true);// 禁止启用缓存定位
			locationOption.setPoiNumber(5); // 最多返回POI个数
			locationOption.setPoiDistance(1000); // poi查询距离
			locationOption.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
			lc_LocationClient.setLocOption(locationOption);
			lc_LocationClient.start();
		}
		lc_LocationClient.requestLocation();

	}

	public void stopLocDate() {
		if (lc_LocationClient != null) {
			if (lc_LocationClient.isStarted()) {
				lc_LocationClient.stop();
			}
			lc_LocationClient = null;
		}
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			if (location.getLatitude() == 4E-324)
				return;

			mSearch.init(app.mBMapManager, new SearchLocalListener());
			GeoPoint ptCenter = new GeoPoint((int) (location.getLatitude() * 1e6),
					(int) (location.getLongitude() * 1e6));
			mSearch.reverseGeocode(ptCenter);
			// 如果不显示定位精度圈，将accuracy赋值为0即可

			System.out.println("location.getLatitude() = "
					+ location.getLatitude());
			System.out.println("location.getLongitude() = "
					+ location.getLongitude());
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			stopLocDate();
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
	

}
