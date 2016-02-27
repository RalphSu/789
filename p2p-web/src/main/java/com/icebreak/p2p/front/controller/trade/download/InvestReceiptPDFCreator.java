package com.icebreak.p2p.front.controller.trade.download;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.division.DivisionService;
import com.icebreak.p2p.division.DivisionTemplateYrdService;
import com.icebreak.p2p.loandemand.LoanDemandManager;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.PersonalInfoManager;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InvestReceiptPDFCreator {
//	
//	@Autowired
//	protected TradeService tradeService;
//	
//	@Autowired
//	protected LoanDemandManager loanDemandManager;
//	
//	@Autowired
//	protected DivisionService divisionService;
//	
//	@Autowired
//	protected DivisionTemplateYrdService divisionTemplateLoanService;
//	
//	@Autowired
//	protected PersonalInfoManager personalInfoManager;
//	
//	@Autowired
//	protected UserBaseInfoManager userBaseInfoManager;
//	
//	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	private String receiptFilePath;
//	
//	/**
//	 * 生成投资凭证到服务器上
//	 * @param tradeId
//	 * @param detailId
//	 * @param servletPath
//	 * @return 投资凭证的文件url
//	 * @throws Exception
//	 */
//	public String creatFile4Receipt(long tradeId, long detailId, String servletPath)
//																					throws Exception {
//		
//		creatFileData4Receipt(tradeId, detailId, servletPath);
//		
//		return this.receiptFilePath;
//	}
//	
//	/**
//	 * 生成投资凭证到服务器上
//	 * @param tradeId
//	 * @param detailId
//	 * @param servletPath
//	 * @return 投资凭证 文件 byte[]
//	 * @throws Exception
//	 */
//	public byte[] creatFileData4Receipt(long tradeId, long detailId, String servletPath)
//																						throws Exception {
//		
//		FileInputStream fis = null;
//		BufferedInputStream buff = null;
//		
//		String fileKey = tradeId + "_" + detailId;//System.currentTimeMillis() + "";
//		String filePath = servletPath + "/resources/pdf/investReceipt_" + fileKey + ".pdf";
//		this.receiptFilePath = filePath;
//		
//		File file = new File(filePath);
//		
//		if (!file.exists()) {
//			
//			String timeLimit = "";
//			String interestRate = "";
//			String guaranteeName = "";
//			String investFlowCode = null;
//			String investor = "";
//			String investorReal = "";
//			String investorCertNo = "";
//			String loanner = "";
//			String loannerReal = "";
//			String loannerCertNo = "";
//			String investAmount = "";
//			String totalAmountStr = "";
//			String effectiveDate = "";
//			String expireDate = "";
//			
//			Trade trade = tradeService.getByTradeId(tradeId);
//			effectiveDate = DateUtil.simpleFormat(trade.getEffectiveDateTime());
//			expireDate = DateUtil.simpleFormat(trade.getExpireDateTime());
//			LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(trade
//				.getDemandId());
//			guaranteeName = loanDemand.getGuaranteeName();
//			if ("W".equals(loanDemand.getTimeLimitUnit())
//				|| "M".equals(loanDemand.getTimeLimitUnit())) {
//				timeLimit = loanDemand.getTimeLimit() + "个月";
//			} else if ("Y".equals(loanDemand.getTimeLimitUnit())) {
//				timeLimit = loanDemand.getTimeLimit() + "年";
//			} else {
//				timeLimit = loanDemand.getTimeLimit() + "天";
//			}
//			interestRate = CommonUtil.mul(loanDemand.getInterestRate(), 100) + "%";
//			List<UserInvestEntry> userInvests = tradeService.getEntriesByTradeIdAndDetailId(
//				tradeId, detailId);
//			long totalAmount = 0;
//			if (userInvests != null && userInvests.size() > 0) {
//				UserInvestEntry tradeItem = userInvests.get(0);
//				investAmount = MoneyUtil.getFormatAmount(tradeItem.getAmount());
//				long investorId = userInvests.get(0).getInvestorId();
//				long loannerId = userInvests.get(0).getLoanerId();
//				investorCertNo = getCertNoByUserId(investorId);
//				loannerCertNo = getCertNoByUserId(loannerId);
//				investor = userInvests.get(0).getInvestorUserName();
//				investorReal = userInvests.get(0).getInvestorRealName();
//				loannerReal = userInvests.get(0).getLoanerRealName();
//				loanner = userInvests.get(0).getLoanerUserName();
//				totalAmount = userInvests.get(0).getAmount();
//			}
//			
//			//计算应收利息
//			/*interest = caculateInterest(new Money(totalAmount), loanDemand.getInterestRate(),
//			loanDemand.getTimeLimit(), loanDemand.getTimeLimitUnit());*/
//			
//			long divisionAmount = 0;
//			long profitAmount = 0;
//			List<TradeDetail> details = tradeService.getInvestProfitTrade(detailId);
//			if (details != null && details.size() > 0) {
//				for (TradeDetail detail : details) {
//					divisionAmount += detail.getAmount();
//					if (detail.getProfitType() > 0) {
//						profitAmount += detail.getAmount();
//					}
//				}
//			}
//			totalAmount += divisionAmount;
//			
//			TradeFlowCode tradeFlow = tradeService.queryInvestFlowCodesByTradeDetailId(detailId);
//			if (tradeFlow != null) {
//				investFlowCode = tradeFlow.getTradeFlowCode();
//			}
//
//
//            String guaranteeLicenseNo = "";
//            Map<String, Object> cond = new HashMap<String, Object>();
//            cond.put("roleId", 8L);
//
//            cond.put("tradeId", trade.getId());
//            List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
//            if (det != null && det.size() > 0) {
//                 tradeFlow = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0)
//                        .getId());
//                if (tradeFlow != null) {
//                    guaranteeLicenseNo = tradeFlow.getTradeFlowCode();
//                }
//            }
//			
//			LoanDemandDO demand = loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId());
//			long divisionTemplateId = demand.getDivisionTemplateId();
//			DivisionTemplateLoanDO divisionTemplateLoan = divisionTemplateLoanService
//				.getByBaseId(divisionTemplateId);
//			List<DivsionRuleRole> investRolelist = divisionService.getRuleRole(String
//				.valueOf(divisionTemplateLoan.getInvestTemplateId()));
//			List<DivsionRuleRole> repayRolelist = divisionService.getRuleRole(String
//				.valueOf(divisionTemplateLoan.getRepayTemplateId()));
//			//只计算经纪人
//			double totalAnnualInterest = 0;
//			investRolelist.addAll(repayRolelist);
//			if (investRolelist != null && investRolelist.size() > 0) {
//				for (DivsionRuleRole druleRole : investRolelist) {
//					if (DivisionPhaseEnum.INVESET_PHASE.code().equals(druleRole.getPhase())) {
//						if ("11".equals(String.valueOf(druleRole.getRoleId()))) {
//							totalAnnualInterest += druleRole.getRule();
//						}
//						
//					}
//				}
//			}
//			
//			totalAmountStr = MoneyUtil.getFormatAmount(totalAmount);
//			String divisionAmountStr = MoneyUtil.getFormatAmount(divisionAmount);
//			
//			FileOutputStream fos = null;
//			Document doc = new Document(PageSize.A4, 20, 20, 140, 20);
//			try {
//				fos = new FileOutputStream(filePath);
//				PdfWriter writer = PdfWriter.getInstance(doc, fos);
//				doc.open();
//				// 解决中文问题  
//				BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//					BaseFont.NOT_EMBEDDED);
//				Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体     
//				Paragraph title = new Paragraph(AppConstantsUtil.getProductName() + "投资凭证",
//					titleChinese);// 抬头
//				title.setAlignment(Element.ALIGN_CENTER); // 居中设置
//				title.setLeading(1f);//设置行间距//设置上面空白宽度
//				doc.add(title);
//				
//				Font fontZH = new Font(bfChinese, 12, Font.NORMAL);
//				float[] widths = { 20f, 30f, 25f, 25f };
//				PdfPTable table = new PdfPTable(widths);
//				
//				table.setSpacingBefore(20f);// 设置表格上面空白宽度
//				table.setTotalWidth(500);// 设置表格的宽度
//				table.setWidthPercentage(100);//设置表格宽度为%100
//				// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
//				PdfPCell cell;
//				//	            cell = new PdfPCell(new Paragraph("投资凭证",fontZH));
//				//	            cell.setColspan(4);
//				//	            table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("平台服务商：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(AppConstantsUtil.getPlatformName(), fontZH));
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("还款方式：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(
//						loanDemand.getRepayDivisionWayMsg(), fontZH));
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("年化收益率：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(interestRate, fontZH));
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("投资期限：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(timeLimit, fontZH));
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("担保公司：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(guaranteeName, fontZH));
//				cell.setColspan(3);
//				table.addCell(cell);
//
//
//                cell = new PdfPCell(new Paragraph("担保函编号：", fontZH));
//                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Paragraph(StringUtil.nullToEmpty(loanDemand.getGuaranteeLicenseNo()), fontZH));
//                cell.setColspan(3);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Paragraph("借款本金：", fontZH));
//                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Paragraph((loanDemand.getLoanAmount()/100)+"", fontZH));
//                cell.setColspan(3);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Paragraph("借款用途：", fontZH));
//                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Paragraph(loanDemand.getLoanPurpose(), fontZH));
//                cell.setColspan(3);
//                table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("投资流水号：", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph(investFlowCode, fontZH));
//				cell.setColspan(3);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("投资人信息", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				cell.setColspan(2);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Paragraph("投资接受人信息", fontZH));
//				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//				cell.setColspan(2);
//				
//				table.addCell(cell);
//				Paragraph iparas = new Paragraph("用户名：" + investor, fontZH);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("姓名：" + investorReal);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("身份证号：" + StringUtil.subString(investorCertNo, 7, "****"));
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("成立日：" + effectiveDate);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("到期日：" + expireDate);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("投资本金(元)：" + investAmount);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("分润(元)：" + divisionAmountStr);
//				iparas.add(Chunk.NEWLINE);
//				iparas.add("应收总计(元)：" + totalAmountStr);
//				
//				cell = new PdfPCell(iparas);
//				cell.setColspan(2);
//				cell.setRowspan(8);
//				
//				cell.setMinimumHeight(120);
//				table.addCell(cell);
//				Paragraph paras = new Paragraph("用户名：" + loanner, fontZH);
//				paras.add(Chunk.NEWLINE);
//				paras.add("姓名：" + loannerReal);
//				paras.add(Chunk.NEWLINE);
//				paras.add("身份证号：" + StringUtil.subString(loannerCertNo, 7, "****"));
//				paras.add(Chunk.NEWLINE);
//				paras.add("成立日：" + effectiveDate);
//				paras.add(Chunk.NEWLINE);
//				paras.add("兑付日：" + expireDate);
//				paras.add(Chunk.NEWLINE);
//				paras.add(Chunk.NEWLINE);
//				paras.add(Chunk.NEWLINE);
//				paras.add("应付总计(元)：" + totalAmountStr);
//				cell = new PdfPCell(paras);
//				cell.setColspan(2);
//				cell.setRowspan(8);
//				table.addCell(cell);
//				doc.add(table);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				Paragraph tips = new Paragraph("  温馨提示： 需要盖章的客户，请亲临"
//												+ AppConstantsUtil.getPlatformName() + "进行人工盖章",
//					fontZH);// 抬头
//				tips.setLeading(1f);//设置行间距//设置上面空白宽度
//				doc.add(tips);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				doc.add(Chunk.NEWLINE);
//				tips = new Paragraph("  公司地址：" + AppConstantsUtil.getPlatformAddress(), fontZH);// 抬头
//				tips.setLeading(1f);//设置行间距//设置上面空白宽度
//				doc.add(tips);
//				//	            XMLWorkerHelper.getInstance().parseXHtml(writer, doc,
//				//	            		new ByteArrayInputStream(str.getBytes()));
//				doc.close();
//				logger.info("创建文档完成");
//				
//			} catch (Exception e) {
//				logger.error("创建投资凭证异常", e);
//				throw new Exception("创建投资凭证异常:" + e.getMessage());
//			} finally {
//				
//				if (fos != null) {
//					fos.close();
//				}
//			}
//		}
//		
//		byte[] data = new byte[1024];
//		
//		file = new File(filePath);
//		try {
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte[] temp = new byte[1024];
//			int size = 0;
//			
//			fis = new FileInputStream(file);
//			buff = new BufferedInputStream(fis);
//			
//			int i = 0;
//			
//			while ((size = buff.read(temp)) != -1) {
//				out.write(temp, 0, size);
//				i += 1;
//			}
//			
//			data = out.toByteArray();
//			
//			buff.close();
//			fis.close();
//			//file.delete();
//			
//			if (i == 0 && size == -1) { //删除上次因异常产生的空白PDF文件
//				file.delete();
//			}
//		} catch (FileNotFoundException e) {
//			logger.error("创建投资凭证异常", e);
//		} catch (IOException e) {
//			logger.error("delete file", e);
//		} finally {
//			if (fis != null) {
//				fis.close();
//			}
//		}
//		
//		return data;
//	}
//	
//	private String getCertNoByUserId(long userId) {
//		String certNo = null;
//		UserBaseInfoDO user = userBaseInfoManager.queryByUserId(userId);
//		try {
//			PersonalInfoDO person = personalInfoManager.query(user.getUserBaseId());
//			certNo = person.getCertNo();
//		} catch (Exception e) {
//			logger.error("getCertNoByUserId is error", e);
//		}
//		
//		return certNo;
//	}
}
