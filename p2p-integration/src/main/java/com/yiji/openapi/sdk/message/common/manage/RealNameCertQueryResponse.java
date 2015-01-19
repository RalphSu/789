package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseResponse;

public class RealNameCertQueryResponse extends BaseResponse {
	
	/** 用户id **/
	private String coreCusUserId;
	
	/** 实名认证状态 */
	private String stauts;
	
	/** 备注信息 */
	private String msg;
	
	public String getCoreCusUserId() {
		return coreCusUserId;
	}
	
	public void setCoreCusUserId(String coreCusUserId) {
		this.coreCusUserId = coreCusUserId;
	}
	
	public String getStauts() {
		return stauts;
	}
	
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
