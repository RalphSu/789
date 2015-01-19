package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.dataobject.ActivityDetail;
import com.icebreak.p2p.dataobject.ActivityGiftCount;
import com.icebreak.p2p.dataobject.ActivityInfo;
import com.icebreak.p2p.dataobject.GiftInfo;
import com.icebreak.p2p.dataobject.GiftUseRecord;

public interface ActivityDao {

	/*#############liu start***********************/
	/*
	 * ActivityClass
	 */
	public void insert(ActivityInfo activityInfo);
	/**
	 * 更新活动
	 * @param activityInfo
	 * @return
	 */
	public int upate(ActivityInfo activityInfo);
	/**
	 * 查询ActivityInfo
	 * @param tblBaseId
	 * @return
	 */
	public ActivityInfo getActivityByBaseId(long tblBaseId);
	public List<ActivityInfo> getAll();
	/**
	 * 根据条件查询活动
	 * @param condition
	 * @return
	 */
	public List<ActivityInfo> queryActivityList(Map<String,Object> condition);
	/**
	 * 根据条件查询记录数量
	 * @param condition
	 * @return
	 */
	public long queryActivityCount(Map<String,Object> condition);
	/**
	 * 查询礼品使用记录数
	 * @param condition
	 * @return
	 */
	public long queryGiftUsedCount(Map<String,Object> condition);
	/**
	 *  查询礼品使用记录信息
	 * @param condition
	 * @return
	 */
	public List<GiftUseRecord> queryGiftUsedList(Map<String,Object> condition);
	/**
	 * 根据bizNumber查询ActivityInfo
	 * @param bizNumber
	 * @return
	 */
	public GiftUseRecord queryGiftUseRecordByBizNo(String bizNumber);
	/**
	 * 更新记录
	 * @param giftUseRecord
	 * @return
	 */
	public int updateGiftUseRcord(GiftUseRecord giftUseRecord);
	/*######################end#######################################/
	
	/*
	 * GiftInfo
	 */
	public void insertGiftInfo(GiftInfo giftInfo);
	public void updateGiftInfo(GiftInfo giftInfo);
	public List<GiftInfo> getGiftInfos(Map<String, Object> condition);
	public GiftInfo getGift(long giftId);
	public Long getGiftCount(Map<String, Object> conditions);
	/*
	 * ActivityGiftUseRecord
	 */
	public void insertGiftUseRecord(GiftUseRecord record);
	public List<GiftUseRecord> getGiftUseRecordByUserId(int userId);
	public long getActivityGiftCount(Map<String, Object> conditions);
	public long insertActivityDetail(ActivityDetail activityDetail);
	public ActivityInfo queryActivityByName(String activityName);
	public List<GiftInfo> queryGiftByConditions(Map<String, Object> conditions);
	public void addGiftUseRecord(GiftUseRecord giftUseRecord);
	public List<ActivityGiftCount> getActivityGiftCountListByConditions(
			Map<String, Object> conditions);
	public int updateActivityGiftCount(ActivityGiftCount activityGiftCount);
	public long queryActivityDetailCount(Map<String, Object> conditions);
	public List<ActivityDetail> queryActivityDetailList(
			Map<String, Object> conditions);
	public ActivityDetail getActivityDetailByBaseId(long giftId);
	public int updateActivityDetail(ActivityDetail detail);
	public long countUserGift(Map<String, Object> giftUserConditions);
	public void insertActivityGiftCount(ActivityGiftCount act);
	public long checkIsUserJoinActivity(Map<String, Object> conditions);
}
