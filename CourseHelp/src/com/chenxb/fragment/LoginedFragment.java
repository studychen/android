package com.chenxb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chenxb.coursehelp.MyInfoActivity;
import com.chenxb.coursehelp.NewLoginActivity;
import com.chenxb.coursehelp.R;


public class LoginedFragment extends Fragment{
	
	private FragmentActivity thisActivity;
	private Button login;
	private View linearView;
	private View messageLayout;
	private TextView nameView;
	private TextView numberView;
	
	//这个是我们必须重写的方法，返回的VIew就是将来显示到界面上的内容
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,Bundle savedInstanceState) {
		messageLayout = 
				inflater.inflate(R.layout.fragment_logined,
				container, false);
		return messageLayout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		thisActivity = getActivity();
		initData();
	}

	private void initData() {
		nameView = (TextView) thisActivity.findViewById(R.id.logined_name);
		numberView = (TextView) thisActivity.findViewById(R.id.logined_number);
		linearView = thisActivity.findViewById(R.id.linearview_logined);
		linearView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toMyinfoView();
			}
			private void toMyinfoView() {
				Intent intent = new Intent(thisActivity, MyInfoActivity.class);
				startActivity(intent);
			}
		});
		SharedPreferences sharePrefer = thisActivity.
				getSharedPreferences("users", Context.MODE_WORLD_READABLE );
		Editor editorPrefer = sharePrefer.edit();
		if ( sharePrefer.getBoolean("isLogin", false) ) {
			nameView.setText(sharePrefer.getString("name", "未登录"));
			//密码暂时不显示
//			numberView.setText(sharePrefer.getString("pwd", "未登录"));
		}
	}
	
}
