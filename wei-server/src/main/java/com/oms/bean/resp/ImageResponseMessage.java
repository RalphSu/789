package com.oms.bean.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.oms.bean.Message;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageResponseMessage extends Message {
	@XmlElement(name = "Image", required = true)
	Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

	@XmlType(name="", propOrder={"MediaId"})
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Image {
		@XmlElement(name = "MediaId", required = true)
		String MediaId;

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}

	}
}
