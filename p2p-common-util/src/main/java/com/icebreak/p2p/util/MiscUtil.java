package com.icebreak.p2p.util;

import com.icebreak.util.lang.util.BeanCopyUtils;
import com.icebreak.util.lang.util.Converter;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class MiscUtil {
	private static final Logger logger = LoggerFactory.getLogger(MiscUtil.class);
	
	private static Converter c = BeanCopyUtils
		.newIgnorePropertiesConverter(new String[] { "bizNo,biz_no" });
	
	public static void copyPoObject(Object po, Object srcPo) {
		copyPoObject(po, srcPo, false);
	}
	
	public static void copyPoObject(Object po, Object srcPo, boolean isNotCopyNull) {
		if (srcPo == null || po == null)
			return;
		//		BeanCopyUtils.copy(srcPo, po, c);
		
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(srcPo);
		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor pd = pds[i];
			
			PropertyDescriptor[] pds1 = PropertyUtils.getPropertyDescriptors(po);
			for (int j = 0; j < pds1.length; j++) {
				if (pds1[j].getName().equals(pd.getName())) {
					if (pd.getPropertyType().equals(pds1[j].getPropertyType())
						&& pds1[j].getWriteMethod() != null) {
						try {
							Object temp = PropertyUtils.getProperty(srcPo, pd.getName());
							if (isNotCopyNull && temp != null) {
								PropertyUtils.setProperty(po, pd.getName(), temp);
							} else if (!isNotCopyNull)
								PropertyUtils.setProperty(po, pd.getName(), temp);
							
						} catch (IllegalAccessException e) {
							logger.error("IllegalAccessException err", e);
						} catch (InvocationTargetException e) {
							logger.error("InvocationTargetException err", e);
						} catch (NoSuchMethodException e) {
							logger.error("NoSuchMethodException err", e);
						}
					}
					continue;
					
				}
				
			}
		}
		
	}
	
	public static <T, V> void convertList(List<T> listFrom, List<V> listTo, Class<V> classz) {
		if (listFrom != null) {
			for (T item : listFrom) {
				V vItem = null;
				try {
					vItem = classz.newInstance();
				} catch (InstantiationException e) {
					logger.error(e.getLocalizedMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
				if (vItem != null) {
					MiscUtil.copyPoObject(vItem, item);
					listTo.add(vItem);
				}
				
			}
		}
	}
	
	public static HashMap<String, Object> parseJSON(String json) {
		if (logger.isInfoEnabled())
			logger.info("..........parseJSON....json:" + json);
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			HashMap<String, Object> o = (HashMap<String, Object>) new JSONParser().parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static JSONArray parseJSONArray(String json) {
		if (logger.isInfoEnabled())
			logger.info("..........parseJSON....json:" + json);
		try {
			if (json == null)
				return null;
			@SuppressWarnings("unchecked")
			JSONArray o = (JSONArray) new JSONParser().parse(json);
			return o;
		} catch (ParseException e) {
			logger.error("JSONParser json is Error", e);
		}
		return null;
	}
	
	public static String escape(String src) {
		if (StringUtil.isEmpty(src))
			return "";
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	public static String addJsonString(String memo, String memoKey, String memoValue) {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(memo))
			jsonMap = parseJSON(memo);
		
		jsonMap.put(memoKey, memoValue);
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(jsonMap);
		return jsonObject.toJSONString();
	}
	
	public static <T> List<T> toList(T obj) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2, T obj3) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		return temp;
	}
	
	public static <T> List<T> toList(T obj, T obj1, T obj2, T obj3, T obj4) {
		List<T> temp = new ArrayList<T>();
		temp.add(obj);
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		temp.add(obj4);
		return temp;
	}
	
	public static List<String> toList(String obj1, String obj2) {
		List<String> temp = new ArrayList<String>();
		temp.add(obj1);
		temp.add(obj2);
		return temp;
	}
	
	public static List<String> toList(String obj1, String obj2, String obj3) {
		List<String> temp = new ArrayList<String>();
		temp.add(obj1);
		temp.add(obj2);
		temp.add(obj3);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1,
											String key2, Object obj2) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		temp.put(key2, obj2);
		return temp;
	}
	
	public static Map<String, Object> toMap(String key, Object obj, String key1, Object obj1,
											String key2, Object obj2, String key3, Object obj3) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(key, obj);
		temp.put(key1, obj1);
		temp.put(key2, obj2);
		temp.put(key3, obj3);
		return temp;
	}
	
	public static List<Map<String, Object>> getMapListByPoList(List<?> poList) {
		List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
		if (ListUtil.isEmpty(poList))
			return mapList;
		for (Iterator<?> it = poList.iterator(); it.hasNext();) {
			Object po = it.next();
			Map<String, Object> itemMap = covertPoToMap(po);
			mapList.add(itemMap);
		}
		return mapList;
	}
	
	public static Map<String, Object> covertPoToMap(Object po) {
		String propertyName = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					
					dataMap.put(propertyName, PropertyUtils.getProperty(po, propertyName));
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	public static Map<String, String> covertPoToMapNoNullValue(Object po) {
		String propertyName = null;
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					Object object = PropertyUtils.getProperty(po, propertyName);
					if (object != null) {
						dataMap.put(propertyName, object.toString());
					}
					
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> covertPoToMapJson(Object po) {
		String propertyName = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (po == null)
				return null;
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(po);
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor pd = pds[i];
				propertyName = pd.getName();
				if (pd.getWriteMethod() != null) {
					Object tempValue = PropertyUtils.getProperty(po, propertyName);
					if (tempValue instanceof Enum) {
						dataMap.put(propertyName, ((Enum) tempValue).name());
					} else {
						dataMap.put(propertyName, PropertyUtils.getProperty(po, propertyName));
					}
					
				}
				
			}
		} catch (IllegalAccessException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		} catch (NoSuchMethodException e) {
			logger.error("covertPoToMap error field [" + propertyName + "]", e);
		}
		return dataMap;
	}
	
	public static void setDOPropertyByDbMap(Map<String, Object> dbMap, Object entityDO) {
		
		Iterator<Map.Entry<String, Object>> iterator = dbMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String propertyName = StringUtil.toCamelCase(entry.getKey());
			try {
				Object temp = entry.getValue();
				if (entry.getValue() != null) {
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityDO,
						propertyName);
					if (pd == null)
						continue;
					if (pd.getPropertyType().equals(String.class)) {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					} else if (pd.getPropertyType() == Integer.class
								|| pd.getPropertyType() == int.class
								|| pd.getPropertyType() == Long.class
								|| pd.getPropertyType() == long.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
					} else if (pd.getPropertyType() == Double.class
								|| pd.getPropertyType() == double.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
						
					} else if (pd.getPropertyType() == Money.class) {
						Money money = new Money();
						money.setCent(((BigDecimal) temp).longValue());
						
						BeanUtils.setProperty(entityDO, pd.getName(), money);
						
					} else {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					}
				}
				
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException err", e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException err", e);
			} catch (NoSuchMethodException e) {
				logger.error("NoSuchMethodException err", e);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
	}
	
	public static void setInfoPropertyByMap(Map<String, Object> dataMap, Object entityDO) {
		
		Iterator<Map.Entry<String, Object>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String propertyName = entry.getKey();
			try {
				Object temp = entry.getValue();
				if (entry.getValue() != null) {
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityDO,
						propertyName);
					if (pd == null)
						continue;
					if (pd.getPropertyType().equals(String.class)) {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					} else if (pd.getPropertyType() == Integer.class
								|| pd.getPropertyType() == int.class
								|| pd.getPropertyType() == Long.class
								|| pd.getPropertyType() == long.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
					} else if (pd.getPropertyType() == Double.class
								|| pd.getPropertyType() == double.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
						
					} else if (pd.getPropertyType() == Money.class) {
						Money money = new Money();
						if (temp instanceof BigDecimal) {
							money.setCent(((BigDecimal) temp).longValue());
						} else if (temp instanceof Number) {
							money.setAmount(new BigDecimal(((Number) temp).doubleValue()));
						} else if (temp instanceof String) {
							money.setAmount(new BigDecimal((String) temp));
						}
						BeanUtils.setProperty(entityDO, pd.getName(), money);
						
					} else if (pd.getPropertyType() == Date.class) {
						
					} else if (Enum.class.isAssignableFrom(pd.getPropertyType())) {
						//不处理Enum类型的属性
						logger.debug("Property :" + pd.getName() + "is sub class of Enum !");
					} else {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					}
				}
				
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException err  propertyName={}", propertyName, e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException err propertyName={}", propertyName, e);
			} catch (NoSuchMethodException e) {
				logger.error("NoSuchMethodException err propertyName={}", propertyName, e);
			} catch (Exception e) {
				logger.error(" propertyName={} is error", propertyName, e);
			}
		}
		
	}
	
	public static void setBeanByMap(Map<String, String> dataMap, Object entityDO) {
		
		Iterator<Map.Entry<String, String>> iterator = dataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String propertyName = entry.getKey();
			try {
				Object temp = entry.getValue();
				if (entry.getValue() != null) {
					PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(entityDO,
							propertyName);
					if (pd == null)
						continue;
					if (pd.getPropertyType().equals(String.class)) {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					} else if (pd.getPropertyType() == Integer.class
							|| pd.getPropertyType() == int.class
							|| pd.getPropertyType() == Long.class
							|| pd.getPropertyType() == long.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
					} else if (pd.getPropertyType() == Double.class
							|| pd.getPropertyType() == double.class) {
						BeanUtils.setProperty(entityDO, pd.getName(), temp);
						
					} else if (pd.getPropertyType() == Money.class) {
						Money money = new Money();
						if (temp instanceof BigDecimal) {
							money.setCent(((BigDecimal) temp).longValue());
						} else if (temp instanceof Number) {
							money.setAmount(new BigDecimal(((Number) temp).doubleValue()));
						} else if (temp instanceof String) {
							money.setAmount(new BigDecimal((String) temp));
						}
						BeanUtils.setProperty(entityDO, pd.getName(), money);
						
					} else if (pd.getPropertyType() == Date.class) {
						
					} else if (Enum.class.isAssignableFrom(pd.getPropertyType())) {
						//不处理Enum类型的属性
						logger.debug("Property :" + pd.getName() + "is sub class of Enum !");
					} else {
						PropertyUtils.setProperty(entityDO, propertyName, temp);
					}
				}
				
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException err  propertyName={}", propertyName, e);
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException err propertyName={}", propertyName, e);
			} catch (NoSuchMethodException e) {
				logger.error("NoSuchMethodException err propertyName={}", propertyName, e);
			} catch (Exception e) {
				logger.error(" propertyName={} is error", propertyName, e);
			}
		}
		
	}

}
