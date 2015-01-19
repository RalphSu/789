package com.icebreak.p2p.front.controller.leke;



import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("leke")
public class lekeController extends UserAccountInfoBaseController{
	
	@RequestMapping("toLeke")
	public String toLeke(HttpSession session,Model model){
		
	    return "/front/leke/toLekePage.vm";
	  }
	
	 @RequestMapping("toChangeLeke")
		public String toChangeLeke(HttpSession session,Model model){
		 	
		    return "/front/leke/changeToleke.vm";
		 }
	 
	 @RequestMapping("changeToleke")
	  public String changeToleke(HttpSession session,  String token,String referees,Model model){
		 JSONObject json = new JSONObject();
		 long userId=SessionLocalManager.getSessionLocal().getUserId();
		
		 boolean unbindRole=true;
		 json = this.validationBroker(referees);
		 PersonalReturnEnum personalReturnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		 try {
			 UserBaseInfoDO userBaseInfo=userBaseInfoManager.queryByUserId(userId);
			 PersonalInfoDO personalInfo=personalInfoManager.query(userBaseInfo.getUserBaseId());
			 if ((Integer) json.get("code") != 1) {
				 model.addAttribute("result", "brokerNoExsit"); 
			 } else {
				 int[] roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
						 SysUserRoleEnum.INVESTOR.getValue() };
				 if (sysFunctionConfigService.isAllEconomicMan()&&StringUtil.isNotBlank(referees)) {
					 roles = new int[] { SysUserRoleEnum.PUBLIC.getValue(),
							 SysUserRoleEnum.INVESTOR.getValue(),
							 SysUserRoleEnum.BROKER.getValue() };
				 }
				 long parentId = json.getLong("parentId");
				
				 personalReturnEnum = personalInfoManager.updatePersonalInfoAndChangBroke(personalInfo, userBaseInfo, unbindRole,parentId, referees,roles);
				 if (personalReturnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
						model.addAttribute("result", "success"); 
				}
			 }
		 } catch (Exception e) {
			 logger.error("个人用户更改角色（经纪人）失败", e);
			 model.addAttribute("result", e.getMessage());
		 }
	    return "/front/leke/changeresult.vm";
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
		
		private long initializeYrdAccountInfo() throws Exception {
			UserBaseInfoDO brokerUser = userBaseInfoManager.queryByUserName(
				AppConstantsUtil.getDefaultBrokerUserName(), 11L);
			long yrd_BrokerId = brokerUser.getUserId();
			return yrd_BrokerId;
		}

}
