package com.icebreak.p2p.integration.openapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.integration.openapi.DepositQueryService;
import com.icebreak.p2p.integration.openapi.client.OpenApiServiceBase;
import com.icebreak.p2p.integration.openapi.info.DepositInfo;
import com.icebreak.p2p.integration.openapi.order.QueryDepositOrder;
import com.icebreak.p2p.integration.openapi.result.DepositQueryResult;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.yiji.openapi.sdk.APITool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("depositQueryService")
public class DepositQueryServiceImpl extends OpenApiServiceBase implements DepositQueryService {

	private static final String DEPOSIT_QUERY_SERVICE_NAME = "deposit.query";

	@Override
	public DepositQueryResult depositQueryService(QueryDepositOrder depositOrder) {
		
		Map<String, String> paramMap = APITool.initBaseParams(DEPOSIT_QUERY_SERVICE_NAME);
		DepositQueryResult result = new DepositQueryResult();
		try {
			paramMap.putAll(MiscUtil.covertPoToMapNoNullValue(depositOrder));
            if (depositOrder.getStartTime() != null) {
                paramMap.put("startTime",
                        DateUtil.getFormat(DateUtil.dtSimpleYmdhms).format(depositOrder.getStartTime()));
            }
            if (depositOrder.getEndTime() != null) {
                paramMap.put("endTime",
                        DateUtil.getFormat(DateUtil.dtSimpleYmdhms).format(depositOrder.getEndTime()));
            }
			String responseString = send(paramMap);
			JSONObject object = JSON.parseObject(responseString);
			Object jsonArray = object.get("data");

			String size = (String)object.get("size");
			long lSize = Long.parseLong(size);

			List<DepositInfo> list = JSON.parseArray(jsonArray+"",DepositInfo.class);
			result.setData(list);
			result.setCurrPage(depositOrder.getCurrPage());
			result.setPageSize(depositOrder.getPageSize());
			result.setSize(lSize);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.UN_KNOWN_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;
	}
	
}
