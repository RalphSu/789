package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class DepositRequest extends BaseRequest {
	/** 用户ID */
	private String userId;
	/** 业务产品编号:个人网银充值 */
	private String tradeBizProductCode = Constants.PRODUCT_CODE_DEPOSIT_PERSONAL_EBANK;
	/** 平台商户 */
	private String tradeMerchantId;
	/** 参与者 */
	private String tradePartnerId;
	/** 充值金额 元 */
	private String depositAmount;

	public DepositRequest(String userId, String depositAmount) {
		super();
		setNotifyUrl(Constants.DEPOSIT_NOTIFY_URL);
		setErrorNotifyUrl(Constants.DEPOSIT_RETRUE_URL);
		setReturnUrl(Constants.DEPOSIT_RETRUE_URL);
		this.userId = userId;
		this.tradePartnerId = userId;
		this.depositAmount = depositAmount;
	}

	public DepositRequest() {
		super();
		setNotifyUrl(Constants.DEPOSIT_NOTIFY_URL);
		setErrorNotifyUrl(Constants.DEPOSIT_RETRUE_URL);
		setReturnUrl(Constants.DEPOSIT_RETRUE_URL);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}

	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}

	public String getTradeMerchantId() {
		return tradeMerchantId;
	}

	public void setTradeMerchantId(String tradeMerchantId) {
		this.tradeMerchantId = tradeMerchantId;
	}

	public String getTradePartnerId() {
		return tradePartnerId;
	}

	public void setTradePartnerId(String tradePartnerId) {
		this.tradePartnerId = tradePartnerId;
	}

	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
