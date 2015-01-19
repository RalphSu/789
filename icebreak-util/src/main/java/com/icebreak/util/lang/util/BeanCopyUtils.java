package com.icebreak.util.lang.util;

import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class BeanCopyUtils {
	
	private static final Map<Key, BeanCopier> copierMap = Maps.newConcurrentMap();
	
	/**
	 * 属性复制
	 * @param from 源对象
	 * @param to 目标对象
	 */
	public static void copy(Object from, Object to) {
		copy(from, to, (Converter) null);
	}
	
	/**
	 * 属性复制<br/>
	 * 不提供
	 * {@link org.springframework.beans.BeanUtils#copyProperties(Object, Object, String[])}
	 * 方法，因为用cglib来处理性能不是太好。<br/>
	 * 替代方式是可以通过静态方法newIgnorePropertiesConverter来生成忽略某些属性的Converter，
	 * 请缓存住converter
	 * @param from 源对象
	 * @param to 目标对象
	 * @param converter 属性转换器,可以用来处理某个属性值
	 */
	public static void copy(Object from, Object to, Converter converter) {
		Assert.notNull(from, "源对象不能为空");
		Assert.notNull(to, "目标对象不能为空");
		boolean useConverter = converter != null;
		Key key = getKey(from, to, useConverter);
		if (!copierMap.containsKey(key)) {
			synchronized (BeanCopyUtils.class) {
				if (!copierMap.containsKey(key)) {
					BeanCopier copy = BeanCopier.create(from.getClass(), to.getClass(),
						useConverter);
					copierMap.put(key, copy);
				}
			}
		}
		copy(from, to, converter, key);
	}
	
	/**
	 * 生成忽略某些属性的converter
	 * 
	 * @param ignoreProperties
	 * @return
	 */
	
	public static Converter newIgnorePropertiesConverter(String[] ignoreProperties) {
		final Set<String> set = convertPropertiesToSetter(ignoreProperties);
		return new Converter() {
			@Override
			public Object convert(Object value, @SuppressWarnings("rawtypes") Class target,
									Object context) {
				if (set.contains(context)) {
					return null;
				}
				return value;
			}
		};
	}
	
	static Set<String> convertPropertiesToSetter(String[] ignoreProperties) {
		Set<String> set = Sets.newHashSet();
		for (String p : ignoreProperties) {
			if (StringUtils.isBlank(p)) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("set");
			sb.append(Character.toUpperCase(p.charAt(0)));
			sb.append(p.substring(1, p.length()));
			set.add(sb.toString());
		}
		return set;
	}
	
	private static void copy(Object from, Object to, Converter converter, Key key) {
		BeanCopier copy = copierMap.get(key);
		copy.copy(from, to, converter);
	}
	
	private static Key getKey(Object from, Object to, boolean useConverter) {
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
		return new Key(fromClass, toClass, useConverter);
	}
	
	private static class Key {
		private Class<?> fromClass;
		private Class<?> toClass;
		private boolean useConverter;
		
		public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter) {
			super();
			this.fromClass = fromClass;
			this.toClass = toClass;
			this.useConverter = useConverter;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fromClass == null) ? 0 : fromClass.hashCode());
			result = prime * result + ((toClass == null) ? 0 : toClass.hashCode());
			result = prime * result + (useConverter ? 1231 : 1237);
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (fromClass == null) {
				if (other.fromClass != null)
					return false;
			} else if (!fromClass.equals(other.fromClass))
				return false;
			if (toClass == null) {
				if (other.toClass != null)
					return false;
			} else if (!toClass.equals(other.toClass))
				return false;
			if (useConverter != other.useConverter)
				return false;
			return true;
		}
		
	}
}
