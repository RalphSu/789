package com.oms.util;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.collections.map.LRUMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class JAXBContextUtil {
	private static final Logger log = LogManager.getLogger(JAXBContextUtil.class);
	@SuppressWarnings("unchecked")
	private static final Map<Class<?>, JAXBContext> cache = new LRUMap(20);//
	
	public static JAXBContext get(Class<?> clazz){
		if(cache.get(clazz) == null){
			set(clazz);
		}
		return cache.get(clazz);
	}
	
	public static void set(Class<?> clazz){
		synchronized (clazz) {
			if(cache.get(clazz) == null){
				try {
					log.info("JAXBContext is creating " + clazz.getName() + "......");
					JAXBContext jaxbcontext = JAXBContext.newInstance(clazz);
					log.info("Create " + clazz.getName() + " success!");
					cache.put(clazz, jaxbcontext);
				} catch (JAXBException e) {
					log.error("Create " + clazz.getName() + " fail!", e);
				}
			}
		}
	}
	
}
