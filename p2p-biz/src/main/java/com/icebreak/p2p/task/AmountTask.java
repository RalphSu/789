package com.icebreak.p2p.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.ws.enums.AmountFlowEnum;

public class AmountTask extends AbstractTask {
	
	protected final Logger		logger				= LoggerFactory.getLogger(getClass());
	
	private AmountFlowDao		amountFlowDao		= null;
	
	private TransferService	transferService	= null;
	
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}
	
	public void setAmountFlowDao(AmountFlowDao amountFlowDao) {
		this.amountFlowDao = amountFlowDao;
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public synchronized void run() {
        if(!canRun()){
            return;
        }

		logger.info("开始执行转账任务");
		List<AmountFlow> amountFlows = amountFlowDao.getAmountFlowsByStatus(false);
		if (amountFlows != null && amountFlows.size() > 0) {
			for (AmountFlow amountFlow : amountFlows) {
				if (transferService.isRiskAmountFlow(amountFlow)) {
					logger.error("拒绝执行，此交易有重复交易风险：flowId=" + amountFlow.getId());
					continue;
				}
				boolean b = false;
				if (AmountFlowEnum.AMOUNT_FLOW_INVEST.code().equals(amountFlow.getType())
					|| transferService.isRepayDivision(amountFlow.getId())||AmountFlowEnum.AMOUNT_FLOW_REPAY.code().equals(amountFlow.getType())) {
					try {
						b = transferService.dealTransfer(amountFlow.getFlowCode(),
							amountFlow.getAmountIn(), amountFlow.getAmountOut(),
							amountFlow.getAmount(), amountFlow.getNote(), amountFlow.getDate());
					} catch (Exception e) {
						logger.error("执行转账任务异常:流水号---" + amountFlow.getFlowCode(), e);
					}

				} else {
					try {
						b = transferService.transfer(amountFlow.getFlowCode(),
							amountFlow.getAmountIn(), amountFlow.getAmountOut(),
							amountFlow.getAmount(), amountFlow.getNote(), amountFlow.getDate());
					} catch (Exception e) {
						logger.error("执行转账任务异常:流水号---" + amountFlow.getFlowCode(), e);
					}
				}
//				if (b) {
//					amountFlowDao.modifyStatus(amountFlow.getId(), b);
//				}
			}
		}
	}
	
}
