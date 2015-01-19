package com.icebreak.util.lang.result;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.util.lang.enums.DataOperationCodeEnum;

public class DataOperationResult extends ResultBase {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -3103564323080304914L;
	
	private DataOperationCodeEnum code = DataOperationCodeEnum.EXECUTE_FAIL;
	
	/**
	 * 构建一个<code>DataOperationResult.java</code>
	 */
	public DataOperationResult() {
		super();
	}
	
	/**
	 * 构建一个<code>DataOperationResult.java</code>
	 * @param code
	 */
	public DataOperationResult(DataOperationCodeEnum code) {
		this.code = code;
	}
	
	/**
	 * @return
	 * @see com.icebreak.util.lang.result.ResultBase#isExecuted()
	 */
	@Override
	public boolean isExecuted() {
		return DataOperationCodeEnum.EXECUTE_SUCCESS == code;
	}
	
	public DataOperationCodeEnum getCode() {
		return code;
	}
	
	public void setCode(DataOperationCodeEnum code) {
		this.code = code;
	}
	
	/**
	 * @return
	 * @see com.icebreak.util.lang.result.ResultBase#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
