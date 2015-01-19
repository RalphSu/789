package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BankCodeBindingSetDefaultRequest extends BaseRequest {

		
	    @NotEmpty(message ="用户ID不能为空" )
		    @Length(max=20,message ="用户ID长度不能超过20" )
	    private String userId;
	
		
	    @NotEmpty(message ="银行卡号不能为空" )
		    @Length(max=20,message ="银行卡号长度不能超过20" )
	    private String bankCardNo;
	
		
	
	public String  getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String  getBankCardNo(){
		return bankCardNo;
	}
	
	public void setBankCardNo(String bankCardNo){
		this.bankCardNo = bankCardNo;
	}
}
