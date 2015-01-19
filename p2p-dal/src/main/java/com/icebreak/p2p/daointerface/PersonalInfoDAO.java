package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.PersonalInfoDO;


public interface PersonalInfoDAO {
	/**
	 *  Insert one <tt>PersonalInfoDO</tt> object to DB table <tt>personal_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into personal_info(user_base_id,real_name,cert_no,business_period,cert_front_path,cert_back_path,bank_open_name,bank_card_no,bank_key,bank_province,bank_city,bank_address,gender,referees) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param personalInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(PersonalInfoDO personalInfo) throws DataAccessException;

	/**
	 *  Update DB table <tt>personal_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update personal_info set user_base_id=?, real_name=?, cert_no=?, business_period=?, cert_front_path=?, cert_back_path=?, bank_open_name=?, bank_card_no=?, bank_key=?, bank_province=?, bank_city=?, bank_address=?, gender=?, referees=? where (id = ?)</tt>
	 *
	 *	@param personalInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(PersonalInfoDO personalInfo) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>personal_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from personal_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int delete(long id) throws DataAccessException;
    
	/***
	 * 根据条件查询个人信息行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryCount(Map<String, Object> condition) throws DataAccessException;

	/***
	 * 根据条件查询个人信息集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 */
	public List<PersonalInfoDO> queryList(Map<String, Object> condition) throws DataAccessException;
	
	
	/***
	 * 根据条件查询个人信息
	 * 
	 * @param userBaseId
	 * @return PersonalInfoDO
	 * @throws DataAccessException
	 */
	public PersonalInfoDO query(String userBaseId) throws DataAccessException;
	
	
	/***
	 * 投资者管理查询行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryChildrenCountByCondition(Map<String, Object> condition) throws DataAccessException;

	/***
	 * 投资者管理查询集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 */
	public List<PersonalInfoDO> queryChildrenListByCondition(Map<String, Object> condition) throws DataAccessException;
	/**
	 * 根据资金账户查询证件号
	 * @param accountId
	 * @return
	 */
	public String getCertNoByAccountId(String accountId);
	/**
	 * 查询所有未通过实名认证用户(个人、机构)
	 * @param conditions
	 * @return
	 * @throws DataAccessException
	 */
	public List<PersonalInfoDO> queryAllByConditions(Map<String, Object> conditions)throws DataAccessException;
	/**
	 * 查询所有数量
	 * @param condition
	 * @return
	 * @throws DataAccessException
	 */
	public long queryAllCountByConditions(Map<String, Object> conditions)throws DataAccessException;
	/**
	 * 检查该角色下面的身份证是否已存在
	 * @param certNo
	 * @param roleCode
	 * @return
	 */
	public long countCertNoByRole(Map<String,Object > conditions);
	

}
