package com.icebreak.p2p.service.openingbank.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;

public class OpeningBankByDisNameAndBankIdOrder extends ValidateOrderBase implements Order {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 地区名称
	 */
	private String				districtName;
	/**
	 * 银行英文简称
	 */
	private String				bankId;
	
	@Override
	public void check() {
		Assert.hasText(districtName, "districtNo 不是字符串");
		Assert.hasText(bankId, "bankId 不是字符串");
	}
	
	public String getDistrictName() {
		return districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Override
	public String toString() {
		return "OpeningBankByDisNameAndBankIdOrder [districtName=" + districtName + ", bankId="
				+ bankId + "]";
	}
	
}
