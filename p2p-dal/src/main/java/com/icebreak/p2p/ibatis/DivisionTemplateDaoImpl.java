package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.DivisionTemplateDao;
import com.icebreak.p2p.daointerface.IdentityObtainer;
import com.icebreak.p2p.dataobject.DivisionTemplate;

public class DivisionTemplateDaoImpl extends SqlMapClientDaoSupport implements
		DivisionTemplateDao {
	
	private IdentityObtainer identityObtainer;

	public IdentityObtainer getIdentityObtainer() {
		return identityObtainer;
	}

	public void setIdentityObtainer(IdentityObtainer identityObtainer) {
		this.identityObtainer = identityObtainer;
	}

	@Override
	public void addDivisionTemplate(DivisionTemplate template) {
		getSqlMapClientTemplate().insert("DIVISIONTEMPLATE-ADD", template);
		template.setId(identityObtainer.getPrimaryKey());
	}

	@Override
	public int deleteByTemplateId(long templateId) {
		return getSqlMapClientTemplate().delete("DIVISIONTEMPLATE-DELETE", templateId);
	}

	@Override
	public int modifyDivisionTemplate(DivisionTemplate template) {
		return getSqlMapClientTemplate().update("DIVISIONTEMPLATE-UPDATE", template);
	}

	@Override
	public DivisionTemplate getByTemplateId(long templateId) {
		return (DivisionTemplate)getSqlMapClientTemplate().queryForObject("DIVISIONTEMPLATE-GETBYTEMPLATE", templateId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DivisionTemplate> getDivisionTemplatesByConditions(Map<String, Object> params) {
		return (List<DivisionTemplate>)getSqlMapClientTemplate().queryForList("DIVISIONTEMPLATE-GETBYCONDITIONS", params);
	}

	@Override
	public long getDivisionTemplatesByConditionsCount(Map<String, Object> params) {
		return (Long)getSqlMapClientTemplate().queryForObject("DIVISIONTEMPLATE-GETBYCONDITIONSCOUNT", params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DivisionTemplate> getDivisionTemplatesByPhase(String phase){
		return (List<DivisionTemplate>)getSqlMapClientTemplate().queryForList("DIVISIONTEMPLATE-GETBYPHASE", phase);
	}


    public int changeStatus(String templateStatus, long divisionTemplateId) throws DataAccessException {
        Map param = new HashMap();

        param.put("templateStatus", templateStatus);
        param.put("divisionTemplateId", new Long(divisionTemplateId));

        return getSqlMapClientTemplate().update("MS-DIVISION-TEMPLATE-CHANGE-STATUS", param);
    }


    public long isUseByDivisionTemplateId(long investTemplateId,long repayTemplateId) throws DataAccessException {

        Map param = new HashMap();

        param.put("investTemplateId", new Long(investTemplateId));
        param.put("repayTemplateId", new Long(repayTemplateId));

        Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-DIVISION-TEMPLATE-TRADE-IS-USE-BY-DIVISION-TEMPLATE-ID", param);

        if (retObj == null) {
            return 0;
        } else {
            return retObj.longValue();
        }

    }





}
