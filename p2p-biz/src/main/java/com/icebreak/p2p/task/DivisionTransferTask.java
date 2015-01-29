package com.icebreak.p2p.task;

import com.icebreak.p2p.daointerface.DivisionDetailDao;
import com.icebreak.p2p.daointerface.UserBaseInfoDAO;
import com.icebreak.p2p.dataobject.DivisionDetail;
import com.icebreak.p2p.dataobject.RechargeFlow;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.ext.enums.DivisionTypeEnum;
import com.icebreak.p2p.integration.openapi.CtrlTransferService;
import com.icebreak.p2p.integration.openapi.context.OpenApiContext;
import com.icebreak.p2p.integration.openapi.order.TransferOrder;
import com.icebreak.p2p.integration.openapi.result.TradeResult;
import com.icebreak.p2p.trade.RechargeFlowService;
import com.icebreak.p2p.transfer.TransferService;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class DivisionTransferTask extends AbstractTask {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private DivisionDetailDao divisionDetailDao;

	private TransferService transferService;

	private String exchangeAccountId = AppConstantsUtil.getExchangeAccount();

	private CtrlTransferService ctrlTransferService;

	private UserBaseInfoDAO userBaseInfoDAO;
	@Resource
	private RechargeFlowService rechargeFlowService;

	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	@Override
	public synchronized void run() {
		if (!canRun()) {
			return;
		}

//		logger.info("开始执行分润转账任务");
		List<DivisionDetail> divisionDetails = divisionDetailDao.getByStatus(0);
		if (ListUtil.isEmpty(divisionDetails)) {
			return;
		}
		for (DivisionDetail divisionDetail : divisionDetails) {
			if (transferService.isRiskDevisionDetail(divisionDetail)) {
				logger.error("此分润为风险分润，系统已自动拒绝执行！divisionId="
						+ divisionDetail.getId());
				continue;
			}
			UserBaseInfoDO user = userBaseInfoDAO.queryByUserId(divisionDetail
					.getUserId());
			try {
				TransferOrder order = new TransferOrder();
				OpenApiContext openApiContext = new OpenApiContext();
				openApiContext.setOpenApiBizNo(divisionDetail.getBusinessCode());
				openApiContext.setNotifyUrl(Constants.CtrlTransferNotifyUrl);

				order.setPayerUserId(exchangeAccountId);
				order.setSellerUserId(user.getAccountId());
				order.setTradeAmount(MoneyUtil.getMoneyByLong(divisionDetail
						.getAmount()));
				order.setTradeMemo("收益");
				order.setTradeName("收益");
				//付款操作(利润)
				TradeResult result = ctrlTransferService.transfer(order,
						openApiContext);
				String resultCode = result.getResultCode();
				int status = 0;
				if (StringUtil.equalsIgnoreCase(resultCode, "EXECUTE_SUCCESS")) {
					status = 1;
					logger.info("添加到收支明细");
					addRechargeFlow(divisionDetail,result);
				} else if (StringUtil.equalsIgnoreCase(resultCode, "TRADE_IN_PROCESSING")) {
					status = 9;
				}
				divisionDetail.setStatus(status);
				divisionDetail.setOutBizNo(result.getTradeNo());
				divisionDetailDao.update(divisionDetail);
			} catch (Exception e) {
				logger.error(
						"分润转账任务异常:流水号--" + divisionDetail.getBusinessCode(),
						e);
			}
		}
	}

	private void addRechargeFlow(DivisionDetail divisionDetail,TradeResult result) throws Exception {
		RechargeFlow rechargeFlow = new RechargeFlow();
		rechargeFlow.setLocalNo(result.getOrderNo());
		rechargeFlow.setOutBizNo(result.getTradeNo());
		rechargeFlow.setAmount((double) divisionDetail.getAmount());
		rechargeFlow.setUserId(divisionDetail.getUserId());
		Date now = new Date();
		rechargeFlow.setPayTime(now);
		rechargeFlow.setStatus(1);
		rechargeFlow.setPayType(DivisionTypeEnum.PROFIT.getCode());
		rechargeFlow.setPayMemo("分润");
		rechargeFlow.setPayFinishTime(now);
		rechargeFlowService.addRechargeFlow(rechargeFlow);
	}

	public void setDivisionDetailDao(DivisionDetailDao divisionDetailDao) {
		this.divisionDetailDao = divisionDetailDao;
	}

	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	public void setCtrlTransferService(CtrlTransferService ctrlTransferService) {
		this.ctrlTransferService = ctrlTransferService;
	}

	public void setUserBaseInfoDAO(UserBaseInfoDAO userBaseInfoDAO) {
		this.userBaseInfoDAO = userBaseInfoDAO;
	}

}
