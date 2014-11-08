package com.wufangnao.combination;

import com.wufangnao.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 显示在屏幕中间的Toast组件
 * @ClassName: CenterToast 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:56:28 
 *
 */
public class CenterToast extends Toast {

	public CenterToast(Context context,String msg) {
		super(context);
		TextView textView = new TextView(context);
		// 设置textview背景
		textView.setBackgroundResource(R.drawable.dialogbg);
		// 设置textview的文字颜色
		textView.setTextColor(Color.WHITE);
		textView.setTextSize(16);
		// 设置文字的位置
		textView.setPadding(10, 10, 10, 10);
		// 设置显示内容
		textView.setText(msg);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// new当前的toast对象
		// 设置toast显示时间
		setDuration(Toast.LENGTH_SHORT);
		// 设置内容的显示位置
		setGravity(Gravity.CENTER, 0, 0);
		// 将textview加入
		setView(textView);
		// 显示
		//toastView.show();

	}

}
