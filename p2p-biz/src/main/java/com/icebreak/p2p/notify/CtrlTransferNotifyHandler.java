package com.icebreak.p2p.notify;

import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.icebreak.p2p.dataobject.DivisionDetail;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.util.lang.util.ListUtil;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.CtrlTransferNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service("ctrlTransferNotifyHandler")
public class CtrlTransferNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory
			.getLogger(CtrlTransferNotifyHandler.class);

	@Resource
	private DivisionDetailDao divisionDetailDao;
	@Resource
	private RechargeFlowService rechargeFlowService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("收到风控转账结果通知：" + notify);
		CtrlTransferNotify nty = (CtrlTransferNotify) notify;

		if ("EXECUTE_SUCCESS".equals(nty.getResultCode())) {
			DivisionDetail divisionDetail = new DivisionDetail();
			divisionDetail.setOutBizNo(nty.getTradeNo());
			List<DivisionDetail> detailList = divisionDetailDao.query(divisionDetail);
			if (ListUtil.isNotEmpty(detailList)) {
				DivisionDetail detail = detailList.get(0);
				logger.info("匹配上风控转账流水：" + detail.getId() + ",流水当前状态为：" + detail.getStatus());
				if (detail.getStatus() == 9) {
					detail.setStatus(1);
					logger.info("把" + detail.getId() + "状态从" + detail.getStatus() + "更新到2");
					divisionDetailDao.update(detail);
					logger.info("添加收支明细");
					addRechargeFlow(detail, nty);
				}
			}
		}
	}
	
	private void addRechargeFlow(DivisionDetail divisionDetail, CtrlTransferNotify notify) {
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setLocalNo(notify.getOrderNo());
		rechargeFlow.setOutBizNo(notify.getTradeNo());
		rechargeFlow.setAmount((double) divisionDetail.getAmount());
		rechargeFlow.setUserId(divisionDetail.getUserId());
		Date now = new Date();
		rechargeFlow.setPayTime(now);
		rechargeFlow.setStatus(1);
		rechargeFlow.setPayType(DivisionTypeEnum.PROFIT.getCode());
		rechargeFlow.setPayMemo("分润");
		rechargeFlow.setPayFinishTime(now);
		try {
			rechargeFlowService.addRechargeFlow(rechargeFlow);
		} catch (Exception e) {
			logger.info("添加收支明细失败.");
		}
	}

}
