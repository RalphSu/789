package com.icebreak.p2p.gold;

import com.icebreak.p2p.dataobject.GoldExperienceDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public interface GoldExperienceService {
  /**
   * 新增
   * @param goldExp
   * @return
   */
  public long addGoldExp(GoldExperienceDO goldExp);

  /**
   * 更新
   * @param goldExp
   * @return
   */
  public long updateGoldExp(GoldExperienceDO goldExp);

  /**
   * 查询
   * @param goldExp
   * @return
   */
  public GoldExperienceDO query(GoldExperienceDO goldExp);

  /**
   * 分页查询
   * @param goldExp
   * @param pageParam
   * @return
   */
  public Page<GoldExperienceDO> queryList(GoldExperienceDO goldExp, PageParam pageParam);

  /**
   *
   * @param id
   * @return
   */
  public GoldExperienceDO queryById(long id);

  public void deleteById(GoldExperienceDO goldExp);

}
