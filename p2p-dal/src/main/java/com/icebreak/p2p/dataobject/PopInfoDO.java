package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class PopInfoDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long popId;
	private String title;
	private short type;
	private short status;
	private long parentId;
	private String parentName;
	private String content;
	private Date addTime;
	private Date modifyTime;
	private String remark;
	private String rem1;
	
	private long hits;
	
	
	public long getHits() {
		return hits;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
	public long getPopId() {
		return popId;
	}
	public void setPopId(long popId) {
		this.popId = popId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRem1() {
		return rem1;
	}
	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}
	@Override
	public String toString() {
		return "PopInfo [popId=" + popId + ", title=" + title + ", type="
				+ type + ", status=" + status + ", parentId=" + parentId
				+ ", parentName=" + parentName + ", content=" + content
				+ ", addTime=" + addTime + ", modifyTime=" + modifyTime
				+ ", remark=" + remark + ", rem1=" + rem1 + ", hits=" + hits + "]";
	}
	
}
