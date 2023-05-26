package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	public static final String MM_DD_YYYY_DATE_PATTERN = "MM/dd/yyyy";

	public static final String YYYY_MM_DD_DATE_PATTERN = "yyyy-MM-dd";

	//public static final String DATE_FORMAT_WITHTIME = "MM/dd/yyyy kk:mm:ss a";

	//public static final String DATE_FORMAT_WITH_TIME = "MM-dd-yyyy hh:mm a";

	public static Timestamp strDateToTs(String dateStr) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY_DATE_PATTERN);
		LocalDateTime localDateTime = LocalDate.parse(dateStr, formatter).atStartOfDay();		
		return Timestamp.valueOf(localDateTime);

//		Timestamp tsDate = null;
//
//		if (dateStr != null && !dateStr.isBlank()) {
//			StringTokenizer dateTokens = new StringTokenizer(dateStr, "/");
//			String mm = dateTokens.nextToken();
//			if (mm.length() == 1) {
//				mm = "0" + mm;
//			}
//			String dd = dateTokens.nextToken();
//			if (dd.length() == 1) {
//				dd = "0" + dd;
//			}
//			String yyyy = dateTokens.nextToken();
//			tsDate = Timestamp.valueOf(yyyy + "-" + mm + "-" + dd + " 00:00:00.000000000");
//		}
//
//		return tsDate;
	}

	public static String tsDateToStr(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY_DATE_PATTERN);

		return formatter.format(dateTS.toLocalDateTime());

//		String dateStr = dateTS.toString();
//
//		dateStr = dateStr.substring(0, 10);
//
//		StringTokenizer dateTokens = new StringTokenizer(dateStr, "-");
//		String year = dateTokens.nextToken();
//		String month = dateTokens.nextToken();
//		String day = dateTokens.nextToken();
//		dateStr = month + "/" + day + "/" + year;
//
//		return dateStr;
	}

	/*
	 * 
	 * @author Pankaj Thakur MethodName -strDateHyphenToTs Method param - String
	 * date Convert string into timestamp
	 */
	public static Timestamp strDateHyphenToTs(String dateStr) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_PATTERN);
		LocalDateTime localDateTime = LocalDate.parse(dateStr, formatter).atStartOfDay();
		return Timestamp.valueOf(localDateTime);

//		Timestamp tsDate = null;
//
//		try {
//			if (!dateStr.isBlank()) {
//				StringTokenizer dateTokens = new StringTokenizer(dateStr, "-");
//				String yyyy = dateTokens.nextToken();
//				String mm = dateTokens.nextToken();
//				if (mm.length() == 1) {
//					mm = "0" + mm;
//				}
//				String ddTemp = dateTokens.nextToken();
//				String[] ddStr = ddTemp.split(" ");
//				String dd = ddStr[0];
//				if (dd.length() == 1) {
//					dd = "0" + dd;
//				}
//				tsDate = Timestamp.valueOf(yyyy + "-" + mm + "-" + dd + " 00:00:00.000000000");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// return tsDate;
	}

}
