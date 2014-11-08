package com.wufangnao.combination;
import com.wufangnao.R;
import com.wufangnao.webInterface.mode.W_Label;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 标签列表组件
 * @ClassName: LabelItem 
 * @Description: TODO 显示楼盘标签
 * @author lrk
 * @date 2014-3-7 上午10:57:41 
 *
 */
public class LabelItem extends LinearLayout {
	private TextView tv_labelName;
	private TextView tv_labelClikcCount;
	private int location;

	public LabelItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.label_layout,this,true);
		tv_labelClikcCount = (TextView) findViewById(R.id.tv_clickcount);
		tv_labelName = (TextView) findViewById(R.id.tv_labelname);
	}
	
	public void init(W_Label label)
	{
		tv_labelClikcCount.setText(String.valueOf(100+(int)(Math.random()*100)));
		tv_labelName.setText(label.getLabelName());
		this.setTag(R.id.tag_id, label.getLabelId());
		this.setTag(R.id.tag_select, false);
		
	}
	public void addCount()
	{
		int count = Integer.valueOf(tv_labelClikcCount.getText().toString());
		count++;
		tv_labelClikcCount.setText(String.valueOf(count));
	}
	public void delCount()
	{
		int count = Integer.valueOf(tv_labelClikcCount.getText().toString());
		count--;
		tv_labelClikcCount.setText(String.valueOf(count));
	}
	public void setLocation(int location)
	{
		this.location = location;
		this.setTag(R.id.tag_location, location);
	}
	

}
