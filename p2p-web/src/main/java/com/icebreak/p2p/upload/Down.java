package com.icebreak.p2p.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icebreak.p2p.front.controller.trade.download.DownloadAndPrivewFileTread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Down {
	protected final Logger	logger	= LoggerFactory.getLogger(this.getClass());
	
	public void downOrPreview(HttpServletResponse response, HttpSession session, String filePath,
								String type, String fileType, String proName) {
		if (filePath == null || filePath == "") {
			return;
		}
		DownloadAndPrivewFileTread downThread = new DownloadAndPrivewFileTread();
		downThread.setResponse(response);
		FileInputStream fis = null;
		BufferedInputStream buff = null;
		OutputStream servletOS = null;
		String extName = "";
		if ("inverst".equals(fileType)) {
			ServletContext application = session.getServletContext();
			String serverRealPath = application.getRealPath("/");
			filePath = serverRealPath + "WEB-INF/doc/投资权益回购履约担保合同.pdf";
			extName = "投资权益回购履约担保合同.pdf";
		}
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			buff = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			logger.error("downOrPreview{}", e.getMessage(), e);
		}
		if ("contract".equals(fileType)) {
			extName = "[" + proName + "]投资权益回购履约担保合同.pdf";
		} else if ("letter".equals(fileType)) {
			extName = "[" + proName + "]担保函.pdf";
		}
		
		if ("downLoad".equals(type)) {
			response.setContentType("application/octet-stream");
			try {
				response.addHeader("Content-Disposition", "attachment; filename="
															+ new String(
																extName.getBytes("GB2312"),
																"ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {
				logger.error("UnsupportedEncodingException{}", e1.getMessage(), e1);
			}
		}
		
		byte[] b = new byte[1024];
		long k = 0;
		try {
			servletOS = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				servletOS.write(b, 0, j);
			}
			servletOS.flush();
			servletOS.close();
		} catch (IOException e) {
			logger.error("IOException{}", e.getMessage(), e);
		}
	}
	
}
