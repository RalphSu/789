package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 权限ID
	 */
	private int id = 0;
	/**
	 * 权限代码
	 */
	private String code = null;
	/**
	 * 权限名称
	 */
	private String name = null;
	/**
	 * 操作
	 */
	private String operate = null;
	/**
	 * 回执操作
	 */
	private String backOperate = null;
	/**
	 * 备注
	 */
	private String note = null;
	
	public Permission(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getBackOperate() {
		return backOperate;
	}

	public void setBackOperate(String backOperate) {
		this.backOperate = backOperate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "id:" + id + ", code:" + code + ", name:" + name + ", operate:" + operate + ", backOperate:" + backOperate + ", note:" + note;
	}
}
