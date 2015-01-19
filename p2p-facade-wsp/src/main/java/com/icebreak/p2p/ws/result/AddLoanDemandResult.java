package com.icebreak.p2p.ws.result;



public class AddLoanDemandResult extends P2PBaseResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8969648130682531875L;
	long demandId;

	public long getDemandId() {
		return demandId;
	}
	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}
	@Override
	public String toString() {
		return "AddLoanDemandResult [demandId=" + demandId + "]";
	}
	
}
