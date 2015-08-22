package com.tomchen.smsbomb.common;

import android.util.Log;

public class LevelLogUtils {

	private static final int VERBOSE = 1;
	private static final int DEBUG = 2;
	private static final int INFO = 3;
	private static final int WARN = 4;
	private static final int ERROR = 5;
	private static final int NOLOG = 6;

	private static final int LEVEL = VERBOSE; // app log's level

	private static LevelLogUtils levelLog;

	private LevelLogUtils() {

	}

	public static LevelLogUtils getInstance() {
		if (levelLog == null) {
			synchronized (LevelLogUtils.class) {
				if (levelLog == null) {
					levelLog = new LevelLogUtils();
				}
			}
		}
		return levelLog;
	}

	public void v(String tag, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}

	public void d(String tag, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(tag, msg);
		}
	}

	public void i(String tag, String msg) {
		if (LEVEL <= INFO) {
			Log.i(tag, msg);
		}

	}

	public void w(String tag, String msg) {
		if (LEVEL <= WARN) {
			Log.w(tag, msg);
		}
	}

	public void e(String tag, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(tag, msg);
		}
	}
}
