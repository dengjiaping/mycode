package com.wufangnao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.wufangnao.R;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.activity.fragment.Fragment_CoordinateCollection;
import com.wufangnao.activity.fragment.Fragment_Feedback;
import com.wufangnao.activity.fragment.Fragment_Help;
import com.wufangnao.activity.fragment.Fragment_Index;
import com.wufangnao.activity.fragment.Fragment_LocalChoice;
import com.wufangnao.activity.fragment.Fragment_LocalProperty;
import com.wufangnao.activity.fragment.Fragment_PropertyCollection;
import com.wufangnao.activity.fragment.Fragment_SlidingMenu;
import com.wufangnao.constant.S_Fragmnet;
import com.wufangnao.manger.CharacterParser;
import com.wufangnao.manger.PinyinComparator;
import com.wufangnao.utils.InfoSharePreferenceUtil;
import com.wufangnao.webInterface.TUserFirstLogin;
import com.wufangnao.webInterface.mode.W_FristUserLogin;

/**
 * 入口Activiyt,进入application后的收割界面
 * 
 * @author lrk
 * @version 1.0
 */
public class Activity_Index extends SlidingFragmentActivity {

	/**
	 * 碎片管理类
	 */
	private FragmentManager fm_Manager;
	/**
	 * 碎片实物管理类
	 */
	private FragmentTransaction ft_Transaction;
	/**
	 * 首页界面
	 */
	private Fragment_Index f_Index;
	/**
	 * 地域选择界面
	 */
	private Fragment_LocalChoice f_LocalChoice;
	/**
	 * application类
	 */
	private WuFangNaoApplication app;
	/**
	 * 楼盘收藏显示界面
	 */
	private Fragment_PropertyCollection f_PropertyCollection;
	/**
	 * 坐标收藏界面
	 */
	private Fragment_CoordinateCollection f_CoordinateCollection;
	/**
	 * 帮助界面
	 */
	private Fragment_Help f_Help;
	/**
	 * 反馈页面
	 */
	private Fragment_Feedback f_Feedback;
	/**
	 * 楼盘信息页面
	 */
	private Fragment_LocalProperty f_LocalProperty;
	/**
	 * 数据库操作类
	 */
	private SQLiteDatabase sql_db = null;

	private CharacterParser characterParser;

	private PinyinComparator pinyinComparator;

	private Bundle propertyBundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("oncreate");
		/**
		 * 显示布局
		 */

		setContentView(R.layout.index_content);
		UmengUpdateAgent.update(this);
		findViewById();
		/**
		 * 初始化参数，并显示首页
		 */
		initValue();

		initSlideMenu();

