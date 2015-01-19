package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.integration.openapi.info.SignBankInfo;
import com.icebreak.p2p.ws.result.P2PBaseResult;

import java.util.ArrayList;
import java.util.List;

public class SignBankResult extends P2PBaseResult {

    /**
     * 签约银行卡
     */
    private List<SignBankInfo> signBankInfos = new ArrayList<SignBankInfo>();

    public List<SignBankInfo> getSignBankInfos() {
        return signBankInfos;
    }

    public void setSignBankInfos(List<SignBankInfo> signBankInfos) {
        this.signBankInfos = signBankInfos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignBankResult{");
        sb.append("signBankInfos=").append(signBankInfos);
        sb.append('}');
        return sb.toString();
    }
}
