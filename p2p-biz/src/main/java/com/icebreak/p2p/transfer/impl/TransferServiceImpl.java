package com.icebreak.p2p.transfer.impl;

import com.icebreak.p2p.base.BaseAutowiredToolsService;
import com.icebreak.p2p.common.services.SysParameterService;
import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.dataobject.AmountFlowTrade;
import com.icebreak.p2p.dataobject.DivisionDetail;
import com.icebreak.p2p.integration.openapi.order.FundsTransferOrder;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.util.lang.util.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class TransferServiceImpl extends BaseAutowiredToolsService implements TransferService {
	
	/**
	 * 普通转账
	 */
	private String transCode = "3000121";
	/**
	 * 转账系统ID
	 */
	private static String SYSTEMID = "PLATFORM";
	
	private static String TRADENAME = "投资转账";
	
	
	@Autowired
	private SysParameterService sysParameterService;
	
	private AmountFlowDao amountFlowDao = null;
	
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	public void setAmountFlowDao(AmountFlowDao amountFlowDao) {
		this.amountFlowDao = amountFlowDao;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public boolean doTransfer(long tradeId, long tradeDetailId, String in, String out, long amount,
								String memo, Date date, long inUserId, long outUserId, String type) {
		if (amount <= 0) {
			return true;
		}
		String flowCode = BusinessNumberUtil.gainOutBizNoNumber();
		
		long flowId = amountFlowDao.addAmountFlow(new AmountFlow(flowCode, out, in, amount, 0,
			memo, date, inUserId, outUserId, type));
		amountFlowDao.addAmountFlowTrade(new AmountFlowTrade(flowId, tradeId, tradeDetailId));
		return true;
	}
	
	@Override
	public boolean doTransfer(String in, String out, long amount, String memo, Date date) {
		return transfer(BusinessNumberUtil.gainOutBizNoNumber(), in, out, amount, memo, date);
	}
	
	@Override
	public boolean transfer(final String flowCode, String in, String out, long amount, String memo,
							Date date) {
		return true;
	}
	
	@Override
	public boolean dealTransfer(String flowCode, String in, String out, long amount, String memo,
								Date date) {
		if (amount <= 0) {
			return true;
		}
		logger.info("执行投资转账：[转出账号->" + out + ", 进账账号->" + in + ", 金额->" + amount + ", 备注->" + memo
					+ "]");
		P2PBaseResult result = this.fundsTransferService.tradeTransfer(
			buildTradeTransferModel(in, out, amount, memo, date, flowCode),
			this.getOpenApiContext());
		logger.info("执行投资转账：[结果{}]", result);
		return result.isSuccess();
	}
	
	/**
	 * 构造冻结转账数据对象
	 * @param in
	 * @param out
	 * @param amount
	 * @param memo
	 * @param date
	 * @param flowCode
	 * @return
	 */
	private static FundsTransferOrder buildTradeTransferModel(String in, String out, long amount,
																String memo, Date date,
																String flowCode) {
		
		FundsTransferOrder fundsTransferOrder = new FundsTransferOrder();
		fundsTransferOrder.setBuyerUserId(out);
		fundsTransferOrder.setSellerUserId(in);
		fundsTransferOrder.setPayerUserId(out);
		Money tradeAmount = new Money();
		tradeAmount.setCent(amount);
		fundsTransferOrder.setTradeAmount(tradeAmount);
		fundsTransferOrder.setMemo(memo);
		fundsTransferOrder.setOrderNo(flowCode);
		fundsTransferOrder.setTradeName(TRADENAME);
		fundsTransferOrder.setSystemId(SYSTEMID);
		return fundsTransferOrder;
	}
	
	@Override
	public boolean isRiskAmountFlow(AmountFlow amountFlow) {
		AmountFlowTrade aft = amountFlowDao.getAmountFlowTradeByFlowId(amountFlow.getId());
		return aft != null && aft.getTradeDetailId() > 0 && amountFlowDao.transFlowCountByTradeDetailIdType(aft.getTradeDetailId(), amountFlow.getType()) > 0;
	}
	
	@Override
	public boolean isRiskDevisionDetail(DivisionDetail divisionDetail) {
		boolean flag = false;
		int transFlowCount = divisionDetailDao.transDivisionCountByTradeDetailId(divisionDetail
			.getTradeDetailId());
		if (transFlowCount > 1) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean isRepayDivision(long id) {
		boolean flag = false;
		String transFlowPhase = amountFlowDao.getTransPhaseByTradeDetailId(id);
		if (transFlowPhase != null
			&& DivisionPhaseEnum.REPAY_TRANSITION_PHASE.code().equals(transFlowPhase)) {
			flag = true;
		}
		return flag;
	}
	
	public boolean isRepayPlanDivision(long id) {
		boolean flag = false;
		int periodNO = amountFlowDao.getRepayPeriodNOByTradeDetailId(id);
		if (periodNO>0) {
			flag = true;
		}
		return flag;
	}
}
