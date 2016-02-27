//package com.icebreak.p2p.front.controller.trade.download;
//
//import java.io.File;
//import java.lang.reflect.Method;
//import java.security.AccessController;
//import java.security.PrivilegedAction;
//import java.util.Random;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.icebreak.p2p.rs.util.UploadFileUtils;
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//
//public class Doc2Pdf {
//	protected final Logger	logger				= LoggerFactory.getLogger(getClass());
//	private final int		wdDoNotSaveChanges	= 0;									// 不保存待定的更改。
//	private final int		wdFormatPDF			= 17;									// PDF 格式
//																						
//	Random					random				= new Random();
//	
//	/**
//	 * 将word转为pdf文件
//	 * @param FilePath
//	 * @return
//	 */
//	public String createPDF(String FilePath) {
//		String PDF_PATH = UploadFileUtils.getStaticFilesPdfDir();
//		long start = (System.currentTimeMillis() * 10000) + random.nextInt(10000);
//		String pdfFile = PDF_PATH + String.valueOf(start) + ".pdf";
//		//		return tofileName;  //启动ActiveX
//		/*ActiveXComponent app = ActiveXComponent.createNewInstance("Word.Application");//启动ActiveX
//		Dispatch docs = app.getProperty("Documents").toDispatch();
//		app.setProperty("Visible", false);
//		Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
//			new Object[] { FilePath, new Variant(false), new Variant(true) }, new int[1])
//			.toDispatch();
//		try {
//			File tofile = new File(pdfFile);
//			if (tofile.exists()) {
//				tofile.delete();
//			}
//			//			Dispatch.call(doc,   //写入PDF中并保存
//			//					"SaveAs",
//			//					tofileName,
//			//					wdFormatPDF);	
//			File fileDir = new File(PDF_PATH);
//			if (!fileDir.exists()) {
//				fileDir.mkdir();
//			}
//			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { pdfFile,
//					new Variant(wdFormatPDF) }, new int[1]);
//			
//			Dispatch.call(doc, "Close", false);//关闭word文件
//			File word = new File(FilePath);
//			word.delete();//生成PDF后，删除word文件
//		} catch (Exception e) {
//			logger.error("word转pdf异常", e);
//		} finally {
//			if (app != null)
//				app.invoke("Quit", wdDoNotSaveChanges);
//		}*/
//		return pdfFile;
//	}
//	
//	/**
//	   * 清空缓冲
//	   * @param buffer
//	   */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void unmap(final Object buffer) {
//		AccessController.doPrivileged(new PrivilegedAction() {
//			@Override
//			@SuppressWarnings("restriction")
//			public Object run() {
//				try {
//					Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
//					getCleanerMethod.setAccessible(true);
//					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer,
//						new Object[0]);
//					cleaner.clean();
//				} catch (Exception e) {
//					logger.error("unmap异常{}", e.getMessage(), e);
//				}
//				return null;
//			}
//		});
//	}
//	
//}
