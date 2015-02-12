package com.icebreak.p2p.notify;

import com.icebreak.p2p.common.services.MessageService;
import com.icebreak.p2p.daointerface.AmountFlowDao;
import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.icebreak.p2p.daointerface.TradeDao;
import com.icebreak.p2p.daointerface.TransferTradeDao;
import com.icebreak.p2p.daointerface.UserBaseInfoDAO;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.yzz.PayTogetherNotify;
import com.yiji.openapi.sdk.notify.NotifyHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("payTogetherNotifyHandler")
public class PayTogetherNotifyHandler implements NotifyHandler {

	private static Logger logger = LoggerFactory.getLogger(PayTogetherNotifyHandler.class);
	@Resource
	private TransferTradeDao transferTradeDao;
	@Resource
	private DivisionDetailDao divisionDetailDao;
	@Resource
	private TradeDao tradeDao;
	@Resource
	private AmountFlowDao amountFlowDao;
	@Resource
	private RechargeFlowService rechargeFlowService;
	@Resource
	private TransactionTemplate transactionTemplate;
	@Resource
	private UserBaseInfoDAO userBaseInfoDAO;
	@Autowired
	private MessageService messageService;

	@Override
	public void handleNotify(BaseNotify notify) {
		logger.info("集体付款回推：" + notify);

		PayTogetherNotify nty = (PayTogetherNotify) notify;

		final String tradeNo = nty.getTradeNo();
		final Trade trade = tradeDao.getByTradeCode(tradeNo);
		logger.info("匹配回推交易："+trade.getName()+",ID:"+trade.getId());
		
		if (StringUtil.equalsIgnoreCase("TRANS_DOING", nty.getExecuteStatus())) {
			logger.info("推回来是处理中，暂不处理。");
		} else if (StringUtil.equalsIgnoreCase("SUCCESS", nty.getExecuteStatus())) {
			logger.info("付款成功回推");

			// 保持事务一致，避免多次回推时的重复处理
			transactionTemplate.execute(new TransactionCallback<Integer>(){

				@Override
				public Integer doInTransaction(TransactionStatus status) {
					List<AmountFlow> amountFlows = amountFlowDao.queryPayTogetherForUpdate(trade.getId(), 1);
					if (ListUtil.isNotEmpty(amountFlows)) {
						logger.info("查询到AmountFlow记录数：" + amountFlows.size());
						long loanerId = 0;
						for (AmountFlow amountFlow : amountFlows) {
							logger.info("更新AmountFlow状态{}为[2]：", amountFlow.getFlowCode());
							amountFlowDao.modifyStatus(amountFlow.getId(), 2);

							if (StringUtil.equalsIgnoreCase("invest", amountFlow.getType())) {
								logger.info("生成投资人收支明细{}", amountFlow.getFlowCode());
								addInvestorRechargeFlow(amountFlow,	trade);

							} else if (StringUtil.equalsIgnoreCase("division", amountFlow.getType())) {
								logger.info("生成融资人收支明细--分润转出{}", amountFlow.getFlowCode());
								loanerId = addLoanerProfitRechargeFlow(amountFlow);
							}
						}

						logger.info("生成融资人收支明细--投资满标集体转入。");
						addLoanerLoanRechargeFlow(tradeNo, trade, loanerId);
						logger.info("AmountFlow全部更新完成.发送短信通知");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("tradeId", trade.getId());
						List<UserBaseInfoDO> users = userBaseInfoDAO.queryAllUserInfoList(params);
						String smsContent = "您购买的789金融"+trade.getName();
						messageService.notifyUsersBySms(users, smsContent);
						List<TransferTrade> investDevisionList = transferTradeDao.getPhaseTransferTrades(
								trade.getId(), DivisionPhaseEnum.INVESET_PHASE.getCode(), null, null);
						if(ListUtil.isEmpty(investDevisionList)) {
							logger.info("居然没有风控转账流水，可能出问题了哦。");
							return -1;
						}
						try {
							logger.info("创建风控转账流水，条数为："+investDevisionList.size());
							for (TransferTrade transfer : investDevisionList) {
								DivisionDetail detail = new DivisionDetail(transfer.getUserId(),
										BusinessNumberUtil.gainOutBizNoNumber(), trade.getId(),
										transfer.getTradeDetailId(), transfer.getAmount(), 0, new Date());
								divisionDetailDao.addDivisionDetail(detail);
							}
							logger.info("添加分润转账流水成功");
						} catch (Exception e) {
							logger.error("生成风控转账流水失败", e);
						}
					}
					return 0;
				}
			});
		}
	}

	private void addLoanerLoanRechargeFlow(String tradeNo, Trade trade, long loanerId) {
		RechargeFlow flow = new RechargeFlow();
		flow.setLocalNo(BusinessNumberUtil.getSerialNo());
		flow.setOutBizNo(tradeNo);
		flow.setUserId(loanerId);
		flow.setStatus(1);
		flow.setPayType(DivisionTypeEnum.LOAN.getCode());
		flow.setAmount(trade.getAmount());
		flow.setPayTime(new Date());
		flow.setPayMemo("借款到账");
		try {
            rechargeFlowService.addRechargeFlow(flow);
        } catch (Exception e) {
            logger.error("生成收支明细失败", e);
        }
	}

	private long addLoanerProfitRechargeFlow(AmountFlow amountFlow) {
		long loanerId;RechargeFlow flow = new RechargeFlow();
		flow.setLocalNo(BusinessNumberUtil.getSerialNo());
		flow.setOutBizNo(amountFlow.getFlowCode());
		loanerId = amountFlow.getOutUserId();
		flow.setUserId(loanerId);
		flow.setYjfAccountId(amountFlow.getAmountOut());
		flow.setStatus(1);
		flow.setPayType(DivisionTypeEnum.PROFIT.getCode());
		flow.setAmount(amountFlow.getAmount());
		flow.setPayTime(new Date());
		flow.setPayMemo("融资利息支出");
		try {
            rechargeFlowService.addRechargeFlow(flow);
        } catch (Exception e) {
            logger.error("生成收支明细失败", e);
        }
		return loanerId;
	}

	private void addInvestorRechargeFlow(AmountFlow amountFlow, Trade trade) {
		RechargeFlow flow = new RechargeFlow();
		flow.setLocalNo(BusinessNumberUtil.getSerialNo());
		flow.setOutBizNo(amountFlow.getFlowCode());
		flow.setUserId(amountFlow.getOutUserId());
		flow.setYjfAccountId(amountFlow.getAmountOut());
		flow.setStatus(1);
		flow.setPayType(DivisionTypeEnum.INVEST.getCode());
		flow.setAmount(amountFlow.getAmount());
		flow.setPayTime(new Date());
		flow.setPayMemo("投资转账|" + trade.getName());
		try {
            rechargeFlowService.addRechargeFlow(flow);
        } catch (Exception e) {
            logger.error("生成收支明细失败", e);
        }
	}

}
