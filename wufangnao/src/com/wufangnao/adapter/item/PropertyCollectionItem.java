package com.wufangnao.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wufangnao.R;
import com.wufangnao.webInterface.mode.W_PropertyCollection_Collection;
/**
 * 楼盘收藏
 * @ClassName: PropertyCollectionItem 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:55:38 
 *
 */
public class PropertyCollectionItem extends LinearLayout {
	private TextView tv_propertyName;
	private TextView tv_propertyAddress;
	private TextView tv_collectionDate;
	private TextView tv_phoneNumber;
	private TextView tv_price;
	private ImageButton ib_del;
	

	public PropertyCollectionItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.propertycollection_item_layout, this, true);
		tv_propertyName = (TextView) findViewById(R.id.tv_propertyname);
		tv_propertyAddress = (TextView) findViewById(R.id.tv_propertyaddress);
		tv_collectionDate = (TextView) findViewById(R.id.tv_collectiondate);
		tv_phoneNumber = (TextView) findViewById(R.id.tv_phonenumber);
		tv_price = (TextView) findViewById(R.id.tv_price);
		ib_del = (ImageButton)findViewById(R.id.ib_deldata);
	}
	public void initValue(W_PropertyCollection_Collection info)
	{
		if(!info.getPropertyID().getProjjectName().equals(""))
		{
			tv_propertyName.setText(info.getPropertyID().getProjjectName());			
			tv_propertyName.setVisibility(View.VISIBLE);
		}
		else {
			tv_propertyName.setVisibility(View.INVISIBLE);
		}
		if(!info.getPropertyID().getCity().equals(""))
		{
			tv_propertyAddress.setText(info.getPropertyID().getCity());			
			tv_propertyAddress.setVisibility(View.VISIBLE);
		}
		else {
			tv_propertyAddress.setVisibility(View.INVISIBLE);
		}
		if(!info.getPropertyID().getHousePrice().equals(""))
		{
			tv_price.setText(info.getPropertyID().getHousePrice());			
			tv_price.setVisibility(View.VISIBLE);
		}
		else {
			tv_price.setVisibility(View.INVISIBLE);
		}
		if(info.getPropertyID().getTelephoneSales()!=null&&!info.getPropertyID().getTelephoneSales().equals(""))
		{
			tv_phoneNumber.setText(info.getPropertyID().getTelephoneSales());			
			tv_phoneNumber.setVisibility(View.VISIBLE);
		}
		else {
			tv_phoneNumber.setVisibility(View.INVISIBLE);
		}
		if(info.getStoretime()!=null&&!info.getStoretime().equals(""))
		{
			tv_collectionDate.setText(info.getStoretime().toString());
			tv_collectionDate.setVisibility(View.VISIBLE);
		}else {
			tv_collectionDate.setVisibility(View.INVISIBLE);
		}
	}
	public void setListener(OnClickListener listener,int location)
	{
		ib_del.setOnClickListener(listener);
		ib_del.setTag(R.id.tag_location, location);
	}

}
