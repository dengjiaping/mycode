package com.wufangnao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 存储信息到本地
 * @author lrk
 *
 */
public class InfoSharePreferenceUtil {
	
	private static final String PREFERENCES_NAME = "cityselsectInfo"; //本地存储文件名
	
	private static final String SELECT_SHENG = "SHENG"; //上一次选择的省
	 
	private static final String SELECT_SHI = "SHI";//上一次选择的市/区
	
	private static final String SELECT_QU = "QU";//上一次选择的区
	
	private static final String PREFERENCES_USERID = "keepuserId"; //userID保存文件
	
	private static final String USERID = "userid";//useid
	
	private static final String FRIST_START = "frist_start";
	
	
	/**
	 * 记录地域选择到本地
	 * @param context 引用的上下文对象
	 * @param sheng  省级信息
	 * @param shi  市级信息
	 * @param qu   区级信息
	 */
	public  static void keepLocalChoiceMsg(Context context,String sheng,String shi,String qu)
	{
		SharedPreferences localChoice_pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		
		Editor localChoice_editor = localChoice_pref.edit();
		
	
		localChoice_editor.putString(SELECT_SHENG, sheng);
	
		localChoice_editor.putString(SELECT_SHI, shi);
	
		localChoice_editor.putString(SELECT_QU, qu);
		
		localChoice_editor.commit();
		
		
	}
	/**
	 * 读取地域选择到本地
	 * @param context 引用的上下文对象
	 */
	public static String[] readLocalChoiceMsg(Context context)
	{
		SharedPreferences localChoice_pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		
		String[] localchoice = new String [3];
		
		localchoice[0] = localChoice_pref.getString(SELECT_SHENG, null);
		
		localchoice[1] = localChoice_pref.getString(SELECT_SHI, null);
		
		localchoice[2] = localChoice_pref.getString(SELECT_QU, null);
		
		return localchoice;
		
	}
	/**
	 * 记录userId到本地
	 * @param context
	 * @param userId
	 */
	public  static void keepUserIDMsg(Context context,Integer userId)
	{
		SharedPreferences userId_pref = context.getSharedPreferences(PREFERENCES_USERID, Context.MODE_PRIVATE);
		
		Editor userId_editor = userId_pref.edit();
		
		userId_editor.putInt(USERID, userId);
		
		userId_editor.commit();
		
	}
	
	/**
	 * 读取地域选择到本地
	 * @param context 引用的上下文对象
	 */
	public static Integer readUserIdMsg(Context context)
	{
		SharedPreferences userId_pref = context.getSharedPreferences(PREFERENCES_USERID, Context.MODE_PRIVATE);
		
		Integer userId  = userId_pref.getInt(USERID, -25535);
		
		return userId;
	}
	/**
	 * 是否首次进入程序
	 */
	public static boolean readFristStart(Context context)
	{
		SharedPreferences frist_start = context.getSharedPreferences(FRIST_START,Context.MODE_PRIVATE); 
		boolean isFrist = frist_start.getBoolean("isFrist",true); 
		
		return isFrist;
	}
	/**
	 * 记录isFrist到本地
	 * @param context
	 * @param userId
	 */
	public  static void keepFristStart(Context context,boolean isFrist)
	{
		SharedPreferences frist_start = context.getSharedPreferences(FRIST_START, Context.MODE_PRIVATE);
		
		Editor frist_start_editor = frist_start.edit();
		
		frist_start_editor.putBoolean("isFrist",isFrist);
		
		frist_start_editor.commit();
		
	}
	

}
