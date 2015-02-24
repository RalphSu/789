package com.oms.bean.event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.oms.bean.EventMessage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class LocationEvenMessage extends EventMessage{
	@XmlElement(name="Latitude", required=true)
	String Latitude;
	@XmlElement(name="Longitude", required=true)
	String Longitude;
	@XmlElement(name="Precision", required=true)
	String Precision;
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	
}
