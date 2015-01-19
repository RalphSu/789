package com.icebreak.p2p.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.icebreak.p2p.dataobject.User;
import com.icebreak.p2p.dataobject.UserPasswordExtend;

public interface UserDao {
	/**
	 * 根据用户ID获取用户
	 * @return
	 */
  public User getByUserId(long id);
  /**
   * 根据用户名获取用户
   * @param userName
   * @return
   */
  public User getByUserName(String userName);
  /**
   * 添加用户
   * @param user
   */
  public void add(User user);
  /**
   * 根据用户ID删除用户
   * @param id
   * @return
   */
  public int deleteByUserId(long id);
  /**
   * 根据用户名删除用户
   * @return                                                                                                                                                                                                                                                                                                           
   */
  public int deleteByUserName(String userName);
  /**
   * 是否存在用户名
   * @param userName
   * @return
   */
  public int exists(String userName);
  /**
   * 根据状态获取用户
   * @param status
   * @return
   */
  public List<User> getByStatus(String status);
  /**
   * 新增用户密码扩展
   * @param userPasswordExten
   */
  public void addUserPasswordExtend(UserPasswordExtend userPasswordExten) throws DataAccessException;
  /**
   * 更新用户密码扩展
   * @param userPasswordExten
   * @return
   */
  public int updateUserPasswordExtend(UserPasswordExtend userPasswordExten) throws DataAccessException;
  /**
   * 验证密码
 * @param conditions
 * @return
 * @throws DataAccessException
 */
public long validationUserPassword(Map<String,Object> conditions) throws DataAccessException;
/**
 * 根据用户信息类型查询是否设置密码
 * @return
 */
public long countUserPwdExistByCondition(Map<String, Object> conditions);
/**
 * 根据用户信息类型查询密码记录
 * @return
 */
public List<UserPasswordExtend> getUserEXPwdBYconditions(Map<String, Object> conditions);
}
