package com.icebreak.p2p.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.web.context.WebApplicationContext;

import com.icebreak.util.lang.util.DateUtil;
import com.icebreak.util.lang.util.StringUtil;
public class FilenameUtil {
	public static String getClassfold() {
		return FilenameUtil.class.getResource("/").getPath();
	}
	
	public static File getClassfoldFile() {
		return new File(getClassfold());
	}
	
	public static String getWebfold() {
		return getWebfoldFile().getPath();
	}
	
	public static File getWebfoldFile() {
		return getClassfoldFile().getParentFile().getParentFile();
	}
	
	public static String getFolderFileName(String fileName){
		String fileDirs ="personal"+File.separator
				+DateUtil.dtSimpleYmFormat(new Date())+File.separator+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if(StringUtil.isNotBlank(fileName))
			fileDirs +=File.separator+fileName;
		return fileDirs;
	}
	
	public static String getWebAppPath(WebApplicationContext wac){
		
		return wac.getServletContext().getRealPath("");
	}
	
	public static String getRealNameCertFileName(String userName,String fileName) throws UnsupportedEncodingException{			
		 return userName+"_"+fileName;		
	}
}
