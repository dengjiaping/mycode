package com.wufangnao.item;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wufangnao.R;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.webInterface.TCollectionProperty;
import com.wufangnao.webInterface.TProMessage;
import com.wufangnao.webInterface.mode.W_LocalProperty_Property;
import com.wufangnao.webInterface.mode.W_PropertyInfo;
/**
 * 楼盘详细信息
 * @ClassName: PropertyInfo 
 * @Description: TODO 
 * @author lrk
 * @date 2014-3-7 上午11:02:01 
 *
 */
public class PropertyInfo {

	private View v_propertyinfo;

	private TextView tv_city;
	private TextView tv_projjectName;
	private TextView tv_projectAdress;
	private TextView tv_newType;
	private TextView tv_housePrice;
	private TextView tv_houseType;
	private TextView tv_decorationStatus;
	private TextView tv_parkingSpace;
	private TextView tv_openTime;
	private TextView tv_checkinTime;
	private TextView tv_numberOfHourse;
	private TextView tv_areaCovered;
	private TextView tv_greeningRate;
	private TextView tv_volumeRate;
	private TextView tv_totalAreacovered;
	private TextView tv_areaYear;
	private TextView tv_poolRate;
	private TextView tv_propertyCost;
	private TextView tv_presellInf;
	private TextView tv_presalePermit;
	private TextView tv_traffic;
	private TextView tv_propertyCompany;
	private TextView tv_developers;
	private TextView tv_addressSales;
	private TextView tv_telephoneSales;
	private LinearLayout llyt_city;
	private LinearLayout llyt_projjectName;
	private LinearLayout llyt_projectAdress;
	private LinearLayout llyt_newType;
	private LinearLayout llyt_housePrice;
	private LinearLayout llyt_houseType;
	private LinearLayout llyt_decorationStatus;
	private LinearLayout llyt_parkingSpace;
	private LinearLayout llyt_openTime;
	private LinearLayout llyt_checkinTime;
	private LinearLayout llyt_numberOfHourse;
	private LinearLayout llyt_areaCovered;
	private LinearLayout llyt_greeningRate;
	private LinearLayout llyt_volumeRate;
	private LinearLayout llyt_totalAreacovered;
	private LinearLayout llyt_areaYear;
	private LinearLayout llyt_poolRate;
	private LinearLayout llyt_propertyCost;
	private LinearLayout llyt_presellInf;
	private LinearLayout llyt_presalePermit;
	private LinearLayout llyt_traffic;
	private LinearLayout llyt_propertyCompany;
	private LinearLayout llyt_developers;
	private LinearLayout llyt_addressSales;
	private LinearLayout llyt_telephoneSales;
	private ImageView iv_collectionProperty;
	private LinearLayout llyt_propertyMessage;
	private RelativeLayout rlyt_propertyProgress;
	private Context context;
	private boolean isFavo;
	private int position;

