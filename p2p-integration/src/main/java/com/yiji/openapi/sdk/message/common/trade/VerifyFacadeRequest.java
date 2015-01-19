package com.yiji.openapi.sdk.message.common.trade;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;

public class VerifyFacadeRequest extends BaseRequest {


		    @Length(max=60,message ="银行简称长度不能超过60" )
	    private String bankCode;
	

	    @NotEmpty(message ="银行卡户名不能为空" )
		    @Length(max=30,message ="银行卡户名长度不能超过30" )
	    private String accountName;
	
		
	    @NotEmpty(message ="银行卡卡号不能为空" )
		    @Length(max=30,message ="银行卡卡号长度不能超过30" )
	    private String accountNo;
	
		
	    @Length(max=10,message ="证件类型长度不能超过10" )
	    private String certType = "ID";
	
		
	    @NotEmpty(message ="证件号码不能为空" )
		    @Length(max=30,message ="证件号码长度不能超过30" )
	    private String certNo;
	
		

	public String  getBankCode(){
		return bankCode;
	}
	
	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}
	
	public String  getAccountName(){
		return accountName;
	}
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	public String  getAccountNo(){
		return accountNo;
	}
	
	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	
	public String  getCertType(){
		return certType;
	}
	
	public void setCertType(String certType){
		this.certType = certType;
	}
	
	public String  getCertNo(){
		return certNo;
	}
	
	public void setCertNo(String certNo){
		this.certNo = certNo;
	}
	
}
