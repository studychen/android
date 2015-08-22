package com.studyday.seenews;


import java.io.BufferedReader;


import com.chenxb.app.ui.BaseActivity;
import com.chenxb.commom.StringUtils;
import com.chenxb.commom.UIHelper;
import com.chenxb.newslist.NewsList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.chenxb.app.ui.XListView;
import com.chenxb.app.ui.XListView.IXListViewListener;
import cn.bidaround.youtui.YouTui;
import com.chenxb.app.ui.NewDataToast;
import com.chenxb.app.api.AppContext;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;




import com.chenxb.mybean.NewsListItem;
import com.example.service.FileService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.chenxb.app.ui.SlideMenuOther;
import com.chenxb.app.api.*;

public class MainActivity extends BaseActivity implements OnClickListener, IXListViewListener{

	private XListView mListView;
	private SlideMenuOther slideMenu;
	private TextView textView;
	private TextView textViewTitle;
	private SimpleAdapter adapterSimple;
	private Handler mHandler;
	private int flag; //决定了是哪个新闻列表 0 最新新闻 ，1 新闻 资讯，2 本科新闻
	private int newsListNum; //显示新闻有几个，最开始是0，然后10，然后20
//	private ProgressDialog dialog;
	List<Map<String, String>> listData; //保存首页的新闻列表数据 //全局listData
	View fullView ;
	
	private AppContext appContext;  // 全局Context
	
	private RelativeLayout loadingView; //主页面上的View，用来改变背景
	
	private RelativeLayout listDataEmptyView; //主页面上的View，咖啡表示没有收藏记录
	
	private long exitTime = 0; //再按一次退出电院新闻
	
	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	private final static int DATA_DATA_COLLECT = 0x003;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //不显示应用标题
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		textViewTitle = (TextView) findViewById(R.id.main_title);

		mListView = (XListView) findViewById(R.id.xListView);
		loadingView = (RelativeLayout) findViewById(R.id.full_view_loading);
		listDataEmptyView = (RelativeLayout) findViewById(R.id.listdata_empty);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		// 获取自定义的SlideMenuOther 继承自ViewGroup
		slideMenu = (SlideMenuOther) findViewById(R.id.slidemenu); 
		
		//这儿设置选中后颜色不变
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
	    
		//全局全屏view
		fullView = findViewById(R.id.full_view);
		appContext = (AppContext) getApplication();
		// 网络连接判断
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
		} 
		
		//第一次访问需要联网，获取最新数据
		newsListNum = 0;
		listData = appContext.getNewsListInter(R.id.last_news, false, newsListNum);
		flag =R.id.last_news;
			
		adapterSimple = new SimpleAdapter(this, listData, R.layout.newslist,
				new String[]{"title","time","read","url"},
				new int[]{R.id.title, R.id.time, R.id.read});
		mListView.setAdapter(adapterSimple); //列表新闻
		
		mListView.setOnItemClickListener(new itemClickListener()); //设置每个列表点击事件
		//应用装的时候就把刷新时间设置好
		mListView.setOrginalTime(StringUtils.RefreshDateString());
		
		//侧边栏的点击事件
		textView = (TextView) findViewById(R.id.last_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.notice_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.undergraduate_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.postgraduate_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.research_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.academic_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.net_news);
		textView.setOnClickListener(new menuClick());
		textView = (TextView) findViewById(R.id.menu_collect);
		textView.setOnClickListener(new menuClick());
		
		
//		Toast.makeText(MainActivity.this, "有网络连接", 1).show();
		
		//设置侧滑按钮 也是后退按钮
		findViewById(R.id.ib_back).setOnClickListener(this);
		
		
		
