package com.oms.bean;

import java.util.ArrayList;
import java.util.List;

public class MenuBean {
	private List<Button> button;

	public List<Button> getButton() {
		if(button == null){
			button = new ArrayList<Button>();
		}
		return button;
	}

	public Button createClickButton(String name, String key){
		Button btn = new Button();
		btn.setType(EventType.CLICK);
		btn.setName(name);
		btn.setKey(key);
		return btn;
	}
	
	public Button createViewButton(String name, String url){
		Button btn = new Button();
		btn.setType(EventType.VIEW);
		btn.setName(name);
		btn.setUrl(url);
		return btn;
	}
	
	public class Button{
		private String type;
		private String name;
		private String key;
		private String url;
		private List<Button> sub_button;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public List<Button> getSub_button() {
			if(sub_button == null){
				sub_button = new ArrayList<Button>();
			}
			return sub_button;
		}
	}
}
