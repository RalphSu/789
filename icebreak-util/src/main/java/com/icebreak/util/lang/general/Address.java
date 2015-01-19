package com.icebreak.util.lang.general;

import java.io.Serializable;

public class Address implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 8588573420557095268L;
	
	private String id;
	
	/** 国家 */
	private String country;
	
	/** 省份 */
	private String province;
	
	/** 城市 */
	private String city;
	
	/** 地区 */
	private String region;
	
	/** 邮编 */
	private String zipCode;
	
	/** 详细地址 */
	private String detailAddress;
	
	/**
	 * 构建一个<code>Address.java</code>
	 */
	public Address() {
		super();
	}
	
	/**
	 * 构建一个<code>Address.java</code>
	 * @param country
	 * @param province
	 * @param city
	 * @param region
	 * @param zipCode
	 * @param detailAddress
	 */
	public Address(String country, String province, String city, String region, String zipCode,
					String detailAddress) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.region = region;
		this.zipCode = zipCode;
		this.detailAddress = detailAddress;
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
	 * @return Returns the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return Returns the province
	 */
	public String getProvince() {
		return province;
	}
	
	/**
	 * @param province The province to set.
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * @return Returns the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return Returns the region
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * @param region The region to set.
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * @return Returns the detailAddress
	 */
	public String getDetailAddress() {
		return detailAddress;
	}
	
	/**
	 * @param detailAddress The detailAddress to set.
	 */
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	/**
	 * @return Returns the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * @param zipCode The zipCode to set.
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String
			.format(
				"Address [id=%s, country=%s, province=%s, city=%s, region=%s, zipCode=%s, detailAddress=%s]",
				id, country, province, city, region, zipCode, detailAddress);
	}
	
}
