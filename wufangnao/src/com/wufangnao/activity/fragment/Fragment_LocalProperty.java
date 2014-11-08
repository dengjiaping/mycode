package com.wufangnao.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.wufangnao.R;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.LogCat.LogCat;
import com.wufangnao.activity.Activity_BaiDuMap;
import com.wufangnao.activity.Activity_Index;
import com.wufangnao.activity.Activity_LotBaiduMap;
import com.wufangnao.activity.view.View_localProperty;
import com.wufangnao.adapter.item.PropertyChartItem;
import com.wufangnao.adapter.item.PropertyTableItem;
import com.wufangnao.combination.CenterToast;
import com.wufangnao.combination.MyDialogView;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.webInterface.TCollectCoordinate;
import com.wufangnao.webInterface.TLocalProperty;
import com.wufangnao.webInterface.TNearLand;
import com.wufangnao.webInterface.TPlotAnalysis;
import com.wufangnao.webInterface.mode.W_LocalProperty;
import com.wufangnao.webInterface.mode.W_NearLand;
import com.wufangnao.webInterface.mode.W_PlotAnalysis;
import com.wufangnao.webInterface.mode.W_PropertyInfo;

public class Fragment_LocalProperty extends Fragment {
	/**
	 * 搜索模块，也可去掉地图模块独立使用
	 */
	private MKSearch pointSearch = null;

	/**
	 * 定位地址的经度
	 */
	private double latitude;
	/**
	 * 定位地址的纬度
	 */
	private double longitude;
	/**
	 * Application 类
	 */
	private WuFangNaoApplication app;
	/**
	 * 显示组件
	 */
	private View_localProperty v_LocalProperty;
	/**
	 * 楼盘基本信息
	 */
	private W_LocalProperty w_LocalProperty;

	private String qu;

	private String shi;
	/**
	 * 弹窗窗口，显示正在加载数据
	 */
	private Dialog messageDialog;
	/**
	 * 坐标名称
	 */
	private String coordinateName = null;
	/**
	 * int类型：柱状图宽度
	 */
	private int chartWidth;
	/**
	 * int类型：柱状图最高高度
	 */
	private int chartMaxHeight;
	/**
	 * double类型：柱状图显示上限数值
	 */
	private double maxValue;
	/**
	 * 百度地图坐标类：当前用户坐标点
	 */
	private GeoPoint userPoint;
	/**
	 * 组状态组件组件列表
	 */
	private List<PropertyChartItem> chartItems = null;
	/**
	 * 表格组件列表
	 */
	private List<PropertyTableItem> tableItems = null;
	/**
	 * 主Activity
	 */
	private Activity_Index index;

