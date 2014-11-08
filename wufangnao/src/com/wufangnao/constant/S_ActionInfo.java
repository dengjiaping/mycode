package com.wufangnao.constant;

import java.io.File;

import android.os.Environment;

public class S_ActionInfo {
	/**
	 * 添加定位信息页面
	 */
	public static final String S_LOCALPROPERTY = "add_localproperty_fragment";
	/**
	 * 改变收藏状态
	 */
	public static final String S_CHANGEFAVO = "changefavo_property";
	/**
	 * 邮箱地址正则表达式
	 */
	public static final String S_EMAILADDRESS = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	/**
	 * 更新截图
	 */
	public static final String S_REFESHMAP = "refeshcurrentmap";
	/**
	 * 记录楼盘信息到本地
	 */
	public static final String S_REFESHDATA = "Set_PropertyMsgLoacl";
	/**
	 * 显示标题
	 */
	public static final String S_SHOWTITLE = "showTitle";
	/**
	 * 定位到用户当前位置
	 */
	public static final String S_LOCATION_USER_POINT = "center_user_postion";
	/**
	 * 存储百度地图截图的位置
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			String filePath = Environment.getExternalStorageDirectory()
					+ File.separator + "wufangnao" + File.separator + "IMG";// 获取跟目录

			sdDir = new File(filePath);
			if (!sdDir.exists()) {
				sdDir.mkdirs();
			}
		}
		return sdDir.toString() + File.separator ;

	}
}
