package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.LoanTypeDO;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/9.
 */
public interface LoanTypeDao {

  public void insert(LoanTypeDO loanTypeDO) throws DataAccessException;

  public void update(LoanTypeDO loanTypeDO) throws DataAccessException;

  public List<LoanTypeDO> queryList(LoanTypeDO loanTypeDO) throws DataAccessException;

  public void deleteById(int id) throws DataAccessException;

}
