package com.icebreak.util.beans;

import java.lang.reflect.Method;

public class SimpleFieldTypeScannerProvider implements FieldTypeScannerProvider {

	public FieldTypeScanner newFieldTypeScanner(Class<?> beanClass, Method writeMethod, Class<?> defaultClass) {
		return new FieldTypeScanner(writeMethod, defaultClass);
	}

}
