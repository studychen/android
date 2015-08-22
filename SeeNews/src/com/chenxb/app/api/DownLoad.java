package com.chenxb.app.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author admin
 *
 */
public class DownLoad {
	private static final int block = 50000;
	
	public static void main(String[] args) throws IOException {
		String path = "http://see.xidian.edu.cn/uploads/file/20141121/20141121105605_40086.rar";
		URL url = new URL(path);
		HttpURLConnection  con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(5000);
		con.setRequestMethod("GET");
		int length = con.getContentLength();
		File file = new File(getName(path));
		RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
		//在本地生成一个相同的文件
		accessFile.setLength(length);
		int num = length%block == 0 ? length/block : length/block+1;
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < num; i++) 
			exec.execute(new DownThread(path, i, file, accessFile));
		 exec.shutdown();
		 accessFile.close();
	}
	
	private static class DownThread implements Runnable{
		private String path;
		private int id;
		private File file;
		private RandomAccessFile accessFile;
		

		public DownThread(String path, int id, File file, RandomAccessFile accessFile) {
			this.path = path;
			this.id = id;
			this.file = file;
			this.accessFile = accessFile;
		}


		@Override
		public  void run() {
			int start = block*id;
			int end = block*(id+1)-1;
			try {
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.seek(start);
				URL url = new URL(path);
				HttpURLConnection  con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5000);
				con.setRequestMethod("GET");
				con.setRequestProperty("Range", "bytes=" + start+"-"+end);
				byte[] buffer = new byte[1024];
				InputStream input = con.getInputStream();
				int length;
				while((length = input.read(buffer)) !=-1) 
					accessFile.write(buffer, 0, length);
				accessFile.close();
				input.close();
				System.out.println(id+1 + "线程下载");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static String getName(String path) {
		return path.substring(path.lastIndexOf("/")+1);
	}


}


