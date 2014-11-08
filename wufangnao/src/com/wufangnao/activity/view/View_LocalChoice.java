package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wufangnao.R;
import com.wufangnao.combination.SideBar;
/**
 * 封装地域选择页面的操作组件
 * @author lrk
 *
 */
public class View_LocalChoice {
	/**
	 * 整个布局文件代表的view
	 */
	private View v_localChoice;
	/**
	 * 城市列表
	 */
	private ExpandableListView lv_citycontent;
	/**
	 * 当前位置的首字母
	 */
	private TextView tv_changedialog;
	/**
	 * 字母列表
	 */
	private SideBar sb_SideBar;
	/**
	 * 实例化方法
	 * @param context 调用实例的引用上下文对象
	 */
	public  View_LocalChoice(Context context)
	{
		v_localChoice = LayoutInflater.from(context).inflate(R.layout.localchocie_layout, null);
		
		lv_citycontent = (ExpandableListView)v_localChoice.findViewById(R.id.lv_citycontent);
		
		lv_citycontent.setGroupIndicator(null);
		
		tv_changedialog = (TextView)v_localChoice.findViewById(R.id.tv_changedialog);
		
		sb_SideBar = (SideBar)v_localChoice.findViewById(R.id.sb_sidrbar);
		
	}
	
	public View getView()
	{
		return v_localChoice;
	}

	public ExpandableListView getLv_citycontent() {
		return lv_citycontent;
	}

	public TextView getTv_changedialog() {
		return tv_changedialog;
	}

	public SideBar getSb_SideBar() {
		return sb_SideBar;
	}
	
	
	
	

}
