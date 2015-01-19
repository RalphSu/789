package com.icebreak.p2p.rs.service.userManage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.icebreak.p2p.dataobject.Role;
import com.icebreak.util.lang.ip.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserLoginLog;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.session.common.Constant;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.IPAddressUtils;
import com.icebreak.p2p.util.MD5Util;

public class LoginService extends ServiceBase implements AppService {
	
	protected final Logger	logger					= LoggerFactory.getLogger(getClass());
	/**
	 * 登录成功提示信息
	 */
	private static String	LOGIN_SUCCESS_MESSAGE	= "用户登录成功";
	/**
	 * 登录提示信息
	 */
	private static String	LOGIN_FIAL_MESSAGE		= "用户名或者密码连续错误";
	/**
	 * 用户不可用
	 */
	private static String	LOGIN_MESSAGE_DISABLE	= "该用户不可用";
	/**
	 * 未激活
	 */
	private static String	LOGIN_MESSAGE_UNACTIVE	= "该用户未激活";
	/**
	 * 账户已被锁定
	 */
	private static String	LOGIN_MESSAGE_LOCKED	= "此账户已被锁定，请等待解锁";
	/**
	 * 账户不存在
	 */
	private static String	LOGIN_USER_UNKNOWN		= "账户不存在";
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		try {
			String userName = params.get("userName").toString();
			String password = params.get("password").toString();
			//String secKey = params.get("secKey").toString();
			UserBaseInfoDO baseInfoDO = loginService.login(userName, password);
			if (baseInfoDO == null) {
				int pwdErrorTimes = 0;
				try {
					baseInfoDO = userBaseInfoManager.queryByUserName(userName, 1);
					if (baseInfoDO.getState().equals("locked")) {
						json.put("code", 0);
						json.put("message", LOGIN_MESSAGE_LOCKED);
						return json;
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
					logger.error("无此用户");
					json.put("code", 0);
					json.put("message", LOGIN_USER_UNKNOWN);
					return json;
				}
				json.put("code", 0);
				json.put("message", LOGIN_FIAL_MESSAGE + pwdErrorTimes + "次...");
				return json;
			}
			if (baseInfoDO.getState().equals("disable")) {
				json.put("code", 0);
				json.put("message", LOGIN_MESSAGE_DISABLE);
				return json;
			}
			if (baseInfoDO.getState().equals("locked")) {
				json.put("code", 0);
				json.put("message", LOGIN_MESSAGE_LOCKED);
				return json;
			}
			if (baseInfoDO.getState().equals("inactive")) {
				json.put("code", 2);
				json.put("message", LOGIN_MESSAGE_UNACTIVE);
				String MD5UserBaseId = MD5Util.getMD5_32(baseInfoDO.getUserBaseId());
				json.put("MD5UserBaseId", MD5UserBaseId);
				return json;
			} else {
				json.put("code", 1);
				json.put("message", LOGIN_SUCCESS_MESSAGE);

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
					.getPermissionsByUserId(baseInfoDO.getUserId()), baseInfoDO.getUserId(),
					baseInfoDO.getUserBaseId(), roleCodesStr, baseInfoDO.getAccountId(), baseInfoDO
						.getAccountName(), baseInfoDO.getRealName(), baseInfoDO.getUserName(), IPUtil.getIpAddr(request)));
				session.setAttribute("sessionInvalidCheck", "30");
				try {
					baseInfoDO.setPwdErrorCount(0);
					userBaseInfoManager.updateUserBaseInfo(baseInfoDO);
				} catch (Exception e) {
					logger.error("重置密码错误次数异常");
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
				String sessionId = Constant.getSessionId();
				logger.info("移动终端登录信息--" + AppConstantsUtil.getProductName() + "用户"
							+ baseInfoDO.getUserName() + ",托管机构资金账户ID--" + baseInfoDO.getAccountId()
							+ "/" + sessionId);
				String appKey = sessionId;
				json.put("appKey", appKey);
			}
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数异常或不完整");
			logger.info("参数异常或不完整", e);
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.userLogin;
	}
	
}
