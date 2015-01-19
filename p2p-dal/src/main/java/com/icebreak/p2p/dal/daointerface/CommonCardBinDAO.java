package com.icebreak.p2p.dal.daointerface;

import com.icebreak.p2p.dal.dataobject.CommonCardBinDO;

import java.util.List;
import org.springframework.dao.DataAccessException;

 @SuppressWarnings("rawtypes") 
public interface CommonCardBinDAO {
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
    public List<CommonCardBinDO> findByCardFlag(String cardFlag) throws DataAccessException;

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
    public List<Integer> getAllFlagLen() throws DataAccessException;

     /**
      *
      * @param cardNo
      * @return
      * @throws DataAccessException
      */
     public CommonCardBinDO findByCardNo(String cardNo, String bankCode) throws  DataAccessException;

}
