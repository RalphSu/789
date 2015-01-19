package com.icebreak.p2p.front.controller.trade.download;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;

public class WordParse {
	protected final Logger	logger		= LoggerFactory.getLogger(getClass());
	private final String	FILE_PATH	= "WEB-INF/doc/";
	private final String	LETTER		= "担保函.doc";
	private final String	CONTRACT	= "投资权益回购履约担保合同.doc";
	
	public void writWordParse(HttpServletResponse response, HttpSession session, Trade trade,
								String loanFlowCode, String guaranteeContractCode,
								InstitutionsInfoDO institutionsInfo, LoanDemandDO loan,
								List<Map<String, Text>> investFlowCodes,
								List<BankBasicInfo> bankBasicInfos, String type, String downType) {
		//意向担保函号
		String guaranteeLicenseNo = loan.getGuaranteeLicenseNo();
		String loaned_amount = MoneyUtil.getFormatAmount(trade.getLoanedAmount() / 10000);//实际借款金额
		double interest_rate = MoneyUtil.getPercentage(loan.getInterestRate());
		;
		Date trade_effective_date = trade.getEffectiveDateTime();//成立日期
		Date trade_expire_date = trade.getExpireDateTime();//到期日期
		String[] arrDate = {};//存放到期日期
		String[] effDate = {};//存放成立日期
		if (trade_expire_date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = format.format(trade_expire_date);
			arrDate = strDate.split("-");
		}
		if (trade_effective_date != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strDate1 = format.format(trade_effective_date);
			effDate = strDate1.split("-");
		}
		String _file = FILE_PATH;
		Map<String, String> map = new HashMap<String, String>();
		List<Map<String, Text>> lst = new ArrayList<Map<String, Text>>();
		if ("contract".equals(type)) {//担保合同
			_file = _file + CONTRACT;
			lst = investFlowCodes;
			map.put("CONTRACTNO", guaranteeContractCode != null ? guaranteeContractCode : "");
			map.put("RFLOWNO", loanFlowCode != null ? loanFlowCode : "");
			map.put("DBHNO",
				loanFlowCode != null ? loanFlowCode.substring(0, loanFlowCode.length() - 1) : "");
			map.put("DMONEY",
				String.valueOf(loaned_amount != null ? String.valueOf(loaned_amount) : "0"));
			map.put("PER", String.valueOf(interest_rate));
			if (effDate.length == 3) {
				map.put("SYYYY", effDate[0]);
				map.put("SMM", effDate[1]);
				map.put("SDD", effDate[2]);
			}
			if (arrDate.length == 3) {
				map.put("EYYYY", arrDate[0]);
				map.put("EMM", arrDate[1]);
				map.put("EDD", arrDate[2]);
			}
			
		} else if ("letter".equals(type)) {//担保函
			//			Map<String,String> bankMap = new HashMap<String, String>();
			//			for(BankBasicInfo info:bankBasicInfos){
			//				bankMap.put(info.getBankCode(), info.getBankName());
			//			}
			
			String enterprise_name = "";//担保公司名称
			String address = "";//地址
			String legal_name = "";//法人姓名
			//			String phone = "";//电话
			//			String bankCode = "";//开户银行
			//			String bankNo = "";//银行账号
			String province = "";//省份
			String city = "";// 城市
			String zipCode = "";//邮编
			
			enterprise_name = institutionsInfo.getEnterpriseName();
			province = institutionsInfo.getBusinessLicenseProvince();
			city = institutionsInfo.getBusinessLicenseCity();
			address = institutionsInfo.getCommonlyUsedAddress();
			legal_name = institutionsInfo.getLegalRepresentativeName();
			//			phone = institutionsInfo.getComPhone();
			//			bankCode = institutionsInfo.getBankType();
			//			bankNo = institutionsInfo.getBankCardNo();
			zipCode = institutionsInfo.getZipCode();
			String content = guaranteeLicenseNo;
			_file = _file + LETTER;
			map.put("LETTERNO", content != null ? content : "");
			map.put("CONTRACTNO", guaranteeContractCode != null ? guaranteeContractCode : "");
			map.put("ENTERPRISE", enterprise_name != null ? enterprise_name : "");
			map.put("COMADDRESS", province + city + address != null ? province + city + address
				: "");
			map.put("LEGALNAME", legal_name != null ? legal_name : "");
			//			map.put("PHONE", phone);
			//			map.put("BANKNAME", bankMap.get(bankCode));
			//			map.put("BANKNO", bankNo);
			map.put("PER", String.valueOf(interest_rate));
			map.put("DMONEY ", loaned_amount != null ? String.valueOf(loaned_amount) : "0");
			map.put("ZIPCODE", zipCode != null ? zipCode : "");
			if (effDate.length == 3) {
				map.put("SYYYY", effDate[0]);
				map.put("SMM", effDate[1]);
				map.put("SDD", effDate[2]);
			}
			if (arrDate.length == 3) {
				map.put("EYYYY", arrDate[0]);
				map.put("EMM", arrDate[1]);
				map.put("EDD", arrDate[2]);
			}
		}
		Date date = new Date();
		SimpleDateFormat simpl = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpl.format(date);
		String[] aDate = strDate.split("-");
		if (aDate.length == 3) {
			map.put("PYYYY", aDate[0]);
			map.put("PMM", aDate[1]);
			map.put("PDD", aDate[2]);
		}
		this.readwriteWord(response, session, _file, map, lst, loan, downType);
		
	}
	
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public void readwriteWord(HttpServletResponse response, HttpSession session, String _file,
								Map<String, String> map, List<Map<String, Text>> lst,
								LoanDemandDO loan, String downType) {
		//读取word模板文件
		FileInputStream in;
		HWPFDocument hdt = null;
		String filePath = _file;
		ServletContext application = session.getServletContext();
		String serverRealPath = application.getRealPath("/");
		String fileTemp = AppConstantsUtil.getYrdUploadFolder() + File.separator + "doc";
		File fileDir = new File(fileTemp);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		try {
			in = new FileInputStream(new File(serverRealPath + filePath));
			hdt = new HWPFDocument(in);
		} catch (Exception e1) {
			logger.error("读取模板文件失败", e1);
		}
		
		//替换读取到的word模板内容的指定字段
		Range range = hdt.getRange();
		TableIterator it = new TableIterator(range);
		Table tb = null;
		while (it.hasNext()) {
			tb = it.next();
			break;
		}
		if (lst.size() > 0) {
			for (int i = 1; i <= lst.size(); i++) {
				Map<String, Text> replaces = lst.get(i - 1);
				TableRow tr = tb.getRow(i);
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text();
						final String old = s;
						for (String key : replaces.keySet()) {
							if (s.contains(key)) {
								s = s.replace(key, replaces.get(key).getText());
							}
						}
						if (!old.equals(s)) {// 有变化
							para.replaceText(old, s);
							s = para.text();
						}
					} // end for
				}
			}
			for (int n = lst.size() + 1; n < tb.numRows(); n++) {
				TableRow tr = tb.getRow(n);
				tr.delete();
			}
		}
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			range.replaceText(entry.getKey(), entry.getValue());
		}
		//String fileName = f[f.length-1];
		String fileName = System.currentTimeMillis()
							+ _file.substring(_file.lastIndexOf("."), _file.length());
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		try {
			FileOutputStream out = new FileOutputStream(fileTemp + fileName);//将新内容生成一个临时word文件
			hdt.write(ostream);
			out.write(ostream.toByteArray());
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("生成临时word文件失败", e);
		}
		Doc2Pdf doc2pdf = new Doc2Pdf();
		String pdfAddress = doc2pdf.createPDF(fileTemp + fileName);//word转pdf
		try {
			String fileType = "";
			if (lst.size() > 0) {//为合同时
				fileType = "contract";
			} else {//为担保函
				fileType = "letter";
			}
			DownloadAndPrivewFileTread downThread = new DownloadAndPrivewFileTread();
			//this.downloadAndPreviewFile(response, loan.getLoanName(), pdfAddress, downType, fileType);//下载
			downThread.setDownType(downType);
			downThread.setFilePath(pdfAddress);
			downThread.setResponse(response);
			downThread.setFileType(fileType);
			downThread.setProName(loan.getLoanName());
			downThread.run();
			File pdfFile = new File(pdfAddress);
			pdfFile.delete();
			
		} catch (Exception e) {
			logger.error("下载pdf文件失败", e);
		}
	}
	
}
