package com.wufangnao.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wufangnao.R;
/**
 * 楼盘柱状图显示组件
 * @ClassName: PropertyChartItem 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:54:52 
 *
 */
public class PropertyChartItem extends LinearLayout{
	private View v_Property;
	private LinearLayout  v_char;
	private TextView tv_propertyName;
	private TextView tv_Value;
	private LinearLayout llyt_chart_item;
	private RelativeLayout llyt_chart_name;
	private double value;
	private int type;
	public PropertyChartItem(Context context ,AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.chart_item_layout,this,true);
		
		v_char = (LinearLayout)findViewById(R.id.tv_chart_dataheight);
		
		tv_propertyName = (TextView)findViewById(R.id.tv_chart_propertyname);
		
		tv_Value = (TextView)findViewById(R.id.tv_char_item_title);
		
		llyt_chart_name = (RelativeLayout)findViewById(R.id.llyt_chart_name);
		
	}
	
	public double getValue() {
		return value;
	}
	
	public RelativeLayout getLlyt_chart_name() {
		return llyt_chart_name;
	}
	public void setValue(int value,int type) {
		this.value = value;
		if(type==8)
			this.value =value-2000;
		tv_Value.setText(String.valueOf(value));
	}
	public void setValue(double value) {
		this.value = value;
		tv_Value.setText(String.valueOf(value));
	}
	public TextView getTv_propertyName() {
		return tv_propertyName;
	}
	public LinearLayout getLlyt_chart_item() {
		return llyt_chart_item;
	}
	public LinearLayout getV_char() {
		return v_char;
	}
	

}
