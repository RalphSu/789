package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.enums.OpenAPIErrorCodeEnum;
import com.icebreak.p2p.ws.result.P2PBaseResult;

public class WithdrawResult extends P2PBaseResult {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 9086185514887765864L;
	/**
	 * 提现id
	 */
	String						withdrawId;
	String						openApiErrorCode;
	String						memo;
	String						outBizNo;
	String						url;
	
	public String getWithdrawId() {
		return withdrawId;
	}
	
	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}
	
	public String getOpenApiErrorCode() {
		return openApiErrorCode;
	}
	
	public void setOpenApiErrorCode(String openApiErrorCode) {
		this.openApiErrorCode = openApiErrorCode;
	}
	
	public boolean isRepeat() {
		return OpenAPIErrorCodeEnum.REPEAT_OUT_BIZ_NO.code().equals(openApiErrorCode);
	}
	
	public boolean isExecuteFailure() {
		return OpenAPIErrorCodeEnum.EXECUTE_FAILURE.code().equals(openApiErrorCode);
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WithdrawResult [withdrawId=");
		builder.append(withdrawId);
		builder.append(", openApiErrorCode=");
		builder.append(openApiErrorCode);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", outBizNo=");
		builder.append(outBizNo);
		builder.append("]");
		return builder.toString();
	}
}
