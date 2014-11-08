package com.wufangnao.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapStatus;
import com.baidu.mapapi.map.MKMapStatusChangeListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.wufangnao.R;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.combination.LabelItem;
import com.wufangnao.combination.MyDialogView;
import com.wufangnao.combination.MyLocationMapView;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.item.PropertyInfo;
import com.wufangnao.utils.DensityUtil;
import com.wufangnao.webInterface.TLabel;
import com.wufangnao.webInterface.TLocalProperty;
import com.wufangnao.webInterface.TPropertyZoom;
import com.wufangnao.webInterface.TShowLabel;
import com.wufangnao.webInterface.mode.W_Label;
import com.wufangnao.webInterface.mode.W_LocalProperty;
import com.wufangnao.webInterface.mode.W_PropertyInfo;
import com.wufangnao.webInterface.mode.W_ShowLabel;

/**
 * 显示百度地图界面。主要用来显示楼盘位置
 * 
 * @author lrk
 * 
 */
public class Activity_BaiDuMap extends Activity {
	/**
	 * 定位图层类
	 */
	private MyLocationMapView mlmv_MapView = null;

	/**
	 * 图层控制类
	 */
	private MapController mapController;
	/**
	 * MKMapViewListener 用于处理地图事件回调
	 */
	private MapViewListener mapview_Listener = null;
	/**
	 * 系统弹窗
	 */
	private PopupOverlay po_pop;
	/**
	 * 区域楼盘信息列表
	 */
	private W_LocalProperty w_LocalProperty;
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
	 * 是否显示全部标签
	 */
	private boolean showAllLabel = true;
	private Context context;
	/**
	 * 用户选择地域，或当前坐标名字
	 */
	private String localName;
	/**
	 * 初始化地图中心点
	 */
	private GeoPoint userPoint;

	/**
	 * 是否显示说明
	 */
	private boolean showLabel = true;
	/**
	 * 楼盘标签
	 */
	private List<W_Label> label_list;
	/**
	 * 楼盘信息布局
	 */
	private LinearLayout llyt_propertyInfo;
	/**
	 * 楼盘详情按钮
	 */
	private ImageView iv_propertyInfo;
	/**
	 * 关闭信息按钮
	 */
	private ImageView iv_colseView;
	/**
	 * 信息内容
	 */
	private ViewPager vp_propertyContent;
	/**
	 * 楼盘信息组件
	 */
	private PropertyInfo pi_Info;
	private ImageView iv_centerTOuser;
	/**
	 * 
	 */
	private DisplayMetrics displayMetrics;
	/**
	 * 楼盘信息与标签数组
	 */
	private List<View> views;
	/**
	 * 楼盘信息标签是否出现
	 */
	private boolean llyt_isShow = false;
	/**
	 * 是否显示楼盘收藏
	 */
	private boolean isPropertyCollection = false;
	/**
	 * 当前点击的楼盘ID
	 */
	private int propertyID = -1;
	private WuFangNaoApplication app;
	/**
	 * 楼盘对应标签
	 */
	private Map<Integer, View> propertyLabel;
	/**
	 * 楼盘信息适配器
	 */
	private propertyInfoAdapter infoAdapter;
	/**
	 * 提示框
	 */
	private MyDialogView msgDialog;
	/**
	 * 当用户停止操作地图后2秒，开始获取数据
	 */
	private Timer timer;
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
	 * 返回按钮
	 */
	private  ImageButton ib_mapback;
	/**
	 * 地图与列表切换按钮
	 */
	private ImageView iv_MaporList;
	private final static float LEVEL_THREE = 16;
	private final static float LEVEL_TWO = 14;
	/**
	 * 楼盘标签的颜色值
	 */
	private int[] unClickColors={R.drawable.unselect_label_bg_1,R.drawable.unselect_label_bg_2,
			R.drawable.unselect_label_bg_3,R.drawable.unselect_label_bg_4};
	private int[] clickColors={R.drawable.select_label_bg_1,R.drawable.select_label_bg_2,
			R.drawable.select_label_bg_3,R.drawable.select_label_bg_4};
	/**
	 * 改变收藏状态
	 */
	private IntentFilter favoFilter = new IntentFilter(
			S_ActionInfo.S_CHANGEFAVO);
	/**
	 * 收藏改变通知接收器
	 */
	private BroadcastReceiver favoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int position = intent.getIntExtra("position", -1);
			boolean favo = intent.getBooleanExtra("favo", false);
			
