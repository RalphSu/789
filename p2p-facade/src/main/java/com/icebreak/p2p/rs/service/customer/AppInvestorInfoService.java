package com.icebreak.p2p.rs.service.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.StringUtil;

public class AppInvestorInfoService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try {
			String userBaseId = params.get("userBaseId").toString();
			PersonalInfoDO personalInfo = personalInfoManager.query(userBaseId);
			Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null,
				personalInfo.getUserId(), null);
			List<UserRelationDO> lst = page.getResult();
			if (lst.size() > 0) {
				result.put("userMemoNo", lst.get(0).getMemberNo());
			} else {
				result.put("userMemoNo", "");
			}
			List<BankBasicInfo> bankBasicInfos = this.bankDataService.getBankBasicInfos();
			String bankType = personalInfo.getBankType();
			for (BankBasicInfo info : bankBasicInfos) {
				if (info.getBankCode().equals(bankType)) {
					bankType = info.getLogoUrl();
				}
			}
			personalInfo.setBankType(bankType);
			String bankCardNo = personalInfo.getBankCardNo();
			if (bankCardNo != null) {
				if (bankCardNo.length() > 4 && StringUtil.isNotBlank(bankCardNo)) {
					bankCardNo = bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
				}
			}
			
			personalInfo.setBankCardNo(bankCardNo);
			result.put("info", personalInfo);
			json.put("code", 1);
			json.put("message", "查询成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数或网络异常");
			logger.error("查询投资记录时,参数或网络异常");
		}
		json.put("dataMap", result);
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appInvestorInfoService;
	}
	
}
