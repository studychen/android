package com.chenxb.mybean;

import java.io.Serializable;

/**
 * 实体基类：实现序列化
 * @version 1.0
 * @created 2012-3-21
 */
public abstract class Base implements Serializable {

	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "xidiandianyuan";
	
	protected News news;

	public News getNotice() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}
