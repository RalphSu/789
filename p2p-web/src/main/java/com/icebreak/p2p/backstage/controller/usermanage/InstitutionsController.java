package com.icebreak.p2p.backstage.controller.usermanage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.integration.openapi.order.MobileBindOrder;
import com.icebreak.p2p.integration.openapi.order.RegisterOrder;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("backstage/userManage")
public class InstitutionsController extends BaseAutowiredController {
	/** 通用页面路径 */
	String USER_MANAGE_PATH = "/backstage/userManage/";
	
	@Override
	protected String[] getDateInputNameArray() {
		return new String[] { "changeLockTime" };
	}
	
	@RequestMapping("institutionManage")
	public String institutionsManage(QueryConditions queryConditions, PageParam pageParam,
										Model model) throws Exception {
		Page<InstitutionsInfoDO> page = institutionsInfoManager.page(queryConditions, pageParam);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("page", page);
		return USER_MANAGE_PATH + "institutionsManage.vm";
	}
	
	@RequestMapping("institutionManage/addInstitutionsUser")
	public String addInstitutionsUser(Model model) throws Exception {
		model.addAttribute("uploadHost", "");
		return USER_MANAGE_PATH + "addInstitutionsUser.vm";
	}
	
	@ResponseBody
	@RequestMapping("institutionManage/institutionsManageSubmit")
	public Object institutionsManageSubmit(InstitutionsInfoDO institutionsInfo,
											UserBaseInfoDO userBaseInfo, int... roleIds) {
		JSONObject jsonobj = new JSONObject();
		
		long parentId = -2;
		for (int roleId : roleIds) {
			if (12 == roleId) {
				if (institutionsInfo.getReferees() != null
					&& !"".equals(institutionsInfo.getReferees())) {
					Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
						null, null, institutionsInfo.getReferees());
					List<UserRelationDO> lst = page.getResult();
					if (lst.size() > 0) {
						parentId = lst.get(0).getChildId();
					} else {
						jsonobj.put("code", 0);
						jsonobj.put("message", "该推荐人不可用");
						return jsonobj;
					}
				} else {
					try {
						UserBaseInfoDO userBase = userBaseInfoManager.queryByUserName(
							AppConstantsUtil.getDefaultBrokerUserName(), 11);
						parentId = userBase.getUserId();
					} catch (Exception e) {
						logger.error("企业用户注册时查询推荐人异常", e);
						jsonobj.put("code", 0);
						jsonobj.put("message", "企业用户注册时查询推荐人异常");
						return jsonobj;
					}
				}
			}
		}
		userBaseInfo.setRealName(institutionsInfo.getEnterpriseName());
		//生成平台关联的托管机构帐户
		userBaseInfo
			.setAccountName(AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName());
		//生成平台关联的托管机构帐户
		userBaseInfo
			.setAccountName(AppConstantsUtil.getYrdPrefixion() + userBaseInfo.getUserName());
		InstitutionsReturnEnum returnEnum = InstitutionsReturnEnum.EXECUTE_FAILURE;
		try {
			//设置密码
			userBaseInfo.setLogPassword("888888");
			userBaseInfo.setPayPassword("888888");
			userBaseInfo.setMobileBinding(BooleanEnum.NO.code());
			userBaseInfo.setMailBinding(BooleanEnum.NO.code());
			returnEnum = institutionsInfoManager.addInstitutionsInfo(institutionsInfo,
				userBaseInfo, parentId, roleIds);
		} catch (Exception e) {
			logger.error("updateTradeExpireDate", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建机构用户失败");
		}
		if (returnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "创建机构用户成功");
			try {
				userBaseInfo = userBaseInfoManager.queryByUserName(userBaseInfo.getUserName(), 0);
			} catch (Exception e) {
				logger.error("查询用户失败", e);
			}
		}
		if (returnEnum == InstitutionsReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建机构用户失败");
		}
		return jsonobj;
	}
	
	@RequestMapping("institutionManage/updateInstitutionsUser")
	public String updateInstitutionsUser(String userBaseId, long userId, Model model)
																						throws Exception {
		model.addAttribute("uploadHost", "");
		List<Role> roleList = authorityService.getRolesByUserId(userId,
			SysUserRoleEnum.PUBLIC.getValue(), SysUserRoleEnum.INVESTOR.getValue()).getResult();
		InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
		model.addAttribute("roleList", roleList);
		model.addAttribute("info", institutionsInfo);
		return USER_MANAGE_PATH + "updateInstitutionsUser.vm";
	}
	
	@ResponseBody
	@RequestMapping("institutionManage/updateInstitutionsUserSubmit")
	public Object updateInstitutionsUserSubmit(InstitutionsInfoDO institutionsInfo,
												UserBaseInfoDO userBaseInfo, int... roleIds)
																							throws Exception {
		JSONObject jsonobj = new JSONObject();
		if (userBaseInfo.getRealNameAuthentication() == null
			|| "".equals(userBaseInfo.getRealNameAuthentication())) {
			userBaseInfo.setRealNameAuthentication(BooleanEnum.NO.code());
		}
		if (userBaseInfo.getMobileBinding() == null || "".equals(userBaseInfo.getMobileBinding())) {
			userBaseInfo.setMobileBinding(BooleanEnum.NO.code());
		}
		if (userBaseInfo.getMailBinding() == null || "".equals(userBaseInfo.getMailBinding())) {
			userBaseInfo.setMailBinding(BooleanEnum.NO.code());
		}
		if (userBaseInfo.getPwdErrorCount() == null || "".equals(userBaseInfo.getPwdErrorCount())) {
			userBaseInfo.setPwdErrorCount(0);
		}
		userBaseInfo.setRealName(institutionsInfo.getEnterpriseName());
		if (userBaseInfo != null) {
			UserBaseInfoDO userBase = userBaseInfoManager.queryByUserId(userBaseInfo.getUserId());
			if (userBase != null) {
				//不相同同步邮箱
				if (!StringUtil.equalsIgnoreCase(userBaseInfo.getMail(), userBase.getMail())) {
					if (StringUtil.isNotEmpty(userBaseInfo.getMail())
						&& StringUtil.isNotEmpty(userBase.getAccountId())) {
						RegisterOrder order = new RegisterOrder();
						order.setEmail(userBaseInfo.getMail());
						order.setUserId(userBase.getAccountId());
						
						
						//TODO
					}
					
				}
				//不相同同步手机
				if (!StringUtil.equalsIgnoreCase(userBaseInfo.getMobile(), userBase.getMobile())) {
					if (StringUtil.isNotEmpty(userBaseInfo.getMobile())
						&& StringUtil.isNotEmpty(userBase.getAccountId())) {
						MobileBindOrder mobileBindOrder = new MobileBindOrder();
						mobileBindOrder.setUserId(userBase.getAccountId());
						mobileBindOrder.setMobile(userBaseInfo.getMobile());
						mobileBindOrder.setIsNew(BooleanEnum.NO);
						P2PBaseResult baseResult = customerService.updateMobileBinding(
							mobileBindOrder, this.getOpenApiContext());
						if (!baseResult.isSuccess()) {
							logger.info("同步手机失败", baseResult.getMessage());
							jsonobj.put("code", 0);
							jsonobj.put("message", baseResult.getMessage());
							return jsonobj;
						}
					}
					
				}
			}
		}
		InstitutionsReturnEnum returnEnum = institutionsInfoManager.updateInstitutionsInfo(
			institutionsInfo, userBaseInfo, true, roleIds);
		if (returnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "修改机构信息成功");
		}
		if (returnEnum == InstitutionsReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修改机构信息失败");
		}
		return jsonobj;
	}
	
	/**
	 * 后台企业实名认证
	 * @param userBaseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("institutionManage/updateInstitutionsUser/backEnterpriseRealNameAuth")
	public Object backEnterpriseRealNameAuth(String userBaseId) {
		JSONObject jsonobj = new JSONObject();
		try {
			UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
			InstitutionsInfoDO institutionsInfo = institutionsInfoManager.query(userBaseId);
			
			userBaseInfoManager.updateUserBaseInfo(userBaseInfo);
		} catch (Exception e) {
			logger.error("后台企业实名发送或更新实名状态异常", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "后台企业实名发送或更新实名状态异常");
		}
		return jsonobj;
	}
	
}
