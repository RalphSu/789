package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class ChargeProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收费项目ID
	 */
	private long id = 0;
	/**
	 * 收费方式
	 */
	private String method = null;
	/**
	 * 项目代码
	 */
	private String code = null;
	/**
	 * 收费项目
	 */
	private String project = null;
	/**
	 * 收费阶段
	 */
	private Integer status = null;
	
	public ChargeProject(){}

	public ChargeProject(String method, String project, Integer status) {
		this.method = method;
		this.project = project;
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "id:" + id + ", method:" + method + ", project:" + project + ", code:" + code;
	}
}
