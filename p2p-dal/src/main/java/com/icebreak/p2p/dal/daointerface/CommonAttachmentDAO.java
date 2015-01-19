package com.icebreak.p2p.dal.daointerface;

import com.icebreak.p2p.dal.dataobject.CommonAttachmentDO;

import org.springframework.dao.DataAccessException;
import java.util.List;

 @SuppressWarnings("rawtypes") 
public interface CommonAttachmentDAO {
	/**
	 *  Insert one <tt>CommonAttachmentDO</tt> object to DB table <tt>common_attachment</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into common_attachment(attachment_id,biz_no,module_type,check_status,file_name,isort,file_physical_path,request_path,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param commonAttachment
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(CommonAttachmentDO commonAttachment) throws DataAccessException;

	/**
	 *  Update DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update common_attachment set biz_no=?, module_type=?, check_status=?, file_name=?, isort=?, file_physical_path=?, request_path=? where ((attachment_id = ?) AND (biz_no = ?) AND (module_type = ?))</tt>
	 *
	 *	@param commonAttachment
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(CommonAttachmentDO commonAttachment) throws DataAccessException;

	/**
	 *  Update DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update common_attachment set check_status=? where ((biz_no = ?) AND (module_type = ?))</tt>
	 *
	 *	@param checkStatus
	 *	@param bizNo
	 *	@param moduleType
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateStatusAllSame(String checkStatus, String bizNo, String moduleType) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from common_attachment where (attachment_id = ?)</tt>
	 *
	 *	@param attachmentId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long attachmentId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from common_attachment where (file_physical_path = ?)</tt>
	 *
	 *	@param filePhysicalPath
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByPicpath(String filePhysicalPath) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from common_attachment where ((file_physical_path = ?) AND (check_status <> "CHECK_PASS"))</tt>
	 *
	 *	@param filePhysicalPath
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByPicpathNotCheck(String filePhysicalPath) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from common_attachment where ((biz_no = ?) AND (module_type = ?))</tt>
	 *
	 *	@param bizNo
	 *	@param moduleType
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByBizNoModuleType(String bizNo, String moduleType) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>common_attachment</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from common_attachment where ((biz_no = ?) AND (module_type = ?) AND (check_status <> "CHECK_PASS"))</tt>
	 *
	 *	@param bizNo
	 *	@param moduleType
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByBizNoModuleTypeNotCheck(String bizNo, String moduleType) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select attachment_id, biz_no, module_type, child_module_type, check_status, file_name, isort, file_physical_path, request_path, raw_add_time, raw_update_time from common_attachment where (attachment_id = ?)</tt>
	 *
	 *	@param attachmentId
	 *	@return CommonAttachmentDO
	 *	@throws DataAccessException
	 */	 
    public CommonAttachmentDO findById(long attachmentId) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select attachment_id, biz_no, module_type, check_status, file_name, isort, file_physical_path, request_path, raw_add_time, raw_update_time from common_attachment where (file_physical_path = ?)</tt>
	 *
	 *	@param filePhysicalPath
	 *	@return List<CommonAttachmentDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonAttachmentDO> findByPicpath(String filePhysicalPath) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select attachment_id, biz_no, module_type, check_status, file_name, isort, file_physical_path, request_path, raw_add_time, raw_update_time from common_attachment where ((file_physical_path = ?) AND (check_status <> "CHECK_PASS"))</tt>
	 *
	 *	@param filePhysicalPath
	 *	@return List<CommonAttachmentDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonAttachmentDO> findByPicpathNotCheck(String filePhysicalPath) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from common_attachment where ((file_physical_path = ?) AND (check_status = "CHECK_PASS"))</tt>
	 *
	 *	@param filePhysicalPath
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findCountByPicpathCheckPass(String filePhysicalPath) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select attachment_id, biz_no, module_type, check_status, file_name, isort, file_physical_path, request_path, raw_add_time, raw_update_time from common_attachment where ((biz_no = ?) AND (module_type = ?)) order by isort ASC</tt>
	 *
	 *	@param commonAttachment
	 *	@return List<CommonAttachmentDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonAttachmentDO> findByBizNoModuleType(CommonAttachmentDO commonAttachment) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select attachment_id, biz_no, module_type, check_status, file_name, isort, file_physical_path, request_path, raw_add_time, raw_update_time from common_attachment where (1 = 2) order by isort ASC</tt>
	 *
	 *	@param moduleTypeList
	 *	@param bizNo
	 *	@return List<CommonAttachmentDO>
	 *	@throws DataAccessException
	 */	 
    public List<CommonAttachmentDO> findByManyModuleType(List moduleTypeList, String bizNo) throws DataAccessException;

	/**
	 *  Query DB table <tt>common_attachment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from common_attachment</tt>
	 *
	 *	@param commonAttachment
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long searchDataCount(CommonAttachmentDO commonAttachment) throws DataAccessException;

}
