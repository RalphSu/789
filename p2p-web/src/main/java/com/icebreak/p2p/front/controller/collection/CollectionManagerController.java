package com.icebreak.p2p.front.controller.collection;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.TradeQueryDetail;
import com.icebreak.p2p.page.Page;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.ws.enums.DivisionPhaseEnum;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;
import com.icebreak.p2p.ws.enums.TradeDetailStatusEnum;
import com.icebreak.p2p.ws.enums.TradeStatusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("collection")
public class CollectionManagerController extends UserAccountInfoBaseController {
    private static final String CLASS = "class=\"light\"";
    private final String vm_path = "/front/collection/";

    @RequestMapping("repaid")
    public String repaid(String startDate,String endDate,String tradeName,PageParam pageParam,Model model) throws Exception {
        JSONObject jsonObject = new JSONObject();
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        Map<String, Object> conditions = new HashMap<String,Object>();
        if(sessionLocal != null){
            conditions.put("userId",sessionLocal.getUserId());
        }
        conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
        List<String> status = new ArrayList<String>();
        status.add(TradeDetailStatusEnum.PS.code());
        conditions.put("status", status);
        conditions.put("startDate",startDate);
        conditions.put("endDate",endDate);
        conditions.put("tradeName",tradeName);
        conditions.put("tradeStatus", TradeStatusEnum.REPAY_FINISH.getValue());
        Page<TradeQueryDetail> page =  tradeService.queryCollectionPage(conditions, pageParam);
        model.addAttribute("page", page);
        model.addAttribute("repaid", CLASS);
        model.addAttribute("myinvest", CLASS);
        return vm_path + "newCollectionManager.vm";
    }

    @RequestMapping("wait")
    public String waitForRepay(String startDate,String endDate,String tradeName,PageParam pageParam, Model model) throws Exception {
        JSONObject jsonObject = new JSONObject();
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        Map<String, Object> conditions = new HashMap<String,Object>();
        if(sessionLocal != null){
            conditions.put("userId",sessionLocal.getUserId());
        }
        conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
        List<String> status = new ArrayList<String>();
        //status.add(TradeDetailStatusEnum.AS.code());
        status.add(TradeDetailStatusEnum.PS.code());
        conditions.put("status", status);

        List<Integer> tradeStatusList = new ArrayList<Integer>();
        //投资成立和待还款zhu状态
     tradeStatusList.add(TradeStatusEnum.REPAYING.getValue());
        tradeStatusList.add(TradeStatusEnum.DOREPAY.getValue());

        conditions.put("tradeStatusList", tradeStatusList);
        conditions.put("tradeName",tradeName);
        conditions.put("startDate",startDate);
        conditions.put("endDate",endDate);
        Page<TradeQueryDetail> page =  tradeService.queryCollectionPage(conditions, pageParam);
        model.addAttribute("myinvest", CLASS);
        model.addAttribute("wait", CLASS);
        model.addAttribute("page", page);
        return vm_path + "newCollectionManager.vm";
    }


    @RequestMapping("list")
    public String list(HttpServletResponse response, Model model,PageParam pageParam) throws Exception {
         SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }
        Map<String, Object> conditions = new HashMap<String,Object>();
        if(sessionLocal != null){
            conditions.put("userId",sessionLocal.getUserId());
        }
        conditions.put("roleId", SysUserRoleEnum.INVESTOR.getValue());
        conditions.put("transferPhase", DivisionPhaseEnum.ORIGINAL_PHASE.getCode());
        Page<TradeQueryDetail> page =  tradeService.queryCollectionPage(conditions, pageParam);
        model.addAttribute("list", CLASS);
        model.addAttribute("myinvest", CLASS);
        model.addAttribute("page", page);
        return vm_path + "newCollectionManager.vm";
    }

}
