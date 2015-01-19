package com.icebreak.util.beans;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FieldTypeScanner {

	private Method writeMethod;

	private Type type;

	private int index;

	private boolean next;

	private Class<?> defaultClass;

	/**
	 * 构造一个空的FieldTypeScanner。
	 */
	public FieldTypeScanner() {

	}

	/**
	 * 使用 {@link String} 作为默认类型构造一个FieldTypeScanner。
	 * 
	 * @param writeMethod 需要扫描字段的写入（set）方法。
	 */
	public FieldTypeScanner(Method writeMethod) {
		this(writeMethod, String.class);
	}

	/**
	 * 使用指定的默认类型构造一个FieldTypeScanner。
	 * 
	 * @param writeMethod 需要扫描字段的写入（set）方法。
	 * @param defaultClass 如果没有扫描到类型信息则使用的默认类型。
	 */
	public FieldTypeScanner(Method writeMethod, Class<?> defaultClass) {
		rest(writeMethod, defaultClass);
	}

	/**
	 * 置当前扫描器为指定状态。
	 * 
	 * @param writeMethod 需要扫描字段的写入（set）方法。
	 * @param defaultClass 如果没有扫描到类型信息则使用的默认类型。
	 */
	public void rest(Method writeMethod, Class<?> defaultClass) {
		this.writeMethod = writeMethod;
		this.defaultClass = defaultClass;
		this.type = null;
		this.next = true;
		this.index = 0;
	}

	/**
	 * 得到 默认类型的 Class 。
	 * 
	 * @return 默认类型的 Class 。
	 */
	public Class<?> getDefaultClass() {
		return defaultClass;
	}

	/**
	 * 跳过下一个扫描。
	 * 
	 * @return 如果之后仍然可以扫描出类型信息返回 true ，否则返回 false 。
	 */
	public boolean skipNext() {
		if (this.writeMethod != null) {
			if (this.index == 0) {
				// 如果是第一次解析，那么直接从方法的参数中取
				this.type = this.writeMethod.getGenericParameterTypes()[0];
			}
			this.index++;
			findType(this.type);
			return hasNext();
		}
		return false;
	}

	/**
	 * 判断在下一次调用 {@link #getNextType()} （包括重载方法）或 {@link #skipNext()}
	 * 方法之前是否存在扫描出的类型信息。
	 * 
	 * @return 如果在下一次调用 {@link #getNextType()} （包括重载方法）或 {@link #skipNext()}
	 *         方法之前存在扫描出的类型信息返回 true ，否则返回 false 。
	 */
	public boolean hasNext() {
		return this.next;
	}

	/**
	 * 执行下一个扫描。如果存在多个泛型类型，则取第一个。
	 * 
	 * @return 扫描出的类型信息或者默认类型信息。
	 */
	public Class<?> getNextType() {
		return getNextType0(null, 0);
	}

	/**
	 * 执行下一个扫描。
	 * 
	 * @param i 如果存在多个泛型类型，则取值的下标。
	 * @return 扫描出的类型信息或者默认类型信息。
	 */
	public Class<?> getNextType(int i) {
		return getNextType0(null, i);
	}

	/**
	 * 执行下一个扫描，在当前扫描没有扫描到类型信息时使用 type 指定的类型完成扫描。
	 * <p>
	 * 如果 type 不为 null
	 * 则扫描分两个阶段。在阶段一仍然使用扫描器内部的状态信息进行扫描，如果扫描到类型信息则返回扫描到的类型信息，否则进入第二阶段。第二阶段使用 type
	 * 指定的类型信息进行扫描，如果扫描到类型信息则返回扫描到的类型信息，否则返回默认类型。在整个过程中，只要 type 不为 null ，即便
	 * {@link #hasNext()} 也会使用 type 指定的类型进行扫描。
	 * 
	 * @param type 指定的类型的 {@link Class} 对象。
	 * @param i 如果存在多个泛型类型，则取值的下标。
	 * @return 扫描出的类型信息或者默认类型信息。
	 */
	public Class<?> getNextType(Type type, int i) {
		return getNextType0(type, i);
	}

	private Class<?> getNextType0(Type type, int i) {
		if (type instanceof Class<?>) {
			this.index++;
			return (Class<?>) type;
		}
		if (!this.next && type == null) {
			return this.defaultClass;
		}
		if (this.index == 0) {
			// 如果是第一次解析，那么直接从方法的参数中取
			this.type = this.writeMethod.getGenericParameterTypes()[0];
		}
		this.index++;
		if (this.type instanceof ParameterizedType) {
			// 参数化泛型信息
			this.type = ((ParameterizedType) this.type).getActualTypeArguments()[i];
			Class<?> returnValue = findType(this.type);
			if (returnValue != null) {
				return returnValue;
			}
		} else if (this.type instanceof GenericArrayType) {
			// 数组泛型信息
			this.type = ((GenericArrayType) this.type).getGenericComponentType();
			Class<?> returnValue = findType(this.type);
			if (returnValue != null) {
				return returnValue;
			}
		}
		// 如果执行到这里，则表示原有的泛型信息不足以取出正确的类型，那么就从方法传入的 Type 中取
		if (type == null) {
			this.next = false;
			return this.defaultClass;
		}
		this.type = type;
		return getNextType(null, 0);
	}

	private Class<?> findType(Type type) {
		if (type instanceof Class<?>) {
			// 如果为 Class ，则表示没有更多的泛型信息
			this.next = false;
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return returnValue(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			return returnValue(((GenericArrayType) type).getGenericComponentType());
		}
		return null;
	}

	private Class<?> returnValue(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else {
			return this.defaultClass;
		}
	}
}
