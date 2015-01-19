package com.icebreak.p2p.rs.service.accountManage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AppDeductService extends ServiceBase implements AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if(SessionLocalManager.getSessionLocal() == null){
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
		UserBaseInfoDO userBaseInfo = null;
		try {
			userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
			if(!"IS".equals(userBaseInfo.getRealNameAuthentication())){
				json.put("code", 0);
				json.put("message", "未通过实名认证，暂不能进行快捷划入服务");
				return json;
			}
		} catch (Exception e1) {
			logger.error("移动终端用户快捷划入时，查询用户信息失败",e1);
			json.put("code", 0);
			json.put("message", "移动终端用户快捷划入时，查询用户信息失败");
			return json;
		}
		
		try{
			String amount = params.get("amount").toString();
			String password = params.get("password").toString();
			UserBaseReturnEnum returnEnum = userBaseInfoManager.validationPayPassword(userBaseId, password);
			if(returnEnum==UserBaseReturnEnum.PASSWORD_SUCCESS){
				json = null;
			}else{
				json.put("code", 0);
				json.put("message", "密码错误");
			}
			
		}catch(Exception e){
			logger.error("代扣参数异常或网络错误",e);
			json.put("code", 0);
			json.put("message", "代扣失败,参数异常或网络错误");
		}
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.deductService;
	}

}
