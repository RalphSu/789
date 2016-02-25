package com.oms.bean.resp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.oms.bean.Message;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsResponseMessage extends Message {
	@XmlElement(name = "ArticleCount", type = Integer.class, required = true)
	Integer ArticleCount;
	@XmlElement(name = "Articles", type = Articles.class, required = false)
	Articles Articles;

	public Integer getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public Articles getArticles() {
		return Articles;
	}

	public void setArticles(Articles articles) {
		Articles = articles;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Articles {
		@XmlElement(name = "item")
		List<Item> items;

		public List<Item> getItems() {
			if(items == null){
				items = new ArrayList<Item>();
			}
			return items;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "")
		public static class Item {
			@XmlElement(name = "Title", required = false)
			String Title;
			@XmlElement(name = "Description", required = false)
			String Description;
			@XmlElement(name = "PicUrl", required = false)
			String PicUrl;
			@XmlElement(name = "Url", required = false)
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
			public String getPicUrl() {
				return PicUrl;
			}
			public void setPicUrl(String picUrl) {
				PicUrl = picUrl;
			}
			public String getUrl() {
				return Url;
			}
			public void setUrl(String url) {
				Url = url;
			}
			
		}
	}
}
