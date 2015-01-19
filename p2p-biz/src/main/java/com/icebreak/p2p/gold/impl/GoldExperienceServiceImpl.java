package com.icebreak.p2p.gold.impl;

import com.icebreak.p2p.daointerface.GoldExperienceDao;
import com.icebreak.p2p.dataobject.GoldExperienceDO;
import com.icebreak.p2p.gold.GoldExperienceService;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
@Service("goldExperienceService")
public class GoldExperienceServiceImpl implements GoldExperienceService {

  @Resource
  private GoldExperienceDao goldExperienceDao;

  @Override
  public long addGoldExp(GoldExperienceDO goldExp) {
    if(null == goldExp) {
      throw new IllegalArgumentException("新增数据不能为空");
    }
    return goldExperienceDao.insert(goldExp);
  }

  @Override
  public long updateGoldExp(GoldExperienceDO goldExp) {
    if(null == goldExp) {
      throw new IllegalArgumentException("更新数据不能为空");
    }
    return goldExperienceDao.update(goldExp);
  }

  @Override
  public GoldExperienceDO query(GoldExperienceDO goldExp) {
    if(null == goldExp) {
      throw new IllegalArgumentException("查询参数不能为空");
    }
    return goldExperienceDao.queryGoldExp(goldExp);
  }

  @Override
  public Page<GoldExperienceDO> queryList(GoldExperienceDO goldExp, PageParam pageParam) {
    if(null == pageParam) {
      throw new IllegalArgumentException("查询条件不能为空");
    }
    Map<String, Object> condition = new HashMap<String, Object>();
    condition.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
    condition.put("pageSize", pageParam.getPageSize());
    if(null != goldExp) {
      condition.put("type", goldExp.getActivityType());
      condition.put("status", goldExp.getStatus());
      condition.put("startTime", goldExp.getStartTime());
      condition.put("endTime", goldExp.getEndTime());
    }
    long countAll = goldExperienceDao.countAll(condition);
    List<GoldExperienceDO> list = goldExperienceDao.queryList(condition);
    int start = PageParamUtil.startValue((int) countAll, pageParam.getPageSize(), pageParam.getPageNo());
    return new Page<GoldExperienceDO>(start, countAll, pageParam.getPageSize(), list);
  }

  public GoldExperienceDO queryById(long id) {
    return goldExperienceDao.queryById(id);
  }

  @Override
  public void deleteById(GoldExperienceDO goldExp) {
    goldExperienceDao.delete(goldExp);
  }

}
