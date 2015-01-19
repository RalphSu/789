package com.icebreak.p2p.activity.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.icebreak.p2p.activity.ActivityFactory;
import com.icebreak.p2p.activity.ActivityHandler;
import com.icebreak.p2p.activity.ActivityService;
import com.icebreak.p2p.daointerface.ActivityDao;
import com.icebreak.p2p.dataobject.ActivityInfo;

public class ActivityServiceImpl implements ActivityService, InitializingBean, DisposableBean{
	
	private ActivityDao activityDao;
	
	public ActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public ActivityFactory factory = null;
	
	List<ActivityHandler> activities = new LinkedList<ActivityHandler>();

	@Override
	public void userRegistered(int userId, boolean isReference) {
		for (ActivityHandler activity : activities) {		
			activity.userRegistered(this, isReference, userId);
		}
	}

	@Override
	public void userActivated(int userId) {
		for (ActivityHandler activity : activities) {		
			activity.userActivated(this, userId);
		}
	}

	@Override
	public void userWithDraw() {
		for (ActivityHandler activity : activities) {		
			activity.userWithDraw(this);
		}
	}

	@Override
	public void destroy() throws Exception {
		activities.clear();		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<ActivityInfo> types = getActivities();
		for (ActivityInfo type : types) {
			activities.add(factory.create(type));
		}
	}

	@Override
	public List<ActivityInfo> getActivities() {
		return activityDao.getAll();
	}

}
