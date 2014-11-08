package com.wufangnao.activity.view;

import com.wufangnao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class View_SlidingMenu {
	private View v_content;
	private LinearLayout llyt_home;
	private LinearLayout llyt_collectionProperty;
	private LinearLayout llyt_cooleciontLot;
	private LinearLayout llyt_help;
	private LinearLayout llyt_opinion;
	public View_SlidingMenu(Context context)
	{
		v_content = LayoutInflater.from(context).inflate(R.layout.slidingleft_layout,null);
		
		llyt_home = (LinearLayout) v_content.findViewById(R.id.llyt_home);
		
		llyt_collectionProperty = (LinearLayout) v_content.findViewById(R.id.llyt_propertycollection);
		
		llyt_cooleciontLot = (LinearLayout) v_content.findViewById(R.id.llyt_coordinatecollection);
		
		llyt_help = (LinearLayout) v_content.findViewById(R.id.llyt_help);
		
		llyt_opinion = (LinearLayout) v_content.findViewById(R.id.llyt_opinion);
		
	}
	public View getView() {
		return v_content;
	}
	public void setV_content(View v_content) {
		this.v_content = v_content;
	}
	public LinearLayout getLlyt_home() {
		return llyt_home;
	}
	public LinearLayout getLlyt_collectionProperty() {
		return llyt_collectionProperty;
	}
	public LinearLayout getLlyt_cooleciontLot() {
		return llyt_cooleciontLot;
	}
	public LinearLayout getLlyt_help() {
		return llyt_help;
	}
	public LinearLayout getLlyt_opinion() {
		return llyt_opinion;
	}
	

}
