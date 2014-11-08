package com.wufangnao.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import com.wufangnao.R;
import com.wufangnao.activity.view.View_PropertyList;
import com.wufangnao.adapter.item.PropertyListItem;
import com.wufangnao.combination.LabelItem;
import com.wufangnao.item.PropertyInfo;
import com.wufangnao.utils.DensityUtil;
import com.wufangnao.webInterface.TLabel;
import com.wufangnao.webInterface.TShowLabel;
import com.wufangnao.webInterface.mode.W_Label;
import com.wufangnao.webInterface.mode.W_LocalProperty;
import com.wufangnao.webInterface.mode.W_PropertyInfo;
import com.wufangnao.webInterface.mode.W_ShowLabel;

/**
 * 周边楼盘列表界面
 * 
 * @ClassName: Activity_PorpoertyList
 * @Description: TODO 以列表形式显示周边楼盘列表
 * @author lrk
 * @date 2014-3-4 上午11:26:41
 * 
 */
public class Activity_PorpoertyList extends Activity {

	/**
	 * 显示内容组件
	 */
	private View_PropertyList view_PropertyList = null;
	/**
	 * 周边楼盘数据
	 */
	private W_LocalProperty w_LocalProperty = null;
	/**
	 * 楼盘基本信息列表
	 */
	private List<W_PropertyInfo> infos = null;
	/**
	 * 楼盘详细信息显示组件
	 */
	private PropertyInfo propertyInfo = null;
	/**
	 * 楼盘信息显示容器列表
	 */
	private List<View> contentViews = null;
	
	/**
	 * 楼盘对应标签
	 */
	private Map<Integer, View> propertyLabel;
	/**
	 * 楼盘标签
	 */
	private List<W_Label> label_list;
	
