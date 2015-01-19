package com.icebreak.p2p.dataobject.viewObject;

public class UserStatisticsInfoVO {
	private String countFildOne;
	private String countFildTwo;
	private String countFildThree;
	public String getCountFildOne() {
		return countFildOne;
	}
	public void setCountFildOne(String countFildOne) {
		this.countFildOne = countFildOne;
	}
	public String getCountFildTwo() {
		return countFildTwo;
	}
	public void setCountFildTwo(String countFildTwo) {
		this.countFildTwo = countFildTwo;
	}
	public String getCountFildThree() {
		return countFildThree;
	}
	public void setCountFildThree(String countFildThree) {
		this.countFildThree = countFildThree;
	}
	@Override
	public String toString() {
		return "UserStatisticsInfoVO [countFildOne=" + countFildOne
				+ ", countFildTwo=" + countFildTwo + ", countFildThree="
				+ countFildThree + ", getCountFildOne()=" + getCountFildOne()
				+ ", getCountFildTwo()=" + getCountFildTwo()
				+ ", getCountFildThree()=" + getCountFildThree()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
