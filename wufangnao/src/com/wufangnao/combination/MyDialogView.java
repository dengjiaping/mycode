package com.wufangnao.combination;

import com.wufangnao.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
/**
 * 自定义的Dialog，显示在屏幕中间
 * @ClassName: MyDialogView 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:58:24 
 *
 */
public class MyDialogView extends Dialog {

	public MyDialogView(Context context) {
		super(context,R.style.dialog);
		LinearLayout layout = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.popdialog, null);
		setContentView(layout);
		setCanceledOnTouchOutside(false);
		
	}

}
