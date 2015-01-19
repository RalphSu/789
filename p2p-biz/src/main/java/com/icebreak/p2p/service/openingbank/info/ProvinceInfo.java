package com.icebreak.p2p.service.openingbank.info;

import java.util.List;

public class ProvinceInfo {
	private String			provinceName;
	
	private List<CityInfo>	cityList;
	
	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public List<CityInfo> getCityList() {
		return cityList;
	}
	
	public void setCityList(List<CityInfo> cityList) {
		this.cityList = cityList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProvinceInfo [provinceName=");
		builder.append(provinceName);
		builder.append(", cityList=");
		builder.append(cityList);
		builder.append("]");
		return builder.toString();
	}
	
}
