package com.icebreak.p2p.backstage.controller.loanmanage;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icebreak.p2p.common.services.LoanTypeService;
import com.icebreak.p2p.dal.daointerface.ExtendAttrDAO;
import com.icebreak.p2p.dal.dataobject.ExtendAttrDO;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.dataobject.DOEnum.LoanTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.backstage.controller.loanmanage.enums.LoanDemandStatusEnum;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.common.services.CommonAttachmentService;
import com.icebreak.p2p.ext.domain.ResultBase;
import com.icebreak.p2p.loandemand.result.LoanDemandResultEnum;
import com.icebreak.p2p.loandemand.valueobject.QueryConditions;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.trade.InvestService;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.user.valueobject.InstitutionsContractedInfo;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.NumberUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.web.util.AttachmentModuleType;
import com.icebreak.p2p.web.util.RateUtil;
import com.icebreak.p2p.web.util.YrdEnumUtil;
import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.DivisionWayEnum;
import com.icebreak.p2p.ws.enums.InsureWayEnum;
import com.icebreak.p2p.ws.enums.LoanBizTypeEnum;
import com.icebreak.p2p.ws.enums.LoanPeriodUnitEnum;
import com.icebreak.p2p.ws.enums.TradeFullConditionEnum;
import com.icebreak.p2p.ws.enums.YrdAuthTypeEnum;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.p2p.ws.order.CalculateLoanCostOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentQueryOrder;
import com.icebreak.p2p.ws.result.AddLoanDemandResult;
import com.icebreak.p2p.ws.result.CalculateLoanCostResult;
import com.icebreak.p2p.ws.result.CommonAttachmentResult;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.icebreak.util.lang.util.money.Money;


@Controller
@RequestMapping("backstage")
public class LoanDemandController extends BaseAutowiredController {
	/** 页面所在路径 */
	private final String		BORROWING_MANAGE__PATH	= "/backstage/borrowingManage/";

	@Autowired
	InvestService				investService;
	@Autowired
	CommonAttachmentService		commonAttachmentService;

	@Autowired
	ExtendAttrDAO extendAttrDAO;

	@Resource
	private LoanTypeService loanTypeService;
	
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "deadline", new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "investAvalibleTime", new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	private List<InstitutionsContractedInfo> getInfos(long roleId) throws Exception {
		List<InstitutionsContractedInfo> infos = institutionsInfoManager
			.queryListeEnterpriseNameAndUserId(roleId);
		return infos;
	}
	
	/** 封装回执页面 */
	private String return_vm(String module) {
		String return_vm = null;
		if ("WITE".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandWiteInfo.vm";
		}
		if ("PASSADNDISMISS".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandPassAanDismissInfo.vm";
		}
		if ("DRAFT".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandDraftInfo.vm";
		}
		if ("ALL".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandAllInfo.vm";
		}
		if ("OVER".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandAllInfo2.vm";
		}
		if ("OVERALL".equals(module)) {
			return_vm = BORROWING_MANAGE__PATH + "pageQueryLoanDemandAllInfo3.vm";
		}
		return return_vm;
	}
	
	/** 封装分页查询参数 */
	private QueryConditions getQueryConditions(QueryConditions queryConditions,
												LoanDemandStatusEnum statusEnum, String module) {
		List<String> statuList = new ArrayList<String>();
		if ("WITE".equals(module)) {
			statuList.add(LoanDemandStatusEnum.WITE.getCode());
		}
		if ("PASSADNDISMISS".equals(module)) {
			if (statusEnum == null) {
				statuList.add(LoanDemandStatusEnum.PASS.getCode());
				statuList.add(LoanDemandStatusEnum.DISMISS.getCode());
			} else {
				statuList.add(statusEnum.getCode());
			}
		}
		if ("DRAFT".equals(module)) {
			statuList.add(LoanDemandStatusEnum.DRAFT.getCode());
			statuList.add(LoanDemandStatusEnum.WITE.getCode());
		}
		if ("ALL".equals(module)) {
			statuList.add(LoanDemandStatusEnum.WITE.getCode());
			statuList.add(LoanDemandStatusEnum.PASS.getCode());
			statuList.add(LoanDemandStatusEnum.DISMISS.getCode());
			statuList.add(LoanDemandStatusEnum.DRAFT.getCode());
		}
		if ("OVERALL".equals(module)) {
			
			statuList.add(LoanDemandStatusEnum.PASS.getCode());
			statuList.add(LoanDemandStatusEnum.DISMISS.getCode());
		}
		queryConditions.setStatus(statuList);
		return queryConditions;
	}
	
