package com.hcl.igovern.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UIDateRoutines {

	private static Timestamp currentDate = null;

	public static Timestamp getCurrentDate() {
		return currentDate;
	}

	public static void setCurrentDate(Timestamp sysDate) {
		UIDateRoutines.currentDate = sysDate;
	}

	public static Timestamp getSysdate() {
		return new Timestamp(new Date().getTime());
	}

	public static Timestamp getSysdateWithoutTime() {
		if (currentDate == null) {
			return new Timestamp(new Date().getTime());
		}
		// currentDate as per database system date
		Calendar systemDateCal = Calendar.getInstance();
		systemDateCal.setTimeInMillis(currentDate.getTime());
		// append current time
		Calendar currentTimeCal = Calendar.getInstance();
		currentTimeCal.setTime(new Timestamp(new Date().getTime()));
		systemDateCal.set(Calendar.HOUR_OF_DAY, 0);
		systemDateCal.set(Calendar.MINUTE, 0);
		systemDateCal.set(Calendar.SECOND, 0);
		systemDateCal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(systemDateCal.getTimeInMillis());
	}

	public static Calendar getCalendarToday() {
		Calendar systemDateCal = Calendar.getInstance();
		systemDateCal.setTimeInMillis(currentDate.getTime());
		// append current time
		Calendar currentTimeCal = Calendar.getInstance();
		currentTimeCal.setTime(new Timestamp(new Date().getTime()));
		systemDateCal.set(Calendar.HOUR, currentTimeCal.get(Calendar.HOUR));
		systemDateCal.set(Calendar.MINUTE, currentTimeCal.get(Calendar.MINUTE));
		systemDateCal.set(Calendar.SECOND, currentTimeCal.get(Calendar.SECOND));
		systemDateCal.set(Calendar.MILLISECOND, currentTimeCal.get(Calendar.MILLISECOND));
		return systemDateCal;
	}

	public static Timestamp getTrunc(Timestamp in) {
		if (in == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (new Timestamp(c.getTime().getTime()));
	}

	public static long getDaysForTimeSpan(Timestamp strtDt, Timestamp endDt) {
		long time = endDt.getTime() - strtDt.getTime();
		return time / (24 * 60 * 60 * 1000);
	}

	public static Timestamp addDays(Timestamp in, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.getTime()));
		c.add(Calendar.DATE, days);
		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp addMonths(Timestamp in, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.getTime()));
		c.add(Calendar.MONTH, months);
		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp addYears(Timestamp in, int years) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.getTime()));
		c.add(Calendar.YEAR, years);
		return new Timestamp(c.getTime().getTime());
	}

	public static String getPeriodYearFormat(Timestamp ts) {
		String retval = "";
		String yrQtr = getYearQtr(ts);
		String yr = yrQtr.substring(0, 4);
		String qtr = yrQtr.substring(4);
		retval = "Q" + qtr + "/" + yr;
		return retval;
	}
	
	public static String getYearQtr(Timestamp in)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(new Date(in.getTime()));
	    String retval = Integer.toString(c.get(Calendar.YEAR));
	    
	    if (c.get(Calendar.MONTH) < Calendar.APRIL)
	    {
	      retval += "1";
	    }
	    else if (c.get(Calendar.MONTH) < Calendar.JULY)
	    {
	      retval += "2";
	    }
	    else if (c.get(Calendar.MONTH) < Calendar.OCTOBER)
	    {
	      retval += "3";
	    }
	    else
	    {
	      retval += "4";
	    }
	    return retval;
	  }

	/**
	 * <b>Method</b> <br>
	 * getDateStringFormat</b> <br>
	 * <b>Description</b></br>
	 * <br>
	 * This method constructs the indicated format based on a timestamp</b>
	 * 
	 * @param inDt
	 * 
	 * @return String
	 */
	public static String getDateStringFormat(Timestamp inDt, char ord) {
	      String retval = null;
	      try{
			    Calendar calendar = new GregorianCalendar();
			    calendar.setTime(inDt);
			    String YY = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
			    String MM = String.valueOf(calendar.get(Calendar.MONTH)+ 1); 
			    String DD = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			    
			    if (MM.length() == 1){
			        MM = "0".concat(MM);
			    }
			    if (DD.length() == 1){
			        DD = "0".concat(DD);
			    }
			    switch(ord){
			    	case 'Y':
			    	   retval = YY.concat(MM).concat(DD);
			    	   break;
			    	case 'M':
			    	    retval = MM.concat(DD).concat(YY);
			    	    break;
			    	default:
			    	    retval = YY.concat(MM).concat(DD);
			    }
	      }
	      catch(NullPointerException npe){
	          retval = "      ";
	      }
	      return retval;
		}

	/**
	 * <b>Method</b> <br>
	 * getDateStringFormat</b> <br>
	 * <b>Description</b></br>
	 * <br>
	 * This method constructs the indicated format based on a timestamp</b>
	 * 
	 * @param inDt
	 * 
	 * @return String
	 */
	/**
	 * construct a string format of the date, including the CC for the year, with
	 * out /. example YYYYMMDD or MMDDYYYY
	 * 
	 * @param inDt timestamp that is to be formated or null if wanting current date
	 * @param ord  Order that is to be used, Y = YYYYMMDD, M = MMDDYYYY, D =
	 *             DDMMYYYY
	 * @return
	 */
	public static String getDateStringFormatWithCC(Timestamp inDt, char ord) {
	      String retval = null;
	      try{
			    Calendar calendar = new GregorianCalendar();
			    
			    if(null == inDt)
			    {
			      inDt = UIDateRoutines.getSysdate();
			    }
			    
			    calendar.setTime(inDt);
			    String YY = String.valueOf(calendar.get(Calendar.YEAR));
			    String MM = String.valueOf(calendar.get(Calendar.MONTH)+ 1); 
			    String DD = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			    
			    if (MM.length() == 1){
			        MM = "0".concat(MM);
			    }
			    if (DD.length() == 1){
			        DD = "0".concat(DD);
			    }
			    switch(ord){
			    	case 'Y':
			    	   retval = YY.concat(MM).concat(DD);
			    	   break;
			    	case 'M':
			    	    retval = MM.concat(DD).concat(YY);
			    	    break;
			    	case 'D':
			    	    retval = DD.concat(MM).concat(YY);
			    	    break;
			    	default:
			    	    retval = YY.concat(MM).concat(DD);
			    }
	      }
	      catch(NullPointerException npe){
	          retval = "      ";
	      }
	      return retval;
	  }

	

	/**
	 * find the fiscal year begin date based on the passed in timestamp
	 * 
	 * @param pTs timestamp of interest
	 * @return timestamp object representing the 1st of the year
	 */
	public static Timestamp findFiscalYearBegDt(Timestamp pTs) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(pTs.getTime()));
		c.add(Calendar.YEAR, -1);
		c.set(c.get(Calendar.YEAR), 9, 1);

		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp findFiscalYearEndDt(Timestamp pTs) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(pTs.getTime()));
		c.set(c.get(Calendar.YEAR), 8, 30);

		return new Timestamp(c.getTime().getTime());
	}

	// zz add
	public static String getPreviousYearMonthStr(Timestamp pTs) {
		Calendar c = Calendar.getInstance();

		if (null == pTs) {
			pTs = UIDateRoutines.getSysdate();
		}

		c.setTime(new Date(pTs.getTime()));
		c.add(Calendar.MONTH, -1);

		String yearStr = "" + c.get(Calendar.YEAR);
		String monthStr = "" + c.get(Calendar.MONTH);

		return yearStr + monthStr;
	}

	public static String getCurrentYearMonthStr() {
		Calendar c = Calendar.getInstance();
		Timestamp pTs = UIDateRoutines.getSysdate();
		c.setTime(new Date(pTs.getTime()));

		String yearStr = "" + c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String monthStr = "" + month;

		return yearStr + monthStr;
	}

	public static String getPreviousYearMonthStr() {
		Timestamp pTs = UIDateRoutines.getSysdate();

		return getPreviousYearMonthStr(pTs);
	}

	public static String getPreviousYearStr(Timestamp pTs) {
		Calendar c = Calendar.getInstance();

		if (null == pTs) {
			pTs = UIDateRoutines.getSysdate();
		}

		c.setTime(new Date(pTs.getTime()));
		c.add(Calendar.YEAR, -1);

		return "" + c.get(Calendar.YEAR);
	}

	public static String getCurrentYearStr() {
		Calendar c = Calendar.getInstance();
		Timestamp pTs = UIDateRoutines.getSysdate();
		c.setTime(new Date(pTs.getTime()));
		return "" + c.get(Calendar.YEAR);
	}

	public static String getPreviousYearStr() {
		Timestamp pTs = UIDateRoutines.getSysdate();

		return getPreviousYearStr(pTs);
	}

	public static Timestamp getFirstDayOfMonth(Timestamp in) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(in.getTime()));
