package com.icebreak.p2p.web.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.icebreak.p2p.util.NumberUtil;
import com.icebreak.util.lang.util.DateUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	public static Map<String, String> getRequestMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String parameterName = en.nextElement();
			map.put(parameterName, request.getParameter(parameterName));
		}
		return map;
	}
	
	public static void setPoPropertyByRequest(Object po, HttpServletRequest request) {
		if (po == null)
			return;
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po.getClass());
		for (int i = 0; i < pds.length; i++) {
			try {
				
				PropertyDescriptor pd = pds[i];
				String propertyName = pd.getName();
				Method method = PropertyUtils.getWriteMethod(pd);
				if (method == null)
					continue;
				Object parameterValue = request.getParameter(propertyName);
				if (parameterValue == null)
					continue;
				setObjectProperty(po, pd, propertyName, parameterValue);
			} catch (IllegalAccessException e) {
				logger.error("setPoPropertyByRequest error", e);
			} catch (InvocationTargetException e) {
				logger.error("setPoPropertyByRequest error", e);
			} catch (NoSuchMethodException e) {
				logger.error("setPoPropertyByRequest error", e);
			}
		}
		
	}
	
	private static void setObjectProperty(Object po, PropertyDescriptor pd, String propertyName,
											Object parameterValue) throws IllegalAccessException,
																	InvocationTargetException,
																	NoSuchMethodException {
		if (po == null)
			return;
		if (propertyName != null) {
			if (pd.getPropertyType().equals(String.class)) {
				PropertyUtils.setProperty(po, propertyName, parameterValue);
			} else if (pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == int.class)
						parameterValue = Integer.valueOf(0);
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Long.class || pd.getPropertyType() == long.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == long.class)
						parameterValue = Long.valueOf(0);
					else
						parameterValue = null;
				} else {
					parameterValue = NumberUtil.parseLong((String) parameterValue, 0);
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Double.class || pd.getPropertyType() == double.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == double.class)
						parameterValue = Double.valueOf(0);
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Date.class) {
				if (StringUtil.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else {
					try {
						if (((String) parameterValue).length() == 10) {
							parameterValue = DateUtil.strToDtSimpleFormat((String) parameterValue);
						} else {
							try {
								parameterValue = DateUtil.getFormat(DateUtil.simple).parse(
									(String) parameterValue);
							} catch (Exception e) {
								logger.info(e.getMessage(), e);
								parameterValue = null;
							}
						}
						
						BeanUtils.setProperty(po, pd.getName(), parameterValue);
					} catch (Exception e) {
						logger.error("parse date parameterValue=[" + parameterValue + "] error", e);
					}
				}
				
			} else if (pd.getPropertyType() == BigDecimal.class) {
				if (StringUtil.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else
					parameterValue = new BigDecimal((String) parameterValue);
				ConvertUtils.register(null, BigDecimal.class);
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
				
			} else if (pd.getPropertyType() == Byte.class || pd.getPropertyType() == byte.class) {
				if ("".equals(parameterValue)) {
					if (pd.getPropertyType() == byte.class)
						parameterValue = Byte.valueOf("0");
					else
						parameterValue = null;
				}
				BeanUtils.setProperty(po, pd.getName(), parameterValue);
			} else if (pd.getPropertyType() == Money.class) {
				if (StringUtil.isEmpty((String) parameterValue)) {
					parameterValue = null;
				} else {
					parameterValue = new Money((String) parameterValue);
					BeanUtils.setProperty(po, pd.getName(), parameterValue);
				}
				
			}
		}
	}
}
