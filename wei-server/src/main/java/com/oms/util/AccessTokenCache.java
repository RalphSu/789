package com.oms.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.oms.bean.AccessToken;
import com.oms.bean.JsapiTicket;
import com.oms.comm.Constants;

public class AccessTokenCache {
	private final static Logger log = LogManager.getLogger(AccessTokenCache.class);
	private volatile static String accessToken;
	private volatile static String jsapi_ticket;

	public static String getAccessToken() {
		if(accessToken == null){
			getAccessTokenFromWeChat();
		}
		return accessToken;
	}
	
	public static String getJsapi_ticket() {
		if(jsapi_ticket == null){
			getJsapiTicketFromWeChat();
		}
		return jsapi_ticket;
	}
	private static void getAccessTokenFromWeChat(){
		try{
			StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
			url.append("&appid=").append(Constants.CORPID)
				.append("&secret=").append(Constants.SECRET);
			GetMethod getMethod = new GetMethod(url.toString());
			HttpClient httpClient = new HttpClient();
			int state = httpClient.executeMethod(getMethod);
			if(state == HttpStatus.SC_OK){
				String response = getMethod.getResponseBodyAsString();
				AccessToken bean = new Gson().fromJson(response, AccessToken.class);
				if(bean != null){
					AccessTokenCache.accessToken = bean.getAccess_token();
				}
			}
		}catch (Exception e) {
			log.error("获取AccessToken时异常", e);
		}
	}
	
	private static void getJsapiTicketFromWeChat(){
		try{
			StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
			url.append("?access_token=").append(accessToken)
				.append("&type=jsapi");
			GetMethod getMethod = new GetMethod(url.toString());
			HttpClient httpClient = new HttpClient();
			int state = httpClient.executeMethod(getMethod);
			if(state == HttpStatus.SC_OK){
				String response = getMethod.getResponseBodyAsString();
				JsapiTicket bean = new Gson().fromJson(response, JsapiTicket.class);
				if(bean != null){
					AccessTokenCache.jsapi_ticket = bean.getTicket();
				}
			}
		}catch (Exception e) {
			log.error("获取AccessToken时异常", e);
		}
	}
	
	public void doTask(){
		log.debug("---------------定时获取AccessToken-------------");
		getAccessTokenFromWeChat();
		getJsapiTicketFromWeChat();
	}


}
