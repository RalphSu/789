package com.icebreak.p2p.rs.service.tradeQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.TradeFlowCode;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.util.TradeUtil;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.LoanUtil;
import com.icebreak.p2p.util.MoneyUtil;

public class AppIndexTradeDetailService extends ServiceBase implements AppService {
	@Autowired
	private LoanDemandManager	loanDemandManager;
	@Autowired
	private TradeService		tradeService;
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Map<String, Object> res = new HashMap<String, Object>();
		String token = UUID.randomUUID().toString();
		try {
			long tradeId = Long.parseLong(params.get("tradeId").toString());
			long demandId = Long.parseLong(params.get("demandId").toString());
			Trade trade = tradeService.getByTradeId(tradeId);
			json.put("proStatus", trade.getStatus());//项目状态
			json.put("loanedAmount", MoneyUtil.getFormatAmount(trade.getLoanedAmount()));//已借款金额(已投资金额)
			json.put("effectiveDateTime", DateUtil.simpleFormatYmdhms(trade.getEffectiveDateTime()));//成立时间
			json.put("investableAmount",
				MoneyUtil.getFormatAmount((trade.getAmount() - trade.getLoanedAmount())));//还需金额
			
			try {
				LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(demandId);
				res.put("item", loanDemandManager.queryLoanDemandByDemandId(demandId));
				json.put("guaranteeLicenseNo", loan.getGuaranteeLicenseNo());//担保函编号
				json.put("loanAmount", MoneyUtil.getFormatAmount(loan.getLoanAmount()));//需求金额
				json.put("userName", loan.getUserName());//融资人用户名
				json.put("interestRate", CommonUtil.mul(loan.getInterestRate(), 100) + "%");
				json.put("timeLimitUnit", loan.getTimeLimitUnit());//期限类型
				json.put("timeLimit",
					LoanUtil.getLoanTime(loan.getTimeLimit(), loan.getTimeLimitUnit()));//期限
				json.put("leastInvestAmount",
					MoneyUtil.getFormatAmount(loan.getLeastInvestAmount()));//最低投资金额
				json.put(
					"saturationCondition",
					TradeUtil.getSaturationCondition(loan.getSaturationConditionMethod(),
						loan.getSaturationCondition()));//满标条件
				json.put("investAvalibleTime",
					DateUtil.simpleFormatYmdhms(loan.getInvestAvalibleTime()));//起投时间
				if (1 == trade.getStatus()) {
					json.put("deadline", DateUtil.simpleFormatYmdhms(trade.getDeadline()));
				} else if (2 == trade.getStatus() || 8 == trade.getStatus()) {
					json.put("deadline", DateUtil.simpleFormatYmdhms(trade.getEffectiveDateTime()));
				} else if (3 == trade.getStatus() || 7 == trade.getStatus()) {
					json.put("deadline", DateUtil.simpleFormatYmdhms(trade.getFinishDate()));
				} else {
					json.put("deadline", DateUtil.simpleFormatYmdhms(trade.getDeadline()));
				}
				//json.put("deadline", DateUtil.simpleFormatYmdhms(loan.getDeadline()));//截止时间
				json.put("selfDefinedTitle", loan.getSelfDefinedTitle());//标题
				json.put("loanNote", loan.getLoanNote());//项目信息
				json.put("guaranteeName", loan.getGuaranteeName());//担保机构
				json.put("guaranteeLicenseUrl", loan.getGuaranteeLicenseUrl());
				json.put("guaranteeAudit", loan.getGuaranteeAudit());//上传担保函标识
				json.put("loanPurpose", loan.getLoanPurpose());//项目用途
				json.put("guaranteeStatement", loan.getGuaranteeStatement());//担保机构话语
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("roleId", 8L);
			cond.put("tradeId", tradeId);
			List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
			if (det != null && det.size() > 0) {
				TradeFlowCode tf = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0)
					.getId());
				if (tf != null) {
					//					res.put("contractNo", tf.getTradeFlowCode());
					json.put("contractNo", tf.getTradeFlowCode());
				}
			}
			if ((trade.getAmount() - trade.getLoanedAmount()) > 0
				&& (trade.getLeastInvestAmount() - (trade.getAmount() - trade.getLoanedAmount())) > 0) {
				//				res.put("lastInvestAvailable", "yes");
				json.put("lastInvestAvailable", "yes");
			}
			//			res.put("tradeId", tradeId);
			json.put("tradeId", tradeId);
			//res.put("trade", trade);
			//			res.put("token", token);
			json.put("token", token);
			//			Session.setAttribute("token", token);
			//json.put("dataMap", res);
			json.put("code", 1);
			json.put("message", "查询成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "查询失败");
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {
		return APIServiceEnum.indexTradeDetail;
	}
	
}
