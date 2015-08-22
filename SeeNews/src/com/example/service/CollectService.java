package com.example.service;

import java.util.ArrayList;
import java.util.List;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class CollectService {
	private MydbOpenHelper dbopenHelper;
	
	
	public CollectService(MydbOpenHelper dbopenHelper) {
		this.dbopenHelper = dbopenHelper;
	}
//	CREATE TABLE collect(collectid integer primary key autoincrement, 
//			colindex integer default (0), 
//			colgroup integer default (0));
	
	
	//保存修改的收藏 num是新闻字段
	//新增收藏字段
	public void setCollect(int num) {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		int index = getIndex(num);
		int flag = setFlag(index, num, true);
		int id = getId(index);
		
		//不存在，执行插入操作
		if(id == -1) {
			db.execSQL("insert into collect(colindex, colgroup) values(?,?)", 
					new Object[] {index, flag});
		} else {
			db.execSQL("update collect set colindex = ?, colgroup = ? where collectid = ?", 
					new Object[] {index, flag, id});
		}
	}
	
	/**
	 * 取消收藏
	 * @param num
	 */
	public void cancelCollect(int num) {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		int index = getIndex(num);
		int flag = setFlag(index, num, false);
		int id = getId(index);
		
		if(id != -1) {
			db.execSQL("update collect set colindex = ?, colgroup = ? where collectid = ?", 
					new Object[] {index, flag, id});
		}
	}
	
	public boolean isCollect(int num) {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		int index = getIndex(num);
		int originalFlag = find(index);
		int id = getId(index);
		if(id == -1) {
			return false;
		}else {
			if((originalFlag  & (1 << num-1)) != 0)
				return true;
			else 
				return false;
		}
	}
	
	public List<Integer> getAllCollect() {
		Integer offset = 0;
		Integer maxResult = (int) count();
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from collect order by collectid asc limit ?,?", 
				new String[]{offset.toString(), maxResult.toString()});
		List<Integer> collects = new ArrayList<Integer> ();
		while( cursor.moveToNext()) {
			int index = cursor.getInt(cursor.getColumnIndex("colindex"));
			int flag = cursor.getInt(cursor.getColumnIndex("colgroup"));
			collects.addAll(getCollectEvery(index, flag));
		}
		cursor.close();
		return collects;
	}
	
	private int getId(Integer index) {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from collect where colindex = ?",
				new String[]{index.toString()});
		if(cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("collectid"));
			return id;
		}
		cursor.close();
		return -1;
	}
	
	private int getIndex(int num) {
		int location = num >> 5;
		if(num%32 == 0)  --location;
		// TODO Auto-generated method stub
		return location;
	}
	
	/**
	 * 
	 * @param index
	 * @param num
	 * @param isAdd 设置是设为收藏。还是取消收藏
	 * @return
	 */
	private int setFlag(int index, int num, boolean isAdd) {
		int original = find(index); // 没有就是0
		if(isAdd) {
			return (original | (1 << num-1)) ;
		}
		else {
			return (original ^ (1 << num-1)) ;
		}
	}

	
	//找出index 0，1，2，3第几块的原来的值，做更新用
	private int find(Integer index) {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from collect where colindex = ?", new String[]{index.toString()});
		if(cursor.moveToNext()) {
			int flag = cursor.getInt(cursor.getColumnIndex("colgroup"));
			return flag;
		}
		cursor.close();
		return 0;
	}
	
	
	
	/**
	 * 
	 * @param i 第几组收藏的数字
	 * @param flag 
	 * @return
	 */
	private List<Integer>  getCollectEvery(int index, int flag) {
		List<Integer> flagList = new ArrayList<Integer>();
		for(int i = 1 ; i <= 32; i ++) {
			if((flag &  1 << (i-1)) != 0)
				flagList.add((index << 5) + i);
		}
		return flagList;
	}
	
	private long count() {
		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from collect ", null);
		cursor.moveToLast();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	
//	/**
//	 * 也是调用update方法
//	 * @param person
//	 */
//	public void delete(Integer id) {
//		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
//		db.execSQL("delete from person where personid = ?", new Object[]{id});
//	}
//	
	/**
	 * 
	 * @param person
	 */
//	public void update(Person person) {
//		SQLiteDatabase db = dbopenHelper.getWritableDatabase();
//		db.execSQL("update person set name = ?, phone = ?, amount = ? where personid = ?", 
//				new Object[]{person.getName(), person.getPhone(), person.getAmount(), person.getId()});
//	}

}
