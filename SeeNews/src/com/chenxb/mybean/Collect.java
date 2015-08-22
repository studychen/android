package com.chenxb.mybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Collect implements Serializable {

	/**
	 * 后来用数据库实现
	 */
	private static final long serialVersionUID = 1L;
	
	//只有一个
	private static int[] flagList = new int[400];
	
	
	public static int[] getFlagArray() {
		return flagList;
	}


	
	/**
	 * 将某一位赋值为1 根据
	 * 1--32，第1位到第32位
	 * 1 表示移动0位，32移动31位
	 * 33--64，第1位到第32位
	 * @param num表示新闻列表
	 * 这儿会改变flagList数组的值
	 */
	public static void  setCollect( int num) {
		int location = num >> 5;
		if(num%32 == 0)  --location;
		flagList[location] = flagList[location] | (1 << num-1);
	}
	
	/**
	 * 取消收藏
	 * @param flagList
	 * @param num
	 */
	public static void  setNotCollect(int num) {
		int location = num >> 5;
		if(num%32 == 0)  --location;
		flagList[location] = flagList[location] ^ (1 << num-1);
	}
	
	/**是否收藏
	 * flag移位       10100000000000000000000000000001
	 * 7568本来移位00100000000000000000000000000000
	 * 两者相与
	 * @param flag
	 * @param num
	 * @return
	 */
	public static boolean  isCollected(int num) {
		int location = num >> 5;
		if(num%32 == 0)  --location;
		if((flagList[location]  & (1 << num-1)) != 0)
			return true;
		else 
			return false;
	}
	
	/**
	 * 还原所有收藏的数字2，14，57等等
	 * @param flagList
	 * @return
	 */
	public static List<Integer> getCollect() {
		List<Integer> collectedList = new ArrayList<Integer>();
		for(int i =0 ;i < flagList.length; i ++ ) {
			if(flagList[i] != 0) {
				collectedList.addAll(getCollectNum(i,flagList[i]));
			}
		}
		return collectedList;
	}
	
	/**
	 * 
	 * @param i 第几组收藏的数字
	 * @param flag
	 * @return
	 */
	public static List<Integer>  getCollectNum(int index, int flag) {
		List<Integer> flagList = new ArrayList<Integer>();
		for(int i = 1 ; i <= 32; i ++) {
			if((flag &  1 << (i-1)) != 0)
				flagList.add((index << 5) + i);
		}
//		String tempString =new StringBuilder(Integer.toBinaryString(flag)).
//				reverse().toString();  
//		int start = tempString.indexOf("1");
//		flagList.add(start+1);
//		int end = tempString.lastIndexOf("1");
//		for(int i = start; start < end; ) {
//			start = tempString.indexOf("1", start);
//			if(start < end)
//				flagList.add(start+1);
//		}
		return flagList;
	}
//	
//	 public static byte[] intToByteArray(int i) {   
//         byte[] result = new byte[4];   
//         //由高位到低位
//         result[0] = (byte)((i >> 24) & 0xFF);
//         result[1] = (byte)((i >> 16) & 0xFF);
//         result[2] = (byte)((i >> 8) & 0xFF); 
//         result[3] = (byte)(i & 0xFF);
//         return result;
//       }
//
//       /**
//        * byte[]转int
//        * @param bytes
//        * @return
//        */
//       public static int byteArrayToInt(byte[] bytes) {
//              int value= 0;
//              //由高位到低位
//              for (int i = 0; i < 4; i++) {
//                  int shift= (4 - 1 - i) * 8;
//                  value +=(bytes[i] & 0x000000FF) << shift;//往高位游
//              }
//              return value;
//        }
	

}
