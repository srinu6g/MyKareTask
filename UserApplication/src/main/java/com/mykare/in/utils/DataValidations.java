package com.mykare.in.utils;


import org.springframework.stereotype.Component;

@Component
public class DataValidations {

	public static boolean isValidString(String str) {
		if (str == null) {
			return false;
		}

		String trimmedStr = str.trim();
		return !(trimmedStr.isEmpty() ||
				trimmedStr.equalsIgnoreCase("null") ||
				trimmedStr.equals("(null)") ||
				trimmedStr.equals("[NULL]") ||
				trimmedStr.equals("0"));
	}
}