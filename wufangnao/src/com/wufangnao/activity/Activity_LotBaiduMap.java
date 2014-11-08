package com.wufangnao.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MKMapStatusChangeListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.wufangnao.R;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.LogCat.LogCat;
import com.wufangnao.combination.MyLocationMapView;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.webInterface.TNearLand;
import com.wufangnao.webInterface.TZoomLand;
import com.wufangnao.webInterface.mode.W_NearLand;
import com.wufangnao.webInterface.mode.W_NearLand_Lot;

public class Activity_LotBaiduMap extends Activity {
	private WuFangNaoApplication app;

	private MyLocationMapView mlmv_MapView;
	private ImageView iv_location;
	/**
	 * 图层控制类
	 */
	private MapController mapController;
	/**
	 * 用户定位的经度
	 */
	private double latitude;
	/**
	 * 用户定位的纬度
	 */
	private double longitude;
	/**
	 * 标注集合
	 */
	private PropertyOverlayItem propertyOverlayItem;
	/**
	 * 用户当前位置
	 */
	private GeoPoint userPoint;
	/**
	 * 屏幕左下角点坐标
	 */
	double latitude_left;
	double longitude_left;
	/**
	 * 屏幕右上角点坐标
	 */
	double latitude_right;
	double longitude_right;
	/**
	 * 当用户停止操作地图后2秒，开始获取数据
	 */
	private Timer timer;
	/**
	 * 是否显示说明
	 */
	private boolean showLabel = true;
	/**
	 * 返回按钮
	 */
	private ImageButton ib_mapback;
	/**
	 * 用户位置
	 */
	private String coordinateName;
	/**
	 * 切换到列表按钮
	 */
	private ImageView iv_MaporList;
	/**
	 * 获取Intent 信息
	 */
	private Intent intent;
	/**
	 * 地块信息
	 */
	private W_NearLand w_NearLand;
	/**
	 * 定位到用户当前位置
	 */
	private IntentFilter locationUserFilter = new IntentFilter(S_ActionInfo.S_LOCATION_USER_POINT);
	/**
	 * 定位到用户当期位置
	 */
	private BroadcastReceiver LocationUserReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			GeoPoint point = new GeoPoint((int)(app.latitude*1E6), (int)(app.longitude*1E6));
			mapController.setCenter(point);
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initValue();
		initBaiduMap();
		setListener();
	}

	/**
	 * 获取地图信息
	 */
	private void initValue() {
		intent = getIntent();
		coordinateName = intent.getStringExtra("location");
		w_NearLand = (W_NearLand) intent.getSerializableExtra("LotList");
		LogCat.d("地块信息","获取的地块信息数量："+w_NearLand.getDatasource().size());
		latitude = intent.getDoubleExtra("latitude",
				WuFangNaoApplication.latitude);

		longitude = intent.getDoubleExtra("longitude",
				WuFangNaoApplication.longitude);
		this.registerReceiver(LocationUserReceiver, locationUserFilter);
	}

	/**
	 * 实例化定位地图信息
	 */
	private void initBaiduMap() {
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		app = (WuFangNaoApplication) this.getApplication();
		if (app.mBMapManager == null) {
			LogCat.d("初始化百度地图","初始化百度地图：");
			app.mBMapManager = new BMapManager(this);
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(WuFangNaoApplication.strKey,
					new WuFangNaoApplication.MyGeneralListener());
		}
		/**
		 * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		 */
		setContentView(R.layout.baidumap_lot_layout);
		/**
		 * 初始化地图组件
		 */
		mlmv_MapView = (MyLocationMapView) findViewById(R.id.mv_lot_baidumap);
		/**
		 * 初始化定位组件
		 */
		iv_location = (ImageView)findViewById(R.id.iv_lot_touserpoint);
		ib_mapback = (ImageButton)findViewById(R.id.iv_lot_mapback);
		iv_MaporList = (ImageView)findViewById(R.id.iv_lot_maporlist);
		mapController = mlmv_MapView.getController();
		/**
		 * 地图初始化放大倍数
		 */
		mapController.setZoom((float)14);
		/**
		 * 能否点击地图
		 */
		mapController.enableClick(true);
		/**
		 * 地图俯视角度
		 */
		mapController.setRotation(-1);
		/**
		 * 地图状态监听
		 */
		mlmv_MapView.regMapStatusChangeListener(listener);
		// 生成ItemizedOverlay图层用来标注结果点
		/**
		 * 用户选择位置，或用户当前位置
		 */
		userPoint = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));
		/**
		 * 初始化标组图层
		 */
		propertyOverlayItem = new PropertyOverlayItem(null, mlmv_MapView);
		// 生成Item
		addOverlayItem(mlmv_MapView.getZoomLevel());
		/**
		 * 设置地图中心点位置
		 */
		mapController.setCenter(userPoint);
		/**
		 * 添加标组图层
		 */
		mlmv_MapView.getOverlays().add(propertyOverlayItem);
		/**
		 * 刷新地图
		 */
		mlmv_MapView.refresh();
		LogCat.d("初始化百度地图","百度地图初始化完成");

	}
	private void setListener()
	{
		ib_mapback.setOnClickListener(new ClickBackListener());
		iv_location.setOnClickListener(new CenterPointToUserListener());
		iv_MaporList.setOnTouchListener(new MaporListOnTounchListener());
	}
	private class ClickBackListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			onBackPressed();
		}
	}
	private class CenterPointToUserListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			
			app.getLocationData();
		}
		
	}
	private class MaporListOnTounchListener implements OnTouchListener
	{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			{
				
				if(event.getX()>v.getWidth()/2)
				{
					Intent intent = new Intent(Activity_LotBaiduMap.this,Activity_LotList.class);
					intent.putExtra("W_NearLand",w_NearLand);
					intent.putExtra("localName", coordinateName);
					startActivity(intent);
					Activity_LotBaiduMap.this.overridePendingTransition(
							R.anim.fragment_right_in, R.anim.fragment_left_out);
				}
				
			}
			
			return false;
		}
		
	}
	/**
	 * 设置地图显示的标注样式
	 * 
	 * @param showLabel
	 *            //是否显示信息
	 */
	private void addOverlayItem(float zoom) {
		LogCat.d("添加用户坐标标记","添加用户坐标标记");
		OverlayItem item = new OverlayItem(userPoint, "", null);
		// 得到需要标在地图上的资源
		Drawable marker = null;
		marker = getResources().getDrawable(R.drawable.ic_user_location);
		// 为maker定义位置和边界
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		// 给item设置marker
		item.setMarker(marker);
		// 在图层上添加item
		propertyOverlayItem.addItem(item);

		/**
		 * 添加地块标签
		 */
		LogCat.d("添加地块标记","地块标记数量："+w_NearLand.getDatasource().size());
		for (W_NearLand_Lot propertyInfo : w_NearLand.getDatasource()) {
			// 生成Item

			GeoPoint point = new GeoPoint((int) (Double.valueOf(propertyInfo
					.getLatitude()) * 1E6), (int) (Double.valueOf(propertyInfo
					.getLongitude()) * 1E6));
			OverlayItem propertyitem = new OverlayItem(point, "", null);
			// 得到需要标在地图上的资源
			Drawable property = null;
			if (zoom >= 12)// 是否显示标签
			{
			
				property = convertViewToBitmap(propertyInfo.getLandNo(),
						propertyInfo.getLandFloor(),
						propertyInfo.getLandarea(),
						propertyInfo.getLandUse(),
						propertyInfo.getLandmaketime(), false);
			} else {
				property = getResources().getDrawable(R.drawable.ic_property);
			}
			// 为maker定义位置和边界

			property.setBounds(0, 0, property.getIntrinsicWidth(),
					property.getIntrinsicHeight());
			// 给item设置marker
			propertyitem.setMarker(property);
			// 在图层上添加item
			propertyOverlayItem.addItem(propertyitem);

		}
	}



	/**
	 * 地块标签类
	 * 
	 * @author lrk 2013-12-13上午11:37:58
	 */
	private class PropertyOverlayItem extends ItemizedOverlay {

		public PropertyOverlayItem(Drawable drawable, MapView mapView) {
			super(drawable, mapView);

		}

		@Override
		protected boolean onTap(int position) {
			

			return false;
		}

		@Override
		public boolean onTap(GeoPoint point, MapView mapView) {

			return false;
		}

	}

	/**
	 * 地图状态监听事件，主要用来监听地图的放大等级，旋转角度，俯视角度，中心点坐标
	 */
	MKMapStatusChangeListener listener = new MKMapStatusChangeListener() {
		public void onMapStatusChange(MKMapStatus mapStatus) {

			float zoom = mapStatus.zoom; // 地图缩放等级
			/*
			 * int overlooking = mapStatus.overlooking; // 地图俯视角度 int rotate =
			 * mapStatus.rotate; // 地图旋转角度 GeoPoint targetGeo =
			 * mapStatus.targetGeo; // 中心点的地理坐标 Point targetScreen =
			 * mapStatus.targetScreen; // 中心点的屏幕坐标
			 */

			/**
			 * 当放大等级小于12并且label显示状态下时，取消label显示
			 */
			System.out.println("zoom = " + zoom);
			/**
			 * 计算经纬度跨度
			 */
			if (mlmv_MapView.getProjection().fromPixels(0, 0) != null) {
				/**
				 * 屏幕左下角点坐标
				 */
				latitude_left = (mlmv_MapView.getMapCenter().getLatitudeE6() - mlmv_MapView
						.getLatitudeSpan() / 2) / 1E6;
				longitude_left = (mlmv_MapView.getMapCenter().getLongitudeE6() - mlmv_MapView
						.getLongitudeSpan() / 2) / 1E6;
				/**
				 * 屏幕右上角点坐标
				 */
				latitude_right = (mlmv_MapView.getMapCenter().getLatitudeE6() + mlmv_MapView
						.getLatitudeSpan() / 2) / 1E6;
				longitude_right = (mlmv_MapView.getMapCenter().getLongitudeE6() + mlmv_MapView
						.getLongitudeSpan() / 2) / 1E6;
				if (timer != null)// 清空之前的计时器，创建新的计时器
				{
					timer.cancel();// 停止计时器
					timer = null;// 清空计时器
				}
				/**
				 * 获取当前地图中心点附近的地块信息
				 */
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						handler.sendEmptyMessage(1);
						timer.cancel();
					}
				};
				timer = new Timer();
				// 延迟1.5秒后向服务器申请新的地块信息
				timer.schedule(task, 1500);

			}
			if (zoom < 12 && showLabel) {// 当地块信息已经显示，并且用户缩小地图到倍数小于12
				propertyOverlayItem.removeAll();
				showLabel = false;
				addOverlayItem(mlmv_MapView.getZoomLevel());
				mlmv_MapView.refresh();
			} else if (zoom >= 12 && !showLabel) {// 当地图放大倍数超过12倍时并且标签为未显示时显示标签
				/**
				 * 当缩放尺度在12~14之间，并且标签全部显示时，要刷新地图标注
				 */
				propertyOverlayItem.removeAll();
				showLabel = true;
				addOverlayItem(mlmv_MapView.getZoomLevel());
				mlmv_MapView.refresh();
			}
		}

	};
	/**
	 * 用户手势操作地图后两秒内没有再操作，读取当前地图跨度，并向服务器申请范围内的地块信息。
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			/**
			 * 申请经纬度范围内的地块信息
			 */
			LogCat.v("申请经纬度范围内的地块信息","申请信息");
			LogCat.v("申请经纬度范围内的地块信息","latitude_left = "+latitude_left);
			LogCat.v("申请经纬度范围内的地块信息","longitude_left = "+longitude_left);
			LogCat.v("申请经纬度范围内的地块信息","latitude_right = "+latitude_right);
			LogCat.v("申请经纬度范围内的地块信息","longitude_right = "+longitude_right);
			Thread zoomThread = new Thread(new TZoomLand(
					Activity_LotBaiduMap.this, new ZoomLandHander(),
					latitude_left, longitude_left, latitude_right,
					longitude_right));
			zoomThread.start();
		}
	};

	/**
	 * 
	 * @param landNo   
	 * @param landfloor
	 * @param landareacount
	 * @param landuse
	 * @param landmaketime
	 * @param isUser
	 * @return
	 */
	public BitmapDrawable convertViewToBitmap(String landNo, String landfloor,
			String landareacount, String landuse, String landmaketime,
			boolean isUser) {
		/**
		 * Mark整体布局
		 */
		View view = LayoutInflater.from(this).inflate(
				R.layout.lotoverlayitem_layout, null);
		/**
		 * 地块编号
		 */
		TextView tv_landNo = (TextView) view.findViewById(R.id.tv_lotid);
		/**
		 * 地块成交价
		 */
		TextView tv_landfloor = (TextView) view.findViewById(R.id.tv_landfloor);
		/**
		 * 楼面价
		 */
		TextView tv_landmaketime = (TextView) view
				.findViewById(R.id.tv_landmaketime);
		/**
		 * 土地使用面积
		 */
		TextView tv_landareacount = (TextView) view
				.findViewById(R.id.tv_lotareacount);
		/**
		 * 土地用途
		 */
		TextView tv_landuse = (TextView) view.findViewById(R.id.tv_landuse);
		/**
		 * 显示地块ID
		 */
		tv_landNo.setText(landNo);
		/**
		 * 楼面价格
		 */

		if (landfloor != null && !"".equals(landfloor)) {
			tv_landfloor.setText(landfloor);
		} else {
			view.findViewById(R.id.rlyt_landfloor).setVisibility(View.GONE);
		}
		/**
		 * 成交时间
		 */
		if (landmaketime != null &&! "".equals(landmaketime)) {
			tv_landmaketime.setText(landmaketime);
		} else {
			view.findViewById(R.id.rlyt_landmaketime).setVisibility(View.GONE);
		}
		/**
		 * 土地使用面积
		 */
		if (landareacount != null &&! "".equals(landareacount)) {
			tv_landareacount.setText(landareacount);
		} else {
			view.findViewById(R.id.rlyt_landareacount).setVisibility(View.GONE);
		}
		/**
		 * 土地用途
		 */
		if (landuse != null && !"".equals(landuse)) {
			tv_landuse.setText(landuse);

		} else {
			view.findViewById(R.id.rlyt_landuse).setVisibility(View.GONE);
		}
		if (isUser)// 是否是用户定位位置，或用户选择区域
		{
			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_lot_voerlay);
			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.llyt_lot_info);
			layout.setVisibility(View.GONE);
			/**
			 * 更改标签为用户标签
			 */
			imageView.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_user_location));
		}
		System.out.println("spec = "
				+ MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.measure(0,
				0);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		return bitmapDrawable;
	}

	@Override
	public void onBackPressed() {
		System.out.println("lot back");
		LogCat.d("返回上一级界面","关闭地块操作");
		Intent intent = new Intent(Activity_LotBaiduMap.this,
				Activity_Index.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	
	}

	private class ZoomLandHander extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.getData().getString("result").equals("true")) {
				/**
				 * 获得区域内的地块信息
				 */
				
				W_NearLand nearLand = TNearLand.getParsingJson(msg.getData()
						.getString("jsonResult"));
				if(nearLand.getDatasource().size()!=0)
				{
					List<W_NearLand_Lot> list_info = new ArrayList<W_NearLand_Lot>();
					LogCat.d("获得区域内的地块信息","地块数量："+nearLand.getDatasource().size());
					/**
					 * 比对现在地块和新的地块信息，是否有更新
					 */
					LogCat.d("地块信息去重","开始去重");
					same: for (W_NearLand_Lot element : nearLand.getDatasource()) {
	
						for (W_NearLand_Lot iterable : w_NearLand.getDatasource()) {
	
							if (iterable.getLandid() == element.getLandid()) {
								break same;
							}
	
						}
						list_info.add(element);
					}
					LogCat.d("去重完毕","完成删除重复操作");
					/**
					 * 如果有新的地块信息，则更新显示地块信息
					 */
					if (list_info.size() != 0) {
						LogCat.d("更新地块标记","有新的标记被添加");
						w_NearLand.setDatasource(nearLand.getDatasource());
						propertyOverlayItem.removeAll();
						addOverlayItem(mlmv_MapView.getZoomLevel());
						mlmv_MapView.refresh();
					}
				}

			}
		}
	}
	
	@Override
	protected void onDestroy() {
		this.unregisterReceiver(LocationUserReceiver);
		propertyOverlayItem.removeAll();
		propertyOverlayItem=null;
		mlmv_MapView.destroyDrawingCache();
		mlmv_MapView.destroy();
		System.out.println("lot_activity destory");
		super.onDestroy();
		
	}

}
