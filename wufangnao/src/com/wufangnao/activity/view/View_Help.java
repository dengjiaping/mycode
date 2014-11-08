package com.wufangnao.activity.view;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wufangnao.R;
/**
 * 
 * @ClassName: View_Help 
 * @Description: TODO 封装帮助页面的组件信息 
 * @author lrk
 * @date 2013-12-17 下午4:06:12 
 *
 */
public class View_Help {
	
	private View v_help;
	
	private EditText vp_helpContent;
	
	public View_Help(Context context)
	{
		v_help = LayoutInflater.from(context).inflate(R.layout.help_layout,null);
		
		vp_helpContent =(EditText)v_help.findViewById(R.id.vp_help_content);
		
		
	}
	public View getView()
	{
		return v_help;
	}
	public EditText getVp_helpContent() {
		return vp_helpContent;
	}
	

}
