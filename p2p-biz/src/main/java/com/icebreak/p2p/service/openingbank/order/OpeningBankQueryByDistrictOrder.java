package com.icebreak.p2p.service.openingbank.order;

import org.springframework.util.Assert;

import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;

public class OpeningBankQueryByDistrictOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 5768064498841378320L;
	/**
	 * 地区编号
	 */
	private String				districtNo;
	/**
	 * 银行英文简称
	 */
	private String				bankId;
	
	public String getDistrictNo() {
		return districtNo;
	}
	
	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpeningBankQueryByDistrictOrder [districtNo=" + districtNo + ", bankId=" + bankId
				+ "]";
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		Assert.hasText(districtNo, "districtNo 不是字符串");
		Assert.hasText(bankId, "bankId 不是字符串");
	}
	
}
