package com.hcl.igovern.validation;

import org.apache.commons.lang.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputLongValidator implements ConstraintValidator<InputLongConstraint, String> {

	@Override
	public void initialize(InputLongConstraint inputField) {
		// Do nothing.
	}

	@Override
	public boolean isValid(String inputField, ConstraintValidatorContext cxt) {
		return StringUtils.isNumeric(inputField);
	}
}
