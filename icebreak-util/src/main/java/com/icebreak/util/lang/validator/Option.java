
package com.icebreak.util.lang.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = { OptionValidator.class })
@Documented
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
	
	String message() default "不正确的可选值";
	
	int[] value() default {};
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
