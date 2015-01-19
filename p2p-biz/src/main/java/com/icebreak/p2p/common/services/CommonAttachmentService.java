package com.icebreak.p2p.common.services;

import java.util.List;

import com.icebreak.p2p.ws.enums.CheckStatusEnum;
import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.p2p.ws.order.CommonAttachmentDeleteOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentOrder;
import com.icebreak.p2p.ws.order.CommonAttachmentQueryOrder;
import com.icebreak.p2p.ws.result.CommonAttachmentResult;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

public interface CommonAttachmentService {
	
	/**
	 *a 群查图片信息
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(	CommonAttachmentQueryOrder order);

	/**
	 * 查询项目缩略图
	 * @param demandId 需求ID
	 * @return
	 */
	CommonAttachmentInfo queryProImage(String demandId);

	/**
	 * 单插图片信息
	 * @param order
	 * @return
	 */
	P2PBaseResult insert(CommonAttachmentOrder order);
	
	/**
	 * 群插图片信息
	 * @param CommonAttachments
	 * @return
	 */
	P2PBaseResult insertAll(List<CommonAttachmentOrder> CommonAttachments);
	
	/**
	 * 通过id删除图片
	 * @param id
	 * @return
	 */
	P2PBaseResult deleteById(long id);
	
	/**
	 * 通过id删除图片(同时删除未审核的所有同张图片)
	 * @param id
	 * @return
	 */
	CommonAttachmentResult deleteByIdAllSame(long id);
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	CommonAttachmentResult deletePicture(CommonAttachmentDeleteOrder order);
	
	/**
	 * 通过id查找图片信息
	 * @param id
	 * @return
	 */
	CommonAttachmentResult findById(long id);
	
	/**
	 * 通过id更新图片信息
	 * @param Id
	 * @return
	 */
	P2PBaseResult updateStatus(long id, CheckStatusEnum status);
	
	/**
	 * 通过bizNo和主状态更新所有符合条件的图片信息(更新状态)
	 * @param Id
	 * @return
	 */
	P2PBaseResult updateStatus(String bizNo, CommonAttachmentTypeEnum moduleType,
								CheckStatusEnum status);
}
