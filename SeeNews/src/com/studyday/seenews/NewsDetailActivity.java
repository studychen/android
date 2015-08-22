package com.studyday.seenews;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.studyday.seenews.R;

import com.chenxb.app.api.AppContext;
import com.chenxb.app.ui.BaseActivity;
import com.chenxb.app.ui.NewDataToast;
import com.chenxb.commom.UIHelper;
import com.chenxb.mybean.News;
import com.chenxb.newslist.NewsDetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chenxb.mybean.Collect;
import com.example.service.CollectService;
import com.example.service.MydbOpenHelper;

public class NewsDetailActivity extends BaseActivity{
	private TextView titleView;
	private TextView timeView;
	private TextView readView;
	private WebView webView;
	private ImageButton imageBack;
	private ImageView imageViewShare;
	private ImageView imageViewCollect;
	private TextView textBack;
	private GestureDetector gd;
	private boolean isFullScreen;
	private LinearLayout mHeader;
	private NewsDetail newsDetail;
	private News news;
	private String url;
	 private ProgressDialog dialog; // 加载框
	 private AppContext appContext;  // 全局Context
	 private String num; //http://see.xidian.edu.cn/html/news/7160.html 7160
	 private MydbOpenHelper dbopen; //http://see.xidian.edu.cn/html/news/7160.html 7160
	 private CollectService collectService; //http://see.xidian.edu.cn/html/news/7160.html 7160
	
	/** 全局web样式 */
	// 链接样式文件，代码块高亮的处理
	public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
			+ "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
			+ "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";
	public final static String WEB_STYLE = linkCss + "<style>* {font-size:18px;} p {color:#333;text-indent:2em;line-height:24px;} a {color:#3E62A6;} img {max-width:310px;display:table-cell;vertical-align:middle;margin-left:1em;} "
			+ "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
			+ "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";
	// "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //不显示应用标题
		setContentView(R.layout.newsdetail);
		
		appContext = (AppContext) getApplication();
		dbopen = new MydbOpenHelper(appContext);
		collectService = new CollectService(dbopen);

		
		//监听后退按钮的事件
		mHeader = (LinearLayout) findViewById(R.id.header);
		imageBack = (ImageButton) findViewById(R.id.ib_back_image);
		textBack = (TextView) findViewById(R.id.ib_back_text);
		imageViewShare = (ImageView) findViewById(R.id.blog_detail_footbar_share);
		imageViewCollect = (ImageView) findViewById(R.id.blog_detail_footbar_collect);
		imageBack.setOnClickListener(new BackListener());
		textBack.setOnClickListener(new BackListener());
		imageViewShare.setOnClickListener(shareClickListener);
		imageViewCollect.setOnClickListener(collectClickListener);
		titleView = (TextView) findViewById(R.id.title);
		timeView = (TextView) findViewById(R.id.time);
		readView = (TextView) findViewById(R.id.read);
		webView = (WebView) findViewById(R.id.content);
		
		url = getIntent().getStringExtra("clickurl");
		AppContext appState = ((AppContext)getApplicationContext());
		Matcher matcher = Pattern.compile("\\d+").matcher(url);
		num = "7000";
	    while (matcher.find()) {  
	        num =  matcher.group(0);  
	    }
		// 注册双击全屏事件
		this.regOnDoubleEvent();
				
		initView();
		dialog = ProgressDialog.show(this, "加载中...", "请稍候");
        Thread thread = new Thread(new Runnable() {
                public void run() {
            		//不是电院调用新浪的网址
            		if (!appContext.isDianyuan()) {
            		    url = "http://seenews.sinaapp.com/jsp/detail.jsp?num=" + num ;
            		}
            		
            		newsDetail = new NewsDetail(url);
            		try { 
            			//联网情况下改变标题
            			if ( appContext.isNetworkConnected() ) {
            				titleView.setText(getIntent().getStringExtra("clicktitle"));
            			}
            			timeView.setVisibility(View.INVISIBLE);  //不显示，但是占位置
            			readView.setVisibility(View.INVISIBLE);  //不显示，但是占位置
//            			webView.setVisibility(View.INVISIBLE);  //不显示，但是占位置
//            			textView.setVisibility(View.GONE); //彻底不显示，也不占位置
            			
            			news = newsDetail.loadNewsDetail();
            		} catch (Exception e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace(System.err);
            		}
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
        });
        thread.start();
		
		
		
	}
	
