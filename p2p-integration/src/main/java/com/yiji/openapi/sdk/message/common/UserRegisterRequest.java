package com.yiji.openapi.sdk.message.common;

import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.util.JsonMapper;

/**
 * 用户注册请求
 * 
 *
 * 
 */
public class UserRegisterRequest extends BaseRequest {

	public static final String USER_TYPE_PERSON = "P";
	public static final String USER_TYPE_MERCHANT = "B";

	private String userName;
	private String userType = USER_TYPE_PERSON;
	private String userStatus = "T";
	private String realName;
	private String loginPassword;
	private String email;

	private String registerFrom = "E_TURNOVER";
	private String originRegisterFrom = "EXT_IMPORT";

	public UserRegisterRequest() {
		super();
	}

	public UserRegisterRequest(String userName, String realName, String loginPassword, String email) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.loginPassword = loginPassword;
		this.email = email;
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

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterFrom() {
		return registerFrom;
	}

	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}

	public String getOriginRegisterFrom() {
		return originRegisterFrom;
	}

	public void setOriginRegisterFrom(String originRegisterFrom) {
		this.originRegisterFrom = originRegisterFrom;
	}

	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}

}
