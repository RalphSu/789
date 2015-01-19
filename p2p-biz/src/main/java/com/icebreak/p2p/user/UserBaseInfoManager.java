package com.icebreak.p2p.user;

import java.util.List;
import java.util.Map;

import com.yiji.openapi.sdk.message.yzz.YzzUserSpecialRegisterResponse;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserLoginLog;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.user.result.UserBaseReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;

public interface UserBaseInfoManager {
	/***
	 * 添加用户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUserBaseInfo(UserBaseInfoDO userBaseInfo) throws Exception;
	
	/***
	 * 修改用户基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateUserBaseInfo(UserBaseInfoDO userBaseInfo) throws Exception;
	
	/***
	 * 验证登录 密码
	 * 
	 * @param userBaseId
	 * @return logPassword
	 * @throws Exception
	 */
	public UserBaseReturnEnum validationLogPassword(String userBaseId, String logPassword)
																							throws Exception;
	
	/***
	 * 修改登录 密码
	 * 
	 * @param userBaseId
	 * @return logPassword
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateLogPassword(String userBaseId, String logPassword)
																						throws Exception;

	/***
	 * 修改登录密码
	 * @param userName
	 * @param logPassword
	 * @param oldPwd
	 * @return
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateLogPasswordByUserNameAndOldPwd(String userName, String logPassword,String oldPwd)
			throws Exception;
	
	/***
	 * 验证支付密码
	 * 
	 * @param userBaseId
	 * @return PayPassword
	 * @throws Exception
	 */
	
	public UserBaseReturnEnum validationPayPassword(String userBaseId, String payPassword)
																							throws Exception;
	
	/***
	 * 修改支付密码
	 * 
	 * @param userBaseId
	 * @return PayPassword
	 * @throws Exception
	 */
	public UserBaseReturnEnum updatePayPassword(String userBaseId, String payPassword)
																						throws Exception;
	
	/***
	 * 修改登录密码和支付密码
	 * 
	 * @param userBaseId
	 * @return logPassword
	 * @return PayPassword
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateLogPasswordAndPayPassword(String userBaseId,
																String logPassword,
																String payPassword)
																					throws Exception;
	
	/***
	 * 修改用户状态
	 * 
	 * @param userBaseId
	 * @return state
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateState(String userBaseId, String state) throws Exception;
	
	/***
	 * 修改资金账户ID
	 * 
	 * @param userBaseId
	 * @return accountId
	 * @throws Exception
	 */
	public UserBaseReturnEnum updateAccountId(String userBaseId, String accountId) throws Exception;
	
	/***
	 * 验证用户名是否存在
	 * 
	 * @param userName
	 * @throws Exception
	 */
	
	public UserBaseReturnEnum validationUserName(String userName) throws Exception;
	
	/***
	 * 根据用户名和角色ID
	 * 
	 * @param condition
	 * @throws DataAccessException
	 */
	public UserBaseInfoDO queryByUserName(String userName, long roleId) throws Exception;
	
	/***
	 * 根据用户名UserBaseId
	 * 
	 * @param userBaseId
	 * @throws DataAccessException
	 */
	public UserBaseInfoDO queryByUserBaseId(String userBaseId) throws Exception;
	
	/***
	 * 根据用户名MD5(UserBaseId)
	 * 
	 * @param userBaseId
	 * @throws DataAccessException
	 */
	public UserBaseInfoDO queryByMD5UserBaseId(String MD5UserBaseId) throws Exception;
	
	/***
	 * 获取对应的角色集合
	 * 
	 * @param type
	 * @throws DataAccessException
	 */
	public List<UserBaseInfoDO> queryByType(String type, String userId) throws Exception;
	
	/**
	 * 根据号段，用户名,机构名,用户类型，获取userId
	 * */
	public long queryUserId(String identityName, String userName, String realName, String type);
	
	/**
	 * 根据号段名称获取userBaseId
	 * */
	public String queryUserBaseId(String identityName, String type);
	
	/**
	 * 根据用户名和角色查询
	 */
	public UserBaseInfoDO getRealNameByUserName(String name);
	
	/**
	 * 获取role条数
	 * @param params
	 * @return
	 */
	public long getRolesByUserIdCount(Map<String, Object> params);
	
	/**
	 * 获取role记录
	 * @param params
	 * @return
	 */
	public List<Role> getRolesByUserId(Map<String, Object> params);
	
	/**
	 * 获取交易
	 * @param conditions
	 * @return
	 */
	public List<TradeQueryDetail> getTradeDetailByConditions(Map<String, Object> conditions);
	
	/**
	 * 利用推荐人编号查找推荐人信息是否可用
	 * @param referees
	 * @return
	 */
	public UserBaseReturnEnum validationReferees(String referees);
	
	/**
	 * 查询所有用户
	 * @param queryConditions
	 * @param pageParam
	 * @return
	 */
	public Page<UserBaseInfoDO> allUserInfo(QueryConditions queryConditions, PageParam pageParam);
	
	/***
	 * 根据用户baseId(UserBaseId)设置支付密码
	 * 
	 * @param userBaseId
	 * @throws DataAccessException
	 */
	public UserBaseReturnEnum resetPayPassword(String userBaseId) throws Exception;
	
	/**
	 * 根据userId查询UserBaseID
	 * @param userId
	 * @return
	 */
	public String getBaseId(long userId);
	
	/****************************** 调用openapi接口 ******************************************/
	
	/**
	 * 验证银行卡信息
	 * @param cardInfo
	 * @return
	 */
	public JSONObject validationBankCardByOpenApi(OpeningBankQueryOrder cardInfo);
	
	/**
	 * openAPI注册
	 * @param personalInfo
	 * @param userBaseInfo
	 * @return
	 */
	public JSONObject registerByOpenApi(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo);
	
	public UserBaseInfoDO queryByUserId(long userId);
	
	/**
	 * 手机或邮箱统计
	 * @param map
	 * @return
	 */
	public long countMailOrMobileByRole(Map<String, Object> map);
	
	/**
	 * 根据AccountId获取用户数据信息
	 * @param userId
	 * @return
	 */
	public UserBaseInfoDO queryByAccountId(String accountId);
	
	/**
	 * 
	 * @param userLoginLog
	 */
	public void addUserLoginLog(UserLoginLog userLoginLog) throws Exception;
	
	/**
	 * 查询用户上次登录地址信息
	 * @param userId
	 */
	public List<UserLoginLog> queryUserLoginLogByUserId(long userId);
	
	/**
	 * 锁定到期解锁用户
	 */
	public void executeUnlockUserJob();
	
	/**
	 * 查询其下输用户量
	 */
	public long queryChildrenCountByCondition(Map<String, Object> condition);
	
	/**
	 * 查询其下输用户数据
	 */
	public List<UserBaseInfoDO> queryChildrenListByCondition(Map<String, Object> condition);
	
	
	public String getCertNoByUserId(long userId);

	public YzzUserSpecialRegisterResponse doRegYJFAccount(PersonalInfoDO personalInfo,
														  UserBaseInfoDO userBaseInfo);
}
