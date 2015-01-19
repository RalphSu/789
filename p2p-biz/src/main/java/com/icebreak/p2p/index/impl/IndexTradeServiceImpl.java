package com.icebreak.p2p.index.impl;

import com.icebreak.p2p.daointerface.IndexTradeDao;
import com.icebreak.p2p.dataobject.IndexTrade;
import com.icebreak.p2p.index.IndexTradeService;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.ws.enums.InsureWayEnum;
import com.icebreak.p2p.ws.service.query.order.IndexQueryOrder;

import java.util.*;

public class IndexTradeServiceImpl implements IndexTradeService {
	
	private IndexTradeDao	indexTradeDao	= null;
	
	public void setIndexTradeDao(IndexTradeDao indexTradeDao) {
		this.indexTradeDao = indexTradeDao;
	}
	
	@Override
	public Pagination<IndexTrade> getIndexTrades(int start, int size,IndexQueryOrder queryOrder) {
		
		Integer status = queryOrder.getStatus();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		params.put("guarantee", queryOrder.getGuarantee());
		params.put("interestRateBegin", queryOrder.getInterestRateBegin());
		params.put("interestRateEnd", queryOrder.getInterestRateEnd());
		params.put("areaCode", queryOrder.getAreaCode());
		params.put("loanType", queryOrder.getLoanType());
		if (status != null) {
			List<Integer> stats = new ArrayList<Integer>();
			if (1 == status) {
				stats.add(1);
				stats.add(2);
				stats.add(3);
				stats.add(6);
				stats.add(7);
                params.put("orderByCreateTime","createTime");
				params.put("status", stats);
			} else {
				stats.add(3);
				stats.add(7);
				params.put("status", stats);
				params.put("orderBy", "finishTime");
			}
		}
		List<IndexTrade> lists = indexTradeDao.getByProperties(params);
		if (lists != null && lists.size() > 0) {
			for (int i = lists.size() - 1; i >= 0; i--) {
				IndexTrade trade = lists.get(i);
				Date investCompleteTime = indexTradeDao.getInvestCompleteTime(trade.getTradeId());
				trade.setInvestCompleteTime(investCompleteTime);
				trade.setInsureWayMsg(InsureWayEnum.getByCode(trade.getInsureWay()==null?"0000000000":trade.getInsureWay()).getMessage());
			}
		}
		Pagination<IndexTrade> page = new Pagination<IndexTrade>(start,
			indexTradeDao.getByPropertiesCount(params), size, lists);
		CommonUtil.clearMap(params);
		return page;
	}
	
	@Override
	public Pagination<IndexTrade> getIndexTrades(int start, int size, String guarantee,
													String timeUnit, Integer startTime,
													Integer endTime, Long startAmount,
													Long endAmount, Double startRate, Double endRate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		if (timeUnit != null && timeUnit.equals("M")) {
			List<String> unit = new ArrayList<String>();
			unit.add("M");
			unit.add("W");
			params.put("timeUnit", unit);
		} else if (timeUnit != null && timeUnit.equals("D")) {
			List<String> unit = new ArrayList<String>();
			unit.add("D");
			unit.add("D");
			params.put("timeUnit", unit);
		} else {
			params.put("timeUnit", timeUnit);
		}
		
		if (startTime != null && startTime > 0) {
			params.put("startTime", startTime);
		}
		if (endTime != null && endTime > 0) {
			params.put("endTime", endTime);
		}
		if (startAmount != null && startAmount > 0) {
			params.put("startAmount", startAmount);
		}
		if (endAmount != null && endAmount > 0F) {
			params.put("endAmount", endAmount);
		}
		if (startRate != null && startRate > 0F) {
			params.put("startRate", startRate);
		}
		if (endRate != null && endRate > 0F) {
			params.put("endRate", endRate);
		}
		List<Integer> stats = new ArrayList<Integer>();
		stats.add(1);
		stats.add(2);
		stats.add(3);
		stats.add(6);
		stats.add(7);
		params.put("status", stats);
		
		List<IndexTrade> lists = indexTradeDao.getByProperties(params);
		if (lists != null && lists.size() > 0) {
			for (IndexTrade trade : lists) {
				Date investCompleteTime = indexTradeDao.getInvestCompleteTime(trade.getTradeId());
				trade.setInvestCompleteTime(investCompleteTime);
				trade.setInsureWayMsg(InsureWayEnum.getByCode(trade.getInsureWay()).getMessage());
			}
		}
		Pagination<IndexTrade> page = new Pagination<IndexTrade>(start,
			indexTradeDao.getByPropertiesCount(params), size, lists);
		CommonUtil.clearMap(params);
		return page;
	}
	
	@Override
	public Pagination<IndexTrade> getIndexTrades(Map<String, Object> conditions) {
		int start = (Integer) conditions.get("start");
		int size = (Integer) conditions.get("size");
		List<IndexTrade> lists = indexTradeDao.getByProperties(conditions);
		if (lists != null && lists.size() > 0) {
			for (IndexTrade trade : lists) {
				Date investCompleteTime = indexTradeDao.getInvestCompleteTime(trade.getTradeId());
				trade.setInvestCompleteTime(investCompleteTime);
			}
		}
		return new Pagination<IndexTrade>(start, indexTradeDao.getByPropertiesCount(conditions),
			size, lists);
	}
}