//		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//		intentFilter.addAction("com.chenxb.APPWIDGET_UPDATE");
//		TweetReceiver connectionReceiver = new TweetReceiver();
//		registerReceiver(connectionReceiver, intentFilter);
	    
		
		mHandler =  new Handler(); //为了下拉刷新和加载更多
		
	
	}
	

	public class itemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//启动线程访问新闻详情
			new Thread(new ItemThread(arg0, arg2)).start();
			//或者mHandler.postDelayed(new ItemThread(arg0, arg2),1);
		}
		
	}
	
	class ItemThread implements Runnable {
		private AdapterView<?> arg0; 
		private int arg2; 

		ItemThread(AdapterView<?> arg0, int arg2) {
			this.arg0 = arg0;
			this.arg2 = arg2;
		}
		@Override
		public void run() {
			Map<String, String> data = (Map<String, String>) arg0.getItemAtPosition(arg2);
	
			Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
			intent.putExtra("clickurl", data.get("url"));
			intent.putExtra("clicktitle", data.get("title"));
			startActivity(intent);
//			回调命令，返回另一个Activity
			//startActivityForResult(intent, 200);
			// TODO Auto-generated method stub
		}
	}
	
	//侧滑上的菜单监听  改变标题和改变选中的菜单颜色
	private class menuClick implements View.OnClickListener {
		@Override
		public  void onClick(final View v) {
			final menuSys menuSys = new menuSys();
			Thread thread = new Thread(new Runnable() {
			@Override
				public void run() {
					menuSys.hideMenu(v);
					menuSys.proDialog(v);
				}
			});
			thread.start();
			
		}
	}
	
	 //实现先切换到主页面，再显示正在加载的框
	 class menuSys {
		//改变每个侧滑菜单栏的主显示页面标题
			private void changeTitle(int id , boolean isSimple) {
				if(isSimple) {
					switch(id) {
					case R.id.last_news:
						textViewTitle.setText(R.string.last_news);
						break;
					case R.id.notice_news:
						textViewTitle.setText(R.string.notice_news);
						break;
					case R.id.undergraduate_news:
						textViewTitle.setText(R.string.undergraduate_news);
						break;
					case R.id.postgraduate_news:
						textViewTitle.setText(R.string.postgraduate_news);
						break;
					case R.id.research_news:
						textViewTitle.setText(R.string.research_news);
						break;
					case R.id.academic_news:
						textViewTitle.setText(R.string.academic_news);
						break;
					case R.id.net_news:
						textViewTitle.setText(R.string.net_news);
						break;
					case R.id.menu_collect:
						textViewTitle.setText(R.string.menu_collect);
						break;
					}
					
				} else {
					switch(id) {
					case R.id.last_news:
						listData = appContext.getNewsListInter(R.id.last_news, false, newsListNum);
//						setListDate(listData);
						
						break;
					case R.id.notice_news:
						listData = appContext.getNewsListInter(R.id.notice_news, false, newsListNum);
//						setListDate(listData);
						flag = R.id.notice_news;
						break;
					case R.id.undergraduate_news:
						listData = appContext.getNewsListInter(R.id.undergraduate_news,  false, newsListNum);
//						setListDate(listData);
						flag = R.id.undergraduate_news;
						break;
					case R.id.postgraduate_news:
						listData = appContext.getNewsListInter(R.id.postgraduate_news, false, newsListNum);
//						setListDate(listData);
						flag = R.id.postgraduate_news;
						break;
					case R.id.research_news:
						listData = appContext.getNewsListInter(R.id.research_news, false, newsListNum);
//						setListDate(listData);
						flag = R.id.research_news;
						break;
					case R.id.academic_news:
						listData = appContext.getNewsListInter(R.id.academic_news, false, newsListNum);
//						setListDate(listData);
						flag = R.id.academic_news;
						break;
					case R.id.net_news:
						listData = appContext.getNewsListInter(R.id.net_news, false, newsListNum);
//						setListDate(listData);
						flag = R.id.net_news;
						break;
					case R.id.menu_collect:
						listData = appContext.getNewsListCollect(appContext);
//						setListDate(listData);
						break;
					}
					
				}
				
				
			}
			
		 @SuppressLint("NewApi")
		public  synchronized void hideMenu(final View v) {
			 mHandler.post(new Runnable() {
					@Override
					public void run() {
						slideMenu.hideMenu();
						setMenuColor(v); 
						changeTitle(v.getId(),true);
						loadingViewSwitch(DATA_LOAD_ING);
					}
			 });
			 
			 //此处sleep是让slidemenu能够回到主页面
//			 try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		 }
		 
		 public  synchronized void proDialog(final View v) {
			//改变菜单栏的颜色
			 mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					changeTitle(v.getId(),false);
					//newsListNum = 0; 设定锚点
					newsListNum = 0;
					Message message = new Message();
                    message.what = 0;
                    message.arg1 = v.getId();
                    menuHandler.sendMessage(message);
					// TODO Auto-generated method stub
				}
			},2000);
		 }
	 }
	
	 private Handler menuHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			 if (msg.what == 0) {
				 //原来的会话框，后来改用其他
//	             dialog.dismiss();
				//把刷新时间设置好
				 //分情况，是点击的收藏按钮还是其他新闻按钮，
				 //若是收藏取消上拉和下拉功能
				 if(msg.arg1 != R.id.menu_collect) {
					 loadingViewSwitch(DATA_LOAD_COMPLETE); 
				 } else {
					 loadingViewSwitch(DATA_DATA_COLLECT);  
				 }
					 
				 mListView.setOrginalTime(StringUtils.RefreshDateString());
				 setListDate(listData);
//				 fullView.setVisibility(View.GONE);
//				 mListView.setEnabled(true);
//				 mListView.setPullLoadEnable(true);
			 }
		}
		 
	 };
	 
	 private void loadingViewSwitch(int type) {
    	switch (type) {
		case DATA_LOAD_ING:
			//清空mListView以前的数据
//			mListView.removeAllViewsInLayout();
			mListView.setVisibility(View.GONE);
			listDataEmptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.VISIBLE);
			fullView.setVisibility(View.GONE);
			break;
		case DATA_LOAD_COMPLETE:
			mListView.setPullLoadEnable(true);
			mListView.setPullRefreshEnable(true);
			listDataEmptyView.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			loadingView.setVisibility(View.GONE);
			break;
		 //若是收藏取消上拉和下拉功能
		case DATA_DATA_COLLECT:
			listDataEmptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
			mListView.setPullLoadEnable(false);
			mListView.setPullRefreshEnable(false);
			break;
		}
    }
	 
	
	/**
	 * 设置菜单的颜色
	 * 选中的变暗，其他的恢复默认颜色
	 */
	@SuppressLint("ResourceAsColor")
	private void setMenuColor(View v) {
		//<color name="transparent">#00000000</color>
		int tranColor  = getResources().getColor(R.color.transparent);
		
		//将所有的菜单恢复成原来的色彩
		textView = (TextView) findViewById(R.id.last_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.notice_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.undergraduate_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.postgraduate_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.research_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.academic_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.net_news);
		textView.setBackgroundColor(tranColor);
		textView = (TextView) findViewById(R.id.menu_collect);
		textView.setBackgroundColor(tranColor);
		//设置点击的菜单为暗色调
		v.setBackgroundColor(getResources().getColor(R.color.clickmenu));
	}
	
	//只有收藏会出现空记录的情况
	private void setListDate(List<Map<String, String>>  listData) {
		if(listData.size() > 0 ) {
			adapterSimple = new SimpleAdapter(this, listData, R.layout.newslist,
					new String[]{"title","time","read","url"},
					new int[]{R.id.title, R.id.time, R.id.read});
			mListView.setAdapter(adapterSimple); //列表新闻
		} else {
			mListView.setVisibility(View.GONE);
			listDataEmptyView.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//无网络连接 提示“检查网络”刷新后将数目初始点设置为0 ，loadmore里面newsListNum+=10
				//有网络连接 提示更新数目或者当前内容是最新
				if ( !appContext.isNetworkConnected() ) {
					NewDataToast.makeText(MainActivity.this,
							getString(R.string.network_not_connected))
							.show();
				} else {
					newsListNum = 0;
					List<Map<String, String>> oldlistData = listData; //保存旧数据
					switch(flag) {
					// 设为true，主动刷新类别
					case R.id.last_news:
						listData = appContext.getNewsListInter(R.id.last_news, true, 0);
						break;
					case R.id.notice_news:
						listData = appContext.getNewsListInter(R.id.notice_news, true, 0);
						break;
					case R.id.undergraduate_news:
						listData = appContext.getNewsListInter(R.id.undergraduate_news, true, 0);
						break;
					case R.id.postgraduate_news:
						listData = appContext.getNewsListInter(R.id.postgraduate_news, true, 0);
						break;
					case R.id.research_news:
						listData = appContext.getNewsListInter(R.id.research_news, true, 0);
						break;
					case R.id.academic_news:
						listData = appContext.getNewsListInter(R.id.academic_news, true, 0);
						break;
					case R.id.net_news:
						listData = appContext.getNewsListInter(R.id.net_news, true, 0);
						break;
					}
					
					setListDate(listData); //设置新闻数据
 
					int newdata = 0;  // 新加载数据-只有刷新动作才会使用到
					for (Map<String, String> freshdata : listData) {
						boolean flag = true;
						for (Map<String, String> olddata : oldlistData) {
							if((freshdata.get("url")).equals(olddata.get("url"))) {
								flag = false;
								break;
							}
						}
						if (flag) newdata++;
					}
					if (newdata > 0) {
						NewDataToast.makeText(MainActivity.this,
										getString(R.string.new_data_toast_message,
												newdata)).show();
					} else {
						NewDataToast.makeText(MainActivity.this,
								getString(R.string.new_data_toast_none)).show();
					}
				}
				onLoad();
				mListView.setEnabled(true);
				mListView.setPullLoadEnable(true);
				
//				mListView.stopRefresh();
				//主动刷新 设置刷新时间
				mListView.setOrginalTime(StringUtils.RefreshDateString());
			}
		}, 200);
	}
	
