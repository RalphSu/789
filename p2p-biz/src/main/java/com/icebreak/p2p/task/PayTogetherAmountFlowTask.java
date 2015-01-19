package com.icebreak.p2p.task;

import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.dataobject.AmountFlow;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.integration.openapi.RemoteTradeService;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.PayTogetherOrder;
import com.icebreak.p2p.integration.openapi.order.PayTogetherSubOrder;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.notify.PayTogetherNotifyHandler;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.ws.enums.AmountFlowEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class PayTogetherAmountFlowTask extends AbstractTask {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private AmountFlowDao amountFlowDao = null;

    private TransferService transferService = null;

    private TradeService tradeService = null;
    private TradeDao tradeDao = null;

    private RemoteTradeService remoteTradeService;
    private PayTogetherNotifyHandler payTogetherNotifyHandler;

    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    @Override
    public synchronized void run() {
        if (!canRun()) {
            return;
        }

//        logger.info("开始执行转账任务");

        List<Trade> tradeList = tradeDao.queryByStatus(2);

        List<AmountFlow> amountFlows = null;
        for (Trade trade : tradeList) {
            amountFlows = amountFlowDao.queryPayTogetherForUpdate(trade.getId(),0);

            if (ListUtil.isNotEmpty(amountFlows)) {

                PayTogetherOrder order = new PayTogetherOrder();
                OpenApiContext openApiContext = new OpenApiContext();
                openApiContext.setOpenApiBizNo(BusinessNumberUtil
                        .gainOutBizNoNumber());
                order.setTradeMemo(trade.getName() + "集体付款");
                order.setTradeNo(trade.getCode());

                List<PayTogetherSubOrder> tradePoolSubTansferOrders = new ArrayList<PayTogetherSubOrder>();

                PayTogetherSubOrder subOrder = new PayTogetherSubOrder();
                subOrder.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
                subOrder.setMemo("集体付款分润");
                subOrder.setPayeeUserId(AppConstantsUtil.getExchangeAccount());
                subOrder.setTradeName("集体付款分润");

                Money transferAmount = new Money();
                for (AmountFlow amountFlow : amountFlows) {
                    if (transferService.isRiskAmountFlow(amountFlow)) {
                        logger.error("拒绝执行，此交易有重复交易风险：flowId="
                                + amountFlow.getId());
                        continue;
                    }

                    if (AmountFlowEnum.AMOUNT_FLOW_DIVISION.code().equals(
                            amountFlow.getType())) {
                        transferAmount = MoneyUtil.getMoneyByLong(amountFlow
                                .getAmount());
                    }
                }

                subOrder.setTransferAmount(MoneyUtil
                        .getFormatAmountByMoney(transferAmount));
                tradePoolSubTansferOrders.add(subOrder);
                order.setTradePoolSubTansferOrders(tradePoolSubTansferOrders);

                TradeResult result = remoteTradeService.payTogether(order,
                        openApiContext);
				if (StringUtil
					.equalsIgnoreCase("REQ_SUCCESS_IN_PROCESSING", result.getResultCode())) {
                for (AmountFlow amountFlow : amountFlows) {
                    amountFlowDao.modifyStatus(amountFlow.getId(), 1);
                }
				}
            }
        }

    }

    public TradeService getTradeService() {
        return this.tradeService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public RemoteTradeService getRemoteTradeService() {
        return this.remoteTradeService;
    }

    public void setRemoteTradeService(RemoteTradeService remoteTradeService) {
        this.remoteTradeService = remoteTradeService;
    }

    public AmountFlowDao getAmountFlowDao() {
        return this.amountFlowDao;
    }

    public void setAmountFlowDao(AmountFlowDao amountFlowDao) {
        this.amountFlowDao = amountFlowDao;
    }

    public TransferService getTransferService() {
        return this.transferService;
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    public TradeDao getTradeDao() {
        return this.tradeDao;
    }

    public void setTradeDao(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    public void setPayTogetherNotifyHandler(
            PayTogetherNotifyHandler payTogetherNotifyHandler) {
        this.payTogetherNotifyHandler = payTogetherNotifyHandler;
    }


}
