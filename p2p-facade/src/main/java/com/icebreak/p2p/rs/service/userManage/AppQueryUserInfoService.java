package com.icebreak.p2p.rs.service.userManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;

public class AppQueryUserInfoService extends ServiceBase implements
		AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try{
			String userName = params.get("userName").toString();
			String certNo = params.get("certNo").toString();
			logger.info("移动终端查询用户手机事情接口参数,userName="+userName+",certNo="+certNo);
			UserBaseInfoDO userInfo = userBaseInfoManager.queryByUserName(userName, 1);
			PersonalInfoDO person = personalInfoManager.query(userInfo.getUserBaseId());
			if(person.getCertNo().equals(certNo)){
				json.put("code", 1);
				json.put("message", "查询信息成功");
				json.put("mobile", userInfo.getMobile());
			}else{
				json.put("code", 0);
				json.put("message", "用户名与身份证号不匹配");
			}
		}catch (Exception e) {
			json.put("code", 0);
			json.put("message", "查询信息异常");
		}
		
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.queryUserInfoService;
	}

}
