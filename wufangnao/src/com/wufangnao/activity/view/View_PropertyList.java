package com.wufangnao.activity.view;

import com.wufangnao.R;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 楼盘列表界面
 * @ClassName: View_PropertyList 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:51:09 
 *
 */
public class View_PropertyList {

	private View v_PorpertyList;

	private ImageView iv_Back;

	private ImageView iv_colseview;

	private ImageView iv_selectbutton;

	private TextView tv_propertylist_mylocation;

	private ViewPager vp_property_content;
	
	private ListView lv_propertylsit_content;
	
	private LinearLayout llyt_propertymsg;

	public View_PropertyList(Context context)
	{
		v_PorpertyList = LayoutInflater.from(context).inflate(R.layout.propertylist_layout,null);
		
		iv_Back = (ImageView)v_PorpertyList.findViewById(R.id.iv_propertylist_back);
		
		iv_colseview = (ImageView)v_PorpertyList.findViewById(R.id.iv_colseview);
		
		iv_selectbutton = (ImageView)v_PorpertyList.findViewById(R.id.iv_selectbutton);
		
		tv_propertylist_mylocation = (TextView)v_PorpertyList.findViewById(R.id.tv_propertylist_mylocation);
		
		vp_property_content = (ViewPager)v_PorpertyList.findViewById(R.id.vp_property_content);
		
		lv_propertylsit_content = (ListView)v_PorpertyList.findViewById(R.id.lv_propertylsit_content);
		
		llyt_propertymsg = (LinearLayout)v_PorpertyList.findViewById(R.id.llyt_propertymsg);
		
	}
	public View getView()
	{
		return v_PorpertyList;
	}
	public ImageView getIv_Back() {
		return iv_Back;
	}

	public void setIv_Back(ImageView iv_Back) {
		this.iv_Back = iv_Back;
	}

	public ImageView getIv_colseview() {
		return iv_colseview;
	}

	public void setIv_colseview(ImageView iv_colseview) {
		this.iv_colseview = iv_colseview;
	}

	public ImageView getIv_selectbutton() {
		return iv_selectbutton;
	}

	public void setIv_selectbutton(ImageView iv_selectbutton) {
		this.iv_selectbutton = iv_selectbutton;
	}

	public TextView getTv_propertylist_mylocation() {
		return tv_propertylist_mylocation;
	}

	public void setTv_propertylist_mylocation(TextView tv_propertylist_mylocation) {
		this.tv_propertylist_mylocation = tv_propertylist_mylocation;
	}

	public ViewPager getVp_property_content() {
		return vp_property_content;
	}

	public void setVp_property_content(ViewPager vp_property_content) {
		this.vp_property_content = vp_property_content;
	}
	public ListView getLv_propertylsit_content() {
		return lv_propertylsit_content;
	}
	public void setLv_propertylsit_content(ListView lv_propertylsit_content) {
		this.lv_propertylsit_content = lv_propertylsit_content;
	}
	public LinearLayout getLlyt_propertymsg() {
		return llyt_propertymsg;
	}
	public void setLlyt_propertymsg(LinearLayout llyt_propertymsg) {
		this.llyt_propertymsg = llyt_propertymsg;
	}
	
	
}
