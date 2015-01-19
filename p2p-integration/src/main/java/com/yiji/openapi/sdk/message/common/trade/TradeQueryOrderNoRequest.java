package com.yiji.openapi.sdk.message.common.trade;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;
public class TradeQueryOrderNoRequest extends BaseRequest {

		
	    @NotEmpty(message ="订单号不能为空" )
		    @Length(max=20,message ="订单号长度不能超过20" )
	    private String qOrderNo;

		public String getQOrderNo() {
			return qOrderNo;
		}

		public void setQOrderNo(String qOrderNo) {
			this.qOrderNo = qOrderNo;
		}
	
		
	
	
}