//	private void setRefreshTime() {
////		String oldtime = mListView.getOrginalTime();
////		mListView.setRefreshTime(StringUtils.friendly_time(oldtime));
//		mListView.setOrginalTime(StringUtils.RefreshDateString());
//	}
	
	/**
	 * 底部上拉刷新 查看更多
	 */
	@Override
	public void onLoadMore() {
//		//设置上次刷新时间
//		setRefreshTime();
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//设置刷新时间
				newsListNum += 10; //增加10条
				List<Map<String, String>> tempListData = new ArrayList<Map<String, String>>();
				switch(flag) {
				
				// 设为true，主动刷新类别
				//不对，加载更多应该是不刷新，读取缓存
				case R.id.last_news:
					tempListData = appContext.getNewsListInter(R.id.last_news, false, newsListNum);
					break;
				case R.id.notice_news:
					tempListData = appContext.getNewsListInter(R.id.notice_news, false, newsListNum);
					break;
				case R.id.undergraduate_news:
					tempListData = appContext.getNewsListInter(R.id.undergraduate_news, false, newsListNum);
					break;
				case R.id.postgraduate_news:
					tempListData = appContext.getNewsListInter(R.id.postgraduate_news, false, newsListNum);
					break;
				case R.id.research_news:
					tempListData = appContext.getNewsListInter(R.id.research_news, false, newsListNum);
					break;
				case R.id.academic_news:
					tempListData = appContext.getNewsListInter(R.id.academic_news, false, newsListNum);
					break;
				case R.id.net_news:
					tempListData = appContext.getNewsListInter(R.id.net_news, false, newsListNum);
					break;
				}
				
				if(tempListData !=null ) {
					//add 就可以，不能赋值，会切断和原本listData的联系
					listData.addAll(tempListData);
	//				setListDate(listData); //设置新闻数据
					adapterSimple.notifyDataSetChanged();
				} else {
					mListView.setPullLoadEnable(false);
					UIHelper.ToastMessage(MainActivity.this, "内容加载完毕");
				}
				
				onLoad();
			}
		}, 200);
	}
	
	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
