package com.wufangnao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wufangnao.R;
import com.wufangnao.activity.view.View_LotList;
import com.wufangnao.adapter.item.LotListItem;
import com.wufangnao.webInterface.mode.W_NearLand;
/**
 * 地块信息列表
 * @ClassName: Activity_LotList 
 * @Description: TODO  显示周边地块的信息
 * @author lrk
 * @date 2014-3-5 上午9:36:00 
 *
 */
public class Activity_LotList extends Activity {
	/**
	 * 封装显示组件
	 */
	private View_LotList v_lotList;
	/**
	 * 地块信息
	 */
	private W_NearLand land;
	/**
	 * 用户当前位置
	 */
	private String location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v_lotList = new View_LotList(this);//实例化显示组件
		super.setContentView(v_lotList.getView());
		/**
		 * 设置监听事件
		 */
		setListener();
	}
	@Override
	protected void onStart() {
		super.onStart();
		/**
		 * 获取用户当前位置信息
		 */
		if(getIntent().hasExtra("localName"))
		{
			location = getIntent().getStringExtra("localName");
			v_lotList.getTv_myLocation().setText(location);
		}
		/**
		 * 获取地块信息
		 */
		if(getIntent().hasExtra("W_NearLand"))
		{
			land = (W_NearLand) getIntent().getSerializableExtra("W_NearLand");
			v_lotList.getLv_lotList().setAdapter(new LotListAdapter());
		}
	}
	/**
	 * 设置监听事件
	 */
	private void setListener()
	{
		v_lotList.getIv_back().setOnClickListener(new BackOnClickListener());
	}
	/**
	 * ListView显示适配器
	 * @ClassName: LotListAdapter 
	 * @Description: TODO 显示地块信息 
	 * @author lrk
	 * @date 2014-3-7 上午9:38:37 
	 *
	 */
	private class LotListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			
			return land.getDatasource().size();
		}

		@Override
		public Object getItem(int position) {
		
			return land.getDatasource().get(position);
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LotListItem item = null;
			if(convertView==null)//是否需要新建组件
			{
				item = new LotListItem(Activity_LotList.this, null);
				convertView = item;
				convertView.setTag(item);
			}
			else {//复用组件
				item = (LotListItem) convertView.getTag();
			}
			item.initValue(land.getDatasource().get(position));
			return convertView;
		}
	}
	/**
	 * 回退事件监听
	 * @ClassName: BackOnClickListener 
	 * @Description: TODO  返回上一个Activity.
	 * @author lrk
	 * @date 2014-3-7 上午9:39:39 
	 *
	 */
	
	private class BackOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
		
			onBackPressed();
			
			
		}
		
	}
	/**
	 * 监听返回键
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Activity_LotList.this,
				Activity_LotBaiduMap.class);
		/**
		 * 回退到上一个Activity,且不重新创建
		 */
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   
		startActivity(intent);
		/*
		 * 回退动画
		 */
		overridePendingTransition(R.anim.fragment_enter,
				R.anim.fragment_exit);
		finish();
	}

}
