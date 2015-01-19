package com.yiji.openapi.sdk.executer;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.exception.OpenApiServiceException;
import com.yiji.openapi.sdk.executer.impl.HttpPostNotifyUnmarshall;
import com.yiji.openapi.sdk.executer.impl.HttpPostRequestMarshall;
import com.yiji.openapi.sdk.executer.impl.JsonResponseUnmarshall;
import com.yiji.openapi.sdk.message.BaseNotify;
import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.support.Signatures;
import com.yiji.openapi.sdk.transport.Transport;
import com.yiji.openapi.sdk.util.BeanMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class OpenApiServiceExecuter {
	
	private static Logger logger = LoggerFactory.getLogger(OpenApiServiceExecuter.class);
	
	@Autowired
	private Transport httpPostTransport;
	
	@Autowired
	private Transport httpPostRedirectTransport;
	
	private RequestMarshall requestMarshall = new HttpPostRequestMarshall();
	private ResponseUnmarshall<String, BaseResponse> responseUnmarshall = new JsonResponseUnmarshall();
	private ResponseUnmarshall<Map<String, String>, BaseNotify> notifyUnmarshall = new HttpPostNotifyUnmarshall();
	
	/**
	 * 同步请求
	 * 
	 * @param request
	 * @return
	 */
	public BaseResponse execute(BaseRequest request) {
		try {
			request.validate();
			Transport transport = getTransport(request);
			String requestMessage = requestMarshall.marshall(request);
			logger.info("请求[" + request.getOrderNo() + "]:" + requestMessage);
			String responseMessage = transport.exchange(requestMessage);
			logger.info("响应[" + request.getOrderNo() + "]:" + responseMessage);
			return responseUnmarshall.unmarshall(request.getService(), responseMessage);
		} catch (OpenApiServiceException ose) {
			logger.warn("服务器错误:" + ose.getMessage(), ose);
			throw ose;
		} catch (OpenApiClientException oce) {
			logger.warn("客户端异常:" + oce.getMessage(), oce);
			throw oce;
		} catch (Exception e) {
			logger.warn("调用内部错误:" + e.getMessage(), e);
			throw new OpenApiClientException("调用内部错误:" + e.getMessage());
		}
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Transport getTransport(BaseRequest request) {
		return httpPostRedirectTransport;
	}
	
	/**
	 * 解析异步通知
	 * 
	 * @param serviceName
	 * @param data
	 * @return
	 */
	public BaseNotify parseNotify(String serviceName, Map<String, String> data) {
		return notifyUnmarshall.unmarshall(serviceName, data);
	}
	
	/**
	 * 签名请求报文
	 * 
	 * 用于跳转POST请求报文签名
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> signRequest(BaseRequest request) {
		String signature = Signatures.sign(request);
		request.setSign(signature);
		Map<String, String> data = new HashMap<String, String>();
		BeanMapper.copy(request, data);
		Map<String, String> reuslt = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : data.entrySet()) {
			if (StringUtils.isNotBlank(entry.getValue())) {
				reuslt.put(entry.getKey(), entry.getValue());
			}
		}
		return reuslt;
	}
	
	public void setRequestMarshall(RequestMarshall requestMarshall) {
		this.requestMarshall = requestMarshall;
	}
	
	public void setResponseUnmarshall(ResponseUnmarshall<String, BaseResponse> responseUnmarshall) {
		this.responseUnmarshall = responseUnmarshall;
	}
	
	public void setNotifyUnmarshall(ResponseUnmarshall<Map<String, String>, BaseNotify> notifyUnmarshall) {
		this.notifyUnmarshall = notifyUnmarshall;
	}
	
	public String signAndBuildURL(BaseRequest request) {
		return Constants.OPENAPI_GATEWAY + "?" + requestMarshall.marshall(request);
	}
	
}
