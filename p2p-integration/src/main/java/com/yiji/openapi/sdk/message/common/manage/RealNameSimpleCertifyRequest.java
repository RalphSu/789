package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class RealNameSimpleCertifyRequest extends BaseRequest {

		
	    @NotEmpty(message ="用户Id不能为空" )
		    @Length(max=20,message ="用户Id长度不能超过20" )
	    private String userId;
	
		
	    @NotEmpty(message ="用户真实名称不能为空" )
		    @Length(max=50,message ="用户真实名称长度不能超过50" )
	    private String realName;
	
		
	    @NotEmpty(message ="身份证号码不能为空" )
		    @Length(max=50,message ="身份证号码长度不能超过50" )
	    private String certNo;
	
		
	    @NotEmpty(message ="身份证有效期不能为空" )
		    @Length(max=8,message ="身份证有效期长度不能超过8" )
	    private String certValidTime;
	
		
	
	public String  getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String  getRealName(){
		return realName;
	}
	
	public void setRealName(String realName){
		this.realName = realName;
	}
	
	public String  getCertNo(){
		return certNo;
	}
	
	public void setCertNo(String certNo){
		this.certNo = certNo;
	}
	
	public String  getCertValidTime(){
		return certValidTime;
	}
	
	public void setCertValidTime(String certValidTime){
		this.certValidTime = certValidTime;
	}
}
