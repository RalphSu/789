package com.icebreak.p2p.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.authority.AuthorityService;
import com.icebreak.p2p.base.OpenApiBaseService;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.integration.openapi.CustomerService;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.user.*;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.MD5Util;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.UserStateEnum;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class RegisterServiceImpl extends OpenApiBaseService implements RegisterService {
	
	@Autowired
	UserBaseInfoManager userBaseInfoManager;
	@Autowired
	UserRelationManager userRelationManager;
	@Autowired
	AuthorityService authorityService;
	@Autowired
	CustomerService customerService;
	@Autowired
	PersonalInfoManager personalInfoManager;
	@Autowired
	InstitutionsInfoManager institutionsInfoManager;
	
	@Override
	public JSONObject personalRegister(HttpSession session, PersonalInfoDO personalInfo,
										UserBaseInfoDO userBaseInfo, String token) throws Exception {
		JSONObject json = new JSONObject();
		String getToken = (String) session.getAttribute("token");
		json = this.validationBroker(personalInfo.getReferees());
		PersonalReturnEnum personalReturnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		if (getToken != null && getToken.equals(token)) {
			try {
				if ((Integer) json.get("code") != 1) {
					return json;
				} else {
					String tpName = AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName();
					//生成平台关联的托管机构帐户
					userBaseInfo.setAccountName(AppConstantsUtil.getYrdPrefixion()
												+ userBaseInfo.getUserName());
					this.setUserBaseInfo(userBaseInfo);
					int[] roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
												SysUserRoleEnum.INVESTOR.getValue() };
					if (sysFunctionConfigService.isAllEconomicMan()
						&& StringUtil.isNotBlank(personalInfo.getReferees())) {
						roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
											SysUserRoleEnum.INVESTOR.getValue(),
											SysUserRoleEnum.BROKER.getValue() };
					}
					personalReturnEnum = personalInfoManager.addPersonalInfo(personalInfo,
						userBaseInfo, json.getLong("parentId"), roles);
					
					logger.info("投资者自主注册，结果：{}", personalReturnEnum);
					if (personalReturnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
						json.put("code", 1);
						json.put("message", "个人用户注册成功");
//						this.sendEmail(userBaseInfo, json);
					}
				}
			} catch (Exception e) {
				logger.error("个人用户注册失败", e);
				json.put("code", 0);
				json.put("message", "个人用户注册失败");
			}
		}
		this.cleanSession(session);
		return json;
	}
	
	@Override
	public JSONObject institutionsRegister(HttpSession session, InstitutionsInfoDO institution,
											UserBaseInfoDO userBaseInfo, String token) {
		InstitutionsReturnEnum institutionReturnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
		JSONObject json = new JSONObject();
		String getToken = (String) session.getAttribute("token");
		if (getToken != null && getToken.equals(token)) {
			try {
				json = this.validationBroker(institution.getReferees());
				if ((Integer) json.get("code") != 1) {
					return json;
				} else {
					String yjfName = AppConstantsUtil.getYrdPrefixion()
										+ userBaseInfo.getUserName();
					this.setUserBaseInfo(userBaseInfo);
					institutionReturnEnum = institutionsInfoManager.addInstitutionsInfo(
						institution, userBaseInfo, json.getLong("parentId"), new int[] { 1, 12 });
					
					logger.info("机构用户注册，结果：{}", institutionReturnEnum);
					if (institutionReturnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
						this.sendEmail(userBaseInfo, json);
						json.put("code", 1);
						json.put("message", "个人用户注册成功");
						
					}
				}
				
			} catch (Exception e) {
				logger.error("个人用户注册失败", e);
				json.put("code", 0);
				json.put("message", "个人用户注册失败");
			}
		}
		this.cleanSession(session);
		
		return json;
	}
	
	/**
	 * 验证推荐人(经纪人)
	 * @param referees
	 * @return
	 */
	public JSONObject validationBroker(String referees) {
		JSONObject json = new JSONObject();
		long parentId = 0l;
		//		boolean participateOBN =false;
		//		long relatedUserId = 0;
		Page<UserRelationDO> page = null;
		List<UserRelationDO> lst = null;
		if (StringUtil.isBlank(referees)) {
			try {
				parentId = this.initializeYrdAccountInfo();
				page = userRelationManager.getRelationsByConditions(null, null, parentId, null);
				lst = page.getResult();
				if (lst.size() > 0) {
					json.put("code", 1);
					json.put("parentId", parentId);
					json.put("referees", lst.get(0).getMemberNo());
					json.put("message", "查询" + AppConstantsUtil.getProductName() + "经纪人编号成功");
				}
			} catch (Exception e) {
				logger.error("注册时查询" + AppConstantsUtil.getProductName() + "经纪人编号异常", e);
				json.put("code", 0);
				json.put("message", "注册时查询" + AppConstantsUtil.getProductName() + "经纪人编号异常");
			}
		} else {
			page = userRelationManager.getRelationsByConditions(null, null, null, referees);
			lst = page.getResult();
			if (lst.size() > 0) {
				long refereeId = lst.get(0).getChildId();
				boolean isBroker = false;
				Pagination<Role> rolesPage = authorityService.getRolesByUserId(refereeId, 0, 99);
				if (rolesPage.getResult() != null && rolesPage.getResult().size() > 0) {
					for (Role role : rolesPage.getResult()) {
						if (SysUserRoleEnum.BROKER.getRoleCode().equals(role.getCode())) {
							isBroker = true;
							break;
						}
					}
				}
				if (isBroker) {
					parentId = lst.get(0).getChildId(); //推荐人是经纪人
					json.put("code", 1);
					json.put("parentId", parentId);
				} else {
					json.put("code", 0);
					json.put("message", "推荐人编号必须为有效的经纪人编号!");
					
				}
				
			} else {
				json.put("code", 0);
				json.put("message", "推荐人不存在，请录入有效的经纪人编号!");
				
			}
		}
		return json;
	}
	
	/**
	 * 设置自主注册用户基本信息
	 * @param userBaseInfo
	 */
	public void setUserBaseInfo(UserBaseInfoDO userBaseInfo) {
		//生成平台关联的托管机构帐户
		userBaseInfo
			.setAccountName(AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName());
		if (StringUtil.isBlank(userBaseInfo.getLogPassword())) {
			userBaseInfo.setLogPassword("888888");
			userBaseInfo.setPayPassword("888888");
		}
		userBaseInfo.setMobileBinding(BooleanEnum.NO.code());
		userBaseInfo.setMailBinding(BooleanEnum.IS.code());
//		userBaseInfo.setState(UserStateEnum.INACTIVE.code());
		//取消邮件验证，注册直接设置为normal 15.01.19
		userBaseInfo.setState(UserStateEnum.NORMAL.code());
	}
	
	/**
	 * 注册成功发送邮件
	 * @param session
	 * @param userBaseInfo
	 * @param json
	 */
	public void sendEmail(UserBaseInfoDO userBaseInfo, JSONObject json) {
		String email = userBaseInfo.getMail();
		String emailUrl = "http://mail."
							+ email.substring(email.lastIndexOf('@') + 1, email.lastIndexOf("."))
							+ ".com";
		json.put("userName", userBaseInfo.getUserName());
		json.put("emailUrl", emailUrl);
		json.put("email", userBaseInfo.getMail());
		String resendEmailUrl = "/anon/resendEmail/"
								+ MD5Util.getMD5_32(userBaseInfo.getUserBaseId());
		json.put("resendEmailUrl", resendEmailUrl);
	}
	
	/**
	 * 清除session值
	 * @param session
	 */
	public void cleanSession(HttpSession session) {
		session.removeAttribute("token");
		session.removeAttribute("brokerNo");
		session.removeAttribute("referNotExist");
	}
	
	private long initializeYrdAccountInfo() throws Exception {
		UserBaseInfoDO brokerUser = userBaseInfoManager.queryByUserName(
			AppConstantsUtil.getDefaultBrokerUserName(), 11L);
		long yrd_BrokerId = brokerUser.getUserId();
		return yrd_BrokerId;
	}
	
}
