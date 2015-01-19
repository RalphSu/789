package com.icebreak.p2p.dataobject;

import com.icebreak.p2p.dataobject.DOEnum.ActivityTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public class GoldExperienceDO implements Serializable {

  /**
   * 体验金ID
   */
  private long id;
  /**
   * 体验金类型
   */
  private ActivityTypeEnum activityType;
  /**
   * 体验金名称
   */
  private String name;
  /**
   * 体验金额
   */
  private BigDecimal amount;
  /**
   * 体验金用途
   */
  private String purpose;
  /**
   * 体验金发放数量
   */
  private int quantity;
  /**
   * 剩余数量
   */
  private int surplusQuantity;
  /**
   * 状态 1:未开始,2:已开始,3:已结束
   */
  private int status;
  /**
   * 开始时间
   */
  private Date startTime;
  /**
   * 结束时间
   */
  private Date endTime;

  public GoldExperienceDO() {
    this(0L);
  }

  public GoldExperienceDO(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ActivityTypeEnum getActivityType() {
    return activityType;
  }

  public void setActivityType(ActivityTypeEnum activityType) {
    this.activityType = activityType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getSurplusQuantity() {
    return surplusQuantity;
  }

  public void setSurplusQuantity(int surplusQuantity) {
    this.surplusQuantity = surplusQuantity;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

}
