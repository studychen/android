package com.chenxb.coursehelp;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
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
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.chenxb.commom.StreamTool;
import com.chenxb.commom.UserUtils;
import com.chenxb.coursebean.MyInformation;
import com.course.app.AppContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfoActivity extends Activity {
	private TextView name;
	private TextView number;
	private TextView colleage;
	private TextView subject;
	private TextView teacher;
	private TextView requirePoint;
	private TextView completePoint;
	private TextView requireDegreePoint;
	private TextView completeDegreePoint;
	private TextView gpa;
	private MyInformation myInfo;
	private String cookie;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_information);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		initData();
	}
	
	void initData() {
		 //初始化控件
		 name = (TextView) findViewById(R.id.name);
		 number = (TextView) findViewById(R.id.number);
		 colleage = (TextView) findViewById(R.id.colleage);
		 subject = (TextView) findViewById(R.id.subject);
		 teacher = (TextView) findViewById(R.id.teacher);
		 requirePoint = (TextView) findViewById(R.id.requirePoint);
		 completePoint = (TextView) findViewById(R.id.completePoint);
		 requireDegreePoint = (TextView) findViewById(R.id.requireDegreePoint);
		 completeDegreePoint = (TextView) findViewById(R.id.completeDegreePoint);
		 gpa = (TextView) findViewById(R.id.gpa);
		 button = (Button) findViewById(R.id.btn_cancel_login);
		 button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancaleLogin();
			}
		});
		 
		 cookie = getMycookie();
		 //通过cookie获取信息
		 myInfo = getMyInfo(cookie);
		 setData(myInfo);
	}

	protected void cancaleLogin() {
		//注销登录，清除数据，回到原始的未登录页面
		SharedPreferences sharePrefer = 
				getSharedPreferences("users", Context.MODE_WORLD_READABLE );
		Editor editorPrefer = sharePrefer.edit();
		editorPrefer.clear();
		editorPrefer.commit();
		Intent intent = new Intent(MyInfoActivity.this, MoreActivity.class);
		startActivity(intent);
	}

	//设置控件的值
	private void setData(MyInformation myInfo2) {
		 name.setText(myInfo2.getName());
		 number.setText(myInfo2.getNumber());
		 colleage.setText("院系：" + myInfo2.getColleage());
		 subject.setText("专业：" + myInfo2.getSubject());
		 teacher.setText("导师：" + myInfo2.getTeacher());
		 requirePoint.setText("额定学分：" + myInfo2.getRequirePoint());
		 completePoint.setText("已完成：" + myInfo2.getCompletePoint());
		 requireDegreePoint.setText("学位课额定学分：" + myInfo2.getRequireDegreePoint());
		 completeDegreePoint.setText("已完成："+ myInfo2.getCompleteDegreePoint());
		 gpa.setText("学位课加权平均分：" + myInfo2.getGpa());
	}

	private MyInformation getMyInfo(String cookie2) {
		return UserUtils.getMyInfo(cookie2);
	}

	private String getMycookie() {
		SharedPreferences sharePrefer = 
				getSharedPreferences("users", Context.MODE_WORLD_READABLE );
		Editor editorPrefer = sharePrefer.edit();
		if (sharePrefer.getBoolean("isLogin", false) ) {
			String tempcookie = sharePrefer.getString("cookie", "nullcookie");
			return tempcookie;
		}
		return "nullcookie";
	}
	
	
	

}
