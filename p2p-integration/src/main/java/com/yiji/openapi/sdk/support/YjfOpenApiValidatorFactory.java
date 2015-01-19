package com.yiji.openapi.sdk.support;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
public enum YjfOpenApiValidatorFactory {
	INSTANCE {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		@Override
		public Validator getValidator() {
			return factory.getValidator();
		}
	};
	
	public abstract Validator getValidator();
}
