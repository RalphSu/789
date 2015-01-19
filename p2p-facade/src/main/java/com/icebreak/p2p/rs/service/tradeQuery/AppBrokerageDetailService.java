package com.icebreak.p2p.rs.service.tradeQuery;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.DivisionTemplateLoanDO;
import com.icebreak.p2p.dataobject.DivsionRuleRole;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.util.TradeUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;

public class AppBrokerageDetailService extends ServiceBase implements AppService {
	private String	jjrRoleId	= SysUserRoleEnum.BROKER.code();
	
	public String getJjrRoleId() {
		return jjrRoleId;
	}
	
	public void setJjrRoleId(String jjrRoleId) {
		this.jjrRoleId = jjrRoleId;
	}
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			long tradeId = Long.parseLong(params.get("tradeId").toString());
			long detailId = Long.parseLong(params.get("detailId").toString());
			long investDetailId = Long.parseLong(params.get("investDetailId").toString());
			logger.info("移动终端经纪人查询投资人投资详情,tradeId=" + tradeId + "detailId=" + detailId
						+ ",investDetailId=" + investDetailId);
			Map<String, Object> result = new HashMap<String, Object>();
			Trade trade = tradeService.getByTradeId(tradeId);
			List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(
				tradeId, investDetailId);
			LoanDemandDO demand = null;
			try {
				demand = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.put("loanItem", demand);
			long divisionTemplateId = demand.getDivisionTemplateId();
			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
				.getByBaseId(divisionTemplateId);
			List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
			List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
			//只计算经纪人
			double investAnnualInterest = 0;
			double payAnnualInterest = 0;
			if (investRolelist != null && investRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : investRolelist) {
					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
						if (getJjrRoleId().equals(String.valueOf(druleRole.getRoleId()))) {
							investAnnualInterest += druleRole.getRule();
						}
						
					}
				}
			}
			if (repayRolelist != null && repayRolelist.size() > 0) {
				for (DivsionRuleRole druleRole : repayRolelist) {
					if (DivisionPhaseEnum.REPAY_PHASE.code().equals(druleRole.getPhase())) {
						if (getJjrRoleId().equals(String.valueOf(druleRole.getRoleId()))) {
							payAnnualInterest += druleRole.getRule();
						}
					}
				}
			}
			if (userInvests != null && userInvests.size() > 0) {
				for (UserInvestEntry invest : userInvests) {
					if (DivisionPhaseEnum.ORIGINAL_PHASE.code().equals(invest.getTransferPhase())) {
						result.put("tradeItem", invest);
					}
				}
			}
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("tradeId", trade.getId());
			conditions.put("roleId", getJjrRoleId());
			conditions.put("detailId", detailId);
			List<TradeQueryDetail> jjrTrades = tradeService.getTradeDetailByConditions(conditions);
			if (jjrTrades != null && jjrTrades.size() > 0) {
				TradeDetail detail = jjrTrades.get(0);
				result.put("jjrTrade", detail);
				String repayStatus = TradeUtil.getRepayStatus(trade, detail);
				result.put("repayStatus", repayStatus);
			}
			double totalAnnualInterest = (investAnnualInterest + payAnnualInterest) * 100;
			result.put("jjrDivisionInterest",
				new BigDecimal(totalAnnualInterest).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue());
			result.put("trade", trade);
			
			json.put("dataMap", result);
			json.put("interestRate", CommonUtil.mulDI(trade.getInterestRate(), 100) + "%");
			json.put("code", 1);
			json.put("message", "查询成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数或网络异常");
			logger.error("移动终端经纪人查询佣金详情时异常", e);
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appBrokerageDetail;
	}
	
}
