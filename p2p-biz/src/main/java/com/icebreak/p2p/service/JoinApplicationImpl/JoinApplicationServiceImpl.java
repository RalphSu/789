package com.icebreak.p2p.service.JoinApplicationImpl;

import com.icebreak.p2p.dal.daointerface.O2oJoinApplicationDAO;
import com.icebreak.p2p.dal.dataobject.O2oJoinApplicationDO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.PageParamUtil;
import com.icebreak.p2p.service.JoinApplication.JoinApplicationService;
import com.icebreak.p2p.service.JoinApplication.queryOrder.JoinApplicationOrder;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class JoinApplicationServiceImpl implements JoinApplicationService {
	@Autowired
	O2oJoinApplicationDAO joinApplicationDAO;

	@Override
	public long insert(JoinApplicationOrder o2oJoinAppliOrder)
			throws DataAccessException {
		
		O2oJoinApplicationDO applicDo = new O2oJoinApplicationDO(); 
		MiscUtil.copyPoObject(applicDo, o2oJoinAppliOrder);
		applicDo.setAge(StringUtil.isEmpty(o2oJoinAppliOrder.getUserAge())?0:Integer.valueOf(o2oJoinAppliOrder.getUserAge()));
		applicDo.setRowAddTime(new Date());
		applicDo.setRowUpdateTime(new Date());
		applicDo.setStatus("0");
		return joinApplicationDAO.insert(applicDo);
	}

	@Override
	public Page<O2oJoinApplicationDO> findByCondition(
			JoinApplicationOrder queryConditions, PageParam pageParam)
			throws DataAccessException {
		long totalSize = joinApplicationDAO.countByCondition(queryConditions, queryConditions.getStatus(), queryConditions.getContactName());
		List<O2oJoinApplicationDO> result = joinApplicationDAO.findByCondition(queryConditions, (pageParam.getPageNo() - 1) * pageParam.getPageSize(), pageParam.getPageSize(), queryConditions.getStatus(), queryConditions.getContactName());
		List<O2oJoinApplicationDO> voLists = new ArrayList<O2oJoinApplicationDO>();

		if (result != null && result.size() > 0) {
			for (O2oJoinApplicationDO info : result) {
				String Stas=info.getStatus();
				if ("0".equalsIgnoreCase(Stas)) {
					info.setStatus("未联系");
				} else if("1".equalsIgnoreCase(Stas)){
					info.setStatus("已联系");
				}else if("2".equalsIgnoreCase(Stas)){
					info.setStatus("已通过");
				}else{
					info.setStatus("已拒绝");
				}
				voLists.add(info);
			}
		}
		int start = PageParamUtil.startValue((int) totalSize, pageParam.getPageSize(),
				pageParam.getPageNo());
			Page<O2oJoinApplicationDO> page = new Page<O2oJoinApplicationDO>(start, totalSize, pageParam.getPageSize(),
				voLists);
			
	return page;
		
	}
	
	@Override
	public O2oJoinApplicationDO findById(long id) throws DataAccessException {
		return joinApplicationDAO.findById(id);
	}

	@Override
	public int deleteById(long id) throws DataAccessException {
		return joinApplicationDAO.deleteById(id);
	}

	@Override
	public int updateById(JoinApplicationOrder queryConditions)
			throws DataAccessException {
		
		return joinApplicationDAO.updateById(queryConditions);
	}
	
	@Override
	public long countByCondition(Map<String, Object> condition)
			throws DataAccessException {
		return 0;
	}
	


}
