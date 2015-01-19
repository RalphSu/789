package com.icebreak.p2p.front.controller.trade.download;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;

public class WordParseThread extends Thread {
	private HttpServletResponse		response;
	private HttpSession				session;
	private Trade					trade;
	private String					loanFlowCode;
	private String					guaranteeContractCode;
	private InstitutionsInfoDO		institutionsInfo;
	private LoanDemandDO			loan;
	private List<Map<String, Text>>	investFlowCodes;
	private List<BankBasicInfo>		bankBasicInfos;
	private String					type;
	private String					downType;
	
	@Override
	public void run() {
		WordParse wordParse = new WordParse();
		wordParse.writWordParse(response, session, trade, loanFlowCode, guaranteeContractCode,
			institutionsInfo, loan, investFlowCodes, bankBasicInfos, type, downType);
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpSession getSession() {
		return session;
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public Trade getTrade() {
		return trade;
	}
	
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public String getLoanFlowCode() {
		return loanFlowCode;
	}
	
	public void setLoanFlowCode(String loanFlowCode) {
		this.loanFlowCode = loanFlowCode;
	}
	
	public String getGuaranteeContractCode() {
		return guaranteeContractCode;
	}
	
	public void setGuaranteeContractCode(String guaranteeContractCode) {
		this.guaranteeContractCode = guaranteeContractCode;
	}
	
	public InstitutionsInfoDO getInstitutionsInfo() {
		return institutionsInfo;
	}
	
	public void setInstitutionsInfo(InstitutionsInfoDO institutionsInfo) {
		this.institutionsInfo = institutionsInfo;
	}
	
	public LoanDemandDO getLoan() {
		return loan;
	}
	
	public void setLoan(LoanDemandDO loan) {
		this.loan = loan;
	}
	
	public List<Map<String, Text>> getInvestFlowCodes() {
		return investFlowCodes;
	}
	
	public void setInvestFlowCodes(List<Map<String, Text>> investFlowCodes) {
		this.investFlowCodes = investFlowCodes;
	}
	
	public List<BankBasicInfo> getBankBasicInfos() {
		return bankBasicInfos;
	}
	
	public void setBankBasicInfos(List<BankBasicInfo> bankBasicInfos) {
		this.bankBasicInfos = bankBasicInfos;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDownType() {
		return downType;
	}
	
	public void setDownType(String downType) {
		this.downType = downType;
	}
	
}