	/** 分页查询借款管理 */
	@RequestMapping(value = "pageQueryLoanDemand")
	public String pageQueryLoanDemand(QueryConditions queryConditions, String module,
										LoanDemandStatusEnum statusEnum, PageParam pageParam,
										Model model) {
		try {
			model.addAttribute("guarantee", getInfos(8));
			model.addAttribute("sponsor", getInfos(9));
			queryConditions = this.getQueryConditions(queryConditions, statusEnum, module);
			//queryConditions.setMaxLoanAmount(String.valueOf(Long.parseLong(queryConditions.getMaxLoanAmount())*100));
			//queryConditions.setMaiLoanAmount(String.valueOf(Long.parseLong(queryConditions.getMaiLoanAmount())*100));
			Page<LoanDemandDO> page = loanDemandManager.pageQueryLoanDemand(queryConditions,
				pageParam);
			model.addAttribute("queryConditions", queryConditions);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.return_vm(module);
	}
	
	@RequestMapping(value = "pageDemand")
	public String pageLoanDemand(QueryTradeOrder queryOrder, PageParam pageParam, String type,
									Model model) {
		String path = "";
		try {
			model.addAttribute("guarantee", getInfos(8));
			List<String> status = new ArrayList<String>();
			status.add("2");
			status.add("8");
			if ("noLetterContract".equals(type)) {
				status.add("3");
				queryOrder.setLetterPdfUrl(null);
				queryOrder.setContractPdfUrl(null);
				queryOrder.setGuaranteeAudit("IS");
				path = "pageUpLoadFileLoan.vm";
				queryOrder.setStatus(status);
				Page<LoanDemandDO> page = loanDemandManager.pageEstablish(queryOrder, pageParam);
				model.addAttribute("page", page);
			} else if ("waitRepay".equals(type)) {
				queryOrder.setEndExpireDate(DateUtil.getWeekdayAfterDateNow(new Date())
												+ " 23:59:59");
				queryOrder.setStartExpireDate(DateUtil.simpleFormat(new Date()) + " 00:00:00 ");
				path = "pageRepayLoan.vm";
				queryOrder.setStatus(status);
				Page<Trade> page = loanDemandManager.pageWaitRepayLoan(queryOrder, pageParam);
				model.addAttribute("page", page);
			}
			model.addAttribute("queryOrder", queryOrder);
		} catch (Exception e) {
			logger.error("项目查询失败", e);
		}
		return BORROWING_MANAGE__PATH + path;
		
	}
	
	/** 查询下线需求 */
	@RequestMapping(value = "pageQueryOfflineLoanDemand")
	public String pageQueryOfflineLoanDemand(QueryConditions queryConditions, PageParam pageParam,
												Model model) {
		List<String> statuList = new ArrayList<String>();
		statuList.add(LoanDemandStatusEnum.PASS.getCode());
		queryConditions.setStatus(statuList);
		try {
			Page<LoanDemandDO> page = loanDemandManager.pageQueryOfflineLoanDemand(queryConditions,
				pageParam);
			model.addAttribute("page", page);
			model.addAttribute("queryConditions", queryConditions);
		} catch (Exception e) {
			logger.error("查询失败", e);
		}
		return BORROWING_MANAGE__PATH + "pageQueryLoanDeamndOfflineInfo.vm";
	}
	
	@RequestMapping(value = "addLoanDemand")
	public String addLoanDemand(String bizType, String amount, Model model) throws Exception {
		//获取担保机构信息，8是角色分类
		model.addAttribute("guarantee", getInfos(8));
		//获取担保人信息，9角色分类
		model.addAttribute("sponsor", getInfos(9));
		//bizType应该是区分的定向还是公共。如果为空，则设置为公共
		if (StringUtil.isNotEmpty(bizType)) {
			model.addAttribute("bizType", bizType);
		} else {
			model.addAttribute("bizType", LoanBizTypeEnum.PUBLIC.code());
		}
		//金额----不知道为什么要在这里加这个，感觉没用
		if (StringUtil.isNotEmpty(amount)) {
			model.addAttribute("amount", Long.parseLong(amount) / 100);
		}
		//附件类型
		List<CommonAttachmentTypeEnum> list = YrdEnumUtil.getAttachmentByIndustry();
		model.addAttribute("enumlist", list);
		//根据就分润阶段查询分润模版 INVESET_PHASE 为投资阶段
		model.addAttribute("invest", divisionService.getDivisionTemplatesByPhase(
			DivisionPhaseEnum.INVESET_PHASE.code(), "normal"));
		//根据就分润阶段查询分润模版  REPAY_PHASE 为还款阶段阶段
		model.addAttribute("repay", divisionService.getDivisionTemplatesByPhase(
			DivisionPhaseEnum.REPAY_PHASE.code(), "normal"));
		//这一行是忘记了删么？？
		model.addAttribute("uploadHost", "");
		//还本付息方式 是一个list
		model.addAttribute("divisionWayList", DivisionWayEnum.getAllEnum());
		//担保方式 是一个List 789没有担保 这个应该是没用的
		model.addAttribute("insureWayList", InsureWayEnum.getAllEnum());
//		model.addAttribute("loanTypes", LoanTypeEnum.getEnums());
		//查询所有融资类型
		List<LoanTypeDO> loanTypes = loanTypeService.queryList(null);
		model.addAttribute("loanTypes", loanTypes);

		return BORROWING_MANAGE__PATH + "addLoanDemand.vm";
	}
	
	private LoanDemandDO getLoanDemandDO(LoanDemandDO loanDemandDO) throws Exception {
		UserBaseInfoDO userInfo = userBaseInfoManager.queryByUserName(loanDemandDO.getUserName(),
			13);
		logger.info("获得借款人用户信息参：{}", userInfo);
		if (userInfo == null) {
			throw new Exception("用户信息不存在...");
		}
		long loanerId = userInfo.getUserId();
		/*
		String guaranteeName = institutionsInfoManager.queryByUserId(loanDemandDO.getGuaranteeId())
			.getEnterpriseName();
		logger.info("获得担保机构名称：[{}]", guaranteeName);
		String sponsorName = "";
		if (loanDemandDO.getSponsorId() != -1) {
			sponsorName = institutionsInfoManager.queryByUserId(loanDemandDO.getSponsorId())
				.getEnterpriseName();
			logger.info("获得保荐机构名称：[{}]", sponsorName);
		}*/
		loanDemandDO.setLoanerId(loanerId);
		loanDemandDO.setTradeCode(BusinessNumberUtil.gainNumber());
		/*
		loanDemandDO.setGuaranteeName(guaranteeName);
		loanDemandDO.setSponsorName(sponsorName);
		*/
		loanDemandDO.setDemandDate(new Date());
		return loanDemandDO;
	}
	/** 添加正式担保函 */
	@ResponseBody
	@RequestMapping("addLoanDemand/updateImg")
	public Object updateImgUrl(Long param_id, String param_name,String guaranteeLicenseUrl1,
								HttpServletResponse response, Model model) throws Exception {
		LoanDemandDO add = new LoanDemandDO();
		add = loanDemandManager.queryLoanDemandByDemandId(param_id);
		add.setContractPdfUrl(guaranteeLicenseUrl1);
		JSONObject json = new JSONObject();
		if(add.getContractPdfUrl().isEmpty()){
			json.put("message", "上传正式担保函出错");
		}else{
			logger.info("上传正式担保函，入参{}", add);
			LoanDemandResultEnum result = loanDemandManager.updateLoanDemand(add);
			json.put("message", result.getMessage());
		}
		
		return json;
	}
	
	/** 修改附件 */
	@ResponseBody
	@RequestMapping("add/updateImg2")
	public Object updateImgUrl2(Long param_id, String param_name, String guaranteeLicenseUrl1,
								HttpServletResponse response, HttpServletRequest request,
								Model model) throws Exception {
		
		P2PBaseResult baseResult = addAttachfile(param_id.toString(), request);
		
		JSONObject json = new JSONObject();
		logger.info("修改附件，入参{}", baseResult);
		
		if (baseResult.isSuccess()) {
			json.put("code", 1);
			json.put("message", "修改成功");
		} else {
			json.put("code", 0);
			json.put("message", "修改失败");
		}
		return json;
		
	}

	/**
	 * 从页面上获取上传的附件列表"pathName_$!info.code"，并更新到数据库中。
	 *
	 * @param param_id
	 * @param request
	 * @return
	 */
	private P2PBaseResult addAttachfile(String param_id, HttpServletRequest request) {
		//插入附件
		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		List<CommonAttachmentTypeEnum> list = YrdEnumUtil.getAttachmentByIndustry();
		for (int i = 0; i < list.size(); i++) {
			CommonAttachmentTypeEnum itemEnum = list.get(i);
			String pathValues = request.getParameter("pathName_" + itemEnum.code());
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String[] paths = path.split(",");
					if (paths.length > 1) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setBizNo(param_id);
						commonAttachmentOrder.setModuleType(itemEnum);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[0]);
						orders.add(commonAttachmentOrder);
					}
				}
			}
			
		}
		//插入项目缩略图.注：如果是在修改附件的请求中，proImgUrl是null
		String proImgUrl = request.getParameter("proImgUrl");
		if(StringUtil.isNotEmpty(proImgUrl))
		{
			CommonAttachmentOrder proImage = new CommonAttachmentOrder();
			proImage.setBizNo(param_id);
			proImage.setModuleType(CommonAttachmentTypeEnum.PRO_IMAGE);
			proImage.setIsort(0);
			proImage.setFilePhysicalPath(proImgUrl);
			proImage.setRequestPath(proImgUrl);
			orders.add(proImage);
		}

		P2PBaseResult baseResult = commonAttachmentService.insertAll(orders);
		return baseResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "addLoanDemandSubmit")
	public Object addLoanDemandSubmit(HttpServletRequest request, HttpServletResponse response,
										Model model, LoanDemandDO loanDemandDO, long... templateIds)
																									throws Exception {
		logger.info("发布借款需求，入参{}", loanDemandDO);
		JSONObject jsonobj = new JSONObject();
//		if (loanDemandDO.getGuaranteeId() <= 0) {
//			jsonobj.put("code", 0);
//			jsonobj.put("message", "未选择担保机构");
//			return jsonobj;
//		}
		//根据userBaseInfo设置一些个人信息，如果没找到对应的userBaseInfo, 会throw一个Exception
		loanDemandDO = this.getLoanDemandDO(loanDemandDO);
		//默认分成模板
		long divisionTemplateId = 0l;
		//如果这里的templateIds的长度为2，即募资阶段和还款阶段的的templateId都有的话，会到divisionTemplateTrade
		//里面去查invest_template_id = templateIds[0] 和 repay_template_id = templateIds[1]的记录。 
		//这个divisionTemplateTrade是个什么，还不知道
		long existDivisionTemplateId = divisionTemplateLoanService
			.getBaseIdByTemplateIds(templateIds);
		//接上 如果divisionTemplateTrade里面有记录，就会把divisionTemplateId设置成existDivisionTemplateId
		//如果existDivisionTemplateId找出来是空，DivisionTemplateLoanDaoImpl.getBaseIdByTemplateIds()会返回0 这种方式我也是醉了
		if (existDivisionTemplateId > 0) {
			divisionTemplateId = existDivisionTemplateId;
		} else {
			//如果existDivisionTemplateId没找到，就在divisionTemplateTrade中插入一条记录
			//但是这里DivisionTemplateLoanServiceImpl.insertDivisionTemplateLoan里面没有对templateIds做长度判断
			//可能会outOfRange啊魂淡
			divisionTemplateId = divisionTemplateLoanService.insertDivisionTemplateLoan(templateIds);
		}
		//这句的意思是查询该阶段的指定角色的分成(融资成本)是多少。这个分成是由分润模板上指定的。下一句是还款阶段的
		List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
			.valueOf(templateIds[0]));
		//你妹 又是一个templateIds[1] 如果确定了长度必然会大于1 你搞个变长参数是为了好玩吗？
		List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
			.valueOf(templateIds[1]));
		//第一阶段借款成本(利率)
		double loanInterest1 = 0;
		//第一阶段投资者成本(利率)
		double investorInterest1 = 0;
		//第二阶段借款成本(利率)
		double loanInterest2 = 0;
		//第二阶段投资者成本(利率)
		double investorInterest2 = 0;
		//总借款成本(利率)
		double totalLoanInterest = 0;
		//总投资者利率
		double totalInvestorInterest = 0;
		//投资阶段分润信息
		if (investRolelist != null && investRolelist.size() > 0) {
			//循环投资阶段所有角色
			for (DivsionRuleRole role : investRolelist) {
				//把分成率乘以100，便于计算
				double bg = CommonUtil.mulDI(role.getRule(), 100);
				//设置第一阶段和总结款利率
				totalLoanInterest = CommonUtil.addDD(totalLoanInterest, bg);
				loanInterest1 = CommonUtil.addDD(loanInterest1, bg);
				if (12 == role.getRoleId()) {
					//如果角色是投资者 还要加上投资利率
					investorInterest1 = CommonUtil.addDD(investorInterest1, bg);
					totalInvestorInterest = CommonUtil.addDD(totalInvestorInterest, bg);
				}
			}
		}
		//还款阶段分润信息 --处理逻辑和上一个if一样
		if (repayRolelist != null && repayRolelist.size() > 0) {
			for (DivsionRuleRole role : repayRolelist) {
				double bg1 = CommonUtil.mulDI(role.getRule(), 100);
				loanInterest2 = CommonUtil.addDD(loanInterest2, bg1);
				totalLoanInterest = CommonUtil.addDD(totalLoanInterest, bg1);
				if (12 == role.getRoleId()) {
					investorInterest2 = CommonUtil.addDD(investorInterest2, bg1);
					totalInvestorInterest = CommonUtil.addDD(totalInvestorInterest, bg1);
				}
			}
		}
		//设置投资者总利率
		if (totalInvestorInterest != loanDemandDO.getInterestRate()) {
			loanDemandDO.setInterestRate(totalInvestorInterest);
		}
		
		loanDemandDO.setDivisionTemplateId(divisionTemplateId);
		//年利率
		loanDemandDO.setInterestRate(CommonUtil.div(loanDemandDO.getInterestRate(), 100, 4));
		//借款金额
		loanDemandDO.setLoanAmount(loanDemandDO.getLoanAmount() * 100);
		//最低投资
		loanDemandDO.setLeastInvestAmount(loanDemandDO.getLeastInvestAmount() * 100);
		//融资完成条件 金额还是比例
		loanDemandDO.setSaturationCondition(getSaturationCondition(
			loanDemandDO.getSaturationConditionMethod(), loanDemandDO.getSaturationCondition()));
		
		logger.info("发布借款需求，完整入参{}", loanDemandDO);
		//暂时取消时间验证
