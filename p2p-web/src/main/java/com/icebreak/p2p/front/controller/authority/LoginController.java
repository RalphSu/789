package com.icebreak.p2p.front.controller.authority;

import com.icebreak.p2p.dataobject.Role;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.icebreak.util.lang.ip.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.base.Image;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserLoginLog;
import com.icebreak.p2p.login.LoginService;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.*;
import com.icebreak.util.lang.ip.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("login")
public class LoginController extends BaseAutowiredController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 登录提示信息
	 */
	protected static String LOGIN_MESSAGE = null;
	/**
	 * 验证码错误提示信息
	 */
	private static String LOGIN_CAPTCHA_MESSAGE = null;
	/**
	 * 用户不可用
	 */
	protected static String LOGIN_MESSAGE_DISABLE = null;
	/**
	 * 未激活
	 */
	private static String LOGIN_MESSAGE_UNACTIVE = null;
	/**
	 * 密码解密异常
	 */
	private static String LOGIN_MESSAGE_DECRIPTERR = null;
	/**
	 * 账户已被锁定
	 */
	protected static String LOGIN_MESSAGE_LOCKED = null;
	/**
	 * 账户不存在
	 */
	protected static String LOGIN_USER_UNKNOWN = null;
	/**
	 * 密码错误提示
	 */
	protected static String LOGIN_WONGMSG_TIP = null;
	/**
	 * 验证码
	 */
	private static String KAPTCHA_SESSION_KEY = "kaptcha_session_key";

	static {
		try {
			LOGIN_MESSAGE = URLEncoder.encode("用户名或者密码连续错误", "UTF-8");
			LOGIN_CAPTCHA_MESSAGE = URLEncoder.encode("验证码错误...", "UTF-8");
			LOGIN_MESSAGE_DISABLE = URLEncoder.encode("该用户不可用...", "UTF-8");
			LOGIN_MESSAGE_UNACTIVE = URLEncoder.encode("该用户未激活...", "UTF-8");
			LOGIN_MESSAGE_DECRIPTERR = URLEncoder.encode("密码解密异常...", "UTF-8");
			LOGIN_MESSAGE_LOCKED = URLEncoder.encode("此账户已被锁定，请等待解锁...",
					"UTF-8");
			LOGIN_USER_UNKNOWN = URLEncoder.encode("账户不存在...", "UTF-8");
			LOGIN_WONGMSG_TIP = URLEncoder.encode(
					"同一用户名密码连续输入错误5次后账户会被锁定，次日将会解锁，也可通过找回密码解锁。", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private final LoginService loginService = null;

	@Autowired
	private final AuthorityService authorityService = null;
	@Autowired
	UserBaseInfoManager userBaseInfoManager;

	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		// CommonUtil.removeCookie(request);
		String message = request.getParameter("message");
		model.addAttribute("message", message);

		return "test/login.vm";
	}
	
	@ResponseBody
	@RequestMapping("checkImgCode")
	public Object checkImgCode(HttpSession session,String captcha, Model model){
		JSONObject json = new JSONObject();
		json.put("code", 1);
		if (!Image.checkImgCode(session, captcha)) {
			json.put("code", 0);
		}else{
			session.removeAttribute(KAPTCHA_SESSION_KEY);
		}
		return json;
	}

	@RequestMapping("dologin")
	public String doLogin(String userName, String password, String redirect,
			String captcha, HttpSession session, HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
 		session.setAttribute("test", "test");
		UserBaseInfoDO baseInfoDO = loginService.login(userName, password);
		if (baseInfoDO == null) {
			int pwdErrorTimes = 0;
			try {
				baseInfoDO = userBaseInfoManager.queryByUserName(userName, 1);
				if (baseInfoDO.getState().equals("locked")) {
					return "redirect:login?message=" + LOGIN_MESSAGE_LOCKED
							+ "&redirect=" + redirect;
				}
				pwdErrorTimes = baseInfoDO.getPwdErrorCount() + 1;
				if (5 == pwdErrorTimes) {
					logger.error("密码已错误5次---立即锁定");
					baseInfoDO.setPwdErrorCount(pwdErrorTimes);
					baseInfoDO.setState("locked");
					baseInfoDO.setChangeLockTime(new Date());
					userBaseInfoManager.updateUserBaseInfo(baseInfoDO);
				} else {
					baseInfoDO.setPwdErrorCount(pwdErrorTimes);
					userBaseInfoManager.updateUserBaseInfo(baseInfoDO);
				}
			} catch (Exception e) {
				logger.error("无此用户", e);
				return "redirect:login?message=" + LOGIN_USER_UNKNOWN
						+ "&redirect=" + redirect;
			}
			return "redirect:login?message=" + LOGIN_MESSAGE + pwdErrorTimes
					+ URLEncoder.encode("次，", "UTF-8") + LOGIN_WONGMSG_TIP
					+ "&redirect=" + redirect;
		}
		if (baseInfoDO.getState().equals("disable")) {
			return "redirect:login?message=" + LOGIN_MESSAGE_DISABLE
					+ "&redirect=" + redirect;
		}
		if (baseInfoDO.getState().equals("locked")) {
			return "redirect:login?message=" + LOGIN_MESSAGE_LOCKED
					+ "&redirect=" + redirect;
		}
		if (baseInfoDO.getState().equals("inactive")) {
			return "redirect:/anon/sendEmailPage/"
					+ MD5Util.getMD5_32(baseInfoDO.getUserBaseId());
		}
		logger.info("登录时--托管机构资金账户ID--" + baseInfoDO.getAccountId());
		//获取角色串begin
		List<Role> roleList = null;
		try {
			roleList = authorityService.getRolesByUserId(baseInfoDO.getUserId());
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
				.getPermissionsByUserId(baseInfoDO.getUserId()), baseInfoDO
				.getUserId(), baseInfoDO.getUserBaseId(), roleCodesStr, baseInfoDO
				.getAccountId(), baseInfoDO.getAccountName(), baseInfoDO
				.getRealName(), baseInfoDO.getUserName(), IPUtil
				.getIpAddr(request)));
		session.setAttribute("sessionInvalidCheck", "30");
		try {
			if (baseInfoDO.getPwdErrorCount() > 0) {
				baseInfoDO.setPwdErrorCount(0);
				userBaseInfoManager.updateUserBaseInfo(baseInfoDO);
			}
		} catch (Exception e) {
			logger.error("重置密码错误次数异常", e);
		}
		if (redirect == null || redirect.length() < 1) {
			redirect = "../";
		}
		String ip = CommonUtil.getIp(request);
		String externalId = BusinessNumberUtil.gainNumber();
		String address = IPAddressUtils.getAddresses("ip=" + ip, "utf-8");
		UserLoginLog userLoginLog = new UserLoginLog();
		userLoginLog.setTblBaseId(externalId);
		userLoginLog.setLoginAddress(address);
		userLoginLog.setLoginIp(ip);
		userLoginLog.setUserId(baseInfoDO.getUserId());
		userLoginLog.setLoginTime(new Date());
		try {
			userBaseInfoManager.addUserLoginLog(userLoginLog);
		} catch (Exception e) {
			logger.error("新增用户登录信息异常", e);
		}
		return "redirect:" + redirect;
	}

	/**
	 * 是否登录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("islogin")
	public Object isLogin() {
		return SessionLocalManager.getSessionLocal() != null;
	}

	/**
	 * 退出登录
	 * 
	 * @param redirect
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(String redirect, HttpSession session) {
		session.removeAttribute("sessionInvalidCheck");
		session.invalidate();
		SessionLocalManager.destroy();
		if (redirect != null && redirect.length() > 0) {
			return "redirect:" + redirect;
		}
		return "redirect:../";
	}

	/**
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("encryptionPwd")
	public Object encryptionPwd(String pwd) {
		JSONObject json = new JSONObject();
		try {
			PublicKey key = RSAUtils.getKeyPair().getPublic();
			String rsaPwd = RSAUtils.encryptString(key, pwd);
			json.put("code", 1);
			json.put("password", rsaPwd);
		} catch (Exception e) {
			logger.error("encryptionPwd加密异常", e);
			json.put("code", 0);
			json.put("message", "加密异常");

		}
		return json;
	}

	public String decryptionPwd(String pwd) {
		return RSAUtils.decryptStringByJs(pwd);
	}

	@ResponseBody
	@RequestMapping("keyPair")
	public Object keyPair() {
		JSONObject json = new JSONObject();
		try {
			PublicKeyMap publicKeyMap = RSAUtils.getPublicKeyMap();
			json.put("code", 1);
			json.put("modulus", publicKeyMap.getModulus());
			json.put("exponent", publicKeyMap.getExponent());
			System.out.println(publicKeyMap);
		} catch (Exception e) {
			logger.error("keyPair加密异常", e);
			json.put("code", 0);
			json.put("message", "获取Key异常");
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("validateCaptcha")
	public Object validateCaptcha(String captcha, HttpSession session) {
		JSONObject json = new JSONObject();
		try {
			if (Image.checkImgCode(session, captcha)) {
				json.put("code", 1);
				json.put("message", "验证码正确");
			} else {
				json.put("code", 0);
				json.put("message", "验证码不正确");
			}
		} catch (Exception e) {
			logger.error("验证码校验异常", e);
			json.put("code", 0);
			json.put("message", "验证码校验异常");
		}
		return json;
	}

}
