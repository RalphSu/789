package com.oms.bean.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.Message;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkRequestMessage extends Message {
	@XmlElement(name="Title", required=true)
	String Title;
	@XmlElement(name="Description", required=true)
	String Description;
	@XmlElement(name="Url", required=true)
	String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
