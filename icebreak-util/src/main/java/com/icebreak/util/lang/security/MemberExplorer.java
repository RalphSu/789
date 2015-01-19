package com.icebreak.util.lang.security;

import java.io.Serializable;

public class MemberExplorer implements Serializable {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 3544811222769090466L;
	
	/** 浏览器类型 */
	private ExplorerType explorerType;
	
	/** 浏览器版本 */
	private String explorerVersion;
	
	/** 主要指User-Agent获取相关信息 */
	private String explorerInfo;
	
	/**
	 * @return Returns the explorerType
	 */
	public ExplorerType getExplorerType() {
		return explorerType;
	}
	
	/**
	 * @param explorerType The explorerType to set.
	 */
	public void setExplorerType(ExplorerType explorerType) {
		this.explorerType = explorerType;
	}
	
	/**
	 * @return Returns the explorerVersion
	 */
	public String getExplorerVersion() {
		return explorerVersion;
	}
	
	/**
	 * @param explorerVersion The explorerVersion to set.
	 */
	public void setExplorerVersion(String explorerVersion) {
		this.explorerVersion = explorerVersion;
	}
	
	/**
	 * @return Returns the explorerInfo
	 */
	public String getExplorerInfo() {
		return explorerInfo;
	}
	
	/**
	 * @param explorerInfo The explorerInfo to set.
	 */
	public void setExplorerInfo(String explorerInfo) {
		this.explorerInfo = explorerInfo;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
			"MemberExplorer [explorerType=%s, explorerVersion=%s, explorerInfo=%s]", explorerType,
			explorerVersion, explorerInfo);
	}
	
}
