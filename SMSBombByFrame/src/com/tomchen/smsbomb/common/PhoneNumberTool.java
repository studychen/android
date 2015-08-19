package com.tomchen.smsbomb.common;

public class PhoneNumberTool {
	public static boolean isPhoneValid(String phone) {
		if (phone.length() != 11) {
			return false;
		}
		return true;
	}

}
