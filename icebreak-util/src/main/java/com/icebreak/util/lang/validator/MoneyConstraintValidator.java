package com.icebreak.util.lang.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.icebreak.util.lang.exception.Exceptions;
import com.icebreak.util.lang.util.money.Money;

public class MoneyConstraintValidator implements ConstraintValidator<MoneyConstraint, Money> {
	
	private long min;
	private long max;
	private boolean nullable;
	
	@Override
	public void initialize(MoneyConstraint constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
		nullable = constraintAnnotation.nullable();
		if (min < 0) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("金额约束最小值不能小于零");
		}
		if (max <= min) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("金额约束最大值必须大于最小值");
		}
	}
	
	@Override
	public boolean isValid(Money value, ConstraintValidatorContext context) {
		if (value == null) {
			if (!nullable) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
					"{com.icebreak.util.lang.validator.Money.message.notNull}")
					.addConstraintViolation();
			}
			return nullable;
		}
		boolean isValid = value.getCent() >= min && value.getCent() <= max;
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
				"{com.icebreak.util.lang.validator.Money.message}").addConstraintViolation();
		}
		return isValid;
	}
}
