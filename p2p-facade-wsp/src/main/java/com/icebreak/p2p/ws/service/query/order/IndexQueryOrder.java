package com.icebreak.p2p.ws.service.query.order;

import java.io.Serializable;

public class IndexQueryOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2874889736244100090L;
	
	Integer status;
	String  guarantee; //担保公司名称，如：重庆渝台信用担保有限公司
	String  areaCode; //地区代码    每个地区的代码来自common_district.nb_no  也参考addLoanDemand.vm 
	String  interestRateBegin;
	String  interestRateEnd;
	String  loanType;

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getInterestRateBegin() {
		return interestRateBegin;
	}
	public void setInterestRateBegin(String interestRateBegin) {
		this.interestRateBegin = interestRateBegin;
	}
	public String getInterestRateEnd() {
		return interestRateEnd;
	}
	public void setInterestRateEnd(String interestRateEnd) {
		this.interestRateEnd = interestRateEnd;
	}

	

}
