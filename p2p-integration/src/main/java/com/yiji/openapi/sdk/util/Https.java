package com.yiji.openapi.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;

/**
 * Http 请求简单工具
 * 
 *
 * 
 */
public class Https {

	public static final int POST_DATA_FORMAT_FORM = 1;
	public static final int POST_DATA_FORMAT_JSON = 2;

	public static String get(String url) {
		return null;
	}

	public static HttpResult post(String url, Map<String, Object> postData, String encoding) {
		return post(url, null, postData, encoding, POST_DATA_FORMAT_FORM);
	}

	public static HttpResult post(String url, Map<String, Object> postData, String encoding, int format) {
		return post(url, null, postData, encoding, format);
	}

	public static HttpResult post(String url, Map<String, String> headers, Map<String, Object> postData,
			String encoding, int format) {
		if (postData == null || postData.isEmpty()) {
			throw new RuntimeException("POST DATA 不允许为空");
		}
		if (headers == null) {
			headers = Maps.newHashMap();
		}
		StringBuilder sb = new StringBuilder();
		if (format == POST_DATA_FORMAT_FORM) {
			if (headers.containsKey("Content-Type")) {
				headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			}
			for (Map.Entry<String, Object> entry : postData.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		} else if (format == POST_DATA_FORMAT_JSON) {
			if (headers.containsKey("Content-Type")) {
				headers.put("application/json", "application/x-www-form-urlencoded; charset=UTF-8");
			}
			sb.append(JsonMapper.nonEmptyMapper().toJson(postData));
		} else {
			throw new UnsupportedOperationException("不支持的格式,format:" + format);
		}
		return post(url, headers, sb.toString(), encoding);
	}

	public static HttpResult post(String url, Map<String, String> headers, String body, String encoding) {
		HttpURLConnection conn = null;
		InputStream in = null;
		OutputStream out = null;
		HttpResult result = new HttpResult();
		try {
			URL realURL = new URL(url);
			HttpURLConnection.setFollowRedirects(true);
			conn = (HttpURLConnection) realURL.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			/* 请求头参数 */
			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					if (entry.getValue() != null) {
						conn.setRequestProperty(entry.getKey(), Encodes.urlEncode(entry.getValue(), encoding));
					}
				}
			}

			out = conn.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(body.getBytes()), out);
			out.flush();

			int status = conn.getResponseCode();
			result.setStatus(status);
			if (status >= 200 && status < 400) {
				in = conn.getInputStream();
			} else {
				in = conn.getErrorStream();
			}
			result.setHeaders(readHeadFormResponse(conn));
			StringWriter sw = new StringWriter();
			IOUtils.copy(in, sw, encoding);
			result.setBody(sw.toString());
			IOUtils.closeQuietly(sw);
		} catch (Exception e) {
			throw new RuntimeException("POST通讯失败: " + e.getMessage(), e);
		} finally {
			// 释放资源和连接
			IOUtils.close(conn);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		return result;
	}

	private static Map<String, String> readHeadFormResponse(HttpURLConnection conn) {
		Map<String, List<String>> responseHeaders = conn.getHeaderFields();
		Map<String, String> result = Maps.newHashMap();
		for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
			if (entry.getKey() != null) {
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {
					if (entry.getValue().size() == 1) {
						result.put(entry.getKey(), entry.getValue().get(0));
					} else {
						result.put(entry.getKey(), entry.getValue().toString());
					}
				}

			}
		}
		return result;
	}

	public static class HttpResult {
		private int status;
		private Map<String, String> headers;
		private String body;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Map<String, String> headers) {
			this.headers = headers;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		@Override
		public String toString() {
			return "HttpResult [status=" + status + ", headers=" + headers + ", body=" + body + "]";
		}

	}

	public static void main(String[] args) {
		// HttpResult result = post("http://www.baidu.com", null, "123456",
		// "gbk");
		// System.out.println(result);
	}

}
