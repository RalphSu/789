package com.icebreak.p2p.rs.service.userManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.util.security.YrdCoderUtil;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.session.common.Constant;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.util.lang.ip.IPUtil;

public class AppUserActiveService extends ServiceBase implements AppService {
	private static final String	PRIVATE_KEY	= "RSAPrivateKey";
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		try {
			String MD5UserBaseId = params.get("MD5UserBaseId").toString();
			String code = params.get("verifyCode").toString();
			String logPassword = params.get("logPassword").toString();
			String payPassword = params.get("payPassword").toString();
			String mobile = params.get("mobile").toString();
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByMD5UserBaseId(MD5UserBaseId);
			SmsCodeResult codeResult = this.smsManagerService.verifySmsCode(mobile,
				SmsBizType.ACTIVATE, code, false);
			if (!codeResult.isSuccess()) {
				json.put("code", "0");
				json.put("message", "验证码校验失败");
				return json;
			}
			if (userBaseInfo.getState() == null || "inactive".equals(userBaseInfo.getState())) {
				UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
				userBaseInfo.setMobile(mobile);
				userBaseInfo.setMobileBinding("IS");
				userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
				returnEnum = userBaseInfoManager.updateLogPasswordAndPayPassword(
					userBaseInfo.getUserBaseId(), logPassword, payPassword);
				if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
					json.put("code", "1");
					json.put("message", "激活成功");

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

					SessionLocalManager.setSessionLocal(new SessionLocal(authorityService
						.getPermissionsByUserId(userBaseInfo.getUserId()),
						userBaseInfo.getUserId(), userBaseInfo.getUserBaseId(), roleCodesStr, userBaseInfo
							.getAccountId(), userBaseInfo.getAccountName(), userBaseInfo
							.getRealName(), userBaseInfo.getUserName(), IPUtil.getIpAddr(request)));
					String sessionId = Constant.getSessionId();
					Map<String, Object> KeyPairMap = YrdCoderUtil.getKeyPairMap();
					String privateKey = (String) KeyPairMap.get(PRIVATE_KEY);
					String appKey = sessionId;
					Session.setAttribute(appKey, privateKey);
					json.put("appKey", appKey);
				} else {
					json.put("code", "0");
					json.put("message", "激活失败");
				}
				
			} else {
				json.put("code", "1");
				json.put("message", "该账户已激活");
			}
		} catch (Exception e) {
			json.put("code", "0");
			json.put("message", "激活异常");
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appUserActive;
	}
}
