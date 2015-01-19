package com.yiji.openapi.sdk.message.yzz;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

public class YzzUserSpecialRegisterRequest extends BaseRequest {
	
	public static final String USER_TYPE_PERSON = "P";
	public static final String USER_TYPE_MERCHANT = "B";
	
	private String userName;
	private String userType = USER_TYPE_PERSON;
	private String realName;
	private String email;
	private String mobile;
	private String certNo;
	String isMainland = "Y";
	private String certValidTime;
	
	public YzzUserSpecialRegisterRequest() {
		super();
	}
	
	/**
	 * @param userName
	 * @param realName
	 * @param email
	 * @param mobileNo
	 * @param certNo
	 */
	public YzzUserSpecialRegisterRequest(String userName, String realName, String email,
											String mobile, String certNo) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.email = email;
		this.mobile = mobile;
		this.certNo = certNo;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getCertValidTime() {
		return this.certValidTime;
	}
	
	public void setCertValidTime(String certValidTime) {
		this.certValidTime = certValidTime;
	}
	
	public String getIsMainland() {
		return this.isMainland;
	}

	public void setIsMainland(String isMainland) {
		this.isMainland = isMainland;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
