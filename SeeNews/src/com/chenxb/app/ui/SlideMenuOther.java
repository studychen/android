package com.chenxb.app.ui;


import com.studyday.seenews.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class SlideMenuOther extends ViewGroup
{
    private int touchSlop;
    private Handler myHandler = new Handler( );
       public SlideMenuOther(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mScroller = new Scroller(context);
        //startX, startY, dx, dy,duration
        //滑动间距的距离 默认为8
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    
    //测量出所有子布局的宽和高
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            //这个super必须要重写的
            measureView(widthMeasureSpec,heightMeasureSpec);
        }
    /**
     * int widthMeasureSpec, int heightMeasureSpec
     * @param widthMeasureSpec 父布局的测量
     * @param heightMeasureSpec
     */
    private void measureView(int widthMeasureSpec, int heightMeasureSpec)
    {
        //测量菜单布局
        View menuView = getChildAt(0);
        menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);
        //测量主界面的布局
        View mainView  = getChildAt(1);
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
                
    }
    //设置菜单和主界面的布局
    @Override
    protected void onLayout(boolean chenged, int l, int t, int r, int b)
    {
        //布置菜单的位置
        View menuView = getChildAt(0);
        //得到测量之后的边长
        menuView.layout(-menuView.getMeasuredWidth(), 0, 0, b);
        //布置主界面的位置
        View mainView =getChildAt(1);
        mainView.layout(0, 0, r, b);
        
    }
    private int mMostRecentX;//最后一次的X轴偏移量
    private int MenuScreen = 0;//菜单页面
    private int MainScreen = 1;//主界面
    private int mCurrentScreen = MainScreen;//当前屏幕的主界面
    //定义数据模拟器 来动态模拟数据的增量
    private Scroller mScroller;
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            mMostRecentX = (int) event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            //最新的X轴偏移量
            int moveX = (int) event.getX();
            //增量值
            int detaX  = mMostRecentX - moveX;
            //把最新的X轴偏移量赋值给最后一次的X轴偏移量
            mMostRecentX = moveX;
            
            //得到X轴移动后的偏移量
            int newScrolLX = (int) (getScrollX() + detaX);
            //当前X轴的偏移量超过了菜单的边界
            if(newScrolLX<-getChildAt(0).getWidth())
            {
                //回到菜单的左边界
            	scrollTo(-getChildAt(0).getWidth(), 0);
            	//这块可能需要改
            	fullViewYes();
            }
            else if(newScrolLX>0)
            {
                //回到主界面的左边界
                scrollTo(0, 0);
                fullViewNo();
                Log.i("VIEW_LOG_TAG", "222");
            }
            else
            {
                scrollBy(detaX, 0);
                Log.i("VIEW_LOG_TAG", "333");
            }
            break;
        case MotionEvent.ACTION_UP :
            //最新的X轴偏移量
            int scrollX = (int) getScrollX();
            //菜单的中心X轴
            int menuXCenter = -getChildAt(0).getWidth()/2;
            if(scrollX>menuXCenter)
            {//切换到主界面
                mCurrentScreen = MainScreen;
                fullViewNo();
            }
            else
            {//切换到菜单界面
                mCurrentScreen = MenuScreen;
                fullViewYes();
            }
            switchScreen();
            break;
        default:
            break;
        }
        
        return true;
    }
    
    private void fullViewYes() {
    	myHandler.post(new Runnable() {
    		public void run() {
		    	 if(mCurrentScreen == MenuScreen) {
		    		 final XListView mListView =  (XListView) findViewById(R.id.xListView);
		    		 View fullView = findViewById(R.id.full_view);
		
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
							scrollTo(0, 0);
							v.setVisibility(View.GONE);
							mListView.setEnabled(true);
						}
					});
		    	 }
						
					}
		    		
		    	});
    }
    
    private void fullViewNo() {
    	myHandler.post(new Runnable() {
    		public void run() {
		    	 if(mCurrentScreen == MainScreen) {
		    		 XListView mListView =  (XListView) findViewById(R.id.xListView);
				      View fullView = findViewById(R.id.full_view);
			    	 fullView.setVisibility(View.GONE);
			         mListView.setEnabled(true);
		    	 }
    		}
    	});
    }
    
    private void switchScreen()
    {
        int scrollX = getScrollX();
        int dx = 0;
        if(mCurrentScreen == MainScreen)
        {//切换到主界面
//            scrollTo(0, 0);
//        	fullViewNo();
            dx = 0-scrollX;
        }
        else if(mCurrentScreen == MenuScreen)
        {//切换到菜单界面
//            scrollTo(-getChildAt(0).getWidth(), 0);
//        	fullViewYes();
            dx = -getChildAt(0).getWidth()-scrollX;
        }
        
//        if(mCurrentScreen == MainScreen) {
//        	fullViewNo();
//        }
        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx)*3);
        
        invalidate();//invalidate --> drawChild -->chlid.draw -->computerScroll
    }
    @Override
    public void computeScroll()
    {
        //invaliadate调用此方法，更新X轴的偏移量
        if(mScroller.computeScrollOffset()) //判断是否正在模拟数据中，true 正在进行
        {
            scrollTo(mScroller.getCurrX(), 0);
            
            invalidate();//引起computeScroll的调用
        }
    }
    //时候显示菜单
    public boolean isShow()
    {
        return mCurrentScreen == MainScreen;
    }
    
    public void hideMenu()
    {
        mCurrentScreen = MainScreen;
        switchScreen();
    }
    
    public void showMenu()
    {
        mCurrentScreen = MenuScreen;
        switchScreen();
    }
    /**
     * 拦截事件的方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            mMostRecentX = (int) ev.getX();
            break;
        case MotionEvent.ACTION_MOVE:
            int diffX = (int) (ev.getX() - mMostRecentX);
            if(Math.abs(diffX)>touchSlop)
            {
                return true;
            }
            break;
        default:
            break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}