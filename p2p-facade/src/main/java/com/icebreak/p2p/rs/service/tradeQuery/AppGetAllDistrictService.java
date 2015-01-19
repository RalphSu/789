package com.icebreak.p2p.rs.service.tradeQuery;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.service.openingbank.info.ProvinceInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AppGetAllDistrictService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject jsonobj = new JSONObject();
		List<ProvinceInfo> provinceList = this.openingBankService.getAllDistrict()
			.getProvinceInfos();
		if (provinceList.size() <= 0) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "查询失败");
		} else {
			jsonobj.put("code", 1);
			jsonobj.put("data", provinceList);
			jsonobj.put("message", "查询成功");
		}
		return jsonobj;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appGetAllDistrictService;
	}
	
}
