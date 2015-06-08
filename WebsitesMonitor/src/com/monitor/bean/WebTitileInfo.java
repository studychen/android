package com.monitor.bean;

public class WebTitileInfo {
	
	private String nameZh;
	private boolean isDown;
	
	public WebTitileInfo(String nameZh, boolean isDown) {
		super();
		this.nameZh = nameZh;
		this.isDown = isDown;
	}
	
	@Override
	public String toString() {
		return "WebTitileInfo [nameZh=" + nameZh + ", isDown=" + isDown + "]";
	}
	
	

}
