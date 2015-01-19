package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseResponse;


public class YzzBankCardBinResponse extends BaseResponse {

	    private String bankId;
	
	    private String bankName;
	
	    private String bankCardName;
	
	    private String bakCardTypeCode;
	
	    private String bankCardTypeName;
	
		
	
	public String  getBankId(){
		return bankId;
	}
	
	public void setBankId(String bankId){
		this.bankId = bankId;
	}
	
	public String  getBankName(){
		return bankName;
	}
	
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	
	public String  getBankCardName(){
		return bankCardName;
	}
	
	public void setBankCardName(String bankCardName){
		this.bankCardName = bankCardName;
	}
	
	public String  getBakCardTypeCode(){
		return bakCardTypeCode;
	}
	
	public void setBakCardTypeCode(String bakCardTypeCode){
		this.bakCardTypeCode = bakCardTypeCode;
	}
	
	public String  getBankCardTypeName(){
		return bankCardTypeName;
	}
	
	public void setBankCardTypeName(String bankCardTypeName){
		this.bankCardTypeName = bankCardTypeName;
	}
}
