package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseResponse;


public class BankCodeBindingAddInfoResponse extends BaseResponse {

	    private String resultCode;
	
		
	
	public String  getResultCode(){
		return resultCode;
	}
	
	public void setResultCode(String resultCode){
		this.resultCode = resultCode;
	}
}
