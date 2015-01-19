package com.yiji.openapi.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.AbstractEnvironment;

import com.yiji.openapi.sdk.util.ConfigurableConstants;

/**
 * 客户端配置参数
 * 
 *
 */
public class Constants extends ConfigurableConstants {

	private static final Logger logger = LoggerFactory
			.getLogger(Constants.class);
	static {
		String activeProfile = System
				.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
		String configFile = "config.properties";
		if (activeProfile != null && !"".equals(activeProfile)) {
			configFile = "config-" + activeProfile + ".properties";
		}
		logger.info("configuration file: {}", configFile);
		init(configFile);
	}

	public static final String ACCESSKEY = getProperty("partnerId",
			"20141104020000065851");
	public static final String SECRETKEY = getProperty("secretKey",
			"");

	public static final String PARTNER_ID = getProperty("partnerId",
			"20141104020000065851");
	public static final String EXCHANGE_ACCOUNT = getProperty(
			"exchangeAccount", "20141106010000067051");

	public static final String CHARSET = getProperty("charset", "UTF-8");
	public static final String OPENAPI_GATEWAY = getProperty("openApiUrl",
			"http://bornapi.yijifu.net/gateway.html");

	public static final String WEBSITE_DOMAIN = getProperty("websiteDomain",
			"");
	public static final String DEPOSIT_RETRUE_URL = WEBSITE_DOMAIN
			+ getProperty("deposit.return.url", "");
	public static final String DEPOSIT_NOTIFY_URL = WEBSITE_DOMAIN
			+ getProperty("deposit.notify.url", "");
	public static final String DEPOSIT_NOTIFY_HANDLER = getProperty(
			"deposit.notify.handler", "");
	public static final String WITHDRAW_NOTIFY_URL = WEBSITE_DOMAIN
			+ getProperty("withdraw.notify.url", "");
	public static final String APPLYWITHDRAW_NOTIFY_HANDLER = getProperty(
			"applyWithdraw.notify.handler", "");
	public static final String TRADEPAYPOOLREVERSE_NOTIFY_URL = WEBSITE_DOMAIN
			+ getProperty("tradePayPoolReverse.notify.url", "");
	public static final String TRADEPAYPOOLREVERSE_NOTIFY_HANDLER = getProperty(
			"tradePayPoolReverse.notify.handler", "");

	/** 给力式交易共用 */
	public static final String LOAN_NOTIFY_URL = WEBSITE_DOMAIN
			+ getProperty("loan.notify.url", "");
	public static final String PAYAPPLY_RETRUE_URL = WEBSITE_DOMAIN
			+ getProperty("payerApply.return.url", "");
	/** 个人网银充值 产品编码 */
	public static final String PRODUCT_CODE_DEPOSIT_PERSONAL_EBANK = getProperty(
			"openapi.productcode.deposit.personal.ebank", "20131213");

	public static final String tradeBizProductCode = getProperty(
			"tradeBizProductCode", "20130326_pool");
	public static final String CtrlTransferNotifyUrl = getProperty(
			"ctrlTransferNotifyUrl", "");
	public static final String PayTogetherNotifyUrl = getProperty(
			"payTogetherNotifyUrl", "");
	public static final String InvestReturnUrl = getProperty("investReturnUrl",
			"");
	public static final String investNotifyUrl = getProperty("investNotifyUrl",
			"");
	public static final String YzzStrengthenWithdrawNotifyUrl = getProperty(
			"YzzStrengthenWithdrawNotifyUrl", "");
	public static final String YzzStrengthenWithdrawReturnUrl = getProperty(
			"YzzStrengthenWithdrawReturnUrl", "");

	public static final String repayNotifyUrl = getProperty("repayNotifyUrl",
			"");
	public static final String repayReturnUrl = getProperty("repayReturnUrl",
			"");
	public static final String signManyBankReturnUrl = getProperty("signManyBankReturnUrl", "");
	public static final String signManyBankNotifyUrl = getProperty("signManyBankNotifyUrl", "");

	public static final String realNameCertReturnUrl = getProperty("realNameCertsReturnUrl", "");
	public static final String realNameCertNotifyUrl = getProperty("realNameCertNotifyUrl", "");

	public static final String eBankDepositReturnUrl = getProperty("eBankDepositReturnUrl", "");
	public static final String eBankDepositNotifyUrl = getProperty("eBankDepositNotifyUrl", "");

	public static final String fastpayNotifyUrl = getProperty("fastpayNotifyUrl", "");
	public static final String fastpayReturnUrl = getProperty("fastpayReturnUrl", "");

	public static final String specialTransferNotifyUrl = getProperty("specialTransferNotifyUrl", "");


}
