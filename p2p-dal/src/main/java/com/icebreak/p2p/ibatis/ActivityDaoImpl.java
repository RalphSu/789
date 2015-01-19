package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ActivityDao;
import com.icebreak.p2p.dataobject.ActivityDetail;
import com.icebreak.p2p.dataobject.ActivityGiftCount;
import com.icebreak.p2p.dataobject.ActivityInfo;
import com.icebreak.p2p.dataobject.GiftInfo;
import com.icebreak.p2p.dataobject.GiftUseRecord;

public class ActivityDaoImpl extends SqlMapClientDaoSupport implements ActivityDao {

	@Override
	public void insert(ActivityInfo activityInfo) {
		getSqlMapClientTemplate().insert("EL-ACTIVITYINFO-INSERT", activityInfo);

	}
	
	@Override
	public int upate(ActivityInfo activityInfo){
		return getSqlMapClientTemplate().update("EL-ACTIVITYINFO-UPDATE", activityInfo);
	}
	
	@Override
	public ActivityInfo getActivityByBaseId(long tblBaseId){
		return (ActivityInfo)getSqlMapClientTemplate().queryForObject("EL-ACTIVITYINFO-QUERY-ACTIVITY", tblBaseId);
	}

	@Override
	public List<ActivityInfo> getAll() {

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityInfo> queryActivityList(Map<String,Object> condition){
		return getSqlMapClientTemplate().queryForList("EL-ACTIVITYINFO-QUERY-LIST", condition);
	}
	
	@Override
	public long queryActivityCount(Map<String,Object> condition){
		return (Long)getSqlMapClientTemplate().queryForObject("EL-ACTIVITYINFO-QUERY-COUNT", condition);
	}
	
	/*
	 * GiftInfo
	 */
	@Override
	public void insertGiftInfo(GiftInfo giftInfo) {
		getSqlMapClientTemplate().insert("EL-GIFTINFO-INSERT", giftInfo);
	}
	@Override
	public void updateGiftInfo(GiftInfo giftInfo) {
		getSqlMapClientTemplate().update("EL-GIFTINFO-UPDATE", giftInfo);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<GiftInfo> getGiftInfos(Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList(
				"EL-GIFTINFO-QUERY-BYCONDITION-WITHLIMIT", condition);
	}
	@Override
	public GiftInfo getGift(long giftId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tblBaseId", giftId);
		@SuppressWarnings("unchecked")
		List<GiftInfo> gifts = getSqlMapClientTemplate().queryForList(
				"EL-GIFTINFO-QUERY-BYCONDITION", condition);
		return (GiftInfo) (gifts.size() > 0 ? gifts.get(0) : null);
	}
	@Override
	public Long getGiftCount(Map<String, Object> conditions) {
		return (Long) getSqlMapClientTemplate().queryForObject("EL-GIFTINFO-QUERY-COUNT", conditions);
	}

	@Override
	public long queryGiftUsedCount(Map<String,Object> condition){
		return (Long)getSqlMapClientTemplate().queryForObject("EL-GIFTUSERECORD-QUERY-COUNT", condition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GiftUseRecord> queryGiftUsedList(Map<String,Object> condition){
		return getSqlMapClientTemplate().queryForList("EL-GIFTUSERECORD-LIST", condition);
	}
	@Override
	public GiftUseRecord queryGiftUseRecordByBizNo(String bizNumber){
		return (GiftUseRecord)getSqlMapClientTemplate().queryForObject("EL-GIFTUSERECORD-QUERY", bizNumber);
	}
	
	@Override
	public int updateGiftUseRcord(GiftUseRecord giftUseRecord){
		return getSqlMapClientTemplate().update("EL-GIFTUSERECORD-UPDATE", giftUseRecord);
	}

	@Override
	public void insertGiftUseRecord(GiftUseRecord record) {


	}

	@Override
	public List<GiftUseRecord> getGiftUseRecordByUserId(int userId) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getActivityGiftCount(Map<String, Object> conditions) {
		
		return (Long)getSqlMapClientTemplate().queryForObject("EL-ACTIVITYGIFTCOUNT-SUM", conditions);
	}

	@Override
	public long insertActivityDetail(ActivityDetail activityDetail) {
		getSqlMapClientTemplate().insert("EL-ACTIVITYDETAIL-INSERT", activityDetail);
		return 1;
	}

	@Override
	public ActivityInfo queryActivityByName(String activityName) {
		return (ActivityInfo) getSqlMapClientTemplate().queryForObject("EL-ACTIVITYINFO-QUERY-BYNAME", activityName);
	}

	@Override
	public List<GiftInfo> queryGiftByConditions(Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("EL-GIFTINFO-QUERY-BYCONDITION", conditions);
	}
	@Override
	public void addGiftUseRecord(GiftUseRecord giftUseRecord) {
		getSqlMapClientTemplate().insert("EL-GIFTUSERECORD-INSERT", giftUseRecord);
	}

	@Override
	public List<ActivityGiftCount> getActivityGiftCountListByConditions(
			Map<String, Object> conditions) {
	
		return getSqlMapClientTemplate().queryForList("EL-ACTIVITYGIFTCOUNT-QUERY-LIST", conditions);
	}

	@Override
	public int updateActivityGiftCount(ActivityGiftCount activityGiftCount) {
		return getSqlMapClientTemplate().update("EL-ACTIVITYGIFTCOUNT-UPDATE", activityGiftCount);
	}

	@Override
	public long queryActivityDetailCount(Map<String, Object> conditions) {
		return (Long)getSqlMapClientTemplate().queryForObject("EL-ACTIVITYDETAIL-QUERY-COUNT", conditions);
	}

	@Override
	public List<ActivityDetail> queryActivityDetailList(
			Map<String, Object> conditions) {
		return getSqlMapClientTemplate().queryForList("EL-ACTIVITYDETAIL-QUERY-LIST", conditions);
	}

	@Override
	public ActivityDetail getActivityDetailByBaseId(long giftId) {
		
		return  (ActivityDetail) getSqlMapClientTemplate().queryForObject("EL-ACTIVITYDETAIL-QUERY", giftId);
	}

	@Override
	public int updateActivityDetail(ActivityDetail detail) {
		
		return (Integer) getSqlMapClientTemplate().update("EL-ACTIVITYDETAIL-UPDATE", detail);
	}

	@Override
	public long countUserGift(Map<String, Object> giftUserConditions) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("EL-ACTIVITYGIFTCOUNT-QUERY-COUNT", giftUserConditions);
	}

	@Override
	public void insertActivityGiftCount(ActivityGiftCount act) {
		getSqlMapClientTemplate().insert("EL-ACTIVITYGIFTCOUNT-INSERT",  act);
		
	}

	@Override
	public long checkIsUserJoinActivity(Map<String, Object> conditions) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("EL-ACTIVITYDETAIL-QUERY-ISUSERJOINACTIVITY", conditions);
	}

}
