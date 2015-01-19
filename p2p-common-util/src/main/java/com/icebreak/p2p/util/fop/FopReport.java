package com.icebreak.p2p.util.fop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FopReport {
	
//	private static ConsoleLogger	log		= new ConsoleLogger(ConsoleLogger.LEVEL_DEBUG);
	
	private static Logger			logger	= LoggerFactory.getLogger(FopReport.class);
	
	//	private final static String sConfigFile = "";
	
	//	private static String REPORT_ROOT = "D:\\date_fop";
	
	public static ReportData createReport(String sXslTemplateFile, byte[] bXmlData,
											String configFile) throws Exception {
		
		ReportData reportData = new ReportData();
		
		reportData.setContentType("application/pdf");
		//设置日志
//		try {
//
//			MessageHandler.setScreenLogger(log);
//			logger.info("sXslTemplateFile:" + sXslTemplateFile);
//			logger.info("configFile:" + configFile);
//
//			logTime("step 1");
//			//将xml文件内容写到临时文件
//			File xmlFile = File.createTempFile("FOP", ".tmp");
//			FileOutputStream fos = new FileOutputStream(xmlFile);
//			fos.write(bXmlData);
//			fos.close();
//			logTime("step 2");
//			//获取文件路径
//			String xslFileName = sXslTemplateFile;
//			File xslFile = new File(xslFileName);
//			logTime("step 3");
//			//将xml,xsl合并成fo格式文件
//			//	        InputSource foInputSource=generateFO(xmlFile,xslFile);
//			//fop输出流
//			logTime("step 4");
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			logTime("step 5");
//			//设成pdf文件
//			Driver driver = new Driver();
//			logTime("step 6");
//			driver.setLogger(log);
//			logTime("step 7");
//			driver.setRenderer(Driver.RENDER_PDF);
//
//			logTime("step 8");
//			InputHandler inputHandler = new XSLTInputHandler(xmlFile, xslFile);
//			logTime("step 9");
//			org.xml.sax.XMLReader parser = inputHandler.getParser();
//			logTime("step 10");
//			driver.setOutputStream(out);
//
//			Options options = new Options(new File(configFile));
//
//			logTime("step 11");
//			driver.render(parser, inputHandler.getInputSource());
//			logTime("step 12");
//			//删除临时文件
//			xmlFile.delete();
//			reportData.setData(out.toByteArray());
//
//		} catch (Exception e) {
//			logger.error("下载pdf文件失败", e);
//		}
//		try {
//			org.apache.fop.image.FopImageFactory.resetCache();
//		} catch (Exception e) {
//			logger.error("下载pdf文件失败", e);
//			//no need to throw the exception
//		}
		logTime("step 12");
		return reportData;
		
	}
	
	public static void logTime(String pStep) {
		
		logger.info(pStep + ":" + String.valueOf(System.currentTimeMillis()));
		
	}
}
