package com.monitor.app;

import java.io.ByteArrayOutputStream;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.monitor.bean.WebInfo;
import com.monitor.common.StringUtils;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
/**
* 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
* @author liux (http://my.oschina.net/liux)
* @version 1.0
* @created 2012-3-21
*/


public class AppContext extends Application {
	
	public static final int NETTYPE_WIFI = 0x01;  //wifi
	public static final int NETTYPE_CMWAP = 0x02; //cmwap
	public static final int NETTYPE_CMNET = 0x03; //cmnet
	
	
	private static final int CACHE_TIME = 60*60000;//缓存失效时间
	public static final int NEWSLIST_EVERY = 10; //每次只加载10条数据，下拉时候再加载
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
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
	
//	/**
//	 * 判断缓存是否失效
//	 * @param cachefile
//	 * @return
//	 */
//	public boolean isCacheDataFailure(String cachefile)
//	{
//		boolean failure = false;
//		File data = getFileStreamPath(cachefile);
//		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
//			failure = true;
//		else if(!data.exists())
//			failure = true;
//		return failure;
//	}
//	
//	/**
//	 * 保存对象
//	 * @param ser
//	 * @param file
//	 * @throws IOException
//	 */
//	public boolean saveObject(Serializable ser, String file) {
//		FileOutputStream fos = null;
//		ObjectOutputStream oos = null;
//		try{
//			fos = openFileOutput(file, MODE_PRIVATE);
//			oos = new ObjectOutputStream(fos);
//			oos.writeObject(ser);
//			oos.flush();
//			return true;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}finally{
//			try {
//				oos.close();
//			} catch (Exception e) {}
//			try {
//				fos.close();
//			} catch (Exception e) {}
//		}
//	}
//	/**
//	 * 读取对象
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public Serializable readObject(String file){
//		if(!isExistDataCache(file))
//			return null;
//		FileInputStream fis = null;
//		ObjectInputStream ois = null;
//		try{
//			fis = openFileInput(file);
//			ois = new ObjectInputStream(fis);
//			return (Serializable)ois.readObject();
//		}catch(FileNotFoundException e){
//		}catch(Exception e){
//			e.printStackTrace();
//			//反序列化失败 - 删除缓存文件
//			if(e instanceof InvalidClassException){
//				File data = getFileStreamPath(file);
//				data.delete();
//			}
//		}finally{
//			try {
//				ois.close();
//			} catch (Exception e) {}
//			try {
//				fis.close();
//			} catch (Exception e) {}
//		}
//		return null;
//	}
//	
//	/**
//	 * 读取对象 通过第一次读取缓存
//	 * @param file
//	 * @return
//	 * @throws IOException
//	 */
//	public Serializable readObject(InputStream input){
//		FileInputStream fis = null;
//		ObjectInputStream ois = null;
//		try{
//			ois = new ObjectInputStream(input);
//			Log.i("ser", ois.toString());
//			return (Serializable)ois.readObject();
//		}catch(FileNotFoundException e){
//		}catch(Exception e){
//			e.printStackTrace(System.err);
//			//反序列化失败 - 删除缓存文件
//		}finally{
//			try {
//				ois.close();
//			} catch (Exception e) {}
//			try {
//				fis.close();
//			} catch (Exception e) {}
//		}
//		return null;
//	}
//	
//	/**
//	 * 判断缓存是否存在
//	 * @param cachefile
//	 * @return
//	 */
//	private boolean isExistDataCache(String cachefile)
//	{
//		boolean exist = false;
//		File data = getFileStreamPath(cachefile);
//		if(data.exists())
//			exist = true;
//		return exist;
//	}
//	
//	
//	/**
//	 * 判断缓存数据是否可读
//	 * 也就是缓存是否存在
//	 * @param cachefile
//	 * @return
//	 */
//
//	private boolean isReadDataCache(String cachefile) {
//		return readObject(cachefile) != null;
//		// TODO Auto-generated method stub
//	}
//	
}
