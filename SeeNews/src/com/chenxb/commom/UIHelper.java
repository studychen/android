package com.chenxb.commom;

import java.io.IOException;


import com.studyday.seenews.R;

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
	
	/**
	 * 分享到'新浪微博'或'腾讯微博'的对话框
	 * 
	 * @param context
	 *            当前Activity
	 * @param title
	 *            分享的标题
	 * @param url
	 *            分享的链接
	 */
	public static void showShareDialog(final Activity context,
			final String title, final String url) {
		final String spiltUrl = url;
		//判断分享的链接是否包含my
//		if(url.indexOf("my")>0){
//			spiltUrl = "http://m.oschina.net/" + url.substring(url.indexOf("blog"));
//		}else{
//			spiltUrl = "http://m.oschina.net/" + url.substring(22);
//		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.btn_star);
		builder.setTitle(context.getString(R.string.share));
		builder.setCancelable(true);
		builder.setItems(R.array.app_share_items,
				new DialogInterface.OnClickListener() {
//					AppConfig cfgHelper = AppConfig.getAppConfig(context);
//					AccessInfo access = cfgHelper.getAccessInfo();
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:// 微信朋友圈
							WXFriendsHelper.shareToWXFriends(context, title, spiltUrl,true);
							break;
						case 1:// 微信朋友圈
							WXFriendsHelper.shareToWXFriends(context, title, spiltUrl,false);
							break;
						case 2:// 更多
//							WXFriendsHelper.shareToWXFriends(context, title, spiltUrl);
							break;
						case 3:// 取消
//							WXFriendsHelper.shareToWXFriends(context, title, spiltUrl);
							break;
						default:
							ToastMessage(context, "default");
							break;
						}
					}
				});
		builder.create().show();
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
