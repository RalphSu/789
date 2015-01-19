package com.icebreak.p2p.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icebreak.p2p.daointerface.ActivityDao;
import com.icebreak.p2p.dataobject.GiftInfo;

@Component
public class GiftManager {
	
	@Autowired
	public ActivityDao	activityDao;
	
	public void insertGiftInfo(GiftInfo data) {
		activityDao.insertGiftInfo(data);
	}
	
	public void updateGiftInfo(GiftInfo data) {
		activityDao.updateGiftInfo(data);
	}
	
	public GiftInfo getGift(long giftId) {
		return activityDao.getGift(giftId);
	}
	
	public long getActivityGiftCount(Map<String, Object> conditions) {
		return activityDao.getActivityGiftCount(conditions);
	}
	
	public long getGiftCount(Map<String, Object> condition) {
		return activityDao.getGiftCount(condition);
	}
	
	public List<GiftInfo> getGiftInfos(Map<String, Object> condition) {
		List<GiftInfo> infos = activityDao.getGiftInfos(condition);
		return infos;
	}
}
