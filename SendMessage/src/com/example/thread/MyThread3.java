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

public class MyThread3 implements Callable<String> {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread3(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 

//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//    	String path = "http://t.dianping.com/ajax/wwwaccount/mobiledynamiclogincode/1";
//		StringBuilder strBui = new StringBuilder();
//		Map<String, String> mapData = new HashMap<String, String>();
//		mapData.put("mobile", phone);
//		
//		
//		try {
//			for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
//				strBui.append(mapEnt.getKey() + "=" );
//				strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//				strBui.append("&");
//			}
//			
//			strBui.deleteCharAt(strBui.length()-1);
//			URL url = new URL(path);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setConnectTimeout(5000);
//			con.setRequestMethod("POST");
//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8;");
//			con.setRequestProperty("Cookie", "_hc.v=\"\\\"974b3790-2fb8-4474-9dd3-7dcfcfb9ab71.1413977875\\\"\"; _tr.u=7dhY0yFhd9rY065F; PHOENIX_ID=0a01743f-1492e1321c8-46e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd6495620e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E0611A3E4CF48; ctu=9d825aff821c23e0ef29757efdbaf12d3e6afc896adba2aa80516a6f9e0fc4a2; aburl=1; __utma=1.29319792.1412090870.1413645970.1413903744.6; __utmc=1; __utmz=1.1413903744.6.3.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral|utmcct=/account/settings; ctu=5ef3e5a6e5c041ee1bf6439112f523af59a0bc2461dd1ebbe1ebfc266405a3c1ad258e23c540056b0645c1075845d5a1; ua=%E6%B5%AA%E5%AD%90%E5%9F%8B%E7%B0%AA1; llm=15691856329; _hc.v=\"\\\"9c142919-e376-4a3d-9327-7e0c0251e534.1413903950\\\"\"; combined_payment_order=; tg_list_scroll=500; tc=17; cy=17; cye=xian; t_rct=6082646|6246400|2172824|2171729|6457678; JSESSIONID=DC57C0FCD7A9CE99CC47A3CC1575880B; _tr.s=Zy1Ca2OAe1ptv06o");
//			con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
//			con.setRequestProperty("Referer", "ttp://t.dianping.com/login");
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
		    	String path = "http://t.dianping.com/ajax/wwwaccount/mobiledynamiclogincode/1";
				StringBuilder strBui = new StringBuilder();
				Map<String, String> mapData = new HashMap<String, String>();
				mapData.put("mobile", phone);
				
				
				try {
					for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
						strBui.append(mapEnt.getKey() + "=" );
						strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
						strBui.append("&");
					}
					
					strBui.deleteCharAt(strBui.length()-1);
					URL url = new URL(path);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setConnectTimeout(5000);
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8;");
					con.setRequestProperty("Cookie", "_hc.v=\"\\\"974b3790-2fb8-4474-9dd3-7dcfcfb9ab71.1413977875\\\"\"; _tr.u=7dhY0yFhd9rY065F; PHOENIX_ID=0a01743f-1492e1321c8-46e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd6495620e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E0611A3E4CF48; ctu=9d825aff821c23e0ef29757efdbaf12d3e6afc896adba2aa80516a6f9e0fc4a2; aburl=1; __utma=1.29319792.1412090870.1413645970.1413903744.6; __utmc=1; __utmz=1.1413903744.6.3.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral|utmcct=/account/settings; ctu=5ef3e5a6e5c041ee1bf6439112f523af59a0bc2461dd1ebbe1ebfc266405a3c1ad258e23c540056b0645c1075845d5a1; ua=%E6%B5%AA%E5%AD%90%E5%9F%8B%E7%B0%AA1; llm=15691856329; _hc.v=\"\\\"9c142919-e376-4a3d-9327-7e0c0251e534.1413903950\\\"\"; combined_payment_order=; tg_list_scroll=500; tc=17; cy=17; cye=xian; t_rct=6082646|6246400|2172824|2171729|6457678; JSESSIONID=DC57C0FCD7A9CE99CC47A3CC1575880B; _tr.s=Zy1Ca2OAe1ptv06o");
					con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
					con.setRequestProperty("Referer", "ttp://t.dianping.com/login");
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
