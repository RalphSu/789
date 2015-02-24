package com.oms.bean.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.EventMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class ScanEventMessage extends EventMessage{
	@XmlElement(name="EventKey", required=true)
	String EventKey;
	@XmlElement(name="Ticket", required=true)
	String Ticket;
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
