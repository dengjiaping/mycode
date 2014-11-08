package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wufangnao.R;
/**
 * 地块列表界面组建
 * @ClassName: View_LotList 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午10:50:22 
 *
 */
public class View_LotList {
	private View v_lotlist;
	private ImageView iv_back;
	private TextView tv_myLocation;
	private ListView lv_lotList;
	
	public View_LotList(Context context)
	{
		v_lotlist = LayoutInflater.from(context).inflate(R.layout.lotlist_layout, null);
		iv_back =(ImageView)v_lotlist.findViewById(R.id.iv_lotlist_back);
		tv_myLocation = (TextView)v_lotlist.findViewById(R.id.tv_lotlist_mylocation);
		lv_lotList = (ListView)v_lotlist.findViewById(R.id.lv_lotlist_content);
		
	}
	public View getView()
	{
		return v_lotlist;
	}

	public ImageView getIv_back() {
		return iv_back;
	}

	public void setIv_back(ImageView iv_back) {
		this.iv_back = iv_back;
	}

	public TextView getTv_myLocation() {
		return tv_myLocation;
	}

	public void setTv_myLocation(TextView tv_myLocation) {
		this.tv_myLocation = tv_myLocation;
	}

	public ListView getLv_lotList() {
		return lv_lotList;
	}

	public void setLv_lotList(ListView lv_lotList) {
		this.lv_lotList = lv_lotList;
	}
	

}
