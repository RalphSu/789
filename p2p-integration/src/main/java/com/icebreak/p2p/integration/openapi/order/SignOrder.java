package com.icebreak.p2p.integration.openapi.order;

import com.icebreak.util.service.Order;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class SignOrder extends BaseOrder implements Order {

    private static final long serialVersionUID = -780880051975183789L;

    @NotEmpty
    @Size(min = 20, max = 32, message = "会员号为20-32字节")
    private String userId;

	/** 银联商户号 */
    private String unionBusinessNo = "easy_trade-yxyt";
	
	/** 银行卡号 */
	private String cardNo;
	
	/** 银行简称 */
	private String bankNo;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionBusinessNo() {
        return unionBusinessNo;
    }

    public void setUnionBusinessNo(String unionBusinessNo) {
        this.unionBusinessNo = unionBusinessNo;
    }

	public String getBankNo() {
		return bankNo;
	}
	
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

    @Override
	public void check() {
		
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
