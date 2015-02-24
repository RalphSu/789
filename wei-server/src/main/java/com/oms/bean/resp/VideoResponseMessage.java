package com.oms.bean.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.oms.bean.Message;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoResponseMessage extends Message {
	@XmlElement(name = "Video", required = true)
	Video Video;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Video {
		@XmlElement(name = "MediaId", required = true)
		String MediaId;
		@XmlElement(name = "Title", required = false)
		String Title;
		@XmlElement(name = "Description", required = false)
		String Description;

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}

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

	}
}
