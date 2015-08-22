package com.chenxb.app.api;

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

import com.chenxb.commom.StringUtils;
import com.chenxb.commom.UIHelper;
import com.chenxb.newslist.NewsDetail;
import com.chenxb.newslist.NewsList;
import com.chenxb.mybean.News;
import com.chenxb.mybean.NewsListItem;
import com.example.service.CollectService;
import com.example.service.FileService;
import com.example.service.MydbOpenHelper;
import com.studyday.seenews.MainActivity;
import com.studyday.seenews.R;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	
	//设置才用新浪的服务器还是电院服务器
	//true表示电院
	private static boolean isDianyuan = true;

	public boolean isDianyuan() {
		return isDianyuan;
	}

	public void setDianyuan(boolean isDianyuan) {
		this.isDianyuan = isDianyuan;
	}
	
	
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
	
	/**
	 * 判断缓存是否失效
	 * @param cachefile
	 * @return
	 */
	public boolean isCacheDataFailure(String cachefile)
	{
		boolean failure = false;
		File data = getFileStreamPath(cachefile);
		if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if(!data.exists())
			failure = true;
		return failure;
	}
	
//	public List<Map<String, String>> getLastNewsList(boolean readJson)  {
//		if(readJson) {
//			
//		} else {
//			getLastNewsList();
//		}
//	}
//	
	
