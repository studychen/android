package com.example.StreamTool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.util.Log;

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
	

}
