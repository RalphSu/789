package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.icebreak.p2p.util.DateUtil;

public class ChargeTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收费模版ID，主键
	 */
	private long id = 0;
	/**
	 * 模版名称
	 */
	private String name = null;
	/**
	 * 角色ID，收费对象
	 */
	private int roleId = 0;
	/**
	 * 模版类型，私有的或者公共的，默认公共的
	 */
	private String type = "public";
	/**
	 * 规则模式
	 */
	private String ruleModel = "amount";
	/**
	 * 收费方式，默认每笔交易收费
	 */
	private String method = "trade";
	/**
	 * 执行时间
	 */
	private Date chargeDate = null;
	/**
	 * 创建日期
	 */
	private Date createDate = new Date();
	/**
	 * 修改日期
	 */
	private Date modifyDate = new Date();
    /**
     * 规则	
     */
	private List<ChargeRule> chargeRules = null;
	/**
	 * 收费项目
	 */
	private List<ChargeProject> projects = null;
	
	public void setChargeDateString(String s){
		chargeDate = DateUtil.parse(s);
	}
	
	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getRuleModel() {
		return ruleModel;
	}

	public void setRuleModel(String ruleModel) {
		this.ruleModel = ruleModel;
	}

	public List<ChargeProject> getProjects() {
		return projects;
	}

	public void setProjects(List<ChargeProject> projects) {
		this.projects = projects;
	}

	public List<ChargeRule> getChargeRules() {
		return chargeRules;
	}

	public void setChargeRules(List<ChargeRule> chargeRules) {
		this.chargeRules = chargeRules;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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
	
	public String getChargeDateString(){
		return DateUtil.simpleFormatYmdhms(chargeDate);
	}

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name + ", roleId:" + roleId + ", type:" + type + ", method:" + method + ", createDate:" + createDate + ", modifyDate:" + modifyDate;
	}
}
