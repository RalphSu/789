package com.icebreak.util.lang.result;

import java.io.Serializable;

import com.icebreak.util.lang.exception.ResultException;

public interface ResultInfo extends Serializable {
	
	/**
	 * 得到结果状态。
	 * @return 结果状态。
	 */
	Status getStatus();
	
	/**
	 * 得到信息码。
	 * @return 信息码。
	 */
	String getCode();
	
	/**
	 * 得到描述。
	 * @return 描述。
	 */
	String getDescription();
	
	/**
	 * 转换该结果为异常，并且得到该结果的异常信息。
	 * <p>
	 * 当且仅当 {@link #getStatus()} 为 {@link Status#FAIL} 时调用该方法才会返回异常信息。
	 * <p>
	 * 返回的结果异常中的信息码和描述应与该结果中的一致。
	 * @return 该结果转换的异常信息，如果为null表示没有异常。
	 */
	ResultException getResultException();
	
}
