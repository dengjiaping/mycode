package com.wufangnao.adapter.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wufangnao.R;
/**
 * 楼盘信息表格
 * @ClassName: PropertyTableItem 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:56:11 
 *
 */
public class PropertyTableItem extends LinearLayout {
	private ImageView jingzhuang;
	private ImageView inscale;
	private ImageView gaoceng;
	private ImageView dachanquanfang;
	private TextView hourseName;
	private LinearLayout llyt_content;
	public PropertyTableItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.propertytable_content_item,this,true);
		jingzhuang = (ImageView) layout//精装
				.findViewById(R.id.propertytable_jingzhuang);
		 inscale = (ImageView) layout//在售
				.findViewById(R.id.propertytable_zaishou);
		 gaoceng = (ImageView) layout//高层
				.findViewById(R.id.propertytable_gaocheng);
		 dachanquanfang = (ImageView) layout
				.findViewById(R.id.propertytable_bigchanquanfang);
		 hourseName = (TextView) layout//楼盘名称
				.findViewById(R.id.propertytable_propertyname);
		 llyt_content = (LinearLayout) layout
				.findViewById(R.id.propertytable_content__llyt);
	}
	public ImageView getJingzhuang() {
		return jingzhuang;
	}
	public ImageView getInscale() {
		return inscale;
	}
	public ImageView getGaoceng() {
		return gaoceng;
	}
	public ImageView getDachanquanfang() {
		return dachanquanfang;
	}
	public TextView getHourseName() {
		return hourseName;
	}
	public LinearLayout getLlyt_content() {
		return llyt_content;
	}
	

}
