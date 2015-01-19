package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class ChargeRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收费模版明细ID
	 */
	private long id = 0;
	/**
	 * 收费模版ID
	 */
	private long templateId = 0;
	/**
	 * 收费方式,amount固定金额, percentage:百分比
	 */
	private String method = null;
	/**
	 * 起始金额
	 */
	private long start = 0;
	/**
	 * 结束金额
	 */
	private long end = Long.MAX_VALUE;
	/**
	 * 值
	 */
	private double chargeValue = 0.0f;
	
	public ChargeRule(){}
	
	public ChargeRule(long templateId, String method, double chargeValue) {
		this.templateId = templateId;
		this.method = method;
		this.chargeValue = chargeValue;
	}

	public ChargeRule(long templateId, String method, long start, long end, double chargeValue) {
		this.templateId = templateId;
		this.method = method;
		this.start = start;
		this.end = end;
		this.chargeValue = chargeValue;
	}

	public double getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(double chargeValue) {
		this.chargeValue = chargeValue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "id:" + id + ", templateId:" + templateId + ", method:" + method + ", start:" + start + ", end:" + end;
	}
}
