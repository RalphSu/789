package com.icebreak.p2p.rs.service.userManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;

public class AppValidationUserNameService extends ServiceBase implements
		AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		String userName = "";
		JSONObject jsonobj = new JSONObject();
		if(params.containsKey("userName")){
			userName = params.get("userName").toString();
		}else{
			jsonobj.put("code", 0);
			jsonobj.put("message", "参数userName不正确或错误");
			logger.error("参数userName不正确或错误");
			return jsonobj;
		}
		
		UserBaseReturnEnum returnEnum=UserBaseReturnEnum.USERNAME_ERROR;
		try {
			returnEnum = userBaseInfoManager.validationUserName(userName);
			//验证用户不存在
			if(returnEnum==UserBaseReturnEnum.USERNAME_SUCCESS){
				jsonobj.put("code", 1);
				jsonobj.put("message", "用户可以用");
			}if(returnEnum==UserBaseReturnEnum.USERNAME_ERROR){
				jsonobj.put("code", 0);
				jsonobj.put("message", "用户已存在");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "用户查询异常");
			logger.error("移动终端查询用户异常",e);
		}
		return jsonobj;
	}

	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appValidationUserNameService;
	}

}
