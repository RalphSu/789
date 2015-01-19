package com.icebreak.p2p.activity;

import java.util.List;
import java.util.Map;

import com.icebreak.p2p.activity.enums.ActivityReturnEnum;
import com.icebreak.p2p.dataobject.ActivityDetail;
import com.icebreak.p2p.dataobject.ActivityGiftCount;
import com.icebreak.p2p.dataobject.ActivityInfo;
import com.icebreak.p2p.dataobject.GiftInfo;
import com.icebreak.p2p.dataobject.GiftUseRecord;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

public interface IActivityService {
	/**
	 * 新增一条活动
	 * @param activityInfo
	 */
	public void addActivityInfo(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 更新一条活动记录
	 * @param activityInfo
	 * @return
	 */
	public int updateActivityInfo(ActivityInfo activityInfo) throws Exception;
	
	/**
	 * 根据tblBaseId查询ActivityInfo
	 * @param tblBaseId
	 * @return
	 */
	public ActivityInfo getActivityInfoByTblBaseId(long tblBaseId);
	
	/**
	 * 根据bizNumber查询ActivityInfo
	 * @param bizNumber
	 * @return
	 */
	public GiftUseRecord getGiftUseRecordByBizNo(String bizNumber);
	
	/**
	 * 更新使用记录
	 * @param giftUseRecord
	 * @return
	 */
	public int updateGiftUsedRecord(GiftUseRecord giftUseRecord) throws Exception;
	
	/**
	 * 根据条件查询获取ActivityInfo信息列表
	 * @param queryActivityOrder
	 * @return
	 */
	public Page<ActivityInfo> queryListActivityInfo(QueryActivityOrder queryActivityOrder,
													PageParam pageParam);
	
	/**根据条件查询获取ActivityInfo数量
	 * @param queryActivityOrder
	 * @return
	 */
	public long getActivityInfoCount(QueryActivityOrder queryActivityOrder);
	
	public Page<GiftUseRecord> getPageGiftUsedRecord(QueryActivityOrder queryActivityOrder,
														PageParam pageParam);
	
	public Page<GiftInfo> getGiftInfos(PageParam pageParam);
	
	public void addGiftInfo(GiftInfo info);
	
	public GiftInfo getGift(long giftId);
	
	public void updateGiftSubmit(GiftInfo info);
	
	public long getActivityGiftCount(Map<String, Object> conditions);
	
	public long addActivityDetail(ActivityDetail activityDetail);
	
	public long participateActivity(long userId, long relatedUserId, String activity);
	
	public Page<ActivityDetail> getActivityDetailPage(Map<String, Object> conditions,
														PageParam pageParam);
	
	public ActivityReturnEnum addGiftUseRecord(GiftUseRecord giftUseRecord);
	
	public ActivityReturnEnum cutDownGiftCount(long userId, String giftType, int num);
	
	public ActivityDetail getActivityDetailByBaseId(long giftId);
	
	public int updateActivityDetail(ActivityDetail detail);
	
	public ActivityReturnEnum addGiftCountAmount(long userId, String giftType, int num);
	
	public long countUserGift(Map<String, Object> giftUserConditions);
	
	public void insertActivityGiftCount(ActivityGiftCount act);
	
	public List<GiftInfo> queryGiftByConditions(Map<String, Object> conditions);
	
	public void updateActivityGiftCount(ActivityGiftCount activityGiftCount);
	
	public List<ActivityGiftCount> getActivityGiftCountListByConditions(Map<String, Object> conditions);
	
	public ActivityReturnEnum updateActivityOBNResult(Long userId);
	
	/**
	 * 检查用户是否参与某活动
	 * @param userId
	 * @param activityEmum
	 * @return
	 */
	public boolean checkIsUserJoinActivity(Long userId, ActivityEnum activityEmum);
	
}
