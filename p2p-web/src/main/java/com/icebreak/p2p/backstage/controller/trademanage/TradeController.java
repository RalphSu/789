package com.icebreak.p2p.backstage.controller.trademanage;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.viewObject.AmounFlowVO;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.trade.QueryTradeOrder;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.web.util.PageUtil;
import com.icebreak.p2p.web.util.WebUtil;
import com.icebreak.p2p.ws.enums.RepayPlanStatusEnum;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.RepayPlanService;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("backstage/trade")
public class TradeController extends BaseAutowiredController {
	
	@Autowired
	private final TradeService	tradeService	= null;
    @Autowired
    RepayPlanService repayPlanQueryService;
	/**
	 * 交易管理
	 * 
	 * @return
	 */
	@RequestMapping("mainTrade/{size}/{pageNo}")
	public String mainTradeGetByProperties(@PathVariable int size, @PathVariable int pageNo,
											QueryTradeOrder queryTradeOrder, Model model,
											HttpSession session) {
		PageParam pageParam = new PageParam();
		pageParam.setPageNo(pageNo);
		pageParam.setPageSize(size);
		queryTradeOrder.setRoleId(13L);
		Page<Trade> page = null;
		try {
			page = tradeService.pageQueryTrade(queryTradeOrder, pageParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("交易查询失败！", e);
		}
		String token = UUID.randomUUID().toString();
		session.setAttribute("token", token);
		model.addAttribute("queryTradeOrder", queryTradeOrder);
		model.addAttribute("page", page);
		return "backstage/trade/trade_main_list.vm";
	}

	@ResponseBody
	@RequestMapping("guaranteeAuditingFinish")
	public Object guaranteeAuditingFinish(long tradeId, String token, HttpSession session) {
		logger.info("担保机构审核完成开始放款转账，入参：[tradeId={}],", tradeId);
		Map<String, Object> map = new HashMap<String, Object>();
		Trade trade = tradeService.getByTradeId(tradeId);
		String getToken = (String) session.getAttribute("token");
		try {
			if (token.equals(getToken) && trade != null) {
				session.removeAttribute("token");
				logger.info("tradeId为" + trade.getId() + "计算转账金额，转账中...");
				divisionService.transfer(trade, SessionLocalManager.getSessionLocal().getUserId());
				LoanDemandDO loan = loanDemandManager
						.queryLoanDemandByDemandId(trade.getDemandId());
				tradeService.addRepayPlan(tradeId);

				map.put("code", 1);
				map.put("message", "放款成功");
			} else {
				map.put("code", 0);
				map.put("message", "请勿重复提交");
			}
		} catch (Exception e) {
			logger.error("放款失败" + e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "放款失败");
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("closeTrade")
	public Object closeTrade(long tradeId, String token, HttpSession session) {
		logger.info("融资项目流标操作，入参：[tradeId={}],", tradeId);
		Map<String, Object> map = new HashMap<String, Object>();
		String getToken = (String) session.getAttribute("token");
		Trade trade = tradeService.getByTradeId(tradeId);
		try {
			if (token.equals(getToken)) {

				if(trade != null){
					if(trade.getStatus() != YrdConstants.TradeStatus.TRADING){
						map.put("message", "项目已非募资阶段，不能进行流标！");
						return map;
					}

					session.removeAttribute("token");
					logger.info("tradeId为" + trade.getId() + "...");
					TradeResult result = remoteTradeService.closeTrade(trade.getCode(),getOpenApiContext());
					if(result.isSuccess()){
						tradeService.modifyStatus(tradeId, YrdConstants.TradeStatus.FAILED);
						map.put("code", 1);
						map.put("message", "【"+trade.getName()+"】融资项目已成功流标。投资人资金解冻成功,资金到账或许有延迟。");
					}
				}

			} else {
				map.put("code", 0);
				map.put("message", "请勿重复提交");
			}
		} catch (Exception e) {
			logger.error("【"+trade.getName()+"】融资项目流标失败，" + e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "【"+trade.getName()+"】融资项目流标失败。");
		}
		return map;
	}

	/**
	 * 投资交易管理
	 * 
	 * @return
	 */
	@RequestMapping("investTrade/{tradeId}")
	public String getByProperties(@PathVariable long tradeId, PageParam pageParam, Integer status,
									String receiveRealName, String receiveUserName,
									String payRealName, String payUserName, Long startAmount,
									Long endAmount, String startDate, String endDate, Model model) {
		model.addAttribute("page", tradeService.getByProperties(
			tradeId,
			(pageParam.getPageNo() - 1) * pageParam.getPageSize(),
			pageParam.getPageSize(),
			status,
			receiveRealName,
			receiveUserName,
			payRealName,
			payUserName,
			startAmount,
			endAmount,
			(startDate == null || startDate.length() < 1) ? null : DateUtil.parse(startDate
																					+ " 00:00:00"),
			(endDate == null || endDate.length() < 1) ? null : DateUtil
				.parse(endDate + " 23:59:59")));
		model.addAttribute("tradeId", tradeId);
		return "backstage/trade/trade_invest_list.vm";
	}
	/**
	 * 还款查询
	 */
	@RequestMapping("repayQuery/{size}/{pageNo}")
	public String repayQuery(@PathVariable int size, @PathVariable int pageNo,Model model,
			Integer status,HttpSession session,HttpServletRequest request, String repayDate){
		List<String> statusList = new ArrayList<String>();
		model.addAllAttributes(WebUtil.getRequestMap(request));
		RepayPlanQueryOrder repayQueryOrder = new RepayPlanQueryOrder();
		WebUtil.setPoPropertyByRequest(repayQueryOrder, request);
		if (status == null) {
			status = 0;
		}

		if(null != repayDate && !"".equals(repayDate)) {
			queryOrder(repayQueryOrder, repayDate);
		}

		repayQueryOrder.setPageNumber(pageNo);
		repayQueryOrder.setPageSize(size);
		if(status==0){
			statusList.add(RepayPlanStatusEnum.NOTPAY.code());
		}else{
			statusList.add(RepayPlanStatusEnum.REPAY_SUCCESS.code());
		}
		repayQueryOrder.setStatusList(statusList);	
		QueryBaseBatchResult<RepayPlanInfo> batchResult = repayPlanQueryService
			.queryRepayPlanInfo(repayQueryOrder);
		for(RepayPlanInfo repayPlanInfo:batchResult.getPageList()){
			Trade trade = tradeService.getByTradeId(repayPlanInfo.getTradeId());
			repayPlanInfo.setNote(String.valueOf(trade.getDemandId()));
		}
		model.addAttribute("repayQuery", repayQueryOrder);
		model.addAttribute("statusList", statusList);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		return "backstage/repay/repayQuery.vm";
	}

	private void queryOrder(RepayPlanQueryOrder repayQueryOrder, String repayDate) {
		if("today".equals(repayDate)) {
			repayQueryOrder.setStartDate(getRepayDate(0, 0, 0));
			repayQueryOrder.setEndDate(getRepayDate(23, 59, 59));
		} else if("tomorrow".equals(repayDate)) {
			Date startDate = getRepayDate(0, 0, 0);
			Date endDate = getRepayDate(23, 59, 59);
			repayQueryOrder.setStartDate(addDay(startDate, 1));
			repayQueryOrder.setEndDate(addDay(endDate, 1));
		} else if("week".equals(repayDate)) {
			Date startDate = getRepayDate(0, 0, 0);
			Date endDate = getRepayDate(23, 59, 59);
			repayQueryOrder.setStartDate(addDay(startDate, 6));
			repayQueryOrder.setEndDate(addDay(endDate, 6));
		}
	}

	private Date getRepayDate(int h, int m, int s) {
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, h);
		currentDate.set(Calendar.MINUTE, m);
		currentDate.set(Calendar.SECOND, s);
		return currentDate.getTime();
	}

	private Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 根据ID查看
	 * @param model
	 * @return
	 */
	@RequestMapping("tradeFlow/{tradeId}")
	public String tradeFlow(@PathVariable long tradeId, String receiveRealName,
							String receiveUserName, String payRealName, String payUserName,
							Long startAmount, Long endAmount, String startDate, String endDate,
							PageParam pageParam, Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("trade", trade);
		//List<AmounFlowVO> tradeflows = tradeService.getTradeFlowByTradeId(tradeId);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", tradeId);
		if (StringUtil.isNotBlank(receiveRealName)) {
			conditions.put("receiveRealName", receiveRealName);
		}
		if (StringUtil.isNotBlank(receiveUserName)) {
			conditions.put("receiveUserName", receiveUserName);
		}
		if (StringUtil.isNotBlank(payRealName)) {
			conditions.put("payRealName", payRealName);
		}
		if (StringUtil.isNotBlank(payUserName)) {
			conditions.put("payUserName", payUserName);
		}
		if (StringUtil.isNotBlank(startDate)) {
			startDate = startDate + " 00:00:00";
			conditions.put("startDate", startDate);
		}
		if (StringUtil.isNotBlank(endDate)) {
			endDate = endDate + " 23:59:59";
			conditions.put("endDate", endDate);
		}
		model.addAttribute("page", tradeService.getTradeFlowPageByParams(conditions, pageParam));
		return "backstage/trade/trade_flow_list.vm";
	}
	
	/**
	 * 根据ID查看分润转账流水
	 * @param model
	 * @return
	 */
	@RequestMapping("tradeDivisionFlow/{tradeId}")
	public String tradeDivisionFlow(@PathVariable long tradeId, String receiveRealName,
									String receiveUserName, Long startAmount, Long endAmount,
									String startDate, String endDate, PageParam pageParam,
									Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("trade", trade);
		//List<AmounFlowVO> divisionFlows = tradeService.getDivisionFlowByTradeId(tradeId);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", tradeId);
		if (StringUtil.isNotBlank(receiveRealName)) {
			conditions.put("receiveRealName", receiveRealName);
		}
		if (StringUtil.isNotBlank(receiveUserName)) {
			conditions.put("receiveUserName", receiveUserName);
		}
		if (StringUtil.isNotBlank(startDate)) {
			startDate = startDate + " 00:00:00";
			conditions.put("startDate", startDate);
		}
		if (StringUtil.isNotBlank(endDate)) {
			endDate = endDate + " 23:59:59";
			conditions.put("endDate", endDate);
		}
		model.addAttribute("page",
			tradeService.getDivisionTradeFlowPageByParams(conditions, pageParam));
		return "backstage/trade/trade_flow_division_list.vm";
	}
	
	/**
	 * 根据ID查看
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("devisionDetails/{tradeId}")
	public String devisionDetails(@PathVariable long tradeId, String userName, String realName,
									String startDate, String endDate, Long roleId,
									PageParam pageParam, Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		model.addAttribute("trade", trade);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tradeId", tradeId);
		if (roleId != null) {
			if (roleId > 0) {
				conditions.put("roleId", roleId);
			}
		}
		if (StringUtil.isNotBlank(realName)) {
			conditions.put("realName", realName);
		}
		if (StringUtil.isNotBlank(userName)) {
			conditions.put("userName", userName);
		}
		if (StringUtil.isNotBlank(startDate)) {
			startDate = startDate + " 00:00:00";
			conditions.put("startDate", startDate);
		}
		if (StringUtil.isNotBlank(endDate)) {
			endDate = endDate + " 23:59:59";
			conditions.put("endDate", endDate);
		}
		model.addAttribute("page", tradeService.getDevisionDetailsPage(conditions, pageParam));
		return "backstage/trade/trade_division_list.vm";
	}
	
	/**
	 * 查看flow详情
	 * @param flowId
	 * @param tradeflow
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tradeFlowInfo/{tradeId}")
	public String tradeFlowInfo(@PathVariable long tradeId, AmounFlowVO tradeflow, String payDate,
								Model model) throws Exception {
		Trade trade = tradeService.getByTradeId(tradeId);
		tradeflow.setDate(DateUtil.parse(payDate));
		model.addAttribute("trade", trade);
		model.addAttribute("item", tradeflow);
		return "backstage/trade/trade_flow_info.vm";
	}
	
	@ResponseBody
	@RequestMapping("offLineTrade")
	public Object offLineTrade(long tradeId, Model model) throws Exception {
		JSONObject jsonobj = new JSONObject();
		try {
			if (tradeId > 0) {
				Trade trade = tradeService.getByTradeId(tradeId);
				LoanDemandDO loan = null;
				try {
					loan = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
				} catch (Exception e) {
					logger.error("查询借款需求失败", e);
				}
				Date curDate = new Date();
				if (loan.getInvestAvalibleTime() != null) {
					if (curDate.before(loan.getInvestAvalibleTime())) {
						long result = tradeService.offLineTrade(tradeId);
						if (1 == result) {
							jsonobj.put("code", 1);
							jsonobj.put("message", "交易下线成功！");
						} else {
							jsonobj.put("code", 0);
							jsonobj.put("message", "交易下线失败！");
						}
					} else {
						jsonobj.put("code", 0);
						jsonobj.put("message", "该交易已进入投资阶段无法下线！");
					}
				} else {
					jsonobj.put("code", 0);
					jsonobj.put("message", "投资时间不确定！");
				}
			} else {
				jsonobj.put("code", 0);
				jsonobj.put("message", "交易下线失败！");
			}
		} catch (Exception e) {
			logger.error("交易下线", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "交易下线失败！");
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
			logger.error("updateTradeExpireDate", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "修订失败！");
		}
		return jsonobj;
	}
}
