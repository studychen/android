package com.monitor.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

import com.monitor.bean.WebInfo;

public class WebApi {

	public static String loadJson(String jsonUrl) throws IOException {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		URL url = new URL(jsonUrl);
		HttpURLConnection httpurlconnection = (HttpURLConnection) url
				.openConnection();
		// 得到输入流并转换为字符串
		InputStream inputStream = httpurlconnection.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(data)) != -1) {
			out.write(data, 0, len);
		}
		out.close();
		return new String(out.toByteArray());
	}

	public static List<Map<String, String>> JsonArrToBeanArr(String jsonStr)
			throws JSONException {

		JSONArray jsonArray = new JSONArray(jsonStr);
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", json.optString("name"));
			map.put("nameZh", json.optString("name_zh"));
			map.put("time", json.optString("time"));
			map.put("code", json.optString("code"));
			map.put("isDown", json.optString("isDown"));
			map.put("intervalTime", json.optString("interval_time"));
			listData.add(map);
		}
		return listData;
	}

}
