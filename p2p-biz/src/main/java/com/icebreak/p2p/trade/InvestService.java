package com.icebreak.p2p.trade;

import com.icebreak.p2p.dataobject.DivsionRuleRole;
import com.icebreak.p2p.dataobject.InvestDetailDO;
import com.icebreak.p2p.dataobject.TradeDetail;
import com.icebreak.p2p.integration.openapi.result.InvestReturnRequest;
import com.yiji.openapi.sdk.message.yzz.InvestApplyNotify;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvestService {
	/**
	 * 执行投资
	 * @param detail
	 * @throws Exception
	 */
	public void investProcessor(TradeDetail detail) throws Exception;
	
	/**
	 * 查詢投資詳情
	 * @param condition
	 * @return
	 */
	public List<InvestDetailDO> queryInvestDetail(Map<String, Object> condition);
	
	/**
	 * 添加投资
	 * @param notify
	 * @throws Exception
	 */
	public void investNotify(InvestApplyNotify notify) throws Exception;

	/**
	 * 投资返回
	 * @param request
	 * @throws Exception
	 */
	public void investReturn(InvestReturnRequest request);
	
	/**
	 * 更新按月付息时的每期付息日期
	 * @param detailId
	 * @param repayDate
	 */
	public void updateInvestDetailRepayDate(long detailId, Date repayDate);
	
	public List<DivsionRuleRole> getRuleRole(String name);
	
	public TradeDetail saveInvestOriginalTradeDetail(TradeDetail detail) throws Exception;
	
	public double getDaysRuleRate(double rule, String timeLimitUnit, int timeLimit, Date effectiveDate, Date endDate);
	
}
