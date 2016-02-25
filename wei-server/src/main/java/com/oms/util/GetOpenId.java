package com.oms.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.google.gson.Gson;
import com.oms.bean.OAuth2AccessToken;
import com.oms.comm.Constants;

public class GetOpenId {
	public static String getOpenId(String code) {
		try{
			StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token")
									.append("?appid=").append(Constants.CORPID)
									.append("&secret=").append(Constants.SECRET)
									.append("&code=").append(code)
									.append("&grant_type=").append("authorization_code");
			GetMethod getMethod = new GetMethod(url.toString());
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			httpClient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();
			System.out.println(response);
			OAuth2AccessToken accessToken = new Gson().fromJson(response, OAuth2AccessToken.class);
			if(accessToken == null || accessToken.getAccess_token() == null
					|| accessToken.getAccess_token().trim().length() == 0){
				throw new RuntimeException("获取Access Token错误！");
			}
			return accessToken.getOpenid();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		System.out.println(getOpenId("021d0e944467023bad02e566710812di"));
	}
}
