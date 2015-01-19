package com.icebreak.p2p.rs.service.accountManage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.NumberUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AppWithdrawasRecordService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (params.containsKey("pageNo") && params.containsKey("pageSize")) {
				String startDate = params.get("startDate").toString();
				String endDate = params.get("endDate").toString();
				String pageNo = params.get("pageNo").toString();
				String pageSize = params.get("pageSize").toString();
				WithdrawQueryOrder queryOrder = new WithdrawQueryOrder();
				queryOrder.setStartTime(DateUtil.parse(startDate));
				queryOrder.setEndTime(DateUtil.parse(endDate));
				
				queryOrder.setCurrPage(NumberUtil.parseInt(pageNo));
				queryOrder.setPageSize(Integer.parseInt(pageSize));
				WithdrawQueryResult queryResult = withdrawQueryService.queryWithdrawService(
					queryOrder);
			} else {
				json.put("code", 0);
				json.put("message", "pageNo或pageSize参数不完整");
				return json;
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数或网络异常,查询失败");
			logger.error("参数或网络异常,查询失败", e);
		}
		
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.withdrawasRecordService;
	}
	
}
