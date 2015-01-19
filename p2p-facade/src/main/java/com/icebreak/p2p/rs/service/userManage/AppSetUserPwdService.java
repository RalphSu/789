package com.icebreak.p2p.rs.service.userManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.util.lang.ip.IPUtil;

public class AppSetUserPwdService extends ServiceBase implements AppService {


	@Override
	public JSONObject execute(Map<String, Object> params,
			HttpServletRequest request) {

		JSONObject json = new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String MD5UserBaseId = params.get("MD5UserBaseId").toString();
			String logPassWord = params.get("logPassWord").toString();
			String payPassWord = params.get("payPassWord").toString();
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByMD5UserBaseId(MD5UserBaseId);
			UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
			returnEnum = userBaseInfoManager.updateLogPasswordAndPayPassword(userBaseInfo.getUserBaseId(), logPassWord, payPassWord);
			if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
				//获取角色串begin
				List<Role> roleList = null;
				try {
					roleList = authorityService.getRolesByUserId(userBaseInfo.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuilder roleCodes = new StringBuilder();
				String roleCodesStr = "";
				if(roleList != null || roleList.size()>0){
					for(int i=0;i<roleList.size();i++){
						roleCodes.append("|").append(roleList.get(i).getCode());
					}
					roleCodesStr = roleCodes.substring(1);
				}
				//获取角色串end

				SessionLocalManager.setSessionLocal(new SessionLocal(
						authorityService.getPermissionsByUserId(userBaseInfo.getUserId()), userBaseInfo.getUserId(),
						userBaseInfo.getUserBaseId(), roleCodesStr, userBaseInfo.getAccountId(),
						userBaseInfo.getAccountName(), userBaseInfo.getRealName(),
						userBaseInfo.getUserName(), IPUtil.getIpAddr(request)));
				result.put("code", "1");
				result.put("message", "设置密码成功");
				json.put("dataMap", result);
			}else{
				result.put("code", "0");
				result.put("message", "设置密码失败");
				json.put("dataMap", result);
			}
		}catch(Exception e){
			result.put("code", "0");
			result.put("message", "设置密码异常");
			json.put("dataMap", result);
		}
		return json;
	}
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appSetUserPwd;
	}

}