	/**
	 * 楼盘标签的颜色值
	 */
	private int[] unClickColors={R.drawable.unselect_label_bg_1,R.drawable.unselect_label_bg_2,
			R.drawable.unselect_label_bg_3,R.drawable.unselect_label_bg_4};
	/**
	 * 选中状态下的楼盘标签值
	 */
	private int[] clickColors={R.drawable.select_label_bg_1,R.drawable.select_label_bg_2,
			R.drawable.select_label_bg_3,R.drawable.select_label_bg_4};
	private DisplayMetrics displayMetrics;//获取屏幕宽高
	/**
	 * 楼盘信息适配器
	 */
	private propertyInfoAdapter infoAdapter;
	/**
	 * 当前点击的楼ID
	 */
	private int propertyID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view_PropertyList = new View_PropertyList(this);//实例化显示容器组建
		displayMetrics= getResources().getDisplayMetrics();
		super.setContentView(view_PropertyList.getView());
		setListener();//设置监听
	}

	@Override
	protected void onStart() {
		super.onStart();
		initValue();//初始化值
	}
	/**
	 * 设置监听事件
	 */
	private void setListener()
	{
		view_PropertyList.getIv_colseview().setOnClickListener(new CloseViewClickListener());
		view_PropertyList.getIv_Back().setOnClickListener(new BackOnClickListener());
		view_PropertyList.getIv_selectbutton().setOnTouchListener(new SelectOnTouchListener());
		view_PropertyList.getVp_property_content().setOnPageChangeListener(new viewPagerSlectionListener());
		
	}
	/**
	 * 回退事件
	 * @ClassName: BackOnClickListener 
	 * @Description: TODO 回退到上一级界面
	 * @author lrk
	 * @date 2014-3-5 上午9:48:09 
	 *
	 */
	private class BackOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			/**
			 * 判断楼盘详细信息界面十分显示
			 */
			if (view_PropertyList.getLlyt_propertymsg().getVisibility() == View.VISIBLE) 
			{
				/**
				 * 设置菜单隐藏动画
				 */
				Animation Animation_Translate = new TranslateAnimation(0, -view_PropertyList.getLlyt_propertymsg().getWidth(), 0,
						0);
				/**
				 * 设置动画播放时长
				 */
				Animation_Translate.setDuration(500);
				view_PropertyList.getLlyt_propertymsg().startAnimation(Animation_Translate);
				view_PropertyList.getLlyt_propertymsg().setVisibility(
						View.GONE);//设置界面不可见
			}
		
				/**
				 * 回退上一级界面
				 */
				Intent intent = new Intent(Activity_PorpoertyList.this,
						Activity_BaiDuMap.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   
				startActivity(intent);
				/**
				 * 回退动画
				 */
				overridePendingTransition(R.anim.fragment_enter,
						R.anim.fragment_exit);
				finish();
			
			
		}
		
	}
	/**
	 * 初始化数据
	 */
	private void initValue() {
		/**
		 * 位置信息
		 */
		if (getIntent().hasExtra("localName")) {
			view_PropertyList.getTv_propertylist_mylocation().setText(
					getIntent().getStringExtra("localName"));
		} 
		/**
		 * 周边楼盘信息
		 */
		if (getIntent().hasExtra("W_LocalProperty")) {

			w_LocalProperty = (W_LocalProperty) getIntent()
					.getSerializableExtra("W_LocalProperty");

			infos = w_LocalProperty.getDatasource();
			/**
			 * 显示楼盘信息
			 */
			PropertyListAdapter adapter = new PropertyListAdapter();

			view_PropertyList.getLv_propertylsit_content().setAdapter(adapter);
		}
		/**
		 * 初始化楼盘详细信息列表组建
		 */
		propertyInfo = new PropertyInfo(this);
		propertyLabel = new HashMap<Integer, View>();// 楼盘标签View
		/**
		 * 楼盘标签列表
		 */
		label_list = new ArrayList<W_Label>();
		/**
		 * View数组
		 */
		contentViews = new ArrayList<View>();
		contentViews.add(propertyInfo.getView());
		/**
		 * 获取第一页的楼盘标签信息
		 */
		Thread thread = new Thread(new TShowLabel(this, new ShowLabelHandler(),
				1));
		thread.start();
	}
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
			

			}
		}
	}
	/**
	 * 楼盘信息列表适配器
	 * @ClassName: PropertyListAdapter 
	 * @Description: TODO 
	 * @author lrk
	 * @date 2014-3-5 上午9:53:23 
	 *
	 */
	private class PropertyListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			
			return infos.size();
		}

		@Override
		public Object getItem(int position) {
			
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {

			return infos.get(position).getPropertyID();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PropertyListItem item = null;

			if (convertView == null) {
				item = new PropertyListItem(Activity_PorpoertyList.this, null);
				convertView = item;
				convertView.setTag(item);
			} else {
				item = (PropertyListItem) convertView.getTag();
			}

			item.initValue(infos.get(position));
			item.setOnClickListener(new ItemOnClickListener(position));
			return convertView;
		}

	}
	/**
	 * 列表元素点击事件
	 * @ClassName: ItemOnClickListener 
	 * @Description: TODO 获取楼盘信息和了楼盘标签数据
	 * @author lrk
	 * @date 2014-3-4 下午4:15:29 
	 *
	 */
	private class ItemOnClickListener implements OnClickListener {
		private int position;
		
		public ItemOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			view_PropertyList.getIv_selectbutton()
			.setImageResource(R.drawable.selectd_propertyinfo);
			if (view_PropertyList.getLlyt_propertymsg().getVisibility() == View.GONE) {
				/**
				 * 设置菜单隐藏动画
				 */
				view_PropertyList.getLlyt_propertymsg().setVisibility(
						View.VISIBLE);
				Animation Animation_Translate = new TranslateAnimation(view_PropertyList.getLlyt_propertymsg().getWidth(), 0, 0,
						0);
				/**
				 * 设置动画播放时长
				 */
				Animation_Translate.setDuration(500);
				view_PropertyList.getLlyt_propertymsg().startAnimation(Animation_Translate);
				
			}
			propertyID = infos.get(position).getPropertyID();
			propertyInfo.initValue(infos.get(position), position);
			initShowLabel(propertyID);
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
		if (contentViews.size() == 1)// 是是第一个楼盘标签
		{
			System.out.println("第一个楼盘标签");
			
			contentViews.add(scrollView);

		} else {// 删除前一个楼盘的表情，显示当前楼盘标签
			System.out.println("替换前一个楼盘标签");
			contentViews.remove(1);
			contentViews.add(scrollView);

		}
		infoAdapter = new propertyInfoAdapter();
		view_PropertyList.getVp_property_content().setAdapter(infoAdapter);
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

	@Override
	public void onBackPressed() {
		if (view_PropertyList.getLlyt_propertymsg().getVisibility() == View.VISIBLE) 
		{
			/**
			 * 设置菜单隐藏动画
			 */
			Animation Animation_Translate = new TranslateAnimation(0, -view_PropertyList.getLlyt_propertymsg().getWidth(), 0,
					0);
			/**
			 * 设置动画播放时长
			 */
			Animation_Translate.setDuration(500);
			view_PropertyList.getLlyt_propertymsg().startAnimation(Animation_Translate);
			view_PropertyList.getLlyt_propertymsg().setVisibility(
					View.GONE);
			return;
		}
		else {

			Intent intent = new Intent(Activity_PorpoertyList.this,
					Activity_BaiDuMap.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			overridePendingTransition(R.anim.fragment_enter,
					R.anim.fragment_exit);
			finish();
		}
	}
	private class CloseViewClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			/**
			 * 信息状态未显示
			 */
			if (view_PropertyList.getLlyt_propertymsg().getVisibility() == View.GONE) {
				return;
			}
			/**
			 * 设置菜单隐藏动画
			 */
			Animation Animation_Translate = new TranslateAnimation(0, -view_PropertyList.getLlyt_propertymsg().getWidth(), 0,
					0);
			/**
			 * 设置动画播放时长
			 */
			Animation_Translate.setDuration(500);
			view_PropertyList.getLlyt_propertymsg().startAnimation(Animation_Translate);
			view_PropertyList.getLlyt_propertymsg().setVisibility(
					View.GONE);
			/**
			 * 播放动画
			 */
			view_PropertyList.getLlyt_propertymsg().setVisibility(View.GONE);
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
			Thread thread = new Thread(new TLabel(Activity_PorpoertyList.this,
					new LabelClickHandler(), propertyID, id));

			thread.start();
		}

	}
	/**
	 * 点击标签后后台数据的解析类
	 * 
	 * @author lrk 2013-12-16上午11:37:11
	 */
	private class LabelClickHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.getData().getString("result").equals("true")) {
		
			}
		}
	}
	/**
	 * 楼盘详情与标签
	 * @ClassName: propertyInfoAdapter 
	 * @Description: TODO 以左右滑动的形势切换楼盘详情与标签列表
	 * @author lrk
	 * @date 2014-3-5 上午9:55:15 
	 *
	 */
	private class propertyInfoAdapter extends PagerAdapter {

		public void destroyItem(View view, int position, Object object) {
			((ViewPager) view).removeView(contentViews.get(position));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {
			return contentViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(contentViews.get(arg1), 0);
			return contentViews.get(arg1);
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
			/**
			 * 滑动后改变按钮标签
			 */
			switch (position) {
			case 0:
				view_PropertyList.getIv_selectbutton()
						.setImageResource(R.drawable.selectd_propertyinfo);
				break;

			default:
				view_PropertyList.getIv_selectbutton().setImageResource(R.drawable.select_showlabel);
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
				view_PropertyList.getIv_selectbutton().setImageResource(R.drawable.select_showlabel);
				view_PropertyList.getVp_property_content().setCurrentItem(1);
			} else {
				view_PropertyList.getVp_property_content().setCurrentItem(0);
				view_PropertyList.getIv_selectbutton()
						.setImageResource(R.drawable.selectd_propertyinfo);
			}
			return true;
		}

	}


}
