package com.example.thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.R.integer;

public class CreateThread {
	
	public static int randomThread(String telnumber, int num) {
		int success = 0;
		Random RAND = new Random();
		int data = RAND.nextInt(6)+1; //共有6个线程
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		for(int i = 0; i < num; i++)
			results.add(exec.submit(selectThread(telnumber)));
		for (Future<String> fs:results)
			try {
				if (fs.get().equals("200"))
					success++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return success;
	}
	
	public static Callable<String> selectThread(String telnumber) {
		Random RAND = new Random();
		int data = RAND.nextInt(6)+1; //共有6个线程
		switch (data) {
		case 1:
			return  new MyThread1("sendMethodGETDianping", telnumber);
		case 2:
			return new MyThread2("sendMethodGETSmsspub", telnumber);
		case 3:
			return new MyThread3("sendMethodPOSTDianping", telnumber);
		case 4:
			return new MyThread4("sendMethodPOSTSendMO", telnumber);
		case 5:
			return new MyThread5("sendMethodPOSTSendAcode", telnumber);
		case 6:
			return new MyThread6("sendMethodPOSTSina", telnumber);
		default:
			return new MyThread6("sendMethodPOSTSina", telnumber);
		}
	}

}
