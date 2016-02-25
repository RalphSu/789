package com.oms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserLoginLog;
import com.icebreak.p2p.front.controller.authority.LoginController;
import com.icebreak.p2p.login.LoginService;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.IPAddressUtils;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.util.lang.ip.IPUtil;

@Controller
@RequestMapping("weichat")
public class WeiUserController extends LoginController {
	@Autowired
	private final LoginService loginService = null;
	@Autowired
	UserBaseInfoManager userBaseInfoManager;
	@Autowired
	private final AuthorityService authorityService = null;
	
	private final String wc_path = "/weichat/";
	
	@RequestMapping("doLogin.do")
	public String doLogin(String userName, String password, String redirect,
			String captcha, HttpSession session, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		session.setAttribute("test", "test");
		UserBaseInfoDO baseInfoDO = loginService.login(userName, password);
		if (baseInfoDO == null) {
			int pwdErrorTimes = 0;
			try {
				baseInfoDO = userBaseInfoManager.queryByUserName(userName, 1);
				if (baseInfoDO.getState().equals("locked")) {
					return "redirect:login.do?message=" + LOGIN_MESSAGE_LOCKED
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
				return "redirect:login.do?message=" + LOGIN_USER_UNKNOWN
						+ "&redirect=" + redirect;
			}
			return "redirect:login.do?message=" + LOGIN_MESSAGE + pwdErrorTimes
					+ URLEncoder.encode("次，", "UTF-8") + LOGIN_WONGMSG_TIP
					+ "&redirect=" + redirect;
		}
		if (baseInfoDO.getState().equals("disable")) {
			return "redirect:login.do?message=" + LOGIN_MESSAGE_DISABLE
					+ "&redirect=" + redirect;
		}
		if (baseInfoDO.getState().equals("locked")) {
			return "redirect:login.do?message=" + LOGIN_MESSAGE_LOCKED
					+ "&redirect=" + redirect;
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
			redirect = "/weichat/first.do";
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
	
	@RequestMapping("login.do")
	public String login(HttpServletRequest request, Model model){
		String message = request.getParameter("message");
		model.addAttribute("message", message);
		return wc_path + "login.vm";
	}
}
