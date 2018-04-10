package com.theforum.util;
/**
 * @author Uliana and David
 */
import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//Date convertor util
public class DateUtils {
	public static Date parseDate(String sdate) {
		    DateFormat df = new SimpleDateFormat("yy-mm-dd", Locale.ENGLISH);
		    Date result;
			try {
				result = df.parse(sdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}  
		    return result;
	}
}
