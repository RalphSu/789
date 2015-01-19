package com.yiji.openapi.sdk.message.yzz;

import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.util.JsonMapper;

public class YzzUserSpecialRegisterResponse extends BaseResponse {
	private String userId;
	private String modifyPwdAddress;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModifyPwdAddress() {
		return modifyPwdAddress;
	}

	public void setModifyPwdAddress(String modifyPwdAddress) {
		this.modifyPwdAddress = modifyPwdAddress;
	}

	public boolean registSuccess() {
		if(StringUtil.equalsIgnoreCase("EXECUTE_SUCCESS",super.getResultCode())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
