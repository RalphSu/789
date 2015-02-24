package com.oms.bean.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.Message;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationRequestMessage extends Message {
	@XmlElement(name="Location_X", required=true)
	String Location_X;
	@XmlElement(name="Location_Y", required=true)
	String Location_Y;
	@XmlElement(name="Scale", required=true)
	String Scale;
	@XmlElement(name="Label", required=true)
	String Label;
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	
}
