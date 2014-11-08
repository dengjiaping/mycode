package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.wufangnao.R;

/**
 * 楼盘与地块组件封装类
 * 
 * @author lrk 2013-12-10上午11:37:11
 */
public class View_localProperty {

	private View v_localProperty;

	private ImageView iv_Property;

	private LinearLayout llyt_CollectionProperty;

	private ImageView iv_lot;

	private View v_include_propertyTable;

	private RelativeLayout llyt_propertymsg_titleLayout;

	private LinearLayout llyt_chartlayout;

	private TextView tv_chartUnit;

	private HorizontalScrollView hsv_list;

	private HorizontalScrollView hsv_table;

	private HorizontalScrollView hsv_chart;

	private View v_left;

	private View v_right;

	private TextView tv_price;

	private TextView tv_propertycost;

	private TextView tv_parkingspace;

	private TextView tv_greeningrate;

	private TextView tv_volumerate;

	private TextView tv_areacovered;

	private TextView tv_totalareacovered;

	private TextView tv_poolrate;

	private TextView tv_opentime;

	private LinearLayout vp_chartlist;

	private LinearLayout llyt_tablecontent;

	private View v_leftLeng;

	private View v_Ylabel;

	private View v_price_bottomline;

	private View v_volumerate_bottomline;

	private View v_areacovered_bottomline;

	private View v_opentime_bottomline;

	private View v_include_chartlayout;

	private LinearLayout llyt_chartContent;

	private TextView tv_chart_Ylabel_four;

	private TextView tv_chart_Ylabel_three;

	private TextView tv_chart_Ylabel_two;

	private TextView tv_chart_Ylabel_one;

	private PullToRefreshScrollView ptrsv_ScrollView;

	/**
	 * 顶部菜单按钮
	 */
	private ImageView iv_menu;
	/**
	 * 顶部定位按钮
	 */
	private LinearLayout llyt_location;
	/**
	 * 城市名称
	 */
	private TextView tv_cityName;

	private View v_PlotAnalysis;

	private TextView tv_CityandArea;

	private TextView tv_Areaprice;
	private TextView tv_Cityprice;
	private TextView tv_Areaaoinum;
	private TextView tv_Cityaoinum;
	private TextView tv_Areamaxprice;
	private TextView tv_Areaminprice;

