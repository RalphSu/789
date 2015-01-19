package com.icebreak.p2p.rs.service.accountManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;

public class AppValidationBankCardService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		try {
			String bankCode = params.get("bankCode").toString();
			String acountName = params.get("acountName").toString();
			String acountNo = params.get("acountNo").toString();
			String cardType = params.get("cardType").toString();
			String certNo = params.get("certNo").toString();
			OpeningBankQueryOrder info = new OpeningBankQueryOrder();
			info.setBankCode(bankCode);
			info.setAccountName(acountName);
			info.setCardNumber(acountNo);
			//"C", "信用卡类型" \"D", "借记卡类型" 平台只接受借记卡
			if ("D".equals(cardType))
				info.setCardType(CardTypeEnum.JJ);
			else {
				info.setCardType(CardTypeEnum.DJ);
			}
			info.setCertNo(certNo);
			json = userBaseInfoManager.validationBankCardByOpenApi(info);
		} catch (Exception e) {
			logger.error("参数或网络异常", e);
			json.put("code", 0);
			json.put("message", "验证失败");
		}
		
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.validationBankCard;
	}
	
}
