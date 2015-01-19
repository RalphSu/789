package com.icebreak.p2p.activity;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.icebreak.p2p.dataobject.ActivityInfo;

public class ActivityFactory implements InitializingBean, DisposableBean {
	
	Logger																		LOG				= LoggerFactory
																									.getLogger(ActivityFactory.class);
	
	public Map<ActivityHandler.Type, Constructor<? extends ActivityHandler>>	constructors	= new HashMap<ActivityHandler.Type, Constructor<? extends ActivityHandler>>();
	
	@Override
	public void destroy() throws Exception {
		constructors.clear();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	public ActivityHandler create(ActivityInfo type) {
		ActivityHandler.Type typeEnum = ActivityHandler.Type.valueOf(type.getActivityName());
		if (typeEnum == null) {
			LOG.error("Type of activity {} not found", type.getActivityName());
			throw new RuntimeException("Enum Type Not Found");
		}
		Constructor<? extends ActivityHandler> cons = constructors.get(typeEnum);
		if (cons == null) {
			LOG.error("Constructor of activity {} not found", type);
			throw new RuntimeException("Constructor Not Found");
		}
		try {
			return cons.newInstance(type);
		} catch (Exception e) {
			LOG.error("create", e);
			LOG.error("Initiation of {} failed", type);
			throw new RuntimeException("Initiation Failed");
		}
	}
	
}
