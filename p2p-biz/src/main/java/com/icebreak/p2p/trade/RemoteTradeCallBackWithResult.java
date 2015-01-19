package com.icebreak.p2p.trade;

import com.alibaba.fastjson.JSONObject;

public interface RemoteTradeCallBackWithResult<T> {
	
	public T call(JSONObject json) throws Throwable;

}
