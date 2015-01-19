package com.icebreak.p2p.ibatis;

import com.icebreak.p2p.daointerface.SignCardInfoDao;
import com.icebreak.p2p.dataobject.SignCardInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by on 2014/11/20.
 */
public class SignCardInfoDaoImpl extends SqlMapClientDaoSupport implements SignCardInfoDao {
    @Override
    public void add(SignCardInfo signCardInfo) throws DataAccessException {
        getSqlMapClientTemplate().insert("RM-SIGN-CARD-INFO-INSERT", signCardInfo);
    }

    @Override
    public void delete(String pactNo) throws DataAccessException {
        getSqlMapClientTemplate().delete("RM-SIGN-CARD-INFO-DELETE-BY-PACT-NO", pactNo);
    }

    @Override
    public SignCardInfo queryByPactNo(String pactNo) throws DataAccessException {
        return (SignCardInfo) getSqlMapClientTemplate().queryForObject("RM-SIGN-CARD-INFO-QUERY-BY-PACT-NO", pactNo);
    }

    @Override
    public List<SignCardInfo> queryList(SignCardInfo signCardInfo) throws DataAccessException {
        return getSqlMapClientTemplate().queryForList("SIGN-CARD-INFO-QUERY-DYNAMIC", signCardInfo);
    }

    @Override
    public SignCardInfo queryOnly(String cardName, String certNo, String cardNo, String userId, String signType) throws DataAccessException {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardName", cardName);
        param.put("certNo", certNo);
        param.put("cardNo", cardNo);
        param.put("userId", userId);
        param.put("signType", signType);
        return (SignCardInfo) getSqlMapClientTemplate().queryForObject("RM-SIGN-CARD-INFO-QUERY-BY-ONLY", param);
    }
}
