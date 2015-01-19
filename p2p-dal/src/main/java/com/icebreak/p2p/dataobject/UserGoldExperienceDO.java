package com.icebreak.p2p.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public class UserGoldExperienceDO implements Serializable {
  /**
   * 用户体验金ID
   */
  private long id;
  /**
   * 用户ID
   */
  private long userId;
  /**
   * 交易ID
   */
  private long tradeId;
  /**
   * 交易记录ID
   */
  private long tradeDetailId;
  /**
   * 体验金ID
   */
  private long goldExpId;
  /**
   * 体验金额
   */
  private BigDecimal amount;
  /**
   * 使用状态
   */
  private String status;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 使用时间
   */
  private Date usageTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getTradeId() {
    return tradeId;
  }

  public void setTradeId(long tradeId) {
    this.tradeId = tradeId;
  }

  public long getTradeDetailId() {
    return tradeDetailId;
  }

  public void setTradeDetailId(long tradeDetailId) {
    this.tradeDetailId = tradeDetailId;
  }

  public long getGoldExpId() {
    return goldExpId;
  }

  public void setGoldExpId(long goldExpId) {
    this.goldExpId = goldExpId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUsageTime() {
    return usageTime;
  }

  public void setUsageTime(Date usageTime) {
    this.usageTime = usageTime;
  }

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
