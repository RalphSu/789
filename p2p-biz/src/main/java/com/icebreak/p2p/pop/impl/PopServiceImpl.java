package com.icebreak.p2p.pop.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.daointerface.PopInfoDao;
import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.pop.IPopService;

@Service
public class PopServiceImpl implements IPopService{
	@Autowired
	PopInfoDao popInfoDao;
	@Override
	public void addNotice(PopInfoDO info) {
		popInfoDao.addNotice(info);
	}
	@Override
	public Page<PopInfoDO> getPageByConditions(PageParam pageParam,
			Map<String, Object> conditions) {
		conditions.put("limitStart", (pageParam.getPageNo() - 1) * pageParam.getPageSize());
		conditions.put("pageSize", pageParam.getPageSize());
		long totalSize = popInfoDao.queryCountByCondition(conditions);
		List<PopInfoDO> result = popInfoDao.queryListByCondition(conditions);
		int start = PageParamUtil.startValue((int) totalSize,pageParam.getPageSize(), pageParam.getPageNo());
		return new Page<PopInfoDO>(start, totalSize,pageParam.getPageSize(), result);
	}
	@Override
	public PopInfoDO getByPopId(long popId) {
		PopInfoDO info = popInfoDao.getByPopId(popId);
		return info;
	}
	@Override
	public void updateNotice(PopInfoDO info) {
		popInfoDao.updateNotice(info);
	}
	@Override
	public List<PopInfoDO> getListByConditions(Map<String, Object> conditions) {
		conditions.put("limitStart", 0);
		conditions.put("pageSize", 9999);
		List<PopInfoDO> result = popInfoDao.queryListByCondition(conditions);
		return result;
	}

	@Override
	public void increaseHit(long popId){
		PopInfoDO info = popInfoDao.getByPopId(popId);
		info.setHits(info.getHits()+1);
		popInfoDao.updateNotice(info);
	}
}
