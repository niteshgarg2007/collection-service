package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	private static final String TIME_COLON = ":";

	public static final String MM_DD_YYYY_DATE_PATTERN = "MM/dd/yyyy";

	public static final String DD_MMM_YYYY_DATE_PATTERN = "dd MMM, yyyy";

	public static final String MM_DD_YY_DATE_PATTERN = "MM/dd/yy";

	public static final String MMDDYYYY_DATE_PATTERN = "MMddyyyy";

	public static final String YYYYMMDD_DATE_PATTERN = "yyyyMMdd";

	public static final String HYPHEN_MMDDYYYY_DATE_PATTERN = "MM-dd-yyyy";

	public static final String HYPHEN_DDMMMYYYY_DATE_PATTERN = "dd-MMM-yyyy";

	public static final String QUARTER_DATE_PATTERN = "MM/yy";

	public static final String TIME_PATTERN = "hh:mm a";

	public static final String TIME_PATTERN1 = "h:mm a";

	public static final String HOUR_MINUTE_PATTERN = "HH:mm";

	public static final String MM_DD_YY_WITHTIME_24 = "MM-dd-yy kk:mm";

	public static final String DATE_FORMAT1 = "mm/dd/yyyy";

	public static final String DATE_FORMAT_WITHTIME = "MM/dd/yyyy kk:mm:ss a";

	public static final String DATE_FORMAT_WITH_TIME = "MM-dd-yyyy hh:mm a";

	public static final String DATE_FORMAT_WITH_TIME1 = "MMddyyyy hh:mm a";

	private static final String DATE_SEPARATOR = "/";
	
	public static final String DATE_FORMAT_MMMMDDYYYY = "MMMM dd, YYYY ";

	public static final String DATE_REGEX = "(0[1-9]|1[012])\\/\\d\\d";

	public static final SimpleDateFormat SIMPLE_MM_DD_YYYY_DATE_FORMAT = new SimpleDateFormat(
			MM_DD_YYYY_DATE_PATTERN);

	public static final SimpleDateFormat SIMPLE_MM_DD_YY_DATE_FORMAT = new SimpleDateFormat(
			MM_DD_YY_DATE_PATTERN);

	public static final SimpleDateFormat SIMPLE_MMDDYYYY_DATE_FORMAT = new SimpleDateFormat(
			MMDDYYYY_DATE_PATTERN);
   //Changes for Product Optimizations	
	public static final String YYYY_MM_DDHH_MM_S_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.S";

	private static final String DATE_ERROR_MESSAGE = "Either Start Date or End Date is Null";

	public static final int MIN_YEAR = 1900;

	public static final int MAX_DAY = 30;

	public static final int PERCENTAGE = 100;

	private int intMonth = 0;

	private int intYear = 0;

	private int intDate = 0;

	private int diffrence = 0;

	protected int month = 0;

	protected int date = 0;

	protected int year = 0;

	private GregorianCalendar datenow;

	private static final Date CURRENT_DATE = new Date();// TODO need to be

	// removed once all the
	// references in
	// Benefits/Appeals/IWF
	// were corrected

	// zz add
	private static final String MMDDYYYYHHMMSS_DATE_PATTERN = "MMddyyyyHHmmSS";
	private static final String YYMMDD_DATE_PATTERN = "yyMMdd";
	
	private static final SimpleDateFormat SIMPLE_MMDDYYYYHHMMSS_DATE_FORMAT = new SimpleDateFormat(
			MMDDYYYYHHMMSS_DATE_PATTERN);
	
	private static final SimpleDateFormat SIMPLE_YYMMDD_DATE_FORMAT = new SimpleDateFormat(
			YYMMDD_DATE_PATTERN);
	
	public static final String DATE_AND_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	public static final String DATE_FORMAT = "MM/dd/yyyy";

	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String DATE_YYYY_PATTERN = "yyyy";

	public static final String YYYY_MM_DD_DATE_PATTERN = "yyyy-MM-dd";

	public static final String YYYYMMDDHHMMSSSS_DATE_PATTERN = "yyyyMMddHHmmSSss";
	
	private static final String YYYY_MM_DD_DATE_TIME_PATTERN = "MM-dd-yyyy HH:mm:ss";

	public static final SimpleDateFormat SIMPLE_YYYYMMDDHHMMSSSS_DATE_FORMAT = new SimpleDateFormat(
			YYYYMMDDHHMMSSSS_DATE_PATTERN);

	public static final String YYYY_MM_DD_HH_MM_SS_DATE_PATTERN = "yyyy-MM-dd_HH_mm_ss";
	
	public static final String HYPHEN_DD_MM_YYYY_DATE_PATTERN = "dd-mm-yyyy";
	// FIXME Need to check by jilani
	public static final String DATE_AND_TIME_FORMAT_24hrs = "MM/dd/yyyy hh:mm:ss a";
	
	public static final String DATE_AND_TIME_DB_24hrs_PATTERN = "yyyy-MM-dd hh:mm:ss a";
	
	private static final String MMDDYYHHMMSS_DATE_PATTERN = "MMddyyHHmmSS";

	private static final SimpleDateFormat SIMPLE_MMDDYYHHMMSS_DATE_FORMAT = new SimpleDateFormat(
			MMDDYYHHMMSS_DATE_PATTERN);
	
	public static final SimpleDateFormat DATE_AND_TIME_DB_24hrs_FORMAT = new SimpleDateFormat(
			DATE_AND_TIME_DB_24hrs_PATTERN);

	/* EXTN 1795 */
	private static Date currentDate = null;
	
	private static int startYear = 1980;

	/**
	 * Get converted <code>Date</code> value. The pattern is used with
	 * java.text.SimpleDateFormat. If the datePattern is null or empty, then use
	 * MM_DD_YYYY_DATE_PATTERN as default datePattern
	 * 
	 * @param date
	 *            The value is being converted.
	 * @param datePattern
	 *            The pattern passed to SimpleDateFormat.
	 * @return the converted Date value.
	 */
	public int compareDate(GregorianCalendar objDate) // difference betn date
	{
		//EXTN 1795
		/*intDate = Calendar.DAY_OF_MONTH;
		intMonth = Calendar.MONTH;
		intYear = Calendar.YEAR;
		datenow = new GregorianCalendar(intYear, intMonth, intDate);*/
		datenow = new GregorianCalendar();
		datenow.setTimeInMillis(getCurrentDate().getTime());

		GregorianCalendar datethen1 = objDate;
		intMonth = datethen1.get(Calendar.MONTH);
		intDate = datethen1.get(Calendar.DATE);
		intYear = datethen1.get(Calendar.YEAR);

		GregorianCalendar orgfinishingdate = new GregorianCalendar(intYear,
				intMonth, intDate);

		if (orgfinishingdate.equals(datenow)) {
			diffrence = 0;
		}

		if (orgfinishingdate.before(datenow)) {
			diffrence = -1;
		}

		if (orgfinishingdate.after(datenow)) {
			diffrence = 1;
		}

		return diffrence;
	}


	/**
	 * Get converted <code>Date</code> value to <code>String</code>. If the
	 * format is null or empty, then use SIMPLE_MM_DD_YYYY_DATE_FORMAT as
	 * default format.
	 * 
	 * @param date
	 *            The value is being converted.
	 * @param format
	 *            The format used to format <code>Date</code>.
	 * @return the converted String value.
	 */
	public static String getStringDate(Date date, DateFormat format) {
		if (date == null) {
			return null;
		}

		if (format == null) {
			format = SIMPLE_MM_DD_YYYY_DATE_FORMAT;
		}

			return format.format(date);
	}



	public static String formatSimpleDateWithTime(Date inputDate) {
		String outputFormatString = DATE_FORMAT_WITHTIME;
		String strReturnDate = "";

		try {
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strReturnDate = inputFormat.format(inputDate);
		} catch (Exception ex) {
		}

		return strReturnDate;
	}

	///************************function for comparing util
	// dates****************
	public static int compare_date(Date date) {
		int i = 0;
		Calendar currentdate = Calendar.getInstance();

		Calendar userdate = (Calendar) currentdate.clone();

		// currentdate.setTime(new Date()); EXTN 1795

		//EXTN 1795 
		currentdate.setTimeInMillis(getCurrentDate().getTime());
		
		int currentDate = currentdate.get(Calendar.DAY_OF_MONTH);
		int currentMonth = currentdate.get(Calendar.MONTH) - 1;
		int currentYear = currentdate.get(Calendar.YEAR) + 1900;

		userdate.setTime(date);
		int userDate = userdate.get(Calendar.DAY_OF_MONTH);
		int userMonth = userdate.get(Calendar.MONTH) - 1;
		int userYear = userdate.get(Calendar.YEAR) + 1900;

		if (userYear == currentYear) {
			if (userMonth > currentMonth) {
				i = 1;
			}

			if (userMonth < currentMonth) {
				i = -1;
			}

			if (userMonth == currentMonth) {
				if (userDate == currentDate) {
					i = 0;
				}

				if (userDate > currentDate) {
					i = 1;
				}

				if (userDate < currentDate) {
					i = -1;
				}
			}
		}

		if (userYear > currentYear) {
			i = 1;
		}

		if (userYear < currentYear) {
			i = -1;
		}

		return i;
	}

	/*
	 * This function will take date as a string parameter in mm/dd/yyyy format
	 * and will return date object.
	 */
	public static Date getDateObject(String date) {
		if(date!=null && !date.equalsIgnoreCase("")) {
			int dateMonth = Integer.parseInt(date.substring(0, 2));
			int dateDate = Integer.parseInt(date.substring(3, 5));
			int dateYear = Integer.parseInt(date.substring(6, 10));
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, dateYear);
			c.set(Calendar.MONTH, dateMonth - 1);
			c.set(Calendar.DATE, dateDate);
			return c.getTime();
		}
		return null;
	}

	public static Date getDateTimeObject(String date) {
		int dateMonth = Integer.parseInt(date.substring(0, 2));
		int dateDate = Integer.parseInt(date.substring(3, 5));
		int dateYear = Integer.parseInt(date.substring(6, 10));
		int dateHour = Integer.parseInt(date.substring(11, 13));
		int dateMinute = Integer.parseInt(date.substring(14, 16));
		Calendar c = new GregorianCalendar();
		c.set(1, dateYear);
		c.set(2, dateMonth - 1);
		c.set(5, dateDate);
		c.set(c.HOUR, dateHour);
		if (dateHour == 12 || dateHour == 0)
			c.set(c.HOUR, dateHour - 12);
		c.set(c.MINUTE, dateMinute);
		return c.getTime();
	}
	public static Date getDateTimeObjectHourOfDay(String date) {
		int dateMonth = Integer.parseInt(date.substring(0, 2));
		int dateDate = Integer.parseInt(date.substring(3, 5));
		int dateYear = Integer.parseInt(date.substring(6, 10));
		int dateHour = Integer.parseInt(date.substring(11, 13));
		int dateMinute = Integer.parseInt(date.substring(14, 16));
		Calendar c = new GregorianCalendar();
		c.set(1, dateYear);
		c.set(2, dateMonth - 1);
		c.set(5, dateDate);
		c.set(Calendar.HOUR_OF_DAY, dateHour);		
		c.set(Calendar.MINUTE, dateMinute);
		return c.getTime();
	}

	public static String getCEDateFormat(String date) {
		String dateYear = date.substring(6, 10);
		String dateDate = date.substring(3, 5);
		String dateMonth = date.substring(0, 2);
		date = dateYear + dateMonth + dateDate;

		return date;
	}

	public static Timestamp strDateToTs(String dateStr) {
		Timestamp tsDate = null;

		try {
			if (!dateStr.isBlank()) {
				StringTokenizer dateTokens = new StringTokenizer(dateStr, "/");
				String mm = dateTokens.nextToken();
				if (mm.length() == 1) {
					mm = "0" + mm;
				}
				String dd = dateTokens.nextToken();
				if (dd.length() == 1) {
					dd = "0" + dd;
				}
				String yyyy = dateTokens.nextToken();
				tsDate = Timestamp.valueOf(yyyy + "-" + mm + "-" + dd
						+ " 00:00:00.000000000");
			}
		} catch (Exception e) {
		}

		return tsDate;
	}
	/*
	 * 
	 *  MethodName -strDateToTsWithTime Method param - String date
	 * Convert string into timestamp
	 */
	public static Timestamp strDateToTsWithTime(String dateStr) {
		Timestamp tsDate = null;
		
		try {
			if ((dateStr != null) && (!"".equals(dateStr))) {
				StringTokenizer dateTokens = new StringTokenizer(dateStr, "/");
				String mm = dateTokens.nextToken();
				if (mm.length() == 1) {
					mm = "0" + mm;
				}
				String dd = dateTokens.nextToken();
				if (dd.length() == 1) {
					dd = "0" + dd;
				}
				String yyyy = dateTokens.nextToken();
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				tsDate = Timestamp.valueOf(yyyy + "-" + mm + "-" + dd + " " + calendar.get(calendar.HOUR_OF_DAY) + ":" + calendar.get(calendar.MINUTE) + ":" + calendar.get(calendar.SECOND) + "." + calendar.get(calendar.MILLISECOND)); 

			}
		} catch (Exception e) {
		}
		
		return tsDate;
	}

	public static String tsDateToStr(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		String dateStr = dateTS.toString();

		try {
			dateStr = dateStr.substring(0, 10);

			StringTokenizer dateTokens = new StringTokenizer(dateStr, "-");
			String year = dateTokens.nextToken();
			String month = dateTokens.nextToken();
			String day = dateTokens.nextToken();
			dateStr = month + "/" + day + "/" + year;
		} catch (Exception e) {
		}

		return dateStr;
	}
	
	public static String tsDateToStrOther(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		String dateStr = dateTS.toString();

		try {
			dateStr = dateStr.substring(0, 10);

			StringTokenizer dateTokens = new StringTokenizer(dateStr, "/");
			String year = dateTokens.nextToken();
			String month = dateTokens.nextToken();
			String day = dateTokens.nextToken();
			dateStr = month + "/" + day + "/" + year;
		} catch (Exception e) {
		}

		return dateStr;
	}

	/*
	 * 
	 * @author sonu.singh MethodName -checkStartdtBeforeEnddt Method param -
	 * String startdate,String endDate compare dates
	 */
	public static boolean checkStartdtBeforeEnddt(String stDate, String enDate) {
		boolean isValid = true;

		try {
			if ((stDate.length() != 0) && (enDate.length() != 0)) {
				Timestamp Startdate = strDateToTs(stDate);
				Timestamp Enddate = strDateToTs(enDate);
				if (Startdate != null) {
					if (Startdate.before(Enddate)) {
						isValid = true;
					} else if (Startdate.after(Enddate)) {
						isValid = false;
					}
				}
			}
		} catch (Exception e) {
			//logger.error(e);
		}

		return isValid;
	}

	/**
	 * Author:dhgupta Method Name:getYear Method Description: This method is
	 * returning current date Year in "yyyy" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getYear() {
		String strYear = "";

		try {
			String outputFormatString = "yyyy";
			// Date dt = new Date(); EXTN 1795
			Date dt = getCurrentDate();
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strYear = inputFormat.format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strYear;
	}

	/**
	 * Author:dhgupta Method Name:getYear Method Description: This method is
	 * returning current date Year in "yyyy" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getYear(Date givenDt) {
		String strYear = "";

		try {
			String outputFormatString = "yyyy";
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strYear = inputFormat.format(givenDt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strYear;
	}

	/**
	 * get the week of the current year as a string with leading zero if week is
	 * less then 10
	 * 
	 * @retrun string value of the week
	 */
	public static String getWeekOfYear() {
		String retVal = "";

		Calendar c = Calendar.getInstance();
		c.setTime(currentDate());

		int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);

		if (weekOfYear < 10) {
			retVal = retVal + "0" + weekOfYear;
		} else {
			retVal = retVal + weekOfYear;
		}
		return retVal;

	}

	/**
	 * Author:dhgupta Method Name:getMonth Method Description: This method is
	 * returning current date Month in "mm" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getMonth() {
		String strMonth = "";

		try {
			String outputFormatString = "MM";
			// Date dt = new Date(); EXTN 1795
			Date dt = getCurrentDate();
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strMonth = inputFormat.format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strMonth;
	}

	/**
	 * Author:dhgupta Method Name:getMonth Method Description: This method is
	 * returning current date Month in "mm" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getMonth(Date givenDt) {
		String strMonth = "";

		try {
			String outputFormatString = "MM";
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strMonth = inputFormat.format(givenDt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strMonth;
	}

	public static String getMonth(Date givenDt, String format)
			{
		if (format.isBlank()) {
			return getMonth(givenDt);
		}
		String strMonth = "";
		try {
			DateFormat inputFormat = new SimpleDateFormat(format);
			strMonth = inputFormat.format(givenDt);
		} catch (IllegalArgumentException ex) {
			
		}
		return strMonth;

	}

	/**
	 * Author:dhgupta Method Name:getDay Method Description: This method is
	 * returning current date date in "dd" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getDay() {
		String strDay = "";

		try {
			String outputFormatString = "dd";
			// Date dt = new Date(); EXTN1795
			Date dt = getCurrentDate();
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strDay = inputFormat.format(dt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strDay;
	}

	/**
	 * Author:dhgupta Method Name:getDay Method Description: This method is
	 * returning current date date in "dd" format.
	 * 
	 * @return String
	 * 
	 */
	public static String getDay(Date givenDt) {
		String strDay = "";

		try {
			String outputFormatString = "dd";
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strDay = inputFormat.format(givenDt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strDay;
	}
	public static String getDaySingle(Date givenDt) {
		String strDay = "";

		try {
			String outputFormatString = "d";
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strDay = inputFormat.format(givenDt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strDay;
	}



	

	/**
	 * @return String
	 */
	public static String getHourMinuteSecondFromCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getCurrentDate().getTime()); // EXTN 1795
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		String h = "";
		String m = "";
		String s = "";
		if (hour == 0) {
			h = "12";
		} else if (hour < 10) {
			h = "0" + hour;
		} else {
			h = "" + hour;
		}

		if (minute < 10) {
			m = "0" + minute;
		} else {
			m = "" + minute;
		}

		if (sec < 10) {
			s = "0" + sec;
		} else {
			s = "" + sec;
		}

		String time = h + TIME_COLON + m + TIME_COLON + s;

		return time;
	}

	public static int getDifferenceInSecond(Calendar lockedCal) {
		int second = 0;

		try {
			Calendar currentCal = Calendar.getInstance();
			currentCal.setTimeInMillis(getCurrentDate().getTime()); // EXTN 1795
			long diff = (currentCal.getTimeInMillis() - lockedCal
					.getTimeInMillis()) / 1000;
			second = (int) diff;
		} catch (Exception e) {
			
		}

		return second;
	}

	public static int getDueDateDiffInSecond(Calendar lockedCal) {
		int second = 0;
		long diff = 0;

		try {
			Calendar currentCal = Calendar.getInstance();
			currentCal.setTimeInMillis(getCurrentDate().getTime()); // EXTN 1795

			if (currentCal.before(lockedCal)) {
				diff = (lockedCal.getTimeInMillis() - currentCal
						.getTimeInMillis()) / 1000;
				second = (int) diff;
			} else {
				diff = (currentCal.getTimeInMillis() - lockedCal
						.getTimeInMillis()) / 1000;
				second = -(int) diff;
			}
		} catch (Exception e) {
		}

		return second;
	}

	public static String findQuarter(String month, String reqMonth) {
		double quarter = Double.parseDouble(month);
		quarter = Math.ceil(quarter / 3);

		String startMonth = "";
		String endMonth = "";

		switch (new Double(quarter).intValue()) {
		case 1:
			startMonth = "01";
			endMonth = "03";

			break;

		case 2:
			startMonth = "04";
			endMonth = "06";

			break;

		case 3:
			startMonth = "07";
			endMonth = "09";

			break;

		case 4:
			startMonth = "10";
			endMonth = "12";
		}

		if ("S".equals(reqMonth)) {
			return startMonth;
		}
		return endMonth;

	}

	/**
	 * find the last day of the month
	 * 
	 * @param year
	 *            String value of the year
	 * @param month
	 *            String value of the month int value
	 * @return string value of the the last day of the month or "0" if there is
	 *         any errors.
	 */
	public static String findLastDayOfMonth(String year, String month) {

		int lastDay = 0;
		Calendar calendar;

		try {
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(getCurrentDate().getTime()); // EXTN 1795

			calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);

			lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			lastDay = 0;
		}

		return Integer.toString(lastDay);
	}

	/**
	 * Create a date string with the following format month/day/year
	 * 
	 * @param month
	 *            string value for the month
	 * @param day
	 *            string value for the day
	 * @param year
	 *            string value for the year
	 * @return date string if all values are there, otherwise empty string
	 */
	public static String createDate(String month, String day, String year) {

		StringBuilder date = new StringBuilder();
		if (!month.isBlank() && !day.isBlank()
				&& !year.isBlank()) {
			final String DATE_SEPERATOR = "/";
			date.append(month).append(DATE_SEPERATOR).append(day)
					.append(DATE_SEPERATOR).append(year);
		}

		return date.toString();
	}


	public static String getYesterday() {

		Calendar c = Calendar.getInstance();
		// c.setTime(new Date()); EXTN 1795
		c.setTimeInMillis(getCurrentDate().getTime());

		c.add(Calendar.DAY_OF_YEAR, -1);

		return getStringDate(c.getTime(), null);
	}

	public static int getQuarter(int month) {
		int qtr = 0;
		if (month != 0) {
			qtr = new Double(Math.ceil(new Integer(month).doubleValue() / 3))
					.intValue();
		}
		return qtr;
	}

	public static Date currentDate() {
		Calendar cal = new GregorianCalendar();
		clearTimeFields(cal);
		cal.setTimeInMillis(getCurrentDate().getTime()); // EXTN 1795
		return cal.getTime();
	}

	

	private static void clearTimeFields(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	

	

	

	public class DateVO {
		private Date qtrBeginDt;

		private Date qtrEndDt;

		/**
		 * @return Returns the qtrBeginDt.
		 */
		public Date getQtrBeginDt() {
			return qtrBeginDt;
		}

		/**
		 * @param qtrBeginDt
		 *            The qtrBeginDt to set.
		 */
		public void setQtrBeginDt(Date qtrBeginDt) {
			this.qtrBeginDt = qtrBeginDt;
		}

		/**
		 * @return Returns the qtrEndDt.
		 */
		public Date getQtrEndDt() {
			return qtrEndDt;
		}

		/**
		 * @param qtrEndDt
		 *            The qtrEndDt to set.
		 */
		public void setQtrEndDt(Date qtrEndDt) {
			this.qtrEndDt = qtrEndDt;
		}
	}

	/**
	 * Compares two Timestamps, returning true if they are equal.
	 * 
	 * nulls are handled without exceptions. Two null references are considered
	 * to be equal.
	 * 
	 * DateUtil.isSameDay(null, null) = true
	 * 
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002
	 * 13:45 and 12 Mar 2002 13:45 would return false
	 * 
	 * @param ts1
	 *            - the first Timestamp, may be null
	 * @param ts2
	 *            - the second Timestamp, may be null
	 * @return true if the Timestamps are equal or both null
	 */
	public static boolean isSameDay(Timestamp ts1, Timestamp ts2) {
		boolean retVal = false;

		if (null == ts1 && null == ts2) {
			retVal = true;
		} else if (null != ts1 && null != ts2) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date(ts1.getTime()));

			Calendar c2 = Calendar.getInstance();
			c2.setTime(new Date(ts2.getTime()));

			if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
					&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
					&& c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
				retVal = true;
		}

		return retVal;
	}

	public static int getCurrentQtr(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		int retval = 0;
		if (c.get(Calendar.MONTH) < Calendar.APRIL) {
			retval = 1;
		} else if (c.get(Calendar.MONTH) < Calendar.JULY) {
			retval = 2;
		} else if (c.get(Calendar.MONTH) < Calendar.OCTOBER) {
			retval = 3;
		} else {
			retval = 4;
		}

		return retval;
	}

	/**
	 * @Author PKasam Convenience to get same date of next month for a given
	 *         date
	 * @param pTs
	 *            Timestamp to work with
	 * @return Timestamp of the desired results, or null if there is any problem
	 */
	public static Timestamp getNextMonthDate(Timestamp pTs) {
		Timestamp retTs = null;

		if (pTs != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date(pTs.getTime()));
			c1.add(Calendar.MONTH, 1);

			retTs = new Timestamp(c1.getTimeInMillis());
		}
		return retTs;
	}

	public static Date addOrRemoveDaysToDate(Date currntDt, String operator,
			int noOfDays) {
		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(currntDt);
		if (operator.equals("+"))
			cal.add(Calendar.DAY_OF_YEAR, +noOfDays);
		else if (operator.equals("-"))
			cal.add(Calendar.DAY_OF_YEAR, -noOfDays);
		// This statement is to clear the hours and seconds form date object
		Date returnDate = new GregorianCalendar(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
				.getTime();
		return returnDate;
	}

	

	

	/**
	 * 
	 * @param currentDate
	 * @return
	 */
	public static String getDateStringAsMMDDYYYHHmmSS(Date currentDate) {
		return getStringDate(currentDate, SIMPLE_MMDDYYYYHHMMSS_DATE_FORMAT);
	}
	
	public static String getDateStringAsYYMMDD(Date currentDate) {
		return getStringDate(currentDate, SIMPLE_YYMMDD_DATE_FORMAT);
	}
	

	/**
	 * 
	 * @return
	 */
	public static String getDateStringAsMMDDYYYHHmmSS() {
		// Date dt = new Date(); EXTN 1795
		Date dt = getCurrentDate();
		return getDateStringAsMMDDYYYHHmmSS(dt);
	}

	

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getNoOfDaysInaMonth(Date date) {
		int noOfDays = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		noOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return noOfDays;
	}

	/**
	 * Checks if date is parsable using MM/DD/YYYY format
	 * 
	 * @param date
	 * @return true if parsable else false
	 */

	public static boolean isValidDateFormat(String date) {

		if (date.isBlank()) return false;
		SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY_DATE_PATTERN);
		Date testDate = null;
		try {
			testDate = sdf.parse(date);
		} catch (Exception e) {
			return false;
		}

		if (!sdf.format(testDate).equals(date)) {
			return false;
		}

		return true;
	}

	

	

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {

		// .format(new Date()); EXTN 1795
		String currentDateTime = new SimpleDateFormat(DATE_AND_TIME_FORMAT)
				.format(getCurrentDate());
		return currentDateTime;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {

		String currentDateString = new SimpleDateFormat(DATE_FORMAT).format(getCurrentDate());
		return currentDateString;
	}

	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {

		// return new Date(); EXTN 1795
		if (currentDate == null) {
			return new Date();
		} else {
			// currentDate as per database system date
			Calendar systemDateCal = Calendar.getInstance();
			systemDateCal.setTimeInMillis(currentDate.getTime());
			// append current time
			Calendar currentTimeCal = Calendar.getInstance();
			currentTimeCal.setTime(new Timestamp(new Date().getTime()));
			systemDateCal.set(Calendar.HOUR, currentTimeCal.get(Calendar.HOUR));
			systemDateCal.set(Calendar.MINUTE,
					currentTimeCal.get(Calendar.MINUTE));
			systemDateCal.set(Calendar.SECOND,
					currentTimeCal.get(Calendar.SECOND));
			systemDateCal.set(Calendar.MILLISECOND,
					currentTimeCal.get(Calendar.MILLISECOND));
			return new Date(systemDateCal.getTimeInMillis());
		}
	}

	/**
	 * 
	 * @return
	 */
	public static Date getDateYearsLaterFromCurrentDate(int years) {

		// currentDate as per database system date
		Calendar systemDateCal = Calendar.getInstance();
		systemDateCal.setTimeInMillis(CURRENT_DATE.getTime());
		systemDateCal.add(Calendar.YEAR, years);
		// append current time
		Calendar currentTimeCal = Calendar.getInstance();
		currentTimeCal.setTime(new Timestamp(new Date().getTime()));
		// currentTimeCal.add(Calendar.YEAR, years);
		systemDateCal.set(Calendar.HOUR, currentTimeCal.get(Calendar.HOUR));
		systemDateCal.set(Calendar.MINUTE, currentTimeCal.get(Calendar.MINUTE));
		systemDateCal.set(Calendar.SECOND, currentTimeCal.get(Calendar.SECOND));
		systemDateCal.set(Calendar.MILLISECOND,
				currentTimeCal.get(Calendar.MILLISECOND));
		// systemDateCal.set(Calendar.YEAR, currentTimeCal.get(Calendar.YEAR));
		return new Date(systemDateCal.getTimeInMillis());
	}

	/**
	 * 
	 * @param dateTimeInMillis
	 * @return
	 */
	public static String getFormattedDateTime(long dateTimeInMillis) {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		return df.format(new Date(dateTimeInMillis));
	}

	

	/**
	 * 
	 * @param currentDate
	 * @return
	 */
	public static String getDateStringAsYYYYMMDDHHmmSS(Date currentDate) {
		return getStringDate(currentDate, SIMPLE_YYYYMMDDHHMMSSSS_DATE_FORMAT);
	}

	/**
	 * This method is used to create a Year List from 2007 to the year after the
	 * current year.
	 * 
	 * @return yearList
	 */
	public static List<String> prepareYearList() {
		int currYear = Integer.parseInt(getYear());
		List<String> yearList = new ArrayList<String>();

		for (int cnt = currYear + 1; cnt >= 2007; cnt--)
			yearList.add(String.valueOf(cnt));
		return yearList;
	}

	public static void setCurrentDate(Date date) {
		currentDate = date;
	}

	

	/**
	 * Check to see if startDate is before endDate, only accepts dates in
	 * mm/dd/yyyy format
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isStartDateBeforeEndDate(String startDate,
			String endDate) {
		try {
			int startYear = Integer.parseInt(startDate.substring(6, 10));
			int endYear = Integer.parseInt(endDate.substring(6, 10));
			if (startYear > endYear) { // compare the years
				return false;
			} else if (startYear == endYear) { // if the years are the same
				int startMonth = Integer.parseInt(startDate.substring(0, 2));
				int endMonth = Integer.parseInt(endDate.substring(0, 2));
				if (startMonth > endMonth) { // compare the months
					return false;
				} else if (startMonth == endMonth) { // if the months are the
														// same
					int startDay = Integer.parseInt(startDate.substring(3, 5));
					int endDay = Integer.parseInt(endDate.substring(3, 5));
					if (startDay > endDay) { // compare the days
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if none of the conditions were met, then return true
		return true;
	}
	
	public static int getCalendartimeHrs(String hrs){
		int n = Integer.valueOf(hrs);
		if(n>12){
			n = n-12;
		}
		return n;
	}
	
	public static int getCalendartimeMins(String mins){
		int n = Integer.valueOf(mins);
		if(n>60){
			n = n-60;
		}
		return n;
	}

	/**
	 * Fetch the first and last day of the current week.
	 * 
	 * @return String containing the first and last date of the current week
	 */

	public static String getGrossWageDates() {
		String grossWagesDates = null;
		// get the current date
		Calendar c = Calendar.getInstance();
		// if the current day is not sunday...
		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			// then back up a day
			c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
		}

		// now put the first date in the string and add 'to'
		grossWagesDates = " " + (c.get(Calendar.MONTH) + 1) + "/"
				+ c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR)
				+ " to ";

		// now change the date to the end of the week by adding 6 to the day
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 6);
		// and append the end of the week to the string
		grossWagesDates = grossWagesDates + (c.get(Calendar.MONTH) + 1) + "/"
				+ c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
		return grossWagesDates;
	}

	public static int getNumberOfWeeks(Timestamp recentEmplrStDate,
			Timestamp emplrEndDate) {
		int numberOfWeeks = 0;
		if (emplrEndDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			String endDate = formatter.format(emplrEndDate.getTime());
			String stDate = formatter.format(recentEmplrStDate.getTime());
			try {
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();

				Date date1 = formatter.parse(stDate);
				Date date2 = formatter.parse(endDate);
				cal1.setTime(date1);
				cal2.setTime(date2);
				int numberOfYears = cal2.get(Calendar.YEAR)
						- cal1.get(Calendar.YEAR);
				int numberOfDays = (numberOfYears * 365)
						+ cal2.get(Calendar.DAY_OF_YEAR)
						- cal1.get(Calendar.DAY_OF_YEAR);
				numberOfWeeks = numberOfDays / 7;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return numberOfWeeks;
	}
	
	public static int getNumberOfWeeks(String sd,
			String ed) {
		int numberOfWeeks = 0;
		if (sd != null && ed!=null) {
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat(MM_DD_YYYY_DATE_PATTERN);
	 
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = format.parse(sd);
				d2 = format.parse(ed);
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				numberOfWeeks=(int)diffDays/7;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
		return numberOfWeeks;
	}

	
	


	// Returns whether the Date String starts with a zero
	// eg, "12/12/0912" returns true
	public static boolean yearStartsWithZero(String text) {
		if (text != null && text.length() == 10) {
			if ("0".equals(text.substring(6, 7))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to get the previous sunday of the week in which the
	 * given date falls, or null otherwise.
	 * 
	 * @param claimFileDate
	 *            The date according to which sunday of the week need to be
	 *            determined
	 * @return sunday Returns the date of sunday of the week in which the given
	 *         date falls
	 */
	public static Date getPreviousSunday(Date claimFileDate) throws Exception {
		Calendar calendar = Calendar.getInstance();
		Date sunday = null;
		if (claimFileDate != null) {

			calendar.setTime(claimFileDate);
			int day = calendar.get(Calendar.DAY_OF_WEEK);

			if (day == Calendar.SUNDAY) {
				calendar.add(Calendar.DATE, 0);
			} else if (day == Calendar.MONDAY) {
				calendar.add(Calendar.DATE, -1);
			} else if (day == Calendar.TUESDAY) {
				calendar.add(Calendar.DATE, -2);
			} else if (day == Calendar.WEDNESDAY) {
				calendar.add(Calendar.DATE, -3);
			} else if (day == Calendar.THURSDAY) {
				calendar.add(Calendar.DATE, -4);
			} else if (day == Calendar.FRIDAY) {
				calendar.add(Calendar.DATE, -5);
			} else if (day == Calendar.SATURDAY) {
				calendar.add(Calendar.DATE, -6);
			}

			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					DateUtil.MM_DD_YYYY_DATE_PATTERN);
			sunday = simpledateformat.parse(simpledateformat.format(calendar
					.getTime()));
		}

		return sunday;
	}

	public static int getNumberOfDays(Timestamp recentEmplrEndDate,
			Timestamp emplrEndDate) {
		int numberOfDays = 0;
		if (emplrEndDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			String endDate = formatter.format(emplrEndDate.getTime());
			String stDate = formatter.format(recentEmplrEndDate.getTime());
			try {
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();
				Date date1 = formatter.parse(endDate);
				Date date2 = formatter.parse(stDate);
				cal1.setTime(date1);
				cal2.setTime(date2);
				numberOfDays = cal2.get(Calendar.DAY_OF_YEAR)
						- cal1.get(Calendar.DAY_OF_YEAR);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return numberOfDays;
	}
	/* Changes as part of Product Optimizations - starts */
	/**
	 * This method will calculate number of days between two dates 
	 * [Existing methods in this class does not handle dates with different year]
	 */
	public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public static int daysBetween(Timestamp t1, Timestamp t2) {
		return (int)( (t2.getTime() - t1.getTime()) / (1000 * 60 * 60 * 24));
	}
	/* Changes as part of Product Optimizations - ends */


	public static int getYearDifference(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		int year2 = calendar2.get(Calendar.YEAR);
		int month2 = calendar2.get(Calendar.MONTH);
		int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

		int year1 = calendar1.get(Calendar.YEAR);
		int month1 = calendar1.get(Calendar.MONTH);
		int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

		int difference = year2 - year1;
		if (month2 < month1 || (month1 == month2 && day2 < day1)) {
			difference--;
		}

		return difference;
	}

	public static String getWeekDayDesc(int day) {
		// Calendar calendar = Calendar.getInstance();
		String dayDesc = null;
		// Changes for Product Optimizations starts
		if (day == Calendar.SUNDAY) {
			dayDesc = "Sunday";
		} else if (day == Calendar.MONDAY) {
			dayDesc = "Monday";
		} else if (day == Calendar.TUESDAY) {
			dayDesc = "Tuesday";
		} else if (day == Calendar.WEDNESDAY) {
			dayDesc = "Wednesday";
		} else if (day == Calendar.THURSDAY) {
			dayDesc = "Thursday";
		} else if (day == Calendar.FRIDAY) {
			dayDesc = "Friday";
		} else if (day == Calendar.SATURDAY) {
			dayDesc = "Saturday";
		}
		// Changes for Product Optimizations Ends
		return dayDesc;
	}


	public static boolean isSunday(Date date)throws Exception{
		Calendar calendar = Calendar.getInstance();		
		if (date != null) {

			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.SUNDAY) {
				return true;
			} 
		}
		return false;
	}
	public static boolean isSaturday(Date date)throws Exception{
		Calendar calendar = Calendar.getInstance();		
		if (date != null) {

			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.SATURDAY) {
				return true;
			} 
		}
		return false;
	}
	public static boolean isMonday(Date date)throws Exception{
		Calendar calendar = Calendar.getInstance();		
		if (date != null) {

			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.MONDAY) {
				return true;
			} 
		}
		return false;
	}
	
	/**
	 * Takes in a Date and an int for the day of the week. Uses the Calendar constants for comparison (Sunday = 1, Monday = 2, ... Saturday = 7)
	 * @param date
	 * @param dayOfWeek
	 * @return
	 *
	 */
	public static boolean isDay(Date date, int dayOfWeek){
		Calendar calendar = Calendar.getInstance();		
		if (date != null) {

			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (day == dayOfWeek) {
				return true;
			} 
		}
		return false;
	}
	
	
	/**
	 * Method to return the century (in terms of ICON spec) of the given date as string
	 * Returns the first two digits of year. For example, date with year 2013 returns "20"
	 * and date with year 1999 returns "19".  
	 * If parameter is null or exception, return null
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getCentury(Date date) throws Exception {
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String yearStr = sdf.format(date);
				String centuryStr = yearStr.substring(0, 2);
				return centuryStr;
			}
			catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	
	public static String dateStanderdFormat(String date) throws Exception {
		if (date != null) {
			DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
			try {
				Date myDate;
				myDate = dateFormat.parse(date);
				// Use the Calendar class to subtract one day
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(myDate);
				calendar.add(Calendar.DAY_OF_YEAR, +10);
				// Use the date formatter to produce a formatted date string
				Date previousDate = calendar.getTime();
				String result = dateFormat.format(previousDate);
				return result;
			}catch (Exception e) {
				return null;
			}
		}else {
			return null;
		}
	}
	
	
	
	
	public static String[] getPastYearList(){
		Date date = Calendar.getInstance().getTime();
		String[] value = new String[0];
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String yearStr = sdf.format(date);
			int currentYear = Integer.parseInt(yearStr);
			int startYearValue = startYear;	
			int diff = currentYear - startYearValue;
			value = new String[diff+1];
			
			for(int i=0 ;  i <= diff ; i++){
				value[i] = String.valueOf(startYearValue);
				startYearValue++;
			}
			return value;
		}
		catch (Exception e) {
			return new String[0];
		}
	}
	
	public static boolean isThisDateValid(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null){
			return false;
		}
		if(dateFromat==null){
			dateFromat = "MM/DD/yyyy";
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
 
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			//System.out.println(date);
 
		} catch (Exception e) {
 
			e.printStackTrace();
			return false;
		}
 
		return true;
	}
	
	public static boolean validateServiceDt(Date inputDate){
		Calendar cal= Calendar.getInstance();
		//cal.setTime(inputDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)
				- 1);
		Date dateBeforeYears = cal.getTime();
		if(inputDate.equals(UIDateRoutines.getSysdate()) || inputDate.before(UIDateRoutines.getSysdate())){
			if (inputDate.equals(dateBeforeYears) || inputDate.after(dateBeforeYears) ){
				return true;
			}}
		return false;
	}
	
	  
			/**
			 * Checks if date is parsable using YYYYMMDD format
			 * 
			 * @param date
			 * @return true if parsable else false
			 */

	public static boolean isValidDateFormatForICON(String date) {

				if (date.isBlank()) return false;
				SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_DATE_PATTERN);
				Date testDate = null;
				try {
					testDate = sdf.parse(date);
				} catch (Exception e) {
					return false;
				}

				if (!sdf.format(testDate).equals(date)) {
					return true;
				}

				return true;
	}
	
	 /**
     * Create a timestamp based upon a string and the SimpleDateFormat
     * describing it. Return null if there is any problem in the formatting.
     * 
     * @param sdf
     *            SimpleDateFormat describing the string parameter.
     * @param input
     *            String to be converted into a timestamp.
     * @return TimeStamp created by parsing the String using the
     *         SimpleDateFormat. Null if there were any problems.
     */
    public static Timestamp getTimestamp(SimpleDateFormat sdf, String input) {
        
        Date date = parse(sdf, input);
        if (date != null) {
            
            return new Timestamp(date.getTime());
        }
        
        return null;
    }
    
    /**
     * Parse a string return a Date. Hide all errors, and return null.
     * 
     * @param sdf
     *            SimpleDateFormat to use in the parsing.
     * @param input
     *            String to be parsed.
     * @return null if there is a problem, the Date if the String parses without
     *         problem.
     */
    public static Date parse(SimpleDateFormat sdf, String input) {
        
        if (sdf != null && input != null) {
            try {
                
                return sdf.parse(input);
            } catch (Exception e) {
                
                return null;
            }
        }
        
        return null;

    }
    
    public static boolean isStartDateAfterEndDate(String strQtr, String endQtr, String strtYear, String endYr ) {
    	try {
    		int startYear = Integer.parseInt(strtYear);
    		int endYear = Integer.parseInt(endYr);
    		if(startYear > endYear) { // compare the years
    			return false;
    		}
    		else if(startYear == endYear) { // if the years are the same
    			int startQtr = Integer.parseInt(strQtr);
    			int endOtr = Integer.parseInt(endQtr);
    			if(startQtr > endOtr) { // compare the months
    				return false;
    			}
        			}
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	// if none of the conditions were met, then return true
    	return true;
    }
    
    
    
    public static Date getDateAfter30days(Date currntDt) {
    	Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(currntDt);		
		cal.add(Calendar.DATE, +30);
		
		//This statement is to clear the hours and seconds form date object
		Date returnDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal
				.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).getTime();
		return returnDate;
    }
    
    /**
	 * This method is used to get the Saturday prior to the week in which the
	 * given date falls, or null otherwise.
	 * 
	 * @param date
	 *            The date according to which Saturday of the week need to be
	 *            determined
	 * @return saturday Returns the date of Saturday of the week in which the given
	 *         date falls
	 */
	public static Date getPreviousSaturday(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		Date saturday = null;
		if (date != null) {

			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);

			if (day == Calendar.SUNDAY) {
				calendar.add(Calendar.DATE, -1);
			} else if (day == Calendar.MONDAY) {
				calendar.add(Calendar.DATE, -2);
			} else if (day == Calendar.TUESDAY) {
				calendar.add(Calendar.DATE, -3);
			} else if (day == Calendar.WEDNESDAY) {
				calendar.add(Calendar.DATE, -4);
			} else if (day == Calendar.THURSDAY) {
				calendar.add(Calendar.DATE, -5);
			} else if (day == Calendar.FRIDAY) {
				calendar.add(Calendar.DATE, -6);
			} else if (day == Calendar.SATURDAY) {
				calendar.add(Calendar.DATE, -7);
			}

			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					DateUtil.MM_DD_YYYY_DATE_PATTERN);
			saturday = simpledateformat.parse(simpledateformat.format(calendar
					.getTime()));
		}

		return saturday;
	}
	
	/*
	 * 
	 * @author Pankaj Thakur MethodName -strDateHyphenToTs Method param - String date
	 * Convert string into timestamp
	 */
	public static Timestamp strDateHyphenToTs(String dateStr) {
		Timestamp tsDate = null;

		try {
			if (!dateStr.isBlank()) {
				StringTokenizer dateTokens = new StringTokenizer(dateStr, "-");
				String yyyy = dateTokens.nextToken();
				String mm = dateTokens.nextToken();
				if (mm.length() == 1) {
					mm = "0" + mm;
				}
				String ddTemp = dateTokens.nextToken();
				String ddStr[] = ddTemp.split(" ");
				String dd = ddStr[0];
				if (dd.length() == 1) {
					dd = "0" + dd;
				}
				tsDate = Timestamp.valueOf(yyyy + "-" + mm + "-" + dd
						+ " 00:00:00.000000000");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tsDate;
	}
    
	public static String formatSimpleDateTime(Date inputDate) {
		String outputFormatString = YYYY_MM_DD_DATE_TIME_PATTERN;
		String strReturnDate = "";

		try {
			DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
			strReturnDate = inputFormat.format(inputDate);
		} catch (Exception ex) {
		}

		return strReturnDate;
	}

	
	 public static Date getDate(Date currntDt , int days) {
	    	Calendar cal = java.util.Calendar.getInstance();
			cal.setTime(currntDt);		
			cal.add(Calendar.DATE, days);			
			Date returnDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).getTime();
			return returnDate;
	    }
	 
	 
	 public static String getQtrFromDate(Date dt) {
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			String retval = null;
			if (c.get(Calendar.MONTH) < Calendar.APRIL) {
				retval = "1";
			} else if (c.get(Calendar.MONTH) < Calendar.JULY) {
				retval = "2";
			} else if (c.get(Calendar.MONTH) < Calendar.OCTOBER) {
				retval = "3";
			} else {
				retval = "4";
			}						
			return retval;
		}
	 
	 /**
	 * 
	 * @param qtr
	 * @param year
	 * @return
	 */
	public static String getQtrEndDateStr(String qtr, String year) {
		String endDate = "";
		if ("1".equals(qtr)) {
			endDate = "03/31/" + year;
		} else if ("2".equals(qtr)) {
			endDate = "06/30/" + year;
		} else if ("3".equals(qtr)) {
			endDate = "09/30/" + year;
		} else if ("4".equals(qtr)) {
			endDate = "12/31/" + year;
		}
		return endDate;

	}
	
	/**
	 * This method is used to get the previous sunday of the week in which the
	 * given timestamp falls, or null otherwise.
	 * 
	 * @param timestamp
	 *            The timestamp according to which sunday of the week need to be
	 *            determined
	 * @return sunday Returns the timestamp of sunday of the week in which the given
	 *         timestamp falls
	 */
	public static Timestamp getPreviousSundayTs(Timestamp givenTimestamp) throws Exception {
		Calendar calendar = Calendar.getInstance();
		Timestamp sunday = null;
		if (givenTimestamp != null) {
			
			calendar.setTime(givenTimestamp);
			calendar.add(Calendar.DAY_OF_WEEK, - (calendar.get(Calendar.DAY_OF_WEEK)-1)); // previous Sunday 
			sunday = new Timestamp(calendar.get(Calendar.DATE));
		}

		return sunday;
	}
	
	/**
	 * @param years
	 * @param day
	 * @return
	 */
	public static Date minusYearDayInCurrentDate(int years, int day) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date currentDate = new Date();
		dateFormat.format(currentDate);
		Calendar systemDateCal = Calendar.getInstance();
		systemDateCal.setTime(currentDate);
		systemDateCal.setTimeInMillis(CURRENT_DATE.getTime());
		systemDateCal.add(Calendar.YEAR, -years);
		systemDateCal.add(Calendar.DAY_OF_MONTH, -day);
		
		Calendar currentTimeCal = Calendar.getInstance();
		currentTimeCal.setTime(new Timestamp(new Date().getTime()));
		
		systemDateCal.set(Calendar.HOUR, currentTimeCal.get(Calendar.HOUR));
		systemDateCal.set(Calendar.MINUTE, currentTimeCal.get(Calendar.MINUTE));
		systemDateCal.set(Calendar.SECOND, currentTimeCal.get(Calendar.SECOND));
		systemDateCal.set(Calendar.MILLISECOND,	currentTimeCal.get(Calendar.MILLISECOND));
	
		return new Date(systemDateCal.getTimeInMillis());
	}
	
	public static Timestamp getWeekDateAfterDays(Timestamp givenTimestamp, int noOfDays) {	
		Calendar calendar = Calendar.getInstance();
		Timestamp weekDt = null;
		if (givenTimestamp != null) {
			calendar.setTime(givenTimestamp);
			calendar.add(Calendar.DAY_OF_WEEK, noOfDays);  		
			weekDt = new Timestamp(calendar.getTimeInMillis());
		}			
		return weekDt;
	}
	
	public static Timestamp getMonthDateAfterMonths(Timestamp givenTimestamp, int noOfMonths) {	
		Calendar calendar = Calendar.getInstance();
		Timestamp monthDt = null;
		if (givenTimestamp != null) {
			calendar.setTime(givenTimestamp);
			calendar.add(Calendar.MONTH, noOfMonths);  		
			monthDt = new Timestamp(calendar.getTimeInMillis());
		}			
		return monthDt;
	}
	
	public static String getDateStringAsMMDDYYHHmmSS(Date date) {
		return getStringDate(date, SIMPLE_MMDDYYHHMMSS_DATE_FORMAT);
	}
	
	public static String getDateStringAsMMDDYYYY(Date date) {
		return getStringDate(date, SIMPLE_MMDDYYYY_DATE_FORMAT);
	}
	
	public static Timestamp getThisSunday(Timestamp weekDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekDate);
		Date sunday=null;
		Timestamp thisSunday = null;
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		if (day == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, 0);
		} else if (day == Calendar.MONDAY) {
			calendar.add(Calendar.DATE, 6);
		} else if (day == Calendar.TUESDAY) {
			calendar.add(Calendar.DATE, 5);
		} else if (day == Calendar.WEDNESDAY) {
			calendar.add(Calendar.DATE, 4);
		} else if (day == Calendar.THURSDAY) {
			calendar.add(Calendar.DATE, 3);
		} else if (day == Calendar.FRIDAY) {
			calendar.add(Calendar.DATE, 2);
		} else if (day == Calendar.SATURDAY) {
			calendar.add(Calendar.DATE, 1);
		}

		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				DateUtil.MM_DD_YYYY_DATE_PATTERN);
		try{
			sunday = simpledateformat.parse(simpledateformat.format(calendar
					.getTime()));
			if(sunday != null) {
				thisSunday = new Timestamp(sunday.getTime());
			}
			
		}catch(Exception e){
		}
		 return thisSunday;
	}
	
	/*
	 * This function will take date as a string parameter in yyyy-mm-dd format
	 * and will return date object.
	 */
	public static Date getDateMMDDYYYY(String date) {//2021-12-12
		if(date!=null && !date.equalsIgnoreCase("")) {
			int dateYear = Integer.parseInt(date.substring(0, 4));
			int dateMonth = Integer.parseInt(date.substring(5, 7));
			int dateDate = Integer.parseInt(date.substring(8, 10));
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MONTH, dateMonth - 1);
			c.set(Calendar.DATE, dateDate);
			c.set(Calendar.YEAR, dateYear);
			return c.getTime();
		}
		return null;
	}

	public static Date getDateNew(String date, String datePattern) {
		if (date.isBlank()) {
			return null;
		}
		if (!date.isBlank() && date.contains("-")) {
			String[] dateParts = date.split("-");
			date = dateParts[1].concat("/").concat(dateParts[2]).concat("/").concat(dateParts[0]);
		}

		Date result = null;

		try {
			result = new SimpleDateFormat(MM_DD_YYYY_DATE_PATTERN).parse(date);
		} catch (Exception e) {
		}

		return result;
	}
	
	public static Date getDateBefore30days(Date currntDt, Integer noOfDays) {
    	Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(currntDt);		
		cal.add(Calendar.DATE, -noOfDays);
		
		//This statement is to clear the hours and seconds form date object
		Date returnDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal
				.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).getTime();
		return returnDate;
    }
}
