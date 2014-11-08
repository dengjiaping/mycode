package com.wufangnao.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.wufangnao.R;

/**
 * 
 * @ClassName: View_Feedback
 * @Description: TODO 封装意见与反馈信息的组件
 * @author lrk
 * @date 2013-12-17 下午4:06:42
 * 
 */
public class View_Feedback {

	private View v_feedback;

	private EditText et_contact;

	private EditText et_contactContent;

	private ImageView iv_submit;

	public View_Feedback(Context context) {
		v_feedback = LayoutInflater.from(context).inflate(R.layout.feedback,
				null);

		et_contact = (EditText) v_feedback.findViewById(R.id.et_contact);

		et_contactContent = (EditText) v_feedback
				.findViewById(R.id.et_feedback_content);

		iv_submit = (ImageView) v_feedback
				.findViewById(R.id.iv_feedback_submit);

	}

	public View getView() {
		return v_feedback;
	}

	public EditText getEt_contact() {
		return et_contact;
	}

	public EditText getEt_contactContent() {
		return et_contactContent;
	}

	public ImageView getIv_submit() {
		return iv_submit;
	}

}
