package com.wufangnao.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wufangnao.activity.view.View_Help;
/**
 * 
 * @ClassName: Fragment_Help 
 * @Description: TODO  帮助显示界面
 * @author lrk
 * @date 2013-12-17 下午4:29:03 
 *
 */
public class Fragment_Help extends Fragment {
	
	private View_Help help;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		help = new View_Help(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return help.getView();
	}

}
