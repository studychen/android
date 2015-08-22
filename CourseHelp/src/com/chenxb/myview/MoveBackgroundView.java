package com.chenxb.myview;

import java.util.Timer;
import java.util.TimerTask;

import com.chenxb.coursehelp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;


/**
 * @author wulianghuan
 * 该类为自定义ImageView，用于显示背景图片，并显示背景图片到移动效果
 *
 */
public class MoveBackgroundView extends ImageView{
	private Bitmap back;		//背景图片资源
	private Bitmap mBitmap;		//生成位图	
	private double startX = 0;	//移动起始X坐标
	
	//构造函数中必须有context,attributeSet这两个	参数，否则父类无法调用
	public MoveBackgroundView(Context context,AttributeSet attributeSet)
	{
		super(context, attributeSet);
	    //由于不是Activity子类，只能通过DisplayMetrics来获取屏幕信息
	    DisplayMetrics dm = getResources().getDisplayMetrics();
	    //屏幕宽度
	    int screenWidth = dm.widthPixels;  
	    //屏幕高度
	    int screenHeight = dm.heightPixels;   
	    
		if( getHour() > 8 && getHour() < 18 ) {
			back = BitmapFactory.decodeResource(context.getResources(), R.drawable.rootblock_default_bg);
		} else {
			back = BitmapFactory.decodeResource(context.getResources(), R.drawable.rootblock_default_bg_night);
		}
		
		mBitmap = Bitmap.createScaledBitmap(back, screenWidth*3, screenHeight, true);    
		
		
		final Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				if (msg.what == 1)
				{
					Log.i("TAG", "-----"+startX);
					if (startX <= -80)
					{
						startX = 0;
					}
					else
					{
						startX -= 0.09;
					}
				}
				invalidate();
			}
		};
		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				handler.sendEmptyMessage(1);
			}
		}, 0 , 10);
		
	}
	
	private int getHour() {
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。  
		t.setToNow(); // 取得系统时间。  
		return  t.hour; // 0-23  
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		Log.i("TAG", "-----onDraw");
		Bitmap bitmap2 = Bitmap.createBitmap(mBitmap);
		canvas.drawBitmap(mBitmap, (float)startX , 0 , null);
	}
}
