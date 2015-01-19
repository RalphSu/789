package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseResponse;


public class CheckUserNameExistResponse extends BaseResponse {

	    private String isExist;
	
		
	
	public String  getIsExist(){
		return isExist;
	}
	
	public void setIsExist(String isExist){
		this.isExist = isExist;
	}
}
