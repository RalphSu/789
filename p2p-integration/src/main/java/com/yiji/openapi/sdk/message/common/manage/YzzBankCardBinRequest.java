package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

public class YzzBankCardBinRequest extends BaseRequest {

		
	    @NotEmpty(message ="银行卡号不能为空" )
		    private String bankCardNo;
	
		
	
	public String  getBankCardNo(){
		return bankCardNo;
	}
	
	public void setBankCardNo(String bankCardNo){
		this.bankCardNo = bankCardNo;
	}
}
