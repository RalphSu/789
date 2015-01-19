package com.yiji.openapi.sdk.transport;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.exception.OpenApiClientException;

@Component("httpPostRedirectTransport")
public class HttpPostRedirectTransport implements Transport {
	
	protected static Logger logger = LoggerFactory.getLogger(HttpPostRedirectTransport.class);
	private String paymentGateway = Constants.OPENAPI_GATEWAY;
	
	private static String EMPTY_JSON_STRING = "{}";
	
	@Override
	public String exchange(String request) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(paymentGateway);
		logger.info("请求地址：{}", paymentGateway);
		try {
			post.setEntity(new StringEntity(request, ContentType.create(
				"application/x-www-form-urlencoded", "UTF-8")));
			HttpResponse response = client.execute(post);
			
			if (HttpStatus.FOUND.value() == response.getStatusLine().getStatusCode()) {
				return EMPTY_JSON_STRING;
			}
			
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity, "UTF-8");
			return body;
		} catch (Exception e) {
			throw new OpenApiClientException("通讯请求失败：" + e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
	
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	
}
