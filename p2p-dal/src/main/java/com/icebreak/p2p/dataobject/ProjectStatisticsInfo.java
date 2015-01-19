package com.icebreak.p2p.dataobject;

public class ProjectStatisticsInfo {
	private String dimension;
	private Long pendingProjects;
	private Long repayProjects;
	private Long count;
	
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public Long getPendingProjects() {
		return pendingProjects;
	}
	public void setPendingProjects(Long pendingProjects) {
		this.pendingProjects = pendingProjects;
	}
	public Long getRepayProjects() {
		return repayProjects;
	}
	public void setRepayProjects(Long repayProjects) {
		this.repayProjects = repayProjects;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	

}
