package com.icebreak.p2p.front.controller.business.guarantee;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.ws.enums.OperatorInfoEnum;

@Controller
@RequestMapping("guaranteeOperator")
public class GuaranteeOperatorManagerController extends UserAccountInfoBaseController {
	/** 返回页面路径 */
	String							GUARANTEE_MANAGE_PATH	= "/front/business/guarantee/";
	@Autowired
	protected IOperatorInfoService	operatorInfoService;
	
	//-----------------------------------------------------操作员管理----------------------------------------------------------------------
	
	@RequestMapping("operatorManager")
	public String operatorManager(HttpSession session, QueryConditions queryConditions,
									PageParam pageParam, Model model) throws Exception {
		
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		
		//session.setAttribute("current", 8);
		queryConditions.setUserId(SessionLocalManager.getSessionLocal().getUserId());
		//Page<InstitutionsInfoDO>  page = institutionsInfoManager.pageChildren(queryConditions, pageParam);
		Page<OperatorInfoDO> page = operatorInfoService.queryOperatorPage(queryConditions,
			pageParam);
		model.addAttribute("page", page);
		model.addAttribute("queryConditions", queryConditions);
		return GUARANTEE_MANAGE_PATH + "guarantee-operator-manage.vm";
	}
	
	//-----------------------------------------------------新增操作员----------------------------------------------------------------------
	
	@RequestMapping("addOperator")
	public String addOperator(HttpSession session, QueryConditions queryConditions,
								PageParam pageParam, Model model) throws Exception {
		this.initAccountInfo(model);
		session.setAttribute("current", 8);
		return GUARANTEE_MANAGE_PATH + "guarantee-add-operator.vm";
	}
	
	//-----------------------------------------------------新增操作员提交----------------------------------------------------------------------
	@ResponseBody
	@RequestMapping("addOperatorSubmit")
	public Object addOperatorSubmit(HttpSession session, UserBaseInfoDO userBaseInfo,
									OperatorInfoDO operatorInfo, PageParam pageParam, Model model,
									int... roleIds) {
		session.setAttribute("current", 8);
		JSONObject jsonobj = new JSONObject();
		long parentId = SessionLocalManager.getSessionLocal().getUserId();
		InstitutionsInfoDO institutionsInfo = null;
		if (2 == operatorInfo.getOperatorType() || 3 == operatorInfo.getOperatorType()) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("parentId", parentId);
			conditions.put("operatorType", operatorInfo.getOperatorType());
			conditions.put("limitStart", 0);
			conditions.put("pageSize", 9999);
			List<OperatorInfoDO> operatorInfos = operatorInfoService
				.queryOperatorsByProperties(conditions);
			if (operatorInfos != null && operatorInfos.size() > 0) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "创建失败,该级审核员已存在");
				return jsonobj;
			}
		} else {
			userBaseInfo.setPayPassword("888888");
		}
		
		try {
			institutionsInfo = institutionsInfoManager.query(SessionLocalManager.getSessionLocal()
				.getUserBaseId());
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建操作员失败");
			logger.error("queryinstitutionsInfoManager{}", e.getMessage(), e);
			return jsonobj;
		}
		UserBaseInfoDO parentUserBaseInfo = null;
		try {
			parentUserBaseInfo = userBaseInfoManager.queryByUserBaseId(SessionLocalManager
				.getSessionLocal().getUserBaseId());
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建操作员失败");
			logger.error("queryByUserBaseId{}", e.getMessage(), e);
			return jsonobj;
		}
		userBaseInfo = map(parentUserBaseInfo, userBaseInfo);
		userBaseInfo.setRowAddTime(Calendar.getInstance().getTime());
		PersonalReturnEnum returnEnum = PersonalReturnEnum.EXECUTE_FAILURE;
		try {
			returnEnum = institutionsInfoManager.addOperatorInstitutionInfo(institutionsInfo,
				userBaseInfo, operatorInfo, parentId, roleIds);
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建操作员失败");
			logger.error("addOperatorInstitutionInfo{}", e.getMessage(), e);
			return jsonobj;
		}
		if (returnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
			jsonobj.put("code", 1);
			jsonobj.put("message", "创建操作员成功");
		}
		if (returnEnum == PersonalReturnEnum.EXECUTE_FAILURE) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "创建操作员失败");
		}
		return jsonobj;
	}
	
	//-----------------------------------------------------投资者详情----------------------------------------------------------------------
	
	@RequestMapping("operatorInfo")
	public String operatorInfo(String userBaseId, Model model) throws Exception {
		
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		Map<String, Object> opConditions = new HashMap<String, Object>();
		opConditions.put("userBaseId", userBaseId);
		opConditions.put("limitStart", 0);
		opConditions.put("pageSize", 100);
		List<OperatorInfoDO> operatorInfos = operatorInfoService
			.queryOperatorsByProperties(opConditions);
		if (operatorInfos != null && operatorInfos.size() > 0) {
			model.addAttribute("info", operatorInfos.get(0));
			if (1 == operatorInfos.get(0).getOperatorType()) {
				model.addAttribute("auth", "yes");
			}
		}
		return GUARANTEE_MANAGE_PATH + "guarantee-operator-info.vm";
	}
	
	private UserBaseInfoDO map(UserBaseInfoDO parentUserBaseInfo, UserBaseInfoDO userBaseInfo) {
		parentUserBaseInfo.setUserName(userBaseInfo.getUserName());
		parentUserBaseInfo.setLogPassword(userBaseInfo.getLogPassword());
		parentUserBaseInfo.setPayPassword(userBaseInfo.getPayPassword());
		parentUserBaseInfo.setRowAddTime(new Date());
		parentUserBaseInfo.setMobile(userBaseInfo.getMobile());
		parentUserBaseInfo.setRealName(userBaseInfo.getRealName());
		return parentUserBaseInfo;
	}
	
	//---更新----------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping("updateOperatorSubmit")
	public Object updateOperatorSubmit(HttpSession session, UserBaseInfoDO userBaseInfo,
										OperatorInfoDO operatorInfo, PageParam pageParam,
										Model model) throws Exception {
		
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		
		JSONObject jsonobj = new JSONObject();
		
		try {
			UserBaseInfoDO newBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseInfo
				.getUserBaseId());
			newBaseInfo.setMobile(userBaseInfo.getMobile());
			newBaseInfo.setState(userBaseInfo.getState());
			if (userBaseInfo.getPayPassword() != null && !"".equals(userBaseInfo.getPayPassword())) {
				userBaseInfoManager.updatePayPassword(userBaseInfo.getUserBaseId(),
					userBaseInfo.getPayPassword());
				newBaseInfo.setPayPassword(userBaseInfo.getPayPassword());
			}
			
			UserBaseReturnEnum be = userBaseInfoManager.updateUserBaseInfo(newBaseInfo);
			
			OperatorInfoEnum oe = operatorInfoService.updateOperatorInfo(operatorInfo);
			
			if (be == UserBaseReturnEnum.EXECUTE_SUCCESS && oe == OperatorInfoEnum.EXECUTE_SUCCESS) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "更新操作员成功！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "更新操作员失败!");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "更新操作员失败！");
			logger.error("updateOperatorSubmit{}", e.getMessage(), e);
			return jsonobj;
		}
		
		return jsonobj;
	}
}