//      c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp getLastDayOfMonth(Timestamp in) {
		Calendar c = Calendar.getInstance();
		in = getFirstDayOfMonth(in);
		c.setTime(new Date(in.getTime()));
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		return new Timestamp(c.getTime().getTime());
	}

	/**
	 * Convenience to get same date of next month for a given date
	 * 
	 * @param pTs Timestamp to work with
	 * @return Timestamp of the desired results, or null if there is any problem
	 */
	public static Timestamp getPreviousMonthDate(Timestamp pTs) {
		Timestamp retTs = null;

		if (pTs != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date(pTs.getTime()));
			c1.add(Calendar.MONTH, -1);

			retTs = new Timestamp(c1.getTimeInMillis());
		}
		return retTs;
	}

	/**
	 * Convenience to get same date of next month for a given date
	 * 
	 * @param pTs Timestamp to work with
	 * @return Timestamp of the desired results, or null if there is any problem
	 */
	public static Timestamp getPreviousDayDate(Timestamp pTs) {
		Timestamp retTs = null;

		if (pTs != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date(pTs.getTime()));
			c1.add(Calendar.DATE, -1);

			retTs = new Timestamp(c1.getTimeInMillis());
		}
		return retTs;
	}

	public static Timestamp getPreviousWeekSunday(Timestamp dt) {
		if (dt == null) {
			dt = getSysdate();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(dt.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		c.add(Calendar.DATE, -7);
		int dy = c.get(Calendar.DAY_OF_WEEK);

		while (dy != 1) {
			c.add(Calendar.DATE, -1);
			dy = c.get(Calendar.DAY_OF_WEEK);
		}
		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp getPreviousWeekSaturday(Timestamp dt) {
		if (dt == null) {
			dt = getSysdate();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(dt.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		c.add(Calendar.DATE, -7);
		int dy = c.get(Calendar.DAY_OF_WEEK);

		while (dy != 7) {
			c.add(Calendar.DATE, 1);
			dy = c.get(Calendar.DAY_OF_WEEK);
		}
		return new Timestamp(c.getTime().getTime());
	}

	public static Timestamp getTimeStamp(int year, int month, int day) {

		Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, day);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static String getQuarterOfMonth(String month) {
		String qtr = null;
		List<String> firstQuarter = new ArrayList<String>();
		firstQuarter.add("01");
		firstQuarter.add("02");
		firstQuarter.add("03");

		List<String> secondQuarter = new ArrayList<String>();
		secondQuarter.add("04");
		secondQuarter.add("05");
		secondQuarter.add("06");

		List<String> thirdQuarter = new ArrayList<String>();
		thirdQuarter.add("07");
		thirdQuarter.add("08");
		thirdQuarter.add("09");

		List<String> fourthQuarter = new ArrayList<String>();
		fourthQuarter.add("10");
		fourthQuarter.add("11");
		fourthQuarter.add("12");

		if (firstQuarter.contains(month)) {
			qtr = "1";
		} else if (secondQuarter.contains(month)) {
			qtr = "2";
		} else if (thirdQuarter.contains(month)) {
			qtr = "3";
		} else if (fourthQuarter.contains(month)) {
			qtr = "4";
		}
		return qtr;
	}

	public static Timestamp getNextWeekSaturday(Timestamp dt) {
		if (dt == null) {
			dt = getSysdate();
		}

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(dt.getTime()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		c.add(Calendar.DATE, 7);
		int dy = c.get(Calendar.DAY_OF_WEEK);

		while (dy != 7) {
			c.add(Calendar.DATE, 1);
			dy = c.get(Calendar.DAY_OF_WEEK);
		}
		return new Timestamp(c.getTime().getTime());
	}
}
