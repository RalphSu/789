package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.InstitutionsInfoDO;


public interface InstitutionsInfoDAO {
	/**
	 *  Insert one <tt>InstitutionsInfoDO</tt> object to DB table <tt>institutions_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into institutions_info(user_base_id,enterprise_name,organization_code,tax_registration_no,business_license_no,business_license_province,business_license_city,commonly_used_address,business_period,legal_representative_name,legal_representative_card_no,business_license_path,business_license_cachet_path,cert_front_path,cert_back_path,opening_license_path,bank_open_name,bank_card_no,bank_key,bank_province,bank_city,bank_address,institutions_in_code,institutions_them_roughly,referees) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param institutionsInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(InstitutionsInfoDO institutionsInfo) throws DataAccessException;

	/**
	 *  Update DB table <tt>institutions_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update institutions_info set user_base_id=?, enterprise_name=?, organization_code=?, tax_registration_no=?, business_license_no=?, business_license_province=?, business_license_city=?, commonly_used_address=?, business_period=?, legal_representative_name=?, legal_representative_card_no=?, business_license_path=?, business_license_cachet_path=?, cert_front_path=?, cert_back_path=?, opening_license_path=?, bank_open_name=?, bank_card_no=?, bank_key=?, bank_province=?, bank_city=?, bank_address=?, institutions_in_code=?, institutions_them_roughly=?, referees=? where (id = ?)</tt>
	 *
	 *	@param institutionsInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(InstitutionsInfoDO institutionsInfo) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>institutions_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from institutions_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int delete(long id) throws DataAccessException;
    
	/***
	 * 根据条件查询机构信息行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryCount(Map<String, Object> condition) throws DataAccessException;

	/***
	 * 根据条件查询机构信息集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 */
	public List<InstitutionsInfoDO> queryList(Map<String, Object> condition) throws DataAccessException;
	
	
	/***
	 * 根据条件查询机构信息UserBaseId
	 * 
	 * @param userBaseId
	 * @return PersonalInfoDO
	 * @throws DataAccessException
	 */
	public InstitutionsInfoDO query(String userBaseId) throws DataAccessException;
	
	/***
	 * 根据条件查询对应机构信息集合
	 * 
	 * @param roleId
	 * @return list
	 * @throws DataAccessException
	 */
	public List<InstitutionsInfoDO> queryListeEnterpriseNameAndUserId(long roleId) throws DataAccessException;
	
	
	/***
	 * 根据条件查询机构信息UserId
	 * 
	 * @param userId
	 * @return PersonalInfoDO
	 * @throws DataAccessException
	 */
	public InstitutionsInfoDO queryByUserId(long userId) throws DataAccessException;
	
	
	/***
	 * 根据机构名称，角色，查询机构信息集合
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryByRoleCount(Map<String, Object> condition) throws DataAccessException;
	
	/***
	 * 根据机构名称，角色，查询机构信息集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 */
	public List<InstitutionsInfoDO> queryByRole(Map<String, Object> condition) throws DataAccessException;
	/***
	 * 投资者管理查询行数
	 * 
	 * @param condition
	 * @return long
	 * @throws DataAccessException
	 */
	public long queryChildrenCountByCondition(Map<String, Object> condition);
	/***
	 * 投资者管理查询集合
	 * 
	 * @param condition
	 * @return list
	 * @throws DataAccessException
	 */
	public List<InstitutionsInfoDO> queryChildrenListByCondition(
			Map<String, Object> condition);

}
