package com.chenxb.coursebean;

public class ImageInfo {

	public String imageMsg;		//菜单标题
	public int imageId;			//logo图片资源
	public int bgId;			//背景图片资源

	public ImageInfo(String msg, int id1,int id2) {
		imageId = id1;
		imageMsg = msg;
		bgId = id2;
	}
}
