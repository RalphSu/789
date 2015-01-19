package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class LoanAuthRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String baseId = null;
	
	private long authUserId = 0;
	
	private long loanDemandId = 0;
	
	private String authType = null;
	
	private Date authTime = null;
	
	private String note = null;

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(long authUserId) {
		this.authUserId = authUserId;
	}

	public long getLoanDemandId() {
		return loanDemandId;
	}

	public void setLoanDemandId(long loanDemandId) {
		this.loanDemandId = loanDemandId;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "LoanAuthRecord [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
