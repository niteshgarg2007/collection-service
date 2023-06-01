package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	private DateUtil() {}

	private static final String MM_DD_YYYY_DATE_PATTERN = "MM/dd/yyyy";

	private static final String YYYY_MM_DD_DATE_PATTERN = "yyyy-MM-dd";
	
	private static final DateTimeFormatter DATE_FORMATTER_yyyy_MM_dd = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_PATTERN);
	private static final DateTimeFormatter DATE_FORMATTER_MM_dd_yyyy = DateTimeFormatter.ofPattern(MM_DD_YYYY_DATE_PATTERN);

	public static Timestamp strDateToTs(String dateStr) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY_DATE_PATTERN);
		LocalDateTime localDateTime = LocalDate.parse(dateStr, formatter).atStartOfDay();		
		return Timestamp.valueOf(localDateTime);
	}

	public static String tsDateToStr(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY_DATE_PATTERN);

		return formatter.format(dateTS.toLocalDateTime());
	}

	public static Timestamp strDateHyphenToTs(String dateStr) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_DATE_PATTERN);
		LocalDateTime localDateTime = LocalDate.parse(dateStr, formatter).atStartOfDay();
		return Timestamp.valueOf(localDateTime);
	}
	
	public static String convertDateToString(Date date) {
		return convertDateToString(date, null);
	}

	public static String convertDateToString(Date date, DateTimeFormatter format) {
		if (date == null)
			return null;
		LocalDate ldt = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		if (format == null) {
			return ldt.format(DATE_FORMATTER_yyyy_MM_dd);
		} else {
			return ldt.format(format);
		}
	}

	public static String getCurrentDateString() {
		return LocalDate.now().format(DATE_FORMATTER_MM_dd_yyyy);
	}
	
	public static Timestamp parseDateTime(String value) {
        return parseDateTime(value,null);
	}
	
	public static Timestamp parseDateTime(String value, DateTimeFormatter format) {
        if (value == null)
               return null;
        DateTimeFormatter formatter = null;
        if (format == null)
               formatter = DATE_FORMATTER_MM_dd_yyyy;
        else
               formatter = format;
        LocalDateTime localDateTime = LocalDate.parse(value, formatter).atStartOfDay();
        return Timestamp.valueOf(localDateTime);
	}
}
