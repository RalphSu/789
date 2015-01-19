package com.icebreak.p2p.ws.order;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;

public class CommonAttachmentQueryOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private String bizNo;
	
	private List<CommonAttachmentTypeEnum> moduleTypeList =null;
	
	private String fileName;
	
	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public List<CommonAttachmentTypeEnum> getModuleTypeList() {
		return moduleTypeList;
	}

	public void setModuleTypeList(List<CommonAttachmentTypeEnum> moduleTypeList) {
		this.moduleTypeList = moduleTypeList;
	}

	public void addModuleTypeEnum(CommonAttachmentTypeEnum attachTypeEnum) {
		if(this.moduleTypeList == null) {
			this.moduleTypeList = new ArrayList<CommonAttachmentTypeEnum>();
		}
		this.moduleTypeList.add(attachTypeEnum);
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonAttachmentQueryOrder [bizNo=");
		builder.append(bizNo);
		builder.append(", moduleTypeList=");
		builder.append(moduleTypeList);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public void check() {
	}
	
}
