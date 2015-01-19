package com.icebreak.p2p.statistics.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.base.BaseAutowiredService;
import com.icebreak.p2p.daointerface.AmountStatisticsDao;
import com.icebreak.p2p.dataobject.RechargeStatistics;
import com.icebreak.p2p.dataobject.viewObject.AmountStatisticsInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.statistics.AmountStatisticsService;
import com.icebreak.p2p.statistics.order.StatisticsQueryOrder;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.util.lang.util.StringUtil;

@Service
public class AmountStatisticsServiceImpl extends BaseAutowiredService implements
																		AmountStatisticsService {
	@Autowired
	AmountStatisticsDao	amountStatisticsDao;
	@Autowired
	TradeService		tradeService;
	
	@Override
	public List<Object> amountStatisticsList(StatisticsQueryOrder queryOrder) {
		return null;
	}
	
	@Override
	public long sumAmountByConditions(Map<String, Object> conditions) {
		
		return tradeService.countAmountByParams(conditions);
	}
	
	@Override
	public Page<AmountStatisticsInfoVO> amountStatisticsPage(StatisticsQueryOrder queryOrder,
																PageParam pageParam) {
		List<AmountStatisticsInfoVO> results = new ArrayList<AmountStatisticsInfoVO>();
		long totalSize = 0;
		long start = 0;
		Map<String, Object> conditions = new HashMap<String, Object>();
		if ("yearly".equals(queryOrder.getQueryType())) {
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				String startDate = "2013";
				conditions.put("startDate", startDate);
			} else {
				Date date = DateUtil.parse(queryOrder.getStartDate());
				String startDate = String.valueOf(date.getYear() + 1900);
				conditions.put("startDate", startDate);
			}
			if (StringUtil.isBlank(queryOrder.getEndDate())) {
				String endDate = String.valueOf(new Date().getYear() + 1900);
				conditions.put("endDate", endDate);
				queryOrder.setEndDate(DateUtil.simpleFormat(new Date()));
			} else {
				Date date = DateUtil.parse(queryOrder.getEndDate());
				String endDate = String.valueOf(date.getYear() + 1900);
				conditions.put("endDate", endDate);
			}
			totalSize = amountStatisticsDao.queryAmountCountYear(conditions);
			start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			conditions.put("start", start);
			conditions.put("size", pageParam.getPageSize());
			results = amountStatisticsDao.queryAmountListYear(conditions);
		} else if ("monthly".equals(queryOrder.getQueryType())) {
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				String startMonth = "10";
				conditions.put("startMonth", startMonth);
			} else {
				Date date = DateUtil.parse(queryOrder.getStartDate() + " 00:00:00");
				String startDate = String.valueOf(date.getMonth() + 1);
				conditions.put("startMonth", startDate);
			}
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				String startYear = "2013";
				conditions.put("startYear", startYear);
			} else {
				Date date = DateUtil.parse(queryOrder.getStartDate() + " 00:00:00");
				String startDate = String.valueOf(date.getYear() + 1900);
				conditions.put("startYear", startDate);
			}
			if (StringUtil.isBlank(queryOrder.getEndDate())) {
				String endMonth = String.valueOf(new Date().getMonth() + 1);
				conditions.put("endMonth", endMonth);
			} else {
				Date date = DateUtil.parse(queryOrder.getEndDate() + " 23:59:59");
				String endMonth = String.valueOf(date.getMonth() + 1);
				conditions.put("endMonth", endMonth);
			}
			if (StringUtil.isBlank(queryOrder.getEndDate())) {
				String endYear = String.valueOf(new Date().getYear() + 1900);
				conditions.put("endYear", endYear);
			} else {
				Date date = DateUtil.parse(queryOrder.getEndDate() + " 23:59:59");
				String endYear = String.valueOf(date.getYear() + 1900);
				conditions.put("endYear", endYear);
			}
			conditions.put("startMonthYear",
				conditions.get("startYear") + "-" + conditions.get("startMonth"));
			conditions.put("endMonthYear",
				conditions.get("endYear") + "-" + conditions.get("endMonth"));
			totalSize = amountStatisticsDao.queryAmountCountMonth(conditions);
			start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			conditions.put("start", start);
			conditions.put("size", pageParam.getPageSize());
			results = amountStatisticsDao.queryAmountListMonth(conditions);
		} else {
			if (StringUtil.isBlank(queryOrder.getStartDate())) {
				String startDate = "2013-10-15 00:00:00";
				conditions.put("startDate", startDate);
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
			totalSize = amountStatisticsDao.queryAmountCountMarketting(conditions);
			start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			conditions.put("start", start);
			conditions.put("size", pageParam.getPageSize());
			results = amountStatisticsDao.queryAmountListMarketting(conditions);
		}
		CommonUtil.clearMap(conditions);
		return new Page<AmountStatisticsInfoVO>(start, totalSize, pageParam.getPageSize(), results);
	}
	
	@Override
	public Page<RechargeStatistics> rechargeStatisticsPage(StatisticsQueryOrder queryOrder,
															PageParam pageParam) {
		List<RechargeStatistics> lst = new ArrayList<RechargeStatistics>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startTime", queryOrder.getStartDate());
		condition.put("endTime", queryOrder.getEndDate());
		condition.put("payType", queryOrder.getPayType());
		condition.put("type", queryOrder.getUserType());
		condition.put("bankName", queryOrder.getBankName());
		long totalSize = 0;
		long start = 0;
		totalSize = amountStatisticsDao.queryRchargeListCounts(condition);
		start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
			pageParam.getPageNo());
		condition.put("limitStart", start);
		condition.put("pageSize", pageParam.getPageSize());
		lst = amountStatisticsDao.queryRchargeList(condition);
		CommonUtil.clearMap(condition);
		return new Page<RechargeStatistics>(start, totalSize, pageParam.getPageSize(), lst);
	}
	
	@Override
	public RechargeStatistics getRechargeStatistics(StatisticsQueryOrder queryOrder) {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<String> status = new ArrayList<String>();
		String[] str = queryOrder.getStatus().split(",");
		for (String s : str) {
			status.add(s);
		}
		condition.put("status", status);
		condition.put("startTime", queryOrder.getStartDate());
		condition.put("endTime", queryOrder.getEndDate());
		condition.put("payType", queryOrder.getPayType());
		condition.put("type", queryOrder.getUserType());
		condition.put("bankName", queryOrder.getBankName());
		RechargeStatistics recharge = amountStatisticsDao.queryByConditions(condition);
		CommonUtil.clearMap(condition);
		return recharge;
	}
	
}
