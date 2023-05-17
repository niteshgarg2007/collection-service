package com.hcl.igovern.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UIUtil {

	public static final double roundUp(double d, int places) {
		return BigDecimal.valueOf(d).setScale(places, RoundingMode.UP).doubleValue();
	}
	
//	public static final Double roundDouble(Double d, int places) {
//
//		return new Double(Math.round(d.doubleValue()
//				* Math.pow(10, (double) places))
//				/ Math.pow(10, (double) places));
//	}

	public static final double roundDouble(double d, int places) {

		return Math.round(d * Math.pow(10, (double) places))
				/ Math.pow(10, (double) places);
	}

	public static final double roundDown(double d, int places) {
		return BigDecimal.valueOf(d).setScale(places, RoundingMode.DOWN).doubleValue();
	}
}
