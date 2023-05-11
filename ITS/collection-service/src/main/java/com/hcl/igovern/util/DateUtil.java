package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	public static final String MM_DD_YYYY_DATE_PATTERN = "MM/dd/yyyy";

	public static final String DATE_FORMAT_WITHTIME = "MM/dd/yyyy kk:mm:ss a";

	public static final String DATE_FORMAT_WITH_TIME = "MM-dd-yyyy hh:mm a";

	private static final String YYYY_MM_DD_DATE_TIME_PATTERN = "MM-dd-yyyy HH:mm:ss";

	private static Date currentDate = null;
	
	private DateUtil() {}
	
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
			format = new SimpleDateFormat(MM_DD_YYYY_DATE_PATTERN);
		}

			return format.format(date);
	}

	public static String formatSimpleDateWithTime(Date inputDate) {
		String outputFormatString = DATE_FORMAT_WITHTIME;
		String strReturnDate = "";

		DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
		strReturnDate = inputFormat.format(inputDate);

		return strReturnDate;
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

		return tsDate;
	}
	

	public static String tsDateToStr(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		String dateStr = dateTS.toString();

		dateStr = dateStr.substring(0, 10);

		StringTokenizer dateTokens = new StringTokenizer(dateStr, "-");
		String year = dateTokens.nextToken();
		String month = dateTokens.nextToken();
		String day = dateTokens.nextToken();
		dateStr = month + "/" + day + "/" + year;

		return dateStr;
	}
	
	public static String tsDateToStrOther(Timestamp dateTS) {
		if (dateTS == null) {
			return null;
		}

		String dateStr = dateTS.toString();

		dateStr = dateStr.substring(0, 10);

		StringTokenizer dateTokens = new StringTokenizer(dateStr, "/");
		String year = dateTokens.nextToken();
		String month = dateTokens.nextToken();
		String day = dateTokens.nextToken();
		dateStr = month + "/" + day + "/" + year;

		return dateStr;
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
		c.setTimeInMillis(getCurrentDate().getTime());

		c.add(Calendar.DAY_OF_YEAR, -1);

		return getStringDate(c.getTime(), null);
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
	 * @param dateTimeInMillis
	 * @return
	 */
	public static String getFormattedDateTime(long dateTimeInMillis) {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		return df.format(new Date(dateTimeInMillis));
	}

	public static void setCurrentDate(Date date) {
		currentDate = date;
	}
	
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
				String[] ddStr = ddTemp.split(" ");
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

		DateFormat inputFormat = new SimpleDateFormat(outputFormatString);
		strReturnDate = inputFormat.format(inputDate);

		return strReturnDate;
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
}
