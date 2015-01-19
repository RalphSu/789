package com.icebreak.util.lang.util.image;

import java.net.URI;

public class Image {
	
	/** 图片名称 */
	private String name;
	
	/** 图片地址 */
	private URI uri;
	
	/** 图片描述 */
	private String description;
	
	/** 图片高 */
	private int height;
	
	/** 图片宽度 */
	private int width;
	
	/**
	 * 构建一个<code>Image.java</code>
	 */
	public Image() {
		super();
	}
	
	/**
	 * 构建一个<code>Image.java</code>
	 * @param name
	 * @param uri
	 * @param description
	 * @param height
	 * @param width
	 */
	public Image(String name, URI uri, String description, int height, int width) {
		super();
		this.name = name;
		this.uri = uri;
		this.description = description;
		this.height = height;
		this.width = width;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public URI getUri() {
		return uri;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Image [name=");
		builder.append(name);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", description=");
		builder.append(description);
		builder.append(", height=");
		builder.append(height);
		builder.append(", width=");
		builder.append(width);
		builder.append("]");
		return builder.toString();
	}
	
}