	private CenterToast toast;
	/**
	 * 广播事件名称：定位到用户当前位置
	 */
	private IntentFilter locationUserFilter = new IntentFilter(
			S_ActionInfo.S_LOCATION_USER_POINT);
	/**
	 * 广播事件接收器:定位到用户当期位置
	 */
	private BroadcastReceiver LocationUserReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			/**
			 * 将百度地图地位到的用户坐标封装到百度地图自带的GeoPoint类中
			 */
			userPoint = new GeoPoint((int) (app.latitude * 1e6),
					(int) (app.longitude * 1e6));
			/**
			 * 延迟1秒向服务器申请信息，使得界面不会因为装载数据而卡住
			 */
			codeHandler.sendEmptyMessageDelayed(0, 1000);

		}
	};
	/**
	 * 通过百度地图定位的出的用户坐标反编译成详细地址，后向服务器申请周边楼盘信息
	 */
	Handler codeHandler = new Handler() {
		public void handleMessage(Message msg) {
			/**
			 * 将用户坐标翻遍成详细地址
			 */
			pointSearch.reverseGeocode(userPoint);

		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		index = (Activity_Index) getActivity();
		v_LocalProperty = new View_localProperty(getActivity());

		super.onCreate(savedInstanceState);
	}

	/**
	 * 定位到用户区域
	 * 
	 * @param bundle
	 */
	public void setLocation(Bundle bundle) {
		/**
		 * 获得application对象，以获取公共信息
		 */
		if (app == null) {
			app = (WuFangNaoApplication) getActivity().getApplication();
		}
		/**
		 * 百度地图是否已经实例化
		 */
		if (app.mBMapManager == null) {
			app.initEngineManager(getActivity());
		}
		/**
		 * 定位定位对象
		 */
		pointSearch = new MKSearch();
		/**
		 * 实例化定位对象
		 */
		pointSearch.init(app.mBMapManager, new SearchLocalListener());
		/**
		 * 设计弹出框
		 */
		messageDialog = new MyDialogView(getActivity());
		messageDialog.show();

		/**
		 * 通过用户当前坐标地位区域
		 */
		w_LocalProperty = null;
		if (bundle.getBoolean("isGeoCode")) {
			app.getLocationData();

		} else {
			/**
			 * 用户选择区域定位
			 */
			qu = bundle.getString("qu");
			shi = bundle.getString("shi");
			System.out.println("用户选择区域为：" + shi + qu);
			v_LocalProperty.getTv_cityName().setText(shi);
			coordinateName = bundle.getString("shi") + bundle.getString("qu");
			pointSearch.geocode(qu, shi);

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setLocation(getArguments());
		/**
		 * 设置监听事件
		 */
		setListener();
		/**
		 * 注册广播接收器
		 */
		regiestReceiver();
		/*
		 * setCurrentMap("property.png",0); setCurrentMap("lot.png",1);
		 */
		return v_LocalProperty.getView();
	}

	/**
	 * 设置监听事件
	 */
	private void setListener() {
		v_LocalProperty.getIv_Property().setOnClickListener(
				new PropertyClickListener());
		v_LocalProperty.getIv_CollectionProperty().setOnClickListener(
				new CollectionCoordinateListener());
		v_LocalProperty.getIv_lot().setOnClickListener(new LotClickListener());

		v_LocalProperty.getTv_price().setOnClickListener(
				new TypeOnclickListener());
		v_LocalProperty.getTv_volumerate().setOnClickListener(
				new TypeOnclickListener());
		v_LocalProperty.getTv_areacovered().setOnClickListener(
				new TypeOnclickListener());
		v_LocalProperty.getTv_opentime().setOnClickListener(
				new TypeOnclickListener());
		v_LocalProperty.getHsv_chart().setOnTouchListener(
				new HsvOntouchListener());
		v_LocalProperty.getHsv_table().setOnTouchListener(
				new HsvOntouchListener());
		v_LocalProperty.getLlyt_location().setOnClickListener(
				new ShowSecondaryMenuClickListner());
		v_LocalProperty.getIv_menu().setOnClickListener(
				new ShowMenuClickListner());
		v_LocalProperty.getPtrsv_ScrollView().setOnRefreshListener(new ScrollViewRefreshListtener());

	}

	private class ShowMenuClickListner implements OnClickListener {

		@Override
		public void onClick(View v) {

			((Activity_Index) getActivity()).toggle();
		}

	}

	private class ShowSecondaryMenuClickListner implements OnClickListener {
		@Override
		public void onClick(View v) {
			((Activity_Index) getActivity()).showSecondaryMenu();
		}
	}

	/**
	 * 
	 * @ClassName: HsvOntouchListener
	 * @Description: TODO 横向滑动组件点击事件
	 * @author lrk
	 * @date 2014-1-19 下午4:55:31
	 * 
	 */
	private class HsvOntouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.d("lrk", "touch hsv");
			v_LocalProperty.getHsv_chart().scrollTo((int) v.getScrollX(), 0);
			v_LocalProperty.getHsv_table().scrollTo((int) v.getScrollX(), 0);
			return false;
		}

	}

	/**
	 * 
	 * @ClassName: TypeOnclickListener
	 * @Description: TODO 切换不同数据的点击事件
	 * @author lrk
	 * @date 2014-1-19 下午4:54:36
	 * 
	 */
	private class TypeOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (w_LocalProperty == null
					|| w_LocalProperty.getDatasource() == null) {
				Toast.makeText(getActivity(), "没有可以显示的数据", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			switch (v.getId()) {
			case R.id.propertytable_title_price:// 点击价格标签

				if (v_LocalProperty.getV_price_bottomline().getVisibility() == View.VISIBLE) {
					return;
				} else {
					v_LocalProperty.getTv_chartUnit().setTextSize(
							TypedValue.COMPLEX_UNIT_SP, 8);
					v_LocalProperty.getTv_chartUnit().setText("元/m2");
					v_LocalProperty.getV_areacovered_bottomline()
							.setVisibility(View.INVISIBLE);

					v_LocalProperty.getV_volumerate_bottomline().setVisibility(
							View.INVISIBLE);

					v_LocalProperty.getV_opentime_bottomline().setVisibility(
							View.INVISIBLE);

					v_LocalProperty.getV_price_bottomline().setVisibility(
							View.VISIBLE);
					v_LocalProperty.getTv_areacovered()
							.setTextColor(Color.GRAY);
					v_LocalProperty.getTv_volumerate().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_opentime().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_price().setTextColor(Color.WHITE);
					messageDialog = new MyDialogView(getActivity());
					messageDialog.show();
					new Thread() {
						@Override
						public void run() {
							initChartView(0);
						}
					}.start();
				}
				break;

			case R.id.propertytable_title_areacovered:// 点击占地面积
				if (v_LocalProperty.getV_areacovered_bottomline()
						.getVisibility() == View.VISIBLE) {
					return;
				} else {
					v_LocalProperty.getTv_chartUnit().setTextSize(
							TypedValue.COMPLEX_UNIT_SP, 14);
					v_LocalProperty.getTv_chartUnit().setText("m2");
					v_LocalProperty.getV_price_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getV_areacovered_bottomline()
							.setVisibility(View.VISIBLE);
					v_LocalProperty.getV_volumerate_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getV_opentime_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getTv_areacovered().setTextColor(
							Color.WHITE);
					v_LocalProperty.getTv_volumerate().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_opentime().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_price().setTextColor(Color.GRAY);
					messageDialog = new MyDialogView(getActivity());
					messageDialog.show();
					new Thread() {
						@Override
						public void run() {
							initChartView(5);
						}
					}.start();
				}
				break;
			case R.id.propertytable_title_volumerate:// 点击容积率
				if (v_LocalProperty.getV_volumerate_bottomline()
						.getVisibility() == View.VISIBLE) {
					return;
				} else {
					v_LocalProperty.getTv_chartUnit().setTextSize(
							TypedValue.COMPLEX_UNIT_SP, 14);
					v_LocalProperty.getTv_chartUnit().setText("%");
					v_LocalProperty.getV_volumerate_bottomline().setVisibility(
							View.VISIBLE);
					v_LocalProperty.getV_areacovered_bottomline()
							.setVisibility(View.INVISIBLE);
					v_LocalProperty.getV_price_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getV_opentime_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getTv_areacovered()
							.setTextColor(Color.GRAY);
					v_LocalProperty.getTv_volumerate()
							.setTextColor(Color.WHITE);
					v_LocalProperty.getTv_opentime().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_price().setTextColor(Color.GRAY);
					messageDialog = new MyDialogView(getActivity());
					messageDialog.show();
					new Thread() {
						@Override
						public void run() {
							initChartView(4);
						}
					}.start();
				}
				break;
			case R.id.propertytable_title_opentime:// 点击开盘时间
				if (v_LocalProperty.getV_opentime_bottomline().getVisibility() == View.VISIBLE) {
					return;
				} else {
					v_LocalProperty.getTv_chartUnit().setTextSize(
							TypedValue.COMPLEX_UNIT_SP, 14);
					v_LocalProperty.getTv_chartUnit().setText("年");
					v_LocalProperty.getV_price_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getV_areacovered_bottomline()
							.setVisibility(View.INVISIBLE);
					v_LocalProperty.getV_volumerate_bottomline().setVisibility(
							View.INVISIBLE);
					v_LocalProperty.getV_opentime_bottomline().setVisibility(
							View.VISIBLE);
					v_LocalProperty.getTv_areacovered()
							.setTextColor(Color.GRAY);
					v_LocalProperty.getTv_volumerate().setTextColor(Color.GRAY);
					v_LocalProperty.getTv_opentime().setTextColor(Color.WHITE);
					v_LocalProperty.getTv_price().setTextColor(Color.GRAY);
					messageDialog = new MyDialogView(getActivity());
					messageDialog.show();
					new Thread() {
						@Override
						public void run() {
							initChartView(8);
						}
					}.start();
				}
				break;
			}

		}

	}

	/**
	 * 注册通知事件
	 */
	private void regiestReceiver() {
		getActivity()
				.registerReceiver(LocationUserReceiver, locationUserFilter);
	}

	/**
	 * 生成柱状图
	 * 
	 * @param type
	 */
	private synchronized void initChartView(int type) {
		/*		*//**
		 * 清空组状图状态
		 */

		Message del = new Message();
		del.what = -1;
		handler.sendMessage(del);

		/**
		 * 柱状图组件数组
		 */

		/**
		 * 柱状图数值数组
		 */
		double[] value = new double[w_LocalProperty.getDatasource().size()];
		/**
		 * 判断柱状图是否添加完成
		 */
		chartItems = new ArrayList<PropertyChartItem>();
		for (int i = 0; i < w_LocalProperty.getDatasource().size(); i++) {
			/**
			 * 新建显示柱状图
			 */
			PropertyChartItem item;

			if (chartItems.size() == 0 || chartItems.size() == i) {

				item = new PropertyChartItem(getActivity(), null);
				item.getTv_propertyName().setText(
						w_LocalProperty.getDatasource().get(i)
								.getProjjectName());
				switch (i % 5) {// 根据位置设置柱状图颜色
				case 0: {
					item.getV_char().setBackgroundResource(
							R.drawable.crocers_crolor_1);
				}
					break;

				case 1: {
					
					item.getV_char().setBackgroundResource(
							R.drawable.crocers_crolor_2);
				}
					break;
				case 2: {
					
					item.getV_char().setBackgroundResource(
							R.drawable.crocers_crolor_3);
				}
					break;
				case 3: {
					
					item.getV_char().setBackgroundResource(
							R.drawable.crocers_crolor_4);
				}
					break;
				case 4: {
					
					item.getV_char().setBackgroundResource(
							R.drawable.crocers_crolor_5);
				}
					break;
				}
				switch (type) {// 显示类型
				case 0: {// 价格

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getHousePrice())
							|| w_LocalProperty.getDatasource().get(i)
									.getHousePrice() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getHousePrice());
					}
					item.setValue(value[i]);
				}
					break;

				case 1:// 物业费

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getPropertyCost())
							|| w_LocalProperty.getDatasource().get(i)
									.getPropertyCost() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getPropertyCost());
					}
					item.setValue(value[i]);
					break;
				case 2:// 停车位

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getParkingSpace())
							|| w_LocalProperty.getDatasource().get(i)
									.getParkingSpace() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getParkingSpace());
					}
					item.setValue(value[i]);
					break;
				case 3:// 绿化率

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getGreeningRate())
							|| w_LocalProperty.getDatasource().get(i)
									.getGreeningRate() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getGreeningRate());
					}
					item.setValue(value[i]);
					break;
				case 4:// 公摊率

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getVolumeRate())
							|| w_LocalProperty.getDatasource().get(i)
									.getVolumeRate() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getVolumeRate());
					}
					item.setValue(value[i]);
					break;
				case 5:// 使用年限

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getAreaCoverednum())
							|| w_LocalProperty.getDatasource().get(i)
									.getAreaCoverednum() == 0) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getAreaCoverednum());
					}
					item.setValue(value[i]);
					break;
				case 6:// 建筑总面积

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getTotalAreacovered())
							|| w_LocalProperty.getDatasource().get(i)
									.getTotalAreacovered() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getTotalAreacovered());
					}
					item.setValue(value[i]);
					break;
				case 7:// 容积率

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getPoolRate())
							|| w_LocalProperty.getDatasource().get(i)
									.getPoolRate() == null) {
						value[i] = 0.000;
					} else {
						value[i] = Double.valueOf(w_LocalProperty
								.getDatasource().get(i).getPoolRate());
					}
					item.setValue(value[i]);
					break;
				case 8:// 开盘时间

					if ("".equals(w_LocalProperty.getDatasource().get(i)
							.getOpenTime())
							|| w_LocalProperty.getDatasource().get(i)
									.getOpenTime() == null) {
						value[i] = 0;
					} else {

						value[i] = Double
								.valueOf(w_LocalProperty.getDatasource().get(i)
										.getOpenTime().getYear() - 100);
					}
					/**
					 * 设置开盘时间为init，显示时数值加2000
					 */
					item.setValue((int) Math.rint((2000 + value[i])), 8);
					break;
				}
				chartItems.add(item);
			}

		}
		/**
		 * 柱状图显示的最高数值
		 */
		maxValue = getMax(getMaxAndMin(value));
		/**
		 * 柱状图组件的宽度，保证一个屏幕中显示5个
		 */
		Log.d("lrk", "v_LocalProperty.getHsv_chart().getWidth() = "
				+ v_LocalProperty.getHsv_chart().getWidth());
		chartWidth = v_LocalProperty.getHsv_chart().getWidth() * 9 / 50;
		/**
		 * 柱状图组件的显示最大高度
		 */
		chartMaxHeight = v_LocalProperty.getV_Ylabel().getHeight();

		if ((chartWidth * 50 / 9 != v_LocalProperty.getHsv_chart().getWidth())) {
			chartWidth = chartWidth + v_LocalProperty.getHsv_chart().getWidth()
					- chartWidth * 50 / 9;
		} else {
			chartWidth = v_LocalProperty.getHsv_chart().getWidth() * 9 / 50;
		}
		/**
		 * 如果显示的价格列表，而没有得到当前城市的均价时，不显示均价，按正常价格显示
		 */
		if (type == 0 && w_LocalProperty.getHouseFlod() != 0) {
			maxValue = w_LocalProperty.getHouseFlod() / 4 * 9;
			Message msg = new Message();
			msg.arg2 = -1;
			handler.sendMessage(msg);

		} else if (type == 8) {// 显示开盘时间时，设置上限为18，即2018年
			maxValue = 18;
			Message msg = new Message();
			msg.arg2 = type;
			handler.sendMessage(msg);

		} else {// 其他类型数据，根据显示上限设置每个刻度的值
			Message msg = new Message();
			msg.arg2 = type == 0 ? 100 : type;
			msg.obj = maxValue;
			handler.sendMessage(msg);
			maxValue = maxValue * 9 / 8;
		}
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		for (int postion = 0; postion < chartItems.size(); postion++) {
			LinearLayout.LayoutParams llyt_Params = new LinearLayout.LayoutParams(
					(chartWidth), LinearLayout.LayoutParams.MATCH_PARENT);

			chartItems.get(postion).setLayoutParams(llyt_Params);
			chartItems.get(postion).setGravity(Gravity.CENTER_HORIZONTAL);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					(chartWidth * 3 / 4),
					(int) (chartItems.get(postion).getValue() / maxValue * chartMaxHeight) >= chartMaxHeight ? chartMaxHeight
							: (int) (chartItems.get(postion).getValue()
									/ maxValue * chartMaxHeight));
			/**
			 * 设置柱状图的外边界宽度
			 */

			/**
			 * 设置柱状图始终在底部显示
			 */
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
					RelativeLayout.TRUE);
			chartItems.get(postion).getV_char().setLayoutParams(params);
			/**
			 * 设置柱状图显示的楼盘名称的布局属性，使名称始终居中显示
			 */
			/*
			 * RelativeLayout.LayoutParams pLayoutParams = new
			 * RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT,
			 * LayoutParams.WRAP_CONTENT);
			 * pLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
			 * RelativeLayout.TRUE);
			 * 
			 * chartItems.get(i).getTv_propertyName()
			 * .setLayoutParams(pLayoutParams);
			 */
			final PropertyChartItem item = chartItems.get(postion);
			Thread thread = new Thread() {
				@Override
				public void run() {

					try {
						Message msg = new Message();
						msg.obj = item;
						msg.what = 10;
						handler.sendMessage(msg);
						this.join();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			};
			thread.start();

		}

		/**
		 * 显示柱状图列表
		 */
		messageDialog.dismiss();

	}

	/**
	 * 得到最大值
	 * 
	 * @param score
	 *            求值数组
	 * @return
	 */
	private double getMaxAndMin(double[] score) {

		double max = 0;

		for (int i = 0; i < score.length; i++) {
			if (max <= score[i]) {
				max = score[i];
			}

		}

		return max;
	}

	/**
	 * 对数值进行优化，通过四舍五入的方式得到合理显示上限
	 * 
	 * @param max
	 * @return
	 */
	private double getMax(double max) {

		if (max >= 10000 && max < 100000) {
			max = Math.rint(max / 10000);
			max = max * 10000;

		} else if (max > 1000 && max < 10000) {
			max = Math.rint(max / 1000);
			max = max * 1000;
		} else if (max >= 100 && max < 1000) {
			max = Math.rint(max / 100);
			max = max * 100;
		} else if (max < 100 && max >= 10) {
			max = Math.rint(max / 10);
			max = max * 10;
		} else if (max >= 1 && max <= 10) {
			max = Math.rint(max);
		} else if (max >= 0 && max < 1) {
			max = 1;
		} else if (max >= 100000 && max < 1000000) {
			max = Math.rint(max / 100000);
			max = max * 100000;

		}
		return max;
	}

	@Override
	public void onResume() {

		super.onResume();

	}

	/**
	 * 显示柱状图级表格信息
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			/**
			 * 添加到柱状图显示组件中
			 */
			if (w_LocalProperty != null
					&& w_LocalProperty.getDatasource() != null) {
				/**
				 * 如果显示的价格列表，切没有得到当前城市的均价时，不显示均价，按正常价格显示
				 */
				if (msg.arg2 == -1 && w_LocalProperty.getHouseFlod() != 0) {
					v_LocalProperty.getTv_chart_Ylabel_one().setText("");
					v_LocalProperty.getTv_chart_Ylabel_three().setText("");
					v_LocalProperty.getTv_chart_Ylabel_four().setText("");
					v_LocalProperty.getTv_chart_Ylabel_two().setText(
							String.valueOf(w_LocalProperty.getHouseFlod()));

				} else if (msg.arg2 == 8) {// 显示开盘时间时，设置上限为18，即2018年

					v_LocalProperty.getTv_chart_Ylabel_one().setText("2004");
					v_LocalProperty.getTv_chart_Ylabel_two().setText("2008");
					v_LocalProperty.getTv_chart_Ylabel_three().setText("2012");
					v_LocalProperty.getTv_chart_Ylabel_four().setText("2016");

				} else if (msg.arg2 > 0) {// 其他类型数据，根据显示上限设置每个刻度的值
					v_LocalProperty.getTv_chart_Ylabel_one().setText(
							String.valueOf(((Double) msg.obj) / 4));
					v_LocalProperty.getTv_chart_Ylabel_two().setText(
							String.valueOf(((Double) msg.obj) / 2));
					v_LocalProperty.getTv_chart_Ylabel_three().setText(
							String.valueOf(((Double) msg.obj) * 3 / 4));
					v_LocalProperty.getTv_chart_Ylabel_four().setText(
							String.valueOf(((Double) msg.obj)));

				}
				if (msg.what == 10) {
					View view = (View) msg.obj;
					v_LocalProperty.getLlyt_chartContent().addView(view);

				} else if (msg.what == 1) {
					View view = (View) msg.obj;
					v_LocalProperty.getLlyt_tablecontent().addView(view);// 添加组件

				} else if (msg.what == -1) {
					v_LocalProperty.getLlyt_chartContent().removeAllViews();
				} else if (msg.what == -2) {
					v_LocalProperty.getLlyt_tablecontent().removeAllViews();
				}
				v_LocalProperty.getView().postInvalidate();
			} else {
				Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT)
						.show();
			}

		};
	};

	/**
	 * 注销广播接收器
	 */
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(LocationUserReceiver);
		pointSearch.destory();
		super.onDestroy();
	}

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
				String str = String.format("错误号：%d", errorCode);
				Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
				return;
			}
			if (res.type == MKAddrInfo.MK_GEOCODE) {
				// 地理编码：通过地址检索坐标点
				/**
				 * 地址经度
				 */
				latitude = res.geoPt.getLatitudeE6() / 1e6;
				/**
				 * 地址纬度
				 */
				longitude = res.geoPt.getLongitudeE6() / 1e6;
				System.out.println("经度 = " + latitude + "纬度 =" + longitude);
				v_LocalProperty.getLlyt_chartContent().removeAllViews();
				Handler adressHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						Thread propertyAction = new Thread(new TLocalProperty(
								getActivity(), new PropertyActionHandler(),
								latitude, longitude, shi));
						propertyAction.start();
						Thread plotThread = new Thread(new TPlotAnalysis(getActivity(), new PlotAnalysisHanlder(), latitude, longitude, shi, qu));
						plotThread.start();
					}
				};
				adressHandler.sendEmptyMessageDelayed(0, 1000);

			}
			if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
				// 反地理编码：通过坐标点检索详细地址及周边poi
				Log.d("lrk", "通过坐标点检索详细地址");
				MKGeocoderAddressComponent kk = res.addressComponents;
				coordinateName = res.strAddr;
				shi = kk.city;//所在城市
				qu = kk.district;//所在辖区
				v_LocalProperty.getTv_cityName().setText(shi);
				latitude = res.geoPt.getLatitudeE6() / 1e6;
				/**
				 * 地址纬度
				 */
				longitude = res.geoPt.getLongitudeE6() / 1e6;
				Thread thread = new Thread(new TLocalProperty(getActivity(),
						new PropertyActionHandler(), latitude, longitude, shi));
				thread.start();
				Thread plotThread = new Thread(new TPlotAnalysis(getActivity(), new PlotAnalysisHanlder(), latitude, longitude, shi, qu));
				plotThread.start();
				
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
	 * 楼盘基本信息后台信息解析
	 * 
	 * @author Administrator 2013-12-10下午02:51:46
	 */
	private class PropertyActionHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.d("lrk", "申请数据完毕");
			if (msg.getData().getString("result").endsWith("true")) {
				/**
				 * 获取楼盘信息
				 */
				w_LocalProperty = TLocalProperty.getPaserJsonResult(msg
						.getData().getString("jsonResult"));
				/**
				 * 获取楼盘信息后柱状图显示的信息默认回到显示第一个列，显示价格。
				 */
				v_LocalProperty.getTv_chartUnit().setText("元/m2");
				v_LocalProperty.getV_areacovered_bottomline().setVisibility(
						View.INVISIBLE);

				v_LocalProperty.getV_volumerate_bottomline().setVisibility(
						View.INVISIBLE);

				v_LocalProperty.getV_opentime_bottomline().setVisibility(
						View.INVISIBLE);

				v_LocalProperty.getV_price_bottomline().setVisibility(
						View.VISIBLE);
				v_LocalProperty.getTv_areacovered().setTextColor(Color.GRAY);
				v_LocalProperty.getTv_volumerate().setTextColor(Color.GRAY);
				v_LocalProperty.getTv_opentime().setTextColor(Color.GRAY);
				v_LocalProperty.getTv_price().setTextColor(Color.WHITE);
				messageDialog.dismiss();
				/**
				 * 弹出提示框
				 */
				messageDialog = new MyDialogView(getActivity());
				messageDialog.show();

				/**
				 * 显示价格柱状图信息
				 * 
				 */

				new Thread() {
					public void run() {
						initChartView(0);
					}
				}.start();
				/**
				 * 表格信息
				 */

				new Thread() {
					public void run() {
						initTable();

					}
				}.start();

			} else {
				Toast.makeText(getActivity(), "网络链接错误", Toast.LENGTH_SHORT)
						.show();
				messageDialog.dismiss();
			}

		}
	}
	/**
	 * 楼市分析回调
	 * @ClassName: PlotAnalysisHanlder 
	 * @Description: TODO 
	 * @author lrk
	 * @date 2014-3-10 下午4:56:00 
	 *
	 */
	private class PlotAnalysisHanlder extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.getData().getString("result").endsWith("true")) {
				W_PlotAnalysis plotAnalysis =	TPlotAnalysis.getParesJson(msg
							.getData().getString("jsonResult"));
				v_LocalProperty.getTv_CityandArea().setText(shi+"-"+qu);
				v_LocalProperty.getTv_Areaprice().setText(String.valueOf(plotAnalysis.getDatasource().getAreaprice())+"元/m2");
				v_LocalProperty.getTv_Cityprice().setText(String.valueOf(plotAnalysis.getDatasource().getCityprice())+"元/m2");
				v_LocalProperty.getTv_Areaaoinum().setText(String.valueOf(plotAnalysis.getDatasource().getAreaaoinum())+"%");
				v_LocalProperty.getTv_Cityaoinum().setText(String.valueOf(plotAnalysis.getDatasource().getCityaoinum())+"%");
				v_LocalProperty.getTv_Areamaxprice().setText(String.valueOf(plotAnalysis.getDatasource().getAreamaxprice())+"元/m2");
				v_LocalProperty.getTv_Areaminprice().setText(String.valueOf(plotAnalysis.getDatasource().getAreaminprice())+"元/m2");
			
			}
			
		}
	}
	/**
	 * 实例化表格信息
	 */
	private synchronized void initTable() {
		/**
		 * 添加表格信息
		 */
		Message del = new Message();
		del.what = -2;
		handler.sendMessage(del);

		if (tableItems == null)
			tableItems = new ArrayList<PropertyTableItem>();
		for (int i = 0; i < w_LocalProperty.getDatasource().size(); i++) {
			/**
			 * 实例化表格组组件
			 */

			PropertyTableItem item = new PropertyTableItem(getActivity(), null);
			initTableItem(item, w_LocalProperty.getDatasource().get(i));
			Message msg = new Message();
			msg.what = 1;
			msg.obj = item;
			handler.sendMessage(msg);
			tableItems.add(item);

		}
		/*
		 * if(tableItems.size()>w_LocalProperty.getDatasource().size()) { for
		 * (int i = tableItems.size(); i
		 * >w_LocalProperty.getDatasource().size(); i--) { tableItems.remove(i);
		 * v_LocalProperty.getLlyt_tablecontent().removeViewAt(i); } }
		 */
		messageDialog.dismiss();

	}

	/**
	 * 处理表格信息
	 * 
	 * @param tableItem
	 *            //表格组件
	 * @param info
	 *            //表格信息
	 */
	private void initTableItem(PropertyTableItem tableItem, W_PropertyInfo info) {
		tableItem.getHourseName().setText(info.getProjjectName());
		/**
		 * 写入表格信息
		 */
		if (info.getAreaYear() != null// 是否大产权房
				&& !info.getAreaYear().equals("")) {
			if (info.getAreaYear().contains("70")
					|| info.getAreaYear().contains("大产权")) {

			} else {
				tableItem.getDachanquanfang().setBackgroundResource(
						R.drawable.no);
			}

		} else {
			tableItem.getDachanquanfang().setBackgroundResource(R.drawable.no);
		}
		if (info.getDecorationStatus() != null// 是否精装
				&& !info.getDecorationStatus().equals("")) {
			tableItem
					.getJingzhuang()
					.setBackgroundResource(
							info.getDecorationStatus().indexOf("精装") != -1 ? R.drawable.has
									: R.drawable.no);
		} else {
			tableItem.getJingzhuang().setBackgroundResource(R.drawable.no);
		}
		if (info.getBuyType() != null// 是否在售
				&& !info.getBuyType().equals("")) {
			tableItem.getInscale().setBackgroundResource(
					info.getBuyType().indexOf("在售") != -1 ? R.drawable.has
							: R.drawable.no);
		} else {
			tableItem.getInscale().setBackgroundResource(R.drawable.no);
		}
		if (info.getBuildType() != null// 是否高层
				&& !info.getBuildType().equals("")) {
			tableItem.getGaoceng().setBackgroundResource(
					info.getBuildType().indexOf("高层") != -1 ? R.drawable.has
							: R.drawable.no);
		} else {
			tableItem.getGaoceng().setBackgroundResource(R.drawable.no);
		}
		int width = v_LocalProperty.getHsv_table()// 设置每一列的宽度，使屏幕始终只显示5列
				.getWidth() / 5;
		width = width * 9 / 10;
		tableItem.getLlyt_content().setMinimumWidth(width);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		tableItem.setLayoutParams(params);
	}

	/**
	 * 楼盘地图点击事件
	 * 
	 * @author lrk 2013-12-10下午03:39:33
	 */
	private class PropertyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (w_LocalProperty == null
					|| w_LocalProperty.getDatasource() == null) {
				Toast.makeText(getActivity(), "没有数据可以显示", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			messageDialog = new MyDialogView(getActivity());
			messageDialog.show();
			Intent intent = new Intent(getActivity(), Activity_BaiDuMap.class);
			if (w_LocalProperty == null) {
				Toast.makeText(getActivity(), "这附近没有楼盘信息", 300).show();
				return;
			}
			intent.putExtra("propertyList", w_LocalProperty);

			intent.putExtra("latitude", latitude);

			intent.putExtra("longitude", longitude);

			intent.putExtra("qu", coordinateName);

			getActivity().startActivity(intent);

			getActivity().overridePendingTransition(
					R.anim.fragment_right_in, R.anim.fragment_left_out);

		}

	}

	/**
	 * 坐标收藏点击事件
	 * 
	 * @author lrk 2013-12-16下午02:18:40
	 */
	private class CollectionCoordinateListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (toast != null) {
				toast.cancel();
			}
			toast = new CenterToast(getActivity(), "收藏成功");
			toast.show();
			Thread thread = new Thread(new TCollectCoordinate(getActivity(),
					new CollectionCoordinateHandler(), latitude, longitude,
					coordinateName, shi));
			thread.start();

		}
	}

	/**
	 * 坐标收藏信息解析类
	 * 
	 * @author lrk 2013-12-16下午02:23:16
	 */
	private class CollectionCoordinateHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.getData().getString("result").equals("true")) {

			} else {
				Toast.makeText(getActivity(), "操作失败", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	/**
	 * 用最新的截图替换原本的图片
	 */
	/*
	 * private void setCurrentMap(String fileName,int type) { File file = new
	 * File(S_ActionInfo.getSDPath()+fileName);
	 * System.out.println("type = "+type); try { if (file.exists()) {
	 * Log.d("lrk", "file.exists()"); Bitmap bitmap =
	 * BitmapFactory.decodeFile(file.getPath()); // 获取这个图片的宽和高 int width =
	 * bitmap.getWidth(); int height = bitmap.getHeight();
	 * 
	 * // 定义预转换成的图片的宽度和高度 int newWidth = getResources().getDrawable(
	 * R.drawable.property_content).getIntrinsicWidth(); int newHeight =
	 * getResources().getDrawable(
	 * R.drawable.property_content).getIntrinsicHeight();
	 * 
	 * // 计算缩放率，新尺寸除原始尺寸 float scaleWidth = ((float) newWidth) / width; float
	 * scaleHeight = ((float) newHeight) / height;
	 * 
	 * // 创建操作图片用的matrix对象 Matrix matrix = new Matrix();
	 * 
	 * // 缩放图片动作 matrix.postScale(scaleWidth, scaleHeight);
	 * 
	 * // 创建新的图片 Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
	 * height, matrix, true);
	 * 
	 * BitmapDrawable drawable = toRoundBitmap(resizedBitmap); switch (type) {
	 * case 0: v_LocalProperty.getIv_Property().setImageDrawable(drawable);
	 * break;
	 * 
	 * case 1: v_LocalProperty.getIv_lot().setImageDrawable(drawable); break; }
	 * 
	 * 
	 * } } catch (Exception e) {
	 * v_LocalProperty.getIv_Property().setImageDrawable(
	 * getResources().getDrawable(R.drawable.property_content));
	 * 
	 * } }
	 */

	/**
	 ** 转换图片成圆形
	 ** 
	 * @param bitmap
	 *            传入Bitmap对象* @return          
	 */
	public BitmapDrawable toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;

		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return new BitmapDrawable(output);
	}

	/**
	 * 
	 * @ClassName: LotClickListener
	 * @Description: TODO 地块图片点击事件
	 * @author lrk
	 * @date 2014-1-3 上午11:05:22
	 * 
	 */
	private class LotClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Thread thread = new Thread(new TNearLand(getActivity(),
					new LotMessageHandler(), latitude, longitude));
			thread.start();
		}

	}

	/**
	 * 
	 * @ClassName: LotMessageHandler
	 * @Description: TODO 解析地块信息，并跳转到地块的
	 * @author lrk
	 * @date 2014-1-3 下午2:16:20
	 * 
	 */
	private class LotMessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			/**
			 * 成功获取信息
			 */
			if (msg.getData().getString("result").equals("true")) {
				W_NearLand w_NearLand = TNearLand.getParsingJson(msg.getData()
						.getString("jsonResult"));
				/**
				 * 检查数据量，是否能够现实信息
				 */
				LogCat.delFile();
				LogCat.v("地块信息：", "地块信息数量： "
						+ w_NearLand.getDatasource().size());
				if (w_LocalProperty != null
						&& w_LocalProperty.getDatasource().size() != 0) {
					messageDialog = new MyDialogView(getActivity());
					messageDialog.show();
					Intent intent = new Intent(getActivity(),
							Activity_LotBaiduMap.class);

					intent.putExtra("LotList", w_NearLand);
					LogCat.v("经度坐标：", latitude);
					intent.putExtra("latitude", latitude);
					LogCat.v("维度坐标：", longitude);
					intent.putExtra("longitude", longitude);
					intent.putExtra("location", coordinateName);
					startActivity(intent);
					getActivity().overridePendingTransition(
							R.anim.fragment_right_in, R.anim.fragment_left_out);
				} else {
					Toast.makeText(getActivity(), "地图附近没有地块信息",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(getActivity(), "网络连接错误，无法获取信息",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/*
	 * private Bitmap getBitmap(View view) { view.measure(0,0); view.layout(0,
	 * 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	 * view.buildDrawingCache(); Bitmap bitmap = view.getDrawingCache();
	 * 
	 * return bitmap; }
	 */
	private Bitmap getViewBitmap(View view) {

		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (messageDialog != null && messageDialog.isShowing()) {
			messageDialog.dismiss();
		}
	}
	private class ScrollViewRefreshListtener implements OnRefreshListener<ScrollView>{

		@Override
		public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			Bundle bundle = new Bundle();
			bundle.putBoolean("isGeoCode", true);
			setLocation(bundle);
			refreshView.onRefreshComplete();
			
		}
	}
	

}
