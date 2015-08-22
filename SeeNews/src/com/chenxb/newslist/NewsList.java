package com.chenxb.newslist;

import java.io.FileOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.test.AndroidTestCase;
import android.util.Log;

import com.chenxb.mybean.News;
import com.chenxb.mybean.NewsListItem;


public class NewsList extends Application  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2587984874709755198L;

	//读取新闻列表
	public List<NewsListItem> getList() {
		return list;
	}
	
	

	private final static String tag = "NewsList";
//	private Elements elements;
//	private Document document;
//	private Element element;
//	private String newsListUrl;
	
	private List<NewsListItem> list = new ArrayList<NewsListItem>();
	
	private static final int CACHE_TIME = 60*60000;//缓存失效时间
	
//	public NewsList(String newsListUrl) {
//		this.newsListUrl = newsListUrl;
//	}
	
	public static NewsList loadNewsListItem(String newsListUrl) {
		NewsList newsList = new NewsList();
		Log.i("NewsList",  "loadNewsListItem010101");
		try {
			Document document = Jsoup.connect(newsListUrl).timeout(100000).get();
			Element element = document.getElementById("list_area");
			ListIterator<Element> eleIte =element.select("li").listIterator();
//			List<NewsListItem> newsList = new ArrayList<NewsListItem> ();
			while (eleIte.hasNext()) {
				Element ele = eleIte.next();
				String url = ele.select("a[href]").get(0).attr("href");
				String date = ele.getElementsByClass("left_date").text();
//				String newdate = date.substring(1, date.length()-1);
				String title = ele.select("a[href]").get(0).text().replace(date, "");
				String click = ele.getElementsByClass("news_date").text();
				
				
				NewsListItem tempNewsList = new NewsListItem(url, date, title, click);
				newsList.list.add(tempNewsList);
			}
			return newsList;
		} catch (IOException e) {
			Log.i(tag,  "0101010101");
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "NewsList [list=" + list + "]";
	}

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
//	
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


}
