package com.icebreak.p2p.dataobject;

import java.io.Serializable;

/**
 * Created by asdfasdfa on 2014/12/9.
 */
public class LoanTypeDO implements Serializable {

  /**
   * ID
   */
  private int id;
  /**
   * 编码
   */
  private String code;
  /**
   * 名称
   */
  private String name;
  /**
   * 首页上是否隐藏
   */
  private String hidden;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHidden() {
    return hidden;
  }

  public void setHidden(String hidden) {
    this.hidden = hidden;
  }
}
