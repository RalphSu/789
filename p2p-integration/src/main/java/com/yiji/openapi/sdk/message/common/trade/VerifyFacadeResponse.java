package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseResponse;


public class VerifyFacadeResponse extends BaseResponse {

	    private String verifyStatus;
	
	    private String errorCode;
	
	    private String errorMsg;
	
	    private String signResult;
	
		
	
	public String  getVerifyStatus(){
		return verifyStatus;
	}
	
	public void setVerifyStatus(String verifyStatus){
		this.verifyStatus = verifyStatus;
	}
	
	public String  getErrorCode(){
		return errorCode;
	}
	
	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}
	
	public String  getErrorMsg(){
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}
	
	public String  getSignResult(){
		return signResult;
	}
	
	public void setSignResult(String signResult){
		this.signResult = signResult;
	}
}
