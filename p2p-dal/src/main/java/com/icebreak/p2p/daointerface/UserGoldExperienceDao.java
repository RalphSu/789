package com.icebreak.p2p.daointerface;


import com.icebreak.p2p.dataobject.UserGoldExperienceDO;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public interface UserGoldExperienceDao {

  /**
   * 新增
   * @param userGoldExp
   * @return
   * @throws DataAccessException
   */
  public long insert(UserGoldExperienceDO userGoldExp) throws DataAccessException;

  /**
   * 更新
   * @param userGoldExp
   * @throws DataAccessException
   */
  public void update(UserGoldExperienceDO userGoldExp) throws DataAccessException;

  /**
   * 查询
   * @param tradeId
   * @return
   * @throws DataAccessException
   */
  public List<UserGoldExperienceDO> query(UserGoldExperienceDO userGoldExp) throws DataAccessException;

 /**
   * 查询
   * @param tradeId
   * @return
   * @throws DataAccessException
   */
  public UserGoldExperienceDO getById(int id) throws DataAccessException;

  /**
   * 统计当前用户可用体验金
   * @param userId
   * @return
   * @throws DataAccessException
   */
  public BigDecimal countByUserId(long userId) throws DataAccessException;

}
