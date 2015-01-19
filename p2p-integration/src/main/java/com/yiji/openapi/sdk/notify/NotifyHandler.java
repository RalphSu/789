package com.yiji.openapi.sdk.notify;

import com.yiji.openapi.sdk.message.BaseNotify;

public interface NotifyHandler {

	void handleNotify(BaseNotify notify);

}
