package com.icebreak.util.lang.result;

public class ResultBase implements Result {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 4165926587298446217L;
	
	/** 成功状态 */
	private boolean success = false;
	
	/** 辅助客户端翻译处理结果代码的描述，包括各层处理结果描述 */
	private String message = "";
	
	/**
	 * 构建一个<code>ResultBase.java</code>
	 */
	public ResultBase() {
		super();
	}
	
	/**
	 * @return
	 * @see com.icebreak.util.lang.result.Result#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * @return
	 * @see com.icebreak.util.lang.result.Result#isExecuted()
	 */
	@Override
	public boolean isExecuted() {
		
		return false;
	}
	
	/**
	 * @return Returns the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @param success The success to set.
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultBase [success=" + success + ", message=" + message + "]";
	}
	
}
