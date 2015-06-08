//package com.monitor.main;
//
//
//
//import android.animation.ObjectAnimator;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.animation.AccelerateInterpolator;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class CopyOfMainActivity extends Activity {
//
//	private String TAG_ARROW = "service_arrow";
//	private String TAG_ITEM = "service_item";
//
//	private View mBottomView;
//	
//
//	private TextView expandText, collapseText;
//	private LinearLayout mMeetingLayout, mLifeLayout, mMedicalLayout,
//			mLiveLayout, mPublicLayout;
//	private RelativeLayout mMeetingBarLayout, mLifeBarLayout,
//			mMedicalBarLayout, mLiveBarLayout, mPublicBarLayout;
//	private LinearLayout mMeetingFoldingLayout, mLifeFoldingLayout,
//			mMedicalFoldingLayout, mLiveFoldingLayout, mPublicFoldingLayout;
//
//	private final int FOLD_ANIMATION_DURATION = 1000;
//
//	private int mNumberOfFolds = 3;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.service_layout);
//		appContext = (AppContext) getApplication();
//		if (!appContext.isNetworkConnected()) {
//			UIHelper.ToastMessage(this, R.string.network_not_connected);
//		} 
//		findViews();
//		initViews();
//	}
//
//	private void findViews() {
//		expandText = (TextView) findViewById(R.id.expand_text);
//		collapseText = (TextView) findViewById(R.id.collapse_text);
//
//		mMeetingLayout = (LinearLayout) findViewById(R.id.meeting_layout);
//		mLifeLayout = (LinearLayout) findViewById(R.id.life_layout);
//		mMedicalLayout = (LinearLayout) findViewById(R.id.medical_layout);
//		mLiveLayout = (LinearLayout) findViewById(R.id.live_layout);
//		mPublicLayout = (LinearLayout) findViewById(R.id.public_layout);
//
//		mMeetingBarLayout = (RelativeLayout) findViewById(R.id.meeting_bar_layout);
//		mLifeBarLayout = (RelativeLayout) findViewById(R.id.life_bar_layout);
//		mMedicalBarLayout = (RelativeLayout) findViewById(R.id.medical_bar_layout);
//		mLiveBarLayout = (RelativeLayout) findViewById(R.id.live_bar_layout);
//		mPublicBarLayout = (RelativeLayout) findViewById(R.id.public_bar_layout);
//
//		mMeetingFoldingLayout = ((LinearLayout) mMeetingLayout
//				.findViewWithTag(TAG_ITEM));
//		mLifeFoldingLayout = ((LinearLayout) mLifeLayout
//				.findViewWithTag(TAG_ITEM));
//		mMedicalFoldingLayout = ((LinearLayout) mMedicalLayout
//				.findViewWithTag(TAG_ITEM));
//		mLiveFoldingLayout = ((LinearLayout) mLiveLayout
//				.findViewWithTag(TAG_ITEM));
//		mPublicFoldingLayout = ((LinearLayout) mPublicLayout
//				.findViewWithTag(TAG_ITEM));
//
//		mBottomView = findViewById(R.id.bottom_view);
//	}
//
//	private void expandOrCollapse(View v, boolean flag) {
//		ImageView arrow = (ImageView) v.findViewWithTag(TAG_ARROW);
//		// true is expand
//		if (flag) {
//			if (arrow.getBackground().getConstantState().equals(getResources().getDrawable(
//					R.drawable.service_arrow_down).getConstantState())) {
//				v.callOnClick();
//			}
//		} else {
//			if (arrow.getBackground().getConstantState().equals(getResources().getDrawable(
//					R.drawable.service_arrow_up).getConstantState())) {
//				v.callOnClick();
//			}
//		}
//	}
//
//	private void initViews() {
//
//		expandText.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				boolean flag = true;
//				expandOrCollapse(mMeetingBarLayout,flag);
//				expandOrCollapse(mLifeBarLayout,flag);
//				expandOrCollapse(mMedicalBarLayout,flag);
//				expandOrCollapse(mLiveBarLayout,flag);
//				expandOrCollapse(mPublicBarLayout,flag);
//			}
//		});
//
//		collapseText.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				boolean flag = false;
//				expandOrCollapse(mMeetingBarLayout,flag);
//				expandOrCollapse(mLifeBarLayout,flag);
//				expandOrCollapse(mMedicalBarLayout,flag);
//				expandOrCollapse(mLiveBarLayout,flag);
//				expandOrCollapse(mPublicBarLayout,flag);
//			}
//		});
//
//		mMeetingBarLayout.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				handleAnimation(v, mMeetingFoldingLayout, mMeetingLayout);
//			}
//		});
//		mLifeBarLayout.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				handleAnimation(v, mLifeFoldingLayout, mLifeLayout);
//			}
//		});
//
//		mMedicalBarLayout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				handleAnimation(v, mMedicalFoldingLayout, mMedicalLayout);
//			}
//		});
//
//		mLiveBarLayout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				handleAnimation(v, mLiveFoldingLayout, mLiveLayout);
//			}
//		});
//
//		mPublicBarLayout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				handleAnimation(v, mPublicFoldingLayout, mPublicLayout);
//			}
//		});
//
//	}
//
//	private void handleAnimation(final View bar,
//			final LinearLayout linearLayout, View parent) {
//
//		final ImageView arrow = (ImageView) parent.findViewWithTag(TAG_ARROW);
//		if(linearLayout.getVisibility() == View.GONE) {
//			linearLayout.setVisibility(View.VISIBLE);
//			arrow.setBackgroundResource(R.drawable.service_arrow_up);
//		} else {
//			linearLayout.setVisibility(View.GONE);
//			arrow.setBackgroundResource(R.drawable.service_arrow_down);
//		}
//
//	}
//}
