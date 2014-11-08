package com.wufangnao.activity.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.wufangnao.DBManager.PullParseService;
import com.wufangnao.activity.view.View_LocalChoice;
import com.wufangnao.adapter.CityAdapter;
import com.wufangnao.combination.CenterToast;
import com.wufangnao.combination.SideBar.OnTouchingLetterChangedListener;
import com.wufangnao.item.City;
import com.wufangnao.item.CityListItem;
import com.wufangnao.item.Sort;
import com.wufangnao.manger.CharacterParser;
import com.wufangnao.manger.PinyinComparator;

/**
 * 地域选择界面，用户通过选择自己想要查看的地域来查看该地域的楼盘信息
 * 
 * @author lrk
 * 
 */
public class Fragment_LocalChoice extends Fragment {

	/**
	 * 地域选择页面
	 */
	private View_LocalChoice v_LocalChoice = null;

	private CenterToast toast;

	private List<CityListItem> cityListItems;

	private CityAdapter cityAdapter;
	
	private CharacterParser characterParser;

	private PinyinComparator pinyinComparator;
	
	private int expanded = -1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		/**
		 * 初始化参数
		 */
		initCity();
		initValue();
		/**
		 * 设置监听事件
		 */
	
		setListener();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return v_LocalChoice.getView();
	}

	private void initCity() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		InputStream inputStream;
		try {
			inputStream = getActivity().getAssets().open("citylist.xml");
			List<Sort> sorts = PullParseService.getBooks(inputStream);
			cityListItems = new ArrayList<CityListItem>();
			for (Sort sort : sorts) {
				for (City city : sort.getListCities()) {
					CityListItem item = new CityListItem();
					item.setAreas(city.getListAreas());
					item.setName(city.getName());
					String pinyin = characterParser.getSelling(sort.getName());
					String sortString = pinyin.substring(0, 1).toUpperCase();
					item.setSortLetters(sortString.toUpperCase());
					cityListItems.add(item);
				}
			}
			Collections.sort(cityListItems, pinyinComparator);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 实例化参数
	 */
	private void initValue() {
		/**
		 * 实例化组件信息
		 */
		v_LocalChoice = new View_LocalChoice(getActivity());
		
		cityAdapter = new CityAdapter(getActivity(), cityListItems);
		v_LocalChoice.getLv_citycontent().setAdapter(cityAdapter);
	}

	/**
	 * 设置监听事件
	 */
	private void setListener() {

		v_LocalChoice.getSb_SideBar().setTextView(
				v_LocalChoice.getTv_changedialog());

		// 设置右侧触摸监听
		v_LocalChoice.getSb_SideBar().setOnTouchingLetterChangedListener(
				new TouchingLetterChanged());
	}

	private class TouchingLetterChanged implements
			OnTouchingLetterChangedListener {
		@Override
		public void onTouchingLetterChanged(String s) {
			// 该字母首次出现的位置
			int position = cityAdapter.getPositionForSection(s.charAt(0));
		
			if (position != -1) {
				
				v_LocalChoice.getLv_citycontent().setSelection(position);
			}

		}
	}
	


}
