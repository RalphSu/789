package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class UserChargeTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户收费模版ID
	 */
	private long id = 0;
	/**
	 * 用户ID
	 */
	private long userId = 0;
	/**
	 * 模版ID
	 */
	private long templateId = 0;
	
	public UserChargeTemplate(){}
	
	public UserChargeTemplate(long userId, long templateId) {
		this.userId = userId;
		this.templateId = templateId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	@Override
	public String toString() {
		return "id:" + id + ", userId:" + userId + ", templateId:" + templateId;
	}
}
