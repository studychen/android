package com.monitor.bean;

public class WebInfo {

	private String name;
	private String nameZh;
	private String time;
	private String code;
	private boolean isDown;
	private String intervalTime;
	
	public WebInfo( String name, String nameZh, String time,
			  String code, boolean isDown, String intervalTime) {
			this.name = name;
			this.nameZh = nameZh;
			this.time = time;
			this.code = code;
			this.isDown = isDown;
			this.intervalTime = intervalTime;
		}
	@Override
	public String toString() {
		return "WebInfo [name=" + name + ", nameZh=" + nameZh
				+ ", time=" + time + ", code=" + code
				+ ", isDown=" + isDown + ", intervalTime=" + intervalTime + "]";
	}
}
