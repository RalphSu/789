package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.base.OpenApiBaseContextServiceBase;
import com.icebreak.p2p.common.services.CommonAttachmentService;
import com.icebreak.p2p.dal.daointerface.CommonAttachmentDAO;
import com.icebreak.p2p.dal.dataobject.CommonAttachmentDO;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.ws.enums.CheckStatusEnum;
import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.p2p.ws.order.CommonAttachmentDeleteOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentQueryOrder;
import com.icebreak.p2p.ws.result.CommonAttachmentResult;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.ResultEnum;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.ListUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommonAttachmentServiceImpl extends OpenApiBaseContextServiceBase implements
																				CommonAttachmentService {
	@Autowired
	CommonAttachmentDAO commonAttachmentDAO;

	//TODO 将DO对象转换成Info对象。临时办法去除重复代码，应该建立工具类或则在对象的构造方法中完成对象转换
	private CommonAttachmentInfo createAttachInfo(CommonAttachmentDO tradeAttachmentDO)
	{
		CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
		MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
		if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
			attachmentInfo.setModuleType(CommonAttachmentTypeEnum
					.getByCode(tradeAttachmentDO.getModuleType()));
		}

		if (StringUtil.isNotBlank(tradeAttachmentDO.getCheckStatus())) {
			attachmentInfo.setCheckstatus(CheckStatusEnum.getByCode(tradeAttachmentDO
					.getCheckStatus()));
		}

		return attachmentInfo;
	}


	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = new QueryBaseBatchResult<CommonAttachmentInfo>();
