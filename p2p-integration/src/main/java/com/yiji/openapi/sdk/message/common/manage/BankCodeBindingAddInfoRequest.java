package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BankCodeBindingAddInfoRequest extends BaseRequest {

		
	    @NotEmpty(message ="用户ID不能为空" )
		    @Length(max=20,message ="用户ID长度不能超过20" )
	    private String userId;
	
		
	    @NotEmpty(message ="银行卡号不能为空" )
		    @Length(max=20,message ="银行卡号长度不能超过20" )
	    private String bankCardNo;
	
		
		    private String bankKey;
	
		
	    @NotEmpty(message ="银行类型不能为空" )
		    private String bankType;
	
		
		    private String bankCardType;
	
		
		    private String bankAccountType;
	
		
		    private String name;
	
		
		    private String certType;
	
		
		    private String certNo;
	
		
		    private String gender;
	
		
		    private String province;
	
		
		    private String city;
	
		
		    private String address;
	
		
	
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
	
	public String  getBankKey(){
		return bankKey;
	}
	
	public void setBankKey(String bankKey){
		this.bankKey = bankKey;
	}
	
	public String  getBankType(){
		return bankType;
	}
	
	public void setBankType(String bankType){
		this.bankType = bankType;
	}
	
	public String  getBankCardType(){
		return bankCardType;
	}
	
	public void setBankCardType(String bankCardType){
		this.bankCardType = bankCardType;
	}
	
	public String  getBankAccountType(){
		return bankAccountType;
	}
	
	public void setBankAccountType(String bankAccountType){
		this.bankAccountType = bankAccountType;
	}
	
	public String  getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
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
	
	public String  getGender(){
		return gender;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String  getProvince(){
		return province;
	}
	
	public void setProvince(String province){
		this.province = province;
	}
	
	public String  getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String  getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
}
