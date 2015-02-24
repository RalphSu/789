package com.oms.bean.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.Message;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class ImageRequestMessage extends Message {
	@XmlElement(name = "PicUrl", required = true)
	String PicUrl;
	@XmlElement(name = "MediaId", required = true)
	String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}


}
