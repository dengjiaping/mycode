package com.wufangnao.adapter.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wufangnao.R;
import com.wufangnao.webInterface.mode.W_CoordiateCollection_Coordiate;
import com.wufangnao.webInterface.mode.W_PropertyCollection_Collection;
/**
 * 区域收藏
 * @author lrk
 *2013-12-11下午03:30:07
 */
public class CollectionItem extends RelativeLayout{
	
	private Context context;
	
	private View v_CollectionItem;
	
	private TextView tv_Collection_Name;
	
	private TextView tv_Collection_Date;
	
	private ImageButton ib_del;
	
	
	public CollectionItem(Context context)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.collection_item_layout,this,true);
		
		tv_Collection_Name = (TextView) findViewById(R.id.tv_lotcollection_name);
		
		tv_Collection_Date = (TextView) findViewById(R.id.tv_lotcollection_data);
		
		ib_del = (ImageButton)findViewById(R.id.ib_deldata);
	}
	public View getView()
	{
		return v_CollectionItem;
	}
	/**
	 * 初始数据
	 * @param coordiate
	 * @param position
	 */
	public void initValue(W_CoordiateCollection_Coordiate coordiate,int position)
	{
		tv_Collection_Name.setText(coordiate.getCoordname());
		tv_Collection_Date.setText(coordiate.getStoretime());
		tv_Collection_Name.setTag(R.id.tag_location,position);
	
	}
	public void setDelListener(OnClickListener listener,int postion)
	{
		ib_del.setOnClickListener(listener);
		ib_del.setTag(R.id.tag_location,postion);
	}
}
