package com.yiji.openapi.sdk.message.common.trade;

import java.util.Map;

import com.yiji.openapi.sdk.message.BaseResponse;

public class TradeQueryResponse extends BaseResponse {
	
	private String tradeNo;
	
	private String tradeType;
	
	private String tradeName;
	
	private String tradeAmount;
	
	private String actuallyPaid;
	
	private String refundAmount;
	
	private String buyerId;
	
	private String buyerRealName;
	
	private String buyerEmail;
	
	private String buyerMemo;
	
	private String sellerId;
	
	private String sellerRealName;
	
	private String sellerEmail;
	
	private String isRefund;
	
	private String tradeStatus;
	
	private String tradeStatusFlag;
	
	private String sellerMessage;
	
	private String buyerMessage;
	
	private String tradeEndTime;
	
	private String tradeFrom;
	
	private String merchantId;
	
	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getTradeType() {
		return tradeType;
	}
	
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public String getTradeAmount() {
		return tradeAmount;
	}
	
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public String getActuallyPaid() {
		return actuallyPaid;
	}
	
	public void setActuallyPaid(String actuallyPaid) {
		this.actuallyPaid = actuallyPaid;
	}
	
	public String getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public String getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBuyerRealName() {
		return buyerRealName;
	}
	
	public void setBuyerRealName(String buyerRealName) {
		this.buyerRealName = buyerRealName;
	}
	
	public String getBuyerEmail() {
		return buyerEmail;
	}
	
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	
	public String getBuyerMemo() {
		return buyerMemo;
	}
	
	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}
	
	public String getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getSellerRealName() {
		return sellerRealName;
	}
	
	public void setSellerRealName(String sellerRealName) {
		this.sellerRealName = sellerRealName;
	}
	
	public String getSellerEmail() {
		return sellerEmail;
	}
	
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	
	public String getIsRefund() {
		return isRefund;
	}
	
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	
	public String getTradeStatus() {
		return tradeStatus;
	}
	
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	public String getTradeStatusFlag() {
		return tradeStatusFlag;
	}
	
	public void setTradeStatusFlag(String tradeStatusFlag) {
		this.tradeStatusFlag = tradeStatusFlag;
	}
	
	public String getSellerMessage() {
		return sellerMessage;
	}
	
	public void setSellerMessage(String sellerMessage) {
		this.sellerMessage = sellerMessage;
	}
	
	public String getBuyerMessage() {
		return buyerMessage;
	}
	
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	
	public String getTradeEndTime() {
		return tradeEndTime;
	}
	
	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}
	
	public String getTradeFrom() {
		return tradeFrom;
	}
	
	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@SuppressWarnings("unchecked")
	public void setData(Map<String, Object> data) {
		Map<String, String> dataMap = (Map<String, String>) data.get("hashMap");
		Object tradeNoObj = dataMap.get("tradeNo");
		if (tradeNoObj != null) {
			this.setTradeNo((String) tradeNoObj);
		}
		Object tradeTypeObj = dataMap.get("tradeType");
		if (tradeTypeObj != null) {
			this.setTradeType((String) tradeTypeObj);
		}
		Object tradeNameObj = dataMap.get("tradeName");
		if (tradeNameObj != null) {
			this.setTradeName((String) tradeNameObj);
		}
		Object tradeAmountObj = dataMap.get("tradeAmount");
		if (tradeAmountObj != null) {
			this.setTradeAmount((String) tradeAmountObj);
		}
		Object actuallyPaidObj = dataMap.get("actuallyPaid");
		if (actuallyPaidObj != null) {
			this.setActuallyPaid((String) actuallyPaidObj);
		}
		Object refundAmountObj = dataMap.get("refundAmount");
		if (refundAmountObj != null) {
			this.setRefundAmount((String) refundAmountObj);
		}
		Object buyerIdObj = dataMap.get("buyerId");
		if (buyerIdObj != null) {
			this.setBuyerId((String) buyerIdObj);
		}
		Object buyerRealNameObj = dataMap.get("buyerRealName");
		if (buyerRealNameObj != null) {
			this.setBuyerRealName((String) buyerRealNameObj);
		}
		Object buyerEmailObj = dataMap.get("buyerEmail");
		if (buyerEmailObj != null) {
			this.setBuyerEmail((String) buyerEmailObj);
		}
		Object buyerMemoObj = dataMap.get("buyerMemo");
		if (buyerMemoObj != null) {
			this.setBuyerMemo((String) buyerMemoObj);
		}
		Object sellerIdObj = dataMap.get("sellerId");
		if (sellerIdObj != null) {
			this.setSellerId((String) sellerIdObj);
		}
		Object sellerRealNameObj = dataMap.get("sellerRealName");
		if (sellerRealNameObj != null) {
			this.setSellerRealName((String) sellerRealNameObj);
		}
		Object sellerEmailObj = dataMap.get("sellerEmail");
		if (sellerEmailObj != null) {
			this.setSellerEmail((String) sellerEmailObj);
		}
		Object isRefundObj = dataMap.get("isRefund");
		if (isRefundObj != null) {
			this.setIsRefund((String) isRefundObj);
		}
		Object tradeStatusObj = dataMap.get("tradeStatus");
		if (tradeStatusObj != null) {
			this.setTradeStatus((String) tradeStatusObj);
		}
		Object tradeStatusFlagObj = dataMap.get("tradeStatusFlag");
		if (tradeStatusFlagObj != null) {
			this.setTradeStatusFlag((String) tradeStatusFlagObj);
		}
		Object sellerMessageObj = dataMap.get("sellerMessage");
		if (sellerMessageObj != null) {
			this.setSellerMessage((String) sellerMessageObj);
		}
		Object buyerMessageObj = dataMap.get("buyerMessage");
		if (buyerMessageObj != null) {
			this.setBuyerMessage((String) buyerMessageObj);
		}
		Object tradeEndTimeObj = dataMap.get("tradeEndTime");
		if (tradeEndTimeObj != null) {
			this.setTradeEndTime((String) tradeEndTimeObj);
		}
		Object tradeFromObj = dataMap.get("tradeFrom");
		if (tradeFromObj != null) {
			this.setTradeFrom((String) tradeFromObj);
		}
		Object merchantIdObj = dataMap.get("merchantId");
		if (merchantIdObj != null) {
			this.setMerchantId((String) merchantIdObj);
		}
		
		Object resultCodeObj = dataMap.get("resultCode");
		if (resultCodeObj != null) {
			setResultCode((String) resultCodeObj);
		}
		Object resultMessageObj = dataMap.get("resultMessage");
		if (resultMessageObj != null) {
			setResultMessage((String) resultMessageObj);
		}
		Object orderNoObj = data.get("orderNo");
		if (orderNoObj != null) {
			setOrderNo((String) orderNoObj);
		}
		Object signTypeObj = data.get("signType");
		if (signTypeObj != null) {
			setSignType((String) signTypeObj);
		}
		Object successObj = data.get("success");
		if (successObj != null) {
			setSuccess((String) successObj);
		}
		
	}
}
