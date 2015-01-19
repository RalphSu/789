package com.icebreak.p2p.notify;

import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.sign.SignCardInfoService;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.util.lang.util.money.Money;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.YzzStrengthenWithdrawNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("yzzStrengthenWithdrawNotifyHandler")
public class YzzStrengthenWithdrawNotifyHandler implements NotifyHandler {
	
	private static Logger logger = LoggerFactory.getLogger(YzzStrengthenWithdrawNotifyHandler.class);
	
	@Resource
	private RechargeFlowService rechargeFlowService;
	@Resource
	private SignCardInfoService signCardInfoService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("提现回推处理：" + notify);
		
		YzzStrengthenWithdrawNotify nty = (YzzStrengthenWithdrawNotify) notify;
		RechargeFlow flow = rechargeFlowService.queryByLocalNo(nty.getPayNo());
		if (flow == null) {
			logger.info("检验回推流水号失败。");
			return;
		}
		logger.info("检验回推流水号成功。");
		//		SignCardInfo signCardInfo = signCardInfoService.queryByPactNo(nty.getPayNo());
		if ("WITHDRAW_SUCCESS".equals(nty.getResultCode())) {
			flow.setStatus(1);
			RechargeFlow feeFlow = new RechargeFlow();
			feeFlow.setAmount(new Money(nty.getAmount()).getCent()
					- new Money(nty.getAmountIn()).getCent());
			feeFlow.setPayType(DivisionTypeEnum.FEE.getCode());
			feeFlow.setPayMemo("提现手续费");
			feeFlow.setUserId(flow.getUserId());
			feeFlow.setYjfAccountId(flow.getYjfAccountId());
			try {
				rechargeFlowService.addRechargeFlow(feeFlow);
				logger.info("添加提现手续费收支明细成功。");
			} catch (Exception e) {
				logger.error("添加提现手续费收支明细失败。", e);
			}
		} else {
			flow.setStatus(0);
		}
		logger.info("更新提现收支明细状态为：" + flow.getStatus());
		rechargeFlowService.updateStatus(flow);
	}
	
}
