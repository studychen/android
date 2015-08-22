package com.chenxb.commom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class StreamTool {

	public static byte[] read(InputStream inputStr) throws Exception{
		ByteArrayOutputStream outStr = new ByteArrayOutputStream();
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];
		int len = 0;
		while ( (len = inputStr.read(buffer)) != -1) {
			outStr.write(buffer, 0, len);
		}
		inputStr.close();
		return outStr.toByteArray();
	}
	
	 public static void printResponse(HttpResponse httpResponse)
		      throws ParseException, IOException {
		    // 获取响应消息实体
		    HttpEntity entity = httpResponse.getEntity();
		    // 响应状态
		    System.out.println("status:" + httpResponse.getStatusLine());
		    System.out.println("headers:");
		    HeaderIterator iterator = httpResponse.headerIterator();
		    while (iterator.hasNext()) {
		      System.out.println("\t" + iterator.next());
		    }
		    // 判断响应实体是否为空
		    if (entity != null) {
		      String responseString = EntityUtils.toString(entity);
		      System.out.println("response length:" + responseString.length());
		      System.out.println("response content:"
		          + responseString.replace("\r\n", ""));
		    }
		  }
	

}
