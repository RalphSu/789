package com.icebreak.p2p.rs.service.tradeQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.dataobject.TradeFlowCode;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.LoanUtil;
import com.icebreak.p2p.util.MoneyUtil;

public class AppInvestDetailService extends ServiceBase implements AppService {
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		if (SessionLocalManager.getSessionLocal() == null) {
			json.put("code", 0);
			json.put("message", "未登录");
			return json;
		}
		try {
			long tradeId = Long.parseLong(params.get("tradeId").toString());
			long detailId = Long.parseLong(params.get("detailId").toString());
			Trade trade = tradeService.getByTradeId(tradeId);
			LoanDemandDO loanItem = loanDemandManager
				.queryLoanDemandByDemandId(trade.getDemandId());
			json.put("effectiveDateTime", DateUtil.simpleFormatYmdhms(trade.getEffectiveDateTime()));//成立日期
			json.put("expireDateTime", DateUtil.simpleFormatYmdhms(trade.getExpireDateTime()));//到期日期
			json.put("tradeName", trade.getName());//交易名称
			json.put("timeLimitUnit", loanItem.getTimeLimitUnit());//期限类型
			json.put("timeLimit",
				LoanUtil.getLoanTime(loanItem.getTimeLimit(), loanItem.getTimeLimitUnit()));//投资期限
			json.put("guaranteeLicenseNo", loanItem.getGuaranteeLicenseNo());//
			json.put("leastInvestAmount",
				MoneyUtil.getFormatAmount(loanItem.getLeastInvestAmount()));//最低投资金额
			json.put("deadline", DateUtil.simpleFormatYmdhms(loanItem.getDeadline()));//截止日期
			json.put("loanPurpose", loanItem.getLoanPurpose());//用途
			json.put("selfDefinedTitle", loanItem.getSelfDefinedTitle());//标题
			json.put("guaranteeAudit", loanItem.getGuaranteeAudit());//是否已上传担保函
			json.put("guaranteeName", loanItem.getGuaranteeName());//担保机构
			json.put("guaranteeStatement", loanItem.getGuaranteeStatement());//担保机构话语
			json.put("loanNote", loanItem.getLoanNote());//项目信息
			json.put("userName", loanItem.getUserName());
			json.put("guaranteeLicenseUrl", loanItem.getGuaranteeLicenseUrl());//担保函地址
			List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(
				tradeId, detailId);
			if (userInvests != null && userInvests.size() > 0) {
				//				result.put("tradeItem",  userInvests.get(0));
				UserInvestEntry tradeItem = userInvests.get(0);
				json.put("amount", MoneyUtil.getFormatAmount(tradeItem.getAmount()));//投资金额
				json.put("interestRate", CommonUtil.mul(trade.getInterestRate(), 100) + "%");//年化收益率
				json.put("tradeName", trade.getName());//交易名称
				json.put("tradeDate", DateUtil.simpleFormatYmdhms(tradeItem.getDate()));//投资日期
				json.put("tradeStatus", tradeItem.getStatus());
				json.put("proStatus", trade.getStatus());//项目状态
				json.put("investflowCode", tradeItem.getInvestflowCode());//投资流水号
			}
			
			long divisionAmount = 0;
			long profitAmount = 0;
			List<TradeDetail> details = tradeService.getInvestProfitTrade(detailId);
			if (details != null && details.size() > 0) {
				for (TradeDetail detail : details) {
					if (detail.getProfitType() > 0) {
						profitAmount += detail.getAmount();
						json.put("showExtraProfit", "yes");//是否经纪人返佣
						json.put("extInterest", detail.getProfitRate() * 100 + "%");//返佣金率
					} else {
						divisionAmount += detail.getAmount();
					}
				}
			}
			json.put("profitAmount", MoneyUtil.getFormatAmount(profitAmount));//返还佣金
			json.put("divisionAmount", MoneyUtil.getFormatAmount(divisionAmount));//利息		
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("loanDemandId", trade.getDemandId());
			condition.put("authType", "MAKELOANLVTWO");
			long count = loanDemandManager.countLoanAuthRecordByCondition(condition);
			if (count > 0) {
				json.put("audit", "yes");//审核状态
			}
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("roleId", 8L);
			cond.put("tradeId", tradeId);
			List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
			if (det != null && det.size() > 0) {
				TradeFlowCode tf = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0)
					.getId());
				if (tf != null) {
					json.put("contractNo", tf.getTradeFlowCode());//合同编号
				}
			}
			json.put("detailId", detailId);
			json.put("code", 1);
			json.put("message", "查询成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "查询异常");
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.appInvestDetail;
	}
	
}
