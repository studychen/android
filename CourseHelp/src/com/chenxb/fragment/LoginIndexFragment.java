package com.chenxb.fragment;

import com.chenxb.coursehelp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginIndexFragment extends Fragment{
	
	private FragmentActivity thisActivity;
	private Button login;
	private View messageLayout;
	
	//这个是我们必须重写的方法，返回的VIew就是将来显示到界面上的内容
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,Bundle savedInstanceState) {
		messageLayout = 
				inflater.inflate(R.layout.new_login,
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
		// TODO Auto-generated method stub
		login = (Button) messageLayout.findViewById(R.id.btn_login); 
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogin(v);
			}
		});
	}
	
	private void onLogin(View v) {
		// TODO Auto-generated method stub
		thisActivity.onBackPressed();
		
	}

}
