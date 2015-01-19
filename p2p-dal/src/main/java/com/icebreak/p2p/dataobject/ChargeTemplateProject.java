package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class ChargeTemplateProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 收费模版项目ID
	 */
	private long id = 0;
	/**
	 * 收费项ID
	 */
	private long projectId = 0;
	/**
	 * 模版ID
	 */
	private long templateId = 0;
	
	public ChargeTemplateProject(){}

	public ChargeTemplateProject(long projectId, long templateId) {
		this.projectId = projectId;
		this.templateId = templateId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	@Override
	public String toString() {
		return "id:" + id + ", projectId:" + projectId + ", templateId:" + templateId;
	}
}
