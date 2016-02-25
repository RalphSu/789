package com.yiji.openapi.sdk.notify;

import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.service.common.OpenApiGatewayService;
import com.yiji.openapi.sdk.service.yzz.YzzGatewayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotifyHandlerDispatcher implements ApplicationContextAware {
	private static Logger logger = LoggerFactory
			.getLogger(NotifyHandlerDispatcher.class);
	@Autowired
	private OpenApiGatewayService openApiGatewayService;
	@Autowired
	private YzzGatewayService yzzGatewayService;

	private ApplicationContext applicationContext;

	public void dispatch(String notifyUrl, Map<String, String> notifyData) {
		try {
			logger.info("支付网关异步通知,{url:{},:{}}", notifyUrl, notifyData);

			// 网银充值
			if (StringUtils.endsWith(Constants.DEPOSIT_NOTIFY_URL, notifyUrl)) {
				openApiGatewayService
						.notifyDeposit(
								notifyData,
								(NotifyHandler) getHandlerInstance("eBankDepositNotifyHandler"));
			}

			// 批量转账
			if (StringUtils.endsWith(Constants.TRADEPAYPOOLREVERSE_NOTIFY_URL,
					notifyUrl)) {
				openApiGatewayService
						.notifyTradePayPoolReverse(
								notifyData,
								(NotifyHandler) getHandlerInstance(Constants.TRADEPAYPOOLREVERSE_NOTIFY_HANDLER));
			}

			// 风控转账
			if (StringUtils
					.endsWith(Constants.CtrlTransferNotifyUrl, notifyUrl)) {
				yzzGatewayService
						.notifyCtrlTransfer(
								notifyData,
								(NotifyHandler) getHandlerInstance("ctrlTransferNotifyHandler"));
			}

			// 投资
			if (StringUtils.endsWith(Constants.investNotifyUrl, notifyUrl)) {
				String executeAction = notifyData.get("executeAction");

				// 集体付款
				if (StringUtil.equalsIgnoreCase(executeAction, "poolTogertherTradeFinished")) {
					yzzGatewayService.notifyPayTogether(notifyData,
							(NotifyHandler) getHandlerInstance("payTogetherNotifyHandler"));
				} else if (StringUtil.equalsIgnoreCase(executeAction,
					"poolTogertherTradeBorrowApply")) {
				yzzGatewayService
						.notifyInvestApply(
								notifyData,
								(NotifyHandler) getHandlerInstance("investApplyNotifyHandler"));
				}
			}

			// 还款
			if (StringUtils.endsWith(Constants.repayNotifyUrl, notifyUrl)) {
				yzzGatewayService
						.notifyRepay(
								notifyData,
								(NotifyHandler) getHandlerInstance("repayNotifyHandler"));
			}

			// 提现
			if (StringUtils.endsWith(Constants.YzzStrengthenWithdrawNotifyUrl,
					notifyUrl)) {
				yzzGatewayService
						.yzzStrengthenWithdraw(
								notifyData,
								(NotifyHandler) getHandlerInstance("yzzStrengthenWithdrawNotifyHandler"));
			}
			
			// 签约
			if (StringUtils.endsWith(Constants.signManyBankNotifyUrl, notifyUrl)) {
				openApiGatewayService.notifySignManyBank(notifyData,
					(NotifyHandler) getHandlerInstance("signManyBankNotifyHandler"));
			}

			// 实名认证通知
			if (StringUtils.endsWith(Constants.realNameCertNotifyUrl, notifyUrl)) {
				openApiGatewayService.notifySignManyBank(notifyData,
					(NotifyHandler) getHandlerInstance("realNameCertNotifyHandler"));
			}
		} catch (Exception e) {
			logger.warn("支付网关异步通知 处理失败", e);
			throw new OpenApiClientException("支付网关异步通知处理失败:{}", e);
		}

	}

	private NotifyHandler getHandlerInstance(String handlerClassPath) {
		try {
			return (NotifyHandler) applicationContext.getBean(handlerClassPath);
		} catch (Exception e) {
			logger.warn("Spring容器不存在handler实例bean:{}", handlerClassPath);
			try {
				// return (NotifyHandler) clazz.newInstance();
			} catch (Exception e2) {
				logger.warn("notifyHandler反射创建失败:{}", handlerClassPath);
				return null;
			}
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
