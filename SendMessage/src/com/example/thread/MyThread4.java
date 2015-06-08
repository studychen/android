package com.example.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import android.util.Log;

import com.example.StreamTool.StreamTool;

public class MyThread4 implements Callable<String>  {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread4(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 
	
//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//    	String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mo.sendMO";
//		StringBuilder strBui = new StringBuilder();
//		Map<String, String> mapData = new HashMap<String, String>();
//		mapData.put("mobile", phone);
//		mapData.put("uid", "xxxyyy1020@126.com");
//		mapData.put("mark", "success_lottery");
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
//			con.setConnectTimeout(5000);
//			con.setRequestMethod("POST");
//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
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
	    	String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mo.sendMO";
			StringBuilder strBui = new StringBuilder();
			Map<String, String> mapData = new HashMap<String, String>();
			mapData.put("mobile", phone);
			mapData.put("uid", "xxxyyy1020@126.com");
			mapData.put("mark", "success_lottery");

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
				con.setConnectTimeout(5000);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
				con.setDoOutput(true);
				OutputStream outStr = con.getOutputStream();
				outStr.write(strBui.toString().getBytes());
				
				if (con.getResponseCode() == 200){
					InputStream inputStr = con.getInputStream();
					String info = new String(StreamTool.read(inputStr), "UTF-8");
					Log.i(tag, Thread.currentThread().getName() + info);
					if(info.contains("200"))
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
