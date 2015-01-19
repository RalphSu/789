package com.icebreak.p2p.backstage.controller.gold;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.DOEnum.ActivityTypeEnum;
import com.icebreak.p2p.dataobject.GoldExperienceDO;
import com.icebreak.p2p.gold.GoldExperienceService;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asdfasdfa on 2014/12/6.
 */
@Controller
@RequestMapping("backstage/goldExp")
public class GoldExperienceController extends BaseAutowiredController {

  @Resource
  private GoldExperienceService goldExperienceService;

  @Override
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class, "startTime", new CustomDateEditor(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    binder.registerCustomEditor(Date.class, "endTime", new CustomDateEditor(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
  }

  @RequestMapping(value = "goldExpList")
  public String goldExperienceList(ServletRequest request, ServletResponse response, ModelMap modelMap, PageParam pageParam) {
    if(null == pageParam) {
      pageParam = new PageParam();
    }
    Page<GoldExperienceDO> page = goldExperienceService.queryList(null, pageParam);
    modelMap.addAttribute("page", page);
    return "/backstage/gold/goldExperinece.vm";
  }

  @RequestMapping(value = "toAddGoldExp")
  public String toAddGoldExp(ServletRequest request, ServletResponse response, ModelMap modelMap) {
    modelMap.addAttribute("activityTypes", ActivityTypeEnum.getEnums());
    return "/backstage/gold/addGoldExp.vm";
  }

  @RequestMapping(value = "addGoldExp")
  public String addGoldExp(ServletRequest request, ServletResponse response, ModelMap modelMap, GoldExperienceDO goldExp) {
    if(null != goldExperienceService.query(goldExp)) {
      modelMap.put("msg", "该活动已经上线");
      return "/backstage/gold/addGoldExp.vm";
    }
    goldExperienceService.addGoldExp(goldExp);
    return goldExperienceList(request, response, modelMap, null);
  }

  @RequestMapping(value = "detail")
  public String goldExpDetail(ServletRequest request, ServletResponse response, ModelMap modelMap, long id) {
    GoldExperienceDO goldExp = goldExperienceService.queryById(id);
    modelMap.addAttribute("goldExp", goldExp);
    return "/backstage/gold/goldExpDetail.vm";
  }

  @RequestMapping(value = "toEditGoldExp")
  public String toEditGoldExp(ServletRequest request, ServletResponse response, ModelMap modelMap, long id) {
    GoldExperienceDO goldExp = goldExperienceService.queryById(id);
    modelMap.addAttribute("goldExp", goldExp);
    modelMap.addAttribute("activityTypes", ActivityTypeEnum.getEnums());
    return "/backstage/gold/editGoldExp.vm";
  }

  @RequestMapping(value = "updateGoldExp")
  public String updateGoldExp(ServletRequest request, ServletResponse response, ModelMap modelMap, GoldExperienceDO goldExp) {
    goldExperienceService.updateGoldExp(goldExp);
    modelMap.addAttribute("goldExp", goldExp);
    modelMap.addAttribute("activityTypes", ActivityTypeEnum.getEnums());
    return goldExperienceList(request, response, modelMap, null);
  }


  @RequestMapping(value = "deleteGoldExp")
  public String deleteGoldExp(ServletRequest request, ServletResponse response, ModelMap modelMap, GoldExperienceDO goldExp) {
    goldExperienceService.deleteById(goldExp);
    return goldExperienceList(request, response, modelMap, null);
  }

}
