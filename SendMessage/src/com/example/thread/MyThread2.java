package com.example.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import android.util.Log;

import com.example.StreamTool.StreamTool;

public class MyThread2  implements Callable<String> {
	private static final String tag = "xxxyyy";
	private String phone;
	private String name;
	public MyThread2(String name, String phone) {
        this.phone = phone;
        this.name = name;
    } 
	
//	//* sendCallBack(1)
//    public void run() {
//    	Log.i(tag, Thread.currentThread().getName() + "......start");
//
//    	String newpath = "http://smsspub.mail.163.com/mobileserv/fsms.do?product=AndroidMail&template=ds42&callback=sendCallback&mobile=" + phone + "&_=1413906120674";
//		StringBuilder strBui = new StringBuilder(newpath);
//		try {
//			URL url = new URL(strBui.toString());
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.addRequestProperty("Content-Type", "text/html; charset=UTF-8");
//			con.addRequestProperty(
//	                       "User-Agent",
//	                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
//			con.setConnectTimeout(5000);
//			con.setRequestMethod("GET");
//			if (con.getResponseCode() == 200){
//				InputStream inputStr = con.getInputStream();
//				String info = new String(StreamTool.read(inputStr), "UTF-8");
//				Log.i(tag, Thread.currentThread().getName() + info);
//		}
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
		//* sendCallBack(1)
	    	Log.i(tag, Thread.currentThread().getName() + "......start");

	    	String newpath = "http://smsspub.mail.163.com/mobileserv/fsms.do?product=AndroidMail&template=ds42&callback=sendCallback&mobile=" + phone + "&_=1413906120674";
			StringBuilder strBui = new StringBuilder(newpath);
			try {
				URL url = new URL(strBui.toString());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.addRequestProperty("Content-Type", "text/html; charset=UTF-8");
				con.addRequestProperty(
		                       "User-Agent",
		                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
				con.setConnectTimeout(5000);
				con.setRequestMethod("GET");
				if (con.getResponseCode() == 200){
					InputStream inputStr = con.getInputStream();
					String info = new String(StreamTool.read(inputStr), "UTF-8");
					Log.i(tag, Thread.currentThread().getName() + info);
					if(info.contains("(1)"))
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
