package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TradeQueryRequest extends BaseRequest {

		
	    @NotEmpty(message ="交易号不能为空" )
		    @Length(max=20,message ="交易号长度不能超过20" )
	    private String tradeNo;
	
		
	
	public String  getTradeNo(){
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo){
		this.tradeNo = tradeNo;
	}
}
