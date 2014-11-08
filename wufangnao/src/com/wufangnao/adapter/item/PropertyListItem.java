package com.wufangnao.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wufangnao.R;
import com.wufangnao.webInterface.mode.W_PropertyInfo;
/**
 * 楼盘地图列表
 * @ClassName: PropertyListItem 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:55:59 
 *
 */
public class PropertyListItem extends LinearLayout {
	private TextView tv_propertyName;
	private TextView tv_propertyAddress;
	private TextView tv_collectionDate;
	private TextView tv_phoneNumber;
	private TextView tv_price;
	public PropertyListItem(Context context,AttributeSet att) {
		super(context,att);
		LayoutInflater.from(context).inflate(R.layout.propertylsit_item, this, true);
		tv_propertyName = (TextView) findViewById(R.id.tv_propertylsit_propertyname);
		tv_propertyAddress = (TextView) findViewById(R.id.tv_propertylsit_propertyaddress);
		tv_collectionDate = (TextView) findViewById(R.id.tv_propertylsit_collectiondate);
		tv_phoneNumber = (TextView) findViewById(R.id.tv_propertylsit_phonenumber);
		tv_price = (TextView) findViewById(R.id.tv_propertylsit_price);
		
	}
	public void initValue(W_PropertyInfo info)
	{
		if(!info.getProjjectName().equals(""))
		{
			Log.d("lrk", "getProjjectName() = "+info.getProjjectName());
			tv_propertyName.setText(info.getProjjectName());			
			tv_propertyName.setVisibility(View.VISIBLE);
		}
		else {
			tv_propertyName.setVisibility(View.INVISIBLE);
		}
		if(!info.getBuildType().equals(""))
		{
			Log.d("lrk", "getCity() = "+info.getCitytown());
			tv_propertyAddress.setText(info.getBuildType());			
			tv_propertyAddress.setVisibility(View.VISIBLE);
		}
		else {
			tv_propertyAddress.setVisibility(View.INVISIBLE);
		}
		if(!info.getHousePrice().equals(""))
		{
			Log.d("lrk", "getHousePrice() = "+info.getHousePrice());
			tv_price.setText(info.getHousePrice());			
			tv_price.setVisibility(View.VISIBLE);
		}
		else {
			tv_price.setVisibility(View.INVISIBLE);
		}
		if(info.getDecorationStatus()!=null&&!info.getDecorationStatus().equals(""))
		{
			Log.d("lrk", "getTelephoneSales() = "+info.getDecorationStatus());
			tv_phoneNumber.setText(info.getDecorationStatus());			
			tv_phoneNumber.setVisibility(View.VISIBLE);
		}
		else {
			tv_phoneNumber.setVisibility(View.INVISIBLE);
		}
		if(info.getShowopenTime()!=null&&!info.getShowopenTime().equals(""))
		{
			Log.d("lrk", "getShowopenTime() = "+info.getShowopenTime());
			tv_collectionDate.setText(info.getShowopenTime().toString());
			tv_collectionDate.setVisibility(View.VISIBLE);
		}else {
			tv_collectionDate.setVisibility(View.INVISIBLE);
		}
		Log.d("lrk", "getCity() = "+info.getCity());
	}

}
