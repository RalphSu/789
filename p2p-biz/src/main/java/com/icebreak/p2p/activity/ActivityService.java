package com.icebreak.p2p.activity;

import java.util.List;

import com.icebreak.p2p.daointerface.ActivityDao;
import com.icebreak.p2p.dataobject.ActivityInfo;


public interface ActivityService {
	public ActivityDao getActivityDao();
	
	public void userRegistered(int userId, boolean isReference);
	public void userActivated(int userId);	
	public void userWithDraw();
	
	public List<ActivityInfo> getActivities();
	
}
