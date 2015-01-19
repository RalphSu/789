package com.icebreak.p2p.ws.service.query.order;

import com.icebreak.p2p.ws.service.query.YrdQueryPageBase;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

public class OperationJournalQueryOrder extends YrdQueryPageBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
	/** 主模块名，父模块名 */
	private String baseModuleName;
	
	/** 子模块名 */
	private String permissionName;
	
	/** 操作内容(如'注册','修改','审核','下载'等) */
	private String operationContent;
	
	/** 操作员ID */
	private long operatorId;
	
	/** 操作员真实姓名 */
	private String operatorName;
	
	/** 客户端IP */
	private String operatorIp;
	
	/** 操作信息(详细操作内容) */
	private String memo;
	
	/** 执行操作时间开始 */
	private Date operatorTimeStart;
	
	/** 执行操作时间结束 */
	private Date operatorTimeEnd;
	

	
	public String getOperationContent() {
		return operationContent;
	}
	
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	

	
	public String getBaseModuleName() {
		return baseModuleName;
	}
	
	public void setBaseModuleName(String baseModuleName) {
		this.baseModuleName = baseModuleName;
	}
	
	public String getPermissionName() {
		return permissionName;
	}
	
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public long getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getOperatorIp() {
		return operatorIp;
	}
	
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Date getOperatorTimeStart() {
		return operatorTimeStart;
	}
	
	public void setOperatorTimeStart(Date operatorTimeStart) {
		this.operatorTimeStart = operatorTimeStart;
	}
	
	public Date getOperatorTimeEnd() {
		return operatorTimeEnd;
	}
	
	public void setOperatorTimeEnd(Date operatorTimeEnd) {
		this.operatorTimeEnd = operatorTimeEnd;
	}
	
	@Override
	public void check() {
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
