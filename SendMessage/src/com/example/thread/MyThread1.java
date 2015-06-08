package com.example.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import android.provider.Telephony.Threads;
import android.util.Log;

import com.example.StreamTool.StreamTool;

public class MyThread1 implements Callable<String>  {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread1(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 
	
//	public MyThread1(String name, String phone) {
//		super(name);
//		this.phone = phone;
//    } 

//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//    	String newpath = "http://www.dianping.com/ajax/json/account/reg/mobile/send?m=" + phone + "&flow=t&callback=DP._JSONPRequest._5";
//		StringBuilder strBui = new StringBuilder(newpath);
//
//		try {
//			URL url = new URL(strBui.toString());
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.addRequestProperty("Content-Type", "text/html; charset=UTF-8");
//			con.addRequestProperty(
//	                       "User-Agent",
//	                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
//			con.addRequestProperty("Cookie", "_hc.v=\"\\\"0ffcdf91-a4d6-4b08-8800-3d59d2e2fe45.1413899372\\\"\"; abtest=\"48,124\\|52,133\\|47,122\\|44,106\\|45,115\"; __utma=1.29319792.1412090870.1413609669.1413645970.5; __utmz=1.1413645970.5.2.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral|utmcct=/xian; _tr.u=7dhY0yFhd9rY065F; tc=17; cy=17; cye=xian; t_rct=2172824|2171729|6457678; PHOENIX_ID=0a01743f-1492e1321c8-46e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd6495620e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E0611A3E4CF48; _hc.v=\"\\\"1bdad871-405e-46e3-97d6-46571ad48e14.1413897928\\\"\"; _tr.s=Zy1Ca2OAe1ptv06o; JSESSIONID=891671FC6B450418DD6E0611A3E4CF48");
//			con.addRequestProperty("Referer", "http://t.dianping.com/register");
//			con.setConnectTimeout(5000);
//			con.setRequestMethod("GET");
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
//        
//    }

	@Override
	public String call() throws Exception {
	    	Log.i(tag, Thread.currentThread().getName() + "......start");
	    	String newpath = "http://www.dianping.com/ajax/json/account/reg/mobile/send?m=" + phone + "&flow=t&callback=DP._JSONPRequest._5";
			StringBuilder strBui = new StringBuilder(newpath);

			try {
				URL url = new URL(strBui.toString());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.addRequestProperty("Content-Type", "text/html; charset=UTF-8");
				con.addRequestProperty(
		                       "User-Agent",
		                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
				con.addRequestProperty("Cookie", "_hc.v=\"\\\"0ffcdf91-a4d6-4b08-8800-3d59d2e2fe45.1413899372\\\"\"; abtest=\"48,124\\|52,133\\|47,122\\|44,106\\|45,115\"; __utma=1.29319792.1412090870.1413609669.1413645970.5; __utmz=1.1413645970.5.2.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral|utmcct=/xian; _tr.u=7dhY0yFhd9rY065F; tc=17; cy=17; cye=xian; t_rct=2172824|2171729|6457678; PHOENIX_ID=0a01743f-1492e1321c8-46e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd6495620e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E0611A3E4CF48; _hc.v=\"\\\"1bdad871-405e-46e3-97d6-46571ad48e14.1413897928\\\"\"; _tr.s=Zy1Ca2OAe1ptv06o; JSESSIONID=891671FC6B450418DD6E0611A3E4CF48");
				con.addRequestProperty("Referer", "http://t.dianping.com/register");
				con.setConnectTimeout(5000);
				con.setRequestMethod("GET");
				
				float x = 3.4f;
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
