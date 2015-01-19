package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private int id = 0;
	/**
	 * 角色代码
	 */
	private String code = null;
	/**
	 * 角色名称
	 */
	private String name = null;
	/**
	 * 父角色ID
	 */
	private int parent = 0;
	/**
	 * 备注
	 */
	private String note = null;

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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "id:" + id + ", code:" + code + ", name:" + name + ",parent:" + parent + ", note:" + note;
	}
   
	
}
