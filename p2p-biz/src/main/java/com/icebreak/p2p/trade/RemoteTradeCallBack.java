package com.icebreak.p2p.trade;

import com.alibaba.fastjson.JSONObject;

public interface RemoteTradeCallBack {
	
	public void call(JSONObject json) throws Throwable;

}
