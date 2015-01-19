package com.icebreak.p2p.front.controller.trade.download;

import javax.servlet.http.HttpServletResponse;

public class DownloadAndPrivewFileTread extends Thread {
	private String filePath;
	private String proName;
	private String downType;
	private String fileType;
	private HttpServletResponse response;
	public void run(){
		DownloadAndPrivewFile downOrPrivew = new DownloadAndPrivewFile();
		downOrPrivew.downloadOrPreview(response, proName, filePath, downType, fileType);
		
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getDownType() {
		return downType;
	}
	public void setDownType(String downType) {
		this.downType = downType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	

}
