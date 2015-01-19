package com.yiji.openapi.sdk.message.common.manage;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CheckUserNameExistRequest extends BaseRequest {

		
	    @NotEmpty(message ="用户名不能为空" )
		    @Length(max=20,message ="用户名长度不能超过20" )
	    private String userName;
	
		
	
	public String  getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
}
