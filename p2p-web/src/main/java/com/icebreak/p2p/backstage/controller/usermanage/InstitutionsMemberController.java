package com.icebreak.p2p.backstage.controller.usermanage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.Role;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.dataobject.UserRelationDO;
import com.icebreak.p2p.dataobject.viewObject.PersonalInfoVO;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.user.result.UserRelationReturnEnum;
import com.icebreak.p2p.user.valueobject.QueryConditions;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.ApplicationConstant;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.p2p.ws.enums.MemberScalEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.UserTypeEnum;
import com.icebreak.p2p.ws.userManage.order.AddMemberOrder;

@Controller
@RequestMapping("backstage/userManage")
public class InstitutionsMemberController extends BaseAutowiredController {
	/** 统一页面路径 */
	private final String		vm_path			= "/backstage/userManage/";
	private final static String	JGAGENTPREFIX	= "";						//机构经纪人前缀 
	private final static String	JJRAGENTPREFIX	= "K";						//经纪人下的投资人前缀
																			
	@RequestMapping("institutions")
	public String institutions(long roleId, String enterpriseName, PageParam pageParam, Model model)
																									throws Exception {
		List<Long> roleIds = new ArrayList<Long>();
		if (roleId == 0) {
			roleIds.add(8L);
			roleIds.add(9L);
			roleIds.add(10L);
		} else {
			roleIds.add(roleId);
		}
		Page<InstitutionsInfoDO> page = institutionsInfoManager.pageByRole(roleIds, enterpriseName,
			pageParam);
		model.addAttribute("roleId", roleId);
		model.addAttribute("enterpriseName", enterpriseName);
		model.addAttribute("page", page);
		return vm_path + "institutions.vm";
	}
	
	/**
	 * 机构人员管理
	 * @throws Exception 
	 * */
	@RequestMapping("institutions/institutionsMember")
	public String institutionMember(QueryConditions queryConditions, PageParam pageParam,
									Model model) throws Exception {
		List<UserBaseInfoDO> list = userBaseInfoManager.queryByType("JG", null);
		if (queryConditions.getUserId() == 0 && list.size() > 0) {
			queryConditions.setUserId(list.get(0).getUserId());
		}
		Page<PersonalInfoVO> page = personalInfoManager.pageChildrenVO(queryConditions, pageParam);
		model.addAttribute("page", page);
		model.addAttribute("list", list);
		model.addAttribute("queryConditions", queryConditions);
		model.addAttribute("isAllBorker", sysFunctionConfigService.isAllEconomicMan());
		return vm_path + "institutionsMember.vm";
	}
	
	/**
	 * 经纪人人员管理
	 * @throws Exception 
	 * */
	@RequestMapping("institutions/personalMember")
	public String personalMember(QueryConditions queryConditions, PageParam pageParam, Model model)
																									throws Exception {
		Page<PersonalInfoVO> page = personalInfoManager.pageChildrenVO(queryConditions, pageParam);
		model.addAttribute("page", page);
		model.addAttribute("queryConditions", queryConditions);
		return vm_path + "personalMember.vm";
	}
	
