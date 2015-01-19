package com.icebreak.p2p.integration.openapi.client.sms;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


/**此版本使用document 对象封装XML，解决发送短信内容包涵特殊字符而出现无法解析，如 短信为：“你好，<%&*&*&><<<>fds测试短信”
 * @author 8373
 */
public class HttpXmlUtil {

	// ############################此部分参数需要修改############################
	public static String userName = "test"; 			// 用户名
	public static String password = "test"; 			// 密码
	public static String phone = "18621803633"; 		// 要发送的手机号码
	public static String content = "tes<%%%*&^>t1"; 	// 短信内容
	public static String sign = ""; 				// 签名内容，此签名需要提前报备
	public static String msgid ="";						//短信id,建议为空
	public static String subcode ="";					//子号码，可空
	public static String sendtime ="";     				//定时下发时间，可空，空为立即发送
	public static String url="https://www.10690300.com"; 		//无限通使用url地址
	//public static String url="http://www.10690300.com"; //三网通使用地址

	// ############################此部分参数需要修改############################


	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore","cer/10690300.truststore");  //设置证书
		// 发送短信
		System.out.println("*************发送短信*************");
		send();

		// 获取状态报告
		System.out.println("*************状态报告*************");
		getReport();

		// 获取上行
		System.out.println("*************获取上行*************");
		getSms();

		// 获取余额
		System.out.println("*************获取余额*************");
		getBalance();



	}

	// MD5加密函数
	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer bf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				bf.append("0");
			}
			bf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return bf.toString();
	}

	// 发送短信
	/**
	 * 发送短信方法使用document 对象方法封装XML字符串
	 */
	private static void send() {
		String posturl = url+"/http/sms/Submit";
		Map<String, String> params = new LinkedHashMap<String, String>();
		HttpXmlUtil docXml = new HttpXmlUtil();
		//使用document对象封装XML ,解决发送“<%^&*>”等内容无法解析
		String message=docXml.DocXml(userName, MD5Encode(password), msgid, phone, content, sign, subcode, sendtime);
		System.out.println(message);
		params.put("message", message);

		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	// 状态报告
	private static void getReport() {
		String posturl = url+"/http/sms/Report";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userName + "</account>" + "<password>"
				+ MD5Encode(password) + "</password>"
				+ "<msgid></msgid><phone>18221177661</phone></message>";
		params.put("message", message);

		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	// 查询余额
	private static void getBalance() {
		String posturl = url+"/http/sms/Balance";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
				+ userName
				+ "</account>"
				+ "<password>"
				+ MD5Encode(password)
				+ "</password></message>";
		params.put("message", message);
		System.out.println(message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	// 获取上行
	private static void getSms() {
		String posturl = url+"/http/sms/Deliver";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
				+ userName
				+ "</account>"
				+ "<password>"
				+ MD5Encode(password)
				+ "</password></message>";
		params.put("message", message);

		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 *
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	private static String doPost(String url, Map<String, String> params) {

		String response = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

		// 设置Post数据
		if (!params.isEmpty()) {
			int i = 0;
			NameValuePair[] data = new NameValuePair[params.size()];
			for (Entry<String, String> entry : params.entrySet()) {
				data[i] = new NameValuePair(entry.getKey(), entry.getValue());
				i++;
			}

			postMethod.setRequestBody(data);

		}
		try {
			client.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
				response = postMethod.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return response;
	}

	public  String DocXml(String userName,String pwd,String msgid,String  phone,String contents,String sign,String  subcode,String sendtime) {
		/*Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element message = doc.addElement("response");
		Element account = message.addElement("account");
		account.setText(userName);
		Element password = message.addElement("password");
		password.setText(pwd);
		Element msgid1 = message.addElement("msgid");
		msgid1.setText(msgid);
		Element phones = message.addElement("phones");
		phones.setText(phone);
		Element content = message.addElement("content");
		content.setText(contents);
		Element sign1 = message.addElement("sign");
		sign1.setText(sign);
		Element subcode1 = message.addElement("subcode");
		subcode1.setText(subcode);
		Element sendtime1 = message.addElement("sendtime");
		sendtime1.setText(sendtime);
		return message.asXML();*/
		//System.out.println(message.asXML());
		return null;
	}


}
