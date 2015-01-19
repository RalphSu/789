package com.icebreak.p2p.activity.impl;

import com.icebreak.p2p.activity.ActivityEnum;
import com.icebreak.p2p.activity.IActivityService;
import com.icebreak.p2p.activity.QueryActivityOrder;
import com.icebreak.p2p.activity.enums.ActivityReturnEnum;
import com.icebreak.p2p.daointerface.ActivityDao;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.ws.enums.PayTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class IActivityServiceImpl implements IActivityService {
	protected final Logger			logger	= LoggerFactory.getLogger(getClass());
	@Autowired
	public UserBaseInfoManager		userBaseInfoManager;
	@Autowired
	protected PersonalInfoManager	personalInfoManager;
	private GiftManager				giftManager;
	
	public void setGiftManager(GiftManager giftManager) {
		this.giftManager = giftManager;
	}
	
	private ActivityDao	activityDao;
	
	public ActivityDao getActivityDao() {
		return activityDao;
	}
	
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public void addActivityInfo(ActivityInfo activityInfo) throws Exception {
		try {
			activityDao.insert(activityInfo);
		} catch (Exception e) {
			logger.error("新增活动内容失败，服务器异常", e);
			throw new Exception("新增活动失败，服务器异常");
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int updateActivityInfo(ActivityInfo activityInfo) throws Exception {
		int reCount = 0;
		try {
			reCount = activityDao.upate(activityInfo);
		} catch (Exception e) {
			logger.error("更新活动内容失败，服务器异常", e);
			throw new Exception("更新活动内容失败，服务器异常");
		}
		return reCount;
	}
	
	@Override
	public ActivityInfo getActivityInfoByTblBaseId(long tblBaseId) {
		ActivityInfo info = activityDao.getActivityByBaseId(tblBaseId);
		return info;
	}
	
	@Override
	public GiftUseRecord getGiftUseRecordByBizNo(String bizNumber) {
		GiftUseRecord gift = activityDao.queryGiftUseRecordByBizNo(bizNumber);
		return gift;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public int updateGiftUsedRecord(GiftUseRecord giftUseRecord) throws Exception {
		int reCount = 0;
		try {
			reCount = activityDao.updateGiftUseRcord(giftUseRecord);
		} catch (Exception e) {
			logger.error("用户更新礼品使用记录失败，服务器异常", e);
			throw new Exception("用户更新礼品使用记录失败，服务器异常");
		}
		return reCount;
	}
	
	@Override
	public Page<ActivityInfo> queryListActivityInfo(QueryActivityOrder queryActivityOrder,
													PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (queryActivityOrder.getActivityName() != null
			&& !"".equals(queryActivityOrder.getActivityName())) {
			condition.put("activityName", queryActivityOrder.getActivityName());
		}
		if (queryActivityOrder.getSendGiftCode() != null
			&& !"".equals(queryActivityOrder.getSendGiftCode())) {
			condition.put("sendGiftCode", queryActivityOrder.getSendGiftCode());
		}
		if (queryActivityOrder.getStatus() > 0) {
			condition.put("status", queryActivityOrder.getStatus());
		}
		long totalSize = activityDao.queryActivityCount(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		List<ActivityInfo> activityList = activityDao.queryActivityList(condition);
		Page<ActivityInfo> pageActivity = new Page<ActivityInfo>(start, totalSize,
			pageParam.getPageSize(), activityList);
		return pageActivity;
	}
	
	@Override
	public long getActivityInfoCount(QueryActivityOrder queryActivityOrder) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (queryActivityOrder.getActivityName() != null
			&& !"".equals(queryActivityOrder.getActivityName())) {
			condition.put("activityName", queryActivityOrder.getActivityName());
		}
		if (queryActivityOrder.getSendGiftCode() != null
			&& !"".equals(queryActivityOrder.getSendGiftCode())) {
			condition.put("sendGiftCode", queryActivityOrder.getSendGiftCode());
		}
		if (queryActivityOrder.getStatus() > 0) {
			condition.put("status", queryActivityOrder.getStatus());
		}
		long totalSize = activityDao.queryActivityCount(condition);
		return totalSize;
	}
	
	@Override
	public Page<GiftUseRecord> getPageGiftUsedRecord(QueryActivityOrder queryActivityOrder,
														PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (queryActivityOrder.getUserName() != null
			&& !"".equals(queryActivityOrder.getUserName())) {
			condition.put("userName", queryActivityOrder.getUserName());
		}
		if (queryActivityOrder.getBizNumber() != null
			&& !"".equals(queryActivityOrder.getBizNumber())) {
			condition.put("bizNumber", queryActivityOrder.getBizNumber());
		}
		long totalSize = activityDao.queryGiftUsedCount(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		List<GiftUseRecord> list = activityDao.queryGiftUsedList(condition);
		Page<GiftUseRecord> pageActivity = new Page<GiftUseRecord>(start, totalSize,
			pageParam.getPageSize(), list);
		return pageActivity;
	}
	
	@Override
	public Page<GiftInfo> getGiftInfos(PageParam pageParam) {
		Map<String, Object> condition = new HashMap<String, Object>();
		long totalSize = giftManager.getGiftCount(condition);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		condition.put("pageSize", pageParam.getPageSize());
		List<GiftInfo> activityList = giftManager.getGiftInfos(condition);
		Page<GiftInfo> pageActivity = new Page<GiftInfo>(start, totalSize, pageParam.getPageSize(),
			activityList);
		return pageActivity;
	}
	
	@Override
	public void addGiftInfo(GiftInfo info) {
		giftManager.insertGiftInfo(info);
	}
	
	@Override
	public GiftInfo getGift(long giftId) {
		return giftManager.getGift(giftId);
	}
	
	@Override
	public void updateGiftSubmit(GiftInfo info) {
		giftManager.updateGiftInfo(info);
	}
	
	@Override
	public long getActivityGiftCount(Map<String, Object> conditions) {
		return activityDao.getActivityGiftCount(conditions);
	}
	
	@Override
	public long addActivityDetail(ActivityDetail activityDetail) {
		
		return activityDao.insertActivityDetail(activityDetail);
	}
	
	@Override
	public long participateActivity(long userId, long relatedUserId, String activity) {
		UserBaseInfoDO user = userBaseInfoManager.queryByUserId(userId);
		UserBaseInfoDO relatedUser = userBaseInfoManager.queryByUserId(relatedUserId);
		if (user == null || relatedUser == null) {
			logger
				.info("=================无效用户 userId-relatedUserId" + userId + "-" + relatedUserId);
			return 0;
		}
		String activityName = ActivityEnum.valueOf(activity).code;
		ActivityInfo acinfo = queryActivityByName(activityName);
		if (acinfo != null) {
			if (acinfo.getStatus() != 2) {
				logger.error("此活动还未开始或已经结束 ");
				return 0;
			}
			logger.info("=================开始新增被推荐人记录 userId=" + userId);
			ActivityDetail activityDetail = new ActivityDetail();
			activityDetail.setActivityId(acinfo.getTblBaseId());
			activityDetail.setActivityName(activityName);
			activityDetail.setAddTime(new Date());
			String giftCodes = acinfo.getSendGiftCode();
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("giftCode", giftCodes);
			List<GiftInfo> gifts = queryGiftByConditions(conditions);
			if (gifts != null && gifts.size() > 0) {
				GiftInfo gift = gifts.get(0);
				activityDetail.setGiftCode(gift.getGiftCode());
				activityDetail.setGiftName(gift.getGiftName());
				activityDetail.setGiftNumber(1);
			} else {
				logger.error("查询礼品失败");
			}
			activityDetail.setRelationId(relatedUserId);
			activityDetail.setRelatedUserName(relatedUser.getUserName());
			activityDetail.setRelatedRealName(relatedUser.getRealName());
			activityDetail.setType(1);
			activityDetail.setUserId(userId);
			activityDetail.setUserName(user.getUserName());
			activityDetail.setRealName(user.getRealName());
			activityDetail.setStatus(0);
			addActivityDetail(activityDetail);
			/**推荐人**/
			logger.info("=================开始新增推荐人记录 userId=" + relatedUserId);
			ActivityDetail activityRefDetail = new ActivityDetail();
			activityRefDetail.setActivityId(acinfo.getTblBaseId());
			activityRefDetail.setActivityName(activityName);
			activityRefDetail.setAddTime(new Date());
			if (gifts != null && gifts.size() > 0) {
				GiftInfo gift = gifts.get(0);
				activityRefDetail.setGiftCode(gift.getGiftCode());
				activityRefDetail.setGiftName(gift.getGiftName());
				activityRefDetail.setGiftNumber(1);
			} else {
				logger.error("查询礼品失败");
			}
			activityRefDetail.setRelationId(userId);
			activityRefDetail.setRelatedUserName(user.getUserName());
			activityRefDetail.setRelatedRealName(user.getRealName());
			activityRefDetail.setType(0);
			activityRefDetail.setUserId(relatedUserId);
			activityRefDetail.setUserName(relatedUser.getUserName());
			activityRefDetail.setRealName(relatedUser.getRealName());
			activityRefDetail.setStatus(0);
			addActivityDetail(activityRefDetail);
		} else {
			logger.error("未开展此活动 ");
			return 0;
		}
		return 1;
	}
	
	@Override
	public List<GiftInfo> queryGiftByConditions(Map<String, Object> conditions) {
		
		return activityDao.queryGiftByConditions(conditions);
	}
	
	private ActivityInfo queryActivityByName(String activityName) {
		
		return activityDao.queryActivityByName(activityName);
	}
	
	public static void main(String[] args) {
		System.out.println(ActivityEnum.OBN);
	}
	
	@Override
	public Page<ActivityDetail> getActivityDetailPage(Map<String, Object> conditions,
														PageParam pageParam) {
		long totalSize = activityDao.queryActivityDetailCount(conditions);
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		conditions.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		List<ActivityDetail> list = activityDao.queryActivityDetailList(conditions);
		Page<ActivityDetail> pageActivityDetail = new Page<ActivityDetail>(start, totalSize,
			pageParam.getPageSize(), list);
		return pageActivityDetail;
	}
	
	@Override
	public ActivityReturnEnum addGiftUseRecord(GiftUseRecord giftUseRecord) {
		ActivityReturnEnum returnEnum = ActivityReturnEnum.EXECUTE_FAILURE;
		try {
			activityDao.addGiftUseRecord(giftUseRecord);
			int num = 1;
			cutDownGiftCount(giftUseRecord.getUserId(), PayTypeEnum.WITHDRAW.code(), num);
			returnEnum = ActivityReturnEnum.EXECUTE_SUCCESS;
		} catch (Exception e) {
			logger.error("获addGiftUseRecord礼品失败", e);
		}
		return returnEnum;
	}
	
	@Override
	public ActivityReturnEnum cutDownGiftCount(long userId, String giftType, int num) {
		ActivityReturnEnum returnEnum = ActivityReturnEnum.EXECUTE_FAILURE;
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("userId", userId);
			conditions.put("giftType", PayTypeEnum.WITHDRAW.code());
			List<Integer> status = new ArrayList<Integer>();
			status.add(1);
			conditions.put("status", status);
			conditions.put("limitStart", 0);
			conditions.put("pageSize", 999);
			List<ActivityGiftCount> giftCountList = getActivityGiftCountListByConditions(conditions);
			if (giftCountList != null && giftCountList.size() > 0) {
				for (ActivityGiftCount activityGiftCount : giftCountList) {
					if (activityGiftCount.getGiftCount() - num >= 0) {
						activityGiftCount.setGiftCount(activityGiftCount.getGiftCount() - num);
						updateActivityGiftCount(activityGiftCount);
						break;
					}
				}
			}
			returnEnum = ActivityReturnEnum.EXECUTE_SUCCESS;
		} catch (Exception e) {
			logger.error("异常" + e);
		}
		return returnEnum;
	}
	
	@Override
	public ActivityReturnEnum addGiftCountAmount(long userId, String giftType, int num) {
		ActivityReturnEnum returnEnum = ActivityReturnEnum.EXECUTE_FAILURE;
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("userId", userId);
			conditions.put("giftType", giftType);
			List<Integer> status = new ArrayList<Integer>();
			status.add(1);
			conditions.put("status", status);
			conditions.put("limitStart", 0);
			conditions.put("pageSize", 999);
			List<ActivityGiftCount> giftCountList = getActivityGiftCountListByConditions(conditions);
			if (giftCountList != null && giftCountList.size() > 0) {
				for (ActivityGiftCount activityGiftCount : giftCountList) {
					activityGiftCount.setGiftCount(activityGiftCount.getGiftCount() + num);
					updateActivityGiftCount(activityGiftCount);
					break;
				}
			}
			returnEnum = ActivityReturnEnum.EXECUTE_SUCCESS;
		} catch (Exception e) {
			logger.error("异常" + e);
		}
		return returnEnum;
	}
	
	@Override
	public void updateActivityGiftCount(ActivityGiftCount activityGiftCount) {
		activityDao.updateActivityGiftCount(activityGiftCount);
	}
	
	@Override
	public List<ActivityGiftCount> getActivityGiftCountListByConditions(Map<String, Object> conditions) {
		
		return activityDao.getActivityGiftCountListByConditions(conditions);
	}
	
	@Override
	public ActivityDetail getActivityDetailByBaseId(long giftId) {
		
		return activityDao.getActivityDetailByBaseId(giftId);
	}
	
	@Override
	public int updateActivityDetail(ActivityDetail detail) {
		
		return activityDao.updateActivityDetail(detail);
	}
	
	@Override
	public long countUserGift(Map<String, Object> giftUserConditions) {
		return activityDao.countUserGift(giftUserConditions);
	}
	
	@Override
	public void insertActivityGiftCount(ActivityGiftCount act) {
		activityDao.insertActivityGiftCount(act);
	}
	
	@Override
	public ActivityReturnEnum updateActivityOBNResult(Long userId) {
		ActivityReturnEnum returnEnum = ActivityReturnEnum.EXECUTE_FAILURE;
		logger.info("参加了老带新活动");
		String activityName = ActivityEnum.OBN.code;
		ActivityInfo acinfo = queryActivityByName(activityName);
		if (acinfo != null && acinfo.getStatus() == 2) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			List<Integer> states = new ArrayList<Integer>();
			states.add(0);
			states.add(1);
			conditions.put("status", states);
			conditions.put("activityId", acinfo.getTblBaseId());
			conditions.put("type", 1);
			conditions.put("userId", userId);
			conditions.put("limitStart", 0);
			conditions.put("pageSize", 1);
			List<ActivityDetail> detailNews = activityDao.queryActivityDetailList(conditions);
			ActivityDetail detailNew = null;
			if (detailNews != null && detailNews.size() > 0) {
				detailNew = detailNews.get(0);
				try {
					UserBaseInfoDO newUser = userBaseInfoManager.queryByUserId(detailNew
						.getUserId());
					PersonalInfoDO newPer = personalInfoManager.query(newUser.getUserBaseId());
					UserBaseInfoDO oldUser = userBaseInfoManager.queryByUserId(detailNew
						.getRelationId());
					PersonalInfoDO oldPer = personalInfoManager.query(oldUser.getUserBaseId());
					if (newPer.getCertNo().equals(oldPer.getCertNo())) {
						logger.error("获取礼品失败：---使用相同的身份证进行认证");
						return returnEnum;
					}
				} catch (Exception e) {
					logger.error("获取礼品失败", e);
					e.printStackTrace();
				}
				detailNew.setFinishTime(new Date());
				detailNew.setStatus(2);
				detailNew.setFinishTime(new Date());
				/***更新记录状态***/
				updateActivityDetail(detailNew);
				/***更新物品***/
				Map<String, Object> giftUserNewConditions = new HashMap<String, Object>();
				List<Integer> giftUserStatus = new ArrayList<Integer>();
				giftUserStatus.add(1);
				giftUserNewConditions.put("status", giftUserStatus);
				giftUserNewConditions.put("giftType", PayTypeEnum.WITHDRAW.code());
				giftUserNewConditions.put("userId", userId);
				long countUserGift = countUserGift(giftUserNewConditions);
				if (countUserGift > 0) {
					addGiftCountAmount(detailNew.getUserId(), PayTypeEnum.WITHDRAW.code(), 1);
				} else {
					ActivityGiftCount act = new ActivityGiftCount();
					act.setUserId(userId);
					act.setStatus(1);
					Map<String, Object> giftconditions = new HashMap<String, Object>();
					giftconditions.put("giftCode", detailNew.getGiftCode());
					List<GiftInfo> gifts = queryGiftByConditions(giftconditions);
					if (gifts != null && gifts.size() > 0) {
						GiftInfo gift = gifts.get(0);
						act.setStartTime(gift.getStartTime());
						act.setEndTime(gift.getEndTime());
						act.setGiftCode(gift.getGiftCode());
						act.setGiftName(gift.getGiftName());
						act.setGiftType(gift.getGiftType());
						act.setGiftCount(1);
						insertActivityGiftCount(act);
					} else {
						logger.error("查询礼品失败");
					}
				}
			} else {
				logger.error("未查询到参与活动记录");
			}
			Map<String, Object> conditionsOld = new HashMap<String, Object>();
			conditionsOld.put("status", states);
			conditionsOld.put("activityId", acinfo.getTblBaseId());
			conditionsOld.put("type", 0);
			conditionsOld.put("userId", detailNew.getRelationId());
			conditionsOld.put("relationId", userId);
			conditionsOld.put("limitStart", 0);
			conditionsOld.put("pageSize", 1);
			List<ActivityDetail> detailOlds = activityDao.queryActivityDetailList(conditionsOld);
			ActivityDetail detailOld = null;
			if (detailOlds != null && detailOlds.size() > 0) {
				detailOld = detailOlds.get(0);
				detailOld.setStatus(2);
				detailOld.setFinishTime(new Date());
				/***更新记录状态***/
				updateActivityDetail(detailOld);
				/***更新物品***/
				Map<String, Object> giftUserOldConditions = new HashMap<String, Object>();
				List<Integer> giftUserStatus = new ArrayList<Integer>();
				giftUserStatus.add(1);
				giftUserOldConditions.put("status", giftUserStatus);
				giftUserOldConditions.put("giftType", PayTypeEnum.WITHDRAW.code());
				giftUserOldConditions.put("userId", detailOld.getUserId());
				long countUserGift = countUserGift(giftUserOldConditions);
				if (countUserGift > 0) {
					addGiftCountAmount(detailOld.getUserId(), PayTypeEnum.WITHDRAW.code(), 1);
				} else {
					ActivityGiftCount act = new ActivityGiftCount();
					act.setUserId(detailOld.getUserId());
					act.setStatus(1);
					Map<String, Object> giftconditions = new HashMap<String, Object>();
					giftconditions.put("giftCode", detailOld.getGiftCode());
					List<GiftInfo> gifts = queryGiftByConditions(giftconditions);
					if (gifts != null && gifts.size() > 0) {
						GiftInfo gift = gifts.get(0);
						act.setStartTime(gift.getStartTime());
						act.setEndTime(gift.getEndTime());
						act.setGiftCode(gift.getGiftCode());
						act.setGiftName(gift.getGiftName());
						act.setGiftType(gift.getGiftType());
						act.setGiftCount(1);
						insertActivityGiftCount(act);
					} else {
						logger.error("查询礼品失败");
					}
				}
			} else {
				logger.error("未查询到参与活动记录");
			}
		} else {
			logger.error("活动不存在或者未开始或者已停止");
			return returnEnum;
		}
		return returnEnum;
	}
	
	@Override
	public boolean checkIsUserJoinActivity(Long userId, ActivityEnum activityEmum) {
		boolean flag = false;
		String activityName = activityEmum.code;
		ActivityInfo acinfo = queryActivityByName(activityName);
		if (acinfo != null && acinfo.getStatus() == 2) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			List<Integer> states = new ArrayList<Integer>();
			states.add(0);
			states.add(1);
			conditions.put("status", states);
			conditions.put("activityId", acinfo.getTblBaseId());
			conditions.put("type", 1);
			conditions.put("userId", userId);
			long count = activityDao.checkIsUserJoinActivity(conditions);
			if (count > 0) {
				flag = true;
			}
		} else {
			logger.error("活动不存在或者未开始或者已停止");
		}
		return flag;
	}
}
