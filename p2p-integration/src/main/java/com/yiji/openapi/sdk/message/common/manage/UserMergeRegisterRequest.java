package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;
import org.hibernate.validator.constraints.Length;

public class UserMergeRegisterRequest extends BaseRequest {

		
		    @Length(max=50,message ="用户名称长度不能超过50" )
	    private String userName;
	
		
		    @Length(max=40,message ="登录密码长度不能超过40" )
	    private String loginPassword;
	
		
		    @Length(max=128,message ="用户邮箱长度不能超过128" )
	    private String email;
	
		
		    @Length(max=1,message ="用户类型长度不能超过1" )
	    private String userType;
	
		
		    @Length(max=50,message ="用户真实名称长度不能超过50" )
	    private String realName;
	
		
		    @Length(max=20,message ="证件类型长度不能超过20" )
	    private String certType;
	
		
		    @Length(max=50,message ="证件号码长度不能超过50" )
	    private String certNo;
	
		
		    @Length(max=8,message ="证件有效期长度不能超过8" )
	    private String certVaildTime;
	
		
		    @Length(max=16,message ="手机号码长度不能超过16" )
	    private String mobileNo;
	
		
		    @Length(max=20,message ="所属商户长度不能超过20" )
	    private String merchantId;
	
		
		    @Length(max=20,message ="用户状态长度不能超过20" )
	    private String userStatus;
	
		
		    @Length(max=20,message ="注册来源长度不能超过20" )
	    private String registerFrom;
	
		
	
	public String  getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String  getLoginPassword(){
		return loginPassword;
	}
	
	public void setLoginPassword(String loginPassword){
		this.loginPassword = loginPassword;
	}
	
	public String  getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String  getUserType(){
		return userType;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	public String  getRealName(){
		return realName;
	}
	
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String  getCertType(){
		return certType;
	}
	
	public void setCertType(String certType){
		this.certType = certType;
	}
	
	public String  getCertNo(){
		return certNo;
	}
	
	public void setCertNo(String certNo){
		this.certNo = certNo;
	}
	
	public String  getCertVaildTime(){
		return certVaildTime;
	}
	
	public void setCertVaildTime(String certVaildTime){
		this.certVaildTime = certVaildTime;
	}
	
	public String  getMobileNo(){
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}
	
	public String  getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String  getUserStatus(){
		return userStatus;
	}
	
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
	}
	
	public String  getRegisterFrom(){
		return registerFrom;
	}
	
	public void setRegisterFrom(String registerFrom){
		this.registerFrom = registerFrom;
	}
}
