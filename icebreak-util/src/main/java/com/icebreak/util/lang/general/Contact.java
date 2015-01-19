package com.icebreak.util.lang.general;

import java.io.Serializable;

public class Contact implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -2467779734410411584L;
	
	private String id;
	
	/** 联系人姓名 */
	private String contactName;
	
	/** 公司 */
	private String company;
	
	/** 联系电话 */
	private String phone;
	
	/** 联系手机 */
	private String mobile;
	
	/** 传真 */
	private String fax;
	
	/**
	 * 构建一个<code>Contact.java</code>
	 */
	public Contact() {
		super();
	}
	
	/**
	 * 构建一个<code>Contact.java</code>
	 * @param id
	 * @param contactName
	 * @param company
	 * @param phone
	 * @param mobile
	 * @param fax
	 */
	public Contact(String id, String contactName, String company, String phone, String mobile,
					String fax) {
		super();
		this.id = id;
		this.contactName = contactName;
		this.company = company;
		this.phone = phone;
		this.mobile = mobile;
		this.fax = fax;
	}
	
	/**
	 * @return Returns the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return Returns the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	
	/**
	 * @param contactName The contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	/**
	 * @return Returns the company
	 */
	public String getCompany() {
		return company;
	}
	
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	
	/**
	 * @return Returns the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return Returns the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * @param mobile The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * @return Returns the fax
	 */
	public String getFax() {
		return fax;
	}
	
	/**
	 * @param fax The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
			"Contact [id=%s, contactName=%s, company=%s, phone=%s, mobile=%s, fax=%s]", id,
			contactName, company, phone, mobile, fax);
	}
	
}
