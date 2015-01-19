package com.icebreak.p2p.ws.service.query.order;

import com.icebreak.p2p.ws.service.query.YrdQueryPageBase;

public class SysParamQueryOrder extends YrdQueryPageBase {
    private static final long serialVersionUID = -4239124486323136636L;
    /** 参数名称*/
    private String paramName;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
