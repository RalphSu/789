package com.icebreak.p2p.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.daointerface.UserBaseInfoDAO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;


public class IbatisUserBaseInfoDAO extends SqlMapClientDaoSupport implements UserBaseInfoDAO {
	/**
	 *  Insert one <tt>UserBaseDO</tt> object to DB table <tt>user_base</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into user_base(user_base_id,user_id,user_name,log_password,pay_password,account_id,account_name,mobile,mobile_binding,mail,mail_binding,type,state,row_add_time,row_update_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param userBase
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public String insert(UserBaseInfoDO userBaseInfo) throws DataAccessException {
    	if (userBaseInfo == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-USER-BASE-INSERT", userBaseInfo);

        return userBaseInfo.getUserBaseId();
    }

	/**
	 *  Update DB table <tt>user_base</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update user_base set user_base_id=?, user_id=?, user_name=?, log_password=?, pay_password=?, account_id=?, account_name=?, mobile=?, mobile_binding=?, mail=?, mail_binding=?, type=?, state=? where (user_base_id = ?)</tt>
	 *
	 *	@param userBase
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(UserBaseInfoDO userBaseInfo) throws DataAccessException {
    	if (userBaseInfo == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE", userBaseInfo);
    }

	/**
	 *  Delete records from DB table <tt>user_base</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from user_base where (user_base_id = ?)</tt>
	 *
	 *	@param userBaseId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int delete(String userBaseId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-USER-BASE-DELETE", userBaseId);
    }

    
	@Override
	public long validationLogPassword(Map<String, Object> condition) throws DataAccessException {
		return  (Long) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-VALIDATION_LOG_PASSWORD", condition);
	}

	@Override
	public int updateLogPassword(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE_LOG_PASSWORD", condition);
	}

	@Override
	public int updateLogPasswordByUserNameAndOldPwd(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE-LOGIN-PWD-BY-USERNAME", condition);
	}

	@Override
	public long validationPayPassword(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-VALIDATION_PAY_PASSWORD", condition);
	}
	
	@Override
	public int updatePayPassword(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE_PAY_PASSWORD", condition);
	}

	@Override
	public int updateLogPasswordAndPayPassword(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE_LOG_PASSWORD_AND_PAY_PASSWORD", condition);
	}

	@Override
	public int updateState(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE_STATE", condition);
	}

	@Override
	public int updateAccountId(Map<String, Object> condition) throws DataAccessException {
		return getSqlMapClientTemplate().update("MS-USER-BASE-UPDATE_ACCOUNT_ID", condition);
	}

	@Override
	public long validationUserName(Map<String, Object> condition) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-VALIDATION_USER_NAME", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryByUserName(Map<String, Object> condition)throws DataAccessException {
		return  (List<UserBaseInfoDO>)getSqlMapClientTemplate().queryForList("MS-USER-BASE-INFO-QUERY_USER_NAME", condition);
	}
	
	@Override
	public UserBaseInfoDO queryByUserBaseId(String userBaseId)throws DataAccessException {
		return (UserBaseInfoDO) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-QUERY_USER_BASE_ID", userBaseId);
	}

	@Override
	public UserBaseInfoDO login(String userName, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("password", password);
		return (UserBaseInfoDO) getSqlMapClientTemplate().queryForObject("USER-LOGIN", params);
	}

	@Override
	public UserBaseInfoDO queryByMD5UserBaseId(String MD5UserBaseId) throws DataAccessException {
		return (UserBaseInfoDO) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-QUERY_MD5_USER_BASE_ID", MD5UserBaseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryByType(Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList("MS-USER-BASE-INFO-QUERY_BY_TYPE", condition);
	}
	@Override
	public long queryUserId(Map<String, Object> condition) {
		List<Long> list = getSqlMapClientTemplate().queryForList("MS-USER-BASE-INFO-QUERY_USERID", condition);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return 0;
	}

	@Override
	public String queryUserBaseId(Map<String, Object> condition) {
		return (String) getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-QUERY_USERBASEID", condition);
	}

	@Override
	public UserBaseInfoDO queryRealname(Map<String, Object> condition)
			throws DataAccessException {

		return (UserBaseInfoDO)getSqlMapClientTemplate().queryForObject("MS-USER-BASE-INFO-QUERY_REAL_NAME", condition);
	}

	@Override
	public long queryAllUserInfoCount(Map<String, Object> condition) {
		return  (Long) getSqlMapClientTemplate().queryForObject("MS-USER-INFO-QUERY_COUNT", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryAllUserInfoList(
			Map<String, Object> condition) {
		return getSqlMapClientTemplate().queryForList("MS-USER-INFO-QUERY_LIST", condition);
	}

	@Override
	public UserBaseInfoDO queryByUserId(long userId) {

		return (UserBaseInfoDO) getSqlMapClientTemplate().queryForObject("MS-USER-INFO-QUERY_BY_USERID", userId);
	}

	@Override
	public long countMailOrMobileByRole(Map<String, Object> map) {

		return (Long) getSqlMapClientTemplate().queryForObject("MS-USER-INFO-MOBILE_EMAIL_ROLR_COUNT", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryLockedUserList() {
		return getSqlMapClientTemplate().queryForList("MS-USER-INFO-QUERY_LOCKED_LIST");
	}

	@Override
	public long queryChildrenCountByCondition(Map<String, Object> condition) {
		
		return  (Long) getSqlMapClientTemplate().queryForObject("MS-USERBASE-INFO-QUERY_COUNT_INVESTOR", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryChildrenListByCondition(
			Map<String, Object> condition) {
		
		return getSqlMapClientTemplate().queryForList("MS-USERBASE-INFO-QUERY_COUNT_INVESTOR_LIST", condition);
	}

	@Override
	public long queryUserInfoCountByParams(Map<String, Object> conditions) {
		
		return (Long) getSqlMapClientTemplate().queryForObject("MS-USER-INFO-STATISTICS", conditions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBaseInfoDO> queryUserInfoListByParams(
			Map<String, Object> conditions) {
		
		return getSqlMapClientTemplate().queryForList("MS-USER-INFO-STATISTICS-LISTS", conditions);
	}
}
