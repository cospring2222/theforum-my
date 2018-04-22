package com.theforum.util;
/**
 * @author Uliana and David
 */
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//Date convertor util
public class DateUtils {
	public static Date parseDate(String sdate) {
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
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
	
	public static String dateToMonthYearOnlyString(Date date) {

	    String result ="";
		Format formatter = new SimpleDateFormat("MMM yyyy");
		result = formatter.format(date);

	    return result;
	}


}