	/**
	 * 机构批量关联用户 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@ResponseBody
	@RequestMapping("institutions/institutionsMemberOpen")
	public Object batchOpenAndAccount(HttpServletRequest request, HttpServletResponse response,
										ModelMap modelMap) {
		//List<InstitutionsMemberOeder> parsedInstitutionsMemberList = null;
		//List<InstitutionsMemberOeder> institutionsMemberList = null;
		String STATICFILESTEMPPATH = AppConstantsUtil.getYrdUploadFolder()
										+ "/files/institutionsMember";
		List<AddMemberOrder> parsedAddMemberOederList = null;
		List<AddMemberOrder> addMemberOederList = null;
		try {
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			try {
				fileList = fileUpload.parseRequest(request);
			} catch (FileUploadException ex) {
				logger.error("FileUploadException", ex);
				return "{\"code\":\"1\",\"resData\":\"" + "文件上传异常！" + "\"}";
			}
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					
					// 解析文件  
					name = item.getName();
					long size = item.getSize();
					String type = item.getContentType();
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 得到文件的扩展名  
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
					}
					File file = null;
					String tempPath = STATICFILESTEMPPATH;
					String savePath = tempPath + "/" + name;
					File fileDir = new File(tempPath);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}
					try {
						file = new File(savePath);
						item.write(file);
					} catch (Exception e) {
						logger.error("Exception", e);
						logger.info("批量添加机构人员文件上传发生异常，异常信息：{}", e.toString());
						return "{\"code\":\"1\",\"resData\":\"" + "文件上传异常" + "\"}";
					}
					//保留导入参数复杂类型
					//institutionsMemberList = parseExcel(file);
					addMemberOederList = parseExcel(file);
				}
			}
			//保留导入参数复杂类型
			//parsedInstitutionsMemberList = institutionsMemberOpenList(institutionsMemberList);
			parsedAddMemberOederList = addMemberSubmit(addMemberOederList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("批量添加机构人员发生异常，异常信息：{}", e.toString(), e);
			return "{\"code\":\"2\",\"resData\":\"" + "数据处理异常" + "\"}";
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("code", "0");
		//保留导入参数复杂类型
		//jsonobj.put("resData",institutionsMemberList);
		jsonobj.put("resData", parsedAddMemberOederList);
		response.setContentType("text/json");
		return jsonobj.toJSONString();
	}
	
	/**
	 * @description 解析excel文件到InstitutionsMemberOeder对象
	 * @param file
	 * @param institutionsMemberOeder
	 * @return
	 */
	public List<AddMemberOrder> parseExcel(File file) {
		//List<InstitutionsMemberOeder> institutionsMemberList=new ArrayList<InstitutionsMemberOeder>();
		//InstitutionsMemberOeder institutionsMemberOeder = null;
		List<AddMemberOrder> addMemberOederList = new ArrayList<AddMemberOrder>();
		AddMemberOrder addMemberOeder = null;
		
		try {
			InputStream is = new FileInputStream(file);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			
			// 循环工作表Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					
					//institutionsMemberOeder = new InstitutionsMemberOeder();
					addMemberOeder = new AddMemberOrder();
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					// 循环列Cell
					// 0用户名 1真实姓名 2证件号码 3手机电话 4常用电子邮箱
					// for (int cellNum = 0; cellNum <=4; cellNum++) {
					HSSFCell usercell = hssfRow.getCell(0);
					if (usercell == null) {
						continue;
					}
					//institutionsMemberOeder.setUserName(getValue(usercell));
					addMemberOeder.setGrUserName(getValue(usercell));//个人用户名
					HSSFCell realNameCell = hssfRow.getCell(1);
					if (realNameCell == null) {
						continue;
					}
					addMemberOeder.setJgUserName(getValue(realNameCell));//机构用户名
					// institutionsMemberOeder.setRealName(getValue(realNameCell));
					//                    HSSFCell certNoCell = hssfRow.getCell(2);
					//                    if (certNoCell == null) {
					//                        continue;
					//                    }
					//                    institutionsMemberOeder.setCertNo(getValue(certNoCell));
					//                    HSSFCell mobileCell = hssfRow.getCell(3);
					//                    if (mobileCell == null) {
					//                        continue;
					//                    }
					//                    institutionsMemberOeder.setMobile(getValue(mobileCell));
					//                    HSSFCell maillCell = hssfRow.getCell(4);
					//                    if (maillCell == null) {
					//                        continue;
					//                    }
					//                    institutionsMemberOeder.setMail(getValue(maillCell));
					//                    HSSFCell institutionCell = hssfRow.getCell(5);
					//                    if (institutionCell == null) {
					//                        continue;
					//                    }
					//                    institutionsMemberOeder.setInstitutionName(getValue(institutionCell));
					// institutionsMemberList.add(institutionsMemberOeder);
					addMemberOederList.add(addMemberOeder);
				}
			}
			is.close();
		} catch (Exception e) {
			logger.info("解析机构人员数据发生异常，异常信息：{}", e.toString(), e);
		}
		return addMemberOederList;
	}
	
	/**
	 * 得到Excel表中的值
	 *
	 * @param hssfCell
	 *            Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	private List<AddMemberOrder> addMemberSubmit(List<AddMemberOrder> addMemberOeders)
																						throws Exception {
		if (addMemberOeders != null) {
			for (AddMemberOrder addMemberOeder : addMemberOeders) {
				long parentId = userBaseInfoManager.queryUserId(null,
					addMemberOeder.getJgUserName(), null, null);
				long childId = userBaseInfoManager.queryUserId(null,
					addMemberOeder.getGrUserName(), null, UserTypeEnum.GR.code());
				if (parentId > 0 && childId > 0) {
					try {
						List<UserBaseInfoDO> parentJGs = userBaseInfoManager.queryByType(null,
							String.valueOf(parentId));
						UserBaseInfoDO parentJG = null;
						if (parentJGs != null && parentJGs.size() > 0) {
							parentJG = parentJGs.get(0);
						} else {
							addMemberOeder.setState("未找到该机构或者经纪人");
							continue;
						}
						String memberNo = null;
						if (UserTypeEnum.JG.code().equals(parentJG.getType())) {
							if (!checkChildIdForAddMember(childId)) {
								addMemberOeder.setState("该成员不是经纪人");
								continue;
							}
							String indentity = parentJG.getIdentityName();
							int startNo = Integer.parseInt(parentJG.getIdentityStartNo());
							int endNo = Integer.parseInt(parentJG.getIdentityEndNo());
							if (startNo == 0) {
								startNo++;
							}
							int count = 0;
							boolean availabelFlag = false;
							while (!availabelFlag) {
								int currentNo = startNo + count;

								if (currentNo > endNo) {
									addMemberOeder.setState("机构编号已满");
									continue;//编号已满
								}
								int memberScale = 0;
								if ("高级机构".equals(parentJG.getType())) {
									memberScale = MemberScalEnum.VIP.getValue();
								} else {
									memberScale = MemberScalEnum.DEFAULT.getValue();
								}

                                String sino = null;
                                if (currentNo <= 99999) {
                                    sino = StringUtils.leftPad(String.valueOf(currentNo),
                                            memberScale, "0");
                                } else {
                                    sino = currentNo + "";
                                }
                                memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接
                                Page<UserRelationDO> page = userRelationManager
                                        .getRelationsByConditions(null, null, null, memberNo);

                                if(AppConstantsUtil.getBrokerIsNumber()){
                                    memberScale = parentJG.getIdentityEndNo().length() ;
                                }


								sino = null;
								if (currentNo <= 99999) {
									sino = StringUtils.leftPad(String.valueOf(currentNo),
										memberScale, "0");
								} else {
									sino = currentNo + "";
								}
								memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接 
								Page<UserRelationDO> page2 = userRelationManager
									.getRelationsByConditions(null, null, null, memberNo);
								if ((page.getResult() != null && page.getResult().size() > 0)||(page2.getResult() != null && page2.getResult().size() > 0)) {
									count++;
								} else {
									availabelFlag = true;
									logger.info("可用经纪人编号：" + memberNo);
								}
							}
							UserRelationReturnEnum returnEnum = userRelationManager
								.insert(new UserRelationDO(parentId, childId, memberNo));
							if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
								addMemberOeder.setState("成功");
							}
						} else {
							Page<UserRelationDO> userRelationsPage = userRelationManager
								.getRelationsByConditions(null, null, parentId, null);
							if (userRelationsPage.getResult() != null
								&& userRelationsPage.getResult().size() > 0) {
								for (int i = 0; i < userRelationsPage.getResult().size(); i++) {
									List<UserBaseInfoDO> curParentJGs = userBaseInfoManager
										.queryByType(
											UserTypeEnum.JG.code(),
											String.valueOf(userRelationsPage.getResult().get(i)
												.getParentId()));
									UserBaseInfoDO curParentJG = null;
									if (curParentJGs != null && curParentJGs.size() > 0) {
										curParentJG = curParentJGs.get(0);
									} else {
										logger.info("经纪人的机构没找到，机构Id"
													+ userRelationsPage.getResult().get(i)
														.getParentId());
										continue;
									}
									int count = userRelationManager
										.countInvestorsInThisJG(curParentJG.getUserId());
									boolean availabelFlag = false;
									while (!availabelFlag) {
										count++;
										int curindex = count;
										if (String.valueOf(curindex).endsWith("4")) {
											curindex++;
										}
										
										int memberScale = 0;
										if ("高级机构".equals(curParentJG.getType())) {
											memberScale = MemberScalEnum.VIP.getValue();
										} else {
											memberScale = MemberScalEnum.DEFAULT.getValue();
										}
										String sino = null;
										if (curindex <= 99999) {
											sino = StringUtils.leftPad(String.valueOf(curindex),
												memberScale, "0");
										} else {
											sino = curindex + "";
										}
										String jgIdentity = curParentJG.getIdentityName();
										memberNo = jgIdentity + JJRAGENTPREFIX + sino;
										Page<UserRelationDO> page = userRelationManager
											.getRelationsByConditions(null, null, null, memberNo);
										if (page.getResult() != null && page.getResult().size() > 0) {
											availabelFlag = false;
										} else {
											availabelFlag = true;
											logger.info("可用投资人编号：" + memberNo);
										}
									}
									
									UserRelationReturnEnum returnEnum = userRelationManager
										.insert(new UserRelationDO(parentId, childId, memberNo));
									if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
										int countNew = userRelationManager
											.countInvestorsInThisJG(curParentJG.getUserId());
										curParentJG.setExIdentityNo(String.valueOf(countNew));
										userBaseInfoManager.updateUserBaseInfo(curParentJG);
										addMemberOeder.setState("成功");
									}
								}
							}
							
						}
					} catch (Exception e) {
						addMemberOeder.setState("失败");
						logger.error("addMemberOeder", e);
					}
					
				}
			}
		}
		return addMemberOeders;
	}
	
	@ResponseBody
	@RequestMapping("institutions/addMember")
	public Map<String, Object> addMember(HttpServletRequest request, Long parentId,
											String memberUserName, String memberRealName,
											String code) {
		logger.info("进入添加机构成员，入参：[{" + parentId + "}]，[{" + memberUserName + "}]，[{"
					+ memberRealName + "}],[{" + code + "}]");
		Map<String, Object> map = new HashMap<String, Object>();
		String validNo = null;
		UserRelationReturnEnum returnEnum = UserRelationReturnEnum.EXECUTE_FAILURE;
		try {
			long childId = userBaseInfoManager.queryUserId(null, memberUserName, memberRealName,
				"GR");
			if (childId > 0) {
				if (!checkChildIdForAddMember(childId)) {
					map.put("code", 0);
					map.put("message", "该成员不是经纪人");
					return map;
				}
				List<UserBaseInfoDO> parentJGs = userBaseInfoManager.queryByType("JG",
					String.valueOf(parentId));
				UserBaseInfoDO parentJG = null;
				if (parentJGs != null && parentJGs.size() > 0) {
					parentJG = parentJGs.get(0);
				} else {
					map.put("code", 3);
					map.put("message", "未找到该机构");
					return map;
				}
				String memberNo = null;
				String indentity = parentJG.getIdentityName();
				int startNo = Integer.parseInt(parentJG.getIdentityStartNo());
				int endNo = Integer.parseInt(parentJG.getIdentityEndNo());
				if (startNo == 0) {
					startNo++;
				}
				//int count = userRelationManager.getRelationsCountByConditions(null, parentId , null , null);
				int count = 0;
				boolean availabelFlag = false;
				while (!availabelFlag) {
					int currentNo = startNo + count;
					if (String.valueOf(currentNo).endsWith("4")) {
						currentNo++;
					}
					if (currentNo > endNo) {
						map.put("code", 2);
						map.put("message", "编号已满");
						return map;
					}
					int memberScale = 0;
					if ("高级机构".equals(parentJG.getType())) {
						memberScale = MemberScalEnum.VIP.getValue();
					} else {
						memberScale = MemberScalEnum.DEFAULT.getValue();
					}


                    String sino = null;
                    if (currentNo <= 99999) {
                        sino = StringUtils.leftPad(String.valueOf(currentNo), memberScale, "0");
                    } else {
                        sino = currentNo + "";
                    }
                    memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接
                    Page<UserRelationDO> page = userRelationManager.getRelationsByConditions(null,
                            null, null, memberNo);


                    if(AppConstantsUtil.getBrokerIsNumber()){
                        memberScale = parentJG.getIdentityEndNo().length();
                    }


                    sino = null;
					if (currentNo <= 99999) {
						sino = StringUtils.leftPad(String.valueOf(currentNo), memberScale, "0");
					} else {
						sino = currentNo + "";
					}
					memberNo = indentity + JGAGENTPREFIX + sino;//串号拼接 
					Page<UserRelationDO> page2 = userRelationManager.getRelationsByConditions(null,
						null, null, memberNo);
					if ((page.getResult() != null && page.getResult().size() > 0)||(page2.getResult() != null && page2.getResult().size()>0)) {
						count++;
					} else {
						availabelFlag = true;
						logger.info("可用经纪人编号：" + memberNo);
					}
				}
				validNo = memberNo;
				returnEnum = userRelationManager.insert(new UserRelationDO(parentId, childId,
					memberNo));
			}
			if (returnEnum == UserRelationReturnEnum.EXECUTE_SUCCESS) {
				map.put("code", 1);
				map.put("message", "添加机构成员成功");
				//发送邮件
				UserBaseInfoDO curUser = userBaseInfoManager.queryByUserName(memberUserName, 1);
				mailService.sendBrokerMail(request, SendInformation.sendBrokerMail(
					curUser.getMail(), curUser.getRealName(), validNo, 25L));
			} else {
				map.put("code", 0);
				map.put("message", "添加机构成员失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("code", 0);
			map.put("message", "添加机构成员失败");
			logger.error("结束添加机构成员，发生异常：{}", e.toString());
		}
		logger.error("结束添加机构成员，结果：{}", map);
		return map;
	}
	
	//只有经纪人才能通过
	private boolean checkChildIdForAddMember(long userId) {
		boolean isJJR = false;
		Pagination<Role> rolesPage = authorityService.getRolesByUserId(userId, 0, 99);
		if (rolesPage.getResult() != null && rolesPage.getResult().size() > 0) {
			for (Role role : rolesPage.getResult()) {
				if (SysUserRoleEnum.BROKER.getRoleCode().equals(role.getCode())) {
					isJJR = true;
					return isJJR;
				}
			}
		}
		
		return isJJR;
	}
	
	/**
	 * 验证只有营销机构才能添加
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("institutions/validateInstitutionForAdd")
	public Map<String, Object> validateInstitutionForAdd(Long parentId) {
		logger.info("进入添加机构成员，入参：[{" + parentId + "}]");
		boolean isYXJG = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Pagination<Role> rolesPage = authorityService.getRolesByUserId(parentId, 0, 99);
		if (rolesPage.getResult() != null && rolesPage.getResult().size() > 0) {
			for (Role role : rolesPage.getResult()) {
				if (SysUserRoleEnum.MARKETING.getRoleCode().equals(role.getCode())) {
					isYXJG = true;
				}
			}
		}
		if (isYXJG) {
			map.put("code", 1);
			map.put("message", "添加机构成员验证成功");
		} else {
			map.put("code", 0);
			map.put("message", "无法为该机构添加成员");
		}
		logger.error("结束添加机构成员验证，结果：{}", map);
		return map;
	}
}