//		if (loanDemandDO.getInvestAvalibleTime().before(new Date())) {
//			jsonobj.put("code", 0);
//			jsonobj.put("message", "可投资时间应该在当前日期之后");
//			return jsonobj;
//		}
		
		if ("month".equals(loanDemandDO.getRepayDivisionWay())&& "D".equals(loanDemandDO.getTimeLimitUnit())) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "融资期限按天计算时，不可选择按月还款！");
			return jsonobj;
		}
		//是否定向融资 这里没有定向融资 页面上用hidden设置了N
		String isDirectional = request.getParameter("isDirectional");
		if (StringUtil.equals(isDirectional, "Y")) {
			//是定向融资的要输入指定密码
			if (StringUtil.isEmpty(loanDemandDO.getLoanPassword())) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "请设置密码");
				return jsonobj;
			}
			if (loanDemandDO.getLoanPassword().length()>8&&loanDemandDO.getLoanPassword().length()<6) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "定向融资密码长度应在6到8之间");
				return jsonobj;
			}
		}else{
			loanDemandDO.setLoanPassword(null);
		}
		//检查融资金额必须在融资规模内 --这里在页面上也设置为空了
		String dimensions = loanDemandDO.getDimensions();
		if (StringUtil.isNotBlank(dimensions)) {
			String[] dimension = dimensions.replaceAll(",", "").split(" ~ ");
			if (dimension != null && dimension.length == 2) {
				if (loanDemandDO.getLoanAmount() < Long.parseLong(dimension[0]) * 100
					|| loanDemandDO.getLoanAmount() > Long.parseLong(dimension[1]) * 100) {
					jsonobj.put("code", 0);
					jsonobj.put("message", "融资金额必须在融资规模内");
					return jsonobj;
				}
				
			}
		}
		//这句是检查按月还息不支持以天计息 没什么用
		ResultBase checkRs = loanDemandManager.checkRules(loanDemandDO);
		if(!checkRs.isSuccess()){
			jsonobj.put("code", 0);
			jsonobj.put("message", checkRs.getMessage());
			return jsonobj;
		}
		//数据库担保机构、保荐机构必须有，这里设置一个默认值避免报错
		loanDemandDO.setGuaranteeId(0);
		loanDemandDO.setGuaranteeName("-");
		loanDemandDO.setGuaranteeLicenseNo("-");
		loanDemandDO.setGuaranteeLicenseName("-");
		loanDemandDO.setSponsorId(0);
		loanDemandDO.setSponsorName("-");
		
		//数据库中的insert操作，设置了几个默认值，并把自身的ID设置进来了
		AddLoanDemandResult resultEnum = loanDemandManager.addLoanDemand(loanDemandDO);
		logger.info("发布借款需求结束：{}", resultEnum);
		if (resultEnum.getResultEnum() == ResultEnum.EXECUTE_SUCCESS) {
			//设置bizNo为loanDemand的ID
			String bizNo = String.valueOf(resultEnum.getDemandId());

			/** 扩展属性 ***/
			ExtendAttrDO extendAttrDO = new ExtendAttrDO();
			extendAttrDO.setRecordId(loanDemandDO.getDemandId());
			//这个attrName没有任何意义，不要被它迷惑了，就是用来存储支付银行的 不能直接在load_demand中加一个字段么？？为什么要这样？可能一对多？
			extendAttrDO.setAttrName("LOANDEMAND_PAYMENTBANKNAME");
			String paymentBankName = request.getParameter("paymentBankName");
			extendAttrDO.setAttrValue(paymentBankName);
			extendAttrDAO.insert(extendAttrDO);
			
			//上传附件
			P2PBaseResult baseResult = addAttachfile(bizNo, request);
			if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "存入草稿成功");
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "发布借款需求成功");
			}
			
		} else {
			if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "存入草稿失败");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "发布借款需求失败");
			}
		}
		return jsonobj;
	}

	/**
	 * 获取满标条件value
	 * @param method
	 * @param value
	 * @return
	 */
	private String getSaturationCondition(String method, String value) {
		if (TradeFullConditionEnum.AMOUNT.code().equals(method)) {
			return String.valueOf(Long.parseLong(value) * 100);
		} else if (TradeFullConditionEnum.PERCENTAGE.code().equals(method)) {
			return String.valueOf(Double.parseDouble(value) / 100);
		}
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkBorrower")
	public Object checkBorrower(String userName) throws Exception {
		logger.info("验证借款人，入参[{}]", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userInfo = userBaseInfoManager.queryByUserName(userName, 13);
		if (userInfo != null) {
			jsonobj.put("code", 1);
			jsonobj.put("message", userInfo.getRealName());
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "该用户没有权限");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkBorrowerByUserName")
	public Object checkBorrowerByUserName(String userName) throws Exception {
		logger.info("验证借款人，入参[{}]", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userInfo = userBaseInfoManager.queryByUserName(userName, 0);
		if (userInfo != null) {
			jsonobj.put("code", 1);
			jsonobj.put("message", userInfo.getRealName());
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "该用户没有权限");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping(value = "getRealName")
	public Object getRealName(String userName) {
		logger.info("验证借款人，入参[{}]", userName);
		JSONObject jsonobj = new JSONObject();
		UserBaseInfoDO userInfo = userBaseInfoManager.getRealNameByUserName(userName);
		if (userInfo != null) {
			jsonobj.put("code", 1);
			jsonobj.put("message", userInfo.getRealName());
		} else {
			jsonobj.put("code", 0);
			jsonobj.put("message", "该用户没有取款权限 ");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkGuaranteeLicenseNo")
	public Object checkGuaranteeLicenseNo(String guaranteeLicenseNo) {
		logger.info("验证借款人，入参[{}]", guaranteeLicenseNo);
		JSONObject jsonobj = new JSONObject();
		try {
			long counts = loanDemandManager.checkNO(guaranteeLicenseNo, "");
			if (counts > 0) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "编号已存在,请重新输入! ");
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "暂无此编号");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonobj.put("code", 2);
			jsonobj.put("message", "查询错误 ");
		}
		return jsonobj;
	}
	
	public long checkUpdateLicenseNo(String licenseNo, String id) {
		long count = 0;
		try {
			count = loanDemandManager.checkNO(licenseNo, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "updateLoanDemand")
	public String updateLoanDemand(long demandId, String info, Model model) throws Exception {
		model.addAttribute("pdfhost", "");
		LoanDemandDO loanDemandInfo = loanDemandManager.queryLoanDemandByDemandId(demandId);
		model.addAttribute("info", loanDemandInfo);
		String value = "";
		DecimalFormat df = new DecimalFormat("0.00");
		if (loanDemandInfo.getSaturationConditionMethod().equals("amount"))
			value = String.valueOf(Long.parseLong(loanDemandInfo.getSaturationCondition()) / 100);
		if (loanDemandInfo.getSaturationConditionMethod().equals("rate"))
			value = String.valueOf(df.format(Double.parseDouble(loanDemandInfo
				.getSaturationCondition()) * 100));
		model.addAttribute("value", value);
		model.addAttribute("guarantee", getInfos(8));
		model.addAttribute("sponsor", getInfos(9));
		model.addAttribute("invest", divisionService.getDivisionTemplatesByPhase(
			DivisionPhaseEnum.INVESET_PHASE.code(), "normal"));
		model.addAttribute("repay", divisionService.getDivisionTemplatesByPhase(
			DivisionPhaseEnum.REPAY_PHASE.code(), "normal"));
		long divisionTemplateLoanBaseId = loanDemandInfo.getDivisionTemplateId();
		DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
			.getByBaseId(divisionTemplateLoanBaseId);
		List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
			.valueOf(divisionTemplateLoan.getInvestTemplateId()));
		List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
			.valueOf(divisionTemplateLoan.getRepayTemplateId()));
		double loanInterest1 = 0;
		double investorInterest1 = 0;
		double loanInterest2 = 0;
		double investorInterest2 = 0;
		double totalLoanInterest = 0;
		double totalInvestorInterest = 0;
		//投资阶段分润信息
		if (investRolelist != null && investRolelist.size() > 0) {
			for (DivsionRuleRole role : investRolelist) {
				double bg = CommonUtil.mulDI(role.getRule(), 100);
				totalLoanInterest = CommonUtil.addDD(totalLoanInterest, bg);
				loanInterest1 = CommonUtil.addDD(loanInterest1, bg);
				if (12 == role.getRoleId()) {
					investorInterest1 = CommonUtil.addDD(investorInterest1, bg);
					totalInvestorInterest = CommonUtil.addDD(totalInvestorInterest, bg);
				}
			}
		}
		//还款阶段分润信息
		if (repayRolelist != null && repayRolelist.size() > 0) {
			for (DivsionRuleRole role : repayRolelist) {
				double bg1 = CommonUtil.mulDI(role.getRule(), 100);
				loanInterest2 = CommonUtil.addDD(loanInterest2, bg1);
				totalLoanInterest = CommonUtil.addDD(totalLoanInterest, bg1);
				if (12 == role.getRoleId()) {
					investorInterest2 = CommonUtil.addDD(investorInterest2, bg1);
					totalInvestorInterest = CommonUtil.addDD(totalInvestorInterest, bg1);
				}
			}
		}
		model.addAttribute("investTemplateId", divisionTemplateLoan.getInvestTemplateId());
		model.addAttribute("repayTemplateId", divisionTemplateLoan.getRepayTemplateId());
		model.addAttribute("totalLoanInterest", totalLoanInterest);
		model.addAttribute("totalInvestorInterest", String.valueOf(totalInvestorInterest));
		model.addAttribute("loanInterest1", loanInterest1);
		model.addAttribute("loanInterest2", loanInterest2);
		model.addAttribute("investorInterest1", investorInterest1);
		model.addAttribute("investorInterest2", investorInterest2);
		JSONObject jsonobj = (JSONObject) this.queryRuleInfo("",
			String.valueOf(divisionTemplateLoan.getInvestTemplateId()),String.valueOf(loanDemandInfo.getLoanAmount()/100),loanDemandInfo.getTimeLimitUnit(),loanDemandInfo.getTimeLimit()+"");
		String str = (String) jsonobj.get("message");
		model.addAttribute("investDivisionInfo", str);
		JSONObject rjsonobj = (JSONObject) this.queryRuleInfo("",
			String.valueOf(divisionTemplateLoan.getRepayTemplateId()),String.valueOf(loanDemandInfo.getLoanAmount()/100),loanDemandInfo.getTimeLimitUnit(),String.valueOf(loanDemandInfo.getTimeLimit()));
		String rstr = (String) rjsonobj.get("message");
		model.addAttribute("repayDivisionInfo", rstr);
		
		//担保公司是否已经二次最终审核
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanDemandId", loanDemandInfo.getDemandId());
		condition.put("authType", YrdAuthTypeEnum.MAKELOANLVTWO.code());
		long count = loanDemandManager.countLoanAuthRecordByCondition(condition);
		if (count > 0) {
			model.addAttribute("audit", "yes");//二次审核完成标识
		}
		
		//项目对应的附件信息	
		CommonAttachmentQueryOrder attachmentQueryOrder = new CommonAttachmentQueryOrder();
		attachmentQueryOrder.setBizNo(demandId + "");
		attachmentQueryOrder.setModuleTypeList(YrdEnumUtil.getAttachmentByIndustry());
		//从通用附件中加载项目缩略图
		attachmentQueryOrder.addModuleTypeEnum(CommonAttachmentTypeEnum.PRO_IMAGE);
		QueryBaseBatchResult<CommonAttachmentInfo> batchResult = commonAttachmentService
			.queryCommonAttachment(attachmentQueryOrder);
		List<AttachmentModuleType> attachmentModuleTypeList = new ArrayList<AttachmentModuleType>();
		if (ListUtil.isNotEmpty(batchResult.getPageList()))
			for (CommonAttachmentInfo attachmentInfo : batchResult.getPageList()) {

				//如果是项目缩略图，不加入附件列表，作为单独对象传递到页面
				if(CommonAttachmentTypeEnum.PRO_IMAGE == attachmentInfo.getModuleType())
				{
					model.addAttribute("proImage", attachmentInfo);
					continue;
				}

				boolean isExist = false;
				for (AttachmentModuleType attachmentModuleType : attachmentModuleTypeList) {
					if (attachmentInfo.getModuleType() == attachmentModuleType.getModuleType()) {
						attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					AttachmentModuleType attachmentModuleType = new AttachmentModuleType();
					attachmentModuleType.setModuleType(attachmentInfo.getModuleType());
					attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
					attachmentModuleTypeList.add(attachmentModuleType);
				}
			}
		model.addAttribute("list", attachmentModuleTypeList);

		//到期无条件兑付银行
		String paymentBankName = extendAttrDAO.getExtendValue(demandId, "LOANDEMAND_PAYMENTBANKNAME");
		model.addAttribute("paymentBankName", paymentBankName);
		
		JSONObject realStr = (JSONObject) this.getRealName(loanDemandInfo.getUserName());
		model.addAttribute("realName", realStr.get("message"));
		model.addAttribute("uploadHost", "");
		model.addAttribute("divisionWayList", DivisionWayEnum.getAllEnum());
		model.addAttribute("insureWayList", InsureWayEnum.getAllEnum());
		model.addAttribute("loanTypes", LoanTypeEnum.getEnums());
		if (info.equals("info")) {
			Trade trade = tradeService.getByDemandId(loanDemandInfo.getDemandId());
			if (trade != null) {
				model.addAttribute("tradeId", trade.getId());
				model.addAttribute("tradeStatus", trade.getStatus());
			}
			return BORROWING_MANAGE__PATH + "loanDemandInfo.vm";
		} else {
			return BORROWING_MANAGE__PATH + "updateLoanDemand.vm";
		}
	}
	/*正式担保函*/
	@RequestMapping("addImg")
	public String addImg(long demandId, HttpServletResponse response, Model model) throws Exception {
		LoanDemandDO result = loanDemandManager.queryLoanDemandByDemandId(demandId);
		model.addAttribute("info", result);
		return BORROWING_MANAGE__PATH + "addImg.vm";
	}
	@RequestMapping("delImg")
	public Object delImg(long attachmentId, HttpServletResponse response, Model model,
							HttpServletRequest request) throws Exception {
		JSONObject jsonobj = new JSONObject();
		
		try {
			
			CommonAttachmentResult baseResult = commonAttachmentService.findById(attachmentId);
			if (baseResult.getAttachmentInfo() != null) {
				deleteServicePicture(baseResult.getAttachmentInfo().getFilePhysicalPath());
				commonAttachmentService.deleteById(attachmentId);
			}
			
			if (baseResult.isSuccess()) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "删除成功");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "删除失败！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "删除异常！");
			logger.error(e.getMessage(), e);
		}
		printHttpResponse(response, jsonobj);
		return null;
		
	}
	
	@RequestMapping("updataImg")
	public String updtaImg(long demandId, HttpServletResponse response, Model model,
							HttpSession session, PageParam pageParam, HttpServletRequest request)
																									throws Exception {
		//项目的信息
		
		LoanDemandDO result = loanDemandManager.queryLoanDemandByDemandId(demandId);
		model.addAttribute("info", result);
		List<CommonAttachmentTypeEnum> enumList = YrdEnumUtil.getAttachmentByIndustry();
		model.addAttribute("enumlist", enumList);
		//项目对应的附件信息	
		CommonAttachmentQueryOrder attachmentQueryOrder = new CommonAttachmentQueryOrder();
		attachmentQueryOrder.setBizNo(demandId + "");
		attachmentQueryOrder.setModuleTypeList(YrdEnumUtil.getAttachmentByIndustry());
		QueryBaseBatchResult<CommonAttachmentInfo> batchResult = commonAttachmentService
			.queryCommonAttachment(attachmentQueryOrder);
		List<AttachmentModuleType> attachmentModuleTypeList = new ArrayList<AttachmentModuleType>();
		for (CommonAttachmentInfo attachmentInfo : batchResult.getPageList()) {
			boolean isExist = false;
			for (AttachmentModuleType attachmentModuleType : attachmentModuleTypeList) {
				if (attachmentInfo.getModuleType() == attachmentModuleType.getModuleType()) {
					attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				AttachmentModuleType attachmentModuleType = new AttachmentModuleType();
				attachmentModuleType.setModuleType(attachmentInfo.getModuleType());
				attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
				attachmentModuleTypeList.add(attachmentModuleType);
			}
		}
		model.addAttribute("list", attachmentModuleTypeList);
		return BORROWING_MANAGE__PATH + "updataImg.vm";
	}
	
	@ResponseBody
	@RequestMapping(value = "updateLoanDemandSubmit")
	public Object updateLoanDemandSubmit(HttpServletRequest request,LoanDemandDO loanDemandDO, long... templateIds)
																						throws Exception {
		Trade trade = tradeService.getByDemandId(loanDemandDO.getDemandId());
		JSONObject jsonobj = new JSONObject();
		if (trade != null) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "该借款需求已经上线，不允许修改");
			return jsonobj;
		}
		this.getLoanDemandDO(loanDemandDO);
		long divisionTemplateId = 0l;
		long existDivisionTemplateId = divisionTemplateLoanService
			.getBaseIdByTemplateIds(templateIds);
		if (existDivisionTemplateId > 0) {
			divisionTemplateId = existDivisionTemplateId;
		} else {
			divisionTemplateId = divisionTemplateLoanService
				.insertDivisionTemplateLoan(templateIds);
		}
		loanDemandDO.setDivisionTemplateId(divisionTemplateId);
		//年利率
		loanDemandDO.setInterestRate(CommonUtil.div(loanDemandDO.getInterestRate(), 100, 4));
		//借款金额
		loanDemandDO.setLoanAmount(loanDemandDO.getLoanAmount() * 100);
		//最低投资
		loanDemandDO.setLeastInvestAmount(loanDemandDO.getLeastInvestAmount() * 100);
		loanDemandDO.setSaturationCondition(getSaturationCondition(
			loanDemandDO.getSaturationConditionMethod(), loanDemandDO.getSaturationCondition()));
		long count = this.checkUpdateLicenseNo(loanDemandDO.getGuaranteeLicenseNo(),
			String.valueOf(loanDemandDO.getDemandId()));
		long count2 = this.checkUpdateLicenseNo(loanDemandDO.getGuaranteeLicenseNo(), "");
		LoanDemandResultEnum resultEnum = null;
		
		if (loanDemandDO.getInvestAvalibleTime().before(new Date())) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "可投资时间应该在当前日期之后");
			return jsonobj;
		}
		
		if ("month".equals(loanDemandDO.getRepayDivisionWay())&& "D".equals(loanDemandDO.getTimeLimitUnit())) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "融资期限按天计算时，不可选择按月还款！");
			return jsonobj;
		}
		
		//检查融资金额必须在融资规模内
		String dimensions = loanDemandDO.getDimensions();
		if (StringUtil.isNotBlank(dimensions)) {
			String[] dimension = dimensions.replaceAll(",", "").split(" ~ ");
			if (dimension != null && dimension.length == 2) {
				if (loanDemandDO.getLoanAmount() < Long.parseLong(dimension[0]) * 100
					|| loanDemandDO.getLoanAmount() > Long.parseLong(dimension[1]) * 100) {
					jsonobj.put("code", 0);
					jsonobj.put("message", "融资金额必须在融资规模内");
					return jsonobj;
				}
				
			}
		}

		/** 扩展属性 ***/
		ExtendAttrDO extendAttrDO = new ExtendAttrDO();
		extendAttrDO.setRecordId(loanDemandDO.getDemandId());
		extendAttrDO.setAttrName("LOANDEMAND_PAYMENTBANKNAME");
		String paymentBankName = request.getParameter("paymentBankName");
		extendAttrDO.setAttrValue(paymentBankName);
		extendAttrDAO.updateByAttrNameAndReordId(extendAttrDO);

		//数据库担保机构、保荐机构必须有，这里设置一个默认值避免报错
		loanDemandDO.setGuaranteeId(0);
		loanDemandDO.setGuaranteeName("-");
		loanDemandDO.setGuaranteeLicenseNo("-");
		loanDemandDO.setGuaranteeLicenseName("-");
		loanDemandDO.setSponsorId(0);
		loanDemandDO.setSponsorName("-");
		resultEnum = loanDemandManager.updateLoanDemand(loanDemandDO);
		if (resultEnum == LoanDemandResultEnum.EXECUTE_SUCCESS) {
			if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "存入草稿成功");
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "发布借款需求成功");
			}

		} else {
			if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "存入草稿失败");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "发布借款需求失败");
			}
		}