	public PropertyInfo(Context context) {
		this.context = context;
		v_propertyinfo = LayoutInflater.from(context).inflate(
				R.layout.propertymessage_content_layout, null);
		tv_city = (TextView) v_propertyinfo.findViewById(R.id.tv_property_city);
		tv_projjectName = (TextView) v_propertyinfo
				.findViewById(R.id.tv_propertyproject_name);
		tv_projectAdress = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_address);
		tv_newType = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_newtype);
		tv_housePrice = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_houseprice);
		tv_houseType = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_housetype);
		tv_decorationStatus = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_decorationstatus);
		tv_parkingSpace = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_parkingspace);
		tv_openTime = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_opentime);
		tv_checkinTime = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_checkintime);
		tv_numberOfHourse = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_numberofhourse);
		tv_areaCovered = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_areacovered);
		tv_greeningRate = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_greeningrate);
		tv_volumeRate = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_volumerate);
		tv_totalAreacovered = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_totalareacovered);
		tv_areaYear = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_areayear);
		tv_poolRate = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_poolrate);
		tv_propertyCost = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_propertycost);
		tv_presellInf = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_presellInf);
		tv_presalePermit = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_presalePermit);
		tv_traffic = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_traffic);
		tv_propertyCompany = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_propertycompany);
		tv_developers = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_developers);
		tv_addressSales = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_addressSales);
		tv_telephoneSales = (TextView) v_propertyinfo
				.findViewById(R.id.tv_property_telephonesales);
		llyt_city = (LinearLayout) v_propertyinfo.findViewById(R.id.llyt_city);
		llyt_projectAdress = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_address);
		llyt_newType = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_newtype);
		llyt_housePrice = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_houseprice);
		llyt_houseType = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_housetype);
		llyt_decorationStatus = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_decorationstatus);
		llyt_parkingSpace = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_parkingspace);
		llyt_openTime = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_opentime);
		llyt_checkinTime = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_checkintime);
		llyt_numberOfHourse = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_numberofhourse);
		llyt_areaCovered = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_areacovered);
		llyt_greeningRate = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_greeningrate);
		llyt_volumeRate = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_volumerate);
		llyt_totalAreacovered = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_totalareacovered);
		llyt_areaYear = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_areayear);
		llyt_poolRate = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_poolrate);
		llyt_propertyCost = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_propertycost);
		llyt_presellInf = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_presellInf);
		llyt_presalePermit = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_presalePermit);
		llyt_traffic = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_traffic);
		llyt_propertyCompany = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_propertycompany);
		llyt_developers = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_developers);
		llyt_addressSales = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_addressSales);
		llyt_telephoneSales = (LinearLayout) v_propertyinfo
				.findViewById(R.id.llyt_telephonesales);
		iv_collectionProperty = (ImageView) v_propertyinfo
				.findViewById(R.id.iv_map_collectionproperty);
		llyt_propertyMessage = (LinearLayout)v_propertyinfo.findViewById(R.id.llyt_propertymessage);
		rlyt_propertyProgress = (RelativeLayout)v_propertyinfo.findViewById(R.id.llyt_propertyprogress);

	}

	public View getView() {
		return v_propertyinfo;
	}

	public void initValue(W_PropertyInfo propertyInfo,int position) {
		llyt_propertyMessage.setVisibility(View.GONE);
		rlyt_propertyProgress.setVisibility(View.VISIBLE);
		if(propertyInfo.getCity()==null||propertyInfo.getCity().equals(""))
		{
			Thread propertyMessgae = new Thread(new TProMessage(context, new ProMessageHandler(position), propertyInfo.getPropertyID()));
			propertyMessgae.start();
		}
		else {
			this.position = position;
			setPropertyInfo(propertyInfo);
		}
		
	}
	/**
	 * 
	* @ClassName: CollectionProperytClickListenr 
	* @Description: 响应收藏按钮点击事件，如果已收藏则取消收藏，未收藏则收藏楼盘信息
	* @author lrk
	* @date 2013-12-16 下午4:31:07 
	*
	 */
	private class CollectionPropertyClickListenr implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			
			Thread thread = new Thread(new TCollectionProperty(context,new CollectionPropertyHandler(),(Integer)v.getTag(R.id.tag_id)));
			thread.start();
		}
		
	}
	/**
	 * 
	* @ClassName: CollectionPropertyHandler 
	* @Description: 解析服务器返回信息，是否操作成功
	* @author lrk
	* @date 2013-12-16 下午4:36:21 
	*
	 */
	private class CollectionPropertyHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.getData().getString("result").equals("true"))
			{
				if(isFavo)
				{
					isFavo = false;
					iv_collectionProperty.setImageResource(R.drawable.ic_uncollection);
					Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
				}
				else {
					isFavo = true;
					iv_collectionProperty.setImageResource(R.drawable.ic_collection);
					Toast.makeText(context, "添加收藏", Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent(S_ActionInfo.S_CHANGEFAVO);
				intent.putExtra("position", position);
				intent.putExtra("favo", isFavo);
				context.sendBroadcast(intent);
				
				
			}
			else {
				Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
			
			}
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
		private int position;
		public ProMessageHandler(int position)
		{
			this.position = position;
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println(msg.getData().getString("result"));
			if(msg.getData().getString("result").equals("true"))
			{
			
				PropertyInfo.this.position = position;
				W_LocalProperty_Property property= TProMessage.getParesJson(msg.getData().getString("jsonResult"));
				setPropertyInfo(property.getDatasource());
				Intent intent = new Intent(S_ActionInfo.S_REFESHDATA);
				intent.putExtra("position",position);
				intent.putExtra("PropertyMessage",property.getDatasource());
				context.sendBroadcast(intent);
				
			}
		}
	}
	
	private void setPropertyInfo(W_PropertyInfo propertyInfo)
	{
		rlyt_propertyProgress.setVisibility(View.GONE);
		llyt_propertyMessage.setVisibility(View.VISIBLE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(propertyInfo.getCity()==null||propertyInfo.getCity().equals(""))
		{
			llyt_city.setVisibility(View.GONE);
		}
		else {
			llyt_city.setVisibility(View.VISIBLE);
			tv_city.setText(propertyInfo.getCity());
		}
		
		tv_projjectName.setText(propertyInfo.getProjjectName());
		if(propertyInfo.getProjectAdress()==null||propertyInfo.getProjectAdress().equals(""))
		{
			llyt_projectAdress.setVisibility(View.GONE);
		}
		else {
			llyt_projectAdress.setVisibility(View.VISIBLE);
			tv_projectAdress.setText(propertyInfo.getProjectAdress());
		}
		if(propertyInfo.getNewType()==null||propertyInfo.getNewType().equals(""))
		{
			llyt_newType.setVisibility(View.GONE);
		}
		else {
			llyt_newType.setVisibility(View.VISIBLE);
			tv_newType.setText(propertyInfo.getNewType());
		}
		if(propertyInfo.getHousePrice()==null||propertyInfo.getHousePrice().equals(""))
		{
			llyt_housePrice.setVisibility(View.GONE);
		}
		else {
			llyt_housePrice.setVisibility(View.VISIBLE);
			tv_housePrice.setText(propertyInfo.getHousePrice());
		}
		if(propertyInfo.getHouseType()==null||propertyInfo.getHouseType().equals(""))
		{
			llyt_houseType.setVisibility(View.GONE);
		}
		else {
			llyt_houseType.setVisibility(View.VISIBLE);
			tv_houseType.setText(propertyInfo.getHouseType());
		}
		if(propertyInfo.getDecorationStatus()==null||propertyInfo.getDecorationStatus().equals(""))
		{
			llyt_decorationStatus.setVisibility(View.GONE);
		}
		else {
			llyt_decorationStatus.setVisibility(View.VISIBLE);
			tv_decorationStatus.setText(propertyInfo.getDecorationStatus());
		}
		if(propertyInfo.getParkingSpace()==null||propertyInfo.getParkingSpace().equals(""))
		{
			llyt_parkingSpace.setVisibility(View.GONE);
		}
		else {
			llyt_parkingSpace.setVisibility(View.VISIBLE);
			tv_parkingSpace.setText(propertyInfo.getParkingSpace());
		}
		if(propertyInfo.getShowopenTime()==null||propertyInfo.getShowopenTime().equals(""))
		{
			llyt_openTime.setVisibility(View.GONE);
		}
		else {
			llyt_openTime.setVisibility(View.VISIBLE);
			tv_openTime.setText(propertyInfo.getShowopenTime());
		}
		if(propertyInfo.getShowcheckinTime()==null||propertyInfo.getShowcheckinTime().equals(""))
		{
			llyt_checkinTime.setVisibility(View.GONE);
		}
		else {
			llyt_checkinTime.setVisibility(View.VISIBLE);
			tv_checkinTime
			.setText(propertyInfo.getShowcheckinTime());
		}
		if(propertyInfo.getNumberOfHourse()==null||propertyInfo.getNumberOfHourse().equals(""))
		{
			llyt_numberOfHourse.setVisibility(View.GONE);
		}
		else {
			llyt_numberOfHourse.setVisibility(View.VISIBLE);
			tv_numberOfHourse.setText(propertyInfo.getNumberOfHourse());
		}
		if(propertyInfo.getAreaCovered()==null||propertyInfo.getAreaCovered().equals(""))
		{
			llyt_areaCovered.setVisibility(View.GONE);
		}
		else {
			llyt_areaCovered.setVisibility(View.VISIBLE);
			tv_areaCovered.setText(propertyInfo.getAreaCovered());
		}
		if(propertyInfo.getGreeningRate()==null||propertyInfo.getGreeningRate().equals(""))
		{
			llyt_greeningRate.setVisibility(View.GONE);
		}
		else {
			llyt_greeningRate.setVisibility(View.VISIBLE);
			tv_greeningRate.setText(propertyInfo.getGreeningRate());
		}
		if(propertyInfo.getVolumeRate()==null||propertyInfo.getVolumeRate().equals(""))
		{
			llyt_volumeRate.setVisibility(View.GONE);
		}
		else {
			llyt_volumeRate.setVisibility(View.VISIBLE);
			tv_volumeRate.setText(propertyInfo.getVolumeRate());
		}
		if(propertyInfo.getTotalAreacovered()==null||propertyInfo.getTotalAreacovered().equals(""))
		{
			llyt_totalAreacovered.setVisibility(View.GONE);
		}
		else {
			llyt_totalAreacovered.setVisibility(View.VISIBLE);
			tv_totalAreacovered.setText(propertyInfo.getTotalAreacovered());
		}
		if(propertyInfo.getAreaYear()==null||propertyInfo.getAreaYear().equals(""))
		{
			llyt_areaYear.setVisibility(View.GONE);
		}
		else {
			llyt_areaYear.setVisibility(View.VISIBLE);
			tv_areaYear.setText(propertyInfo.getAreaYear());
		}
		if(propertyInfo.getPoolRate()==null||propertyInfo.getPoolRate().equals(""))
		{
			llyt_poolRate.setVisibility(View.GONE);
		}
		else {
			llyt_poolRate.setVisibility(View.VISIBLE);
			tv_poolRate.setText(propertyInfo.getPoolRate());
		}
		if(propertyInfo.getPropertyCost()==null||propertyInfo.getPropertyCost().equals(""))
		{
			llyt_propertyCost.setVisibility(View.GONE);
		}
		else {
			llyt_propertyCost.setVisibility(View.VISIBLE);
			tv_propertyCost.setText(propertyInfo.getPropertyCost());
		}
		if(propertyInfo.getPresellInf()==null||propertyInfo.getPresellInf().equals(""))
		{
			llyt_presellInf.setVisibility(View.GONE);
		}
		else {
			llyt_presellInf.setVisibility(View.VISIBLE);
			tv_presellInf.setText(propertyInfo.getPresellInf());
		}
		if(propertyInfo.getPresalePermit()==null||propertyInfo.getPresalePermit().equals(""))
		{
			llyt_presalePermit.setVisibility(View.GONE);
		}
		else {
			llyt_presalePermit.setVisibility(View.VISIBLE);
			tv_presalePermit.setText(propertyInfo.getPresalePermit());
		}
		if(propertyInfo.getTraffic()==null||propertyInfo.getTraffic().equals(""))
		{
			llyt_traffic.setVisibility(View.GONE);
		}
		else {
			llyt_traffic.setVisibility(View.VISIBLE);
			tv_traffic.setText(propertyInfo.getTraffic());
		}
		if(propertyInfo.getPropertyCompany()==null||propertyInfo.getPropertyCompany().equals(""))
		{
			llyt_propertyCompany.setVisibility(View.GONE);
		}
		else {
			llyt_propertyCompany.setVisibility(View.VISIBLE);
			tv_propertyCompany.setText(propertyInfo.getPropertyCompany());
		}
		if(propertyInfo.getDevelopers()==null||propertyInfo.getDevelopers().equals(""))
		{
			llyt_developers.setVisibility(View.GONE);
		}
		else {
			llyt_developers.setVisibility(View.VISIBLE);
			tv_developers.setText(propertyInfo.getDevelopers());
		}
		if(propertyInfo.getAddressSales()==null||propertyInfo.getAddressSales().equals(""))
		{
			llyt_addressSales.setVisibility(View.GONE);
		}
		else {
			llyt_addressSales.setVisibility(View.VISIBLE);
			tv_addressSales.setText(propertyInfo.getAddressSales());
		}
		if(propertyInfo.getTelephoneSales()==null||propertyInfo.getTelephoneSales().equals(""))
		{
			llyt_telephoneSales.setVisibility(View.INVISIBLE);
		}
		else {
			llyt_telephoneSales.setVisibility(View.VISIBLE);
			tv_telephoneSales.setText(propertyInfo.getTelephoneSales());
		}
		
		
		if(propertyInfo.isFavo())//是否已经收藏楼盘信息
		{
			isFavo = true;
			iv_collectionProperty.setImageResource(R.drawable.ic_collection);
		}
		else {
			isFavo = false;
			iv_collectionProperty.setImageResource(R.drawable.ic_uncollection);
		}
		iv_collectionProperty.setTag(R.id.tag_id,propertyInfo.getPropertyID());
		iv_collectionProperty.setOnClickListener(new CollectionPropertyClickListenr());
	}
	


}
