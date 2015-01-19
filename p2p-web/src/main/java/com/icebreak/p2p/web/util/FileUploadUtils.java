package com.icebreak.p2p.web.util;

import javax.servlet.http.HttpServletRequest;

import com.icebreak.p2p.rs.util.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadUtils {
	private static final Logger	logger	= LoggerFactory.getLogger(FileUploadUtils.class);
	
	/**
	 * @param req
	 * @return
	 */
	
	public static String[] getStaticFilesImgPath(HttpServletRequest req, String fileOrgName) {
		
		return UploadFileUtils.getFilePath(req, "uploadfile", "images", fileOrgName);
	}
	
	public static String[] getStaticFilesPdfPath(HttpServletRequest req, String fileOrgName) {
		return UploadFileUtils.getFilePath(req, "uploadfile", "pfd", fileOrgName);
	}
	
	public static String getStaticFilesPdfDir() {
		return UploadFileUtils.getStaticFilesPdfDir();
	}
	
	public static String getStaticFilesImgDir() {
		return UploadFileUtils.getStaticFilesImgDir();
	}
	
}
