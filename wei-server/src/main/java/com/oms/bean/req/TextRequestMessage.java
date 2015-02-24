package com.oms.bean.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.Message;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class TextRequestMessage extends Message {
	@XmlElement(name = "Content", required = true, nillable = true)
	String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}


}
