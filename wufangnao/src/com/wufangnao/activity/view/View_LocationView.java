package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wufangnao.R;
/**
 * 
 * @ClassName: View_LocationView 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:49:32 
 *
 */
public class View_LocationView {
	private View view;
	
	public View_LocationView(Context context)
	{
		view = LayoutInflater.from(context).inflate(R.layout.index_bg, null);
	
		
	}
	public View getView() {
		return view;
	}
	

}
