package com.icebreak.p2p.login;

import com.icebreak.p2p.dataobject.UserBaseInfoDO;

public interface LoginService {
   /**
    * 登录
    * @param userName
    * @param password
    * @return
    */
  public UserBaseInfoDO login(String userName, String password);

}
