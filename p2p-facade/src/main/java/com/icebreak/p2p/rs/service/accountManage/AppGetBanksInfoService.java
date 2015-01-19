package com.icebreak.p2p.rs.service.accountManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.BankInfo;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;

public class AppGetBanksInfoService extends ServiceBase implements AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {

		String isDeduct = request.getParameter("isDeduct");
		BankInfo info = new BankInfo();
		info.setDeduct(isDeduct);
		JSONObject json = new JSONObject();
		JSONArray arry = new JSONArray();
		List<BankInfo> list = bankBaseService.queryByConditions(info, null);
		for(BankInfo bankInfo : list){
			JSONObject temp = new JSONObject();
			temp.put("bankName", bankInfo.getBankName());
			temp.put("bankCode", bankInfo.getBankCode());
			temp.put("logoUrl", bankInfo.getLogoUrl());
			arry.add(temp);
		}
		json.put("code", 1);
		json.put("message", "查询信息成功");
		json.put("bankInfos", arry);
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.getBanksInfoService;
	}

}
