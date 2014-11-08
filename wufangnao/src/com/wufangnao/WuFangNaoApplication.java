package com.wufangnao;

import java.util.Stack;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.wufangnao.combination.CenterToast;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.manger.CheckWifiConnection;

/**
 * Application类，用于Activity之间的数据共享
 * 
 * @author lrk
 * 
 */
public class WuFangNaoApplication extends Application {

	/**
	 * 判断百度地图的密钥是否可用
	 */
	public static boolean m_bKeyRight = true;
	/**
	 * 地图引擎管理类
	 */
	public BMapManager mBMapManager = null;
	/**
	 * 百度地图密钥
	 */
	public static final String strKey = "ktbl8RtpWXxpBVyqm6oXIeSw";
	/**
	 * 用户ID
	 */
	private static Integer userId;
	/**
	 * Fragment栈
	 */
	private Stack<Fragment> fragment_stack = new Stack<Fragment>();
	/**
	 * 定位的客户端
	 */
	private LocationClient lc_LocationClient = null;
	
	public static boolean open_GPS = false;

	/**
	 * 位置信息类
	 */
	public static double latitude;
	public static double longitude;
	/**
	 * 定位信息回调监听
	 */
	private MyLocationListener Locationlistener = new MyLocationListener();

	private int position = -1;

	/**
	 * 实例化地图引擎管理类，并判断密钥是否可用
	 * 
	 * @param context
	 *            调用地图引擎的上下文对象
	 */
	public void initEngineManager(Context context) {
		/**
		 * 地图引擎管理类是否为空
		 */
		if (mBMapManager == null) {
			/**
			 * 创建引擎管类
			 */
			mBMapManager = new BMapManager(context);
		}
		/**
		 * 实例并启动地图管理引擎
		 */
		if (mBMapManager != null) {
			if (!mBMapManager.init(strKey, new MyGeneralListener())) {
				/**
				 * 地图引擎管理类实例失败
				 */
				Log.d("baiduMap_error", "初始化失败");
			}
		} else {
			Toast.makeText(this, "百度地图初始化失败", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 常用事件监听，用来处理通常的网络错误，授权验证错误等
	 * 
	 * @author lrk
	 * 
	 */
	public static class MyGeneralListener implements MKGeneralListener {

		/**
		 * 返回网络状态的错误信息
		 */
		@Override
		public void onGetNetworkState(int iError) {
			/**
			 * 判断网络连接是否出错
			 */
			if (iError == MKEvent.ERROR_NETWORK_CONNECT
					|| iError == MKEvent.ERROR_NETWORK_DATA) {
				Log.d("baiduMap_error", "您的网络出错啦！");

			} else {
				/**
				 * 错误代码非网络状态
				 */
				Log.d("baiduMap_error", "错误代码：" + iError + ",未知错误，请联系我们");

			}

		}

		@Override
		/**
		 * 返回密钥认证信息的错误
		 */
		public void onGetPermissionState(int iError) {
			/**
			 * 非零值表示key验证未通过
			 */
			if (iError != 0) {
				Log.d("baiduMap_error", "key验证未通过,请联系开发商！error: " + iError);

				m_bKeyRight = false;
			} else {
				m_bKeyRight = true;
			}
		}
	}

	public static Integer getUserId() {
		return userId;
	}

	public static void setUserId(Integer userId) {
		WuFangNaoApplication.userId = userId;
	}
	/**
	 * 获得Fragment的回退栈
	 * @return
	 */
	public Stack<Fragment> getFragment_stack() {
		return fragment_stack;
	}
	/**
	 * 首次创建回退栈记录到app
	 * @param fragment_stack
	 */
	public void setFragment_stack(Stack<Fragment> fragment_stack) {
		this.fragment_stack = fragment_stack;
	}

	/**
	 * 查找栈中的指定元素
	 * 
	 * @param fragment
	 *            需要查找的元素
	 * @return 找到则返回true;否则返回false
	 */
	public boolean findFragment(String fragment) {
		if (fragment != null && !fragment.equals("") && fragment_stack != null
				&& fragment_stack.size() != 0)
			for (int i = 0; i < fragment_stack.size(); i++) {
				/**
				 * 判断是否是要查找的指定元素，如果说是这返回true
				 */
				if (fragment_stack.get(i).getTag().toString().equals(fragment)) {
					position = i;
					return true;
				}

			}
		position = -1;
		return false;
	}

	/**
	 * 配合findFragment(Fragment fragment)使用，返回查找的fragment的位置
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * 将指定的布局弹到栈顶
	 * 
	 * @param fragment
	 *            指定元素
	 */
	public void popToTop(Fragment fragment) {
		while (true) {
			/**
			 * 判断栈顶元素是否是指定的布局
			 */
			if (fragment_stack.peek().equals(fragment)) {
				return;
			} else {
				/**
				 * 弹出
				 */
				fragment_stack.pop();
			}
		}
	}
	/**
	 * 关闭百度地图的管理类
	 */
	public void destoryManager() {
		if (mBMapManager != null) {
			mBMapManager.stop();
			mBMapManager.destroy();
			mBMapManager=null;
		}
	}
	/**
	 * 获得用户当前位置信息
	 */
	public void getLocationData() {
		/**
		 * 判断当前是否有可用网络连接
		 */
		if (!CheckWifiConnection.isNetworkConnected(getApplicationContext())) {
			CenterToast textView = new CenterToast(getApplicationContext(),"网络不可用，请检查网络连接");
			textView.show();//
			 open_GPS = false;
			return;
		}
		open_GPS = true;
		/**
		 * 开始进行定位
		 */
		if (lc_LocationClient == null) {
			lc_LocationClient = new LocationClient(getApplicationContext());
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
		/**
		 * 回调定位方法信息到MyLocationListener中
		 */
		lc_LocationClient.requestLocation();

	}
	/**
	 * 停止定位
	 */
	public void stopLocDate() {
		if (lc_LocationClient != null) {
			if (lc_LocationClient.isStarted()) {
				lc_LocationClient.stop();
			}
			lc_LocationClient = null;
		}
	}
	/**
	 * 
	 * @ClassName: MyLocationListener 
	 * @Description: TODO  定位信息回调方法
	 * @author lrk
	 * @date 2014-3-7 上午9:34:02 
	 *
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			if (location.getLatitude() == 4E-324)
				return;
			/**
			 * 获得定位到的经纬度信息
			 */
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			getApplicationContext().sendBroadcast(new Intent(S_ActionInfo.S_LOCATION_USER_POINT));
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
