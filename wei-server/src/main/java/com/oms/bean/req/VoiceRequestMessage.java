package com.oms.bean.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.Message;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceRequestMessage extends Message{
	@XmlElement(name="MediaId", required=true)
	String MediaId;
	@XmlElement(name="Format", required=true)
	String Format;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
}
