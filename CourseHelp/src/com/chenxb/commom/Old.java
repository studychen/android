package com.chenxb.commom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.util.Log;
import android.view.View;


public class Old {
}
//	private final class LoginListen implements View.OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			mHandler.post(new Runnable(){
//				@Override
//				public void run() {
//					/* 
//		             * NameValuePair代表一个HEADER，List<NameValuePair>存储全部的头字段 
//		             * UrlEncodedFormEntity类似于URLEncoder语句进行URL编码 
//		             * HttpPost类似于HTTP的POST请求 
//		             * client.execute()类似于发出请求，并返回Response 
//		             */  
//		             
//		            List<NameValuePair> list = new ArrayList<NameValuePair>();  
//		            NameValuePair pair1 = new BasicNameValuePair("j_username", account);  
//		            NameValuePair pair2 = new BasicNameValuePair("j_password", password);  
//		            list.add(pair1);  
//		            list.add(pair2);  
//		            StringBuilder strBui = new StringBuilder("j_username");
////		            j_username=1402120978&j_password=139526
//		            strBui.append("=" + account + "&j_password=" +password);
//					try {
//						//实体根据请求参数构造
//						UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"GBK");
//						HttpPost post = new HttpPost("http://210.27.12.1:90/j_security_check");  
//						post.setHeader("Host", "210.27.12.1:90");
//						post.setHeader("Referer", "http://210.27.12.1:90/login.jsp");
//						post.setHeader("Content-Type", "application/x-www-form-urlencoded" );
//						post.setEntity(entity);  
//			            
//			            
//			            //差不多等同一个浏览器
//						DefaultHttpClient client = new DefaultHttpClient(); 
//			            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, 
//			            		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)"); 
//			           
////			            post.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);  
//			            post.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH); 
//			            
//			            HttpResponse response = client.execute(post); 
////			            String cookie = response.getFirstHeader("Set-Cookie").getValue(); 
////			            String cooknew = cookie.substring(cookie.indexOf("JSE"),cookie.indexOf(";"));  
////			            Log.i("cookie111", cookie);
////            //Set-Cookie: JSESSIONID=E448C61196252D3FCEBB4A7D65531922; Path=/
////			            Log.i("cookie111", cooknew);
////						Toast.makeText(MainActivity.this, cooknew, 1).show();
//			             StreamTool.printResponse(response);
//			            
//			            CookieStore cookieStore = client.getCookieStore();
//			            List<Cookie> listCookie = cookieStore.getCookies();
//			            Log.i(tag, listCookie.toString());
//			            client.setCookieStore(cookieStore);
////			            Log.i("cookie", cookieStore.toString());
////			            headers.setHeader("Cookie", "JSESSIONID=0EBAE958C39AB821AD65A7C0479C2288");
//			            Log.i(tag, String.valueOf(response.getStatusLine().getStatusCode()));
//			            Log.i(tag, String.valueOf(response));
//			            Log.i(tag, String.valueOf(post.getRequestLine()));
////			            String location = response.getFirstHeader("Location").getValue(); 
//			            String newUrl =  "http://210.27.12.1:90/student/index.jsp";
//			            Log.i(tag,"从新定向的页面为：" + newUrl); 
//			            String courseUrl =  "http://210.27.12.1:90/student/selectcourse/myschedule.jsp";
//			            
//			            
//			            
////			            HttpGet get1 = new HttpGet(location);  
//////			            get1.addHeader("Cookie", cooknew); 
////			            response = client.execute(get1); 
////			            HttpEntity entity3 = response.getEntity();  
//////			            String homePage = EntityUtils.toString(entity2,"UTF-8");  
////			            StreamTool.printResponse(response);
//			            
//			            HttpGet get = new HttpGet(courseUrl);  
////			            if ("" != cookie) {  
//////			                get.addHeader("Cookie", "JSESSIONID=2A869564BF0697C7EFB292FFBEA262F6");  
////			                get.addHeader("Cookie", cooknew);  
////			            } 
////			            get.setCookieStore(cookieStore);
////			            get.setHeader("Cookie", "JSESSIONID=JSESSIONID=52CF326CB7DF7B2319272302A43944C2");  
////		                get.addHeader("Cookie", listCookie); 
////			            get.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);
////			            HttpParams  params = client.getParams();
////			            params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);    
//			            
////			            Log.i("homePage",homePage);  
//			            response = client.execute(get); 
//			            HttpEntity entity2 = response.getEntity();  
//			            String page = EntityUtils.toString(entity2,"UTF-8");  
////			            StreamTool.printResponse(response);
//
////						 Toast.makeText(MainActivity.this, homePage, 1).show();
//			            Log.i(tag, String.valueOf(response.getStatusLine().getStatusCode()));
//			            Log.i(tag, String.valueOf(response));
//			            Log.i(tag, String.valueOf(post.getRequestLine()));
//			            Intent intent = new Intent(LoginActivity.this, OldCourseActivity.class);
//			            intent.putExtra("body", page);
//			            startActivity(intent);
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (ClientProtocolException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}  
//				}
//				
//				
//			});
//			
//			
//		}
//
//}
	

