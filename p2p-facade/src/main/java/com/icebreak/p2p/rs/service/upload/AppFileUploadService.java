package com.icebreak.p2p.rs.service.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.rs.base.ServiceBase;
import com.icebreak.p2p.rs.base.enums.APIServiceEnum;
import com.icebreak.p2p.rs.service.base.AppService;
import com.icebreak.p2p.rs.util.UploadFileUtils;
import com.icebreak.p2p.util.security.AlgorithmUtils;

public class AppFileUploadService extends ServiceBase implements AppService {
	
	String	GETIMGPATH	= "/upload/getUploadImages?imagePath=";
	
	@Override
	public JSONObject execute(Map<String, Object> params, HttpServletRequest request) {
		

		JSONObject json = new JSONObject();
		String fileStr = null;
		String fileExt = null;
		try {
			fileStr = params.get("file").toString();
			fileExt = params.get("fileExt").toString();
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "参数异常,文件上传异常");
			logger.error("参数异常,文件上传异常", e);
		}
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			String[] path = UploadFileUtils.getStaticFilesImgPath(request, fileExt);
			String fileName = System.currentTimeMillis() + fileExt;
			byte[] bfile = AlgorithmUtils.hex2byte(fileStr);
			
			String savePath = path[0];
			file = new File(savePath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
			json.put("code", 1);
			json.put("message", "文件上传成功");
			json.put("filePath", GETIMGPATH + savePath);
			logger.info("文件上传成功");
		} catch (Exception e) {
			json.put("code", 0);
			json.put("message", "文件上传失败");
			logger.error("文件上传失败");
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return json;
	}
	
	@Override
	public APIServiceEnum getServiceType() {

		return APIServiceEnum.appFileUploadService;
	}
	
}
