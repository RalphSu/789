package com.icebreak.p2p.gold.impl;

import com.icebreak.p2p.daointerface.UserGoldExperienceDao;
import com.icebreak.p2p.dataobject.UserGoldExperienceDO;
import com.icebreak.p2p.gold.UserGoldExperienceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
@Service("userGoldExperienceService")
public class UserGoldExperienceServicempl implements UserGoldExperienceService {

  @Resource
  private UserGoldExperienceDao userGoldExperienceDao;

  @Override
  public long addUserGoldExp(UserGoldExperienceDO userGoldExp) {
    if(null == userGoldExp) {
      throw new IllegalArgumentException("添加参数不能为空");
    }
    return userGoldExperienceDao.insert(userGoldExp);
  }

  @Override
  public void updateUserGoldExp(UserGoldExperienceDO userGoldExp) {
    if(null == userGoldExp) {
      throw new IllegalArgumentException("修改参数不能为空");
    }
    userGoldExperienceDao.update(userGoldExp);
  }

  @Override
  public List<UserGoldExperienceDO> queryList(UserGoldExperienceDO userGoldExp) {
    return userGoldExperienceDao.query(userGoldExp);
  }
}
