package com.wufangnao.adapter.item;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wufangnao.R;
import com.wufangnao.item.CityListItem;

public class CityItem {

	private Context context;
	
	private TextView tv_cityName;
	
	private View view;
	
	private TextView tv_catalog;
	
	private ImageView iv_cityclick;
	
	public CityItem(Context context) {
		
		view=LayoutInflater.from(context).inflate(R.layout.citylist_layout, null);
		
		tv_cityName = (TextView)view.findViewById(R.id.tv_cityname);
		
		tv_catalog = (TextView)view.findViewById(R.id.tv_catalog);
		
		iv_cityclick = (ImageView)view.findViewById(R.id.iv_cityclick);
		
	}
	public View getView()
	{
		return view;
	}
	public TextView getTv_cityName() {
		return tv_cityName;
	}

	public void setTv_cityName(TextView tv_cityName) {
		this.tv_cityName = tv_cityName;
	}

	public TextView getTv_catalog() {
		return tv_catalog;
	}

	public void setTv_catalog(TextView tv_catalog) {
		this.tv_catalog = tv_catalog;
	}

	public ImageView getIv_cityclick() {
		return iv_cityclick;
	}

	public void setIv_cityclick(ImageView iv_cityclick) {
		this.iv_cityclick = iv_cityclick;
	}
	

}