//		logger.info("进入列表查询图片信息");
		try {
		
			List<String> moduleTypeCodeList=new ArrayList<String>();
			if(order.getModuleTypeList()!=null)
			{
				for(CommonAttachmentTypeEnum typeEnum:order.getModuleTypeList())
				{
					moduleTypeCodeList.add(typeEnum.code());
				}	
			}
		
			List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO
				.findByManyModuleType(moduleTypeCodeList,order.getBizNo());
			List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
			for (CommonAttachmentDO tradeAttachmentDO : attachmentDOs) {
				attachmentInfos.add(createAttachInfo(tradeAttachmentDO));
			}
			result.setPageList(attachmentInfos);
			result.setSuccess(true);
			result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
			result.setMessage(ResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public CommonAttachmentInfo queryProImage(String demandId) {

//		logger.info("进入查询项目缩略图信息");
		try {

			List<String> moduleTypeCodeList=new ArrayList<String>();
			moduleTypeCodeList.add(CommonAttachmentTypeEnum.PRO_IMAGE.code());

			List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO
					.findByManyModuleType(moduleTypeCodeList,demandId);

			return ListUtil.isNotEmpty(attachmentDOs) ? createAttachInfo(attachmentDOs.get(0))
				: null;
		} catch (DataAccessException e) {
			logger.error("查询项目缩略图信息失败,{}", e.getMessage(), e);
		} catch (Exception e) {
			logger.error("查询项目缩略图信息失败,{}", e.getMessage(), e);
		}
		return null;
	}

	@Override
	public P2PBaseResult insert(CommonAttachmentOrder order) {
		P2PBaseResult result = new P2PBaseResult();
		logger.info("进入插入单条图片信息");
		try {
			int count = 1;
			CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
			MiscUtil.copyPoObject(attachmentDO, order);
			attachmentDO.setModuleType(order.getModuleType().getCode());
			commonAttachmentDAO.insert(attachmentDO);
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public P2PBaseResult insertAll(List<CommonAttachmentOrder> CommonAttachments) {
		P2PBaseResult result = new P2PBaseResult();
		logger.info("进入插入多条图片信息");
		try {
			int count = 0;
			for (CommonAttachmentOrder order : CommonAttachments) {
				CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
				MiscUtil.copyPoObject(attachmentDO, order);
				attachmentDO.setModuleType(order.getModuleType().getCode());
				commonAttachmentDAO.insert(attachmentDO);
				count++;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("插入多条图片信息失败");
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult deleteById(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				commonAttachmentDAO.deleteById(id);
				
				result.setSuccess(true);
				result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
				result.setMessage(ResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult deleteByIdAllSame(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息(同时删除未审核的其他同地址图片信息)");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				long findCount = 0;
				long delCount = 0;
				commonAttachmentDAO.deleteById(id);
				delCount++;
				List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
					.getFilePhysicalPath());
				for (CommonAttachmentDO pic : pics) {
					if (StringUtil.isNotBlank(pic.getCheckStatus())
						&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
						findCount++;
					} else {
						commonAttachmentDAO.deleteById(pic.getAttachmentId());
						delCount++;
					}
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append("删除完成,总计删除[");
				sb.append(delCount);
				sb.append("]行,找到[");
				sb.append(findCount);
				sb.append("]行,已审核过的数据!");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult findById(long id) {
		
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入查询单条图片信息{}", id);
		try {
			CommonAttachmentDO tradeAttachmentDO = commonAttachmentDAO.findById(id);
			if (tradeAttachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
				MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
				if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
					attachmentInfo.setModuleType(CommonAttachmentTypeEnum
						.getByCode(tradeAttachmentDO.getModuleType()));
				}
				
				result.setAttachmentInfo(attachmentInfo);
				result.setSuccess(true);
				result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
				result.setMessage(ResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult deletePicture(CommonAttachmentDeleteOrder order) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			order.check();
			if (order.getAttachmentId() > 0) {
				CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(order
					.getAttachmentId());
				if (attachmentDO == null || !attachmentDO.getBizNo().equals(order.getBizNo())) {
					result.setSuccess(false);
					result.setMessage("无效的图片id");
				} else {
					long findCount = 0;
					long delCount = 0;
					commonAttachmentDAO.deleteById(order.getAttachmentId());
					delCount++;
					List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
						.getFilePhysicalPath());
					for (CommonAttachmentDO pic : pics) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			} else {
				if (order.getFilePhysicalPath() != null) {
					long findCount = 0;
					long delCount = 0;
					List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO
						.findByPicpath(order.getFilePhysicalPath());
					for (CommonAttachmentDO pic : attachmentDOs) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public P2PBaseResult updateStatus(long id, CheckStatusEnum status) {
		P2PBaseResult result = new P2PBaseResult();
		logger.info("进入更新图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				attachmentDO.setCheckStatus(status.code());
				commonAttachmentDAO.update(attachmentDO);
				StringBuilder sb = new StringBuilder();
				sb.append("更新完成,将id为[");
				sb.append(id);
				sb.append("]更新为[");
				sb.append(status);
				sb.append("]状态");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public P2PBaseResult updateStatus(String bizNo, CommonAttachmentTypeEnum moduleType,
										CheckStatusEnum status) {
		
		P2PBaseResult result = new P2PBaseResult();
		StringBuilder sb1 = new StringBuilder();
		sb1.append("入参:bizNo=[");
		sb1.append(bizNo);
		sb1.append("],moduleType=[");
		sb1.append(moduleType);
		sb1.append("],status=[");
		sb1.append(status);
		sb1.append("].");
		if (StringUtil.isBlank(bizNo) || moduleType == null || status == null) {
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("请求参数不完整,参数全部必传");
			sb1.append("进入更新图片状态信息,请求参数不完整");
			logger.error(sb1.toString());
			return result;
		}
		logger.info(sb1.toString());
		try {
			commonAttachmentDAO.updateStatusAllSame(status.code(), bizNo, moduleType.code());
			result.setSuccess(true);
			result.setResultEnum(ResultEnum.EXECUTE_SUCCESS);
			result.setMessage(ResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setResultEnum(ResultEnum.DATABASE_EXCEPTION);
			result.setMessage(ResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
}
