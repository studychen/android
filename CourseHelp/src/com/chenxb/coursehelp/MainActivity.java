package com.chenxb.coursehelp;



import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity{

		private TabHost tabhost;
		private TabHost.TabSpec first;
		private TabHost.TabSpec second;
		private TabHost.TabSpec third;
		private TabHost.TabSpec fourth;

		private long exitTime = 0;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.app_main);
			init();

		}
		private void init() {
			tabhost = this.getTabHost();
			first = tabhost.newTabSpec("first");
			second = tabhost.newTabSpec("second");
			third = tabhost.newTabSpec("third");
			fourth = tabhost.newTabSpec("fourth");
			// 指定选项卡上文字，图标
			/*
			 * first.setIndicator(createContent("团购",
			 * getResources().getDrawable(R.drawable.ic_menu_deal_on)));
			 * second.setIndicator(createContent("商家",
			 * getResources().getDrawable(R.drawable.ic_menu_poi_off)));
			 * third.setIndicator(createContent("我的",
			 * getResources().getDrawable(R.drawable.ic_menu_user_off)));
			 * fourth.setIndicator(createContent("更多",
			 * getResources().getDrawable(R.drawable.ic_menu_more_off)));
			 */

			first.setIndicator(createContent("搜索", R.drawable.two_tab));
			second.setIndicator(createContent("通知", R.drawable.first_tab));
			third.setIndicator(createContent("我的", R.drawable.third_tab));
			fourth.setIndicator(createContent("更多", R.drawable.fours_tab));

			// 绑定显示的页面
			// first.setContent(R.id.ll_first);
			first.setContent(new Intent(this, SearchActivity.class));
			second.setContent(new Intent(this, ThirdMyActivity.class));
			third.setContent(new Intent(this, ThirdMyActivity.class));
			fourth.setContent(new Intent(this, MoreActivity.class));
			// 将选项卡加进TabHost
			tabhost.addTab(first);
			tabhost.addTab(second);
			tabhost.addTab(third);
			tabhost.addTab(fourth);
			
			setCuttentTab();
			// 设置tabHost切换时动态更改图标
			tabhost.setOnTabChangedListener(new OnTabChangeListener() {
				@Override
				public void onTabChanged(String tabId) {
					tabChanged(tabId);
				}

			});
		}

		private void setCuttentTab() {
			Intent intent = getIntent();
			//改变至第4个选项卡
			if (intent.getBooleanExtra("changeTab3", false)) {
				tabhost.setCurrentTab(3);
			}
			else {
				tabhost.setCurrentTab(0);
			}
			// TODO Auto-generated method stub
			
		}
		// 捕获tab变化事件
		private void tabChanged(String tabId) {
			// 当前选中项
			if (tabId.equals("first")) {
				tabhost.setCurrentTabByTag("搜索");
			} else if (tabId.equals("second")) {
				tabhost.setCurrentTabByTag("通知");
			} else if (tabId.equals("third")) {
				tabhost.setCurrentTabByTag("我的");
			} else if (tabId.equals("fourth")) {
				tabhost.setCurrentTabByTag("更多");
			}
		}

		/*
		 * // 设置选项卡上的布局内容 private View createContent(String text, Drawable drawable)
		 * { View view = LayoutInflater.from(this).inflate(R.layout.tabwidget, null,
		 * false); ImageView img_name = (ImageView)
		 * view.findViewById(R.id.img_name); TextView tv_name = (TextView)
		 * view.findViewById(R.id.tv_name); img_name.setImageDrawable(drawable);
		 * tv_name.setText(text); return view; }
		 */

		// 返回单个选项
		private View createContent(String text, int resid) {
			View view = LayoutInflater.from(this).inflate(R.layout.tabwidget, null,
					false);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.img_name);
			tv_name.setText(text);
			iv_icon.setBackgroundResource(resid);
			return view;
		}

		@Override
		
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
		        if((System.currentTimeMillis()-exitTime) > 2000){  
		            Toast.makeText(this, R.string.back_exit_tips, Toast.LENGTH_SHORT).show();                                
		            exitTime = System.currentTimeMillis();   
		        } else {
		            MainActivity.this.finish();
		            System.exit(0);
		        }
		        return true;   
		    }
		    return super.onKeyDown(keyCode, event);
		}
}
