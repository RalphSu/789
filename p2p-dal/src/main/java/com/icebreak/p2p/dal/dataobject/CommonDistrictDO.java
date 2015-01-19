package com.icebreak.p2p.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class CommonDistrictDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private int id;

	private String nbNo;

	private String fatherNo;

	private String areaName;

	private String shortName;

	private String areaPy;

	private String areaNagda;

	private String cnaps;

	private String userId;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNbNo() {
		return nbNo;
	}
	
	public void setNbNo(String nbNo) {
		this.nbNo = nbNo;
	}

	public String getFatherNo() {
		return fatherNo;
	}
	
	public void setFatherNo(String fatherNo) {
		this.fatherNo = fatherNo;
	}

	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAreaPy() {
		return areaPy;
	}
	
	public void setAreaPy(String areaPy) {
		this.areaPy = areaPy;
	}

	public String getAreaNagda() {
		return areaNagda;
	}
	
	public void setAreaNagda(String areaNagda) {
		this.areaNagda = areaNagda;
	}

	public String getCnaps() {
		return cnaps;
	}
	
	public void setCnaps(String cnaps) {
		this.cnaps = cnaps;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
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
