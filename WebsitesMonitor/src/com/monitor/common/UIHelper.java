package com.monitor.common;

import java.io.IOException;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */

public class UIHelper {
	
	
	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	
	//toast提示框
	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	
//	public static void addWebImageShow(final Context cxt, WebView wv) {
//		wv.getSettings().setJavaScriptEnabled(true);
//		wv.addJavascriptInterface(new OnWebViewImageListener() {
//
//			@Override
//			public void onImageClick(String bigImageUrl) {
//				if (bigImageUrl != null)
//					UIHelper.showImageZoomDialog(cxt, bigImageUrl);
//			}
//		}, "mWebViewImageListener");
//	}

}
