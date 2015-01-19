package com.icebreak.p2p.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadPictures {
	
	private static String prefix  = "F:/";
     
	public List<String> addImage(MultipartFile[] files) throws IOException {
		List<String> paths = new ArrayList<String>();
		if(files != null && files.length > 0){
			for (MultipartFile multipartFile : files) {
				String fileName = prefix + System.currentTimeMillis() + multipartFile.getOriginalFilename();
				writeFile(multipartFile.getBytes(), fileName);
				paths.add(fileName);
			}
		}
		return paths;
	}

	private void writeFile(byte[] data, String fileName) throws IOException{
		File file = new File(fileName);
		createFile(file);
		OutputStream out = new FileOutputStream(file);
		out.write(data);
		out.close();
	}
	
	private void createFile(File file) throws IOException{
		File dir = file.getParentFile();
		dir.mkdirs();
		file.createNewFile();
	}
	
}
