package com.icebreak.p2p.front.controller.trade.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icebreak.p2p.ws.enums.PDFTypeEnum;

public class DownloadAndPrivewFile {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	//	private final String LETTER_PDF = "担保函.pdf";
	//	private final String CONTRACT_PDF = "投资权益回购履约担保合同.pdf";
	
	public void downloadOrPreview(HttpServletResponse response, String proName, String filePath,
									String downType, String fileType) {
		if (filePath == null || filePath == "") {
			return;
		}
		FileInputStream fis = null;
		BufferedInputStream buff = null;
		OutputStream servletOS = null;
		String extName = "";
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			buff = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			logger.error("读取模板文件失败", e);
		}
		if ("contract".equals(fileType)) {
			extName = "[" + proName + "]" + "担保函.pdf";
		} else if ("letter".equals(fileType)) {
			extName = "[" + proName + "]" + "投资权益回购履约担保合同.pdf";
		}
		if ("downLoad".equals(downType)) {
			response.setContentType("application/x-msdownload");
			try {
				response.addHeader("Content-Disposition", "attachment; filename="
															+ new String(
																extName.getBytes("GB2312"),
																"ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {
				logger.error("resonse设置失败", e1);
			}
		}
		byte[] bytes = new byte[1024];
		long k = 0;
		try {
			servletOS = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(bytes, 0, 1024);
				k += j;
				servletOS.write(bytes, 0, j);
			}
			fis.close();
			buff.close();
			servletOS.flush();
			servletOS.close();
		} catch (IOException e) {
			logger.error("下载失败", e);
		}
	}
	
	public void downloadOrPreview(HttpServletResponse response, String proName, byte[] fileBytes,
									String downType, PDFTypeEnum pdfTypeEnum) {
		
		OutputStream servletOS = null;
		String extName = "";
		
		extName = "[" + proName + "]" + pdfTypeEnum.getPdfFileName();
		
		if ("downLoad".equals(downType)) {
			response.setContentType("application/x-msdownload");
			try {
				response.addHeader("Content-Disposition", "attachment; filename="
															+ new String(
																extName.getBytes("GB2312"),
																"ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {
				logger.error("resonse设置失败", e1);
			}
		}
		try {
			servletOS = response.getOutputStream();
			servletOS.write(fileBytes);
			servletOS.flush();
			servletOS.close();
		} catch (IOException e) {
			logger.error("下载失败", e);
		}
	}
	
}
