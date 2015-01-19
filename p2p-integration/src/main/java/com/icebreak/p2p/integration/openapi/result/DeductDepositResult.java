package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.enums.OpenAPIErrorCodeEnum;
import com.icebreak.p2p.integration.openapi.info.DepositInfo;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ext.enums.DepositStatusEnum;

public class DeductDepositResult extends P2PBaseResult {

	private static final long	serialVersionUID	= 9086185514887765864L;
	/**
	 * 代扣充值id
	 */
	String						depositId;
	
	String						openApiErrorCode;
	String						memo;
	DepositInfo					depositInfo;
	/**
	 * 网银充值有用
	 */
	String						url;
	DepositStatusEnum			depositStatusEnum;
	
	public String getDepositId() {
		return depositId;
	}
	
	public void setDepositId(String depositId) {
		this.depositId = depositId;
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
	
	public DepositInfo getDepositInfo() {
		return depositInfo;
	}
	
	public void setDepositInfo(DepositInfo depositInfo) {
		this.depositInfo = depositInfo;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	@Override
	public void setUrl(String url) {
		this.url = url;
	}
	
	public DepositStatusEnum getDepositStatusEnum() {
		return depositStatusEnum;
	}
	
	public void setDepositStatusEnum(DepositStatusEnum depositStatusEnum) {
		this.depositStatusEnum = depositStatusEnum;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeductDepositResult [depositId=");
		builder.append(depositId);
		builder.append(", openApiErrorCode=");
		builder.append(openApiErrorCode);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", depositInfo=");
		builder.append(depositInfo);
		builder.append(", url=");
		builder.append(url);
		builder.append(", depositStatusEnum=");
		builder.append(depositStatusEnum);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
