package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;
import com.icebreak.util.lang.util.money.Money;

public class BankBaseInfoDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private String bankCode;

	private String bankName;

	private Money withholdingAmount = new Money(0, 0);

	private Money withdrawAmount = new Money(0, 0);

	private String signedWay;

	private String isStop;

	private String logoUrl;

	private String memo;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Money getWithholdingAmount() {
		return withholdingAmount;
	}
	
	public void setWithholdingAmount(Money withholdingAmount) {
		if (withholdingAmount == null) {
			this.withholdingAmount = new Money(0, 0);
		} else {
			this.withholdingAmount = withholdingAmount;
		}
	}

	public Money getWithdrawAmount() {
		return withdrawAmount;
	}
	
	public void setWithdrawAmount(Money withdrawAmount) {
		if (withdrawAmount == null) {
			this.withdrawAmount = new Money(0, 0);
		} else {
			this.withdrawAmount = withdrawAmount;
		}
	}

	public String getSignedWay() {
		return signedWay;
	}
	
	public void setSignedWay(String signedWay) {
		this.signedWay = signedWay;
	}

	public String getIsStop() {
		return isStop;
	}
	
	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}

	public String getLogoUrl() {
		return logoUrl;
	}
	
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}


	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
