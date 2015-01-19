package com.icebreak.p2p.backstage.controller;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.util.lang.ip.IPUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admincenter")
public class BackstageLoginController extends BaseAutowiredController {
	private final String	vm_path					= "/backstage/";
	private static String	LOGIN_MESSAGE			= "用户名或者密码错误";
	private static String	LOGIN_MESSAGE_DISABLE	= "该用户不可用...";
	/**
	 * 账户已被锁定
	 */
	private static String	LOGIN_MESSAGE_LOCKED	= "此账户已被锁定，请等待解锁";
	/**
	 * 账户不存在
	 */
	private static String	LOGIN_USER_UNKNOWN		= "账户不存在";
	
	@RequestMapping("login")
	private String backstageLogin(String userName, String logPassword, Model model,
									HttpSession session, HttpServletRequest request,
									HttpServletResponse response) {
		logger.info("用户登录后台系统，入参:[username={}]{}", userName, AppConstantsUtil.getProductName());
		if (!StringUtil.isNotEmpty(userName)) {
			CommonUtil.removeCookie(request);
//			return vm_path + "backstageLogin.vm";
			return vm_path + "backLogin.vm";
		}
		UserBaseInfoDO baseInfoDO = loginService.login(userName, logPassword);
		if (baseInfoDO == null) {
			int pwdErrorTimes = 0;
			try {
				baseInfoDO = userBaseInfoManager.queryByUserName(userName, 1);
				if (baseInfoDO.getState().equals("locked")) {
					model.addAttribute("message", LOGIN_MESSAGE_LOCKED);
					return vm_path + "backLogin.vm";
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
				model.addAttribute("message", LOGIN_MESSAGE + pwdErrorTimes + "次...");
			} catch (Exception e) {
				logger.error("无此用户", e);
				model.addAttribute("message", LOGIN_USER_UNKNOWN);
			}
			CommonUtil.removeCookie(request);
			return vm_path + "backLogin.vm";
		}
		if (baseInfoDO.getState().equals("disable")) {
			model.addAttribute("message", LOGIN_MESSAGE_DISABLE);
			CommonUtil.removeCookie(request);
			return vm_path + "backLogin.vm";
		}
		session.setAttribute("sessionInvalidCheck", "31");
		if (baseInfoDO != null) {
			try {
				baseInfoDO.setPwdErrorCount(0);
				userBaseInfoManager.updateUserBaseInfo(baseInfoDO);
			} catch (Exception e) {
				logger.error("重置密码错误次数异常", e);
			}

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
				.getPermissionsByUserId(baseInfoDO.getUserId()), baseInfoDO.getUserId(), baseInfoDO
				.getUserBaseId(), roleCodesStr, baseInfoDO.getAccountId(), baseInfoDO.getAccountName(),
				baseInfoDO.getRealName(), baseInfoDO.getUserName(), IPUtil.getIpAddr(request)));
		}
		model.addAttribute("userName", userName);
		return "redirect:/backstage/backstageIdex";
	}

    @SuppressWarnings("unused")
    @RequestMapping("updateGoto")
    private String updateGoto(Model model) {
        String userName = SessionLocalManager.getSessionLocal().getUserName();
        String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
        model.addAttribute("userName", userName);
        model.addAttribute("userBaseId", userBaseId);
        return vm_path + "backstageLoginUpdate.vm";
    }

    @SuppressWarnings("unused")
    @ResponseBody
    @RequestMapping("updateUserPassword")
    private Object updateUserPassword(String newPassword) {
        JSONObject json = new JSONObject();

        try {
            userBaseInfoManager.updateLogPassword(SessionLocalManager.getSessionLocal().getUserBaseId(), newPassword);
            json.put("message", "修改密码成功");
            logger.info(SessionLocalManager.getSessionLocal().getUserName()+"更新密码成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("更新密码异常");
            json.put("message", "修改密码失败");
        }

        return json;
    }
	
}
