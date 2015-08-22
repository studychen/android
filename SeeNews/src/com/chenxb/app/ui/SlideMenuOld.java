package com.chenxb.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideMenuOld extends ViewGroup {

	private final String TAG = SlideMenuOld.class.getSimpleName();

	private View mMenuView; // 菜单

	private View mContentView;// 内容

	private int mPreX; // 之前的x轴坐标

	private int mTouchSlop; // 认为是用户是滑动的最小间隔，由系统定

	private Scroller mScroller; // 模拟滑动时使用

	private final int LOCATION_CONTENT = 0; //主页面
	private final int LOCATION_MENU = 1; // 侧边菜单

	private int mLocation = LOCATION_CONTENT;

	private int mDuration = 300; // 滚动的时长

	public SlideMenuOld(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.i(TAG, "onMeasure");

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int measureWidth = measureWidth(widthMeasureSpec); // 获取测量的宽度

		int measureHeight = measureHeight(heightMeasureSpec);// 获取测量的高度

		// 计算自定义的ViewGroup中所有子控件的大小
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		// 设置自定义的控件的大小
		setMeasuredDimension(measureWidth, measureHeight);

	}

	private int measureWidth(int widthMeasureSpec) {
		return getSize(widthMeasureSpec);
	}

	private int getSize(int measureSpec) {
		int result = 0;
		int mode = MeasureSpec.getMode(measureSpec);// 得到模式
		int size = MeasureSpec.getSize(measureSpec);// 得到尺寸

		switch (mode) {
		/**
		 * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
		 * MeasureSpec.AT_MOST。
		 * 
		 * 
		 * MeasureSpec.EXACTLY是精确尺寸，
		 * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
		 * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
		 * 
		 * 
		 * MeasureSpec.AT_MOST是最大尺寸，
		 * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
		 * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
		 * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
		 * 
		 * 
		 * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
		 * 通过measure方法传入的模式。
		 */
			case MeasureSpec.AT_MOST:
			case MeasureSpec.EXACTLY:
				result = size;
				break;
		}

		return result;
	}

	private int measureHeight(int heightMeasureSpec) {
		return getSize(heightMeasureSpec);
	}

	/**
	 * 获取子控件
	 */
	private void findChildView() {

		mMenuView = getChildAt(0); // 需要根据子控件的布局顺序确定序号

		mContentView = getChildAt(1);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		findChildView();

		mMenuView.layout(-mMenuView.getMeasuredWidth(), 0, 0, b); // 把menu放在屏幕外的左侧

		mContentView.layout(l, t, r, b); // 占满整个屏幕

	}
	
	private void switchScreen()
    {
        int scrollX = getScrollX();
        int dx = 0;
        if(mLocation == LOCATION_CONTENT)
        {//切换到主界面
//            scrollTo(0, 0);
            dx = 0-scrollX;
        }
        else if(mLocation == LOCATION_MENU)
        {//切换到菜单界面
//            scrollTo(-getChildAt(0).getWidth(), 0);
            dx = -getChildAt(0).getWidth()-scrollX;
        }
        mScroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx)*3);
        
        invalidate();//invalidate --> drawChild -->chlid.draw -->computerScroll
    }
   
    //时候显示菜单
    public boolean isShow()
    {
        return mLocation == LOCATION_CONTENT;
    }
    
    public void hideMenu()
    {
    	mLocation = LOCATION_CONTENT;
        switchScreen();
    }
    
    public void showMenu()
    {
    	mLocation = LOCATION_MENU;
        switchScreen();
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mPreX = (int) event.getX(); // 记录按下时的x轴坐标
				break;
			}
			case MotionEvent.ACTION_UP: {

				// 获取控件位于哪个界面
				mLocation = getScreenLocation();

				// 滚动到该界面
				toLocation();

				break;
			}
			case MotionEvent.ACTION_MOVE: {

				// 获取移动中的坐标
				int moveX = (int) event.getX();
				int dx = mPreX - moveX;

				// 判断是否移动后是否超过边界
				// Log.i(TAG, "getScrollX=" + getScrollX() + "   dx=" + dx
				// + "  content width=" + mContentView.getWidth());
				int scrollX = getScrollX() + (int) dx; // getScrollX()为控件x轴方向已经滑动的距离
				if (scrollX > 0) { // 超过右边距
					scrollTo(0, 0);
				}
				else if (scrollX < -mMenuView.getWidth()) { // 超过左边距
					scrollTo(-mMenuView.getWidth(), 0);
				}
				else {
					scrollBy(dx, 0); // 使组件位移dx个单位，正数为左移，负数为右移
				}

				mPreX = moveX;

				break;
			}
			default:
				break;
		}

		return true;
	}

	@Override
	public void computeScroll() {

		Log.i(TAG, "computeScroll");

		// 更新x轴偏移量，与scroller配合使用
		if (mScroller.computeScrollOffset()) { // 滚动是否结束
			scrollTo(mScroller.getCurrX(), 0);
		}

		invalidate();

	}

	private void toLocation() {

		int startX = getScrollX();
		int dx = 0;
		if (mLocation == LOCATION_MENU) {
			dx = -(mMenuView.getWidth() + startX);
		}
		else {
			dx = 0 - startX;
		}

		mScroller.startScroll(startX, 0, dx, 0, mDuration);
	}

	private int getScreenLocation() {
		// 认为切换到menu界面的距离
		int locateMenuSlop = (int) (mMenuView.getWidth() / 2);

		// 获得当前控件位于哪个界面
		int scrollX = getScrollX();

		return (Math.abs(scrollX) >= locateMenuSlop ? LOCATION_MENU
				: LOCATION_CONTENT);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 处理listview左右滑动时，滑动菜单不能滑动的情况

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {

				mPreX = (int) ev.getX();

				break;
			}
			case MotionEvent.ACTION_UP: {

				break;
			}
			case MotionEvent.ACTION_MOVE: {

				int moveX = (int) ev.getX();

				int dx = moveX - mPreX;
				if (Math.abs(dx) > mTouchSlop) { // 认为是横向移动的消耗掉此事件，避免传递到Listview的点击事件
					return true;
				}

				break;
			}
			default:
				break;
		}

		return super.onInterceptTouchEvent(ev);
	}

}
