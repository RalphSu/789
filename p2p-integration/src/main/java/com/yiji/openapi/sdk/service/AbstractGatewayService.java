package com.yiji.openapi.sdk.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yiji.openapi.sdk.exception.OpenApiServiceException;
import com.yiji.openapi.sdk.executer.OpenApiServiceExecuter;
import com.yiji.openapi.sdk.message.BaseRequest;
import com.yiji.openapi.sdk.message.BaseResponse;
import com.yiji.openapi.sdk.support.Ids;
import com.yiji.openapi.sdk.support.Signatures;
import com.yiji.openapi.sdk.util.BeanMapper;

public abstract class AbstractGatewayService {
	
	public static final String SUCCESS_FLAG = "T";
	public static final String SUCCESS_RESULT_CODE = "EXECUTE_SUCCESS,success";
	
	protected OpenApiServiceExecuter executor;
	
	protected String generateOrderNo() {
		return Ids.getInstance().getOrderNo();
	}
	
	/**
	 * 断言返回报文存在EXECUTE_SUCCESS,success， 如果返回结果中没有SUCCESS则报错
	 * @param response
	 * @return
	 */
	protected BaseResponse assertExcecuteSuccess(BaseResponse response) {
		
//		if (!StringUtils.contains(SUCCESS_RESULT_CODE, response.getResultCode())) {
//			throw new OpenApiServiceException(response.getResultCode(), response.getResultMessage());
//		}
		return response;
	}
	
	/**
	 * 断言返回报文存在 SUCCESS,success， 如果返回结果中没有SUCCESS则报错
	 * @param response
	 * @return
	 */
	protected BaseResponse assertSuccess(BaseResponse response) {
		if (!SUCCESS_FLAG.equals(response.getSuccess())) {
			throw new OpenApiServiceException(response.getResultCode(), response.getResultMessage());
		}
		return response;
	}
	
	protected BaseResponse execute(BaseRequest request) {
		if (StringUtils.isBlank(request.getOrderNo())) {
			request.setOrderNo(generateOrderNo());
		}
		return executor.execute(request);
	}
	
	/**
	 * 计算signature
	 */
	protected Map<String, String> signRequest(BaseRequest request) {
		request.setOrderNo(generateOrderNo());
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
	
	public OpenApiServiceExecuter getExecuter() {
		return executor;
	}
	
	public void setExecuter(OpenApiServiceExecuter executer) {
		this.executor = executer;
	}
	
}
