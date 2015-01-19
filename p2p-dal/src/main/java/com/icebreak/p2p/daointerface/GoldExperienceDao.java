package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.GoldExperienceDO;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public interface GoldExperienceDao {

  public long countAll(Map<String, Object> condition) throws DataAccessException;

  /**
   * 新增体验金
   * @param goldExp
   * @return
   * @throws DataAccessException
   */
  public long insert(GoldExperienceDO goldExp) throws DataAccessException;

  /**
   * 更新体验金
   * @param goldExp
   * @return
   * @throws DataAccessException
   */
  public long update(GoldExperienceDO goldExp) throws DataAccessException;

  /**
   * 删除
   * @param goldExp
   * @return
   * @throws DataAccessException
   */
  public long delete(GoldExperienceDO goldExp) throws DataAccessException;

  /**
   * 查询
   * @param goldExp
   * @return
   * @throws DataAccessException
   */
  public GoldExperienceDO queryGoldExp(GoldExperienceDO goldExp) throws DataAccessException;

  /**
   * 动态查询
   * @param goldExp
   * @return
   * @throws DataAccessException
   */
  public List<GoldExperienceDO> queryList(Map<String, Object> condition) throws DataAccessException;

  /**
   * 根据ID查询
   * @param id
   * @return
   * @throws DataAccessException
   */
  public GoldExperienceDO queryById(long id) throws DataAccessException;

  /**
   * 查询可用的体验金类型
   * @return
   * @throws DataAccessException
   */
  public List<GoldExperienceDO> queryMay() throws DataAccessException;

}
