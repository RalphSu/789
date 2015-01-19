package com.icebreak.util.beans;

import java.lang.reflect.Method;

public interface FieldTypeScannerProvider {

	/**
	 * 创建一个字段类型扫描器。
	 * 
	 * @param beanClass 需要扫描的类的 {@link Class} 对象。
	 * @param writeMethod 为扫描器给定的需要扫描字段的写入（set）方法。
	 * @param defaultClass 为扫描器给定的如果没有扫描到类型信息则使用的默认类型。
	 * @return 合适的字段类型扫描器。
	 */
	FieldTypeScanner newFieldTypeScanner(Class<?> beanClass, Method writeMethod, Class<?> defaultClass);

}
