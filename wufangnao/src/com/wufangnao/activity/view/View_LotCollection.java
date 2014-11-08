package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wufangnao.R;
/**
 * 坐标收藏显示界面
 * @author lrk
 *2013-12-11下午02:37:39
 */
public class View_LotCollection {
	
	private View v_LotCollection;
	
	private ListView lv_LotContent;
	
	private ImageView iv_menu;
	
	private LinearLayout llyt_edit;
	
	public View_LotCollection(Context context)
	{
		v_LotCollection = LayoutInflater.from(context).inflate(R.layout.coordinatecollection_layout, null);
		
		lv_LotContent = (ListView)v_LotCollection.findViewById(R.id.lv_lotcollection_content);
		
		iv_menu = (ImageView) v_LotCollection.findViewById(R.id.iv_menu);
		
		llyt_edit = (LinearLayout)v_LotCollection.findViewById(R.id.llyt_edit);
	}

	public View getView()
	{
		return v_LotCollection;
	}

	public ListView getLv_LotContent() {
		return lv_LotContent;
	}

	public void setLv_LotContent(ListView lv_LotContent) {
		this.lv_LotContent = lv_LotContent;
	}
	
	public ImageView getIv_menu() {
		return iv_menu;
	}

	public LinearLayout getLlyt_edit() {
		return llyt_edit;
	}
	

}
