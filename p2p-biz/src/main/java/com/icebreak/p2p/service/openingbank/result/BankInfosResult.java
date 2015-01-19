package com.icebreak.p2p.service.openingbank.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.p2p.service.openingbank.info.BankInfo;
import com.icebreak.p2p.ws.enums.SettleResultEnum;
import com.icebreak.p2p.ext.domain.ResultBase;

public class BankInfosResult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -2549498738906898533L;
	
	private SettleResultEnum	resultCode			= SettleResultEnum.UN_KNOWN_EXCEPTION;
	
	private List<BankInfo>		bankInfos			= new ArrayList<BankInfo>();
	
	@Override
	public boolean isExecuted() {
		
		return resultCode == SettleResultEnum.EXECUTE_SUCCESS ? true : false;
	}
	
	public SettleResultEnum getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(SettleResultEnum resultCode) {
		this.resultCode = resultCode;
	}
	
	public List<BankInfo> getBankInfos() {
		return bankInfos;
	}
	
	public void setBankInfos(List<BankInfo> bankInfos) {
		this.bankInfos = bankInfos;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
