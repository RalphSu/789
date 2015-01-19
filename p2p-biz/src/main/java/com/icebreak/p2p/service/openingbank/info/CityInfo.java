package com.icebreak.p2p.service.openingbank.info;

public class CityInfo {
	
	private String	cityName;
	private String	branchDistrictNo;
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getBranchDistrictNo() {
		return branchDistrictNo;
	}
	
	public void setBranchDistrictNo(String branchDistrictNo) {
		this.branchDistrictNo = branchDistrictNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CityInfo [cityName=");
		builder.append(cityName);
		builder.append(", branchDistrictNo=");
		builder.append(branchDistrictNo);
		builder.append("]");
		return builder.toString();
	}
	
}
