package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.UserGoldExperienceDao;
import com.icebreak.p2p.dataobject.UserGoldExperienceDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
public class UserGoldExperienceDaoImpl extends SqlMapClientDaoSupport implements UserGoldExperienceDao {

  @Override
  public long insert(UserGoldExperienceDO userGoldExp) throws DataAccessException {
    return (long) getSqlMapClientTemplate().insert("RM-USER-GOLD-EXPERIENCE-INSERT", userGoldExp);
  }

  @Override
  public void update(UserGoldExperienceDO userGoldExp) throws DataAccessException {
    getSqlMapClientTemplate().update("RM-USER-GOLD-EXPERIENCE-UPDATE", userGoldExp);
  }

  @Override
  public List<UserGoldExperienceDO> query(UserGoldExperienceDO userGoldExp) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("RM-USER-GOLD-EXPERIENCE-SELECT-LIST", userGoldExp);
  }
	
	@Override
	public UserGoldExperienceDO getById(int id) throws DataAccessException {
		return (UserGoldExperienceDO) getSqlMapClientTemplate().queryForObject(
			"RM-USER-GOLD-EXPERIENCE-SELECT-BYID", id);
	}

  @Override
  public BigDecimal countByUserId(long userId) throws DataAccessException {
    return (BigDecimal) getSqlMapClientTemplate().queryForObject(
        "RM-USER-GOLD-EXPERIENCE-SELECT-COUNT", userId);
  }


}
