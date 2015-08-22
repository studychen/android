package com.tomchen.smsbomb.activity;


import com.tomchen.smsbomb.fragment.SMSBombFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class SMSBombActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new SMSBombFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}



}
