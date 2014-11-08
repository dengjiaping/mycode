package com.wufangnao.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.wufangnao.R;
import com.wufangnao.activity.Activity_BaiDuMap;
import com.wufangnao.activity.Activity_Index;
import com.wufangnao.activity.view.View_PropertyCollection;
import com.wufangnao.adapter.item.PropertyCollectionItem;
import com.wufangnao.webInterface.TCollectionProperty;
import com.wufangnao.webInterface.TProMessage;
import com.wufangnao.webInterface.TPropertyCollection;
import com.wufangnao.webInterface.mode.W_LocalProperty;
import com.wufangnao.webInterface.mode.W_LocalProperty_Property;
import com.wufangnao.webInterface.mode.W_MessageStatue;
import com.wufangnao.webInterface.mode.W_PropertyCollection;
import com.wufangnao.webInterface.mode.W_PropertyInfo;

/**
 * 楼盘收藏界面
 * 
 * @author lrk 2013-12-11下午02:44:18
 */
public class Fragment_PropertyCollection extends Fragment {
	/**
	 * 收藏信息界面
	 */
	private View_PropertyCollection v_PropertyCollection;
	/**
	 * 收藏信息封装类
	 */
	private W_PropertyCollection w_PropertyCollection;
	/**
	 * 数据适配器
	 */
	private PCinfoAdapter infoAdapter;
	/**
	 * 操作信息提示框
	 */
	private Dialog dialog;
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) {
			Thread thread = new Thread(new TPropertyCollection(getActivity(),
					new PropertyCollectionHandler()));
			thread.start();
			
		}
		
		;
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		v_PropertyCollection = new View_PropertyCollection(getActivity());
		postMsg();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setListener();
		return v_PropertyCollection.getView();
	}

	/**
	 * 网络数据请求方法
	 */
	public void postMsg() {
		
			handler.sendEmptyMessageDelayed(0, 500);
	}
	/**
	 * 设置监听
	 */
	private void setListener() {
		
		v_PropertyCollection.getIv_menu().setOnClickListener(new ShowMenuClickListener());
		//v_PropertyCollection.getLlyt_edit().setOnClickListener(l);

	}
	private class ShowMenuClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			((Activity_Index)getActivity()).toggle();
		}
		
	}
	/**
	 * JSON 数据解析类
	 * 
	 * @author lrk 2013-12-11下午03:28:08
	 */
	private class PropertyCollectionHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.getData().getString("result").endsWith("true")) {
				w_PropertyCollection = TPropertyCollection.getParsJson(msg
						.getData().getString("jsonResult"));
				
				infoAdapter = new PCinfoAdapter();
				v_PropertyCollection.getLv_PropertyContent().setAdapter(
						infoAdapter);
			} else {
				Toast.makeText(getActivity(), msg.getData().getString("info"),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class PCinfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return w_PropertyCollection.getDatasource().size();
		}

		@Override
		public Object getItem(int position) {

			return w_PropertyCollection.getDatasource().get(position)
					.getPropertyID();
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			PropertyCollectionItem item = null;
			if (convertView == null) {
				item = new PropertyCollectionItem(getActivity(),null);
				convertView = item;
				convertView.setTag(item);
			} else {
				item = (PropertyCollectionItem) convertView.getTag();
			}
			
			item.initValue(w_PropertyCollection.getDatasource().get(position));
			item.setTag(R.id.tag_location, position);
			item.setOnClickListener(new PropertyItemClickListenter());
			item.setListener(new DelItemClickListenter(),position);
			return convertView;
		}

	}

	/**
	 * 楼盘点击事件
	 * 
	 * @author lrk 2013-12-11下午04:12:09
	 */
	private class PropertyItemClickListenter implements OnClickListener {

		@Override
		public void onClick(View v) {

			int position = (Integer) v.getTag(R.id.tag_location);

			

			Thread thread = new Thread(new TProMessage(getActivity(),
					new ProMessageHandler(),
					w_PropertyCollection.getDatasource()
							.get(position).getPropertyID().getPropertyID()));
			thread.start();

			

		}

	}
	/**
	 * 
	 * @ClassName: ProMessageHandler 
	 * @Description: TODO  解析楼盘信息数据
	 * @author lrk
	 * @date 2014-1-2 下午2:24:14 
	 *
	 */
	private class ProMessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println(msg.getData().getString("result"));
			if(msg.getData().getString("result").equals("true"))
			{
				Intent intent = new Intent(getActivity(), Activity_BaiDuMap.class);
				
				W_LocalProperty w_LocalProperty  = new W_LocalProperty();
				
				W_LocalProperty_Property property= TProMessage.getParesJson(msg.getData().getString("jsonResult"));
				
				List<W_PropertyInfo> datasource = new ArrayList<W_PropertyInfo>();
				
				datasource.add(property.getDatasource());
				
				w_LocalProperty.setDatasource(datasource);
				
				intent.putExtra("isPropertyCollection", true);
				
				intent.putExtra("propertyList", w_LocalProperty);

				getActivity().startActivity(intent);
			}
		}
	}

	/**
	 * 删除点击事件
	 * 
	 * @author lrk 2013-12-11下午04:12:42
	 */
	private class DelItemClickListenter implements OnClickListener {

		@Override
		public void onClick(View v) {
			final int position = (Integer) v.getTag(R.id.tag_location);
			System.out.println("position = " + position);
			new AlertDialog.Builder(getActivity()).setTitle("删除收藏").setMessage("确定要删除这个坐标么？")
			.setPositiveButton("删除", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface onclickdialog, int which) {
			Thread thread = new Thread(new TCollectionProperty(getActivity(),
					new CollectionPropertyHandler(position),
					w_PropertyCollection.getDatasource().get(position)
							.getPropertyID().getPropertyID()));
			thread.start();
			dialog = new AlertDialog.Builder(getActivity()).setMessage(
					"正在删除数据……").create();
			dialog.show();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			}).setCancelable(true)
			.show();

		}

	}

	/**
	 * 删除收藏信息解析类
	 * 
	 * @author Administrator 2013-12-11下午05:05:46
	 */
	private class CollectionPropertyHandler extends Handler {
		private int position; // 删除收藏位置

		public CollectionPropertyHandler(int position) {
			this.position = position;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dialog.dismiss();// 提示矿消失
			if (msg.getData().getString("result").endsWith("true")) {
				W_MessageStatue statue = TCollectionProperty.getParsJosn(msg
						.getData().getString("jsonResult"));
				if (statue.isSuccess())// 删除成功，删除本地数据
				{
					w_PropertyCollection.getDatasource().remove(position);
					infoAdapter.notifyDataSetChanged();
				}
			} else {// 删除出错，显示信息
				Toast.makeText(getActivity(), "网络访问出错", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
