package com.chenxb.coursehelp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.course.app.AppContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;


public class OldCourseActivity extends Activity {
	private WebView mWeb;
	private static final String courseUrl =  "http://210.27.12.1:90/student/selectcourse/myschedule.jsp";
	private AppContext appContext;  // 全局Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.old_course);
		appContext = (AppContext) getApplication();
		
		mWeb = (WebView) findViewById(R.id.webView);
		mWeb.getSettings().setJavaScriptEnabled(true);	
		mWeb.getSettings().setSupportZoom(true);
		mWeb.getSettings().setBuiltInZoomControls(true);
		mWeb.getSettings().setDefaultFontSize(15);
		String cookie = getIntent().getStringExtra("cookie");
		String body = getCourse(cookie);
		mWeb.loadDataWithBaseURL(null, body, "text/html",
				"utf-8", null);
		appContext.readCourse(body);
	}
	
	private String getCourse(String myCookie){
	    URL url;
		try {
			url = new URL(courseUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();  
			con.setRequestProperty("Cookie", myCookie);
			con.setDoOutput(true);
			System.out.println(con.getResponseCode());
			InputStream inputStr = con.getInputStream();
			return new String(com.chenxb.commom.StreamTool.read(inputStr), "GBK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return("会话超时，请重新登录");
	}

}
