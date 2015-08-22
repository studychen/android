package com.chenxb.adapter;

import java.util.ArrayList;

import com.chenxb.commom.UserUtils;
import com.chenxb.coursebean.ImageInfo;
import com.chenxb.coursehelp.MyInfoActivity;
import com.chenxb.coursehelp.NewLoginActivity;
import com.chenxb.coursehelp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义适配器
 * @author wulianghuan
 *
 */
public class ZakerPagerAdapter extends PagerAdapter {
	Vibrator vibrator;
	ArrayList<ImageInfo> data;
	Activity activity;
	LayoutParams params;

	public ZakerPagerAdapter(Activity activity, ArrayList<ImageInfo> data) {
		this.activity = activity;
		this.data = data;
		vibrator = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int index) {
		Log.v("test", index + "index");

		View view = activity.getLayoutInflater().inflate(R.layout.zaker_grid, null);
		GridView gridView = (GridView) view.findViewById(R.id.gridView1);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);
		gridView.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return 6;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View item = LayoutInflater.from(activity).inflate(
						R.layout.zaker_grid_item, null);
				ImageView iv = (ImageView) item.findViewById(R.id.imageView1);
				RelativeLayout relativeLayout = (RelativeLayout)item.findViewById(R.id.relativeLayout);
				iv.setImageResource((data.get(index * 6 + position)).imageId);
				relativeLayout.setBackgroundResource((data.get(index * 6 + position)).bgId);
				relativeLayout.getBackground().setAlpha(225);
				TextView tv = (TextView) item.findViewById(R.id.msg);
				tv.setText((data.get(index * 6 + position)).imageMsg);
				return item;
			}
		});

//		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				View view = arg1;
//				arg1.setVisibility(View.INVISIBLE);
//
//				params = new WindowManager.LayoutParams();
//			//	activity.getWindowManager().addView(view, params);
//				vibrator.vibrate(2500);
//
//				return true;
//			}
//		});
		gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SharedPreferences sharePrefer = 
				activity.getSharedPreferences("users", Context.MODE_WORLD_READABLE );
				if (sharePrefer.getBoolean("isLogin", false)) {
					String cookie = sharePrefer.getString("cookie", "errorcookie");
					if(!UserUtils.isCookieUserful(cookie)) {
						Toast.makeText(activity, "请您先登录", 1).show();
					} else{
						//从左往右数，从上往下数，第0到5个，共6个控件
						switch (arg2) {
						case 0:
							Intent intent = new Intent(activity,MyInfoActivity.class);
							activity.startActivity(intent);
							break;
						case 1:
							Intent intent1 = new Intent(activity,NewLoginActivity.class);
							activity.startActivity(intent1);
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							break;
							
						}
					}
				} else {
					Toast.makeText(activity, "请您先登录", 1).show();
				}
				
				
				
			}
		});
		
		gridView.setDrawSelectorOnTop(true);
		//设置按下的背景颜色变换
		gridView.setSelector(activity.getResources().getDrawable(R.drawable.grid_selector));
	
//		 gridView.setOnTouchListener(new View.OnTouchListener() {
//		
//			 @Override
//			 public boolean onTouch(View v, MotionEvent event) {
//				 
//				 return true;
//			 }
//		 });
		((ViewPager) container).addView(view);

		return view;
	}
}
