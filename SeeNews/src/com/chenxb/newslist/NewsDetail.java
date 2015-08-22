package com.chenxb.newslist;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.util.Log;

import com.chenxb.commom.StreamTool;
import com.chenxb.mybean.News;

/**
 * @name Jsoup�����������
 * @description �߳��и�ݲ�ͬ�ı�Ǿ�������һ�����Ż��������б�
 * @author ������
 * @date 2014-4-29
 * 
 */
public class NewsDetail  implements Runnable {
	private final String tag = "getNewsDetail";
	
	private Elements elements;
	private Document document;
	private Element element;
	private String newsDetailUrl; //���ӵ�ַ
	
	private Pattern pattern = Pattern.compile("\\d+");  

	
	public NewsDetail(String url) {
			this.newsDetailUrl = url;
	}

	/**
	 * ����һ�����ŵ���ϸ
	 * @throws Exception 
	 * @throws ScriptException 
	 */
	@SuppressLint("NewApi")
	public News loadNewsDetail() throws Exception {
			try {
				document = Jsoup.connect(newsDetailUrl).timeout(100000).get();
				// ���ű����Դ��ͽ���
				element = document.getElementById("article_title");
//				System.out.println(element.toString());
				String title = element.text();

				element = document.getElementById("article_detail");
//				System.out.println(element.toString());
				String pubDate = element.select("span").get(0).text(); 
				
				int readCount = getReadCount();
				
				element = document.getElementById("article_content");
				StringBuilder contentStr = new StringBuilder();
				
				
				contentStr.append( element.html().toString() );
//				//有p标签可能也有span标签
//				//有span标签一定没有p标签
//				if (! element.select("p").text().isEmpty() &&
//						element.select("p").text() != null) {
//					elements = element.select("p");
//					for (Element ele : elements) {
//						if (ele.toString().contains("<img")) {
//							contentStr.append(ele.toString());
//						}  else {
//							contentStr.append("<p>" + ele.text().replaceAll(
//									Jsoup.parse("&nbsp;").text(), "") +"</p>");
////							System.out.println(ele.text().replaceAll(
////									Jsoup.parse("&nbsp;").text(), " "));
//							
//						}
//						
//					}
//				} else if( ! element.select("span").text().isEmpty() &&
//						element.select("span").text() != null ) {
//					elements = element.select("span");
//					for (Element ele : elements) {
//						if (ele.toString().contains("<img")) {
//							contentStr.append(ele.toString());
//						}  else {
//						contentStr.append("<p>" + ele.text().replaceAll(
//								Jsoup.parse("&nbsp;").text(), "") +"</p>");
//						}
//					}
//				} else {
//					contentStr.append("<p>" + element.html().toString() + "</p>");
//				}
////				
				
				return new News(title, pubDate, readCount, contentStr.toString());
				
				
			} catch (IOException e) {
				Log.i(tag,  "0101010101");
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
			return null;
			

			
	}

	public String getNewsDetailUrl() {
		return newsDetailUrl;
	}

	
	//�����ҳ���������
	private int getReadCount() throws Exception {
		 Matcher matcher = pattern.matcher(newsDetailUrl);  
		 String num = "7027"; //��Ժ��ַ���Ӻ���Ĵ���
         while (matcher.find()) {  
             num =  matcher.group(0);
         }
		
        String ScriptUrl = "http://see.xidian.edu.cn/index.php/news/click/id/" + num;

		URL url = new URL(ScriptUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty(
                       "User-Agent",
                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
		con.setConnectTimeout(5000);
		con.setRequestMethod("GET");
		
		if (con.getResponseCode() == 200){
			InputStream inputStr = con.getInputStream();
			String info = new String(StreamTool.read(inputStr), "UTF-8");
			Log.i(tag, info);
			matcher = pattern.matcher(info);  
	        while (matcher.find()) {  
	            num =  matcher.group(0); 
	        }
	        return Integer.valueOf(num);
		}
		return 233;
	}

	@Override
	public void run() {
		try {
			loadNewsDetail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
}

