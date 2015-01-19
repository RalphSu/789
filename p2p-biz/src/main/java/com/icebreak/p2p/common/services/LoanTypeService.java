package com.icebreak.p2p.common.services;

import com.icebreak.p2p.dataobject.LoanTypeDO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/9.
 */
public interface LoanTypeService {

  public void insert(LoanTypeDO loanTypeDO);

  public void update(LoanTypeDO loanTypeDO);

  public List<LoanTypeDO> queryList(LoanTypeDO loanTypeDO);

  public void deleteById(int id);
}
