package com.wufangnao.activity.fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.wufangnao.R;
import com.wufangnao.activity.Activity_BaiDuMap;
import com.wufangnao.activity.Activity_Index;
import com.wufangnao.activity.view.View_LotCollection;
import com.wufangnao.adapter.item.CollectionItem;
import com.wufangnao.combination.MyDialogView;
import com.wufangnao.webInterface.TCollectCoordinate;
import com.wufangnao.webInterface.TCollectionProperty;
import com.wufangnao.webInterface.TLocalProperty;
import com.wufangnao.webInterface.TShowCoordinate;
import com.wufangnao.webInterface.mode.W_CoordiateCollection;
import com.wufangnao.webInterface.mode.W_LocalProperty;
import com.wufangnao.webInterface.mode.W_MessageStatue;

/**
 * 楼盘收藏界面
 * 
 * @author lrk 2013-12-11下午02:44:18
 */
public class Fragment_CoordinateCollection extends Fragment {
	/**
	 * 收藏信息界面
	 */
	private View_LotCollection v_LotCollection;
	/**
	 * 坐标收藏信息
	 */
	private W_CoordiateCollection w_CoordiateCollection;

	/**
	 * 数据适配器
	 */
	private PCinfoAdapter infoAdapter;
	/**
	 * 操作信息提示框
	 */
	private Dialog dialog;
	/**
	 * 点击的收藏坐标的经度
	 */
	private double latitude ;
	/**
	 * 收藏的维度
	 */
	private double longitude;
	/**
	 *点击的收藏坐标地址
	 */
	private String localName;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			Thread thread = new Thread(new TShowCoordinate(getActivity(),
					new PropertyCollectionHandler()));
			thread.start();
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {

		v_LotCollection = new View_LotCollection(getActivity());
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setListener();
		handler.sendEmptyMessageDelayed(0,500);
		return v_LotCollection.getView();
	}
	
	/**
	 * 网络数据请求方法
	 */
	public void postMsg() {
		handler.sendEmptyMessageDelayed(0,500);
	}
	/**
	 * 设置监听
	 */
	private void setListener() {
		
		v_LotCollection.getIv_menu().setOnClickListener(new ShowMenuClickListener());
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
			/**
			 * 判断得到数据是否正确
			 */
			if (msg.getData().getString("result").endsWith("true")) {
				/**
				 * 解析数据
				 */
				w_CoordiateCollection = TShowCoordinate.getParsJson(msg
						.getData().getString("jsonResult"));
				/**
				 * 将数据写入到适配器中
				 */
				infoAdapter = new PCinfoAdapter();
				/**
				 * 显示数据
				 */
				v_LotCollection.getLv_LotContent().setAdapter(infoAdapter);
			} else {
				/**
				 * 网络数据错误，显示错误信息
				 */
				Toast.makeText(getActivity(), msg.getData().getString("info"),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	/**
	 * 
	 * @ClassName: PCinfoAdapter 
	 * @Description: TODO 收藏坐标列表的适配器，用于显示数据
	 * @author lrk
	 * @date 2013-12-21 下午2:20:14 
	 *
	 */
	private class PCinfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return w_CoordiateCollection.getDatasource().size();
		}

		@Override
		public Object getItem(int position) {

			return w_CoordiateCollection.getDatasource().get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			/**
			 * 数据显示容器
			 */
			CollectionItem item = null;
			/**
			 * 判断是否是复用上一个容器
			 */
			if (convertView == null) {
				/**
				 * 创建容器
				 */
				item = new CollectionItem(getActivity());
				/**
				 * 实例化显示容器。
				 */
				convertView = item;
				/**
				 * 记录容器，下次使用时不需要创建
				 */
				convertView.setTag(item);
			} else {
				/**
				 * 复用容器
				 */
				item = (CollectionItem) convertView.getTag();
			}
			/**
			 * 将信息放入容器中，进行装载
			 */
			item.setTag(R.id.tag_location, position);
			item.initValue(w_CoordiateCollection.getDatasource().get(position),
					position);
			item.setOnClickListener(new PropertyItemClickListenter());
			item.setDelListener(new DelItemClickListenter(), position);
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
			/**
			 * 获得点击的坐标位置
			 */
			int position = (Integer) v.getTag(R.id.tag_location);
			/**
			 * 获得点击坐标的经度
			 */
			latitude = Double.valueOf(w_CoordiateCollection.getDatasource().get(position).getLatitude());
			/**
			 * 获得点击坐标的维度
			 */
			longitude = Double.valueOf(w_CoordiateCollection.getDatasource().get(position).getLongitude());
			
			/**
			 * 获得点击收藏坐标的名称
			 */
			localName = w_CoordiateCollection.getDatasource().get(position).getCoordname();
			/**
			 * 弹出提示框，
			 */
			dialog = new MyDialogView(getActivity());
			dialog.show();
			/**
			 * 获得该坐标附近的楼盘信息
			 */
			Thread thread = new Thread(new TLocalProperty(getActivity(),new PropertyListHandler(), latitude,
					longitude,
					null));
			/**
			 * 开始获取网络数据
			 */
			thread.start();

		}

	}
	/**
	 * 
	 * @ClassName: PropertyListHandler 
	 * @Description: TODO  楼盘信息解析类
	 * @author lrk
	 * @date 2013-12-21 下午2:28:11 
	 *
	 */
	private class PropertyListHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			/**
			 * 判断是否正确获取网络信息
			 */
			if(msg.getData().getString("result").equals("true"))
			{
				/**
				 * 解析JSON数据为对象信息
				 */
				W_LocalProperty w_LocalProperty  = TLocalProperty.getPaserJsonResult(msg.getData().getString("jsonResult"));
				
				/**
				 * 将信息写入到Intent对象中传递到百度地图的Activity中进行显示操作
				 */
				Intent intent = new Intent(getActivity(),Activity_BaiDuMap.class);
				
				intent.putExtra("propertyList", w_LocalProperty);//楼盘信息

				intent.putExtra("latitude", latitude);//坐标经度

				intent.putExtra("longitude", longitude);//坐标维度

				intent.putExtra("qu", localName);//坐标名称

				getActivity().startActivity(intent);//跳转到Activity
	
			}
			if(dialog.isShowing())//关闭提示窗口
			{
				dialog.dismiss();
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
			/**
			 * 获得要删除的信息位置
			 */
			final int position = (Integer) v.getTag(R.id.tag_location);
			/**
			 * 进行删除操作
			 */
			new AlertDialog.Builder(getActivity()).setTitle("删除收藏").setMessage("确定要删除这个坐标么？")
			.setPositiveButton("删除", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface onclickdialog, int which) {
					// TODO Auto-generated method stub
					Thread thread = new Thread(new TCollectCoordinate(getActivity(),
							new CollectionPropertyHandler(position),
							Double.valueOf(w_CoordiateCollection.getDatasource().get(position)
									.getLatitude()),
									Double.valueOf(w_CoordiateCollection.getDatasource().get(position)
											.getLongitude()),
											w_CoordiateCollection.getDatasource().get(position).getCoordname(),
											w_CoordiateCollection.getDatasource().get(position).getCityName()));
					thread.start();
					/**
					 * 提示正在删除
					 */
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
					
						w_CoordiateCollection.getDatasource().remove(position);
						infoAdapter.notifyDataSetChanged();
					 
				}
			} else {// 删除出错，显示信息
				Toast.makeText(getActivity(), "网络访问出错", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
