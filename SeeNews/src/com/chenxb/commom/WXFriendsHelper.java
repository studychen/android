package com.chenxb.commom;

import java.io.ByteArrayOutputStream;


import com.studyday.seenews.R;

import com.tencent.mm.sdk.openapi.GetMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

/**
 * 微信朋友圈帮助类
 * @author http://my.oschina.net/LittleDY
 * @version 1.0
 * @created 2014-02-13
 */
public class WXFriendsHelper {
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
	private static String APP_ID = "wx594cd1796b8b25c8";
	private static final int MIN_SUPPORTED_VERSION = 0x21020001;// 最小支持的版本
	
	/**
	 * 分享到微信朋友圈或者对话
	 * @param context
	 * @param title
	 * @param url
	 * @param isQuan true值表示分享到朋友圈，否则表示分享到朋友对话
	 */
public static void shareToWXFriends(Activity context,String title,String url,boolean isQuan){
		IWXAPI api = WXAPIFactory.createWXAPI(context,APP_ID, true);
	    api.registerApp(APP_ID);
		// 检查是否安装微信
	    if(!api.isWXAppInstalled()) {
	    	UIHelper.ToastMessage(context, "抱歉，您尚未安装微信客户端，无法进行微信分享！");
	    	return;
	    }
	    // 检查是否支持
	    if(api.getWXAppSupportAPI() < MIN_SUPPORTED_VERSION) {
	    	UIHelper.ToastMessage(context, "抱歉，您的微信版本不支持分享到朋友圈！");
	    	return;
	    }
	   
	    WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		
		// 用WXWebpageObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = "网址：" + url;
		
		// 缩略图的二进制数据
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		msg.thumbData = bmpToByteArray(thumb, true);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		// 分享的时间
		// transaction字段用于唯一标识一个请求
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		if(isQuan) 
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		else 
			req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}
	// 处理缩略图
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			//回收Bitmap的空间
			bmp.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
 
}
