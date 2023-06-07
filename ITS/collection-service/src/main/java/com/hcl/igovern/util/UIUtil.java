package com.hcl.igovern.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UIUtil {
	
	private UIUtil() {}

	public static final double roundUp(double d, int places) {
		return BigDecimal.valueOf(d).setScale(places, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static final double roundDouble(double d, int places) {
		return Math.round(d * Math.pow(10, (double) places))
				/ Math.pow(10, (double) places);
	}

	public static final double roundDown(double d, int places) {
		return BigDecimal.valueOf(d).setScale(places, RoundingMode.DOWN).doubleValue();
	}
}
