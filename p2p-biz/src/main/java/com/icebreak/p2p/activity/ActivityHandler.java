package com.icebreak.p2p.activity;

import com.icebreak.p2p.dataobject.ActivityInfo;

public class ActivityHandler {
	
	public enum Type {
		OBN("OldBringInNewActivity");		
		public String name = null;	
		private Type(String name) {
			this.name = name;
		}
	}
	
	protected Type type = null;
	protected ActivityInfo data = null;
	
	public ActivityHandler(ActivityInfo data) {
		this.data = data;
	}

	public void userRegistered(ActivityService activityService, boolean isReference, int userId) {
	}

	public void userActivated(ActivityService activityService, int userId) {
	}

	public void userWithDraw(ActivityService activityService) {
	}	
}
