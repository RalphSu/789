package com.icebreak.p2p.integration.openapi.result;

import com.yiji.openapi.sdk.message.BaseResponse;

public class RepayReturnRequest extends BaseResponse {

	/** 本次还款的提交结果 */
	String tradeStatus;
	
	/** 本次还款的还款批次号 */
	String createBatchNo;
	
	public String getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public String getCreateBatchNo() {
		return this.createBatchNo;
	}
	
	public void setCreateBatchNo(String createBatchNo) {
		this.createBatchNo = createBatchNo;
	}
	

}
