package com.monitor.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.monitor.bean.WebInfo;
import com.monitor.common.StringUtils;
import com.monitor.common.WebApi;
import com.monitor.ui.ExpandAdapter;
import com.monitor.ui.XListView;
import com.monitor.ui.XListView.IXListViewListener;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements IXListViewListener {

	private XListView mListView;
	private SimpleAdapter adapterSimple;
	
	private RelativeLayout relativeBarLayout;
	
	private LinearLayout linearItemLayout;
	
	private ExpandAdapter expandAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info_list);
		findViews();
		initViews();
	}

	private void findViews() {
		mListView = (XListView) findViewById(R.id.xListView);
//		relativeBarLayout = (RelativeLayout)findViewById(R.id.bar_layout);
//		linearItemLayout = (LinearLayout) findViewById(R.id.item);
	}

	private void initViews() {
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setOrginalTime(StringUtils.RefreshDateString());
		
		String url = "http://123.57.219.143/webmon/iOS/status.php";
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		try {
			String json = WebApi.loadJson(url);
			listData = WebApi.JsonArrToBeanArr(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		expandAdapter = new ExpandAdapter(this, listData);
//		adapterSimple = new SimpleAdapter(
//				this,
//				listData,
//				R.layout.web_info,
//				new String[] { "name", "nameZh", "time", "code", "intervalTime" },
//				new int[] { R.id.name, R.id.nameZh, R.id.time, R.id.code,
//						R.id.intervalTime });
		mListView.setAdapter(expandAdapter); // 列表新闻
		
//		relativeBarLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				if (linearItemLayout.getVisibility() == View.GONE) {
//					linearItemLayout.setVisibility(View.VISIBLE);
//				} else {
//					linearItemLayout.setVisibility(View.GONE);
//				}
//				
//			}
//		});
//
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}
