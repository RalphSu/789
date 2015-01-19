package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class BankNoQueryRequest extends BaseRequest {

	private String districtName;
	private String bankId;

	public BankNoQueryRequest(String bankId, String districtName) {
		super();
		this.districtName = districtName;
		this.bankId = bankId;
	}

	public BankNoQueryRequest() {
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
