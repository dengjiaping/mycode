package com.wufangnao.activity.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;
import com.wufangnao.WuFangNaoApplication;
import com.wufangnao.activity.view.View_Feedback;
import com.wufangnao.constant.S_ActionInfo;
import com.wufangnao.constant.S_UserInfo;
/**
 * 
 * @ClassName: Fragment_Feedback 
 * @Description: TODO 用户反馈页面
 * @author lrk
 * @date 2013-12-17 下午4:31:24 
 *
 */
public class Fragment_Feedback extends Fragment {
	/**
	 * 用户反馈页面组件封装类
	 */
	private View_Feedback Feedback;
	/**
	 * 友盟用户反馈方法
	 */
	private FeedbackAgent agent;
	/**
	 * 友盟用户反馈回调类
	 */
	private Conversation conversation;
	/**
	 * 友盟封装用户信息类
	 */
	private UserInfo userInfo;
	/**
	 * 用户信息键值对
	 */
	private Map<String,String> infocontent;
	/**
	 * 提示窗口
	 */
	private Dialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initValue();//实例化对象
		initUser();//设置用户信息
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/**
		 * 提交反馈信息
		 */
		Feedback.getIv_submit().setOnClickListener(new SubmitOnclickListener());
		
		return Feedback.getView();
	}
	/**
	 * 实例化对象
	 */
	private void initValue()
	{
		Feedback = new View_Feedback(getActivity());
		agent = new FeedbackAgent(getActivity());
		dialog = new AlertDialog.Builder(getActivity()).setMessage("正在提交数据……").create();
		userInfo = new UserInfo();
		infocontent = new HashMap<String, String>();
	}
	/**
	 * 设置用户i型你想，暂时只有USER_ID
	 */
	private void initUser()
	{
		infocontent.put(S_UserInfo.USER_ID,WuFangNaoApplication.getUserId()+"");
		
	}
	/**
	 * 
	 * @ClassName: SubmitOnclickListener 
	 * @Description: TODO  提交意见与反馈按钮
	 * @author lrk
	 * @date 2013-12-19 下午1:27:32 
	 *
	 */
	private class SubmitOnclickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			/**
			 * 获取用户联系方式
			 */
			String user_contact = Feedback.getEt_contact().getText().toString();
			/**
			 * 判断联系方式是否可用
			 */
			if(user_contact==null||user_contact.replaceAll(" ","").equals(""))
			{
				Toast.makeText(getActivity(), "联系方式不能为空",Toast.LENGTH_SHORT).show();
				return;
			}
			else {
				if(!user_contact.matches(S_ActionInfo.S_EMAILADDRESS))
				{
					Toast.makeText(getActivity(), "邮箱地址不正常",Toast.LENGTH_SHORT).show();
					return;
				}
			}
			/**
			 * 获得用户输入信息
			 */
			String contact_content = Feedback.getEt_contactContent().getText().toString();
			/**
			 * 判断信息是否可用
			 */
			if(contact_content==null||contact_content.replaceAll(" ","").equals(""))
			{
				Toast.makeText(getActivity(), "请输入您宝贵的意见和建议",Toast.LENGTH_SHORT).show();
				return;
			}
			/**
			 * 添加用户信息
			 */
			infocontent.put(S_UserInfo.USER_CONTACT,user_contact);
			/**
			 * 添加用户信息
			 */
			userInfo.setContact(infocontent);
			/**
			 * 写入用户信息
			 */
			agent.setUserInfo(userInfo);
			dialog.show();
			conversation = agent.getDefaultConversation();
			conversation.addUserReply(contact_content);
			conversation.sync(new SyncListener() {
				
				@Override
				public void onSendUserReply(List<Reply> replys) {
					
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
					
					Feedback.getEt_contact().setText("");
					Feedback.getEt_contactContent().setText("");
					Toast.makeText(getActivity(),"意见与反馈提交成功", Toast.LENGTH_SHORT).show();
					
					
				}
				
				@Override
				public void onReceiveDevReply(List<DevReply> devReplies) {
					
					
				}
			});
		}
		
	}
	

}
