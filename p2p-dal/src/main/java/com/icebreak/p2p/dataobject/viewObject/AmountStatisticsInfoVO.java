package com.icebreak.p2p.dataobject.viewObject;

public class AmountStatisticsInfoVO {
	private String countFildOne;
	private long countFildTwo;
	private long countFildThree;
	private long countFildFour;
	public String getCountFildOne() {
		return countFildOne;
	}
	public void setCountFildOne(String countFildOne) {
		this.countFildOne = countFildOne;
	}
	public long getCountFildTwo() {
		return countFildTwo;
	}
	public void setCountFildTwo(long countFildTwo) {
		this.countFildTwo = countFildTwo;
	}
	public long getCountFildThree() {
		return countFildThree;
	}
	public void setCountFildThree(long countFildThree) {
		this.countFildThree = countFildThree;
	}
	public long getCountFildFour() {
		return countFildFour;
	}
	public void setCountFildFour(long countFildFour) {
		this.countFildFour = countFildFour;
	}
	@Override
	public String toString() {
		return "AmountStatisticsInfoVO [countFildOne=" + countFildOne
				+ ", countFildTwo=" + countFildTwo + ", countFildThree="
				+ countFildThree + ", countFildFour=" + countFildFour + "]";
	}
	
}
