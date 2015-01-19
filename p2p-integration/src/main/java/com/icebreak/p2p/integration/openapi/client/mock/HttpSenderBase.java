package com.icebreak.p2p.integration.openapi.client.mock;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpSenderBase {
	
	protected Logger		logger			= LoggerFactory.getLogger(getClass());
	
	protected HttpClient	defaultClient	= null;
	
	public List<NameValuePair> putParams(Map<String, String> paramMap) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for (Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator(); iterator
			.hasNext();) {
			Entry<String, String> entry = iterator.next();
			
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		
		return params;
	}
	
	public HttpEntity producePostEntity(Map<String, String> paramMap, String encoding) {
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(putParams(paramMap), encoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		
		return entity;
	}
	
	public URI produceGetUri(Map<String, String> paramMap, String schame, String host, int port,
								String path, String encoding) {
		URI uri = null;
		try {
			uri = URIUtils.createURI(schame, host, port, path,
				URLEncodedUtils.format(putParams(paramMap), encoding), null);
		} catch (URISyntaxException e) {
			logger.error("", e);
		}
		
		return uri;
	}
	
	public HttpPost buildHttpPost(String uri, HttpEntity entity) {
		
		HttpPost post = new HttpPost(uri);
		
		post.setEntity(entity);
		
		return post;
	}
	
	public HttpGet buildHttpRequest(URI uri) {
		HttpGet get = new HttpGet(uri);
		
		return get;
	}
	
}
