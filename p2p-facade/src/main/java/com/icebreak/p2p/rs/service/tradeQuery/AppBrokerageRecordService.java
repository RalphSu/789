package com.icebreak.p2p.rs.service.tradeQuery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.AgentInvestorTrade;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.util.MoneyUtil;

public class AppBrokerageRecordService extends ServiceBase implements
		AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if(SessionLocalManager.getSessionLocal() == null){
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try{
			if(params.containsKey("pageSize") && params.containsKey("pageNo")){
				String size = params.get("pageSize").toString();
				String pageNo = params.get("pageNo").toString();
				//String startDate = params.get("startDate").toString();
				//String endDate = params.get("endDate").toString();
				QueryTradeOrder queryConditions = new QueryTradeOrder();
				queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
				queryConditions.setRoleId(11L);
				PageParam pageParam = new PageParam();
				pageParam.setPageNo(Integer.parseInt(pageNo));
				pageParam.setPageSize(Integer.parseInt(size));
				Page<AgentInvestorTrade> page= tradeService.pageQueryBrokerInvestorTrade(queryConditions, pageParam);
				long allAmount = tradeService.queryAllAmount(queryConditions);
				json.put("allAmount", MoneyUtil.getFormatAmount(allAmount));
				Long payedAmount = tradeService.sumPaidDivisionAmount(SessionLocalManager.getSessionLocal().getUserId());
				json.put("payedAmount", MoneyUtil.getFormatAmount(payedAmount));
				JSONArray array = new JSONArray();
				for(AgentInvestorTrade record : page.getResult()){
					JSONObject object = new JSONObject();  
					object.put("investorName", record.getInvestorName());
					object.put("investAmount", MoneyUtil.getFormatAmount(record.getInvestAmount()));
					object.put("brokerBenifitAmount", MoneyUtil.getFormatAmount(record.getBrokerBenifitAmount()));
					object.put("tradeId", record.getTradeId());
					object.put("detailId", record.getDetailId());
					object.put("investDetailId", record.getInvestDetailId());
					array.add(object);
				}
				json.put("infos", array);
				json.put("code", 1);
				json.put("message", "查询成功");
			}else{
				json.put("code", 0);
				json.put("message", "参数size或page不完整");
			}
			
		}catch(Exception e){
			json.put("code", 0);
			json.put("message", "参数或网络异常");
			logger.error("查询投资记录时,参数或网络异常");
		}
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appBrokerageRecordService;
	}

}