///去掉担保、保荐之后不验证
		
//		if (count == 1 && count2 == 1) {
//			resultEnum = loanDemandManager.updateLoanDemand(loanDemandDO);
//			if (resultEnum == LoanDemandResultEnum.EXECUTE_SUCCESS) {
//				if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
//					jsonobj.put("code", 1);
//					jsonobj.put("message", "存入草稿成功");
//				} else {
//					jsonobj.put("code", 1);
//					jsonobj.put("message", "发布借款需求成功");
//				}
//
//			} else {
//				if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
//					jsonobj.put("code", 0);
//					jsonobj.put("message", "存入草稿失败");
//				} else {
//					jsonobj.put("code", 0);
//					jsonobj.put("message", "发布借款需求失败");
//				}
//			}
//
//		} else if (count == 0 && count2 == 0) {
//
//			ResultBase checkRs = loanDemandManager.checkRules(loanDemandDO);
//			if(!checkRs.isSuccess()){
//				jsonobj.put("code", 0);
//				jsonobj.put("message", checkRs.getMessage());
//				return jsonobj;
//			}
//
//			resultEnum = loanDemandManager.updateLoanDemand(loanDemandDO);
//			if (resultEnum == LoanDemandResultEnum.EXECUTE_SUCCESS) {
//				if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
//					jsonobj.put("code", 1);
//					jsonobj.put("message", "存入草稿成功");
//				} else {
//					jsonobj.put("code", 1);
//					jsonobj.put("message", "发布借款需求成功");
//				}
//
//			} else {
//				if ("draft".equalsIgnoreCase(loanDemandDO.getStatus())) {
//					jsonobj.put("code", 0);
//					jsonobj.put("message", "存入草稿失败");
//				} else {
//					jsonobj.put("code", 0);
//					jsonobj.put("message", "发布借款需求失败");
//				}
//			}
//		} else if (count2 > 0 && count != 1) {
//			jsonobj.put("code", 2);
//			jsonobj.put("message", "担保函编号已存在,请重新输入！");
//		} else {
//			jsonobj.put("code", 3);
//			jsonobj.put("message", "担保函编号已存在,请重新输入！");
//		}
		return jsonobj;
		
	}
	
	@ResponseBody
	@RequestMapping("approvalPass")
	public Object approvalPass(long demandId, String status, String publishDate) {
		JSONObject jsonobj = new JSONObject();
		try {
			int number = loanDemandManager.passInDismiss(demandId, status, publishDate, null);
			if (number > 0) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "借款需求,通过成功！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "借款需求,通过失败！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "借款需求,通过异常！");
			e.printStackTrace();
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("approvalDismiss")
	public Object approvalDismiss(long demandId, String status, String dismissReason) {
		JSONObject jsonobj = new JSONObject();
		try {
			int number = loanDemandManager.passInDismiss(demandId, status, null, dismissReason);
			if (number > 0) {
				jsonobj.put("code", 1);
				jsonobj.put("message", "借款需求,驳回成功！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "借款需求,驳回失败！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "借款需求,驳回异常！");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("queryRuleInfo")
	public Object queryRuleInfo(String phase,String name,String loanAmount, String timeLimitUnit,
                                String timeLimit) {
		StringBuffer str = new StringBuffer();
		JSONObject jsonobj = new JSONObject();
		double loanInterest = 0;
        double investorInterest = 0;
        try {
            CalculateLoanCostOrder loanCostOrder = new CalculateLoanCostOrder();
            loanCostOrder.setTemplateId(NumberUtil.parseLong(name));
            if (StringUtil.isNotEmpty(loanAmount)) {
                Money loanMoney = new Money(NumberUtil.parseDouble(loanAmount));
                loanCostOrder.setLoanAmount(loanMoney);
            }
            loanCostOrder.setTimeLimitUnit(LoanPeriodUnitEnum.getByCode(timeLimitUnit));
            loanCostOrder.setTimeLimit(NumberUtil.parseInt(timeLimit));
            CalculateLoanCostResult loanCostResult = tradeService
                    .calculateLoanCost(loanCostOrder);
            if (loanCostResult.getRoleName() != null) {
                for (int i = 0; i < loanCostResult.getRoleName().length; i++) {
                    str.append(loanCostResult.getRoleName()[i] + ":")
                            .append(RateUtil.getRate(loanCostResult.getDivisionRule()[i])).append(" ");

                }
            }
            investorInterest = loanCostResult.getInvestorInterest();
            loanInterest = loanCostResult.getLoanInterest();
            jsonobj.put("message", str.toString());
            jsonobj.put("loanInterest", loanInterest);
            jsonobj.put("investorInterest", investorInterest);
            jsonobj.put("tradeChargeRate", loanCostResult.getTradeChargeRate());
            jsonobj.put("tradeChargeAmount", loanCostResult.getTradeChargeAmount()
                    .toStandardString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            jsonobj.put("code", 0);
            jsonobj.put("message", "查询失败！");
            jsonobj.put("loanInterest", loanInterest);
            jsonobj.put("investorInterest", investorInterest);
        }
        return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("updateFileUpLoadUrl")
	public Object updateFileUpLoadUrl(long id, String newUrl, long loanAmount, String audit)
																							throws Exception {
		LoanDemandDO loanDemandInfo;
		int result = 0;
		JSONObject jsonobj = new JSONObject();
		try {
			loanDemandInfo = loanDemandManager.queryLoanDemandByDemandId(id);
			if (loanDemandInfo == null) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "不存在要更新的记录");
			} else {
				result = loanDemandManager.updateFileUploadUrlById(id, newUrl, loanAmount, audit);
				if (result > 0) {
					jsonobj.put("code", 1);
					jsonobj.put("message", "更新担保函成功");
				} else {
					jsonobj.put("code", 0);
					jsonobj.put("message", "更新担保函失败");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonobj.put("code", 0);
			jsonobj.put("message", "更新异常");
			return jsonobj;
		}
		return jsonobj;
	}


	/**
	 * 
	 * @param demandId
	 *  验证担保公司是否审核通过
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("guaranteeAuthCheck")
	public Object guaranteeAuthCheck(long demandId) {
		LoanDemandDO loanDemandInfo = null;
		JSONObject jsonobj = new JSONObject();
		try {
			loanDemandInfo = loanDemandManager.queryLoanDemandByDemandId(demandId);
		} catch (Exception e1) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "处理失败");
		}
		String filteredGuarantees = YrdConstants.GuaranteeAuthFilterCanstants.FILTEREDGUARANTEES;
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("loanDemandId", demandId);
			conditions.put("authType", "DEPLOYLVTWO");
			if (filteredGuarantees.indexOf(loanDemandInfo.getGuaranteeName()) >= 0) {
				long result = loanDemandManager.countLoanAuthRecordByCondition(conditions);
				if (result > 0) {
					jsonobj.put("code", 1);
					jsonobj.put("message", "可以发布");
				} else {
					jsonobj.put("code", 0);
					jsonobj.put("message", "请等待担保公司审核");
				}
			} else {
				jsonobj.put("code", 1);
				jsonobj.put("message", "可以发布");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "处理失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("fixRepayTime")
	public Object fixRepayTime(long tradeId, String expireDate, Model model) throws Exception {
		JSONObject jsonobj = new JSONObject();
		try {
			if (tradeId > 0 && expireDate != null) {
				tradeService.updateTradeExpireDate(DateUtil.parse(expireDate), tradeId);
				jsonobj.put("code", 1);
				jsonobj.put("message", "修订成功！");
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "修订失败！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "修订失败！");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("onlineTrade")
	public Object onlineTrade(long loanId, Model model) throws Exception {
		JSONObject jsonobj = new JSONObject();
		try {
			if (loanId > 0) {
				try {
					loanDemandManager.onlineTrade(loanId);
					jsonobj.put("code", 1);
					jsonobj.put("message", "交易上线成功！");
				} catch (Exception e) {
					jsonobj.put("code", 0);
					jsonobj.put("message", "交易上线失败！");
				}
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "交易上线失败！");
			}
		} catch (Exception e) {
			jsonobj.put("code", 0);
			jsonobj.put("message", "交易上线失败！");
		}
		return jsonobj;
	}
	
	/**
	 * 上传担保函或合同时更新url地址
	 * @param id
	 * @param pdfUrl
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePdfUrl")
	public Object updatePdfUrl(long id, String pdfUrl, String type) {
		LoanDemandDO loanDemandInfo;
		JSONObject jsonobj = new JSONObject();
		String message = "更新失败";
		LoanDemandResultEnum result = LoanDemandResultEnum.EXECUTE_FAILURE;
		try {
			loanDemandInfo = loanDemandManager.queryLoanDemandByDemandId(id);
			if (loanDemandInfo == null) {
				jsonobj.put("code", 0);
				jsonobj.put("message", "不存在要更新的记录");
			} else {
				if ("contract".equals(type)) {
					loanDemandInfo.setContractPdfUrl(pdfUrl);
					message = "投资权益回购履约担保合同";
				} else if ("letter".equals(type)) {
					loanDemandInfo.setLetterPdfUrl(pdfUrl);
					message = "担保函";
				}
				result = loanDemandManager.updateLoanDemand(loanDemandInfo);
				if (result == LoanDemandResultEnum.EXECUTE_SUCCESS) {
					loanDemandInfo = loanDemandManager.queryLoanDemandByDemandId(id);
					//如果上传了担保函和合同发出消息通知
					if ("IS".equals(loanDemandInfo.getGuaranteeAudit())
						&& StringUtil.isNotBlank(loanDemandInfo.getContractPdfUrl())
						&& StringUtil.isNotBlank(loanDemandInfo.getLetterPdfUrl())
						&& StringUtil.isNotBlank(loanDemandInfo.getGuaranteeLicenseUrl())) {
						message = message + "成功，合同以及担保函均上传";
					}
					jsonobj.put("code", 1);
					jsonobj.put("message", "更新" + message + "成功");
				} else {
					jsonobj.put("code", 0);
					jsonobj.put("message", "更新" + message + "失败");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新异常", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "更新异常");
			return jsonobj;
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("notifyInvestorPdfOK")
	public Object notifyInvestorPdfOK(long demandId) {
		JSONObject jsonobj = new JSONObject();
		Trade trade = tradeService.getByDemandId(demandId);
		try {
			notifierService.notifierInvestorPDFOK(trade.getId());
			jsonobj.put("code", "1");
			jsonobj.put("message", "通知成功");
		} catch (Exception e) {
			logger.error("通知投资人合同已上传失败", e);
			jsonobj.put("code", "0");
			jsonobj.put("message", "通知失败");
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("notifyInvest")
	public Object notifyInvest(long demandId, Model model) {
		JSONObject jsonobj = new JSONObject();
		try {
			Trade trade = tradeService.getByDemandId(demandId);
			//TODO
//			investService.successInvestNotify(trade.getId());
			jsonobj.put("code", 1);
			jsonobj.put("message", "通知投资结果成功");
		} catch (Exception e) {
			logger.error("通知投资结果异常", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "通知投资结果异常");
			return jsonobj;
		}
		return jsonobj;
	}
	
	@ResponseBody
	@RequestMapping("notifyRepay")
	public Object notifyRepay(long demandId, Model model) {
		JSONObject jsonobj = new JSONObject();
		try {
			Trade trade = tradeService.getByDemandId(demandId);
			//TODO
//			depositLoanFacade.repaymentResult(String.valueOf(trade.getId()));
			jsonobj.put("code", 1);
			jsonobj.put("message", "通知还款结果成功");
		} catch (Exception e) {
			logger.error("通知还款结果异常", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "通知还款结果异常");
			return jsonobj;
		}
		return jsonobj;
	}
	
	protected boolean deleteServicePicture(String picPath) {
		if (picPath != null) {
			File file = new File(picPath);
			if (file.exists()) {
				try {
					file.delete();
					return true;
				} catch (Exception e) {
					logger.error("删除图片出错，图片物理路径{}", picPath, e);
					return false;
				}
			}
		}
		return false;
	}

	@RequestMapping("deleteloan")
	public String deleteLoan(HttpServletRequest request, long demandId, LoanDemandStatusEnum loanDemandStatus, QueryConditions queryConditions, PageParam pageParam, Model model) {
		loanDemandManager.delete(demandId);
		return pageQueryLoanDemand(queryConditions, "DRAFT", loanDemandStatus, pageParam, model);
	}
	
}
