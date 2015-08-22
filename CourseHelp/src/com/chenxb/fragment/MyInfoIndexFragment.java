package com.chenxb.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chenxb.adapter.ZakerPagerAdapter;
import com.chenxb.coursebean.ImageInfo;
import com.chenxb.coursehelp.NewLoginActivity;
import com.chenxb.coursehelp.R;


public class MyInfoIndexFragment extends Fragment{
	
	private View messageLayout;
	private ArrayList<ImageInfo> data; // 菜单数据
	private FragmentActivity thisActivity;
	private Button login;
	private FragmentManager fm ;
	private Fragment loginFragment;
	private Fragment loginedFragment;
	
	//这个是我们必须重写的方法，返回的VIew就是将来显示到界面上的内容
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,Bundle savedInstanceState) {
		messageLayout = 
				inflater.inflate(R.layout.fragment_my_index,
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
		fm = thisActivity.getSupportFragmentManager(); //fragment管理器
		loginFragment = new LoginIndexFragment();
		loginedFragment = new LoginedFragment();
		login = (Button) messageLayout.findViewById(R.id.ib_login_icon); 
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogin(v);
			}
		});
		data = new ArrayList<ImageInfo>();
		data.add(new ImageInfo("我的课程", R.drawable.icon1, R.drawable.icon_bg01));
		data.add(new ImageInfo("最新资讯", R.drawable.icon4, R.drawable.icon_bg03));
		data.add(new ImageInfo("科技频道", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("军事频道", R.drawable.icon7, R.drawable.icon_bg01));
		data.add(new ImageInfo("ͨ娱乐八卦", R.drawable.icon9, R.drawable.icon_bg03));
		data.add(new ImageInfo("体育新闻", R.drawable.icon10, R.drawable.icon_bg02));
		ViewPager vpager = (ViewPager) messageLayout.findViewById(R.id.vPager);
		vpager.setAdapter(new ZakerPagerAdapter(thisActivity, data));
		vpager.setPageMargin(10);
		vpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
//	private void onLogin(View btn){
//		//跳转到登录页面
//		FragmentTransaction ft1 = fm.beginTransaction();
//		ft1.replace(R.id.index_container,loginFragment );
//		ft1.addToBackStack(null);
//		ft1.commit();
//	}
	
	private void onLogin(View btn){
		//跳转到登录页面
		FragmentTransaction ft1 = fm.beginTransaction();
		ft1.replace(R.id.islogin_info,loginedFragment );
//		ft1.addToBackStack(null); //可以返回键得到
		ft1.commit();
	}

}
