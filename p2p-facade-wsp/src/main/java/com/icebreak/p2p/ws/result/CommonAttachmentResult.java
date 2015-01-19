package com.icebreak.p2p.ws.result;

import com.icebreak.p2p.ws.info.CommonAttachmentInfo;

public class CommonAttachmentResult extends P2PBaseResult {
	
	private static final long serialVersionUID = -8894712705496167696L;
	CommonAttachmentInfo attachmentInfo;
	
	public CommonAttachmentInfo getAttachmentInfo() {
		return this.attachmentInfo;
	}
	
	public void setAttachmentInfo(CommonAttachmentInfo attachmentInfo) {
		this.attachmentInfo = attachmentInfo;
	}
	
}
