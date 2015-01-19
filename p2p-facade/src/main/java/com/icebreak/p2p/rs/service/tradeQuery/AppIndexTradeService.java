package com.icebreak.p2p.rs.service.tradeQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.IndexTrade;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.index.IndexTradeService;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.util.RateUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.LoanUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.util.lang.util.StringUtil;

public class AppIndexTradeService extends ServiceBase implements AppService {
	@Autowired
	private IndexTradeService indexTradeService;
	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try{
			int tradeType = Integer.parseInt(params.get("tradeType").toString());
			int pageSize = Integer.parseInt(params.get("pageSize").toString());
			int pageNo = Integer.parseInt(params.get("pageNo").toString());
			String guarantee = null;
			if(params.containsKey("guarantee")){
				guarantee = params.get("guarantee").toString();
			}
			String timeUnit = null;
			if(params.containsKey("timeUnit")){
				timeUnit = params.get("timeUnit").toString();
			}
			Integer startTime = 0;
			if(params.containsKey("startTime")){
				startTime =  Integer.parseInt(params.get("startTime").toString());
			}
			Integer endTime = null;
			if(params.containsKey("endTime")){
				endTime = Integer.parseInt(params.get("endTime").toString());
			}
			Double startRate = null;
			if(params.containsKey("startRate")){
				startRate = Double.parseDouble(params.get("startRate").toString());
			}
			Double endRate = null;
			if(params.containsKey("endRate")){
				endRate = Double.parseDouble(params.get("endRate").toString());
			}
			//Pagination<IndexTrade> indexTrade=indexTradeService.getIndexTrades((pageNo - 1) * pageSize, pageSize, tradeType, guarantee);
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("start", (pageNo - 1) * pageSize);
			conditions.put("size", pageSize);
			if(timeUnit!=null&&timeUnit.equals("M")){
				List<String> unit=new ArrayList<String>();
				unit.add("M");
				unit.add("W");
				conditions.put("timeUnit", unit);
			}else if(timeUnit!=null&&timeUnit.equals("D")){
				List<String> unit=new ArrayList<String>();
				unit.add("D");
				unit.add("D");
				conditions.put("timeUnit", unit);
			}else{
				List<String> unit=new ArrayList<String>();
				unit.add("M");
				unit.add("W");
				conditions.put("timeUnit", unit);
			}
			
			if(startTime != null && startTime > 0){
				conditions.put("startTime", startTime);
			}
			if(endTime != null && endTime > 0){
				conditions.put("endTime", endTime);
			}
			if(startRate != null && startRate > 0F){
				conditions.put("startRate", CommonUtil.div(startRate, 100));
			}
			if(endRate != null && endRate > 0F){
				conditions.put("endRate", CommonUtil.div(endRate, 100));
			}
			if(StringUtil.isNotBlank(guarantee)){
				if("YT".equals(guarantee)){
					conditions.put("guarantee", "重庆渝台信用担保有限公司");
				}else if("EDU".equals(guarantee)){
					conditions.put("guarantee", "重庆市教育担保有限责任公司");
				}
			}
			List<Integer> stats = new ArrayList<Integer>();
			if(0 == tradeType){
				stats.add(1);
				stats.add(2);
				stats.add(3);
				stats.add(6);
				stats.add(7);
				conditions.put("status", stats);
			}else if(1 == tradeType){
				stats.add(1);
				stats.add(6);
				conditions.put("status", stats);
			}else if(2 == tradeType){
				stats.add(2);
				stats.add(3);
				stats.add(7);
				conditions.put("status", stats);
			}
			Pagination<IndexTrade> indexTrade=indexTradeService.getIndexTrades(conditions);
			for(IndexTrade i:indexTrade.getResult()){
				i.setStrRaate(RateUtil.getRate(i.getRate()));
				if(i.getSponsorId() == null){
					i.setSponsorId(-1);
				}
			}
			JSONArray jsonArray = new JSONArray();
			if(indexTrade.getResult() != null && indexTrade.getResult().size() > 0){
				for(IndexTrade ind:indexTrade.getResult()){
					Map<String, Object> res = new HashMap<String ,Object>();
					res.put("tradeId", ind.getTradeId());
					res.put("demandId", ind.getDemandId());
					res.put("tradeName", ind.getName());
					res.put("guaranteeName", ind.getGuaranteeName());
					res.put("tradeStatus", ind.getStatus());
					res.put("tradeAmount", MoneyUtil.getFormatAmount(ind.getAmount()));
					res.put("tradeLoanedAmount", MoneyUtil.getFormatAmount(ind.getLoanedAmount()));
					res.put("tradeAnnualRate", ind.getStrRaate());
					res.put("timePeriod", LoanUtil.getLoanTime(ind.getTime(), ind.getTimeUnit()));
					res.put("investStartTime", DateUtil.simpleFormatYmdhms(ind.getInvestAvalibleTime()));
					res.put("investCompleteTime", DateUtil.simpleFormatYmdhms(ind.getInvestCompleteTime()));
					Trade trade = tradeService.getByTradeId(ind.getTradeId());
					if(1 == trade.getStatus()){
						res.put("deadline", DateUtil.simpleFormatYmdhms(ind.getDeadline()));
					}else if(2 == trade.getStatus() || 8 == trade.getStatus()){
						res.put("deadline", DateUtil.simpleFormatYmdhms(trade.getEffectiveDateTime()));
					}else if(3 == trade.getStatus() || 7 == trade.getStatus()){
						res.put("deadline", DateUtil.simpleFormatYmdhms(trade.getFinishDate()));
					}else{
						res.put("deadline", DateUtil.simpleFormatYmdhms(ind.getInvestCompleteTime()));
					}
					res.put("effectiveDate", DateUtil.simpleFormat(ind.getEffectiveDate()));
					String percent = null;
					if(1 == ind.getStatus()){
						percent = MoneyUtil.getPercentage(ind.getLoanedAmount(), ind.getAmount(), ind.getLeastInvestAmount());
						res.put("persontage", percent);
						res.put("requiredAmount", MoneyUtil.getMoneyByw(ind.getAmount(), ind.getLoanedAmount()));
					}else{
						res.put("persontage", "100%");
						res.put("requiredAmount",  "0.0");
					}
					jsonArray.add(res);
				}
			}
			json.put("infos", jsonArray);
			json.put("code", 1);
			json.put("message", "查询成功");
		}catch(Exception e){
			logger.error("移动终端查询项目列表失败",e);
			json.put("code", 0);
			json.put("message", "查询失败");
		}
		
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.indexTrade;
	}

}
