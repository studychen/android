package com.example.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MydbOpenHelper extends SQLiteOpenHelper{

	public MydbOpenHelper(Context context) {
		//factory null使用默认的游标工厂
		//version 1 是数据库版本号
		super(context, "seenewscollect.db", null, 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//数据库自动创建
		// TODO Auto-generated method stub
		db.execSQL("create table collect(collectid integer primary " +
				"key autoincrement, colindex integer default (0), " +
				"colgroup integer default (0))");

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("create table collect(collectid integer primary " +
				"key autoincrement, colindex integer, " +
				"colgroup integer default (0))");
		// TODO Auto-generated method stub
		
	}

}
