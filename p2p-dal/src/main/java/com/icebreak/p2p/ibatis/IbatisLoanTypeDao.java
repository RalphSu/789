package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.LoanTypeDao;
import com.icebreak.p2p.dataobject.LoanTypeDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asdfasdfa on 2014/12/9.
 */
public class IbatisLoanTypeDao extends SqlMapClientDaoSupport implements LoanTypeDao {
  @Override
  public void insert(LoanTypeDO loanTypeDO) throws DataAccessException {
    getSqlMapClientTemplate().insert("RM-LOANTYPE-INSERT", loanTypeDO);
  }

  @Override
  public void update(LoanTypeDO loanTypeDO) throws DataAccessException {
    getSqlMapClientTemplate().update("RM-LOANTYPE-UPDATE", loanTypeDO);
  }

  @Override
  public List<LoanTypeDO> queryList(LoanTypeDO loanTypeDO) throws DataAccessException {
    return getSqlMapClientTemplate().queryForList("RM-LOANTYPE-SELECT-LIST", loanTypeDO);
  }

  @Override
  public void deleteById(int id) throws DataAccessException {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("id", id);
    getSqlMapClientTemplate().delete("RM-LOANTYPE-DELETE", param);
  }
}
