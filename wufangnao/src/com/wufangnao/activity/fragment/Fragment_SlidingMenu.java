package com.wufangnao.activity.fragment;

import com.wufangnao.activity.Activity_Index;
import com.wufangnao.activity.view.View_SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
/**
 * 设置菜单栏界面
 * @ClassName: Fragment_SlidingMenu 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:01:38 
 *
 */
public class Fragment_SlidingMenu extends Fragment {
	private View_SlidingMenu v_SlidingMenu;//菜单栏组建
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		v_SlidingMenu = new View_SlidingMenu(getActivity());
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setOnClickListener();
		return v_SlidingMenu.getView();
	}
	/**
	 * 设置监听
	 */
	private void setOnClickListener()
	{
		v_SlidingMenu.getLlyt_home().setOnClickListener(new ClickListener());
		v_SlidingMenu.getLlyt_collectionProperty().setOnClickListener(new ClickListener());
		v_SlidingMenu.getLlyt_cooleciontLot().setOnClickListener(new ClickListener());
		v_SlidingMenu.getLlyt_help().setOnClickListener(new ClickListener());
		v_SlidingMenu.getLlyt_opinion().setOnClickListener(new ClickListener());
	}
	/**
	 * 单击响应事件
	 * @ClassName: ClickListener 
	 * @Description: TODO 响应按钮 
	 * @author lrk
	 * @date 2014-3-7 上午10:05:42 
	 *
	 */
	private class ClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			if(getActivity() instanceof Activity_Index)
			{
				Activity_Index index = (Activity_Index) getActivity();
				index.toggle();
				if(v_SlidingMenu.getLlyt_home() ==v)//回到主页
				{
					index.indexClick();
				}
				else if(v_SlidingMenu.getLlyt_collectionProperty() ==v) {//进入楼盘收藏
					index.propertyCollection();
				}
				else if(v_SlidingMenu.getLlyt_cooleciontLot() ==v) {//进入地块收藏
					index.coordinateCollection();
				}
				else if(v_SlidingMenu.getLlyt_help() ==v) {//帮助界面
					index.helpOnClick();
				}
				else if(v_SlidingMenu.getLlyt_opinion() ==v) {//反馈界面
					index.feedbackClick();
				}
			}
			
		}
		
	}

}
