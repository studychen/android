package com.chenxb.mybean;

import java.io.Serializable;

/**
 * 鏋勯�鏂规硶
 * @param title 鏍囬
 * @param pubDate 鍙戣〃鏃ユ湡
 * @param readCount 娴忚鏁�
 * @param body 鍐呭
 */
public class News implements Serializable  {

	private String title;
	private String pubDate;
	private int readCount;
	private String body;
	public News(String title,  String pubDate, int readCount, String body) {
		this.title = title;
		this.pubDate = pubDate;
		this.readCount = readCount;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "News [title=" + title + ", pubDate=" + pubDate + ", readCount="
				+ readCount + ", body=" + body + "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
