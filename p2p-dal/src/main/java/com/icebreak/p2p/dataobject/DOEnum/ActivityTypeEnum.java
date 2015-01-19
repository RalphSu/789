package com.icebreak.p2p.dataobject.DOEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/7.
 */
public enum ActivityTypeEnum {

  REGISTER_GOLD("REGISTER_GOLD", "体验金"),

//  GIFT_MONEY("GIFT_MONEY", "红包"),

  OTHER("OTHER", "其他")
  ;

  public String code = null;
  public String message = null;

  private ActivityTypeEnum(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }
  public String getMessage() {
    return message;
  }

  public static List<ActivityTypeEnum> getEnums() {
    List<ActivityTypeEnum> list = new ArrayList<ActivityTypeEnum>();
    for(ActivityTypeEnum loanType : values()) {
      list.add(loanType);
    }
    return list;
  }

}
