package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class DivisionRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private long id = 0;
	/**
	 * 角色ID
	 */
	private int roleId = 0;
	/**
	 * 分润模版ID
	 */
	private long templateId = 0;
	/**
	 * 规则
	 */
	private double rule = 0.0;
	
	public DivisionRule(){}
	
	public DivisionRule(int roleId, long templateId, double rule) {
		this.roleId = roleId;
		this.templateId = templateId;
		this.rule = rule;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public double getRule() {
		return rule;
	}

	public void setRule(double rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "id:" + id + ", roleId:" + roleId + ", templateId:" + templateId + ", rule:" + rule;
	}

}
