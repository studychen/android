package com.studyday.seenews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoadingActivity extends Activity {
    private ProgressDialog dialog;
    private String url;
    private String choice; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
             url = getIntent().getStringExtra("clickurl");
             choice = getIntent().getStringExtra("which");
             if(choice.equals("detail")) {
            	 
             }
            //设置一个progressdialog的弹窗
            
            dialog = ProgressDialog.show(this, "加载中...", "请稍候");
            Thread thread = new Thread(new Runnable() {
                    public void run() {
                            try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}                                     //do...
                            Message message = new Message();
                            message.what = 0;
                            mHandler.sendMessage(message);
                    }
            });
            thread.start();

    }
    
    //处理跳转到主Activity
    private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    Log.d(">>>>>Mhandler", "开始handleMessage");
                    LoadingActivity.this.finish();
                    Intent mIntent = new Intent();
                    mIntent.setClass(LoadingActivity.this, NewsDetailActivity.class);
                    mIntent.putExtra("clickurl",url);
                    startActivity(mIntent);
                    
                    Log.d(">>>>>Mhandler", "LoadActivity关闭");
                    if (msg.what == 0) {
                        dialog.dismiss();
                }
            }
    };
}