	public View_localProperty(Context context) {
		v_localProperty = LayoutInflater.from(context).inflate(
				R.layout.localproperty_layout, null);

		iv_Property = (ImageView) v_localProperty
				.findViewById(R.id.iv_property);

		llyt_CollectionProperty = (LinearLayout) v_localProperty
				.findViewById(R.id.llyt_collectionproperty);

		iv_lot = (ImageView) v_localProperty.findViewById(R.id.lot_frame);
		llyt_location = (LinearLayout) v_localProperty
				.findViewById(R.id.llyt_position);
		iv_menu = (ImageView) v_localProperty.findViewById(R.id.iv_menu);
		tv_cityName = (TextView) v_localProperty
				.findViewById(R.id.tv_postion_name);
		ptrsv_ScrollView = (PullToRefreshScrollView) v_localProperty
				.findViewById(R.id.pull_refresh_scrollview);
		ptrsv_ScrollView.setMode(Mode.PULL_FROM_START);

		v_include_propertyTable = v_localProperty
				.findViewById(R.id.property_message_table);
		v_PlotAnalysis = v_localProperty
				.findViewById(R.id.property_price_layout);

		tv_CityandArea = (TextView) v_PlotAnalysis
				.findViewById(R.id.price_userlocation);
		tv_Areaprice = (TextView) v_PlotAnalysis
				.findViewById(R.id.local_hoursprices);
		tv_Cityprice = (TextView) v_PlotAnalysis
				.findViewById(R.id.city_hoursprices);
		tv_Areaaoinum = (TextView) v_PlotAnalysis
				.findViewById(R.id.local_hoursprices_upordown);
		tv_Cityaoinum = (TextView) v_PlotAnalysis
				.findViewById(R.id.city_hoursprices_upordown);
		tv_Areamaxprice = (TextView) v_PlotAnalysis
				.findViewById(R.id.local_hoursprices_maxprice);
		tv_Areaminprice = (TextView) v_PlotAnalysis
				.findViewById(R.id.local_hoursprices_minprice);

		llyt_propertymsg_titleLayout = (RelativeLayout) v_include_propertyTable
				.findViewById(R.id.llyt_propertymsg_title);

		llyt_chartlayout = (LinearLayout) v_include_propertyTable
				.findViewById(R.id.llyt_chartlayout);

		tv_chartUnit = (TextView) v_include_propertyTable
				.findViewById(R.id.tv_chartunit);

		hsv_list = (HorizontalScrollView) v_include_propertyTable
				.findViewById(R.id.hsv_list);

		tv_price = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_price);
		tv_propertycost = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_propertycost);

		tv_parkingspace = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_parkingspace);

		tv_greeningrate = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_greeningrate);

		tv_volumerate = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_volumerate);

		tv_areacovered = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_areacovered);

		tv_totalareacovered = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_totalareacovered);

		tv_poolrate = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_poolrate);

		tv_opentime = (TextView) v_include_propertyTable
				.findViewById(R.id.propertytable_title_opentime);

		vp_chartlist = (LinearLayout) v_include_propertyTable
				.findViewById(R.id.vp_chartlist);

		llyt_tablecontent = (LinearLayout) v_include_propertyTable
				.findViewById(R.id.llyt_tablecontent);

		v_leftLeng = (View) v_include_propertyTable
				.findViewById(R.id.v_leftleng);

		v_price_bottomline = (View) v_include_propertyTable
				.findViewById(R.id.v_price_bottomline);

		v_volumerate_bottomline = (View) v_include_propertyTable
				.findViewById(R.id.v_volumerate_bottomline);

		v_areacovered_bottomline = (View) v_include_propertyTable
				.findViewById(R.id.v_areacovered_bottomline);

		v_opentime_bottomline = (View) v_include_propertyTable
				.findViewById(R.id.v_opentime_bottomline);

		hsv_table = (HorizontalScrollView) v_include_propertyTable
				.findViewById(R.id.hsv_tablehsv);

		v_include_chartlayout = (View) v_localProperty
				.findViewById(R.id.vp_chartlist);

		llyt_chartContent = (LinearLayout) v_include_chartlayout
				.findViewById(R.id.llyt_propert_chart_content);

		hsv_chart = (HorizontalScrollView) v_include_chartlayout
				.findViewById(R.id.hsv_chart);

		tv_chart_Ylabel_four = (TextView) v_include_chartlayout
				.findViewById(R.id.tv_chart_Ylabel_four);

		tv_chart_Ylabel_three = (TextView) v_include_chartlayout
				.findViewById(R.id.tv_chart_Ylabel_three);

		tv_chart_Ylabel_two = (TextView) v_include_chartlayout
				.findViewById(R.id.tv_chart_Ylabel_two);

		tv_chart_Ylabel_one = (TextView) v_include_chartlayout
				.findViewById(R.id.tv_chart_Ylabel_one);

		v_Ylabel = (View) v_include_chartlayout
				.findViewById(R.id.v_chart_YLable);

	}

	public View getV_Ylabel() {
		return v_Ylabel;
	}

	public View getV_include_propertyTable() {
		return v_include_propertyTable;
	}

	public HorizontalScrollView getHsv_chart() {
		return hsv_chart;
	}

	public View getV_include_chartlayout() {
		return v_include_chartlayout;
	}

	public LinearLayout getLlyt_chartContent() {
		return llyt_chartContent;
	}

	public TextView getTv_chart_Ylabel_four() {
		return tv_chart_Ylabel_four;
	}

	public TextView getTv_chart_Ylabel_three() {
		return tv_chart_Ylabel_three;
	}

	public TextView getTv_chart_Ylabel_two() {
		return tv_chart_Ylabel_two;
	}

	public TextView getTv_chart_Ylabel_one() {
		return tv_chart_Ylabel_one;
	}

	public HorizontalScrollView getHsv_table() {
		return hsv_table;
	}

	public View getV_price_bottomline() {
		return v_price_bottomline;
	}

	public View getV_volumerate_bottomline() {
		return v_volumerate_bottomline;
	}

	public View getV_areacovered_bottomline() {
		return v_areacovered_bottomline;
	}

	public View getV_opentime_bottomline() {
		return v_opentime_bottomline;
	}

	public View getView() {
		return v_localProperty;
	}

	public ImageView getIv_Property() {
		return iv_Property;
	}

	public void setIv_Property(ImageView iv_Property) {
		this.iv_Property = iv_Property;
	}

	public LinearLayout getIv_CollectionProperty() {
		return llyt_CollectionProperty;
	}

	public void setIv_CollectionProperty(LinearLayout iv_CollectionProperty) {
		this.llyt_CollectionProperty = iv_CollectionProperty;
	}

	public ImageView getIv_lot() {
		return iv_lot;
	}

	public void setIv_lot(ImageView iv_lot) {
		this.iv_lot = iv_lot;
	}

	public RelativeLayout getLlyt_propertymsg_titleLayout() {
		return llyt_propertymsg_titleLayout;
	}

	public TextView getTv_chartUnit() {
		return tv_chartUnit;
	}

	public HorizontalScrollView getHsv_list() {
		return hsv_list;
	}

	public View getV_left() {
		return v_left;
	}

	public View getV_right() {
		return v_right;
	}

	public TextView getTv_price() {
		return tv_price;
	}

	public TextView getTv_propertycost() {
		return tv_propertycost;
	}

	public TextView getTv_parkingspace() {
		return tv_parkingspace;
	}

	public TextView getTv_greeningrate() {
		return tv_greeningrate;
	}

	public TextView getTv_volumerate() {
		return tv_volumerate;
	}

	public TextView getTv_areacovered() {
		return tv_areacovered;
	}

	public TextView getTv_totalareacovered() {
		return tv_totalareacovered;
	}

	public TextView getTv_poolrate() {
		return tv_poolrate;
	}

	public TextView getTv_opentime() {
		return tv_opentime;
	}

	public LinearLayout getVp_chartlist() {
		return vp_chartlist;
	}

	public LinearLayout getLlyt_tablecontent() {
		return llyt_tablecontent;
	}

	public View getV_leftLeng() {
		return v_leftLeng;
	}

	public View getv_include_propertyTable() {
		return v_include_propertyTable;
	}

	public LinearLayout getLlyt_chartlayout() {
		return llyt_chartlayout;
	}

	public ImageView getIv_menu() {
		return iv_menu;
	}

	public LinearLayout getLlyt_location() {
		return llyt_location;
	}

	public TextView getTv_cityName() {
		return tv_cityName;
	}

	public PullToRefreshScrollView getPtrsv_ScrollView() {
		return ptrsv_ScrollView;
	}

	public LinearLayout getLlyt_CollectionProperty() {
		return llyt_CollectionProperty;
	}

	public TextView getTv_CityandArea() {
		return tv_CityandArea;
	}

	public TextView getTv_Areaprice() {
		return tv_Areaprice;
	}

	public TextView getTv_Cityprice() {
		return tv_Cityprice;
	}

	public TextView getTv_Areaaoinum() {
		return tv_Areaaoinum;
	}

	public TextView getTv_Cityaoinum() {
		return tv_Cityaoinum;
	}

	public TextView getTv_Areamaxprice() {
		return tv_Areamaxprice;
	}

	public TextView getTv_Areaminprice() {
		return tv_Areaminprice;
	}
	
}
