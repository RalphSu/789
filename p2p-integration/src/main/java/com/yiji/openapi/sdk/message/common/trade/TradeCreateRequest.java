package com.yiji.openapi.sdk.message.common.trade;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yiji.openapi.sdk.message.BaseRequest;
public class TradeCreateRequest extends BaseRequest {
	
	@NotEmpty(message = "交易金额不能为空")
	@Length(max = 20, message = "交易金额长度不能超过20")
	private String tradeAmount;
	
	@Length(max = 20, message = "交易名称长度不能超过20")
	private String tradeName;
	
	@NotEmpty(message = "交易时间不能为空")
	@Length(max = 20, message = "交易时间长度不能超过20")
	private String tradeTime;
	
	@NotEmpty(message = "产品业务号不能为空")
	@Length(max = 20, message = "产品业务号长度不能超过20")
	private String tradeBizProductCode;
	
	@NotEmpty(message = "卖家id不能为空")
	@Length(max = 20, message = "卖家id长度不能超过20")
	private String sellerUserId;
	
	@NotEmpty(message = "交易时间不能为空")
	@Length(max = 20, message = "交易时间长度不能超过20")
	private String buyerUserId;
	
	public String getTradeAmount() {
		return tradeAmount;
	}
	
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public String getTradeTime() {
		return tradeTime;
	}
	
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getSellerUserId() {
		return sellerUserId;
	}
	
	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	
	public String getBuyerUserId() {
		return buyerUserId;
	}
	
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
}
