package com.chenxb.old;


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

import com.chenxb.commom.StreamTool;
import com.chenxb.coursehelp.OldCourseActivity;
import com.chenxb.coursehelp.R;
import com.course.app.AppContext;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button loginButton;
	private EditText accEdit;
	private EditText pasEdit;
	private String account;
	private String password;
	private static final String tag = "CourseHelp";
	static CookieStore cookieStore = null;
	private Handler mHandler;
	private static final String indexUrl =  "http://210.27.12.1:90/student/index.jsp";
	private static final String courseUrl =  "http://210.27.12.1:90/student/selectcourse/myschedule.jsp";
	private static final String loginUrl =  "http://210.27.12.1:90/j_security_check";
	private static String errorString = "error";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		loginButton = (Button)findViewById(R.id.btnlogin);
		accEdit = (EditText)findViewById(R.id.account); 
		pasEdit = (EditText)findViewById(R.id.password); 
		
		account = pasEdit.getText().toString();
		password = pasEdit.getText().toString();
		mHandler = new Handler();
		
		LoginListen x = new LoginListen();
		loginButton.setOnClickListener(new LoginListen());
	}
	
	private final class LoginListen implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			mHandler.post(new Runnable(){
				@Override
				public void run() {
					try {
						postLogin(loginUrl);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
			});
			
			
		}
		
		private void postLogin(String url) throws IOException {
			  CookieManager manager = new CookieManager();
			   CookieHandler.setDefault(manager);
			    
			  //随便用一个错误的密码，获取一个jsessionid
			  RequestBody formBody = new FormEncodingBuilder()
				.add("j_username", "101")
				.add("j_password", "11")
				.build();
			  OkHttpClient client = new OkHttpClient();
			  Request request = new Request.Builder()
			      .url(url)
			      .addHeader("Referer",  "http://210.27.12.1:90/login.jsp" )
			      .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")
			      .post(formBody)
			      .build();
			  client.setFollowRedirects(false);
			  Response response = client.newCall(request).execute();
			  
			  System.out.println("isSuccessful----" + response.isSuccessful());
			  System.out.println("response.headers()----" +response.headers());
			  
			  String SetCookie = response.header("Set-Cookie");
			  String myCookie = SetCookie.substring(0,SetCookie.indexOf(";"));
			  System.out.println("myCookie----" +myCookie);
			  System.out.println(response.body().string());
			  
			  java.net.CookieStore cookieJar = manager.getCookieStore();
			    List<HttpCookie> cookies = cookieJar.getCookies();
			    System.out.println("cookies.size()----" + cookies.size());
			    System.out.println("cookies.get(0).toString()----" + cookies.get(0).toString());
			    for (HttpCookie cookie : cookies) {
			      System.out.println(cookie);
			    }
			    
			    
			  formBody = new FormEncodingBuilder()
			  .add("j_username", account)
				.add("j_password", password)
				.build();
			  
			  String lowerCookie = "jsessionid" + myCookie.substring(myCookie.indexOf("="));
			  String referUrl = "http://210.27.12.1:90/login.jsp;" 
					  + lowerCookie;
			  request = new Request.Builder()
		      .url(url)
		      .addHeader("Connection",  "keep-alive" )
		      .addHeader("Referer", referUrl)
		      .addHeader("Cookie", myCookie)
		      .post(formBody)
		      .build();
			  response = client.newCall(request).execute();
			  Log.i("ssss", myCookie);
			  
			  System.out.println("isSuccessful----" + response.isSuccessful());
			  System.out.println("headers----" + response.headers());
			  System.out.println(response.body().string());
			  Intent intent = new Intent(LoginActivity.this, OldCourseActivity.class);
              intent.putExtra("cookie", myCookie);
              startActivity(intent);
			}
		
	}

}
