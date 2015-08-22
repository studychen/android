/**
 * 
 */
package com.chenxb.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.studyday.seenews.NewsDetailActivity;
import com.studyday.seenews.R;
import com.studyday.seenews.MainActivity.itemClickListener;
import com.studyday.seenews.R.id;
import com.studyday.seenews.R.layout;

import com.chenxb.newslist.NewsList;
import com.chenxb.mybean.News;
import com.chenxb.mybean.NewsListItem;
import com.chenxb.newslist.NewsDetail;

/**
 * @author allin
 * 
 */
public class MyListViewYan extends ListActivity {

	
	// private List<String> data = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //不显示应用标题
		setContentView(R.layout.slidemenu_main_yan);
		ListView listView = getListView();
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.newslist,
				new String[]{"title","time","read","url"},
				new int[]{R.id.title, R.id.time, R.id.read});
		listView.setAdapter(adapter); //列表新闻
		listView.setOnItemClickListener(new myClickListener()); //设置点击事件
		
	}
	
	class myClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Log.i("getNewsDetail", "onItemClick");
			Map<String, String> data = (Map<String, String>)arg0.getItemAtPosition(arg2);
			Intent intent = new Intent(MyListViewYan.this, NewsDetailActivity.class);
			intent.putExtra("clickurl", data.get("url"));
			startActivity(intent);
//			Toast.makeText(getBaseContext(), data.get("url"), 1).show();
			// TODO Auto-generated method stub
		}
	}

	private List<Map<String, String>> getData() {
		NewsList newsList;
		try {
			newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/3.html");
			Log.i("getNewsDetail",  "0202020202");
			List<NewsListItem> getNewsList = newsList.getList();
			
			Log.i("getNewsDetail",  getNewsList.toString());
			Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			while (getNewsListIte.hasNext()) {
				NewsListItem newsItem = getNewsListIte.next();
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", newsItem.getTitle());
				map.put("time", newsItem.getDate());
				map.put("read", "浏览次数：" + newsItem.getClick());
				map.put("url",  newsItem.getUrl());
				list.add(map);
			}
			

			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
		return null;
	}
}
