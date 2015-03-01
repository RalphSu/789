package com.icebreak.p2p.integration.openapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.integration.openapi.WithdrawQueryService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.info.QueryWithdrawInfo;
import com.icebreak.p2p.integration.openapi.order.WithdrawQueryOrder;
import com.icebreak.p2p.integration.openapi.result.WithdrawQueryResult;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.yiji.openapi.sdk.APITool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("withdrawQueryService")
public class WithdrawQueryServiceImpl extends OpenApiServiceBase implements WithdrawQueryService {

	private static final String WITHDRAW_QUERY_SERVICE_NAME = "withdraw.query";
	
	@Override
	public WithdrawQueryResult queryWithdrawService(WithdrawQueryOrder order) {
		Map<String, String> paramMap = APITool.initBaseParams(WITHDRAW_QUERY_SERVICE_NAME);
		WithdrawQueryResult result = new WithdrawQueryResult();
		try {
			paramMap.putAll(MiscUtil.covertPoToMapNoNullValue(order));
			if (order.getStartTime() != null) {
				paramMap.put("startTime",
					DateUtil.getFormat(DateUtil.dtSimpleYmdhms).format(order.getStartTime()));
			}
			if (order.getEndTime() != null) {
				paramMap.put("endTime",
					DateUtil.getFormat(DateUtil.dtSimpleYmdhms).format(order.getEndTime()));
			}
			logger.info(paramMap.toString());
			String responseString = send(paramMap);
			logger.info(responseString);
			JSONObject object = JSON.parseObject(responseString);
			Object jsonArray = object.get("data");
			String size = (String)object.get("size");
			long lSize = Long.parseLong(size);
			List<QueryWithdrawInfo> list = JSON.parseArray(jsonArray + "", QueryWithdrawInfo.class);
			result.setData(list);
			result.setCurrPage(order.getCurrPage());
			result.setPageSize(order.getPageSize());
			result.setSize(lSize);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
}
