package com.icebreak.p2p.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum PDFTypeEnum {
	
	CONTRACT_MAIN("contract", "guarantee_contract.xsl", "担保合同", "投资权益回购履约担保合同.pdf"),
	
	LETTER_ZJHT("letter", "guarantee_letter.xsl", "承诺函", "承诺函.pdf");
	
	private String code;
	private final String xslfileName;
	private final String note;
	private final String pdfFileName;
	
	private PDFTypeEnum(String code, String xslfileName, String note, String pdfFileName) {
		this.code = code;
		this.xslfileName = xslfileName;
		this.note = note;
		this.pdfFileName = pdfFileName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getXslfileName() {
		return xslfileName;
	}
	
	public String getPdfFileName() {
		return pdfFileName;
	}
	
	public String getNote() {
		return note;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return LogResultEnum
	 */
	public static PDFTypeEnum getByCode(String code) {
		for (PDFTypeEnum _enum : values()) {
			if (_enum.getCode().equalsIgnoreCase(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<LogResultEnum>
	 */
	public List<PDFTypeEnum> getAllEnum() {
		List<PDFTypeEnum> list = new ArrayList<PDFTypeEnum>();
		for (PDFTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (PDFTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
}
