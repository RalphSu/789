package com.yiji.openapi.sdk.exception;

public class OpenApiServiceException extends RuntimeException {
	/** UID */
	private static final long serialVersionUID = 2148374270769534530L;
	
	private String resultCode;
	private String resultMessage;
	
	public OpenApiServiceException() {
		super();
	}
	
	/**
	 * @param resultCode
	 * @param resultMessage
	 */
	public OpenApiServiceException(String resultMessage) {
		super(resultMessage);
		this.resultMessage = resultMessage;
	}
	
	/**
	 * @param resultCode
	 * @param resultMessage
	 */
	public OpenApiServiceException(String resultCode, String resultMessage) {
		super(resultMessage);
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	
	public OpenApiServiceException(String resultCode, String resultMessage, Throwable cause) {
		super(resultMessage, cause);
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	
	@Override
	public String getMessage() {
		return "服务端异常:" + super.getMessage();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("服务端异常:").append(resultCode).append(":").append(resultMessage);
		return builder.toString();
	}
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}
