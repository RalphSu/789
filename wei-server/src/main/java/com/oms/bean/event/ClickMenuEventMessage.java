package com.oms.bean.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.EventMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class ClickMenuEventMessage extends EventMessage{
	@XmlElement(name="EventKey", required=true)
	String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
}
