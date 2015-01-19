package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.CommonCardBinDAO;
import com.icebreak.p2p.dal.dataobject.CommonCardBinDO;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

@SuppressWarnings("unchecked")

public class IbatisCommonCardBinDAO extends SqlMapClientDaoSupport implements CommonCardBinDAO {
	/**
	 *  Query DB table <tt>common_card_bin</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from common_card_bin where (card_flag = ?)</tt>
	 *
	 *	@param cardFlag
	 *	@return List<CommonCardBinDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonCardBinDO> findByCardFlag(String cardFlag) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-COMMON-CARD-BIN-FIND-BY-CARD-FLAG", cardFlag);

    }

	/**
	 *  Query DB table <tt>common_card_bin</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select card_flag_len from common_card_bin group by card_flag_len order by card_flag_len DESC</tt>
	 *
	 *	@return List<Integer>
	 *	@throws DataAccessException
	 */	 
    public List<Integer> getAllFlagLen() throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-COMMON-CARD-BIN-GET-ALL-FLAG-LEN", null);

    }

    /**
     *
     * @param cardNo
     * @return
     * @throws DataAccessException
     */
    public CommonCardBinDO findByCardNo(String cardNo, String bankCode) throws  DataAccessException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("cardNum", cardNo);
        params.put("bankId", bankCode);
        return (CommonCardBinDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-CARD-BIN-GET-BY-CARD-NO", params);
    }

}
