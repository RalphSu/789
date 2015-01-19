package com.icebreak.p2p.integration.openapi.client.mock.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.icebreak.p2p.integration.openapi.client.mock.HttpSender;
import com.icebreak.p2p.integration.openapi.client.mock.HttpSenderBase;



public class DefaultHttpSender extends HttpSenderBase implements HttpSender {
	
	@Override
	public HttpResponse get(HttpUriRequest request) {
		defaultClient = new DefaultHttpClient();
		HttpResponse response = null;
		
		try {
			response = defaultClient.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		
		return response;
	}
	
	@Override
	public HttpResponse post(HttpUriRequest request) {
		defaultClient = new DefaultHttpClient();
		HttpResponse response = null;
		
		try {
			response = defaultClient.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		
		return response;
	}
	
}
