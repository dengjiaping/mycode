package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wufangnao.R;
/**
 * 楼盘收藏显示界面
 * @author lrk
 *2013-12-11下午02:36:32
 */
public class View_PropertyCollection {
	
	private View v_PropertyCollection;
	
	private ListView lv_PropertyContent;
	
	private ImageView iv_menu;
	
	private LinearLayout llyt_edit;
	
	public View_PropertyCollection(Context context)
	{
		v_PropertyCollection = LayoutInflater.from(context).inflate(R.layout.propertycollection_layout, null);
		
		lv_PropertyContent = (ListView)v_PropertyCollection.findViewById(R.id.lv_propertycollection_content);
		
		iv_menu = (ImageView) v_PropertyCollection.findViewById(R.id.iv_menu);
		
		llyt_edit = (LinearLayout)v_PropertyCollection.findViewById(R.id.llyt_edit);
		
	}

	public View getView()
	{
		return v_PropertyCollection;
	}

	public ListView getLv_PropertyContent() {
		return lv_PropertyContent;
	}

	public void setLv_PropertyContent(ListView lv_PropertyContent) {
		this.lv_PropertyContent = lv_PropertyContent;
	}

	public ImageView getIv_menu() {
		return iv_menu;
	}

	public LinearLayout getLlyt_edit() {
		return llyt_edit;
	}
	
	

}
