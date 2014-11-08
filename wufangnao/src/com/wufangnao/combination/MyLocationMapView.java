package com.wufangnao.combination;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;
/**
 * 自定义的百度MapView，添加弹出窗口
 * @author lrk
 *2013-12-6上午10:04:13
 */
public class MyLocationMapView extends MapView {

	public static PopupOverlay   pop  = null;//弹出泡泡图层，点击图标使用
	public MyLocationMapView(Context context) {
		super(context);
	}
	public MyLocationMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			//消隐泡泡
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}

}