//	/**
//	 * 获取最新新闻列表 有网络和无网络分开
//	 * 使用实体化
//	 * @return
//	 */
//	
//	public List<Map<String, String>> getLastNewsList()  {
//		if( isNetworkConnected() ) {
//			Log.i("ser", "seryes");
//			NewsList newsList;
//			try {
//				newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/index.php/index/more");
//				List<NewsListItem> getNewsList;
//				saveObject(newsList, "Ser11.txt");
//				getNewsList = newsList.getList();
//				Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
//				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//			
//				
//				while (getNewsListIte.hasNext()) {
//					NewsListItem newsItem = getNewsListIte.next();
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("title", newsItem.getTitle());
//					map.put("time", newsItem.getDate());
//					map.put("read", "浏览次数：" + newsItem.getClick());
//					map.put("url",  newsItem.getUrl());
//					list.add(map);
//				}
//				return list;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		} else {
//			/**
//			 * 获取新闻列表通过保持的JSON文本文件
//			 * 无网络连接时使用
//			 * @return
//			 */
//			Log.i("ser", "serynono");
//			NewsList newsList = (NewsList) readObject("Ser11.txt");
//			try {
//				List<NewsListItem> getNewsList = newsList.getList();
//				Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
//				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//				
//				while (getNewsListIte.hasNext()) {
//					NewsListItem newsItem = getNewsListIte.next();
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("title", newsItem.getTitle());
//					map.put("time", newsItem.getDate());
//					map.put("read", "浏览次数：" + newsItem.getClick());
//					map.put("url",  newsItem.getUrl());
//					list.add(map);
//			}
//			return list;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//	
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
	 * 读取对象 通过第一次读取缓存
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(InputStream input){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(input);
			Log.i("ser", ois.toString());
			return (Serializable)ois.readObject();
		}catch(FileNotFoundException e){
		}catch(Exception e){
			e.printStackTrace(System.err);
			//反序列化失败 - 删除缓存文件
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
	
	
	/**
	 * 获取最新新闻列表 有网络和无网络分开
	 * @return
	 */
	
	/**
	 * 获取最新新闻列表 有网络和无网络分开
	 * @return
	 */
//	使用JSON，后改成实体类
//	public List<Map<String, String>> getLastNewsList()  {
//	if( isNetworkConnected() ) {
//		NewsList newsList = new NewsList("http://see.xidian.edu.cn/index.php/index/more");;
//			
//			List<NewsListItem> getNewsList;
//			try {
//				getNewsList = newsList.loadNewsListItem();
//				Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
//				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//				
//				//保存json数据
//				JSONArray array=new JSONArray();  
//				
//				while (getNewsListIte.hasNext()) {
//					NewsListItem newsItem = getNewsListIte.next();
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("title", newsItem.getTitle());
//					map.put("time", newsItem.getDate());
//					map.put("read", "浏览次数：" + newsItem.getClick());
//					map.put("url",  newsItem.getUrl());
//					list.add(map);
//					
//					JSONObject jsonObject=new JSONObject(); 
//					jsonObject.put("title", newsItem.getTitle());
//					jsonObject.put("time", newsItem.getDate());
//					jsonObject.put("read", newsItem.getClick());
//					jsonObject.put("url",  newsItem.getUrl());
//					array.put(jsonObject);
//				}
//				
//					
//				 String jsonData=new JSONStringer().object().
//						 key("newslistlst").value(array).endObject().toString();  
//			
//				 //保存json缓存，当无网络时访问
//				 FileService ser = new FileService(this);
//				 
//				 // 保存数据 第一次的时候都保存为 选项名+txt格式 ，供缓存时候访问
//				 ser.save(item.toString()+".txt", jsonData);
//				return list;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		} else {
//			
//			/**
//			 * 获取新闻列表通过保持的JSON文本文件
//			 * 无网络连接时使用
//			 * @return
//			 */
//			
//			FileService ser = new FileService(this);
//			String readJSON;
//			try {
//				readJSON = ser.read("listtt22.txt");
//				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//			       
//				JSONObject jsonObject = new JSONObject(readJSON);
//				JSONArray jsonArray=jsonObject.getJSONArray("newslistlst");  
//		        for(int i = 0; i < jsonArray.length(); i++){  
//		            JSONObject json=jsonArray.getJSONObject(i);  
//		            Map<String, String> map = new HashMap<String, String>();
//		            map.put("title", json.optString("title"));
//		            map.put("time", json.optString("time"));
//		            map.put("read", "浏览次数：" + json.optString("read"));
//		            map.put("url", json.optString("url"));
//		            list.add(map);
//		        }
//		        return list;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//}
//
	/**
	 * 缓存存在就读缓存，否则就联网读取
	 * 这样方便第一次加载页面后
	 * 第二次加载页面就直接读取缓存，
	 * 第二次下拉刷新来读取列表
	 * @param item
	 * @return
	 */
	public List<Map<String, String>> getNewsListInter(int item, boolean isRefresh, int start)  {
		NewsList newsList;
		List<Map<String, String>> list;
		String key = String.valueOf(item) + ".txt";
		//联网情况下， 如果缓存不可以读，或者主动要求刷新，或者 缓存超过一段时间 
		if(isNetworkConnected() && (!isReadDataCache(key) || isRefresh || isCacheDataFailure(key))) {
			//先赋值为电院的新闻列表
			if(isDianyuan) {
				switch (item) {
				case R.id.last_news :
					newsList =  NewsList.loadNewsListItem("http://see.xidian.edu.cn/index.php/index/more");;
					break;
				case R.id.notice_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/1.html");
					break;
				case R.id.undergraduate_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/2.html");
					break;
				case R.id.postgraduate_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/3.html");
					break;
				case R.id.research_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/4.html");
					break;
				case R.id.academic_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/5.html");
					break;
				case R.id.net_news :
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/html/category/6.html");
					break;
				default:
					newsList = NewsList.loadNewsListItem("http://see.xidian.edu.cn/index.php/index/more");
					break;
				}
				
			} else {
				switch (item) {
				case R.id.last_news :
					newsList =  NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=0");;
					break;
				case R.id.notice_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=1");
					break;
				case R.id.undergraduate_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=2");
					break;
				case R.id.postgraduate_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=3");
					break;
				case R.id.research_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=4");
					break;
				case R.id.academic_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=5");
					break;
				case R.id.net_news :
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=6");
					break;
				default:
					newsList = NewsList.loadNewsListItem("http://seenews.sinaapp.com/jsp/list.jsp?index=0");
					break;
				}
			}
			
			Log.i("ser", "seryes");
			List<NewsListItem> getNewsList;
			
			//保存缓存，如果存在就直接读取缓存
			saveObject(newsList, key);
			getNewsList = newsList.getList();
			Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
			list = new ArrayList<Map<String, String>>();
		
			
			while (getNewsListIte.hasNext()) {
				NewsListItem newsItem = getNewsListIte.next();
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", newsItem.getTitle());
				map.put("time", newsItem.getDate());
				map.put("read", "浏览次数：" + newsItem.getClick());
				map.put("url",  newsItem.getUrl());
				list.add(map);
			}
			
		//为了第一次，还没有建立缓存目录，读取eclipse里的文件
		} else if (!isReadDataCache(key)) {
			Log.i("ser", "nononon");
			Log.i("ser", "1111");
			Log.i("ser", "2222");
			AssetManager am = null; 
			am = getAssets(); 
			list = null;
			try {
				InputStream is = am.open("tempdatalist/" + String.valueOf(item) + ".txt");
				newsList = (NewsList) readObject(is);
				List<NewsListItem> getNewsList = newsList.getList();
				Log.i("ser", "333");
				Log.i("ser", "length" + String.valueOf(getNewsList.size()));
				
				Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
				list = new ArrayList<Map<String, String>>();
				
				while (getNewsListIte.hasNext()) {
					NewsListItem newsItem = getNewsListIte.next();
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", newsItem.getTitle());
					map.put("time", newsItem.getDate());
					map.put("read", "浏览次数：" + newsItem.getClick());
					map.put("url",  newsItem.getUrl());
					list.add(map);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
				Log.i("ser", "err");
			} 
			
			
		} else{
			/**
			 * 获取新闻列表通过保持的JSON文本文件
			 * 无网络连接时使用
			 * @return
			 */
			newsList = (NewsList) readObject(String.valueOf(item) + ".txt");
			List<NewsListItem> getNewsList = newsList.getList();
			Iterator<NewsListItem> getNewsListIte = getNewsList.iterator();
			list = new ArrayList<Map<String, String>>();
			
			while (getNewsListIte.hasNext()) {
				NewsListItem newsItem = getNewsListIte.next();
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", newsItem.getTitle());
				map.put("time", newsItem.getDate());
				map.put("read", "浏览次数：" + newsItem.getClick());
				map.put("url",  newsItem.getUrl());
				list.add(map);
			}
		}
		
		if (list!=null && start < list.size() && start + NEWSLIST_EVERY < list.size() ) {
			return list.subList(start, start + NEWSLIST_EVERY);
		} else if(start < list.size()) {
			return list.subList(start, list.size());
		} 
		Log.i("ser", "null");
		return null;
		
	}
	
	public List<Map<String, String>> getNewsListCollect(AppContext appContext)  {
		String key = "course.txt";
		MydbOpenHelper dbopen = new MydbOpenHelper(appContext);
		CollectService collectService = new CollectService(dbopen);
		List<Integer> collects = collectService.getAllCollect();
		ListIterator<Integer> iterator = collects.listIterator();
		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		while(iterator.hasNext()) {
			int num = iterator.next();
			String url = "http://see.xid" +
					"ian.edu.cn/html/news/"+num+".html";
			News newsTemp;
			try {
				newsTemp = new NewsDetail(url).loadNewsDetail();
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", newsTemp.getTitle());
				map.put("time", newsTemp.getPubDate());
				map.put("read", "浏览次数：" + newsTemp.getReadCount());
				map.put("url",  url);
				list.add(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
		
		
			
			
	}
	
	/**
	 * 判断缓存数据是否可读
	 * 也就是缓存是否存在
	 * @param cachefile
	 * @return
	 */

	private boolean isReadDataCache(String cachefile) {
		return readObject(cachefile) != null;
		// TODO Auto-generated method stub
	}
}
