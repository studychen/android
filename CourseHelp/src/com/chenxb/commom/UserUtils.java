package com.chenxb.commom;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chenxb.coursebean.MyInformation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.chenxb.coursebean.Student;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class UserUtils {

	private static final String loginUrl =  "http://210.27.12.1:90/j_security_check";
	private static final String courseUrl =  "http://210.27.12.1:90/student/selectcourse/myschedule.jsp";
	private static final String isUserfulUrl =  "http://210.27.12.1:90/queryExamAction.do";
	private static final String infoUrl =  "http://210.27.12.1:90/student/main.jsp";
	private static final String mainStudentUrl = "http://210.27.12.1:90/student/main.jsp";
	private static final String courseListUrl = "http://210.27.12.1:90/viewStudyPlanAction.do";
	
	private static CookieManager manager = new CookieManager();
	private static OkHttpClient client = new OkHttpClient();
	private static String errorString = "error";
	/**
	 * 判断是否登录成功，获取Cookie
	 * @param name
	 * @param pwd
	 * @return
	 */
	public static String getCookie(String name, String pwd) {
		  CookieHandler.setDefault(manager);
		    
		  //随便用一个错误的密码，获取一个jsessionid
		  RequestBody formBody = new FormEncodingBuilder()
			.add("j_username", "11")
			.add("j_password", "")
			.build();
		  Request request = new Request.Builder()
		      .url(loginUrl)
		      .addHeader("Referer",  "http://210.27.12.1:90/login.jsp" )
		      .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36")
		      .post(formBody)
		      .build();
		  client.setFollowRedirects(false);
		  Response response;
		  try {
				response = client.newCall(request).execute();
				String setCookie = response.header("Set-Cookie");
				if(setCookie != null ) {
					String cookie = setCookie.substring(0,setCookie.indexOf(";"));
					makeCookieUserful(cookie, 
							 name,  pwd);
					return cookie;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return errorString;
	}
	
	
	
	private String getCourse(String myCookie){
		//判断Cookie是否有效
		if(isCookieUserful(myCookie)) {
			 URL url;
				try {
					url = new URL(courseUrl);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();  
					con.setRequestProperty("Cookie", myCookie);
					con.setDoOutput(true);
					System.out.println(con.getResponseCode());
					InputStream inputStr = con.getInputStream();
					return new String(StreamTool.read(inputStr), "GBK");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return("会话超时，请重新登录");
		} else {
			return("会话超时，请重新登录");
		}
	   
		
	}
	
	/**
	 * 根据返回的网页判断cookie是否有效
	 * 
	 * @param myCookie
	 * @return
	 */
	public static boolean isCookieUserful(String myCookie) {
		URL url;
		try {
			url = new URL(mainStudentUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();  
			con.setRequestProperty("Cookie", myCookie);
			con.setDoOutput(true);
			if (con.getResponseCode() == 200){
				InputStream inputStr = con.getInputStream();
				String info = new String(StreamTool.read(inputStr), "UTF-8");
				System.out.println("返回的错误信息"+ info);
				return !info.contains("media/default/images/login_middle_bg.gif");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取个人信息,返回MyInformation类
	 */
	
	public static MyInformation getMyInfo(String myCookie) {
		//判断Cookie是否有效
		if(isCookieUserful(myCookie)) {
			 URL url;
			 try {
				url = new URL(infoUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();  
				con.setRequestProperty("Cookie", myCookie);
				con.setDoOutput(true);
				System.out.println(con.getResponseCode());
				InputStream inputStr = con.getInputStream();
				String html = new String(StreamTool.read(inputStr), "GBK");
				Document doc = Jsoup.parse(html); 
				Elements eles = doc.select("td[width=\"29%\"]");
				eles=eles.get(0).select("td[align=\"center\"]");
				MyInformation.Builder builderInfo = new MyInformation.Builder("1402120978");
				 Pattern pattern = Pattern.compile("[：]([\u4e00-\u9fa5]+)[^0-9]*([0-9]+)");
				 Pattern patternChine = Pattern.compile("[：]([\u4e00-\u9fa5\\s]+)");
				 Pattern patternPointDouble = Pattern.compile("([\\d]+\\.[\\d]+).*([\\d]+\\.[\\d]+)");
				 Pattern patternPointSingle = Pattern.compile("([\\d]+\\.[\\d]+)");
				 
				 Matcher matcher = pattern.matcher(eles.get(0).text());
				 if(matcher.find()) {
					 builderInfo = builderInfo.name(matcher.group(1)).number(matcher.group(2));
					 System.out.println("姓名-------"+matcher.group(1)+"ddddddd");
					 System.out.println("学号-------"+matcher.group(2)+"ddddddd");
				 }
				 matcher = patternChine.matcher(eles.get(3).text()+"ddddddd");
				 if(matcher.find()) {
					 builderInfo = builderInfo.colleage(matcher.group(1));
					 System.out.println("学院----------"+matcher.group(1)+"ddddddd");
				 }
				 matcher = patternChine.matcher(eles.get(5).text()+"ddddddd");
				 if(matcher.find()) {
					 builderInfo = builderInfo.subject(matcher.group(1));
					 System.out.println("专业----------"+matcher.group(1)+"ddddddd");
				 }
				 
				 matcher = patternChine.matcher(eles.get(9).text());
				 if(matcher.find()) {
					 builderInfo = builderInfo.teacher(matcher.group(1));
					 System.out.println("导师----------"+matcher.group(1)+"ddddddd");
				 }
				 
				 matcher = patternPointDouble.matcher(eles.get(13).text());
				 if(matcher.find()) {
					 builderInfo = builderInfo.requirePoint(matcher.group(1))
							 .completePoint(matcher.group(2));
					 System.out.println("总学分----------"+matcher.group(1)+"ddddddd");
					 System.out.println("完成学分----------"+matcher.group(2)+"ddddddd");
				 }
				 
				 matcher = patternPointDouble.matcher(eles.get(15).text());
				 if(matcher.find()) {
					 builderInfo = builderInfo.requireDegreePoint(matcher.group(1))
							 .completeDegreePoint(matcher.group(2));
					 System.out.println("学位课学分----------"+matcher.group(1)+"ddddddd");
					 System.out.println("完成学分----------"+matcher.group(2)+"ddddddd");
				 }
				 matcher = patternPointSingle.matcher(eles.get(17).text());
				 if(matcher.find()) {
					 builderInfo = builderInfo.gpa(matcher.group(1));
					 System.out.println("平均分----------"+matcher.group(1)+"ddddddd");
				 }
				 MyInformation myInfo = builderInfo.builder();
				 System.out.println(myInfo);
				 return myInfo;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
		}
		return null;
	}
	
	/**
	 * 重新发送一个正确的使得让cookie有效
	 */
	private static void makeCookieUserful(String cookie, 
			String name, String pwd) {
		RequestBody formBody = new FormEncodingBuilder()
		  .add("j_username", name)
		  .add("j_password", pwd)
		  .build();
		  
		  String lowerCookie = "jsessionid" + cookie.substring(cookie.indexOf("="));
		  String referUrl = "http://210.27.12.1:90/login.jsp;" 
				  + lowerCookie;
		  Request request = new Request.Builder()
	      .url(loginUrl)
	      .addHeader("Connection",  "keep-alive" )
	      .addHeader("Referer", referUrl)
	      .addHeader("Cookie", cookie)
	      .post(formBody)
	      .build();
		Response response;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
