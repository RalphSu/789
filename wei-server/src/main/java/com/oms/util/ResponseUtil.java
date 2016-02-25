package com.oms.util;

import com.oms.bean.MsgType;
import com.oms.bean.resp.TextResponseMessage;

public class ResponseUtil {
	public static TextResponseMessage createTextResponse(String toUserName, String fromUserName, String content){
		TextResponseMessage text = new TextResponseMessage();
		text.setToUserName(toUserName);
		text.setFromUserName(fromUserName);
		text.setCreateTime(DateUtil.now2Int());
		text.setMsgType(MsgType.TEXT);
		text.setContent(content);
		return text;
	}

}
