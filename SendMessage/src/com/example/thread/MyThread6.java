package com.example.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import android.util.Log;

import com.example.StreamTool.StreamTool;

public class MyThread6 implements Callable<String>  {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread6(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 

//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//    	String path = "http://mail.sina.com.cn/client/mobile/sms.php";
//		StringBuilder strBui = new StringBuilder();
//		Map<String, String> mapData = new LinkedHashMap<String, String>();
//		mapData.put("phone_number", phone);
//		
//		try {
//			for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
//				strBui.append(mapEnt.getKey() + "=" );
//				strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
////				Log.i(tag, mapEnt.getValue());
//				strBui.append("&");
//			}
//			strBui.deleteCharAt(strBui.length()-1);
//			URL url = new URL(path);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("POST");
//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
//			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
//			con.setDoOutput(true);
//			OutputStream outStr = con.getOutputStream();
//			outStr.write(strBui.toString().getBytes());
//			
//			if (con.getResponseCode() == 200){
//				InputStream inputStr = con.getInputStream();
//				String info = new String(StreamTool.read(inputStr), "UTF-8");
//				Log.i(tag, Thread.currentThread().getName() + info);
//			}
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//    }

	@Override
	public String call() throws Exception {
	    	Log.i(tag, Thread.currentThread().getName() + "......start");
	    	String path = "http://mail.sina.com.cn/client/mobile/sms.php";
			StringBuilder strBui = new StringBuilder();
			Map<String, String> mapData = new LinkedHashMap<String, String>();
			mapData.put("phone_number", phone);
			
			try {
				for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
					strBui.append(mapEnt.getKey() + "=" );
					strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//					Log.i(tag, mapEnt.getValue());
					strBui.append("&");
				}
				strBui.deleteCharAt(strBui.length()-1);
				URL url = new URL(path);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
				con.setDoOutput(true);
				OutputStream outStr = con.getOutputStream();
				outStr.write(strBui.toString().getBytes());
				
				if (con.getResponseCode() == 200){
					InputStream inputStr = con.getInputStream();
					String info = new String(StreamTool.read(inputStr), "UTF-8");
					Log.i(tag, Thread.currentThread().getName() + info);
					if(info.contains("true"))
						return "200";
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// TODO Auto-generated method stub
		return "404";
	} 
	

}
