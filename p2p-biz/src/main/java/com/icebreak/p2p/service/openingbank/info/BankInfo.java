package com.icebreak.p2p.service.openingbank.info;

import java.util.List;

public class BankInfo {
	
	/**
	 *  当前节点是否存在，如果不存在为fasle,对应的联行号、开户行名称为空
	 */
	private boolean				existNode		= false;
	/**
	 *  银行英文简称
	 */
	private String				bankId;
	
	/**
	 * 联行号
	 */
	private String				bankLasalle;
	/**
	 * 开户行名称
	 */
	private String				branchName;
	
	/**
	 * 当前地区的地区编号
	 */
	private String				branchDistrictNo;
	/**
	 * 当前地区的地区名称
	 */
	private String				branchDistrictName;
	/**
	 * 地区名称
	 */
	private String				areaName;
	
	/**
	 * 上级地区的编号
	 */
	private String				fatherNo;
	/**
	 * 上级地区的地区名称,,只有在getOpeningBankByDistrictNameAndBankId查询中才会有
	 */
	private String				fatherName;
	/**
	 * 当前银行机构下属的开户行列表，如果为叶子节点，则为null
	 */
	private List<BankInfo>		childs			= null;
	private List<ProvinceInfo>	provinceInfos	= null;
	
	public String getBranchDistrictName() {
		return branchDistrictName;
	}
	
	public void setBranchDistrictName(String branchDistrictName) {
		this.branchDistrictName = branchDistrictName;
	}
	
	public String getFatherName() {
		return fatherName;
	}
	
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
	public boolean isExistNode() {
		return existNode;
	}
	
	public void setExistNode(boolean existNode) {
		this.existNode = existNode;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public List<BankInfo> getChilds() {
		return childs;
	}
	
	public void setChilds(List<BankInfo> childs) {
		this.childs = childs;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public String getBankLasalle() {
		return bankLasalle;
	}
	
	public void setBankLasalle(String bankLasalle) {
		this.bankLasalle = bankLasalle;
	}
	
	public String getBranchDistrictNo() {
		return branchDistrictNo;
	}
	
	public void setBranchDistrictNo(String branchDistrictNo) {
		this.branchDistrictNo = branchDistrictNo;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getFatherNo() {
		return fatherNo;
	}
	
	public void setFatherNo(String fatherNo) {
		this.fatherNo = fatherNo;
	}
	
	public List<ProvinceInfo> getProvinceInfos() {
		return provinceInfos;
	}
	
	public void setProvinceInfos(List<ProvinceInfo> provinceInfos) {
		this.provinceInfos = provinceInfos;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BankInfo [existNode=");
		builder.append(existNode);
		builder.append(", bankId=");
		builder.append(bankId);
		builder.append(", bankLasalle=");
		builder.append(bankLasalle);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", branchDistrictNo=");
		builder.append(branchDistrictNo);
		builder.append(", branchDistrictName=");
		builder.append(branchDistrictName);
		builder.append(", areaName=");
		builder.append(areaName);
		builder.append(", fatherNo=");
		builder.append(fatherNo);
		builder.append(", fatherName=");
		builder.append(fatherName);
		builder.append(", childs=");
		builder.append(childs);
		builder.append(", provinceInfos=");
		builder.append(provinceInfos);
		builder.append("]");
		return builder.toString();
	}
	
}
