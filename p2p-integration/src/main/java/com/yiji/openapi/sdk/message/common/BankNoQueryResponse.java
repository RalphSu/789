package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.util.JsonMapper;

public class BankNoQueryResponse extends BaseResponse {

	/** 联行号 */
	private String bankLasalle;
	/** 开户行名称 */
	private String branchName;

	public BankNoQueryResponse() {
	}

	public String getBankLasalle() {
		return bankLasalle;
	}

	public void setBankLasalle(String bankLasalle) {
		this.bankLasalle = bankLasalle;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
