package com.icebreak.p2p.rs.service.userManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.ws.enums.SmsBizType;

public class AppForgotPasswordService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		try {
			String newPassword = params.get("newPassword").toString();
			String modifyType = params.get("pwdType").toString();
			String userName = params.get("userName").toString();
			String verifyCode = params.get("verifyCode").toString();
			String business = params.get("business").toString();
			String mobile = params.get("mobile").toString();
			SmsCodeResult result = this.smsManagerService.verifySmsCode(mobile,
				SmsBizType.getByCode(business), verifyCode, false);
			if (!result.isSuccess()) {
				json.put("code", 0);
				json.put("message", "验证码校验失败");
				return json;
			}
			if ("payPassword".equalsIgnoreCase(modifyType)) {
				if (SessionLocalManager.getSessionLocal() == null) {
					json.put("code", 0);
					json.put("message", "未登录");
					return json;
				}
				String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
				UserBaseReturnEnum returnEnum = userBaseInfoManager.updatePayPassword(userBaseId,
					newPassword);
				if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
					json.put("code", 1);
					json.put("message", "支付密码保存成功");
				} else {
					json.put("code", 0);
					json.put("message", "支付密码保存失败");
				}
			} else {
				UserBaseInfoDO userInfo = userBaseInfoManager.queryByUserName(userName, 1);
				String userBaseId = userInfo.getUserBaseId();
				UserBaseReturnEnum returnEnum = userBaseInfoManager.updateLogPassword(userBaseId,
					newPassword);
				if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
					if ("locked".equals(userInfo.getState())) {
						userInfo.setState("normal");
						userInfo.setPwdErrorCount(0);
						userBaseInfoManager.updateUserBaseInfo(userInfo);
					}
					json.put("code", 1);
					json.put("message", "登录密码保存成功");
				} else {
					json.put("code", 0);
					json.put("message", "登录密码保存失败");
				}
			}
		} catch (Exception e) {
			logger.error("网络或参数, 找回密码失败", e);
			json.put("code", 0);
			json.put("message", "网络或参数,找回密码失败");
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.forgotPasswordService;
	}
	
}
