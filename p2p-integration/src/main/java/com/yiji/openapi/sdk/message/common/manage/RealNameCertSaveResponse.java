package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseResponse;

public class RealNameCertSaveResponse extends BaseResponse {
	
	/** 用户ID **/
	private String userId;
	
	/**
	 * 执行结果 <br />
	 * UN_KNOWN_EXCEPTION : 未知异常<br>
	 * DATABASE_EXCEPTION : 数据库异常<br>
	 * EXECUTE_SUCCESS : 执行成功<br />
	 * EXECUTE_FAILED : 执行失败<br>
	 * INCOMPLETE_REQ_PARAM :请求参数不完整
	 * **/
	private String resultCode;
	
	/**
	 * 外部订单号
	 * @return
	 */
	private String tradeNo;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
