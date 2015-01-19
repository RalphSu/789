package com.yiji.openapi.sdk.message.common.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiji.openapi.sdk.message.BaseResponse;

import java.util.List;


public class QueryBindBankCardResponse extends BaseResponse {

	private String size;

	private List<BindBankCardInfo> cardInfos;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<BindBankCardInfo> getCardInfos() {
		return cardInfos;
	}

	public void setCardInfos(List<BindBankCardInfo> cardInfos) {
		this.cardInfos = cardInfos;
	}

	@Override
	public void unmarshall(String message) {
		JSONObject object = JSON.parseObject(message);
		Object jsonArray = object.get("datas");
		this.cardInfos = JSON.parseArray(jsonArray+"",BindBankCardInfo.class);
	}

	public static class BindBankCardInfo {
		private String cardNo;
		private String userId;
		private String pactStatus;
		private String pactTime;
		private String bindTime;
		private String bankCode;
		private String provinceName;
		private String cityName;

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getPactStatus() {
			return pactStatus;
		}

		public void setPactStatus(String pactStatus) {
			this.pactStatus = pactStatus;
		}

		public String getPactTime() {
			return pactTime;
		}

		public void setPactTime(String pactTime) {
			this.pactTime = pactTime;
		}

		public String getBindTime() {
			return bindTime;
		}

		public void setBindTime(String bindTime) {
			this.bindTime = bindTime;
		}

		public String getBankCode() {
			return bankCode;
		}

		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}

		public String getProvinceName() {
			return provinceName;
		}

		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
	}
}