//		mListView.setOrginalTime(StringUtils.RefreshDateString());
//		//将当前时间显示为刷新时间
//		setRefreshTime();
//		mListView.setRefreshTime(StringUtils.RefreshDateString());
	}
	
	
	//再按一次退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), R.string.back_exit_tips, Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            MainActivity.this.finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	//主页上的侧滑按钮 可以侧滑显示或者隐藏
	 public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ib_back:
        	if(slideMenu.isShow()) {
        		slideMenu.showMenu();
        		
//        		原本这个UnabledSimpleAdapter 是复写isEnabled方法
//        		class UnabledSimpleAdapter extends SimpleAdapter {
//
//					public UnabledSimpleAdapter(Context context,
//							List<? extends Map<String, ?>> data, int resource,
//							String[] from, int[] to) {
//						super(context, data, resource, from, to);
//						// TODO Auto-generated constructor stub
//					}
//					
//					@Override
//					public boolean isEnabled(int position) {
//						return false;
//					}
//        			
//        		}
//        		
//        		adapterSimple = new UnabledSimpleAdapter(this, listData, R.layout.newslist,
//        				new String[]{"title","time","read","url"},
//        				new int[]{R.id.title, R.id.time, R.id.read}); 
//        		mListView.setAdapter(adapterSimple); //列表新闻
        		
        		//这个已经在if语句里面了，肯定是menu在显示
        		mHandler.post(new Runnable() {

					@Override
					public void run() {
						mListView.setFocusable(false);
		        		mListView.setClickable(false);
		        		mListView.setEnabled(false);
		        		
		        		
		        		fullView.setVisibility(View.VISIBLE);
		        		fullView.setFocusable(true);
		        		fullView.setClickable(true);
		        		fullView.setEnabled(true);
		        		
		        		//设置 浮上来的全局背景 点击事件，让主页面出现，让列表可以刷新和显示详情
		        		fullView.setOnClickListener(new View.OnClickListener( ) {
							@Override
							public void onClick(View v) {
								v.setVisibility(View.GONE);
								slideMenu.hideMenu();
								mListView.setEnabled(true);
								// TODO Auto-generated method stub
							}
						});
							// TODO Auto-generated method stub
							
					}
    			}
    		);
        		
        		
        		
        		
        		
//        		LinearLayout view12 = (LinearLayout) findViewById(R.id.main_view);
//        		view12.setEnabled(false);
//        		mListView.setClickable(false);
//        		RelativeLayout view23 = (RelativeLayout) findViewById(R.id.editNews);
//        		view23.setEnabled(false);
//        		view23.setClickable(false);
        		
//        		TextView view = new TextView(this);
//        		view.setBackgroundColor(Color.RED);
//        		view.setMinWidth(500);
//        		view.setMinHeight(500);
//        		view.setText("HELLO");
//        		
//        		PopupWindow popupWindow = new PopupWindow(view);
//        		popupWindow.showAtLocation(slideMenu, Gravity.CENTER , 
//        				0, 0);
//        		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        		AlertDialog dialog = builder.setView(view).create();
//        		dialog.show();
//        		AlertDialog builder = new AlertDialog.Builder(this).create();
//        		
//        		 Window window = builder.getWindow();        
//        		 WindowManager.LayoutParams lp = window.getAttributes();        
//        		 // 设置透明度为0.3         
//        		 lp.alpha = 0.3f;        
//        		 window.setAttributes(lp);
//        		
////    		    android.view.ViewGroup.LayoutParams params = new LayoutParams();
//        		   WindowManager.LayoutParams layoutParams = window.getAttributes();  
//        	        layoutParams.width = 200;  
//        	        layoutParams.height = LayoutParams.WRAP_CONTENT; 
//        	        window.setAttributes(layoutParams);
////    		    WindowManager.LayoutParams lp1 =window.getAttributes();
////    		    builder.onWindowAttributesChanged(lp1);  
////                lp1.x=20;
////                lp1.y=0;  
////    		    
////设置后面数据模糊效果
////       		 window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,      
////				 WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
////    		    params.height =-85;//设置x坐标
////
////    		    params.width =-105;//设置y坐标
////    		    window.setAttributes(params);
//        		    
//        		    builder.show();
////        		PopupWindow popupWindow = new PopupWindow(this);
//        		popupWindow.showAtLocation(view12, Gravity.CENTER , 
//        				10, view12.getHeight());

            } else {
            	slideMenu.hideMenu();
            	mHandler.post(new Runnable() {

					@Override
					public void run() {
						fullView.setVisibility(View.GONE);
		            	mListView.setEnabled(true);
						// TODO Auto-generated method stub
					}
            	});
//            	LinearLayout view12 = (LinearLayout) findViewById(R.id.main_view);
//            	view12.setEnabled(true);
            }
        	break;
        default:
            break;
        }
    }

//  回调另一个Activity返回数据
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		String result = data.getStringExtra("result");
//		Toast.makeText(this, result + requestCode + resultCode, 1).show();
//	}
	
	
}

	 