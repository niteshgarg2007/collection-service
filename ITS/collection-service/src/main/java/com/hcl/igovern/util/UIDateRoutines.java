package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class UIDateRoutines {
	
	public static final DateTimeFormatter DATE_FORMATTER_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DATE_FORMATTER_MM_dd_yyyy_HH_MM_SS = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMATTER_MM_dd_yyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static Timestamp currentDate = null;
	
	private UIDateRoutines() {}

	public static Timestamp getCurrentDate() {
		return currentDate;
	}

	public static void setCurrentDate(Timestamp sysDate) {
		UIDateRoutines.currentDate = sysDate;
	}

	public static Timestamp getSysdate() {
		return new Timestamp(new Date().getTime());
	}

	 public static Date parseDate(String value , DateTimeFormatter format) {		  
	 Date d =null;
	LocalDate date = LocalDate.parse(value, format);
    d = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return d;			    	       
	 }
	 
	 public static String getDateString(Date date,  DateTimeFormatter format ) {
 		LocalDate ldt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
 		if(format ==null) {
 			return ldt.format(DATE_FORMATTER_yyyy_MM_dd);
 		}else {
 			return ldt.format(format);
 		}
 	}
	 
	 
}
