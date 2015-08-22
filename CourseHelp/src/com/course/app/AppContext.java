package com.course.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.commom.StringUtils;
import com.chenxb.coursebean.CourseBean;


import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
* 全局应用程序类：用于保存和调用全局用配置及访问网络数据
*/


public class AppContext extends Application {
	
	public static final int NETTYPE_WIFI = 0x01;  //wifi
	public static final int NETTYPE_CMWAP = 0x02; //cmwap
	public static final int NETTYPE_CMNET = 0x03; //cmnet
    private static final String tag = "CourseHelp";
	
	
	private static final int CACHE_TIME = 60*60000;//缓存失效时间
	
	public void readCourse(String body) {

		 Document document = Jsoup.parse(body); 
		 Elements testeles = document.select("tr[height=\"50\"]");
		 ArrayList<CourseBean[]> fitElment = new ArrayList<CourseBean[]>(5); 
		 //j表示第几节课
		 for (int j = 0; j < 5; j ++) {
			 Elements element1 =testeles.get(j).children();
			 CourseBean[] tempList = new CourseBean[7];
			 for(int i = 1; i < 8; i ++) {
				 //i表示星期几
				 Element eles = element1.get(i);
				 if(eles.children().size()==0) {
					 
					 tempList[i-1] = new CourseBean();
				 } else {
					 Elements info = eles.select("div");
					 String name = info.get(0).text();
					 name = name.substring(0,name.indexOf("（"));
					 tempList[i-1] = new CourseBean(name, 
							 info.get(1).text(), info.get(2).text(), 
							 info.get(3).text(), i, j);
				 }
			 }
			 fitElment.add(tempList);
		 }
		 for (int j = 0; j < 5; j ++) {
			 CourseBean[] xx= fitElment.get(j);
			 for(int i = 0; i < 7; i ++) {
				 System.out.println(xx[i]);
			 }
		 }
		 saveObject(fitElment, "course.txt");
		
	}
	
	/**
	 * 网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * @return 0：没有网络  1：WIFI网络   2：WAP网络    3：NET网络
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtils.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	public String loginWebsite(String account, String password) {
		// 在联网情况下
		if(isNetworkConnected()) {
			/* 
             * NameValuePair代表一个HEADER，List<NameValuePair>存储全部的头字段 
             * UrlEncodedFormEntity类似于URLEncoder语句进行URL编码 
             * HttpPost类似于HTTP的POST请求 
             * client.execute()类似于发出请求，并返回Response 
             */  
            DefaultHttpClient client = new DefaultHttpClient();  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            NameValuePair pair1 = new BasicNameValuePair("j_username", account);  
            NameValuePair pair2 = new BasicNameValuePair("j_password", password);  
            list.add(pair1);  
            list.add(pair2);  
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
				HttpPost post = new HttpPost("http://210.27.12.1:90/j_security_check");  
	            post.setEntity(entity);  
	            HttpResponse response = client.execute(post); 
	            Log.i(tag, String.valueOf(response.getStatusLine().getStatusCode()));
	            return String.valueOf(response.getStatusLine().getStatusCode()==200);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return null;
	}
	
	/**
	 * 保存对象
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try {
				oos.close();
			} catch (Exception e) {}
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	/**
	 * 读取对象
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file){
		if(!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace();
			//反序列化失败 - 删除缓存文件
			if(e instanceof InvalidClassException){
				File data = getFileStreamPath(file);
				data.delete();
			}
		}finally{
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	/**
	 * 判断缓存是否存在
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile)
	{
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists())
			exist = true;
		return exist;
	}
	
	
	
	
}
