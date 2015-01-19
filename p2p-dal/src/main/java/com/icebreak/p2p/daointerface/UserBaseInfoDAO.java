package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.UserBaseInfoDO;


public interface UserBaseInfoDAO {
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
    public String insert(UserBaseInfoDO userBaseInfo) throws DataAccessException;

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
    public int update(UserBaseInfoDO userBaseInfo) throws DataAccessException;

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
    public int delete(String userBaseId) throws DataAccessException;
    
    
    
    /***
	 * 验证登录 密码
	 * 
	 * @param condition
	 * @throws DataAccessException
	 */
    public long validationLogPassword(Map<String, Object> condition) throws DataAccessException;
    
    /***
   	 * 修改登陆录密码
   	 * 
   	 * @param condition
   	 * @throws DataAccessException
   	 */
    public int updateLogPassword(Map<String, Object> condition) throws DataAccessException;

	/***
	 * 修改登陆录密码
	 *
	 * @param condition
	 * @throws DataAccessException
	 */
	public int updateLogPasswordByUserNameAndOldPwd(Map<String, Object> condition) throws DataAccessException;
    
    /***
	 * 验证支付密码
	 * 
	 * @param condition
	 * @throws DataAccessException
	 */
    
    public long validationPayPassword(Map<String, Object> condition) throws DataAccessException;
    
    /***
	 * 修改支付密码
	 * 
	 * @param condition
	 * @throws DataAccessException
	 */
    public int  updatePayPassword(Map<String, Object> condition) throws DataAccessException;
    
    /***
   	 * 修改登录密码和支付密码
   	 * 
   	 * @param condition
   	 * @throws DataAccessException
   	 */
    public int  updateLogPasswordAndPayPassword(Map<String, Object> condition) throws DataAccessException;
    
    /***
   	 * 修改用户状态
   	 * 
   	 * @param condition
   	 * @throws DataAccessException
   	 */
    public int  updateState(Map<String, Object> condition) throws DataAccessException;
    
    /***
   	 * 修改用户资金ID
   	 * 
   	 * @param condition
   	 * @throws DataAccessException
   	 */
    public int  updateAccountId(Map<String, Object> condition) throws DataAccessException;
       
    
    /***
	 * 验证用户名是否存在
	 * 
	 * @param condition
	 * @throws DataAccessException
	 */
    
    public long validationUserName(Map<String, Object> condition) throws DataAccessException;
    
    /***
   	 * 根据用户名和角色ID
   	 * 
   	 * @param condition
   	 * @throws DataAccessException
   	 */
    public List<UserBaseInfoDO> queryByUserName(Map<String, Object> condition)throws DataAccessException;
    
    public UserBaseInfoDO queryRealname(Map<String,Object> condition)throws DataAccessException;
    
    
	/***
	 * 根据用户名UserBaseId
	 * 
	 * @param userBaseId
	 * @throws DataAccessException
	 */
	public UserBaseInfoDO queryByUserBaseId(String userBaseId)throws DataAccessException;
	
	/***
	 * 根据用户名MD5(UserBaseId)
	 * 
	 * @param userBaseId
	 * @throws DataAccessException
	 */
	public UserBaseInfoDO queryByMD5UserBaseId(String MD5UserBaseId)throws DataAccessException;
	
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserBaseInfoDO login(String userName, String password);
	
	/**
	 *  获取对应的角色集合
	 * */
	public List<UserBaseInfoDO> queryByType(Map<String, Object> condition);
	/**
	 * 根据号段名称获取userId
	 * */
	public long queryUserId(Map<String, Object> condition);
	
	/**
	 * 根据号段名称获取userBaseId
	 * */
	public String queryUserBaseId(Map<String, Object> condition);
	/**
	 * 查询所有用户Count
	 * @param condition
	 * @return
	 */
	public long queryAllUserInfoCount(Map<String, Object> condition);
	/**
	 * 查询所有用户LIST
	 * @param condition
	 * @return
	 */
	public List<UserBaseInfoDO> queryAllUserInfoList(
			Map<String, Object> condition);

	public UserBaseInfoDO queryByUserId(long userId);
	/**
	 * 检查邮箱或手机
	 * @param map
	 * @return
	 */
	public long countMailOrMobileByRole(Map<String, Object> map);
	/**
	 * 获取锁定用户
	 * @param condition
	 * @return
	 */
	public List<UserBaseInfoDO> queryLockedUserList();
	/**
	 * 获取该条件下的用户数量
	 * @param condition
	 * @return
	 */
	public long queryChildrenCountByCondition(Map<String, Object> condition);
	/**
	 * 获取该条件下的用户信息
	 * @param condition
	 * @return
	 */
	public List<UserBaseInfoDO> queryChildrenListByCondition(
			Map<String, Object> condition);
	/**
	 * 统计信息查询条数
	 * @param conditions
	 * @return
	 */
	public long queryUserInfoCountByParams(Map<String, Object> conditions);
	/**
	 * 统计信息查询列表
	 * @param conditions
	 * @return
	 */
	public List<UserBaseInfoDO> queryUserInfoListByParams(
			Map<String, Object> conditions);

	
	
}
