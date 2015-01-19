package com.icebreak.p2p.backstage.controller.activityManage;

import com.icebreak.p2p.activity.IActivityService;
import com.icebreak.p2p.activity.QueryActivityOrder;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.GiftInfo;
import com.icebreak.p2p.dataobject.GiftUseRecord;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("backstage")
public class GiftCenterController extends BaseAutowiredController {
	private final String	VM_PATH	= "/backstage/activity/gift/";
	@Autowired
	IActivityService		iActivityService;
	
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	@RequestMapping("giftCenter")
	public String giftCenter(HttpSession session, PageParam pageParam, Model model) {
		model.addAttribute("page", iActivityService.getGiftInfos(pageParam));
		return VM_PATH + "gift-center.vm";
	}
	
	@RequestMapping("giftCenter/addGift")
	public String addGift(HttpSession session, Model model) {
		return VM_PATH + "add-gift.vm";
	}
	
	@RequestMapping("giftCenter/addGiftSubmit")
	public String addGiftSubmit(GiftInfo info, HttpSession session) {
		iActivityService.addGiftInfo(info);
		return "forward:giftCenter";
	}
	
	@RequestMapping("giftCenter/updateGift")
	public String updateGift(long tblBaseId, Model model) {
		String[] giftTypes = new String[] { "WITHDRAW" };
		model.addAttribute("giftTypes", giftTypes);
		model.addAttribute("info", iActivityService.getGift(tblBaseId));
		return VM_PATH + "update-gift.vm";
	}
	
	@RequestMapping("giftCenter/updateGiftSubmit")
	public String updateGiftSubmit(GiftInfo info, HttpSession session) {
		try {
			iActivityService.updateGiftSubmit(info);
		} catch (Exception e) {
			logger.error("修改礼品失败", e);
		}
		return "forward:giftCenter";
	}
	
	@RequestMapping("giftUseRecord")
	public String giftUseRecord(QueryActivityOrder queryActivityOrder, PageParam pageParam,
								Model model) {
		try {
			Page<GiftUseRecord> page = iActivityService.getPageGiftUsedRecord(queryActivityOrder,
				pageParam);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询失败", e);
		}
		return VM_PATH + "gift-use-record.vm";
	}
}
