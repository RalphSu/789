package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.common.services.LoanTypeService;
import com.icebreak.p2p.daointerface.LoanTypeDao;
import com.icebreak.p2p.dataobject.LoanTypeDO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by asdfasdfa on 2014/12/9.
 */
@Service("loanTypeService")
public class LoanTypeServiceImpl implements LoanTypeService {

  @Resource
  private LoanTypeDao loanTypeDao;

  @Override
  public void insert(LoanTypeDO loanTypeDO) throws DataAccessException {
    if(null == loanTypeDO) {
      throw new IllegalArgumentException("新增参数不能为空");
    }
    loanTypeDao.insert(loanTypeDO);
  }

  @Override
  public void update(LoanTypeDO loanTypeDO) throws DataAccessException {
    if(null == loanTypeDO) {
      throw new IllegalArgumentException("修改参数不能为空");
    }
    loanTypeDao.update(loanTypeDO);
  }

  @Override
  public List<LoanTypeDO> queryList(LoanTypeDO loanTypeDO) throws DataAccessException {
    return loanTypeDao.queryList(loanTypeDO);
  }

  @Override
  public void deleteById(int id) throws DataAccessException {
    loanTypeDao.deleteById(id);
  }
}
