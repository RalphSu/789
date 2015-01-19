package com.icebreak.p2p.backstage.controller.systemSet;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dal.dataobject.SysParamDO;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.util.DateUtil;
import com.icebreak.p2p.web.util.PageUtil;
import com.icebreak.p2p.ws.order.SysParamOrder;
import com.icebreak.p2p.ws.result.P2PBaseResult;
import com.icebreak.p2p.ws.service.query.order.SysParamQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("backstage")
public class SystemParamController extends BaseAutowiredController {
    private final String vm_path = "/backstage/systemSet/";

    /**
     * 系统参数管理页面
     * @param paramName
     * @param pageParam
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("sysParamManage")
    public String sysParamManage(String paramName, PageParam pageParam,
                                 HttpServletResponse response, Model model) throws Exception {
        SysParamQueryOrder queryOrder = new SysParamQueryOrder();
        queryOrder.setParamName(paramName);
        queryOrder.setPageSize(pageParam.getPageSize());
        queryOrder.setPageNumber(pageParam.getPageNo());
        QueryBaseBatchResult<SysParamDO> queryBaseBatchResult  = sysParameterService.querySysPram(queryOrder);
        model.addAttribute("page",  PageUtil.getCovertPage(queryBaseBatchResult));
        model.addAttribute("queryConditions",queryOrder);
        response.setHeader("Pragma", "No-cache");
        return vm_path + "sysParamManage.vm";
    }

    /**
     * 转到新增页面
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping("sysParamManage/toAddSysParam")
    public String toAddSysParamManager(HttpServletResponse response, Model model) throws Exception {
            return vm_path + "addSysParam.vm";
    }

    /**
     * 转到编辑页面
     * @param paramName
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping("sysParamManage/editSysParam")
    public String editSysParamManager(String paramName,
                                    HttpServletResponse response, Model model) throws Exception {
        SysParamDO sysParamDO  = sysParameterService.getSysParameterValueDO(paramName);
        model.addAttribute("info", sysParamDO);
        return vm_path + "editSysParam.vm";
    }

    /**
     * 更新系统参数
     * @param param_name
     * @param param_value
     * @param extend_attribute_one
     * @param extend_attribute_two
     * @param rawAddTime
     * @param description
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping("sysParamManage/updateSysParam")
    public Object updateSystemParamManager(String param_name ,String param_value,String extend_attribute_one,
                                           String extend_attribute_two,String rawAddTime,String description,
                                  HttpServletResponse response, Model model) throws Exception {
        SysParamOrder sysParamOrder = new SysParamOrder();
        sysParamOrder.setParamName(param_name);
        sysParamOrder.setParamValue(param_value);
        sysParamOrder.setExtendAttributeOne(extend_attribute_one);
        sysParamOrder.setExtendAttributeTwo(extend_attribute_two);
        if(StringUtil.isNotBlank(rawAddTime)){
            sysParamOrder.setRawAddTime(DateUtil.parse(rawAddTime));
        }else{
            sysParamOrder.setRawAddTime(new Date());
        }

        JSONObject json = new JSONObject();
            logger.info("更新系统参数，入参{}", sysParamOrder);
            P2PBaseResult result = sysParameterService.updateSysParameterValueDO(sysParamOrder);
            if(result.isSuccess()) {
                json.put("message", "更新系统参数成功");
            }else {
                json.put("message", "更新系统参数失败");
            }
        return json;
    }

    /**
     * 新增系统参数
     * @param param_name
     * @param param_value
     * @param extend_attribute_one
     * @param extend_attribute_two
     * @param rawAddTime
     * @param description
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping("sysParamManage/addSysParam")
    public Object addSysParamManager(String param_name ,String param_value,String extend_attribute_one,
                                     String extend_attribute_two,String rawAddTime,String description,
                                          HttpServletResponse response, Model model) throws Exception {
        SysParamOrder sysParamOrder = new SysParamOrder();
        sysParamOrder.setParamName(param_name);
        sysParamOrder.setParamValue(param_value);
        sysParamOrder.setExtendAttributeOne(extend_attribute_one);
        sysParamOrder.setExtendAttributeTwo(extend_attribute_two);
        sysParamOrder.setRawAddTime(new Date());
        JSONObject json = new JSONObject();
        logger.info("新增系统参数，入参{}", sysParamOrder);
        SysParamDO sysParamDO = sysParameterService.getSysParameterValueDO(param_name);
        if(sysParamDO != null){
            json.put("message","参数名称已经存在");
            return json;
        }
        P2PBaseResult result = sysParameterService.insertSysParameterValueDO(sysParamOrder);
        if(result.isSuccess()) {
            json.put("message", "添加系统参数成功");
        }else {
            json.put("message", "添加系统参数失败");
        }
        return json;


    }

}
