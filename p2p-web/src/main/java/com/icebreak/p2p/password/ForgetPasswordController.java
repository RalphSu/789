package com.icebreak.p2p.password;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.common.info.SessionMobileSend;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.session.Session;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.util.security.AES;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("anon")
public class ForgetPasswordController extends BaseAutowiredController {
	
	private final String	VM_PATH	= "/front/password/";


	@RequestMapping("sendSmsVerifyCode")
	public @ResponseBody Map<String, String> sendMobileCode(Model model, String userName,
															String mobile) throws Exception {
		Validate.notEmpty(mobile, "手机号不能为空");
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserName(userName, 12L);
		if (null == userBaseInfo) {
			throw new Exception("未找到对应的用户信息");
		}
		//若输入的手机号与绑定的不同，则不能修改密码
		if (!mobile.equals(userBaseInfo.getMobile())) {
			throw new Exception("输入的手机号与绑定用户绑定的手机号不同");
		}
		//TODO 发送短信接口
		Map<String, String> map = new HashMap<String, String>();
		map.put("res", "success");
		return map;
	}

	@RequestMapping(value = "findpwd")
	public String findPassword(Model model) {
		String token = UUID.randomUUID().toString();
		model.addAttribute("token", token);
		return VM_PATH + "findpwd.vm";
	}
	
	@RequestMapping(value = "setNewPassword")
	public String input(String userName, String mobile, String verifyCode, Model model) {
		String token = UUID.randomUUID().toString();
		//用户名就是手机号码
		userName = mobile;
		model.addAttribute("token", token);
		model.addAttribute("userName", userName);
		model.addAttribute("mobile", mobile);

		//验证短信
		SessionMobileSend validCode = (SessionMobileSend) Session.getAttribute("session_mobile_send");
		if(validCode == null){
			model.addAttribute("userName", userName);
			model.addAttribute("reason", "短信验证码不匹配，请先获取短信验证码！");
			return VM_PATH + "findpwd.vm";
		}

		if(verifyCode == null || !verifyCode.equals(validCode.getCode())){
			model.addAttribute("userName", userName);
			model.addAttribute("reason", "短信验证码不匹配！");
			return VM_PATH + "findpwd.vm";
		}
		return VM_PATH + "setNewPassword.vm";
	}
	
	@RequestMapping(value = "doSetNewPassword")
	public String doSetNewPassword(HttpSession session, String userName,
									String newLogPassword, String token, Model model)
																						throws Exception {
		String getToken = (String) session.getAttribute("token");
		if (token == null || !token.equalsIgnoreCase(token)) {
			return VM_PATH + "newPasswordOk.vm";// 重复提交
		}
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserName(userName, 12L);
		if ("locked".equals(userBaseInfo.getState())) {
			userBaseInfo.setState("normal");
			userBaseInfo.setPwdErrorCount(0);
			userBaseInfo.setChangeLockTime(new Date());
			userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		}
//		UserBaseReturnEnum returnEnum = userBaseInfoManager.updateLogPassword(
//			userBaseInfo.getUserBaseId(), MD5Util.getMD5_16(newLogPassword));
		//SQL语句进行了密码MD5加密，为了与登录保持一致，这里加密去掉
		UserBaseReturnEnum returnEnum = userBaseInfoManager.updateLogPassword(
			userBaseInfo.getUserBaseId(), newLogPassword);
		if (returnEnum != UserBaseReturnEnum.EXECUTE_SUCCESS) {
			return VM_PATH + "newPasswordNo.vm";// 修改登录密码出错啦
		}
		return VM_PATH + "setNewPasswordSuccess.vm";
	}
	
	
	
	//=====================================================================================================
	
	@RequestMapping("newLogPasswordMailOk")
	public String newLogPasswordMailOk(String userName, String mail, String code,
										HttpSession session, Model model) throws Exception {
		UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserName(userName, 1);
		long sendTime = System.currentTimeMillis();
		String securityKey = "2r0e1g3i1s2t2e3d";
		AES aes = new AES(securityKey);
		String encriptData = aes.getEncrypt(String.valueOf(sendTime));
		//发送邮件
		mailService.sendMail(SendInformation.sendMail(mail, userBaseInfo.getRealName(),
			"/PasswordManage/ForgetLogPassword/" + MD5Util.getMD5_32(userBaseInfo.getUserBaseId())
					+ "," + encriptData, 20L), "HTML");
		String mainUrl = "http://mail."
							+ mail.substring(mail.lastIndexOf('@') + 1, mail.lastIndexOf("."))
							+ ".com";
		
		String[] strMail = mail.split("@");
		model.addAttribute("mainUrl", mainUrl);
		model.addAttribute("mail", mail);
		model.addAttribute("mailStr", strMail[0].substring(0, 3) + "********@" + strMail[1]);
		return VM_PATH + "newLogPasswordMailOk.vm";
	}
	
	//		自动登录设置
	//		SessionLocalManager.setSessionLocal(new SessionLocal(
	//						authorityService.getPermissionsByUserId(userBaseInfo.getUserId()),
	//						userBaseInfo.getUserId(),
	//						userBaseInfo.getUserBaseId(),
	//						userBaseInfo.getAccountId(),
	//						userBaseInfo.getAccountName(),
	//						userBaseInfo.getRealName(),
	//						userBaseInfo.getUserName())
	//				);
}
