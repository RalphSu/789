package com.icebreak.p2p.front.controller.repay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.OperatorInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.web.util.PageUtil;
import com.icebreak.p2p.ws.enums.RepayPlanStatusEnum;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.RepayPlanService;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;

@Controller
@RequestMapping("repayDB")
public class RepayDBManagerController extends UserAccountInfoBaseController {
	private final String vm_path = "/front/repay/";
	@Autowired
	RepayPlanService repayPlanService;

	@Autowired
	IOperatorInfoService operatorInfoService;

	protected String[] getDateInputDayNameArray() {
		return new String[] { "startDate", "endDate" };
	}

	@ResponseBody
	@RequestMapping("doneDBRepay")
	public JSONObject doneDBRepayManage(RepayPlanQueryOrder queryOrder,
			PageParam pageParam, Model model) throws Exception {
		JSONObject jsonObject = new JSONObject();
		queryOrder.setPageSize(pageParam.getPageSize());
		queryOrder.setPageNumber(pageParam.getPageNo());
		List<String> status = new ArrayList<String>(1);
		status.add(RepayPlanStatusEnum.REPAY_SUCCESS.getCode());
		queryOrder.setGuaranteeId(getGuaranteeUserId());
		queryOrder.setStatusList(status);
		QueryBaseBatchResult<RepayPlanInfo> queryBaseBatchResult = repayPlanService
				.queryRepayPlanInfoGuarantee(queryOrder);
		Map<String, String> map = new HashMap<String, String>();
		List<RepayPlanInfo> recordList = queryBaseBatchResult.getPageList();
		if (ListUtil.isNotEmpty(recordList)) {
			for (RepayPlanInfo item : recordList) {
				Date date = new Date();
				if (date.after(item.getRepayDate())) {
					item.setCanPay("Y");
					getTradeLoanDemandId(map, item.getTradeId());
				}
			}
		}

		jsonObject.put("map", map);
		jsonObject.put("page", PageUtil.getCovertPage(queryBaseBatchResult));
		jsonObject.put("queryConditions", queryOrder);
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping("waitDBRepayManage")
	public JSONObject waitDBRepayManage(RepayPlanQueryOrder queryOrder,
			PageParam pageParam, Model model) throws Exception {
		JSONObject jsonObject = new JSONObject();
		queryOrder.setPageSize(pageParam.getPageSize());
		queryOrder.setPageNumber(pageParam.getPageNo());
		List<String> status = new ArrayList<String>(1);
		status.add(RepayPlanStatusEnum.NOTPAY.code());
		queryOrder.setStatusList(status);
		queryOrder.setGuaranteeId(getGuaranteeUserId());
		if (queryOrder.getStartDate() != null) {
			queryOrder.setStartDate(com.icebreak.util.lang.util.DateUtil
					.getStartTimeOfTheDate(queryOrder.getStartDate()));
		}
		if (queryOrder.getEndDate() != null) {
			queryOrder.setEndDate(com.icebreak.util.lang.util.DateUtil
					.getEndTimeOfTheDate(queryOrder.getEndDate()));
		}
		QueryBaseBatchResult<RepayPlanInfo> queryBaseBatchResult = repayPlanService
				.queryRepayPlanInfoGuarantee(queryOrder);
		List<RepayPlanInfo> recordList = queryBaseBatchResult.getPageList();
		Map<String, String> map = new HashMap<String, String>();
		if (ListUtil.isNotEmpty(recordList)) {
			for (RepayPlanInfo item : recordList) {
				Date date = new Date();
				if (date.after(item.getRepayDate())) {
					item.setCanPay("Y");
					getTradeLoanDemandId(map, item.getTradeId());
				}
			}
		}
		jsonObject.put("page", PageUtil.getCovertPage(queryBaseBatchResult));
		jsonObject.put("queryConditions", queryOrder);
		jsonObject.put("map", map);
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping("repayDB")
	public Object repayDB(long tradeId, long repayPlanId, String smsCode,
			String mobile, String business) {

		Map<String, Object> map = new HashMap<String, Object>();

		SmsCodeResult smsCodeResult = this.smsManagerService.verifySmsCode(
				mobile, SmsBizType.getByCode(business), smsCode, true);
		boolean codeCheck = smsCodeResult.isSuccess();

		if (!codeCheck) {
			map.put("code", 0);
			map.put("message", "短信验证码错误");
			return map;
		}

		Trade trade = tradeService.getByTradeId(tradeId);
		long demandId = trade.getDemandId();

		try {
			if (demandId > 0 && codeCheck) {
				int result = 0;
				if (result == 0) {
					LoanDemandDO loan = loanDemandManager
							.queryLoanDemandByDemandId(demandId);
					map.put("code", 1);
					map.put("message", "还款成功");
				} else {
					map.put("code", 0);
					map.put("message", "还款失败");
				}
			} else {
				map.put("code", 0);
				map.put("message", "还款失败");
			}
		} catch (Exception e) {
			logger.error("还款失败异常{}", e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "还款失败");
		}
		return map;
	}

	@RequestMapping("repayDBManager")
	public String repayDBManage(HttpServletResponse response, Model model)
			throws Exception {
		SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
		if (sessionLocal != null && sessionLocal.getAccountId() != null) {
			this.initAccountInfo(model);
		}
		return vm_path + "repayDBManager.vm";
	}

	public long getGuaranteeUserId() {
		try {
			String userBaseId = SessionLocalManager.getSessionLocal()
					.getUserBaseId();
			long userId = SessionLocalManager.getSessionLocal().getUserId();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			long count = userBaseInfoManager.getRolesByUserIdCount(params);
			if (count > 0) {
				List<Role> roles = userBaseInfoManager.getRolesByUserId(params);
				for (Role role : roles) {
					if (SysUserRoleEnum.OPERATOR.getRoleCode().equals(
							role.getCode())) {
						Map<String, Object> conditions = new HashMap<String, Object>();
						conditions.put("userBaseId", userBaseId);
						conditions.put("limitStart", 0);
						conditions.put("pageSize", 9999);
						List<OperatorInfoDO> operatorInfos = operatorInfoService
								.queryOperatorsByProperties(conditions);
						if (operatorInfos != null && operatorInfos.size() > 0) {
							List<UserBaseInfoDO> curParentJGs = userBaseInfoManager
									.queryByType("JG", String
											.valueOf(operatorInfos.get(0)
													.getParentId()));
							if (curParentJGs != null && curParentJGs.size() > 0) {
								userId = curParentJGs.get(0).getUserId();
							}
						}
					}
				}
			}
			return userId;
		} catch (Exception e) {
			logger.error("获取担保机构userId失败", e);
		}

		return 0;
	}

	// todo laji

	public Map<String, String> getTradeLoanDemandId(Map<String, String> map,
			long tradeId) {
		Trade trade = tradeService.getByTradeId(tradeId);
		long demandId = trade.getDemandId();
		map.put(String.valueOf(tradeId), String.valueOf(demandId));
		return map;
	}

}
