package com.example.sendmessage;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.example.StreamTool.StreamTool;

import android.util.Log;
import android.widget.EditText;

public class ManagerService {
	private static final String tag = "xxxyyy";
	
	public static boolean sendData(String phone, String times) throws Exception {
//		return sendMethodPOSTDianping(phone);
		return sendMethodGETDianping(phone) | sendMethodGETSmsspub(phone) | 
				sendMethodPOSTDianping(phone) | sendMethodPOSTSendMO(phone) 
				| sendMethodPOSTSendAcode(phone) | sendMethodPOSTSina(phone);
		
	}

	
/**
 * sendMethodGET  通过大众点评接口，1分钟只能发一条
 * 已经注册的不行
 * get 网站返回值 {"code":200,"msg":{"info":""}}
 * @param path
 * @param mapData
 * @return
 * @throws Exception
 */
	public static boolean sendMethodGETDianping(String phone) throws Exception {
		String newpath = "http://www.dianping.com/ajax/json/account/reg/mobile/send?m=" + 
					phone + "&flow=t&callback=DP._JSONPRequest._5";
		StringBuilder strBui = new StringBuilder(newpath);

		URL url = new URL(strBui.toString());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Content-Type", "text/html; charset=UTF-8");
		con.addRequestProperty(
                       "User-Agent",
                       "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
		con.addRequestProperty("Cookie", "_hc.v=\"\\\"0ffcdf91-a4d6-4b08-8800-3d59d2e2" +
				"fe45.1413899372\\\"\"; abtest=\"48,124\\|52,133\\|47,122\\|44,106\\|45," +
				"115\"; __utma=1.29319792.1412090870.1413609669.1413645970.5; __utmz=1.141" +
				"3645970.5.2.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral" +
				"|utmcct=/xian; _tr.u=7dhY0yFhd9rY065F; tc=17; cy=17; cye=xian; t_r" +
				"ct=2172824|2171729|6457678; PHOENIX_ID=0a01743f-1492e1321c8-4" +
				"6e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd649562" +
				"0e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E061" +
				"1A3E4CF48; _hc.v=\"\\\"1bdad871-405e-46e3-97d6-46571ad48e14." +
				"1413897928\\\"\"; _tr.s=Zy1Ca2OAe1ptv06o; JSESSIONID=891671" +
				"FC6B450418DD6E0611A3E4CF48");
		con.addRequestProperty("Referer", "http://t.dianping.com/register");
		con.setConnectTimeout(5000);
		con.setRequestMethod("GET");
		
		if (con.getResponseCode() == 200){
			InputStream inputStr = con.getInputStream();
			String info = new String(StreamTool.read(inputStr), "UTF-8");
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
	/**
	 * sendMethodGET  通过网易邮箱手机大师 1分钟2条
	 * sendCallBack(1)
	 * @param path
	 * @param mapData
	 * @return
	 * @throws Exception
	 */
	public static boolean sendMethodGETSmsspub(String phone) throws Exception {
		String newpath = "http://smsspub.mail.163.com/mobileserv/fsms.do?product=AndroidMail&template=ds42&callback=sendCallback&mobile=" + phone + "&_=1413906120674";
		StringBuilder strBui = new StringBuilder(newpath);

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
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
	/**
	 * 大众点评动态密码登录 post请求 
	 * {"code":200,"msg":{"info":""}}
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	
	public static boolean sendMethodPOSTDianping(String phone) throws Exception  {
		String path = "http://t.dianping.com/ajax/wwwaccount/mobiledynamiclogincode/1";
		StringBuilder strBui = new StringBuilder();
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("mobile", phone);
		
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
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8;");
		con.setRequestProperty("Cookie", "_hc.v=\"\\\"974b3790-2fb8-4474-9dd3-7dcfcfb9ab71.1413977875\\\"\"; _tr.u=7dhY0yFhd9rY065F; PHOENIX_ID=0a01743f-1492e1321c8-46e6a; 13.t=17deda7d2a7d8e46d84bf12a74627b72; 13.ts=cd3bd6495620e2a204912c4bdfd43220; thirdtoken=891671FC6B450418DD6E0611A3E4CF48; ctu=9d825aff821c23e0ef29757efdbaf12d3e6afc896adba2aa80516a6f9e0fc4a2; aburl=1; __utma=1.29319792.1412090870.1413645970.1413903744.6; __utmc=1; __utmz=1.1413903744.6.3.utmcsr=t.dianping.com|utmccn=(referral)|utmcmd=referral|utmcct=/account/settings; ctu=5ef3e5a6e5c041ee1bf6439112f523af59a0bc2461dd1ebbe1ebfc266405a3c1ad258e23c540056b0645c1075845d5a1; ua=%E6%B5%AA%E5%AD%90%E5%9F%8B%E7%B0%AA1; llm=15691856329; _hc.v=\"\\\"9c142919-e376-4a3d-9327-7e0c0251e534.1413903950\\\"\"; combined_payment_order=; tg_list_scroll=500; tc=17; cy=17; cye=xian; t_rct=6082646|6246400|2172824|2171729|6457678; JSESSIONID=DC57C0FCD7A9CE99CC47A3CC1575880B; _tr.s=Zy1Ca2OAe1ptv06o");
		con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
		con.setRequestProperty("Referer", "ttp://t.dianping.com/login");
		con.setDoOutput(true);
		OutputStream outStr = con.getOutputStream();
		outStr.write(strBui.toString().getBytes());
		
		if (con.getResponseCode() == 200){
			InputStream inputStr = con.getInputStream();
			String info = new String(StreamTool.read(inputStr), "UTF-8");
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
	/**
	 * 用网易彩票接口 一天3条
	 * @param titleStr
	 * @param mapDataNew
	 * @return
	 * @throws Exception
	 */
	public static boolean sendMethodPOSTSendMO(String phone) throws Exception  {
		String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mo.sendMO";
		StringBuilder strBui = new StringBuilder();
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("mobile", phone);
		mapData.put("uid", "xxxyyy1020@126.com");
		mapData.put("mark", "success_lottery");
		for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
			strBui.append(mapEnt.getKey() + "=" );
			strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//			Log.i(tag, mapEnt.getValue());
			strBui.append("&");
		}
		strBui.deleteCharAt(strBui.length()-1);
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(5000);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
		con.setDoOutput(true);
		OutputStream outStr = con.getOutputStream();
		outStr.write(strBui.toString().getBytes());
		
		if (con.getResponseCode() == 200){
			InputStream inputStr = con.getInputStream();
			String info = new String(StreamTool.read(inputStr), "UTF-8");
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
	/**
	 * 163手机邮箱注册 一天3条
	 * {"code":200,"desc":"OK","msg":"2"}
	 * cookie 会失效吗？
	 * mobile 手机号
	 */
	public static boolean sendMethodPOSTSendAcode(String phone) throws Exception  {
		String path = "http://reg.email.163.com/unireg/call.do?cmd=added.mobileverify.sendAcode";

		StringBuilder strBui = new StringBuilder();
		Map<String, String> mapData = new LinkedHashMap<String, String>();
		mapData.put("mobile", phone);
		mapData.put("uid", phone+"@163.com");
		mapData.put("mark", "mobile_start");
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
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
//	/**
//	 * 通过网易红包 
//	 * 和手机注册的接口一样
//	 * code 200
//	 * @param titleStr
//	 * @param mapDataNew
//	 * @return
//	 * @throws Exception
//	 */
//	public static boolean sendMethodPOSTSucOne(String phone) throws Exception  {
//		String path = "http://reg.email.126.com/unireg/call.do?cmd=added.mobileverify.sendAcode";
//		StringBuilder strBui = new StringBuilder();
//		Map<String, String> mapData = new HashMap<String, String>();
//		mapData.put("mobile", phone);
//		mapData.put("uid", "xxyy102011@126.com");
//		mapData.put("mark", "success_one"); //此处不一样
//		for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
//			strBui.append(mapEnt.getKey() + "=" );
//			strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//			strBui.append("&");
//		}
//		strBui.deleteCharAt(strBui.length()-1);
//		URL url = new URL(path);
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setConnectTimeout(5000);
//		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
//		con.setRequestProperty("Referer", "Referer:http://reg.email.163.com/unireg/call.do?cmd=register.entrance&from=126mail");
//		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
//		con.setRequestProperty("Cookie", "JSESSIONID=8A45E05B6833F80E741D8AF2CF45E404; vjuids=-f9ffe457.1481c034ba7.0.1036ead1; _ntes_nnid=05f91a78b5ab44439f7b872d98222393,1409219252159; _ntes_nuid=05f91a78b5ab44439f7b872d98222393; usertrack=ezq0eFQEd6KOyCefByNqAg==; Province=029; City=029; vjlast=1409219251.1413504114.11; vinfo_n_f_l_n3=9da8c81469e2010b.1.2.1412840061761.1412840067289.1413504771640; P_INFO=xxxyyy1020@126.com|1413797229|0|other|00&99|sxi&1413794601&mail126#sxi&610100#10#0#0|&0|mail126|xxxyyy1020@126.com; mailsync=3d9c078d69973e018530a24eb44a66e2743a63ee54afa312267f8c0dc1c5682e7513b419628be079a8dc42bce97830b5; ser_adapter=INTERNAL134");
//		con.setDoOutput(true);
//		OutputStream outStr = con.getOutputStream();
//		outStr.write(strBui.toString().getBytes());
//		
//		if (con.getResponseCode() == 200){
//			InputStream inputStr = con.getInputStream();
//			String info = new String(StreamTool.read(inputStr), "UTF-8");
//			Log.i(tag, info);
//			return true;
//		}
//		return false;
//	}
//	
	/**
	 * 新浪邮箱手机客户端
	 * {"result":true}
	 * @param mobile
	 * @param mapDataNew
	 * @return
	 * @throws Exception
	 */
	
	public static boolean sendMethodPOSTSina(String phone) throws Exception  {
		String path = "http://mail.sina.com.cn/client/mobile/sms.php";
		StringBuilder strBui = new StringBuilder();
		Map<String, String> mapData = new LinkedHashMap<String, String>();
		mapData.put("phone_number", phone);
		for(Map.Entry<String, String> mapEnt: mapData.entrySet()) {
			strBui.append(mapEnt.getKey() + "=" );
			strBui.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
//			Log.i(tag, mapEnt.getValue());
			strBui.append("&");
		}
		strBui.deleteCharAt(strBui.length()-1);
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", String.valueOf(strBui.length()));
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
		con.setDoOutput(true);
		OutputStream outStr = con.getOutputStream();
		outStr.write(strBui.toString().getBytes());
		
		if (con.getResponseCode() == 200){
			InputStream inputStr = con.getInputStream();
			String info = new String(StreamTool.read(inputStr), "UTF-8");
			Log.i(tag, info);
			return true;
		}
		return false;
	}
	
	
	
	
	
}
