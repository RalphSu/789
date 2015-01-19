package com.icebreak.p2p.charge;

public interface TradeChargeServiceFactory {
  /**
   * 获取按笔收费服务
   * @param chargeCode
   * @return
   */
  public TradeChargeService getTradeChargeService(String chargeCode);
}
