package com.icebreak.p2p.statistics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.viewObject.UserStatisticsInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.statistics.UserStatisticsService;
import com.icebreak.p2p.statistics.order.StatisticsQueryOrder;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.util.lang.util.StringUtil;

@Service
public class UserStatisticsServiceImpl extends BaseAutowiredService implements
																	UserStatisticsService {
	
	@Override
	public List<Object> userStatisticsList(StatisticsQueryOrder queryOrder) {
		return null;
	}
	
	@Override
	public long countUserByConditions(Map<String, Object> conditions) {
		return userBaseInfoDAO.queryUserInfoCountByParams(conditions);
	}
	
	@Override
	public Page<Object> userStatisticsPage(StatisticsQueryOrder queryOrder, PageParam pageParam) {
		List<Object> results = new ArrayList<Object>();
		long totalSize = 0;
		long start = 0;
		if ("userRole".equals(queryOrder.getQueryType())) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			totalSize = roleDao.getAllRolesCount();
			start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			conditions.put("start", start);
			conditions.put("size", pageParam.getPageSize());
			List<Role> roles = roleDao.getAllRoles(conditions);
			CommonUtil.clearMap(conditions);
			if (roles != null && roles.size() > 0) {
				for (Role role : roles) {
					Map<String, Object> conditionsRole = new HashMap<String, Object>();
					if (StringUtil.isBlank(queryOrder.getStartDate())) {
						//String startDate=DateUtil.getWeekdayBeforeNow(new Date())+" 00:00:00";
						//condition.put("maiDemandDate",startDate );
					} else {
						String startDate = queryOrder.getStartDate() + " 00:00:00";
						conditionsRole.put("startDate", startDate);
					}
					if (StringUtil.isBlank(queryOrder.getEndDate())) {
						String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
						conditionsRole.put("endDate", endDate);
						queryOrder.setEndDate(DateUtil.simpleFormat(new Date()));
					} else {
						String endDate = queryOrder.getEndDate() + " 23:59:59";
						conditionsRole.put("endDate", DateUtil.parse(endDate));
					}
					conditionsRole.put("roleId", role.getId());
					long total = userBaseInfoDAO.queryUserInfoCountByParams(conditionsRole);
					conditionsRole.put("roleId", role.getId());
					conditionsRole.put("isRealNamePass", "IS");
					long realPass = userBaseInfoDAO.queryUserInfoCountByParams(conditionsRole);
					UserStatisticsInfoVO vo1 = new UserStatisticsInfoVO();
					vo1.setCountFildOne(role.getName());
					vo1.setCountFildTwo(String.valueOf(total));
					vo1.setCountFildThree(String.valueOf(realPass));
					results.add(vo1);
					CommonUtil.clearMap(conditionsRole);
				}
			}
		} else if ("userType".equals(queryOrder.getQueryType())) {
			totalSize = 2;
			Map<String, Object> conditions = new HashMap<String, Object>();
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				//String startDate=DateUtil.getWeekdayBeforeNow(new Date())+" 00:00:00";
				//condition.put("maiDemandDate",startDate );
			} else {
				String startDate = queryOrder.getStartDate() + " 00:00:00";
				conditions.put("startDate", startDate);
			}
			if (StringUtil.isBlank(queryOrder.getEndDate())) {
				String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
				conditions.put("endDate", endDate);
				queryOrder.setEndDate(DateUtil.simpleFormat(new Date()));
			} else {
				String endDate = queryOrder.getEndDate() + " 23:59:59";
				conditions.put("endDate", DateUtil.parse(endDate));
			}
			conditions.put("userType", "JG");
			long total = userBaseInfoDAO.queryUserInfoCountByParams(conditions);
			conditions.put("isRealNamePass", "IS");
			long realPass = userBaseInfoDAO.queryUserInfoCountByParams(conditions);
			UserStatisticsInfoVO vo1 = new UserStatisticsInfoVO();
			CommonUtil.clearMap(conditions);
			vo1.setCountFildOne("个人用户");
			vo1.setCountFildTwo(String.valueOf(total));
			vo1.setCountFildThree(String.valueOf(realPass));
			results.add(vo1);
			Map<String, Object> conditions1 = new HashMap<String, Object>();
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				//String startDate=DateUtil.getWeekdayBeforeNow(new Date())+" 00:00:00";
				//condition.put("maiDemandDate",startDate );
			} else {
				String startDate = queryOrder.getStartDate() + " 00:00:00";
				conditions1.put("startDate", startDate);
			}
			if (StringUtil.isBlank(queryOrder.getEndDate())) {
				String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
				conditions1.put("endDate", endDate);
				queryOrder.setEndDate(DateUtil.simpleFormat(new Date()));
			} else {
				String endDate = queryOrder.getEndDate() + " 23:59:59";
				conditions1.put("endDate", DateUtil.parse(endDate));
			}
			conditions1.put("userType", "GR");
			long total1 = userBaseInfoDAO.queryUserInfoCountByParams(conditions1);
			conditions1.put("isRealNamePass", "IS");
			long realPass1 = userBaseInfoDAO.queryUserInfoCountByParams(conditions1);
			UserStatisticsInfoVO vo2 = new UserStatisticsInfoVO();
			vo2.setCountFildOne("企业用户");
			vo2.setCountFildTwo(String.valueOf(total1));
			vo2.setCountFildThree(String.valueOf(realPass1));
			results.add(vo2);
			CommonUtil.clearMap(conditions1);
		} else if ("marketting".equals(queryOrder.getQueryType())) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("roleId", "10");
			totalSize = userBaseInfoDAO.queryUserInfoCountByParams(conditions);
			start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			conditions.put("limitStart", start);
			conditions.put("pageSize", pageParam.getPageSize());
			List<UserBaseInfoDO> users = userBaseInfoDAO.queryUserInfoListByParams(conditions);
			CommonUtil.clearMap(conditions);
			if (users != null && users.size() > 0) {
				for (UserBaseInfoDO user : users) {
					Map<String, Object> conditionsMakt = new HashMap<String, Object>();
					if (StringUtil.isBlank(queryOrder.getStartDate())) {
						//String startDate=DateUtil.getWeekdayBeforeNow(new Date())+" 00:00:00";
						//condition.put("maiDemandDate",startDate );
					} else {
						String startDate = queryOrder.getStartDate() + " 00:00:00";
						conditionsMakt.put("startDate", startDate);
					}
					if (StringUtil.isBlank(queryOrder.getEndDate())) {
						String endDate = DateUtil.simpleFormat(new Date()) + " 23:59:59";
						conditionsMakt.put("endDate", endDate);
						queryOrder.setEndDate(DateUtil.simpleFormat(new Date()));
					} else {
						String endDate = queryOrder.getEndDate() + " 23:59:59";
						conditionsMakt.put("endDate", DateUtil.parse(endDate));
					}
					conditionsMakt.put("userId", user.getUserId());
					long total = userBaseInfoDAO.queryChildrenCountByCondition(conditionsMakt);
					conditionsMakt.put("userId", user.getUserId());
					conditionsMakt.put("isRealNamePass", "IS");
					long realPass = userBaseInfoDAO.queryChildrenCountByCondition(conditionsMakt);
					UserStatisticsInfoVO vo1 = new UserStatisticsInfoVO();
					vo1.setCountFildOne(user.getRealName());
					vo1.setCountFildTwo(String.valueOf(total));
					vo1.setCountFildThree(String.valueOf(realPass));
					results.add(vo1);
					CommonUtil.clearMap(conditionsMakt);
				}
			}
		}
		return new Page<Object>(start, totalSize, pageParam.getPageSize(), results);
	}
	
}
