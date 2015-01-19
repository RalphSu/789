package com.icebreak.p2p.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.icebreak.p2p.dal.daointerface.CommonAttachmentDAO;
import com.icebreak.p2p.dal.dataobject.CommonAttachmentDO;


import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
	
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisCommonAttachmentDAO extends SqlMapClientDaoSupport implements CommonAttachmentDAO {
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
    public long insert(CommonAttachmentDO commonAttachment) throws DataAccessException {
    	if (commonAttachment == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-COMMON-ATTACHMENT-INSERT", commonAttachment);

        return commonAttachment.getAttachmentId();
    }

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
    public int update(CommonAttachmentDO commonAttachment) throws DataAccessException {
    	if (commonAttachment == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-COMMON-ATTACHMENT-UPDATE", commonAttachment);
    }

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
    public int updateStatusAllSame(String checkStatus, String bizNo, String moduleType) throws DataAccessException {
        Map param = new HashMap();

        param.put("checkStatus", checkStatus);
        param.put("bizNo", bizNo);
        param.put("moduleType", moduleType);

        return getSqlMapClientTemplate().update("MS-COMMON-ATTACHMENT-UPDATE-STATUS-ALL-SAME", param);
    }

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
    public int deleteById(long attachmentId) throws DataAccessException {
        Long param = new Long(attachmentId);

        return getSqlMapClientTemplate().delete("MS-COMMON-ATTACHMENT-DELETE-BY-ID", param);
    }

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
    public int deleteByPicpath(String filePhysicalPath) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-COMMON-ATTACHMENT-DELETE-BY-PICPATH", filePhysicalPath);
    }

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
    public int deleteByPicpathNotCheck(String filePhysicalPath) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-COMMON-ATTACHMENT-DELETE-BY-PICPATH-NOT-CHECK", filePhysicalPath);
    }

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
    public int deleteByBizNoModuleType(String bizNo, String moduleType) throws DataAccessException {
        Map param = new HashMap();

        param.put("bizNo", bizNo);
        param.put("moduleType", moduleType);

        return getSqlMapClientTemplate().delete("MS-COMMON-ATTACHMENT-DELETE-BY-BIZ-NO-MODULE-TYPE", param);
    }

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
    public int deleteByBizNoModuleTypeNotCheck(String bizNo, String moduleType) throws DataAccessException {
        Map param = new HashMap();

        param.put("bizNo", bizNo);
        param.put("moduleType", moduleType);

        return getSqlMapClientTemplate().delete("MS-COMMON-ATTACHMENT-DELETE-BY-BIZ-NO-MODULE-TYPE-NOT-CHECK", param);
    }

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
    public CommonAttachmentDO findById(long attachmentId) throws DataAccessException {
        Long param = new Long(attachmentId);

        return (CommonAttachmentDO) getSqlMapClientTemplate().queryForObject("MS-COMMON-ATTACHMENT-FIND-BY-ID", param);

    }

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
    public List<CommonAttachmentDO> findByPicpath(String filePhysicalPath) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-COMMON-ATTACHMENT-FIND-BY-PICPATH", filePhysicalPath);

    }

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
    public List<CommonAttachmentDO> findByPicpathNotCheck(String filePhysicalPath) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-COMMON-ATTACHMENT-FIND-BY-PICPATH-NOT-CHECK", filePhysicalPath);

    }

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
    public long findCountByPicpathCheckPass(String filePhysicalPath) throws DataAccessException {

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-COMMON-ATTACHMENT-FIND-COUNT-BY-PICPATH-CHECK-PASS", filePhysicalPath);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

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
    public List<CommonAttachmentDO> findByBizNoModuleType(CommonAttachmentDO commonAttachment) throws DataAccessException {
    	if (commonAttachment == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


        return getSqlMapClientTemplate().queryForList("MS-COMMON-ATTACHMENT-FIND-BY-BIZ-NO-MODULE-TYPE", commonAttachment);

    }

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
    public List<CommonAttachmentDO> findByManyModuleType(List moduleTypeList, String bizNo) throws DataAccessException {
        Map param = new HashMap();

        param.put("moduleTypeList", moduleTypeList);
        param.put("bizNo", bizNo);

        return getSqlMapClientTemplate().queryForList("MS-COMMON-ATTACHMENT-FIND-BY-MANY-MODULE-TYPE", param);

    }

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
    public long searchDataCount(CommonAttachmentDO commonAttachment) throws DataAccessException {
    	if (commonAttachment == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-COMMON-ATTACHMENT-SEARCH-DATA-COUNT", commonAttachment);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}
