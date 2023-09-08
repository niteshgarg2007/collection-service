package com.hcl.igovern.util;

import org.apache.commons.lang.StringUtils;

import com.hcl.igovern.exception.BusinessException;

public class ParamValidateUtil {
	private ParamValidateUtil() {			
	}
	
	public static Long validateLongData(String inputData) {
		Long outputData = null;
		if (inputData != null) {
			if(StringUtils.isNumeric(inputData)) {
				outputData = Long.parseLong(inputData);
			} else {
				throw new BusinessException("ERR_CODE", "Please provide the valid input parameter.");
			}
		}
		return outputData;
	}
}
