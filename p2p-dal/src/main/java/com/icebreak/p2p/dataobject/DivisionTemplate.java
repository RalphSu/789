package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DivisionTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分润模版ID
	 */
	private long id = 0;
	/**
	 * 模版名称
	 */
	private String name = null;
	/**
	 * 分润阶段
	 */
	private String phase = null;
	/**
	 * 分润方式
	 */
	private String way = null;
	/**
	 * 状态
	 */
	private String status = "normal";
	/**
	 * 创建日期
	 */
	private Date createDate = new Date();
	/**
	 * 修改日期
	 */
	private Date modifyDate = new Date();
	/**
	 * 描述
	 */
	private String note = null;
	/**
	 * 规则明细
	 */
	private List<DivisionRule> rules = null;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DivisionRule> getRules() {
		return rules;
	}

	public void setRules(List<DivisionRule> rules) {
		this.rules = rules;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name + ", phase:" + phase + ", way:" + way + ", createDate:" + createDate + ", modifyDate:" + modifyDate + ", note:" + note + ", status:" + status;
	}
}
