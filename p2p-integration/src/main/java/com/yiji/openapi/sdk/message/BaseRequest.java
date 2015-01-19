package com.yiji.openapi.sdk.message;

import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.exception.OpenApiClientException;
import com.yiji.openapi.sdk.support.MessageSupport;
import com.yiji.openapi.sdk.support.YjfOpenApiValidatorFactory;
import com.yiji.openapi.sdk.util.JsonMapper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public abstract class BaseRequest {
	
	/** 合作伙伴网站唯一订单号 */
	@NotEmpty(message = "合作伙伴网站唯一订单号不能为空")
	private String orderNo;
	
	@NotEmpty(message = "服务名不能为空")
	private String service = MessageSupport.getRequestServiceName(this);
	
	@NotEmpty(message = "合作伙伴ID不能为空")
	@Length(max = 20, min = 20, message = "合作伙伴ID长度必须是20位")
	private String partnerId = Constants.ACCESSKEY;
	
	@NotEmpty(message = "签名类型不能为空")
	private String signType = "MD5";
	
	private String sign;
	
	/** 异步通知：服务器异步通知页面路径 */
	private String notifyUrl;
	/** 同步：页面跳转同步通知页面路径 */
	private String returnUrl;
	/** 异步通知：请求出错时的通知页面路径 */
	private String errorNotifyUrl;
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getPartnerId() {
		return partnerId;
	}
	
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	public String getSignType() {
		return signType;
	}
	
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}
	
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public String getErrorNotifyUrl() {
		return errorNotifyUrl;
	}
	
	public void setErrorNotifyUrl(String errorNotifyUrl) {
		this.errorNotifyUrl = errorNotifyUrl;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**
	 * 验证请求参数合法性，子類可选實現。也可以使用其他验证框架实现。验证失败时需要抛出OpenApiClientException异常 <br />
	 */
	public void validate() {
		
		Validator validator = YjfOpenApiValidatorFactory.INSTANCE.getValidator();
		Set<ConstraintViolation<BaseRequest>> validateResultSet = validator.validate(this);
		if (!validateResultSet.isEmpty()) {
			throw new OpenApiClientException(concatMessage(validateResultSet));
		}
		
		// ...
		doValidate();
	}
	
	/**
	 * @param validateResultSet
	 * @return
	 */
	private String concatMessage(Set<ConstraintViolation<BaseRequest>> validateResultSet) {
		StringBuilder validateResultMsgBuilder = new StringBuilder();
		for (ConstraintViolation<BaseRequest> constraintViolation : validateResultSet) {
			validateResultMsgBuilder.append(constraintViolation.getPropertyPath()).append(":")
				.append(constraintViolation.getMessage()).append(",");
		}
		if (validateResultMsgBuilder.length() > 1) {
			return StringUtils.removeEnd(validateResultMsgBuilder.toString(), ",");
		} else
			return validateResultMsgBuilder.length() > 1 ? StringUtils.removeEnd(
				validateResultMsgBuilder.toString(), ",") : validateResultMsgBuilder.toString();
	}
	
	protected void doValidate() {
		
	}
	
	@Override
	public String toString() {
		return JsonMapper.nonDefaultMapper().toJson(this);
	}
	
}
