package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.GoldExperienceDao;
import com.icebreak.p2p.dataobject.GoldExperienceDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public class GoldExperienceDaoImpl extends SqlMapClientDaoSupport implements GoldExperienceDao {

  @Override
  public long countAll(Map<String, Object> condition) throws DataAccessException {
    return (long) getSqlMapClientTemplate().queryForObject("RM-GOLD-EXPERIENCE-SELECT-COUNT", condition);
  }

  @Override
  public long insert(GoldExperienceDO goldExp) throws DataAccessException {
    return (long) getSqlMapClientTemplate().insert("RM-GOLD-EXPERIENCE-INSERT", goldExp);
  }

  @Override
  public long update(GoldExperienceDO goldExp) throws DataAccessException {
    return (long) getSqlMapClientTemplate().update("RM-GOLD-EXPERIENCE-UPDATE", goldExp);
  }

  @Override
  public long delete(GoldExperienceDO goldExp) throws DataAccessException {
    return getSqlMapClientTemplate().delete("RM-GOLD-EXPERIENCE-DELETE", goldExp);
  }

  @Override
  public GoldExperienceDO queryGoldExp(GoldExperienceDO goldExp) throws DataAccessException {
    return (GoldExperienceDO) getSqlMapClientTemplate().queryForObject("RM-GOLD-EXPERIENCE-SELECT-ONLY", goldExp);
  }

  @Override
  public List<GoldExperienceDO> queryList(Map<String, Object> condition) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("RM-GOLD-EXPERIENCE-SELECT-DYNAMIC", condition);
  }

  @Override
  public GoldExperienceDO queryById(long id) throws DataAccessException {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    return (GoldExperienceDO) getSqlMapClientTemplate().queryForObject("RM-GOLD-EXPERIENCE-SELECT-BY-ID", map);
  }

  public List<GoldExperienceDO> queryMay() throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("RM-GOLD-EXPERIENCE-SELECT-MAY");
  }

}
