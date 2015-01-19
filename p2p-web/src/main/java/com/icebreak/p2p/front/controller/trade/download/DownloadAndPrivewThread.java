package com.icebreak.p2p.front.controller.trade.download;

import javax.servlet.http.HttpServletResponse;

import com.icebreak.p2p.ws.enums.PDFTypeEnum;

public class DownloadAndPrivewThread extends Thread {
	
	private byte[] fileBytes;
	private String proName;
	private String downType;
	private PDFTypeEnum pdfTypeEnum;
	private HttpServletResponse response;
	
	public void run() {
		DownloadAndPrivewFile downOrPrivew = new DownloadAndPrivewFile();
		downOrPrivew.downloadOrPreview(response, proName, fileBytes, downType, pdfTypeEnum);
		
	}
	
	public byte[] getFileBytes() {
		return this.fileBytes;
	}
	
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
	public String getProName() {
		return this.proName;
	}
	
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getDownType() {
		return this.downType;
	}
	
	public void setDownType(String downType) {
		this.downType = downType;
	}
	
	public PDFTypeEnum getPdfTypeEnum() {
		return this.pdfTypeEnum;
	}
	
	public void setPdfTypeEnum(PDFTypeEnum pdfTypeEnum) {
		this.pdfTypeEnum = pdfTypeEnum;
	}
	
	public HttpServletResponse getResponse() {
		return this.response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
