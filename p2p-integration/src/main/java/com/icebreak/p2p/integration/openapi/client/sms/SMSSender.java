package com.icebreak.p2p.integration.openapi.client.sms;

import com.yiji.openapi.sdk.exception.OpenApiClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("smsSender")
public class SMSSender implements ISender {

    public static final String url = "http://023hy.cn/http/sms/Submit";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String args[]) {
        SMSSender smsSender = new SMSSender();////13594137669
//        smsSender.send("13340200788", "您本次的验证码是：8951，请妥善保管！");
        smsSender.send("13594137669", "您本次的验证码是：8959，请妥善保管！");
//        smsSender.send("18696685225", "您本次的验证码是：8956，请妥善保管！");
//        smsSender.send("15826138575", "您本次的验证码是：8956，请妥善保管！");
//        smsSender.send("13667625908", "您本次的验证码是：8939，请妥善保管！");
//        smsSender.send("18523931030", "您本次的验证码是：8936，请妥善保管！");
        //18696685225
    }

    public String send(String phone,String content) {
//        System.setProperty("javax.net.ssl.trustStore","cer/10690300.truststore");  //设置证书
        Message message = new Message();
        List<String> phones = new ArrayList<String>();
        message.setPhones(phone);
        message.setContent(content);
		logger.info("开始发送短信:" + message.toXMLMessage());
		String result = exchange(message.toXMLMessage());
		logger.info("发送结果：" + result);

        return  null;
    }

    public String exchange(String request) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(request, ContentType.create(
                    "application/x-www-form-urlencoded", "UTF-8")));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "UTF-8");
            body = new String(body.getBytes("ISO-8859-1"),"UTF-8");
            return body;
        } catch (Exception e) {
            throw new OpenApiClientException("通讯请求失败：" + e.getMessage());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
