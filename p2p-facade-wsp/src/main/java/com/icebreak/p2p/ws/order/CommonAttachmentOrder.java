package com.icebreak.p2p.ws.order;

import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;
import com.icebreak.util.service.Order;

public class CommonAttachmentOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private long attachmentId;
	
	private String bizNo;
	
	private CommonAttachmentTypeEnum moduleType = CommonAttachmentTypeEnum.OTHER;
	
	private String fileName;
	
	private long isort;
	
	private String filePhysicalPath;
	
	private String requestPath;
	
	public long getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public CommonAttachmentTypeEnum getModuleType() {
		return moduleType;
	}
	
	public void setModuleType(CommonAttachmentTypeEnum moduleType) {
		this.moduleType = moduleType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getIsort() {
		return isort;
	}
	
	public void setIsort(long isort) {
		this.isort = isort;
	}
	
	public String getFilePhysicalPath() {
		return filePhysicalPath;
	}
	
	public void setFilePhysicalPath(String filePhysicalPath) {
		this.filePhysicalPath = filePhysicalPath;
	}
	
	public String getRequestPath() {
		return requestPath;
	}
	
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonAttachmentOrder [attachmentId=");
		builder.append(attachmentId);
		builder.append(", bizNo=");
		builder.append(bizNo);
		builder.append(", moduleType=");
		builder.append(moduleType);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", isort=");
		builder.append(isort);
		builder.append(", filePhysicalPath=");
		builder.append(filePhysicalPath);
		builder.append(", requestPath=");
		builder.append(requestPath);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 * @see com.icebreak.util.service.Order#check()
	 */
	@Override
	public void check() {
		validateHasText(bizNo, "交易流水号");
		validateNotNull(moduleType, "模块类型");
		validateHasText(fileName, "文件名称");
		validateHasZore(isort, "序号");
		validateHasText(filePhysicalPath, "文件路径");
		validateHasText(requestPath, "访问路径");
	}
}
