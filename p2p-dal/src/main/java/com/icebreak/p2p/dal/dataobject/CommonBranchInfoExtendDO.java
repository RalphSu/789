package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CommonBranchInfoExtendDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -4282603875229233564L;
	
	//========== properties ==========
	
	/**
	 * This property corresponds to db column <tt>bank_lasalle</tt>.
	 */
	private String				bankLasalle;
	
	/**
	 * This property corresponds to db column <tt>branch_name</tt>.
	 */
	private String				branchName;
	
	/**
	 * This property corresponds to db column <tt>branch_district_no</tt>.
	 */
	private String				branchDistrictNo;
	
	/**
	 * This property corresponds to db column <tt>bank_id</tt>.
	 */
	private String				bankId;
	
	/**
	 * 地区名称
	 */
	private String				areaName;
	
	/**
	 * 上级地区的编号
	 */
	private String				fatherNo;
	
	//========== getters and setters ==========
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String getBankLasalle() {
		return bankLasalle;
	}
	
	public void setBankLasalle(String bankLasalle) {
		this.bankLasalle = bankLasalle;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchDistrictNo() {
		return branchDistrictNo;
	}
	
	public void setBranchDistrictNo(String branchDistrictNo) {
		this.branchDistrictNo = branchDistrictNo;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getFatherNo() {
		return fatherNo;
	}
	
	public void setFatherNo(String fatherNo) {
		this.fatherNo = fatherNo;
	}
}
