package com.icebreak.util.lang.util;

public interface Converter extends net.sf.cglib.core.Converter {
	/**
	 * 
	 * @param value 属性值
	 * @param target 属性类型
	 * @param context setter方法名
	 * @return
	 * @see net.sf.cglib.core.Converter#convert(java.lang.Object,
	 *      java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Object value, Class target, Object context);
}
