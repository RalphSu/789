package com.icebreak.p2p.integration.openapi.client.mock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface HttpSender {
	
	HttpResponse get(HttpUriRequest request);
	
	HttpResponse post(HttpUriRequest request);
}
