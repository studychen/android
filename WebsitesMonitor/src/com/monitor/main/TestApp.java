package com.monitor.main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.monitor.bean.WebInfo;

import android.content.Context;
import android.os.StrictMode;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestApp extends AndroidTestCase {
	private static final String tag = "TestRunner";
	private Context appContext; // 全局Context

	public static void testLoadJson() throws JSONException, IOException {
		String url = "http://123.57.219.143/webmon/iOS/status.php";
		// String url =
		// "http://www.kuaidi100.com/query?type=yunda&postid=1201386764793";
		String json = loadJson(url);
		Log.i(tag, "json:" + json);
		// System.out.println(json);
//		String newJson = json.charAt(0) + "\"info\":"
//				+ json.substring(1, json.length());
//		System.out.println(newJson);

		List<WebInfo> list = JsonArrToBeanArr(json);
		Log.i(tag, "list:" + list);
	}

	public static String loadJson(String jsonUrl) throws IOException {
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		URL url = new URL(jsonUrl);  
		HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();  
		//得到输入流并转换为字符串  
		InputStream inputStream = httpurlconnection.getInputStream();  
		ByteArrayOutputStream out =new ByteArrayOutputStream();
		byte[] data =new byte[1024];
		int len =0;
		while((len=inputStream.read(data))!=-1){
		out.write(data, 0, len);//原本应该是out.write(data, 0, len);,但当时用了快捷键,打成了0,就抛出了如上的异常消息
		}
		out.close();
		return new String(out.toByteArray());
	}

	public static List<WebInfo> JsonArrToBeanArr(String jsonStr) throws JSONException {

		JSONArray jsonArray = new JSONArray(jsonStr);
		List<WebInfo> infoList= new ArrayList<WebInfo>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Map<String, String> map = new HashMap<String, String>();
			int id = Integer.valueOf(json.optString("id"));
			String name =  json.optString("name");
			String nameZh =   json.optString("name_zh");
			String time = json.optString("time");
			int code =   Integer.valueOf(json.optString("code"));
			boolean isDown = Boolean.valueOf(json.optString("isDown"));
			int  intervalTime =Integer.valueOf(json.optString("interval_time"));
		}
		return infoList;
	}
}
