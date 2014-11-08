package com.wufangnao.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wufangnao.R;
import com.wufangnao.activity.Activity_Index;
import com.wufangnao.adapter.item.CityItem;
import com.wufangnao.combination.CenterToast;
import com.wufangnao.constant.S_Fragmnet;
import com.wufangnao.item.Area;
import com.wufangnao.item.CityListItem;
import com.wufangnao.manger.CheckWifiConnection;

/**
 * 地域选择相关适配器
 * @author lrk
 *2013-12-5上午11:15:17
 */
public class CityAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<CityListItem> list;
	private List<Area> childrendCotent;
	private Activity_Index index = null;
	public CityAdapter(Context context,List<CityListItem> list)
	{
		this.context = context;
		this.list = list;
	}

	@Override
	public int getGroupCount() {
		
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		childrendCotent =list.get(groupPosition).getAreas();
		return childrendCotent.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		childrendCotent =list.get(groupPosition).getAreas();
		return childrendCotent.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return groupPosition*10+childPosition;
	}

	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		CityItem cityItem;
		final CityListItem cityListItem = list.get(groupPosition);
		if (convertView == null) {
			cityItem = new CityItem(context);
			convertView = cityItem.getView();
			convertView.setTag(cityItem);
		} else {
			cityItem = (CityItem) convertView.getTag();
		}
		//根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(groupPosition);
				
				//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(groupPosition == getPositionForSection(section)){
					cityItem.getTv_catalog().setVisibility(View.VISIBLE);
					cityItem.getTv_catalog().setText(cityListItem.getSortLetters());
					Log.d("lrk","Letter = "+cityListItem.getSortLetters());
				}else{
					cityItem.getTv_catalog().setVisibility(View.GONE);
				}
				if(isExpanded)
				{
					cityItem.getIv_cityclick().setBackgroundResource(R.drawable.city_click);
				}
				else {
					cityItem.getIv_cityclick().setBackgroundResource(R.drawable.city_unclick);
				}
				cityItem.getTv_cityName().setText(this.list.get(groupPosition).getName());
				
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
			TextView children  = null;
			if(convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.city_item_layout,null);
				children = (TextView) convertView.findViewById(R.id.tv_cityitem_name);
				convertView.setTag(children);
			}
			else {
				children = (TextView) convertView.getTag();
			}
			childrendCotent = list.get(groupPosition).getAreas();
			children.setText(childrendCotent.get(childPosition).getName().toString());
			children.setTag(R.id.tag_city,list.get(groupPosition).getName());
			children.setTag(R.id.tag_area,childrendCotent.get(childPosition).getName().toString());
			children.setOnClickListener(new CiytClickListener());
			return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getGroupCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	public class CityChildren{
		TextView textView;
		
	}

	private class CiytClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			if(!CheckWifiConnection.isNetworkConnected(context))
			{
				CenterToast toast = new CenterToast(context,"网络连接不可用，请检查网络");
				toast.show();
				return;
			}
			Message msg = new Message();
			index = (Activity_Index) context;
			index.toggle();
			msg.obj = v;
			handler.sendMessageDelayed(msg,500);
			
		}
		
	}
	
	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			
			View v = (View)msg.obj;
			Bundle bundle = new Bundle();
			bundle.putBoolean("isGeoCode", false);
			bundle.putString("shi",v.getTag(R.id.tag_city).toString());
			bundle.putString("qu",v.getTag(R.id.tag_area).toString());
			index.locationProperty(bundle);
		};
	};

}