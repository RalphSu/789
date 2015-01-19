package com.icebreak.p2p.rs.service.userManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;

public class AppGetUserMemberNoService extends ServiceBase implements AppService {

	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if(SessionLocalManager.getSessionLocal() == null){
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try{
			Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null, null, SessionLocalManager.getSessionLocal().getUserId(), null);
			List<UserRelationDO> lst =  page.getResult();
			if(lst.size()>0){
				json.put("memberNo", lst.get(0).getMemberNo());
			}else{
				json.put("memberNo", "");
			}
			json.put("code", 1);
			json.put("message", "查询用户编号成功");
		}catch(Exception e){
			json.put("code", 0);
			json.put("message", "查询用户编号失败");
		}
		return json;
	}

	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appGetUserMemberNoService;
	}

}
