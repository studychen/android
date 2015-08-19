package com.tomchen.smsbomb.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {
	private static final String tag = "bomb";

	// check all network connect, WIFI or mobile
	public static boolean isNetworkAvailable(final Context context) {
		boolean hasWifoCon = false;
		boolean hasMobileCon = false;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfos = cm.getAllNetworkInfo();
		for (NetworkInfo net : netInfos) {

			String type = net.getTypeName();
			if (type.equalsIgnoreCase("WIFI")) {
				LevelLogUtils.getInstance().i(tag, "get Wifi connection");
				if (net.isConnected()) {
					hasWifoCon = true;
				}
			}

			if (type.equalsIgnoreCase("MOBILE")) {
				LevelLogUtils.getInstance().i(tag, "get Mobile connection");
				if (net.isConnected()) {
					hasMobileCon = true;
				}
			}
		}
		return hasWifoCon || hasMobileCon;

	}

	// network available cannot ensure Internet is available
	public static boolean isInternetAvailable(final Context context) {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process pingProcess = runtime.exec("/system/bin/ping -c 1 www.baidu.com");
			int exitCode = pingProcess.waitFor();
			LevelLogUtils.getInstance().i(tag, "ping -c 1 www.baidu.com");
			return (exitCode == 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
