package com.wufangnao.activity;

import java.util.ArrayList;
import java.util.List;

import com.wufangnao.R;
import com.wufangnao.utils.InfoSharePreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 
 * @ClassName: Activity_Guide
 * @Description: TODO 引导界面
 * @author lrk
 * @date 2014-2-12 下午3:07:00
 * 
 */
public class Activity_Guide extends Activity {

	private ViewPager vp_Guide;
	private List<View> imageList;
	private ViewPageAdapter viewPageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (InfoSharePreferenceUtil.readFristStart(this)) {
			InfoSharePreferenceUtil.keepFristStart(this, false);
			super.setContentView(R.layout.llyt_guide);
			vp_Guide = (ViewPager) findViewById(R.id.vp_guide);
			imageList = new ArrayList<View>();
			ImageView guidView_One = new ImageView(this);
			guidView_One.setBackgroundResource(R.drawable.guide_1);
			ImageView guidView_two = new ImageView(this);
			guidView_two.setBackgroundResource(R.drawable.guide_2);
			ImageView guidView_three = new ImageView(this);
			guidView_three.setBackgroundResource(R.drawable.guide_3);
			RelativeLayout relativeLayout = new RelativeLayout(this);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			ProgressBar guidView_four = new ProgressBar(this);
			guidView_four.setLayoutParams(layoutParams);
			relativeLayout.addView(guidView_four);
			imageList.add(guidView_One);
			imageList.add(guidView_two);
			imageList.add(guidView_three);
			imageList.add(relativeLayout);
			viewPageAdapter = new ViewPageAdapter();
			vp_Guide.setAdapter(viewPageAdapter);
			vp_Guide.setOnPageChangeListener(new ViewPagerChangeListener());
		} else {
			toIndex();
		}
	}

	private void toIndex() {
		Intent intent = new Intent(this, Activity_Index.class);
		startActivity(intent);
		finish();
	}

	private class ViewPageAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageList.get(position));// 删除页卡
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
			container.addView(imageList.get(position), 0);// 添加页卡
			return imageList.get(position);
		}

		@Override
		public int getCount() {
			return imageList.size();// 返回页卡的数量
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;// 官方提示这样写
		}

	}
	private class ViewPagerChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int location) {
			if(location==imageList.size()-1)
			{
				Handler  handler= new Handler()
				{
					public void handleMessage(android.os.Message msg) {
						
						toIndex();
					};
					
				};
				handler.sendEmptyMessageDelayed(0,1500);
			}
			
		}
		
	}
}
