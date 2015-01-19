package com.icebreak.p2p.common.services;

import com.icebreak.p2p.dataobject.UserBaseInfoDO;

public interface MessageService {
	//通知服务
	public void notifyUser(UserBaseInfoDO notifiedUser, String content);
	
	public void notifyUserByEmail(UserBaseInfoDO notifiedUser, String content, String attachs[]);
	
	public void notifyUserByType(UserBaseInfoDO notifiedUser, String content, String notify_Type);
	
}
