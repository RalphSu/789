package com.icebreak.p2p.dataobject.DOEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/7.
 */
public enum LoanTypeEnum {

  PERSONAL_INVEST("person", "个人投资"),

  BUSINESS_INVEST("business", "商业投资");

  public String code = null;
  public String message = null;

  private LoanTypeEnum(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }
  public String getMessage() {
    return message;
  }


  public static List<LoanTypeEnum> getEnums() {
    List<LoanTypeEnum> list = new ArrayList<LoanTypeEnum>();
    for(LoanTypeEnum loanType : values()) {
      list.add(loanType);
    }
    return list;
  }

}
