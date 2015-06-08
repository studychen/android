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

import android.media.MediaRouter.Callback;
import android.util.Log;

import com.example.StreamTool.StreamTool;

public class MyThread5 implements Callable<String> {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread5(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 

//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//    	String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mobileverify.sendAcode";
//
//		StringBuilder strBui = new StringBuilder();
//		Map<String, String> mapData = new LinkedHashMap<String, String>();
//		mapData.put("mobile", phone);
//		mapData.put("uid", phone+"@163.com");
//		mapData.put("mark", "mobile_start");
//		
//		try {
//			for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
//				strBui.append(mapEnt.getKey() + "=" );
//				strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//				strBui.append("&");
//			}
//			strBui.deleteCharAt(strBui.length()-1);
//			URL url = new URL(path);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setConnectTimeout(5000);
//			con.setRequestMethod("POST");
//			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
//			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
//			con.setRequestProperty("Referer", "http://reg.email.163.com/unireg/call.do?cmd=register.entrance&from=163mail");
//			con.setRequestProperty("Cookie", "JSESSIONID=A598FA321C6DC086EFC8443BF5DE03F4; vjuids=-f9ffe457.1481c034ba7.0.1036ead1; _ntes_nnid=05f91a78b5ab44439f7b872d98222393,1409219252159; _ntes_nuid=05f91a78b5ab44439f7b872d98222393; usertrack=ezq0eFQEd6KOyCefByNqAg==; Province=029; City=029; vjlast=1409219251.1413504114.11; vinfo_n_f_l_n3=9da8c81469e2010b.1.2.1412840061761.1412840067289.1413504771640; mailsync=3d9c078d69973e018530a24eb44a66e2743a63ee54afa312267f8c0dc1c5682e7513b419628be079a8dc42bce97830b5; ser_adapter=INTERNAL134; NTES_SESS=RUJIhtVKy20_hUIqUg1TLgGeEnNF2_eLYj7JxOCYEL643X6Zb3l6uE7Rhv2vDLVpUKqSywwJagw1jKxR63hZcIqypJ8vjqrVKc2BOPSmrAT2gJjGj82wjwbBaEr_gzBhWgATIMeeKPDQp; S_INFO=1413906072|0|3&20##|dd222sdsd; P_INFO=dd222sdsd@163.com|1413906072|0|other|00&99|null&null&null#sxi&610100#10#0#0|&0||dd222sdsd@163.com; ANTICSRF=bf3d4a152a50c2dd878f40e6384197bb");
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
	    	String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mobileverify.sendAcode";

			StringBuilder strBui = new StringBuilder();
			Map<String, String> mapData = new LinkedHashMap<String, String>();
			mapData.put("mobile", phone);
			mapData.put("uid", phone+"@163.com");
			mapData.put("mark", "mobile_start");
			
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
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
				con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
				con.setRequestProperty("Referer", "http://reg.email.163.com/unireg/call.do?cmd=register.entrance&from=163mail");
				con.setRequestProperty("Cookie", "JSESSIONID=A598FA321C6DC086EFC8443BF5DE03F4; vjuids=-f9ffe457.1481c034ba7.0.1036ead1; _ntes_nnid=05f91a78b5ab44439f7b872d98222393,1409219252159; _ntes_nuid=05f91a78b5ab44439f7b872d98222393; usertrack=ezq0eFQEd6KOyCefByNqAg==; Province=029; City=029; vjlast=1409219251.1413504114.11; vinfo_n_f_l_n3=9da8c81469e2010b.1.2.1412840061761.1412840067289.1413504771640; mailsync=3d9c078d69973e018530a24eb44a66e2743a63ee54afa312267f8c0dc1c5682e7513b419628be079a8dc42bce97830b5; ser_adapter=INTERNAL134; NTES_SESS=RUJIhtVKy20_hUIqUg1TLgGeEnNF2_eLYj7JxOCYEL643X6Zb3l6uE7Rhv2vDLVpUKqSywwJagw1jKxR63hZcIqypJ8vjqrVKc2BOPSmrAT2gJjGj82wjwbBaEr_gzBhWgATIMeeKPDQp; S_INFO=1413906072|0|3&20##|dd222sdsd; P_INFO=dd222sdsd@163.com|1413906072|0|other|00&99|null&null&null#sxi&610100#10#0#0|&0||dd222sdsd@163.com; ANTICSRF=bf3d4a152a50c2dd878f40e6384197bb");
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
