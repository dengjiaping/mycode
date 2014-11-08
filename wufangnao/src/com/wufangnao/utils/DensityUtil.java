package com.wufangnao.utils;

import android.content.Context;

public class DensityUtil {
		
		/** 
	     * 密度转换像素 
	     * */  
		public static int dip2px(Context context, float dipValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(dipValue * scale + 0.5f); 
		} 
		/** 
	     * 像素转换密度 
	     * */  
		public static int px2dip(Context context, float pxValue){ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(pxValue / scale + 0.5f); 
		} 
		

}
