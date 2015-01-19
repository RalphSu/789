package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseRequest;

public class QueryBindBankCardRequest extends BaseRequest {

		
	    private String userId;
	    private String cardNo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
}
