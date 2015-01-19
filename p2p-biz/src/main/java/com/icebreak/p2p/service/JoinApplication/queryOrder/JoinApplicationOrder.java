package com.icebreak.p2p.service.JoinApplication.queryOrder;

import java.io.Serializable;
import java.util.Date;

import com.icebreak.p2p.dal.dataobject.O2oJoinApplicationDO;

public class JoinApplicationOrder extends O2oJoinApplicationDO implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -3055796728022655200L;
	
	private long id;
	
	private String companyName;

	private String contactName;

	private String humanResources;

	private String contactPhone;

	private String industry;

	private String userAge;

	private String salesProducts;

	private String companyAddress;

	private String status;

	private Date rowUpdateTime;

	private Date rowAddTime;

	private String annexUrl;


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

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	@Override
	public String toString() {
		return "JoinApplicationOrder [id=" + id + ", companyName="
				+ companyName + ", contactName=" + contactName
				+ ", humanResources=" + humanResources + ", contactPhone="
				+ contactPhone + ", industry=" + industry + ", userAge="
				+ userAge + ", salesProducts=" + salesProducts
				+ ", companyAddress=" + companyAddress + ", status=" + status
				+ ", rowUpdateTime=" + rowUpdateTime + ", rowAddTime="
				+ rowAddTime + ", annexUrl=" + annexUrl + "]";
	}

	
	

	
	
	

}
