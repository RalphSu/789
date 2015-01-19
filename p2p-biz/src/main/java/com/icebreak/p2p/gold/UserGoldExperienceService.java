package com.icebreak.p2p.gold;

import com.icebreak.p2p.dataobject.UserGoldExperienceDO;

import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public interface UserGoldExperienceService {

  public long addUserGoldExp(UserGoldExperienceDO userGoldExp);

  public void updateUserGoldExp(UserGoldExperienceDO userGoldExp);

  public List<UserGoldExperienceDO> queryList(UserGoldExperienceDO userGoldExp);

}
