package com.icebreak.util.lang.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { MoneyConstraintValidator.class })
public @interface MoneyConstraint {
	
	String message() default "{com.icebreak.util.lang.validator.Money.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * 最小值 <br/>
	 * 单位：分 <br/>
	 * 在校验时会包含次值
	 * @return
	 */
	long min() default 0;
	
	/**
	 * 最大值， <br/>
	 * 单位：分 <br/>
	 * 在校验时会包含次值
	 * @return
	 */
	long max() default Long.MAX_VALUE;
	
	/**
	 * 是否可以为空
	 * @return
	 */
	boolean nullable() default false;
	
}