	private void initView() {
		//初始化，让红心收藏显示
		if(collectService.isCollect(Integer.valueOf(num))) {
			imageViewCollect.setSelected(true);
		} else {
			imageViewCollect.setSelected(false);
		}
		
	}
	
	//处理跳转到新闻详情页面
    private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    Log.d(">>>>>Mhandler", "开始handleMessage");
//                    Log.d(">>>>>Mhandler", news.toString());
//                    LoadingActivity.this.finish();
//                    Intent mIntent = new Intent();
//                    mIntent.setClass(LoadingActivity.this, NewsDetailActivity.class);
//                    mIntent.putExtra("clickurl",url);
//                    startActivity(mIntent);
                    
                    Log.d(">>>>>Mhandler", "LoadActivity关闭");
                    if (msg.what == 0) {
                        dialog.dismiss();
                        if ( !appContext.isNetworkConnected() ) {
//        					NewDataToast.makeText(NewsDetailActivity.this,
//        							getString(R.string.network_not_connected))
//        							.show();
        				
        				} else {
        					timeView.setVisibility(View.VISIBLE);  
                			readView.setVisibility(View.VISIBLE);  
                          
                			timeView.setText(news.getPubDate());
                			readView.setText("浏览次数："+ String.valueOf(news.getReadCount()));
                			String body = WEB_STYLE + news.getBody();
                			
                			//因为我sae已经添加了图片支持
//                			body = body.replaceAll(
//                					"(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
//                			body = body.replaceAll(
//                					"(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
//                			body = body.replaceAll(
//                					"src=\"", "src=\"http://see.xidian.edu.cn/");
                //
//                			// 添加点击图片放大支持
//                			body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
//                					"$1$2\" onClick=\"javascript:mWebViewImage" +
//                					"Listener.onImageClick('$2')\"");
//                			body += "<div style='margin-bottom: 80px'/>";
                			Log.i("http", body);
                			
                			webView.getSettings().setJavaScriptEnabled(true);	
                			webView.getSettings().setSupportZoom(true);
                			webView.getSettings().setBuiltInZoomControls(true);
                			webView.getSettings().setDefaultFontSize(15);
                			webView.loadDataWithBaseURL(null, body, "text/html",
                					"utf-8", null);
        					
        				}
                    	
                }
            }
    };
    
	private View.OnClickListener shareClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			if (news.getBody() == null ) {
				UIHelper.ToastMessage(v.getContext(),
						R.string.msg_read_detail_fail);
				return;
			} else {
				
//			UIHelper.ToastMessage(NewsDetailActivity.this, url);
			// 分享到
			UIHelper.showShareDialog(NewsDetailActivity.this, news.getTitle(),
					 url); 
			}
		}
	};
	
	private View.OnClickListener collectClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			//如果选中了，而且是收藏了
			if(v.isSelected() || Collect.isCollected(Integer.valueOf(num))) {
				//这边是取消收藏
				v.setSelected(false);
				collectService.cancelCollect(Integer.valueOf(num));
				UIHelper.ToastMessage(NewsDetailActivity.this, R.string.collect_cancel);
			}else { //如果没有选中
				
				//这边是收藏
				v.setSelected(true);
				collectService.setCollect(Integer.valueOf(num));
				UIHelper.ToastMessage(NewsDetailActivity.this, R.string.collect_success);
			}
		}
	};
	
	/**
	 * 注册双击全屏事件
	 */
	private void regOnDoubleEvent() {
		gd = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						isFullScreen = !isFullScreen;
						if (!isFullScreen) {
							WindowManager.LayoutParams params = getWindow()
									.getAttributes();
							params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
							getWindow().setAttributes(params);
							getWindow()
									.clearFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							mHeader.setVisibility(View.VISIBLE);
						} else {
							WindowManager.LayoutParams params = getWindow()
									.getAttributes();
							params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
							getWindow().setAttributes(params);
							getWindow()
									.addFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							mHeader.setVisibility(View.GONE);
						}
						return true;
					}
				});
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (isAllowFullScreen()) {
			gd.onTouchEvent(event);
		}
		return super.dispatchTouchEvent(event);
	}
	
	private class BackListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			
//			Intent intent = new Intent();
//			intent.putExtra("result", "xy哈哈哈");
//			setResult(30, intent);
			NewsDetailActivity.this.finish(); 
		}
		
	}
	
	//右上角的三个点菜单显示
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	 

}
