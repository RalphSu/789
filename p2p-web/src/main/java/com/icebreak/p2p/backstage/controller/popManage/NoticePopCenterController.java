package com.icebreak.p2p.backstage.controller.popManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.PopInfoDO;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.pop.IPopService;

@Controller
@RequestMapping("backstage")
public class NoticePopCenterController extends BaseAutowiredController {
	private final String	VM_PATH	= "/backstage/publicNotice/noticeCenter/";
	@Autowired
	IPopService				popService;
	
	@RequestMapping("noticeCenter")
	public String noticeCenter(HttpSession session, PageParam pageParam, Model model) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		List<Integer> types = new ArrayList<Integer>();
		types.add(1);
		types.add(2);
		types.add(10);
		types.add(11);
		types.add(12);
		types.add(13);
		conditions.put("type", types);
		model.addAttribute("page", popService.getPageByConditions(pageParam, conditions));
		return VM_PATH + "notice-center.vm";
	}
	
	@RequestMapping("noticeCenter/addNotice")
	public String addNotice(HttpSession session) {
		return VM_PATH + "add-notice.vm";
	}

    @ResponseBody
	@RequestMapping("noticeCenter/addNoticeSubmit")
	public JSONObject addNoticeSubmit(PopInfoDO info, HttpSession session) {
        JSONObject json = new JSONObject();
        try{
            if(info.getPopId() != 0){
                info.setModifyTime(new Date());
                popService.updateNotice(info);
            }else{
                info.setAddTime(new Date());
                popService.addNotice(info);
            }
            json.put("code",1);
            json.put("popNoticeId",info.getPopId());
        }catch (Exception e){
            logger.error("添加公告出错: e={}",e.getMessage(),e);
            json.put("code",0);
        }
		return json;
	}
	
	@RequestMapping("noticeCenter/updateNotice")
	public String updateNotice(long popId, Model model) {
		model.addAttribute("info", popService.getByPopId(popId));
		return VM_PATH + "update-notice.vm";
	}

    @ResponseBody
	@RequestMapping("noticeCenter/updateNoticeSubmit")
	public JSONObject updateNoticeSubmit(PopInfoDO info, HttpSession session) {
        JSONObject json = new JSONObject();
        try {
            info.setModifyTime(new Date());
            popService.updateNotice(info);
            json.put("code","1");
            json.put("popNoticeId",info.getPopId());
        }catch (Exception e){
            logger.error("更新公告出错: e={}",e.getMessage(),e);
            json.put("code",0);

        }
        return json;

	}
	
	@ResponseBody
	@RequestMapping(value = "noticeCenter/changeStatus")
	public Object changeStatus(long popId, short status) throws Exception {
		JSONObject jsonobj = new JSONObject();
		PopInfoDO info = popService.getByPopId(popId);
		info.setStatus(status);
		try {
			popService.updateNotice(info);
			jsonobj.put("code", 1);
			jsonobj.put("message", "执行成功！");
		} catch (Exception e) {
			logger.error("changeStatus", e);
			jsonobj.put("code", 0);
			jsonobj.put("message", "执行失败！");
		}
		return jsonobj;
	}
}