			if (position > -1) {// 收藏成功，修改收藏状态
				
				w_LocalProperty.getDatasource().get(position).setFavo(favo);

			}

		}

	};
	/**
	 *刷新本地数据
	 */
	private IntentFilter refeshDataFilter = new IntentFilter(S_ActionInfo.S_REFESHDATA);
	/***
	 * 记录楼盘详情到本地
	 */
	private BroadcastReceiver refeshDataReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			int position = 	intent.getIntExtra("position", -1);
			if(position!=-1)
			{
				W_PropertyInfo propertyInfo = (W_PropertyInfo) intent.getSerializableExtra("PropertyMessage");
				w_LocalProperty.getDatasource().get(position).setData(propertyInfo);
				
			}
			
			
		}
		
	};
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
		Log.d("lrk", "create 百度地图");
		initValue();// 获取信息
		initBaiduMap();// 初始化百度地图信息
		setListener();
		initReceiver();

	}

	/**
	 * 获取前一个Activity传递的信息
	 */
	private void initValue() {
		context = this;

		/**
		 * 获得楼盘信息数组
		 */
		w_LocalProperty = (W_LocalProperty) getIntent().getSerializableExtra(
				"propertyList");
		if (getIntent().hasExtra("isPropertyCollection")) {
			isPropertyCollection = true;

			latitude = Double.valueOf(w_LocalProperty.getDatasource().get(0)
					.getLatitude());

			longitude = Double.valueOf(w_LocalProperty.getDatasource().get(0)
					.getLongitude());

		} else {
			latitude = getIntent().getDoubleExtra("latitude", 0.000);// 获得地图中心点经度

			longitude = getIntent().getDoubleExtra("longitude", 0.0000);// 获得地图中西点纬度

			localName = getIntent().getStringExtra("qu");
		}

		propertyLabel = new HashMap<Integer, View>();// 楼盘标签View

		/**
		 * 获取第一页的楼盘标签信息
		 */
		Thread thread = new Thread(new TShowLabel(this, new ShowLabelHandler(),
				1));
		thread.start();
	}

	private void initReceiver() {
		Activity_BaiDuMap.this.registerReceiver(favoReceiver, favoFilter);
		Activity_BaiDuMap.this.registerReceiver(refeshDataReceiver,refeshDataFilter);
		Activity_BaiDuMap.this.registerReceiver(LocationUserReceiver,locationUserFilter);
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
			app.mBMapManager = new BMapManager(this);
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(WuFangNaoApplication.strKey,
					new WuFangNaoApplication.MyGeneralListener());
		}
		/**
		 * 窗口管理对象
		 */
		displayMetrics = getResources().getDisplayMetrics();
		/**
		 * 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		 */
		setContentView(R.layout.baidumap_layout);
		/**
		 * 初始化地图组件
		 */
		mlmv_MapView = (MyLocationMapView) findViewById(R.id.mv_baidumap);
		/**
		 * 初始化楼盘信息组件
		 */
		llyt_propertyInfo = (LinearLayout) findViewById(R.id.llyt_propertymsg);
		/**
		 *用户定位按钮
		 */
		iv_centerTOuser = (ImageView)findViewById(R.id.iv_touserpoint);
		/**
		 * 返回按钮
		 */
		ib_mapback = (ImageButton)findViewById(R.id.ib_mapback);
		/**
		 * 地图切换按钮
		 */
		iv_MaporList =(ImageView)findViewById(R.id.iv_maporlist);
		iv_centerTOuser.setOnClickListener(new CenterToUserPosition());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, getWindowManager()
						.getDefaultDisplay().getHeight() * 2 / 3);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		llyt_propertyInfo.setLayoutParams(params);
		/**
		 * 选择按钮
		 */
		iv_colseView = (ImageView) findViewById(R.id.iv_colseview);
		/**
		 * 用户信息点击按钮
		 */
		iv_propertyInfo = (ImageView) findViewById(R.id.iv_selectbutton);
		/**
		 * 信息内容组件
		 */
		vp_propertyContent = (ViewPager) findViewById(R.id.vp_property_content);
		/**
		 * 楼盘信息
		 */
		pi_Info = new PropertyInfo(this);

		/**
		 * View数组
		 */
		views = new ArrayList<View>();
		views.add(pi_Info.getView());
		/**
		 * 地图空组组件
		 */
		mapController = mlmv_MapView.getController();
		/**
		 * 地图初始化放大倍数
		 */
		mapController.setZoom((float) 16.2);
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
		Log.d("lrk","生成Item");
		addOverlayItem(mlmv_MapView.getZoomLevel());
		
		/**
		 * 设置地图中心点位置
		 */
		mapController.setCenter(userPoint);
		/**
		 * 添加标组图层
		 */
		Log.d("lrk","添加标组图层");
		mlmv_MapView.getOverlays().add(propertyOverlayItem);
		/**
		 * 刷新地图
		 */
		mlmv_MapView.refresh();
		Log.d("lrk","刷新地图");

	}

	/**
	 * 设置监听事件
	 */
	private void setListener() {
		iv_propertyInfo.setOnTouchListener(new SelectOnTouchListener());
		vp_propertyContent
				.setOnPageChangeListener(new viewPagerSlectionListener());
		iv_colseView.setOnClickListener(new CloseViewClickListener());
		/**
		 * 地图截图监听事件
		 */
		mapview_Listener = new MapViewListener();
		ib_mapback.setOnClickListener(new ClickBackListener());
		iv_MaporList.setOnTouchListener(new MaporListOnTounchListener());
		mlmv_MapView.regMapViewListener(app.mBMapManager, mapview_Listener);
	}
	private class MaporListOnTounchListener implements OnTouchListener
	{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			{
				
				if(event.getX()>v.getWidth()/2)
				{
					Intent intent = new Intent(Activity_BaiDuMap.this,Activity_PorpoertyList.class);
					intent.putExtra("W_LocalProperty",w_LocalProperty);
					intent.putExtra("localName", localName);
					startActivity(intent);
					Activity_BaiDuMap.this.overridePendingTransition(
							R.anim.fragment_right_in, R.anim.fragment_left_out);
				}
				
			}
			
			return false;
		}
		
	}
	/**
	 * 
	 * @ClassName: ClickBackListener 
	 * @Description: TODO 返回上一级界面
	 * @author lrk
	 * @date 2014-3-4 上午10:48:50 
	 *
	 */
	private class ClickBackListener implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			onBackPressed();
		}
	}
	/**
	 * 设置地图显示的标注样式
	 * 
	 * @param showLabel
	 *            //是否显示信息
	 */
	private void addOverlayItem(float zoom) {
		if (!isPropertyCollection) {
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
		}
		/**
		 * 设置房产信息
		 */
		int type = 0;
	
		if (zoom>=LEVEL_THREE)// 是否显示标签
		{
			showAllLabel = true;
			type =0;
		}
		else if (zoom>=LEVEL_TWO&&zoom<LEVEL_THREE) {//显示两级标签
			showAllLabel = false;
			showLabel=true;
			type =1;
		}
		else {//完全不显示标签
			showAllLabel = false;
			showLabel=false;
			type = 2;
		}
		for (W_PropertyInfo propertyInfo : w_LocalProperty.getDatasource()) {
			// 生成Item

			GeoPoint point = new GeoPoint((int) (Double.valueOf(propertyInfo
					.getLatitude()) * 1E6), (int) (Double.valueOf(propertyInfo
					.getLongitude()) * 1E6));
			OverlayItem propertyitem = new OverlayItem(point, "", null);
			// 得到需要标在地图上的资源
			Drawable property = null;
			switch (type) {
			case 0:
				property = convertViewToBitmap(propertyInfo.getProjjectName(),
						propertyInfo.getHotlabeName(),propertyInfo.getHousePrice(), false);
				break;
			case 1:
				property = convertViewToBitmap(null,
						null,propertyInfo.getHousePrice(), false);
							break;
			case 2:
				property = getResources().getDrawable(
						R.drawable.ic_property);
				break;
			}
			property.setBounds(0, 0, property.getIntrinsicWidth(),
					property.getIntrinsicHeight());
			// 给item设置marker
			propertyitem.setMarker(property);
			// 在图层上添加item
			propertyOverlayItem.addItem(propertyitem);

		}
	}
	/**
	 * 定位到用户当前位置
	 */
	private class CenterToUserPosition implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			
			app.getLocationData();
			
		}
		
	}
	@Override
	protected void onPause() {
		mlmv_MapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mlmv_MapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		System.out.println("map destory");
		this.unregisterReceiver(favoReceiver);
		this.unregisterReceiver(refeshDataReceiver);
		this.unregisterReceiver(LocationUserReceiver);
		propertyOverlayItem.removeAll();
		propertyOverlayItem=null;
		mlmv_MapView.destroyDrawingCache();
		mlmv_MapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mlmv_MapView.onSaveInstanceState(outState);

	}

	/**
	 * 楼盘标签类
	 * 
	 * @author lrk 2013-12-13上午11:37:58
	 */
	private class PropertyOverlayItem extends ItemizedOverlay {

		public PropertyOverlayItem(Drawable drawable, MapView mapView) {
			super(drawable, mapView);

		}

		@Override
		protected boolean onTap(int position) {
			/**
			 * 点击的是不是用户当前位置
			 */
			if (position != 0) {

				iv_propertyInfo
						.setImageResource(R.drawable.selectd_propertyinfo);
				propertyID = w_LocalProperty.getDatasource().get(position - 1)
						.getPropertyID();
				pi_Info.initValue(
						w_LocalProperty.getDatasource().get(position - 1),
						position - 1);
				initShowLabel(propertyID);
				/**
				 * 设置菜单隐藏动画
				 */

				llyt_propertyInfo.setVisibility(View.VISIBLE);
				Animation Animation_Translate = new TranslateAnimation(0, 0,
						llyt_propertyInfo.getHeight(), 0);
				/**
				 * 设置动画播放时长
				 */
				Animation_Translate.setDuration(500);
				llyt_propertyInfo.startAnimation(Animation_Translate);

				llyt_isShow = true;

			} else {
				if (isPropertyCollection) {
					propertyID = w_LocalProperty.getDatasource().get(position)
							.getPropertyID();
					pi_Info.initValue(
							w_LocalProperty.getDatasource().get(position),
							position);
					initShowLabel(propertyID);
					/**
					 * 设置菜单隐藏动画
					 */

					llyt_propertyInfo.setVisibility(View.VISIBLE);
					Animation Animation_Translate = new TranslateAnimation(0,
							0, llyt_propertyInfo.getHeight(), 0);
					/**
					 * 设置动画播放时长
					 */
					Animation_Translate.setDuration(500);
					llyt_propertyInfo.startAnimation(Animation_Translate);

					llyt_isShow = true;
				}
			}

			return false;
		}

		@Override
		public boolean onTap(GeoPoint point, MapView mapView) {

			return false;
		}

	}

	/**
	 * 使用自定义的布局转化成BitmapDrawable作为自定义的Mark,显示楼盘名称和最热门标签
	 * 
	 * @param propertyName
	 *            //楼盘名称
	 * @param hotLabel
	 *            //最热标签
	 * @return
	 */
	public BitmapDrawable convertViewToBitmap(String propertyName,
			String hotLabel,String price, boolean isUser) {
		/**
		 * Mark整体布局
		 */
		View view = LayoutInflater.from(context).inflate(
				R.layout.overlayitem_layout, null);
		/**
		 * 楼盘名称
		 */
		TextView tv_propertyName = (TextView) view
				.findViewById(R.id.tv_propertyname);
		/**
		 * 楼盘价格
		 */
		TextView tv_price = (TextView)view.findViewById(R.id.tv_price); 
		/**
		 * 热门标签
		 */
		TextView tv_hotlabel = (TextView) view.findViewById(R.id.tv_hotlabel);
		/**
		 * 显示楼盘标签
		 */
		tv_propertyName.setText(propertyName);
		/**
		 * 显示最热标签
		 */
		if (hotLabel != null && !hotLabel.equals("")) {
			tv_hotlabel.setVisibility(View.VISIBLE);
			tv_hotlabel.setText(hotLabel);
		} else// 最热标签为空，则隐藏
		{
			tv_hotlabel.setVisibility(View.GONE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
			lp.gravity=Gravity.CENTER_HORIZONTAL;
			tv_price.setLayoutParams(lp);
			
		}
		if(price!=null&&!price.equals(""))
		{
			tv_price.setVisibility(View.VISIBLE);
			tv_price.setText(price);
		}
		else {
			tv_price.setVisibility(View.GONE);
			
		}
		if(propertyName!=null&&!propertyName.equals(""))
		{
			tv_propertyName.setVisibility(View.VISIBLE);
			tv_propertyName.setText(propertyName);
		}
		else {
			LinearLayout imageView = (LinearLayout) view
					.findViewById(R.id.llyt_info);
			ImageView image_Button=(ImageView)view.findViewById(R.id.iv_infobtoom);
			ImageView image_overlay=(ImageView)view.findViewById(R.id.iv_voerlay);
			imageView.setBackgroundResource(R.drawable.property_price);
			tv_propertyName.setVisibility(View.GONE);
			image_Button.setVisibility(View.GONE);
			image_overlay.setVisibility(View.GONE);
		}
		if (isUser)// 是否是用户定位位置，或用户选择区域
		{
			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_voerlay);
			/**
			 * 更改标签为用户标签
			 */
			imageView.setImageDrawable(getResources().getDrawable(
					R.drawable.ic_user_location));
		}
		view.measure(0,
				0);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

		return bitmapDrawable;
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
				 * 获取当前地图中心点附近的楼盘信息
				 */
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						if(msgDialog!=null)
						{
							msgDialog.cancel();
						}
						Looper.prepare();
						msgDialog = new MyDialogView(Activity_BaiDuMap.this);
						
						msgDialog.show();
						handler.sendEmptyMessage(1);
						Looper.loop();
						timer.cancel();
					}
				};
				timer = new Timer();
				//延迟1.5秒后向服务器申请新的地块信息
				timer.schedule(task, 1500);

			}
		
			}
		
	};

	/**
	 * 楼盘标签解析类
	 * 
	 * @author lrk 2013-12-13上午11:35:06
	 */
	private class ShowLabelHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			if (msg.getData().getString("result").equals("true")) {
				W_ShowLabel showLabel = TShowLabel.getJsonParsing(msg.getData()
						.getString("jsonResult"));

				label_list = showLabel.getDatasource();
				Log.d("lrk", "label_list = "+label_list.size());

			}
		}
	}

	/**
	 * 标签列表装载数据
	 */
	private void initShowLabel(int propertyID) {
		ScrollView scrollView = new ScrollView(this);
		scrollView.setVerticalScrollBarEnabled(true);
		LinearLayout llyt_showLabel = null;
		if (propertyLabel.get(propertyID) == null)// 判断该楼盘的标签是否已经显示，首次点击则生成楼盘标签
		{
			
			llyt_showLabel = new LinearLayout(this);

			llyt_showLabel.setOrientation(LinearLayout.VERTICAL);
			llyt_showLabel.setPadding(DensityUtil.dip2px(this, 10),
					DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10),
					DensityUtil.dip2px(this, 10));
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			layout.setPadding(DensityUtil.dip2px(this, 5),
					DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5),
					DensityUtil.dip2px(this, 5));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(DensityUtil.dip2px(this, 2f),
					DensityUtil.dip2px(this, 2f), DensityUtil.dip2px(this, 2f),
					DensityUtil.dip2px(this, 2f));
			int length = 0;
			/**
			 * 根据标签列表，添加标签
			 */
			for (int i = 0; i < label_list.size(); i++) {
				LabelItem hottopicName = new LabelItem(this,null);
				hottopicName.setLayoutParams(params);
				hottopicName.setOnClickListener(new LabelClickListener());
				hottopicName.init(label_list.get(i));
				measureView(hottopicName);
				if (layout.getChildCount() > 0) {
					
					length += hottopicName.getMeasuredWidth() + 4;
					System.out.println("length--->" + length);
					if (length > displayMetrics.widthPixels
							- DensityUtil.dip2px(this, 20)) {
						System.out.println("-----------------------------");
						llyt_showLabel.addView(layout);
						layout = new LinearLayout(this);
						layout.setPadding(DensityUtil.dip2px(this, 5),
								DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5),
								DensityUtil.dip2px(this, 5));
						length = hottopicName.getMeasuredWidth()
								+ DensityUtil.dip2px(this, 4f);
						hottopicName.setLocation(0);
						hottopicName.setBackgroundResource(unClickColors[0]);
						layout.addView(hottopicName);
					} else {
						hottopicName.setLocation(layout.getChildCount());
						hottopicName.setBackgroundResource(unClickColors[layout.getChildCount()]);
						layout.addView(hottopicName);
					}
					if(layout.getChildCount()==4)
					{
						Log.d("lrk", "layout.getChildCount()==4");
						llyt_showLabel.addView(layout);
						layout = new LinearLayout(this);
						layout.setPadding(DensityUtil.dip2px(this, 5),
								DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 5),
								DensityUtil.dip2px(this, 5));
						length = 0;
					}
				} else {
					length += hottopicName.getMeasuredWidth()
							+ DensityUtil.dip2px(this, 4f);
					hottopicName.setLocation(0);
					hottopicName.setBackgroundResource(unClickColors[0]);
					layout.addView(hottopicName);
				}
				
					
			}
			scrollView.addView(llyt_showLabel);
			propertyLabel.put(propertyID, scrollView);
		} else {// 获得已完成的楼盘标签
			System.out.println("已有组件");
			scrollView = (ScrollView) propertyLabel.get(propertyID);
		}
		if (views.size() == 1)// 是是第一个楼盘标签
		{
			System.out.println("第一个楼盘标签");
			
			views.add(scrollView);

		} else {// 删除前一个楼盘的表情，显示当前楼盘标签
			System.out.println("替换前一个楼盘标签");
			views.remove(1);
			views.add(scrollView);

		}
		infoAdapter = new propertyInfoAdapter();
		vp_propertyContent.setAdapter(infoAdapter);
	}

	private void measureView(View child) {
		final DisplayMetrics displayMetrics = getResources()
				.getDisplayMetrics();
		ViewGroup.LayoutParams mContentLP = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		int widthMeasureSpec, heightMeasureSpec;

		if (mContentLP.width >= 0) {
			widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
					mContentLP.width, View.MeasureSpec.EXACTLY);
		} else { // wrap_content
			widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
					displayMetrics.widthPixels, View.MeasureSpec.AT_MOST);
		}

		if (mContentLP.height >= 0) {
			heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
					mContentLP.height, View.MeasureSpec.EXACTLY);
		} else { // wrap_content
			heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
					displayMetrics.heightPixels, View.MeasureSpec.AT_MOST);
		}

		child.measure(widthMeasureSpec, heightMeasureSpec);
	}

	private class propertyInfoAdapter extends PagerAdapter {

		public void destroyItem(View view, int position, Object object) {
			((ViewPager) view).removeView(views.get(position));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {
			return views.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

	}

	/**
	 * 界面滑动监听
	 * 
	 * @author lrk 2013-12-13下午03:27:13
	 */
	private class viewPagerSlectionListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				iv_propertyInfo
						.setImageResource(R.drawable.selectd_propertyinfo);
				break;

			default:
				iv_propertyInfo.setImageResource(R.drawable.select_showlabel);
				break;
			}

		}

	}

	/**
	 * 类型按钮点击事件
	 * 
	 * @author lrk 2013-12-13下午03:42:32
	 */
	private class SelectOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getX() >= v.getWidth() / 2)// 判断点击位置
			{
				iv_propertyInfo.setImageResource(R.drawable.select_showlabel);
				vp_propertyContent.setCurrentItem(1);
			} else {
				vp_propertyContent.setCurrentItem(0);
				iv_propertyInfo
						.setImageResource(R.drawable.selectd_propertyinfo);
			}
			return true;
		}

	}

	private class CloseViewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			/**
			 * 信息状态未显示
			 */
			if (llyt_propertyInfo.getVisibility() == View.GONE) {
				return;
			}
			llyt_isShow = false;
			/**
			 * 设置菜单隐藏动画
			 */
			Animation Animation_Translate = new TranslateAnimation(0, 0, 0,
					llyt_propertyInfo.getHeight());
			/**
			 * 设置动画播放时长
			 */
			Animation_Translate.setDuration(500);
			llyt_propertyInfo.startAnimation(Animation_Translate);
			/**
			 * 播放动画
			 */
			llyt_propertyInfo.setVisibility(View.GONE);

		}

	}

	@Override
	public void onBackPressed() {

		if (llyt_isShow)// 关闭信息内容
		{
			/**
			 * 设置菜单隐藏动画
			 */
			Animation Animation_Translate = new TranslateAnimation(0, 0, 0,
					llyt_propertyInfo.getHeight());
			/**
			 * 设置动画播放时长
			 */
			Animation_Translate.setDuration(500);
			llyt_propertyInfo.startAnimation(Animation_Translate);
			/**
			 * 播放动画
			 */
			llyt_propertyInfo.setVisibility(View.GONE);
			llyt_isShow = false;
			return;
		} else {

			Intent intent = new Intent(Activity_BaiDuMap.this,
					Activity_Index.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		}

	}

	/**
	 * 标签点击响应事件
	 * 
	 * @author lrk 2013-12-16上午11:28:54
	 */
	private class LabelClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			int id = (Integer) view.getTag(R.id.tag_id);
			boolean selsect = (Boolean) view.getTag(R.id.tag_select);
			int location = (Integer)view.getTag(R.id.tag_location);
			if (selsect) {
				view.setTag(R.id.tag_select, false);
				((LabelItem)view).delCount();
				view.setBackgroundResource(unClickColors[location]);
			} else {
				view.setTag(R.id.tag_select, true);
				((LabelItem)view).addCount();
				view.setBackgroundResource(clickColors[location]);
			}
			Thread thread = new Thread(new TLabel(Activity_BaiDuMap.this,
					new LabelClickHandler(view), propertyID, id));

			thread.start();
		}

	}

	/**
	 * 点击标签后后台数据的解析类
	 * 
	 * @author lrk 2013-12-16上午11:37:11
	 */
	private class LabelClickHandler extends Handler {
		private View view;

		public LabelClickHandler(View view) {
			this.view = view;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.getData().getString("result").equals("true")) {
			/*	boolean selsect = (Boolean) view.getTag(R.id.tag_select);
				int location = (Integer)view.getTag(R.id.tag_location);
				if (selsect) {
					view.setTag(R.id.tag_select, false);
					((LabelItem)view).delCount();
					view.setBackgroundResource(unClickColors[location]);
				} else {
					view.setTag(R.id.tag_select, true);
					((LabelItem)view).addCount();
					view.setBackgroundResource(clickColors[location]);
				}
*/
			}
		}
	}

	private class MapViewListener implements MKMapViewListener {

		@Override
		public void onClickMapPoi(MapPoi arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetCurrentMap(Bitmap bitmap) {
			Thread thread = new Thread(new CurrentMap(bitmap));
			thread.start();

		}

		@Override
		public void onMapAnimationFinish() {

		}

		@Override
		public void onMapLoadFinish() {

		}

		@Override
		public void onMapMoveFinish() {

		}

	}

	private class CurrentMap implements Runnable {
		private Bitmap bitmap;

		public CurrentMap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public void run() {
			/**
			 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
			 */
			System.out.println("开始截图");
			File file = new File(S_ActionInfo.getSDPath()+"property.png");
			System.out.println("文件位置" + file.toURI().toString());
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 70, out)) {
					out.flush();
					out.close();
				}
				System.out.println("截图位置+" + file.toURI().toString());
				Intent intent = new Intent(
						S_ActionInfo.S_REFESHMAP);
				intent.putExtra("Type",0);
				Activity_BaiDuMap.this.sendBroadcast(intent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @ClassName: PropertyZoomHandler
	 * @Description: TODO 解析楼盘基本信息
	 * @author lrk
	 * @date 2014-1-1 下午2:47:05
	 * 
	 */
	private class PropertyZoomHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.getData().getString("result").equals("true")) {
				/**
				 * 获得区域内的楼盘信息
				 */
				W_LocalProperty localProperty = TLocalProperty
						.getPaserJsonResult(msg.getData().getString(
								"jsonResult"));
				List<W_PropertyInfo> list_info = new ArrayList<W_PropertyInfo>();
				/**
				 * 比对现在楼盘和新的楼盘信息，是否有更新
				 */
				same: for (W_PropertyInfo element : localProperty
						.getDatasource()) {

					for (W_PropertyInfo iterable : w_LocalProperty
							.getDatasource()) {

						if (iterable.getPropertyID() == element.getPropertyID()) {
							break same;
						}

					}
					list_info.add(element);
				}
				msgDialog.dismiss();
				/**
				 * 如果有新的楼盘信息，则更新显示楼盘信息
				 */
				if(list_info.size()!=0)
				{
					w_LocalProperty.setDatasource(localProperty.getDatasource());
					propertyOverlayItem.removeAll();
					addOverlayItem(mlmv_MapView.getZoomLevel());
					mlmv_MapView.refresh();
				}
				else {
					Log.d("lrk", "mlmv_MapView.getZoomLevel() = "+mlmv_MapView.getZoomLevel());
					Log.d("lrk", "showLabel = "+showLabel);
					Log.d("lrk", "showAllLabel = "+showAllLabel);
					if (mlmv_MapView!=null&&mlmv_MapView.getZoomLevel() < LEVEL_TWO && showLabel) {
						Log.d("lrk", "显示一级菜单");
						propertyOverlayItem.removeAll();
						showLabel = false;			
						addOverlayItem(mlmv_MapView.getZoomLevel());
						mlmv_MapView.refresh();
				} else if (mlmv_MapView!=null&& mlmv_MapView.getZoomLevel()  >= LEVEL_TWO) {
						/**
						 * 当缩放尺度在12~14之间，并且标签全部显示时，要刷新地图标注
						 */
						if(mlmv_MapView!=null&&mlmv_MapView.getZoomLevel() <LEVEL_THREE&&showAllLabel)
						{
							Log.d("lrk", "显示二级级菜单");
							propertyOverlayItem.removeAll();
							showLabel = true;
							addOverlayItem(mlmv_MapView.getZoomLevel());
							mlmv_MapView.refresh();
						}
						/**
						 * 当缩放尺度大于14是，并且标签未显示时，要刷新地图标注
						 */
						else if(mlmv_MapView!=null&&mlmv_MapView.getZoomLevel() >=LEVEL_THREE&&!showAllLabel){
							Log.d("lrk", "显示三级菜单");
							propertyOverlayItem.removeAll();
							showLabel = true;
							showAllLabel = true;
							addOverlayItem(mlmv_MapView.getZoomLevel());
							mlmv_MapView.refresh();
						}
						else if(mlmv_MapView!=null&&mlmv_MapView.getZoomLevel() <LEVEL_THREE&&!showLabel){
							Log.d("lrk", "显示三级菜单");
							propertyOverlayItem.removeAll();
							showLabel = true;
							addOverlayItem(mlmv_MapView.getZoomLevel());
							mlmv_MapView.refresh();
						}
					}
				}
				
				
				
			} 
		
		}
	}
	/**
	 * 用户手势操作地图后两秒内没有再操作，读取当前地图跨度，并向服务器申请范围内的楼盘信息。
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			/**
			 * 申请经纬度范围内的楼盘信息
			 */
			Thread zoomThread = new Thread(new TPropertyZoom(
					Activity_BaiDuMap.this, new PropertyZoomHandler(),
					latitude_left, longitude_left, latitude_right,
					longitude_right));
			zoomThread.start();
		}
	};

}
