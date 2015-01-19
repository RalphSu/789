package com.icebreak.p2p.front.controller.trade.download;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.dataobject.UserInvestEntry;
import com.icebreak.p2p.trade.TradeService;
import com.icebreak.p2p.user.UserBaseInfoManager;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.LoanUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.util.fop.FopReport;
import com.icebreak.p2p.util.fop.ReportData;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.p2p.ws.enums.PDFTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PDFParse {


    @Autowired
    protected UserBaseInfoManager userBaseInfoManager;
	@Autowired
	protected TradeService tradeService;
	
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final String XSL_BASE_PATH = "WEB-INF" + File.separator + "xsl";
    private final String XSL_FILE_PATH = XSL_BASE_PATH + File.separator + "xsls" + File.separator;
    //	private final String LETTER = "guarantee_letter.xsl";
    //	private final String CONTRACT = "guarantee_contract_longKing.xsl";
    private final String XSL_CONFIGFILE = "userconfig.xml";

    public void writPDF(HttpServletResponse response, HttpSession session, Trade trade,
                        String loanFlowCode, String guaranteeContractCode,
                        InstitutionsInfoDO institutionsInfo, LoanDemandDO loan,
                        List<Map<String, Text>> investFlowCodes,
                        List<BankBasicInfo> bankBasicInfos, String pdfFileCode, String downType) {
        //意向担保函号

        PDFTypeEnum pdfTypeEnum = PDFTypeEnum.getByCode(pdfFileCode);
        Map<String, String> dataMap = creatDataMap(trade, loanFlowCode, guaranteeContractCode,
                institutionsInfo, loan, investFlowCodes, pdfTypeEnum);

        this.readPDF(response, session, dataMap, investFlowCodes, loan, pdfTypeEnum, downType);

    }

    public void readPDF(HttpServletResponse response, HttpSession session,
                        Map<String, String> dataMap, List<Map<String, Text>> investFlowCodes,
                        LoanDemandDO loan, PDFTypeEnum pdfTypeEnum, String downType) {

        try {
            ReportData data = null;

            String serverRealPath = session.getServletContext().getRealPath("/");

            String configFile = serverRealPath + XSL_BASE_PATH + File.separator + XSL_CONFIGFILE;
            String fileType = pdfTypeEnum.getCode();

            //生成xml数据文件
            byte[] xmlBuf = getXMLData(pdfTypeEnum, dataMap, investFlowCodes);
            data = FopReport.createReport(
                    serverRealPath + XSL_FILE_PATH + pdfTypeEnum.getXslfileName(), xmlBuf, configFile);

            DownloadAndPrivewThread downThread = new DownloadAndPrivewThread();
            downThread.setDownType(downType);
            downThread.setFileBytes(data.getData());
            downThread.setResponse(response);
            downThread.setPdfTypeEnum(pdfTypeEnum);
            downThread.setProName(loan.getLoanName());
            downThread.run();

        } catch (Exception e) {
            logger.error("下载pdf文件失败", e);
        }
    }

    private Map<String, String> creatDataMap(Trade trade, String loanFlowCode,
                                             String guaranteeContractCode,
                                             InstitutionsInfoDO institutionsInfo,
                                             LoanDemandDO loan,
                                             List<Map<String, Text>> investFlowCodes,
                                             PDFTypeEnum pdfTypeEnum) {

        String platformName = AppConstantsUtil.getPlatformName();
        String productName = AppConstantsUtil.getProductName();

        String guaranteeLicenseNo = loan.getGuaranteeLicenseNo();
        String loaned_amount = MoneyUtil.getFormatAmount(trade.getLoanedAmount() / 10000);//实际借款金额
        double interest_rate = MoneyUtil.getPercentage(loan.getInterestRate());
        String moneyTal=MoneyUtil.digitUppercase(trade.getLoanedAmount());//大写金额
        String perTal=MoneyUtil.getPercentageUppercase(loan.getInterestRate());
        
        Date trade_effective_date = trade.getEffectiveDateTime();//成立日期
        Date trade_expire_date = trade.getExpireDateTime();//到期日期
        String limit=  LoanUtil.getLoanTime(trade.getTimeLimit(),trade.getTimeLimitUnit());//期限
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
        Map<String, String> map = new HashMap<String, String>();

        map.put("platformName", StringUtil.nullToEmpty(platformName));
        map.put("productName", StringUtil.nullToEmpty(productName));

        map.put("DMONEY", loaned_amount != null ? String.valueOf(loaned_amount) : "0");
        map.put("moneyTal",moneyTal);
        
        
        String fileType = pdfTypeEnum.getCode();
        if (fileType.indexOf("contract") != -1) {//担保合同
            map.put("CONTRACTNO", StringUtil.nullToEmpty(guaranteeContractCode));
            map.put("RFLOWNO", StringUtil.nullToEmpty(loanFlowCode));
            map.put("DBHNO",
                    loanFlowCode != null ? loanFlowCode.substring(0, loanFlowCode.length() - 1) : "");

            map.put("PER", String.valueOf(interest_rate));
            map.put("perTal", perTal);
            if (effDate.length == 3) {
                map.put("SYYYY", StringUtil.nullToEmpty(effDate[0]));
                map.put("SMM", StringUtil.nullToEmpty(effDate[1]));
                map.put("SDD", StringUtil.nullToEmpty(effDate[2]));
            }
            if (arrDate.length == 3) {
                map.put("EYYYY", StringUtil.nullToEmpty(arrDate[0]));
                map.put("EMM", StringUtil.nullToEmpty(arrDate[1]));
                map.put("EDD", StringUtil.nullToEmpty(arrDate[2]));
            }

        } else {//担保函

            String enterprise_name = "";//担保公司名称
            String address = "";//地址
            String legal_name = "";//法人姓名

            String province = "";//省份
            String city = "";// 城市
            String zipCode = "";//邮编

            enterprise_name = institutionsInfo.getEnterpriseName();
            province = institutionsInfo.getBusinessLicenseProvince();
            city = institutionsInfo.getBusinessLicenseCity();
            address = institutionsInfo.getCommonlyUsedAddress();
            legal_name = institutionsInfo.getLegalRepresentativeName();

            zipCode = institutionsInfo.getZipCode();
            String content = guaranteeLicenseNo;
            String comAddress = StringUtil.nullToEmpty(province) + StringUtil.nullToEmpty(city)
                    + StringUtil.nullToEmpty(address);

            map.put("LETTERNO", StringUtil.nullToEmpty(content));
            map.put("CONTRACTNO", StringUtil.nullToEmpty(guaranteeContractCode));
            map.put("ENTERPRISE", StringUtil.nullToEmpty(enterprise_name));
            map.put("COMADDRESS", comAddress);
            map.put("LEGALNAME", StringUtil.nullToEmpty(legal_name));
            
            map.put("loanerName", StringUtil.subString(userBaseInfoManager.getRealNameByUserName(loan.getUserName()).getRealName(),1,"**"));//融资人真实姓名
            map.put("loanerIdentityId", StringUtil.subString(userBaseInfoManager.getCertNoByUserId(loan.getLoanerId()),3,"************"));
            map.put("limit", limit);
            map.put("PER", String.valueOf(interest_rate));
            map.put("perTal", perTal);
            
            
            
            //			map.put("DMONEY ", loaned_amount != null ? String.valueOf(loaned_amount) : "0");
            map.put("ZIPCODE", StringUtil.nullToEmpty(zipCode));
            if (effDate.length == 3) {
                map.put("SYYYY", StringUtil.nullToEmpty(effDate[0]));
                map.put("SMM", StringUtil.nullToEmpty(effDate[1]));
                map.put("SDD", StringUtil.nullToEmpty(effDate[2]));
            }
            if (arrDate.length == 3) {
                map.put("EYYYY", StringUtil.nullToEmpty(arrDate[0]));
                map.put("EMM", StringUtil.nullToEmpty(arrDate[1]));
                map.put("EDD", StringUtil.nullToEmpty(arrDate[2]));
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
        return map;
    }

    private byte[] getXMLData(PDFTypeEnum pdfTypeEnum, Map<String, String> dataMap,
                              List<Map<String, Text>> investFlowCodes) {
        StringBuffer xml = new StringBuffer();

        String fileType = pdfTypeEnum.getCode();

        if (fileType.indexOf("contract") != -1) {//担保合同
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>     \n\r");
            xml.append(" <EntryDailyListReport>             \r\n");
            xml.append(" 	<PageList><!-- 报表主体  //-->        \r\n");
            xml.append(" 		<ReportBody><!-- 报表数据,表格形式,可以定义多个表格 //-->      \r\n");
            xml.append(" 			<Blank />                     \r\n");
            xml.append(" 			<Blank />                     \r\n");
            xml.append(" 			<TableBegin>                  \r\n");
            xml.append(" 				<LETTERNO>" + dataMap.get("CONTRACTNO")
                    + "</LETTERNO> <!-- 编号 -->        \r\n");
            xml.append(" 			</TableBegin>	                \r\n");
            xml.append(" 			<TableContract>               \r\n");

            xml.append(" 			   <platformName>" + dataMap.get("platformName")
                    + "</platformName>\r\n");
            xml.append(" 			   <productName>" + dataMap.get("productName") + "</productName> \r\n");

            xml.append(" 			   <!-- 投资人流水号 -->	          \r\n");
            xml.append(" 			   <InvestorTable>            \r\n");

            for (Map<String, Text> map : investFlowCodes) {
                xml.append(" 		       <InvestorRow>        \r\n");
                xml.append(" 		           	<serialNO>" + map.get("TRLOWNO").getText()
                        + "</serialNO> \r\n");
                xml.append("                    <amout>" + map.get("TMONEY").getText()
                        + "</amout> \r\n");
                xml.append("                    <userName>" + map.get("USER_NAME").getText()
                        + "</userName> \r\n");
                xml.append("                    <realName>" + map.get("REAL_NAME").getText()
                        + "</realName> \r\n");
                xml.append(" 		       </InvestorRow>       \r\n");
            }
            xml.append(" 	           </InvestorTable>       \r\n");
            xml.append(" 	           <!-- 投资接受人流水编号 -->     \r\n");
            xml.append(" 	           <financier>            \r\n");
            xml.append(" 	  <serialNO>" + dataMap.get("RFLOWNO")
                    + "</serialNO>                \r\n");
            xml.append(" 	           </financier>           \r\n");
            xml.append(" 	           <!-- 担保承诺函编号 -->       \r\n");
            xml.append(" 	           <guaranteeLetter>      \r\n");
            xml.append(" 	<serialNO>" + dataMap.get("DBHNO") + "</serialNO>               \r\n");
            xml.append(" 	           </guaranteeLetter>     \r\n");
            xml.append(" 	           <investTable>          \r\n");
            xml.append(" 		           <!-- 投资权益本金合计 -->    \r\n");
            xml.append(" 					<DMONEY>" + dataMap.get("DMONEY") + "</DMONEY>     \r\n");
            xml.append(" 					<!-- 投资权益年化收益率 -->  \r\n");
            xml.append(" 					<PER>" + dataMap.get("PER") + "</PER>   \r\n");
            xml.append(" 					<perTal>" + dataMap.get("perTal") + "</perTal>   \r\n");
            xml.append(" 					<moneyTal>" + dataMap.get("moneyTal") + "</moneyTal>   \r\n");
            
            
            
            
            xml.append(" 					<!-- 投资期间 -->        \r\n");
            xml.append(" 					<SYYYY>" + dataMap.get("SYYYY") + "</SYYYY>       \r\n");
            xml.append(" 					<SMM>" + dataMap.get("SMM") + "</SMM>             \r\n");
            xml.append(" 					<SDD>" + dataMap.get("SDD") + "</SDD>             \r\n");
            xml.append(" 					<!-- 回购到期日 -->            \r\n");
            xml.append(" 					<EYYYY>" + dataMap.get("EYYYY") + "</EYYYY>       \r\n");
            xml.append(" 					<EMM>" + dataMap.get("EMM") + "</EMM>             \r\n");
            xml.append(" 					<EDD>" + dataMap.get("EDD") + "</EDD>             \r\n");
            xml.append(" 			  </investTable>              \r\n");
            xml.append(" 					<SYYYY>" + dataMap.get("SYYYY") + "</SYYYY>       \r\n");
            xml.append(" 					<SMM>" + dataMap.get("SMM") + "</SMM>             \r\n");
            xml.append(" 					<SDD>" + dataMap.get("SDD") + "</SDD>             \r\n");
            xml.append(" 			</TableContract>              \r\n");
            xml.append(" 			<TableEnd>                    \r\n");
            xml.append(" 				<ENTERPRISE>                \r\n");
            xml.append(" 				   <![CDATA[ ENTERPRISE ]]>       \r\n");
            xml.append(" 				</ENTERPRISE>               \r\n");
            xml.append(" 				<PYYYY>" + dataMap.get("PYYYY") + "</PYYYY><!-- 打印日期 -->           \r\n");
            xml.append(" 				<PMM>" + dataMap.get("PMM") + "</PMM>               \r\n");
            xml.append(" 				<PDD>" + dataMap.get("PDD") + "</PDD>               \r\n");
            xml.append(" 			</TableEnd>	                  \r\n");
            xml.append(" 		</ReportBody><!-- 报表尾 //-->     \r\n");
            xml.append(" 		<ReportFooter></ReportFooter>   \r\n");
            xml.append(" 	</PageList>                       \r\n");
            xml.append(" </EntryDailyListReport>            \r\n");

        } else if(com.icebreak.util.lang.util.StringUtil.equals(fileType,"letter_zjht")){//担保函
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>     \n\r");
            xml.append("<EntryDailyListReport>          \n\r");
            xml.append("	<PageList>                  \n\r"); //     <!-- 报表主体  //-->
            xml.append("		<ReportBody>            \n\r"); //     <!-- 报表数据,表格形式,可以定义多个表格 //-->
            xml.append("		<TableBegin>            \n\r");
            xml.append("			<LETTERNO>" + dataMap.get("LETTERNO") + "</LETTERNO>\n\r"); //    <!-- 编号 -->
            xml.append("			<ENTERPRISE>        \n\r");
            xml.append("			   <![CDATA[" + dataMap.get("ENTERPRISE") + "]]>            \n\r");
            xml.append("			</ENTERPRISE>       \n\r");
            xml.append("			<COMADDRESS><![CDATA[" + dataMap.get("COMADDRESS")
                    + " ]]></COMADDRESS> \n\r");
            xml.append("			<ZIPCODE>" + dataMap.get("ZIPCODE") + "</ZIPCODE>          \n\r");
            xml.append("			<LEGALNAME>" + dataMap.get("LEGALNAME") + "</LEGALNAME>   \n\r"); // -- 法定代表人 -->
            xml.append("		</TableBegin>	        \n\r");
            xml.append("		<TableLetter>           \n\r");

            xml.append(" 		<ENTERPRISE>" + dataMap.get("ENTERPRISE") + "</ENTERPRISE>\r\n");
            xml.append(" 		<productName>" + dataMap.get("productName") + "</productName> \r\n");

            xml.append("			<DMONEY>" + dataMap.get("DMONEY") + "</DMONEY> \n\r"); // <!-- 投资权益  （单位万元）-->
            xml.append("			<PER>" + dataMap.get("PER") + "</PER>    \n\r"); //  <!--年化收益率  xx.0%-->
            xml.append(" 					<perTal>" + dataMap.get("perTal") + "</perTal>   \r\n");
            xml.append(" 					<moneyTal>" + dataMap.get("moneyTal") + "</moneyTal>   \r\n");
            
            
            
            xml.append("			<SYYYY>" + dataMap.get("SYYYY") + "</SYYYY> \n\r"); //          <!-- 投资期间 -->
            xml.append("			<SMM>" + dataMap.get("SMM") + "</SMM>       \n\r");
            xml.append("			<SDD>" + dataMap.get("SDD") + "</SDD>       \n\r");
            xml.append("			<EYYYY>" + dataMap.get("EYYYY") + "</EYYYY> \n\r"); //           <!-- 投资期间 -->
            xml.append("			<EMM>" + dataMap.get("EMM") + "</EMM>       \n\r");
            xml.append("			<EDD>" + dataMap.get("EDD") + "</EDD>       \n\r");

            xml.append("			<loanerName>" + dataMap.get("loanerName") + "</loanerName>       \n\r");
            xml.append("			<loanerIdentityId>" + dataMap.get("loanerIdentityId") + "</loanerIdentityId>       \n\r");
            xml.append("			<limit>" + dataMap.get("limit") + "</limit>       \n\r");
           
            xml.append("			<CONTRACTNO>" + dataMap.get("CONTRACTNO")
                    + "</CONTRACTNO>                        \n\r"); //       <!-- 合同编号 -->
            xml.append("		</TableLetter>          \n\r");
            xml.append("		<TableEnd>              \n\r");
            xml.append("			<ENTERPRISE>        \n\r");
            xml.append("			   <![CDATA[" + dataMap.get("ENTERPRISE") + "]]>            \n\r");
            xml.append("			</ENTERPRISE>       \n\r");
            xml.append("			<PYYYY>"+dataMap.get("SYYYY")+"</PYYYY> \n\r"); //    <!-- 打印日期 -->
            xml.append("			<PMM>"+dataMap.get("SMM")+"</PMM>       \n\r");
            xml.append("			<PDD>"+dataMap.get("SDD")+"</PDD>       \n\r");
            xml.append("		</TableEnd>	            \n\r");
            xml.append("		</ReportBody>           \n\r"); //     <!-- 报表尾 //-->
            xml.append("		<ReportFooter></ReportFooter>          \n\r");
            xml.append("	</PageList>                 \n\r");
            xml.append("</EntryDailyListReport>");

        }else {//担保函
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>     \n\r");
            xml.append("<EntryDailyListReport>          \n\r");
            xml.append("	<PageList>                  \n\r"); //     <!-- 报表主体  //-->
            xml.append("		<ReportBody>            \n\r"); //     <!-- 报表数据,表格形式,可以定义多个表格 //-->
            xml.append("		<TableBegin>            \n\r");
            xml.append("			<LETTERNO>" + dataMap.get("LETTERNO") + "</LETTERNO>\n\r"); //    <!-- 编号 -->
            xml.append("			<ENTERPRISE>        \n\r");
            xml.append("			   <![CDATA[" + dataMap.get("ENTERPRISE") + "]]>            \n\r");
            xml.append("			</ENTERPRISE>       \n\r");
            xml.append("			<COMADDRESS><![CDATA[" + dataMap.get("COMADDRESS")
                    + " ]]></COMADDRESS> \n\r");
            xml.append("			<ZIPCODE>" + dataMap.get("ZIPCODE") + "</ZIPCODE>          \n\r");
            xml.append("			<LEGALNAME>" + dataMap.get("LEGALNAME") + "</LEGALNAME>   \n\r"); // -- 法定代表人 -->
            xml.append("		</TableBegin>	        \n\r");
            xml.append("		<TableLetter>           \n\r");

            xml.append(" 		<platformName>" + dataMap.get("platformName") + "</platformName>\r\n");
            xml.append(" 		<productName>" + dataMap.get("productName") + "</productName> \r\n");

            xml.append("			<DMONEY>" + dataMap.get("DMONEY") + "</DMONEY> \n\r"); // <!-- 投资权益  （单位万元）-->
            xml.append("			<PER>" + dataMap.get("PER") + "</PER>    \n\r"); //  <!--年化收益率  xx.0%-->
            xml.append(" 					<perTal>" + dataMap.get("perTal") + "</perTal>   \r\n");
            xml.append(" 					<moneyTal>" + dataMap.get("moneyTal") + "</moneyTal>   \r\n");
            
            
            xml.append("			<SYYYY>" + dataMap.get("SYYYY") + "</SYYYY> \n\r"); //          <!-- 投资期间 -->
            xml.append("			<SMM>" + dataMap.get("SMM") + "</SMM>       \n\r");
            xml.append("			<SDD>" + dataMap.get("SDD") + "</SDD>       \n\r");
            xml.append("			<EYYYY>" + dataMap.get("EYYYY") + "</EYYYY> \n\r"); //           <!-- 投资期间 -->
            xml.append("			<EMM>" + dataMap.get("EMM") + "</EMM>       \n\r");
            xml.append("			<EDD>" + dataMap.get("EDD") + "</EDD>       \n\r");

            xml.append("			<loanerName>" + dataMap.get("loanerName") + "</loanerName>       \n\r");
            xml.append("			<loanerIdentityId>" + dataMap.get("loanerIdentityId") + "</loanerIdentityId>       \n\r");
            xml.append("			<limit>" + dataMap.get("limit") + "</limit>       \n\r");
            
            xml.append("			<CONTRACTNO>" + dataMap.get("CONTRACTNO")
                    + "</CONTRACTNO>                        \n\r"); //       <!-- 合同编号 -->
            xml.append("		</TableLetter>          \n\r");
            xml.append("		<TableEnd>              \n\r");
            xml.append("			<ENTERPRISE>        \n\r");
            xml.append("			   <![CDATA[" + dataMap.get("ENTERPRISE") + "]]>            \n\r");
            xml.append("			</ENTERPRISE>       \n\r");
            xml.append("			<PYYYY>" + dataMap.get("PYYYY") + "</PYYYY> \n\r"); //    <!-- 打印日期 -->
            xml.append("			<PMM>" + dataMap.get("PMM") + "</PMM>       \n\r");
            xml.append("			<PDD>" + dataMap.get("PDD") + "</PDD>       \n\r");
            xml.append("		</TableEnd>	            \n\r");
            xml.append("		</ReportBody>           \n\r"); //     <!-- 报表尾 //-->
            xml.append("		<ReportFooter></ReportFooter>          \n\r");
            xml.append("	</PageList>                 \n\r");
            xml.append("</EntryDailyListReport>");

        }

        byte[] bytes = null;

        try {
            bytes = xml.toString().getBytes("utf-8");
            logger.debug("XMLData:" + xml.toString());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return bytes;
    }

	public UserBaseInfoManager getUserBaseInfoManager() {
		return userBaseInfoManager;
	}

	public void setUserBaseInfoManager(UserBaseInfoManager userBaseInfoManager) {
		this.userBaseInfoManager = userBaseInfoManager;
	}
    
    

}
