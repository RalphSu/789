package com.icebreak.p2p.rs.service.userManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;

public class AppGetUserRoleService extends ServiceBase implements AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if(SessionLocalManager.getSessionLocal() == null){
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		Pagination<Role> rolesPage = authorityService.getRolesByUserId(
				SessionLocalManager.getSessionLocal().getUserId(), 0, 99);
		int broker = 11;
		int investor = 12;
		try{
			if(rolesPage.getResult()!=null && rolesPage.getResult().size()>0){
				for(Role role : rolesPage.getResult()){
					if(role.getId() == broker ){
						json.put("role", broker);
					}else if(role.getId() == investor){
						json.put("role", investor);
					}
				}
			}else{
				json.put("role", -1);
			}
		}catch(Exception e){
			logger.error("查询用户角色失败",e);
			json.put("code", 0);
			json.put("message", "查询用户角色异常");
		}
		json.put("code", 1);
		json.put("message", "查询用户角色成功");
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appGetUserRoleService;
	}

}
