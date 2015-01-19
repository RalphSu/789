package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class O2oJoinApplicationDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long id;

	private String companyName;

	private String contactName;

	private String humanResources;

	private String contactPhone;

	private String industry;

	private int age;

	private String salesProducts;

	private String companyAddress;

	private String status;

	private Date rowUpdateTime;

	private Date rowAddTime;

	private String annexUrl;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getHumanResources() {
		return humanResources;
	}
	
	public void setHumanResources(String humanResources) {
		this.humanResources = humanResources;
	}

	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getIndustry() {
		return industry;
	}
	
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public String getSalesProducts() {
		return salesProducts;
	}
	
	public void setSalesProducts(String salesProducts) {
		this.salesProducts = salesProducts;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRowUpdateTime() {
		return rowUpdateTime;
	}
	
	public void setRowUpdateTime(Date rowUpdateTime) {
		this.rowUpdateTime = rowUpdateTime;
	}

	public Date getRowAddTime() {
		return rowAddTime;
	}
	
	public void setRowAddTime(Date rowAddTime) {
		this.rowAddTime = rowAddTime;
	}

	public String getAnnexUrl() {
		return annexUrl;
	}
	
	public void setAnnexUrl(String annexUrl) {
		this.annexUrl = annexUrl;
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
