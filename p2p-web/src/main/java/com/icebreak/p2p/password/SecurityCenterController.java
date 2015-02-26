package com.icebreak.p2p.password;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.integration.openapi.order.MobileBindOrder;
import com.icebreak.p2p.integration.openapi.order.RegisterOrder;
import com.icebreak.p2p.integration.openapi.result.CustomerResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.RSAUtils;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.util.StringUtil;

@Controller
@RequestMapping("security")
public class SecurityCenterController extends BaseAutowiredController {
	
	private final String	VM_PATH	= "/front/security/";
	
	@RequestMapping("center")
	public String securityCenter(HttpSession session, Model model) throws Exception {
		session.setAttribute("current", 4);
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
			.getSessionLocal().getUserBaseId());
		String mobile = userBaseInfo.getMobile();
		String mail = userBaseInfo.getMail();
		String[] strMail = mail.split("@");
		if (StringUtil.isNotBlank(mobile) && mobile.length() > 10) {
			mobile = mobile.substring(0, 3) + "*****"
						+ mobile.substring(mobile.length() - 3, mobile.length());
		}
		if (CommonUtil.checkEmail(mail)) {
			mail = strMail[0].subSequence(0, 3) + "*******@" + strMail[1];
		}
		String passwordType1 = "AUTHLEVELONE";
		String passwordType2 = "AUTHLEVELTWO";
		long countLv1 = userService.countUserPwdExistByType(SessionLocalManager.getSessionLocal()
			.getUserBaseId(), passwordType1);
		long countLv2 = userService.countUserPwdExistByType(SessionLocalManager.getSessionLocal()
			.getUserBaseId(), passwordType2);
		if (countLv1 > 0 || countLv2 > 0) {
			model.addAttribute("isAuthCodeSet", "true");
		}
		model.addAttribute("mobile", mobile);
		model.addAttribute("mail", mail);
		model.addAttribute("info", userBaseInfo);
		return VM_PATH + "securityCenter.vm";
	}
	
	@ResponseBody
	@RequestMapping("updatePassword")
	public Object updatePassword(String password, String newPassword, String newPasswordTo,
									String type) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		JSONObject jsonobj = new JSONObject();
		password = RSAUtils.decryptStringByJs(password);
		newPassword = RSAUtils.decryptStringByJs(newPassword);
		newPasswordTo = RSAUtils.decryptStringByJs(newPasswordTo);
		if ("logPassword".equalsIgnoreCase(type)) {
			returnEnum = userBaseInfoManager.validationLogPassword(SessionLocalManager
				.getSessionLocal().getUserBaseId(), password);
			if (newPassword.equals(newPasswordTo)
				&& returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS) {
				returnEnum = userBaseInfoManager.updateLogPassword(SessionLocalManager
					.getSessionLocal().getUserBaseId(), newPassword);
			}
		}
		if ("payPassword".equalsIgnoreCase(type)) {
			returnEnum = userBaseInfoManager.validationPayPassword(SessionLocalManager
				.getSessionLocal().getUserBaseId(), password);
			if (newPassword.equals(newPasswordTo)
				&& returnEnum == UserBaseReturnEnum.PASSWORD_SUCCESS) {
				returnEnum = userBaseInfoManager.updatePayPassword(SessionLocalManager
					.getSessionLocal().getUserBaseId(), newPassword);
			}
		}
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "设置新密码成功");
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "设置新密码失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("boundMobile")
	public Object boundMobile(String mobile, String code) throws Exception {
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
			.getSessionLocal().getUserBaseId());
		userBaseInfo.setMobile(mobile);
		MobileBindOrder mobileBindOrder = new MobileBindOrder();
		mobileBindOrder.setUserId(userBaseInfo.getAccountId());
		mobileBindOrder.setMobile(mobile);
		mobileBindOrder.setIsNew(BooleanEnum.YES);
		P2PBaseResult baseResult = this.customerService.updateMobileBinding(mobileBindOrder,
			this.getOpenApiContext());
		userBaseInfo.setMobileBinding("IS");
		if (baseResult.isSuccess()) {
			returnEnum = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
			if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "绑定手机成功");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "绑定手机失败");
			}
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "绑定手机失败");
		}
		
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("updateBoundMobile")
	public Object updateBoundMobile(String mobile, String newMobile, String code) throws Exception {
		JSONObject jsonobj = new JSONObject();
		if(StringUtil.isBlank(newMobile)){
			jsonobj.put("code", 0);
			jsonobj.put("message", "手机号码丢失！");
			return jsonobj;
		}
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		UserBaseInfoDO existUser = userBaseInfoManager.queryByUserName(newMobile, -1l);
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
			.getSessionLocal().getUserBaseId());
		if((!existUser.getUserBaseId().equals(userBaseInfo.getUserBaseId())) && (existUser.getUserName().equals(userBaseInfo.getUserName()))){
			//如果已存在新手机号的用户 且这个用户不是当前用户时，说明手机号码重复
			jsonobj.put("code", 0);
			jsonobj.put("message", "手机号码重复！");
			return jsonobj;
		}
		userBaseInfo.setMobile(newMobile);
		MobileBindOrder mobileBindOrder = new MobileBindOrder();
		mobileBindOrder.setUserId(userBaseInfo.getAccountId());
		mobileBindOrder.setMobile(newMobile);
		mobileBindOrder.setIsNew(BooleanEnum.NO);
		P2PBaseResult baseResult = this.customerService.updateMobileBinding(mobileBindOrder,
			this.getOpenApiContext());
		if (baseResult.isSuccess()) {
			userBaseInfo.setMobileBinding("IS");
		}
		returnEnum = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "修改绑定手机成功");
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修改绑定手机失败");
		}
		
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("boundUpdateMail")
	public Object boundUpdateMail(String newMail, String mailCode) throws Exception {
		JSONObject jsonobj = new JSONObject();
		UserBaseReturnEnum returnEnum = UserBaseReturnEnum.EXECUTE_FAILURE;
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
			.getSessionLocal().getUserBaseId());
        RegisterOrder order = new RegisterOrder();
        order.setUserId(userBaseInfo.getAccountId());
        order.setEmail(newMail);
		userBaseInfo.setMail(newMail);
		userBaseInfo.setMailBinding("IS");
       //TODO
		returnEnum = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "修改绑定邮箱成功");
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修改绑定邮箱失败");
		}
		return jsonobj;
	}
	
	@RequestMapping("boundPhone")
	public String boundPhone(HttpSession session, Model model) throws Exception {
		session.setAttribute("current", 4);
		return VM_PATH + "boundPhone.vm";
	}
	
	@RequestMapping("completeBoundPhone")
	public String completeBoundPhone(String mobile, String code, Model model) throws Exception {
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
			.getSessionLocal().getUserBaseId());
		userBaseInfo.setMobile(mobile);
		userBaseInfo.setMobileBinding("IS");
		UserBaseReturnEnum returnEnum = userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		String mobile_q3 = mobile.substring(0, 3);
		String mobile_h4 = mobile.substring(mobile.length() - 4, mobile.length());
		String mobile_h3_h4 = mobile_q3 + "****" + mobile_h4;
		if (returnEnum == UserBaseReturnEnum.EXECUTE_SUCCESS) {
			model.addAttribute("info", "您的手机号码：" + mobile_h3_h4 + "，绑定成功。");
			model.addAttribute("result", "success");
		}
		if (returnEnum == UserBaseReturnEnum.EXECUTE_FAILURE) {
			model.addAttribute("info", "您的手机号码：" + mobile_h3_h4 + "，绑定失败。");
			model.addAttribute("result", "failure");
		}
		return VM_PATH + "completeBoundPhone.vm";
	}
	
}
