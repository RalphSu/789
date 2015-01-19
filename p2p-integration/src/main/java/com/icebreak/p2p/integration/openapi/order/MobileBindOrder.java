package com.icebreak.p2p.integration.openapi.order;

import com.icebreak.p2p.ws.enums.BooleanEnum;
import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;

public class MobileBindOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= 4126384442017471133L;
	/** 用户ID*/
	protected String			userId;
	/**手机号 */
	private String				mobile;
	/**是否新绑定 */
	private BooleanEnum			isNew				= BooleanEnum.YES;
	
	@Override
	public void check() {
		this.validateHasText(userId, "用户id");
		this.validateHasText(mobile, "手机号码");
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public BooleanEnum getIsNew() {
		return isNew;
	}
	
	public void setIsNew(BooleanEnum isNew) {
		this.isNew = isNew;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MobileBindOrder [userId=");
		builder.append(userId);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", isNew=");
		builder.append(isNew);
		builder.append("]");
		return builder.toString();
	}
	
}
