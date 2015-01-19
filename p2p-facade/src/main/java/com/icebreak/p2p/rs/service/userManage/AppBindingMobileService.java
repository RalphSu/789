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
import com.icebreak.util.lang.util.StringUtil;

public class AppBindingMobileService extends ServiceBase implements AppService {
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try {
			String newMobile = "";
			if (params.containsKey("newMobile")) {
				newMobile = params.get("newMobile").toString().trim();
			}
			String code = params.get("verifyCode").toString().trim();
			String mobile = params.get("mobile").toString().trim();
			String business = params.get("business").toString().trim();
			logger.info("newMobile=" + newMobile + ",code=" + code + ",mobile=" + mobile
						+ ",business=" + business);
			SmsCodeResult returnMessageCode = this.smsManagerService.verifySmsCode(mobile,
				SmsBizType.getByCode(business), code, false);
			if (!returnMessageCode.isSuccess()) {
				json.put("code", 0);
				json.put("message", "验证码校验失败");
				return json;
			}
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserId(SessionLocalManager
				.getSessionLocal().getUserId());
			userBaseInfo.setMobileBinding("IS");
			if (!StringUtil.isBlank(newMobile)) {
				userBaseInfo.setMobile(newMobile);
			} else {
				userBaseInfo.setMobile(mobile);
			}
			UserBaseReturnEnum returnEnum = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
			if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
				json.put("code", 1);
				json.put("message", "绑定手机成功");
			} else {
				json.put("code", 0);
				json.put("message", "绑定手机失败");
			}
		} catch (Exception e) {
			logger.error("移动终端绑定手机,参数或网络异常");
			json.put("code", 0);
			json.put("message", "移动终端绑定手机,参数或网络异常");
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appBindingMobileService;
	}
	
}
