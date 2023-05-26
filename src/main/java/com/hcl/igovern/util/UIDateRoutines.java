package com.hcl.igovern.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UIDateRoutines {

	public static final DateTimeFormatter DATE_FORMATTER_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DATE_FORMATTER_MM_dd_yyyy_HH_MM_SS = DateTimeFormatter
			.ofPattern("MM/dd/yyyy HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMATTER_MM_dd_yyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");



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

}
