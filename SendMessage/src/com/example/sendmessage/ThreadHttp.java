package com.example.sendmessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Message;
import android.util.Log;

public class ThreadHttp extends Thread {
	private final String tag = "ThreadHttp";
    public ThreadHttp(ThreadGroup tg, String name) {
            super(tg, name);
    }

//    public void run() {
//        String getWebSite = Thread.currentThread().getName();
//        String[] getValue = getWebSite.split(">>");
//        Log.i(tag, "The " + getValue[0] + " thread is" + " starting.");               
//
//        HttpClient httpClient = new DefaultHttpClient();
//        if (getValue[1].equals("get")) {
//                HttpGet httpGet = new HttpGet(getValue[0]);
//                httpGet.addHeader("Content-Type", "text/html;charset=gbk");
//                httpGet.addHeader(
//                                "User-Agent",
//                                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
//                Log.i(tag, "executing request " + httpGet.getURI());
//                try {
//                        HttpResponse httpResponse = httpClient.execute(httpGet);
//                        Log.i(tag, httpResponse.getStatusLine().toString());
//                        Message message = new Message();
//                        message.obj = getValue[0];
//                        message.what = 0;
//                        handler.sendMessage(message);
//                } catch (ClientProtocolException e) {
//                        e.printStackTrace();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                }
//        } else if (getValue[1].equals("post")) {
//                String key = getValue[0];
//                try {
//                        HttpClient httpClientpost = new DefaultHttpClient();
//                        HttpPost httpPost = new HttpPost(key);
//                        StringEntity requestEntity = new StringEntity(getValue[2]);
//                        httpPost.addHeader("Content-Type",
//                                        "application/x-www-form-urlencoded");
//                        httpPost.addHeader("DNT", "1");
//                        httpPost.addHeader("Referer", getValue[3]);
//                        httpPost.addHeader("User-Agent", "IE10");
//                        httpPost.setEntity(requestEntity);
//                        try {
//                                HttpResponse response = httpClientpost
//                                                .execute(httpPost);
//                               Log.i(tag, response.getStatusLine().toString());
////                                        System.out.println(response.getStatusLine());
//                                HttpEntity getentity = response.getEntity();
//                                InputStream inputStream = getentity.getContent();
//                                InputStreamReader reader = new InputStreamReader(
//                                                inputStream, "UTF-8");
//                                BufferedReader bfReader = new BufferedReader(reader);
//                                String line = null;
//                                while ((line = bfReader.readLine()) != null) {
//                                        System.out.println(line);
//                                }
//                                inputStream.close();
//                                reader.close();
//                                bfReader.close();
//                                message = new Message();
//                                message.obj = key;
//                                message.what = 0;
//                                handler.sendMessage(message);
//                        } catch (ClientProtocolException e) {
//                                e.printStackTrace();
//                        } catch (IOException e) {
//                                e.printStackTrace();
//                        }
//                } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                }
//        }
//
//    }
}
