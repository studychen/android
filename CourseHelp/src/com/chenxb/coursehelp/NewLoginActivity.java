package com.chenxb.coursehelp;

import java.util.HashMap;
import java.util.Map;

import com.chenxb.commom.UserUtils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class NewLoginActivity  extends Activity  {
	
	private EditText et_username;
	private EditText et_userpwd;
	private CheckBox cbrp;
	private CheckBox cbal;
	private SharedPreferences sharePrefer;
	private Button btnlogin;
	private Editor editorPrefer;
	private String name;
	private String pwd;
	private boolean isLogin;
	private ProgressDialog dialog; // 延迟加载框
	private String cookie;  
	private static String errorString = "error";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_login);
		initData();
		
		btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginMain();
			}
		});
	}
	
	private void LoginMain() {
		//启动新的线程，为了按钮立刻返回
		new Thread(new Runnable() {
			@Override
			public void run() {
				name = et_username.getText().toString();
				pwd = et_userpwd.getText().toString();
				if (name == null || name.equals("") ) {
					// 弹出对话框（内容：“请输入Email或手机号”）
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(NewLoginActivity.this, "请输入学号", Toast.LENGTH_LONG).show();
						}
					});
					} else if (pwd == null || pwd.equals("")) {
					// 弹出对话框(内容："请输入密码")
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(NewLoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
							}
						});
					
				} else {
					//启动新的线程，让按钮尽快返回
					Message message = new Message();
		            message.what = 1;
		            mHandler.sendMessage(message);
				}
			}
		}).start();
	}
	
	//登录成功，跳转到原来页面
    private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			 if (msg.what == 0) {
                 //保存Preference
                 setSharePreference();
 				 Intent intent = new Intent(NewLoginActivity.this, MainActivity.class);
 				 intent.putExtra("changeTab3", true);
 				 startActivity(intent);
 				//消失对话框
                 dialog.dismiss();
			 } else if(msg.what == 1) {
				 
				 //没有标题就是一个简单的加载框
				 dialog = ProgressDialog.show(NewLoginActivity.this, "", "登录中...");
				 new Thread(new Runnable() {
						@Override
						public void run() {
							cookie = UserUtils.getCookie(name,pwd);
							isLogin = UserUtils.isCookieUserful(cookie);
							if (isLogin && !cookie.equals(errorString)) {
								// 登录成功,对话框消失，跳转至原来页面
								Message message = new Message();
					            message.what = 0;
					            mHandler.sendMessage(message);
							} else {
									
								// 帐号密码错误，对话框消失
								Message message = new Message();
					            message.what = 3;
					            mHandler.sendMessage(message);
							}
						}
					}).start(); 
			 } else {
				 dialog.dismiss();
				 Toast.makeText(NewLoginActivity.this, "账户或者密码错误", Toast.LENGTH_LONG).show();
			 }
		}
    };
	
	/**
	 * 设置SharePreference
	 */
	private void setSharePreference() {
		sharePrefer = getSharedPreferences("users", MODE_WORLD_READABLE);
		editorPrefer = sharePrefer.edit();
		editorPrefer.putString("name", name);
		editorPrefer.putString("pwd", pwd);
		editorPrefer.putString("cookie", cookie);
		editorPrefer.putBoolean("isLogin", isLogin);
		editorPrefer.commit();
	}

	private void initData() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_userpwd = (EditText) findViewById(R.id.et_userpwd);
		cbrp = (CheckBox) findViewById(R.id.cbrp);// 记住密码
		cbal = (CheckBox) findViewById(R.id.cbal);// 自动登录
		btnlogin = (Button) findViewById(R.id.btn_login);
//		changeBack();
	}

//	/**
//	 * 在获得焦点时改变编辑框背景
//	 * 
//	 * @param btn
//	 */
//	private void changeBack() {
//		et_username.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					et_username
//							.setBackgroundResource(R.drawable.bg_edit_selected);
//				} else {
//					et_username
//							.setBackgroundResource(R.drawable.bg_edit_unselected);
//				}
//
//			}
//		});
//		et_userpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					et_userpwd
//							.setBackgroundResource(R.drawable.bg_edit_selected);
//				} else {
//					et_userpwd
//							.setBackgroundResource(R.drawable.bg_edit_unselected);
//				}
//			}
//		});
//	}

}
