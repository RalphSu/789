package com.oms.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oms.bean.ErrorBean;
import com.oms.bean.MenuBean;
import com.oms.bean.MenuBean.Button;
import com.oms.comm.Constants;

public class CreateMenu {
	public static void main(String[] args) throws Exception {
		String access_token = AccessTokenCache.getAccessToken();//获取access_token
		deleteMenu(access_token);//删除旧菜单
		String menu_json = createMenuJson();//创建菜单json
		System.out.println(menu_json);
		sendMenu(access_token, menu_json);//将数据post至微信以创建菜单
	}
	
	public static String createMenuJson(){
		MenuBean menu = new MenuBean();
		List<Button> button = menu.getButton();
		//菜单1
		Button button1=menu.new Button();
		button1.setName(Constants.firstMenu);
		List<Button> sub_button1=button1.getSub_button();
		for(int i=0;i<Constants.firstMenuChildren.length;i++){
			sub_button1.add(menu.createViewButton(Constants.firstMenuChildren[i], createOAuth2Url(Constants.firstMenuChildrenURL[i])));
		}
		//菜单2
		Button button2=menu.new Button();
		button2.setName(Constants.secondMenu);
		List<Button> sub_button2=button2.getSub_button();
		for(int i=0;i<Constants.secondMenuChildren.length;i++){
			sub_button2.add(menu.createViewButton(Constants.secondMenuChildren[i], createOAuth2Url(Constants.secondMenuChildrenURL[i])));
		}	
		//菜单3
		Button button3=menu.new Button();
		button3.setName(Constants.thirdMenu);
		List<Button> sub_button3=button3.getSub_button();
		for(int i=0;i<Constants.thirdMenuChildren.length;i++){
			sub_button3.add(menu.createViewButton(Constants.thirdMenuChildren[i], createOAuth2Url(Constants.thirdMenuChildrenURL[i])));
		}		
		button.add(button1);
		button.add(button2);
		button.add(button3);
		return new GsonBuilder().disableHtmlEscaping().create().toJson(menu);
	}

	/**
	 * 用来生成需要获取微信openId的url
	 * @param url 原始url
	 * @return 转换后的url
	 */
	private static String createOAuth2Url(String url) {
		StringBuilder oAuth2Url = new StringBuilder();
		try {
			oAuth2Url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(Constants.CORPID)
				.append("&redirect_uri=").append(URLEncoder.encode(url, "UTF-8"))
				.append("&response_type=code")
				.append("&scope=snsapi_base")
				.append("&state=123")
				.append("#wechat_redirect");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return oAuth2Url.toString();
	}
	
	private static void deleteMenu(String access_token) throws Exception{
		PostMethod postMethod = new PostMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+access_token);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.executeMethod(postMethod);
		String response = postMethod.getResponseBodyAsString();
		ErrorBean error = new Gson().fromJson(response, ErrorBean.class);
		if(error.getErrcode() != 0){
			throw new RuntimeException("删除菜单错误！" + error.getErrmsg());
		}else{
			System.out.println("&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&&*&*&*&*&*&*&*&*&*&*&删除菜单成功!");
		}
	}
	
	private static void sendMenu(String access_token, String menu_json) throws Exception{
		PostMethod postMethod = new PostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token);
		InputStream is = new ByteArrayInputStream(menu_json.getBytes("UTF-8"));
		RequestEntity re = new InputStreamRequestEntity(is, "text/xml; charset=utf-8");
		postMethod.setRequestEntity(re);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.executeMethod(postMethod);
		String response = postMethod.getResponseBodyAsString();
		ErrorBean error = new Gson().fromJson(response, ErrorBean.class);
		if(error.getErrcode() != 0){
			throw new RuntimeException("创建菜单错误！" + error.getErrmsg());
		}else{
			System.out.println("创建菜单成功!");
		}
	}
	
}
