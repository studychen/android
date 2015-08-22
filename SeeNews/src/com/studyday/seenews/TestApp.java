package com.studyday.seenews;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chenxb.app.api.AppContext;
import com.example.service.CollectService;
import com.example.service.MydbOpenHelper;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestApp extends AndroidTestCase{
	private static final String tag = "TestRunner";
	private Context appContext;  // 全局Context
	

	public void testCreateDB() throws Exception{
		MydbOpenHelper dbopen = new MydbOpenHelper(getContext());
		dbopen.getWritableDatabase();
	}
	
	
	public void testAddSet() throws Exception{
		MydbOpenHelper dbopen = new MydbOpenHelper(getContext());
		CollectService collectService = new CollectService(dbopen);
		collectService.setCollect(2);
		collectService.setCollect(4);
		collectService.setCollect(1);
		collectService.setCollect(33);
		collectService.setCollect(7568);
		collectService.setCollect(7161);
//		for (int i=0; i < 20; i++)
//			personService.save(new Person("liming"+i,"123123"+i,i*10+10));
	}
	
	public void testcancelCollect() throws Exception{
		MydbOpenHelper dbopen = new MydbOpenHelper(getContext());
		CollectService collectService = new CollectService(dbopen);
		collectService.cancelCollect(96);
//		collectService.cancelCollect(4);
//		collectService.cancelCollect(1);
//		collectService.cancelCollect(33);
//		collectService.cancelCollect(7568);
//		for (int i=0; i < 20; i++)
//			personService.save(new Person("liming"+i,"123123"+i,i*10+10));
	}
	
	
	public void testgetAllData() throws Exception{
		MydbOpenHelper dbopen = new MydbOpenHelper(getContext());
		CollectService collectService = new CollectService(dbopen);
		List<Integer> persons = collectService.getAllCollect();
		Log.i(tag, persons.toString());
	}
	
	public void testisCollect() throws Exception{
		MydbOpenHelper dbopen = new MydbOpenHelper(getContext());
		CollectService collectService = new CollectService(dbopen);
		boolean flag = collectService.isCollect(22);
		Log.i(tag, "22:" + String.valueOf(flag));
		flag = collectService.isCollect(96);
		Log.i(tag, "96:" + String.valueOf(flag));
		flag = collectService.isCollect(7568);
		Log.i(tag, "96:" + String.valueOf(flag));
	}
	

		public static String loadJson(String url) {
			StringBuilder json = new StringBuilder();
			try {
				URL urlObject = new URL(url);
				URLConnection uc = urlObject.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						uc.getInputStream()));
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					json.append(inputLine);
				}
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return json.toString();
		}
		
		public static void testLoadJson( ) throws JSONException {
			String url = "http://123.57.219.143/webmon/iOS/status.php";
			// String url =
			// "http://www.kuaidi100.com/query?type=yunda&postid=1201386764793";
			String json = loadJson(url);
			Log.i(tag, "96:" + String.valueOf(json));
//			System.out.println(json);
			String newJson = json.charAt(0)+ "\"info\":" + json.substring(1, json.length());
			System.out.println(newJson);
			
			JsonToBean(newJson);
		}

		public static String JsonToBean(String jsonStr) throws JSONException {

			final JSONArray jsonArray = new JSONArray(jsonStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", json.optString("id"));
				Log.i(tag, "96:" + String.valueOf(json.optString("id")));
				System.out.println(json.optString("id"));
				map.put("name", json.optString("name"));
				map.put("name_zh", json.optString("name_zh"));
				map.put("code", json.optString("code"));
				map.put("isDown", json.optString("isDown"));
				map.put("interval_time", json.optString("interval_time"));
				Log.i(tag, "96:" + map);

			}
			return null;
		}

	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws Exception 
	 * @throws IOException
	 */
//	public void saveObject() throws Exception {
//		 testCreateDB() ;
//		 int[] flagArray = new int[400];
//		 flagArray[1] = 32;
//		 
//		Serializable ser = flagArray;
//		String file = "xx.txt";
//		appContext = getContext();
//		FileOutputStream fos = null;
//		ObjectOutputStream oos = null;
//		try{
//			fos = appContext.openFileOutput(file, android.content.Context.MODE_PRIVATE);
//			oos = new ObjectOutputStream(fos);
//			oos.writeObject(ser);
//			oos.flush();
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			try {
//				oos.close();
//			} catch (Exception e) {}
//			try {
//				fos.close();
//			} catch (Exception e) {}
//		}
//	}

}
