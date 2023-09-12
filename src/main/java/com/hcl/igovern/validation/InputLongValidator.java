package com.hcl.igovern.validation;

import org.apache.commons.lang.StringUtils;

import com.hcl.igovern.exception.BusinessException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputLongValidator implements ConstraintValidator<InputLongConstraint, String> {

	@Override
	public void initialize(InputLongConstraint inputField) {
		// Do nothing.
	}

	@Override
	public boolean isValid(String inputField, ConstraintValidatorContext cxt) {
		boolean validInputField = false;
		try {
			validInputField = StringUtils.isNumeric(inputField);
		} catch (Exception e) {
			throw new BusinessException("ERR_CODE", "Invalid input received.");
		}
		return validInputField;
	}
}
