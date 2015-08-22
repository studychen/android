package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
public class FileService {
	private Context context;
	/*
	 * �����ļ�
	 * @param filename    �ļ����
	 * @param filecontent �ļ�����
	 */
	public FileService(Context context) {
		this.context = context;
	}
	
	public void saveToSd(String filename,String filecontent) throws Exception {
		
		File file = new File(Environment.getExternalStorageDirectory().toString(), filename);

		FileOutputStream outStream = new FileOutputStream(file);
		write(filecontent, outStream);
	}
	

	public void save(String filename,String filecontent) throws Exception {
		//˽�в���ģʽ�������������ļ�ֻ�ܱ���Ӧ�÷��ʣ�����Ӧ���޷�����
		// ���⣬����˽�в����������ļ�д���ļ��е����ݻḲ��ԭ�ļ�����
		
		//�ļ���Ʋ�Ҫ��·����file.txt ./ds/files/
		FileOutputStream fileoutStream = context.openFileOutput(
				filename, Context.MODE_PRIVATE);
		write(filecontent, fileoutStream);
	}

	private void write(String filecontent, FileOutputStream fileoutStream)
			throws IOException {
		fileoutStream.write(filecontent.getBytes());
		fileoutStream.close();
	}
	
	public void saveAppend(String filename,String filecontent) throws Exception {
		//˽�в���ģʽ�������������ļ�ֻ�ܱ���Ӧ�÷��ʣ�����Ӧ���޷�����
		// ���⣬����˽�в����������ļ�д���ļ��е����ݻḲ��ԭ�ļ�����
		
		//�ļ���Ʋ�Ҫ��·����file.txt ./ds/files/
		FileOutputStream fileoutStream = context.openFileOutput(filename, 
				Context.MODE_WORLD_WRITEABLE+Context.MODE_WORLD_READABLE+
				Context.MODE_APPEND);
		write(filecontent, fileoutStream);
	}
	
	
	

	/**
	 * ��ȡ�ļ�����
	 * @param filename �ļ����
	 * @return �ļ�����
	 * @throws Exception
	 */
	public String read(String filename) throws Exception {
		FileInputStream fileinputStream = context.openFileInput(filename);
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream filecontentStr = new ByteArrayOutputStream();
		int len = 0;
		//whileѭ�������len=**��= ǰ���������
		while ((len=fileinputStream.read(buffer)) != -1) {
			filecontentStr.write(buffer, 0, len);
		}
		byte[] data = filecontentStr.toByteArray();
		fileinputStream.close();
		filecontentStr.close();
		String resultStr = new String(data);
		return resultStr;
	}

}