		/**
		 * 设置监听事件
		 */

	}

	private void findViewById() {

		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// 如果您同时使用了手动更新和自动检查更新，请加上下面这句代码，因为这些配置是全局静态的。
		UmengUpdateAgent.setUpdateOnlyWifi(true);
		UmengUpdateAgent.setUpdateAutoPopup(true);
		UmengUpdateAgent.setUpdateListener(null);
		UmengUpdateAgent.setDownloadListener(null);
		UmengUpdateAgent.setDialogListener(null);
	}

	/**
	 * 设置菜单界面，内容页面，和地区选择界面
	 */
	private void initSlideMenu() {

		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);

		setBehindContentView(R.layout.flyt_menu);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.flyt_menu_content, new Fragment_SlidingMenu())
				.commit();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);

		sm.setSecondaryMenu(R.layout.flyt_index);
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.index_frame, new Fragment_LocalChoice()).commit();
	}

	/**
	 * 初始化参数
	 */
	private void initValue() {
		app = (WuFangNaoApplication) getApplication();
		fm_Manager = Activity_Index.this.getSupportFragmentManager();
		addFragment(f_Index, "Fragment_Index");
		if (InfoSharePreferenceUtil.readUserIdMsg(this) == -25535) {
			Thread thread = new Thread(new TUserFirstLogin(this, new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					/**
					 * 成功获得userId信息
					 */
					if (msg.getData().getString("result").endsWith("true")) {
						W_FristUserLogin fristUserLogin = TUserFirstLogin
								.getParsJson(msg.getData().getString(
										"jsonResult"));
						/**
						 * 保存用户ID到本地
						 */
						InfoSharePreferenceUtil.keepUserIDMsg(
								Activity_Index.this, fristUserLogin
										.getDatasource().getUserid());
						/**
						 * 保存用户ID到当前应用中
						 */
						System.out.println("id = "
								+ fristUserLogin.getDatasource().getUserid());
						WuFangNaoApplication.setUserId(fristUserLogin
								.getDatasource().getUserid());
					} else// 获取失败
					{

					}

				}
			}));
			thread.start();
		} else {// 读取本地保存的UserId

			WuFangNaoApplication.setUserId(InfoSharePreferenceUtil
					.readUserIdMsg(this));

		}
		System.out.println("userid = " + WuFangNaoApplication.getUserId());
	}

	/**
	 * 显示楼盘信息界面
	 */
	public void locationProperty(Bundle bundle) {
		propertyBundle = bundle;

		addFragment(f_LocalProperty, "Fragment_LocalProperty");

	}

	/**
	 * 显示主页
	 */
	public void indexClick() {

		addFragment(f_LocalProperty, "Fragment_LocalProperty");

	}

	/**
	 * 显示反馈页面
	 */
	public void feedbackClick() {

		addFragment(f_Feedback, "Fragment_Feedback");

	}

	/**
	 * 显示帮助页面
	 */
	public void helpOnClick() {

		addFragment(f_Help, "Fragment_Help");

	}

	/**
	 * 显示坐标收藏界面
	 */
	public void coordinateCollection() {

		addFragment(f_CoordinateCollection, "Fragment_CoordinateCollection");

	}

	/**
	 * 
	 * @ClassName: PropertyCollectionListenter
	 * @Description: TODO 楼盘收藏按钮点击响应事件，关闭左侧的选择栏，并跳转到到楼盘收藏界面
	 * @author lrk
	 * @date 2013-12-17 上午10:19:40
	 * 
	 */
	public void propertyCollection() {

		addFragment(f_PropertyCollection, "Fragment_PropertyCollection");
	}

	/**
	 * 监听返回按钮事件
	 */
	@Override
	public void onBackPressed() {
		/**
		 * 如果侧边栏开启则关闭侧边栏
		 */
		/*
		 * if (view_index.getSlyt_Layout().isLeftLayoutVisible()) {
		 * view_index.getSlyt_Layout().scrollToRightLayout(); return; }
		 */
		/**
		 * 检查回退栈的数量，如果只省下一个，则直接退出应用。
		 */
		if (app.getFragment_stack().size() > 2) {
			ft_Transaction = getSupportFragmentManager().beginTransaction();

			boolean isMain = true;
			while (isMain) {

				Fragment pop = app.getFragment_stack().pop();
				Fragment peek = app.getFragment_stack().peek();

				if (peek.equals(f_LocalProperty) || peek.equals(f_Index)) {
					ft_Transaction.setCustomAnimations(R.anim.fragment_enter,
							R.anim.fragment_exit);
					isMain = false;

				}
				ft_Transaction.hide(pop).show(peek);
				// view_index.getTv_title().setVisibility(View.INVISIBLE);

			}

			ft_Transaction.commit();

		} else {// 退出应用
			new AlertDialog.Builder(Activity_Index.this)
					.setTitle("提示：")
					.setMessage("是否要退出无房恼？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									/**
									 * 关闭地图管理方法
									 */
									app.destoryManager();
									/**
									 * 关闭定位方法
									 */
									app.stopLocDate();
									finish();
									System.exit(0);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).setCancelable(true).show();

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	public void addFragment(Fragment fragment, String name) {
		int position = -1;
		/**
		 * 如果fragment未被实例化
		 */
		find: if (fragment == null) {
			/**
			 * 实例化界面
			 */
			if (name.equals("Fragment_CoordinateCollection")) {
				fragment = new Fragment_CoordinateCollection();
				f_CoordinateCollection = (Fragment_CoordinateCollection) fragment;
				position = 0;
			} else if (name.equals("Fragment_Feedback")) {
				fragment = new Fragment_Feedback();
				f_Feedback = (Fragment_Feedback) fragment;
				position = 1;
			} else if (name.equals("Fragment_Help")) {
				fragment = new Fragment_Help();
				f_Help = (Fragment_Help) fragment;
				position = 2;
			} else if (name.equals("Fragment_LocalChoice")) {
				fragment = new Fragment_LocalChoice();
				f_LocalChoice = (Fragment_LocalChoice) fragment;
				position = 3;
			} else if (name.equals("Fragment_LocalProperty")) {
				fragment = new Fragment_LocalProperty();

				f_LocalProperty = (Fragment_LocalProperty) fragment;
				f_LocalProperty.setArguments(propertyBundle);
				position = 4;
			} else if (name.equals("Fragment_PropertyCollection")) {
				fragment = new Fragment_PropertyCollection();
				f_PropertyCollection = (Fragment_PropertyCollection) fragment;

				position = 5;
			} else if (name.equals("Fragment_Index")) {
				fragment = new Fragment_Index();
				f_Index = (Fragment_Index) fragment;
				position = 6;
			}
			/**
			 * 判断界面是否已经实例化并显示
			 */

			if (app.findFragment(S_Fragmnet.FRAGMENT_TAG[position])) {

				int location = app.getPosition();
				fragment = app.getFragment_stack().get(location);
				break find;
			}
			ft_Transaction = fm_Manager.beginTransaction();
			/**
			 * 界面切换动画
			 */

			ft_Transaction.setCustomAnimations(R.anim.fragment_right_in,
					R.anim.fragment_left_out);
			/**
			 * 添加到activity中，并显示
			 */
			ft_Transaction.add(R.id.rlyt_index_content, fragment,
					S_Fragmnet.FRAGMENT_TAG[position]);
			/**
			 * 隐藏之前的界面
			 */
			if (app.getFragment_stack().size() != 0)
				ft_Transaction.hide(app.getFragment_stack().peek());

			/**
			 * 提交事物
			 */
			ft_Transaction.commit();

			app.getFragment_stack().push(fragment);

		} else {
			/**
			 * 如果点击的fragment是第一个的话，不做任何操作
			 */
			if (app.getFragment_stack().peek().equals(fragment)) {

			} else {

				ft_Transaction = fm_Manager.beginTransaction();
				/**
				 * 界面切换动画
				 */
				ft_Transaction.setCustomAnimations(R.anim.fragment_right_in,
						R.anim.fragment_left_out);

				/**
				 * 地域选择页面已经在栈中
				 */
				if (app.findFragment(fragment.getTag().toString())) {

					/**
					 * 显示地域选择页面，隐藏前一个页面
					 */
					ft_Transaction.show(fragment)
							.hide(app.getFragment_stack().peek()).commit();
					app.popToTop(fragment);
				} else {
					/**
					 * 显示地域选择页面，隐藏前一个页面
					 */
					ft_Transaction.show(fragment)
							.hide(app.getFragment_stack().peek()).commit();
					/**
					 * 地域选择界面入栈
					 */
					app.getFragment_stack().push(fragment);
				}

			}
			/**
			 * 跳转时是否需要进行一定的操作
			 */
			if (fragment.getTag().equals("f_CoordinateCollection")) {
				/**
				 * 刷新收藏坐标信息
				 */
				((Fragment_CoordinateCollection) fragment).postMsg();
			} else if (fragment.getTag().equals("f_LocalProperty")) {
				Log.d("lrk", "调用 Fragment_LocalProperty");
				/**
				 * 定位坐标
				 */
				((Fragment_LocalProperty) fragment).setLocation(propertyBundle);
			} else if (fragment.getTag().equals("f_PropertyCollection")) {
				/**
				 * 刷新楼盘收藏信息
				 */
				((Fragment_PropertyCollection) fragment).postMsg();
			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
