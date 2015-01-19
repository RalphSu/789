package com.icebreak.p2p.dataobject;

public class DivisionTemplateLoanDO {
	private long baseId;
	private long investTemplateId;
	private long repayTemplateId;
	public long getBaseId() {
		return baseId;
	}
	public void setBaseId(long baseId) {
		this.baseId = baseId;
	}
	public long getInvestTemplateId() {
		return investTemplateId;
	}
	public void setInvestTemplateId(long investTemplateId) {
		this.investTemplateId = investTemplateId;
	}
	public long getRepayTemplateId() {
		return repayTemplateId;
	}
	public void setRepayTemplateId(long repayTemplateId) {
		this.repayTemplateId = repayTemplateId;
	}
	
	public DivisionTemplateLoanDO() {
		super();
	}
	public DivisionTemplateLoanDO(long investTemplateId, long repayTemplateId) {
		super();
		this.investTemplateId = investTemplateId;
		this.repayTemplateId = repayTemplateId;
	}
	
}
