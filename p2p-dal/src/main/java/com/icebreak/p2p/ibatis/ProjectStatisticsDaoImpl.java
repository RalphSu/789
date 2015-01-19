package com.icebreak.p2p.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.ProjectStatisticsDao;
import com.icebreak.p2p.dataobject.ProjectStatisticsInfo;

public class ProjectStatisticsDaoImpl extends SqlMapClientDaoSupport implements ProjectStatisticsDao {

	@Override
	public long getCountsProjectQuantityStatus(Map<String,Object> condition) {
		Long counts = 0L;
		if("year".equals(condition.get("dimension").toString())){
			counts = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-STATUS", condition);
		}else if("month".equals(condition.get("dimension").toString())){
			counts = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-MONTH-STATUSCOUNTS", condition);
		}else if("loaner".equals(condition.get("dimension").toString())){
			counts = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-USER-STATUS-COUNTS", condition);
		}
		if(counts == null)
			counts = 0L;
		return counts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectStatisticsInfo> queryListProject(
			Map<String, Object> condition) {
		List<ProjectStatisticsInfo> lst = null;
		if("year".equals(condition.get("dimension").toString())){
			lst = (List<ProjectStatisticsInfo>)getSqlMapClientTemplate().
					queryForList("PROJECT-STATISTICS-QUERY", condition);
			this.toNumber(lst);
		}else if("month".equals(condition.get("dimension").toString())){
			lst = (List<ProjectStatisticsInfo>)getSqlMapClientTemplate().
					queryForList("PROJECT-STATISTICS-QUERY-MONTH", condition);
			this.toNumber(lst);
		}else if("loaner".equals(condition.get("dimension").toString())){
			lst = (List<ProjectStatisticsInfo>)getSqlMapClientTemplate().
					queryForList("PROJECT-STATISTICS-QUERY-USER", condition);
			this.toNumber(lst);
		}
		return lst;
	}
	
	@Override
	public long queryCountsByCondition(Map<String,Object> condition){
		Long count =0l;
		if("loaner".equals(condition.get("dimension").toString())){
			count = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-USER-COUNTS", condition);
		}else if("year".equals(condition.get("dimension").toString())){
			count = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-COUNTS", condition);
		}else if("month".equals(condition.get("dimension").toString())){
			count = (Long)getSqlMapClientTemplate().queryForObject("PROJECT-STATISTICS-QUERY-MONTH-ALLCOUNTS", condition);
		}
		if(count==null){
			count = 0l;
		}
		return count;
	}
	
	public void toNumber(List<ProjectStatisticsInfo> lst){
		for(ProjectStatisticsInfo pro : lst){
			if(pro.getPendingProjects() == null){
				pro.setPendingProjects(0L);
			}
			if(pro.getRepayProjects() == null){
				pro.setRepayProjects(0L);
			}
		}
	}

}
