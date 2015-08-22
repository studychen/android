package com.tomchen.smsbomb.common;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

	public static void showToastShort(Context context,int resId ) {
		Toast.makeText(context,resId,Toast.LENGTH_SHORT).show();
	}
	
	public static void showToastLong(Context context,int resId ) {
		Toast.makeText(context,resId,Toast.LENGTH_LONG).show();
	}


}
