package com.icebreak.p2p.front.controller.repay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.common.result.SmsCodeResult;
import com.icebreak.p2p.dataobject.LoanDemandDO;
import com.icebreak.p2p.dataobject.Trade;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.web.util.PageUtil;
import com.icebreak.p2p.ws.enums.RepayPlanStatusEnum;
import com.icebreak.p2p.ws.enums.SmsBizType;
import com.icebreak.p2p.ws.info.RepayPlanInfo;
import com.icebreak.p2p.ws.service.RepayPlanService;
import com.icebreak.p2p.ws.service.query.order.RepayPlanQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

@Controller
@RequestMapping("repay")
public class RepayManagerController extends UserAccountInfoBaseController  {
    private final String vm_path = "/front/repay/";
    @Autowired
    RepayPlanService repayPlanService;

    protected String[] getDateInputDayNameArray() {
        return new String[]{"startDate","endDate"};
    }


    @ResponseBody
    @RequestMapping("doneRepay")
    public JSONObject doneRepayManage(RepayPlanQueryOrder queryOrder , PageParam pageParam,Model model,HttpSession session) throws Exception {
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }
        JSONObject jsonObject = new JSONObject();
        queryOrder.setPageSize(pageParam.getPageSize());
        queryOrder.setPageNumber(pageParam.getPageNo());
        List<String> status = new ArrayList<String>(1);
        status.add(RepayPlanStatusEnum.REPAY_SUCCESS.getCode());

        if(sessionLocal != null){
            queryOrder.setRepayUserId(sessionLocal.getUserId());
        }
        queryOrder.setStatusList(status);
        QueryBaseBatchResult<RepayPlanInfo> queryBaseBatchResult = repayPlanService.queryRepayPlanInfo(queryOrder);
        jsonObject.put("page", PageUtil.getCovertPage(queryBaseBatchResult));
        jsonObject.put("queryConditions", queryOrder);
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping("waitRepay")
    public JSONObject waitRepayManage(RepayPlanQueryOrder queryOrder , PageParam pageParam,Model model,HttpSession session) throws Exception {
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }
        JSONObject jsonObject = new JSONObject();
        queryOrder.setPageSize(pageParam.getPageSize());
        queryOrder.setPageNumber(pageParam.getPageNo());
        List<String> status = new ArrayList<String>(1);
        status.add(RepayPlanStatusEnum.NOTPAY.code());


        if(sessionLocal != null){
            queryOrder.setRepayUserId(sessionLocal.getUserId());
        }
        queryOrder.setStatusList(status);
        if(queryOrder.getStartDate() != null){
            queryOrder.setStartDate(com.icebreak.util.lang.util.DateUtil.getStartTimeOfTheDate(queryOrder.getStartDate()));
        }
        if(queryOrder.getEndDate() != null){
            queryOrder.setEndDate(com.icebreak.util.lang.util.DateUtil.getEndTimeOfTheDate(queryOrder.getEndDate()));
        }
        QueryBaseBatchResult<RepayPlanInfo> queryBaseBatchResult = repayPlanService.queryRepayPlanInfo(queryOrder);
        jsonObject.put("page", PageUtil.getCovertPage(queryBaseBatchResult));
        jsonObject.put("queryConditions", queryOrder);
        return jsonObject;
    }







    @ResponseBody
    @RequestMapping("repay")
    public Object repay(long tradeId, long repayPlanId, String smsCode, String mobile,
                                      String business,Model model,HttpSession session) {
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }

        Map<String, Object> map = new HashMap<String, Object>();

        SmsCodeResult smsCodeResult = this.smsManagerService.verifySmsCode(mobile,
                SmsBizType.getByCode(business), smsCode, true);
        boolean codeCheck = smsCodeResult.isSuccess();

        if (!codeCheck) {
            map.put("code", 0);
            map.put("message", "短信验证码错误");
            return map;
        }

        Trade trade = tradeService.getByTradeId(tradeId);
        long demandId = trade.getDemandId();


        try {
            if (demandId > 0 && codeCheck) {
                int result = 0;
//                tradeService.repay(SessionLocalManager.getSessionLocal().getUserId(),
//                        demandId,repayPlanId);
                if (result == 0) {
                    LoanDemandDO loan = loanDemandManager.queryLoanDemandByDemandId(demandId);
                    map.put("code", 1);
                    map.put("message", "还款成功");
                } else {
                    map.put("code", 0);
                    map.put("message", "还款失败");
                }
            } else {
                map.put("code", 0);
                map.put("message", "还款失败");
            }
        } catch (Exception e) {
            logger.error("还款失败异常{}", e.getMessage(), e);
            map.put("code", 0);
            map.put("message", "还款失败");
        }
        return map;
    }


    @RequestMapping("list")
    public String repayManage(HttpServletResponse response, Model model,HttpSession session) throws Exception {
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }
        return vm_path + "repayManager.vm";
    }

}
