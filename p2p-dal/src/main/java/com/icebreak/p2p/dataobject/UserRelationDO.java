package com.icebreak.p2p.dataobject;

import java.io.Serializable;

public class UserRelationDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;

	// ========== properties ==========

	private long userRelationId;

	private long parentId;

	private long childId;
	
	private String memberNo;
	
	private String relationStatus;

	public UserRelationDO() {
		super();
	}

	public UserRelationDO(long parentId, long childId, String memberNo) {

		this.parentId = parentId;
		this.childId = childId;
		this.memberNo = memberNo;
	}

	// ========== getters and setters ==========

	public long getUserRelationId() {
		return userRelationId;
	}

	public void setUserRelationId(long userRelationId) {
		this.userRelationId = userRelationId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getChildId() {
		return childId;
	}

	public void setChildId(long childId) {
		this.childId = childId;
	}
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	public String getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(String relationStatus) {
		this.relationStatus = relationStatus;
	}

	@Override
	public String toString() {
		return "UserRelationDO [userRelationId=" + userRelationId
				+ ", parentId=" + parentId + ", childId=" + childId
				+ ", memberNo=" + memberNo + ", relationStatus="
				+ relationStatus + "]";
	}
	
}
