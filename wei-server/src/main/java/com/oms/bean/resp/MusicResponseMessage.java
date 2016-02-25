package com.oms.bean.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.oms.bean.Message;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicResponseMessage extends Message {
	@XmlElement(name = "Music", required = true)
	Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public class Music {
		@XmlElement(name = "Title", required = false)
		String Title;
		@XmlElement(name = "Description", required = false)
		String Description;
		@XmlElement(name = "MusicUrl", required = false)
		String MusicUrl;
		@XmlElement(name = "HQMusicUrl", required = false)
		String HQMusicUrl;
		@XmlElement(name = "ThumbMediaId", required = true)
		String ThumbMediaId;

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

		public String getMusicUrl() {
			return MusicUrl;
		}

		public void setMusicUrl(String musicUrl) {
			MusicUrl = musicUrl;
		}

		public String getHQMusicUrl() {
			return HQMusicUrl;
		}

		public void setHQMusicUrl(String hQMusicUrl) {
			HQMusicUrl = hQMusicUrl;
		}

		public String getThumbMediaId() {
			return ThumbMediaId;
		}

		public void setThumbMediaId(String thumbMediaId) {
			ThumbMediaId = thumbMediaId;
		}

	}
}
