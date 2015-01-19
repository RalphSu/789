package com.icebreak.p2p.web.util;

import java.util.ArrayList;
import java.util.List;

import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.ws.enums.CommonAttachmentTypeEnum;
import com.icebreak.util.lang.util.StringUtil;

public class YrdEnumUtil {
	public static List<CommonAttachmentTypeEnum> getAttachmentByIndustry() {
		List<CommonAttachmentTypeEnum> list = new ArrayList<CommonAttachmentTypeEnum>();
		for (CommonAttachmentTypeEnum item : CommonAttachmentTypeEnum.values()) {
			if (StringUtil.equals(item.getIndustryType(), AppConstantsUtil.getCurrentIndustry())
				|| StringUtil.equals(item.getIndustryType(),
					CommonAttachmentTypeEnum.OTHER.getIndustryType())) {
				list.add(item);
			}
		}
		return list;
	}
}
