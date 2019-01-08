package org.serji.sw.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class of static helper methods
 * 
 * @author Justin
 *
 */
public abstract class Utils {

	/**
	 * Attempts to parse a string to a date object
	 * 
	 * @param s the String to be parsed
	 * @return the Date object created from supplied String
	 */
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

	/**
	 * Converts a Date object to a String
	 * 
	 * @param dt the Date to be converted to a String
	 * @return
	 */
	public static String dateToString(Date dt) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
		return df.format(dt);
	}
}
