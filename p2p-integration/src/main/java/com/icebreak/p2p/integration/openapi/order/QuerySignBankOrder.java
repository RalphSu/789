package com.icebreak.p2p.integration.openapi.order;

import com.icebreak.p2p.ws.order.ValidateOrderBase;
import com.icebreak.util.service.Order;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class QuerySignBankOrder extends ValidateOrderBase implements Order {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1975280762950326569L;

    /**
     * 用户号
     */
    @NotEmpty
    @Size(min = 20, max = 32, message = "会员号为20-32字节")
    private String userId;

    /**
     * 商户号
     */
    @NotEmpty
    private String upUserNo;

    /**
     * 状态
     */
    private String afterStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpUserNo() {
        return upUserNo;
    }

    public void setUpUserNo(String upUserNo) {
        this.upUserNo = upUserNo;
    }

    public String getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(String afterStatus) {
        this.afterStatus = afterStatus;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
