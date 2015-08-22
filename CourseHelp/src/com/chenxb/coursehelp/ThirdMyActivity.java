package com.chenxb.coursehelp;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.chenxb.adapter.ZakerPagerAdapter;
import com.chenxb.commom.StreamTool;
import com.chenxb.commom.UserUtils;
import com.chenxb.coursebean.ImageInfo;
import com.chenxb.fragment.LoginIndexFragment;
import com.chenxb.fragment.LoginedFragment;
import com.course.app.AppContext;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdMyActivity extends FragmentActivity {
	private ArrayList<ImageInfo> data; // 菜单数据
	private Button login;
	private FragmentManager fm ;
	private Fragment notLoginFragment;
	private Fragment okLoginedFragment;
	private Handler myHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_my_index);
		initData();
		changeLoginView();
	}
	
	private void initData() {
		fm = getSupportFragmentManager(); //fragment管理器
		myHandler = new Handler();
		notLoginFragment = new LoginIndexFragment();
		okLoginedFragment = new LoginedFragment();
		login = (Button) findViewById(R.id.ib_login_icon); 
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLogin(v);
			}
		});
		data = new ArrayList<ImageInfo>();
		data.add(new ImageInfo("个人资料", R.drawable.icon1, R.drawable.icon_bg01));
		data.add(new ImageInfo("课程列表", R.drawable.icon4, R.drawable.icon_bg03));
		data.add(new ImageInfo("课表安排", R.drawable.icon5, R.drawable.icon_bg02));
		data.add(new ImageInfo("军事频道", R.drawable.icon7, R.drawable.icon_bg01));
		data.add(new ImageInfo("ͨ娱乐八卦", R.drawable.icon9, R.drawable.icon_bg03));
		data.add(new ImageInfo("体育新闻", R.drawable.icon10, R.drawable.icon_bg02));
		ViewPager vpager = (ViewPager) findViewById(R.id.vPager);
		vpager.setAdapter(new ZakerPagerAdapter(ThirdMyActivity.this, data));
		vpager.setPageMargin(10);
		vpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
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
	
	//切换已经登录和未登录显示效果
	private void changeLoginView(){
		myHandler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences sharePrefer = 
				getSharedPreferences("users", Context.MODE_WORLD_READABLE );
				Editor editorPrefer = sharePrefer.edit();
				if (sharePrefer.getBoolean("isLogin", false) ) {
					String cookie = sharePrefer.getString("cookie", "errorcookie");
					if(UserUtils.isCookieUserful(cookie)) {
						FragmentTransaction ft1 = fm.beginTransaction();
						ft1.replace(R.id.islogin_info,okLoginedFragment);
						ft1.commit();
					}
				}
			}
		});
	}
	
	//切换已经登录和未登录显示效果
	private void onLogin(View btn){
		//跳转到登录页面
		//切换已经登录和未登录显示效果
		Intent intent = new Intent(this,NewLoginActivity.class);
		startActivity(intent);
	}
}
