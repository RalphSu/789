package com.icebreak.p2p.integration.openapi.result;

import com.icebreak.p2p.ws.result.P2PBaseResult;

public class SignResult extends P2PBaseResult {

    private static final long serialVersionUID = -2720824044634911298L;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignResult{");
        sb.append("url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
