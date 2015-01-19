package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TradePayRequest extends BaseRequest {

		
	    @NotEmpty(message ="用户名不能为空" )
		    @Length(max=20,message ="用户名长度不能超过20" )
	    private String userName;
	
		
	    @NotEmpty(message ="交易号不能为空" )
		    @Length(max=20,message ="交易号长度不能超过20" )
	    private String tradeNo;
	
		
	    @NotEmpty(message ="来源系统不能为空" )
		    @Length(max=50,message ="来源系统长度不能超过50" )
	    private String systemId;
	
		
	    @NotEmpty(message ="付款人会员号不能为空" )
		    @Length(max=20,message ="付款人会员号长度不能超过20" )
	    private String payerId;
	
		
		    @Length(max=27,message ="交易额度长度不能超过27" )
	    private String tradeAmount;
	
		
	
	public String  getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String  getTradeNo(){
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo){
		this.tradeNo = tradeNo;
	}
	
	public String  getSystemId(){
		return systemId;
	}
	
	public void setSystemId(String systemId){
		this.systemId = systemId;
	}
	
	public String  getPayerId(){
		return payerId;
	}
	
	public void setPayerId(String payerId){
		this.payerId = payerId;
	}
	
	public String  getTradeAmount(){
		return tradeAmount;
	}
	
	public void setTradeAmount(String tradeAmount){
		this.tradeAmount = tradeAmount;
	}
}
