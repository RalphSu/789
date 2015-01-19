package com.icebreak.p2p.integration.openapi.client.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@Service("linkSmsSender")
public class LinkSMSSender implements ISender {

	public static final String SIGN = "【789金融】";
	public static final String URL = "http://sdk.mobilesell.cn:89/WS/BatchSend.aspx";
	public static final String CORPID = "CQLKJ0003536";
	public static final String PWD = "xd@849600";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		new LinkSMSSender().send("13594137669", "您本次的验证码是：3158，请妥善保管。");
	}

	/**
	 * 返回0 或者 1 都是  提交成功
	 * @param phone
	 * @param content
	 * @return
	 */
	public String send(String phone,String content) {

		URL url = null;
		//content=SIGN+content;
		String result = "-1";
		if(phone == null || "".equals(phone.trim())){
			logger.info("短信发送失败，手机号码为"+phone+",内容："+content);
			return  result;
		}
		try {
			String sendContent=URLEncoder.encode(content, "GBK");
			url = new URL(URL+"?CorpID="+CORPID+"&Pwd="+PWD+"&Mobile="+phone+"&Content="+sendContent+"&Cell=&SendTime=");
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			result = in.readLine();
		} catch (Exception e) {
			logger.error("发送出现异常:", e);
			result="-2";
		}
		logger.info("发送结果：" + result);
		return result;
	}


}
