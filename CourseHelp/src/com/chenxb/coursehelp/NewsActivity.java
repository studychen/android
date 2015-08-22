package com.chenxb.coursehelp;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.commom.StreamTool;
import com.chenxb.coursebean.CourseBean;
import com.course.app.AppContext;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsActivity extends Activity {
	private static final int colors[] = {
			Color.rgb(0xee,0xff,0xff),
			Color.rgb(0xf0,0x96,0x09),
			Color.rgb(0x8c,0xbf,0x26),
			Color.rgb(0x00,0xab,0xa9),
			Color.rgb(0x99,0x6c,0x33),
			Color.rgb(0x3b,0x92,0xbc),
			Color.rgb(0xd5,0x4d,0x34),
			Color.rgb(0xcc,0xcc,0xcc)
		};
	
	private AppContext appContext;  // 全局Context
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.course_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		appContext = (AppContext) getApplication();
		ArrayList<CourseBean[]> fitElment 
		 		=(ArrayList<CourseBean[]>) appContext.readObject("course.txt");
		LinearLayout[] linear = new LinearLayout[7];
		linear[0] = (LinearLayout)findViewById(R.id.ll1);
		linear[1] = (LinearLayout)findViewById(R.id.ll2);
		linear[2] = (LinearLayout)findViewById(R.id.ll3);
		linear[3] = (LinearLayout)findViewById(R.id.ll4);
		linear[4] = (LinearLayout)findViewById(R.id.ll5);
		linear[5] = (LinearLayout)findViewById(R.id.ll6);
		linear[6] = (LinearLayout)findViewById(R.id.ll7);
	    
        CourseBean[][] temp = new CourseBean[7][5];  
		 for (int j = 0; j < 5; j ++) {
			 CourseBean[] xx= fitElment.get(j);
			 for(int i = 0; i < 7; i ++) {
				 temp[i][j] = xx[i];
				 System.out.println(xx[i]);
			 }
		 }
		 System.out.println(temp.length);
		 Random random = new Random(47);    
		 for(int j = 0 ; j < 7; j ++) {
			 for(int i = 0 ; i <5 ; i++) {
				 int color = random.nextInt(8);
				 CourseBean every = temp[j][i];
				 if(every.isEmpty() ) {
					 setNoClass(linear[j], 2, 0);
				 } else {
					 setClass(linear[j], every.getName(),
							 every.getTeacher(), every.getClassroom(), 
							 every.getTime(), 2, color);
				 }
					 
			 }
		 }
	        
//	        //每天的课程设置
//	        setClass(ll1, "", "", "", "", 2, 0);
//	        setClass(ll1, "windows编程实践", "国软  4-503", "1-9周，每一周", "9:50-11:25", 2, 1);
//	        setNoClass(ll1, 3, 0);
//	        setClass(ll1, "概率论与数理统计", "国软  4-304", "1-15周，每一周", "14:55-17:25", 3, 2);
//	        setNoClass(ll1, 1, 0);
//	        setClass(ll1, "人文化学", "一区 3-404", "3-13周，每一周", "19:00-20:30", 2, 4);
//	        setNoClass(ll1, 1, 0);
//	        
//	        setClass(ll2, "大学英语", "国软 4-302", "1-18周，每一周", "8:00-9:35", 2, 3);
//	        setClass(ll2, "计算机组织体系与结构", "国软 4-204", "1-15，每一周", "9:50-12:15", 3, 5);
//	        setNoClass(ll2, 3, 0);
//	        setClass(ll2, "团队激励和沟通", "国软 4-204", "1-9周，每一周", "15:45-17:25", 2, 6);
//	        setNoClass(ll2, 1, 0);
//	        setClass(ll2, "中国近现代史纲要", "3区 1-327", "1-9周，每一周", "19:00-21:25", 3, 1);
//	        
//	        setNoClass(ll3, 2, 0);
//	        setClass(ll3, "中国近现代史纲要", "3区 1-328", "1-9周，每一周", "9:50-12:15", 3, 1);
//	        setNoClass(ll3, 1, 0);
//	        setClass(ll3, "体育（网球）", "信息学部 操场", "6-18周，每一周", "14:00-15:40", 2, 2);
//	        setNoClass(ll3, 3, 0);
//	        setClass(ll3, "当代政治与经济", "3区 1-501", "1-7周，每一周", "19:00-21:25", 3, 3);
//	        
//	        setClass(ll4, "计算机组织体系与结构", "国软 4-204", "1-15，每一周", "8:00-9:35", 2, 5);
//	        setClass(ll4, "数据结构与算法", "国软 4-304", "1-18周，每一周", "9:50-12:15", 3, 4);
//	        setNoClass(ll4, 1, 0);
//	        setClass(ll4, "面向对象程序设计（JAVA）", "国软 1-103", "1-18周，每一周", "14:00-16:30", 3, 5);
//	        setNoClass(ll4, 2, 0);
//	        setNoClass(ll4, 3, 0);
//	        
//	        setClass(ll5, "c#程序设计", "国软 4-102", "1-9周，每一周", "8:00-9:35", 2, 6);
//	        setClass(ll5, "大学英语", "国软 4-302", "1-18周，每一周", "9:50-11:25", 2, 3);
//	        setNoClass(ll5, 2, 0);
//	        setClass(ll5, "基础物理", "国软 4-304", "1-18周，每一周", "14:00-16:30", 3, 1);
//	        setNoClass(ll5, 2, 0);
//	        setClass(ll5, "手机应用分析与创意", "1区 5-103", "1-7周，每一周", "19:00-21:2", 3, 2);
	        
	    }
	    /**
	     * 设置课程的方法
	     * @param ll
	     * @param title 课程名称
	     * @param place 地点
	     * @param last 时间
	     * @param time 周次
	     * @param classes 节数
	     * @param color 背景色
	     */
	    void setClass(LinearLayout ll, String title, String place,
	    		String last, String time, int classes, int color)
	    {
	    	View view = LayoutInflater.from(this).inflate(R.layout.course_item, null);
	    	view.setMinimumHeight(dip2px(this,classes * 48));
	    	view.setBackgroundColor(colors[color]);
	    	((TextView)view.findViewById(R.id.title)).setText(title);
	        ((TextView)view.findViewById(R.id.place)).setText(place);
	        ((TextView)view.findViewById(R.id.last)).setText(last);
	        ((TextView)view.findViewById(R.id.time)).setText(time);
//	        //为课程View设置点击的监听器
//	        view.setOnClickListener(new OnClickClassListener());
	        TextView blank1 = new TextView(this);
	        TextView blank2 = new TextView(this);
	        blank1.setHeight(dip2px(this,classes));
	        blank2.setHeight(dip2px(this,classes));
	        ll.addView(blank1);
	        ll.addView(view);
	        ll.addView(blank2);
	    }
	    /**
	     * 设置无课（空百）
	     * @param ll
	     * @param classes 无课的节数（长度）
	     * 第几节，0，1，2，3，4
	     * 代表12节，34节、、、晚上
	     * @param color
	     */
	    void setNoClass(LinearLayout ll,int classes, int color)
	    {
	    	TextView blank = new TextView(this);
	    	if(color == 0)
	    		blank.setMinHeight(dip2px(this,classes * 50));
	    	blank.setBackgroundColor(colors[color]);
	    	ll.addView(blank);
	    }
	    //点击课程的监听器
	    class OnClickClassListener implements OnClickListener{
	    	
	    	public void onClick(View v) {
	    		// TODO Auto-generated method stub
	    		String title;
	    		title = (String) ((TextView)v.findViewById(R.id.title)).getText();
	    		Toast.makeText(getApplicationContext(), "你点击的是:" + title, 
	    				Toast.LENGTH_SHORT).show();
	    	}
		}
	    
	    public static int dip2px(Context context, float dpValue) {        
	    	final float scale = context.getResources().getDisplayMetrics().density;        
	    	return (int) (dpValue * scale + 0.5f);} /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	    
	    public static int px2dip(Context context, float pxValue) {        
	    	final float scale = context.getResources().getDisplayMetrics().density;        
	    	return (int) (pxValue / scale + 0.5f);}

}
