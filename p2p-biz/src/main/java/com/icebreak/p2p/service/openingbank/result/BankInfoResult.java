package com.icebreak.p2p.service.openingbank.result;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.icebreak.p2p.service.openingbank.info.BankInfo;
import com.icebreak.p2p.service.openingbank.info.CardInfo;
import com.icebreak.p2p.service.openingbank.info.ProvinceInfo;
import com.icebreak.p2p.ws.enums.SettleResultEnum;
import com.icebreak.p2p.ext.domain.ResultBase;

public class BankInfoResult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -2549498738906898533L;
	
	private SettleResultEnum	resultCode			= SettleResultEnum.UN_KNOWN_EXCEPTION;
	/**
	 * 所在开户行银行信息
	 */
	private BankInfo			bankInfo			= null;
	/**
	 * 银行卡的属性信息,
	 * 在getAllDistrict 、getOpeningBankByDistrictNameAndBankId中该值为null
	 */
	private CardInfo			cardInfo			= null;
	private List<ProvinceInfo>	provinceInfos		= null;
	
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
	
	public BankInfo getBankInfo() {
		return bankInfo;
	}
	
	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	public List<ProvinceInfo> getProvinceInfos() {
		return provinceInfos;
	}
	
	public void setProvinceInfos(List<ProvinceInfo> provinceInfos) {
		this.provinceInfos = provinceInfos;
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
