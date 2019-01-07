package org.serji.sw.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static Date stringToDate(String s) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
		Date dt = null;
		try {
			dt = df.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dt;
	}

	public static String dateToString(Date dt) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
		return df.format(dt);
	}
}
