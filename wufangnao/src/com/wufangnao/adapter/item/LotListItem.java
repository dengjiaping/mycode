package com.wufangnao.adapter.item;

import com.wufangnao.R;
import com.wufangnao.webInterface.mode.W_NearLand_Lot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LotListItem extends LinearLayout {
	private TextView tv_LotId;
	private TextView tv_LotUsed;
	private TextView tv_LotPayDate;
	private TextView tv_Price;
	public LotListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.lotlist_item_layout,this,true);
		tv_LotId = (TextView) findViewById(R.id.tv_lotlist_id);
		tv_LotUsed = (TextView) findViewById(R.id.tv_lotlist_used);
		tv_LotPayDate = (TextView)findViewById(R.id.tv_lotlist_paydate);
		tv_Price = (TextView)findViewById(R.id.tv_lotlist_price);
		
	}
	/**
	 * 初始化数据
	 * @param lot
	 */
	public void initValue(W_NearLand_Lot lot)
	{
		tv_LotId.setText(String.valueOf(lot.getLandid()));
		tv_LotUsed.setText(lot.getLandUse());
		tv_LotPayDate.setText(lot.getLandmaketime());
		tv_Price.setText(lot.getLandprice());
		
	}

}
