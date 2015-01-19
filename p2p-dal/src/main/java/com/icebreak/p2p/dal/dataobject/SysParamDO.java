package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class SysParamDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private String paramName;

	private String paramValue;

	private String extendAttributeOne;

	private String extendAttributeTwo;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}
	
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getExtendAttributeOne() {
		return extendAttributeOne;
	}
	
	public void setExtendAttributeOne(String extendAttributeOne) {
		this.extendAttributeOne = extendAttributeOne;
	}

	public String getExtendAttributeTwo() {
		return extendAttributeTwo;
	}
	
	public void setExtendAttributeTwo(String extendAttributeTwo) {
		this.extendAttributeTwo = extendAttributeTwo;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}


	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
