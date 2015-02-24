package com.icebreak.p2p.front.controller.boot;

import com.alibaba.fastjson.JSONObject;
import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dal.daointerface.ExtendAttrDAO;
import com.icebreak.p2p.dataobject.*;
import com.icebreak.p2p.gold.UserGoldExperienceService;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.page.Pagination;
import com.icebreak.p2p.session.SessionLocal;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.IOperatorInfoService;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.CommonUtil;
import com.icebreak.p2p.util.MoneyUtil;
import com.icebreak.p2p.util.security.AES;
import com.icebreak.p2p.web.util.AttachmentModuleType;
import com.icebreak.p2p.web.util.RateUtil;
import com.icebreak.p2p.web.util.YrdEnumUtil;
import com.icebreak.p2p.ws.info.CommonAttachmentInfo;
import com.icebreak.p2p.ws.order.CommonAttachmentQueryOrder;
import com.icebreak.p2p.ws.service.query.order.IndexQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;
import com.icebreak.util.lang.ip.IPUtil;
import com.icebreak.util.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("index")
public class BootController extends UserAccountInfoBaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	  protected IOperatorInfoService	operatorInfoService;

    @Autowired
    protected UserGoldExperienceService userGoldExperienceService;

    @Autowired
    ExtendAttrDAO extendAttrDAO;

    /**
     * 前端首页
     *
     * @return
     */
    @RequestMapping("index/{size}/{page}")
    public String index(@PathVariable int size, @PathVariable int page, IndexQueryOrder queryOrder, Model model) {
    	
    	Integer status = queryOrder.getStatus();
		//String guarantee = queryOrder.getGuarantee();
    	
        Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, queryOrder);
        for (IndexTrade i : indexTrade.getResult()) {
            i.setStrRaate(RateUtil.getRate(i.getRate()));
           
        }
        
        model.addAttribute("page", indexTrade);
        model.addAttribute("status", status);
        return "front/index/index.vm";
    }


    /**
     * 前端首页
     *
     * @return
     */
    @RequestMapping("staticIndex/{size}/{page}")
    public String staticIndex(@PathVariable int size, @PathVariable int page, IndexQueryOrder queryOrder, Model model) {

        Integer status = queryOrder.getStatus();
        //String guarantee = queryOrder.getGuarantee();

        Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, queryOrder);

        model.addAttribute("page", fillDetail(indexTrade));
        //model.addAttribute("percentnum", indexTrade.getPercent());

        model.addAttribute("status", status);
        return "front/index/common/entries.vm";
    }

    /**
     * 前端首页 更多
     *
     * @return
     */
    @RequestMapping("moreIndex/{size}/{page}")
    public String moreIndex(@PathVariable int size, @PathVariable int page, IndexQueryOrder queryOrder, Model model) {

        Integer status = queryOrder.getStatus();
        //String guarantee = queryOrder.getGuarantee();

        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();
        if (sessionLocal != null && sessionLocal.getAccountId() != null) {
            this.initAccountInfo(model);
        }
        Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, queryOrder);

        model.addAttribute("page", fillDetail(indexTrade));
        model.addAttribute("status", status);
        return "front/index/index_more.vm";
    }

    /**
     * 搜索借款需求
     *
     * @param size
     * @param page
     * @param timeUnit
     * @param startTime
     * @param endTime
     * @param startAmount
     * @param endAmount
     * @param startRate
     * @param endRate
     * @param model
     * @return
     */
    @RequestMapping("invest/{size}/{page}")
    public String invest(@PathVariable int size, @PathVariable int page, String guarantee,
                         String timeUnit, Integer startTime, Integer endTime, Long startAmount,
                         Long endAmount, Double startRate, Double endRate, Model model) {

        this.initAccountInfo(model);

        Pagination<IndexTrade> indexTrade = indexTradeService.getIndexTrades((page - 1) * size,
                size, guarantee, timeUnit, startTime, endTime, startAmount, endAmount, startRate,
                endRate);

        model.addAttribute("page", fillDetail(indexTrade));
        model.addAttribute("status", 1);

        return "front/index/index_invest.vm";
    }

    /**
     * 查看借款需求详情
     *
     * @param demandId
     * @param tradeId
     * @param session
     * @param model
     * @param pageParam
     * @param tab
     * @return
     * @throws Exception
     */
    @RequestMapping("lookup/{demandId}/{tradeId}")
    public String lookup(@PathVariable long demandId, @PathVariable long tradeId,
                         HttpSession session, Model model, PageParam pageParam, String tab)
            throws Exception {
        SessionLocal sessionLocal = SessionLocalManager.getSessionLocal();

        boolean investsDownLoadable = false;
        long countInvestTimes = 0;

        //查询当前用户是否存在体验金
        if(null != sessionLocal) {
            model.addAttribute("userGoldExp", queryUserGoldExp(sessionLocal.getUserId()));
        }

        String token = UUID.randomUUID().toString();
        Trade trade = tradeService.getByTradeId(tradeId);
        if(trade.getStatus() == 2) {
            model.addAttribute("finishTrade", "true");
        }

        //到期无条件兑付银行
        String paymentBankName = extendAttrDAO.getExtendValue(demandId, "LOANDEMAND_PAYMENTBANKNAME");
        model.addAttribute("paymentBankName", paymentBankName);

        model.addAttribute("investableAmount", trade.getAmount() - trade.getLoanedAmount());

        LoanDemandDO loanDemand = loanDemandManager.queryLoanDemandByDemandId(demandId);
        model.addAttribute("loanDemand", loanDemand);
        Date date = new Date();
        if(date.getTime() > loanDemand.getDeadline().getTime()) {
            model.addAttribute("overDeadLine", "true");
        }
        CommonAttachmentInfo proImageInfo = commonAttachmentService.queryProImage(String.valueOf(demandId));
        String proImageURL = (proImageInfo != null ? proImageInfo.getRequestPath() : AppConstantsUtil.getProjectDefaultThumbnailUrl());
        model.addAttribute("proImageURL", proImageURL);

        Map<String, Object> cond = new HashMap<String, Object>();
        cond.put("roleId", 8L);
        cond.put("tradeId", tradeId);
        List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
        if (det != null && det.size() > 0) {
            TradeFlowCode tf = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0).getId());
            if (tf != null) {
                model.addAttribute("contractNo", tf.getTradeFlowCode());
            }
        }
        if ((trade.getAmount() - trade.getLoanedAmount()) > 0
                && (trade.getLeastInvestAmount() - (trade.getAmount() - trade.getLoanedAmount())) > 0) {
            model.addAttribute("lastInvestAvailable", "yes");
        }

        pageParam.setPageSize(10);
        model.addAttribute("page", tradeService.getByProperties(tradeId,
                (pageParam.getPageNo() - 1) * pageParam.getPageSize(), pageParam.getPageSize(), null,
                null, null, null, null, null, null, null, null));

        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("loanDemandId", demandId);
        conditions.put("authType", "MAKELOANLVONE");
        long count1 = loanDemandManager.countLoanAuthRecordByCondition(conditions);
        String auditUserBaseId = "";
        if (count1 > 0) {
            List<LoanAuthRecord> reccord1 = loanDemandManager
                    .getLoanAuthRecordByCondition(conditions);
            List<UserBaseInfoDO> users = userBaseInfoManager.queryByType("JG",
                    String.valueOf(reccord1.get(0).getAuthUserId()));
            auditUserBaseId = users.get(0).getUserBaseId();
            model.addAttribute("auditUser1", users.get(0).getUserName());
            //备注
            Map<String, Object> opConditions = new HashMap<String, Object>();
            opConditions.put("userBaseId", users.get(0).getUserBaseId());
            opConditions.put("limitStart", 0);
            opConditions.put("pageSize", 100);
            List<OperatorInfoDO> operatorInfos = operatorInfoService.queryOperatorsByProperties(opConditions);
            model.addAttribute("remark1", operatorInfos.get(0).getRemark());
			      //END
            model.addAttribute("auditrecord1", reccord1.get(0));
            model.addAttribute("audit", "yes");
        }
        Map<String, Object> conditions2 = new HashMap<String, Object>();
        conditions2.put("loanDemandId", demandId);
        conditions2.put("authType", "MAKELOANLVTWO");
        long count2 = loanDemandManager.countLoanAuthRecordByCondition(conditions2);
        if (count2 > 0) {
            List<LoanAuthRecord> reccord2 = loanDemandManager
                    .getLoanAuthRecordByCondition(conditions2);
            List<UserBaseInfoDO> users = userBaseInfoManager.queryByType("JG",
                    String.valueOf(reccord2.get(0).getAuthUserId()));
            model.addAttribute("auditUser2", users.get(0).getUserName());
            //备注
            Map<String, Object> opConditions = new HashMap<String, Object>();
            opConditions.put("userBaseId", users.get(0).getUserBaseId());
            opConditions.put("limitStart", 0);
            opConditions.put("pageSize", 100);
            List<OperatorInfoDO> operatorInfos = operatorInfoService.queryOperatorsByProperties(opConditions);
            model.addAttribute("remark2", operatorInfos.get(0).getRemark());
			      //END
            model.addAttribute("auditrecord2", reccord2.get(0));
            model.addAttribute("audit", "yes");
        }

        //是否投资过某融资需求，决定是否允许下载合同，担保函
        if (sessionLocal != null) {

            this.initAccountInfo(model);

            countInvestTimes = tradeService.countInvestTimes(SessionLocalManager.getSessionLocal()
                    .getUserBaseId(), demandId, null);

            //是否可下载某融资需求的投资人列表
            investsDownLoadable = institutionsInfoManager.isFromSameOrgan(SessionLocalManager
                    .getSessionLocal().getUserBaseId(), auditUserBaseId);
        }

        String percent = "0%";

        if (trade.getDeadline().before(new Date())) {
            if (isFullScale(trade)) {
                percent = "100.0%";
            } else {
                percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(), trade.getLeastInvestAmount());
            }
        } else {
            percent = MoneyUtil.getPercentage(trade.getLoanedAmount(), trade.getAmount(), trade.getLeastInvestAmount());
        }

        model.addAttribute("percent", percent);
        model.addAttribute("tab", tab); //分页跳转后显示正确的tab页面
        model.addAttribute("tradeId", tradeId);
        model.addAttribute("demandId", demandId);
        model.addAttribute("trade", trade);
        model.addAttribute("downLoadableInvests", investsDownLoadable);
        model.addAttribute("countInvestTimes", countInvestTimes);

        //计算最迟还款日（到期日期(结息日期)+3个工作日）
        Date expireDate = trade.getExpireDateTime();
        if(expireDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(expireDate);
            cal.add(Calendar.DAY_OF_YEAR, 3);//直接加3天
//            //一周第一天是否为星期天
//            boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
//            //获取周几
//            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
//            //若一周第一天为星期天，则-1
//            if(isFirstSunday){
//                weekDay = weekDay - 1;
//                if(weekDay == 0){
//                    weekDay = 7;
//                }
//            }
//            //加3个工作日
//            int addDay = 3; //结息日期为星期一、星期二、星期天时加3天
//            if(weekDay == 3 || weekDay == 4 || weekDay == 5){
//                addDay = 5; //结息日期为星期三、星期四、星期五时加5天，跨过周末
//            }else if(weekDay == 6){
//                addDay = 4; //结息日期为星期六时加4天
//            }
//
//            cal.add(Calendar.DATE, addDay);
            model.addAttribute("lastExpireDate", cal.getTime());
        }


        //查附件图片
        CommonAttachmentQueryOrder attachmentQueryOrder = new CommonAttachmentQueryOrder();
		    attachmentQueryOrder.setBizNo(demandId+"");
		    attachmentQueryOrder.setModuleTypeList(YrdEnumUtil.getAttachmentByIndustry());
		    QueryBaseBatchResult<CommonAttachmentInfo> batchResult = commonAttachmentService.queryCommonAttachment(attachmentQueryOrder);
		    List<AttachmentModuleType> attachmentModuleTypeList=new ArrayList<AttachmentModuleType>();
		    List<List<CommonAttachmentInfo>> attachmentInfoList=new ArrayList<List<CommonAttachmentInfo>>();
		    for(CommonAttachmentInfo attachmentInfo:batchResult.getPageList()) {
			      boolean isExist=false;
			      for(AttachmentModuleType attachmentModuleType:attachmentModuleTypeList) {
				        if(attachmentInfo.getModuleType()==attachmentModuleType.getModuleType()) {
					          attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
					          isExist=true;
                    break;
				        }
			      }
            if(!isExist) {
                AttachmentModuleType attachmentModuleType=new AttachmentModuleType();
                attachmentModuleType.setModuleType(attachmentInfo.getModuleType());
                attachmentModuleType.getAttachmentInfos().add(attachmentInfo);
                attachmentModuleTypeList.add(attachmentModuleType);
            }
		    }
		    model.addAttribute("list", attachmentModuleTypeList);
        session.setAttribute("token", token);

        boolean investAvlTimeReached = (new Date().after(loanDemand.getInvestAvalibleTime()));
        model.addAttribute("investAvlTimeReached", investAvlTimeReached);
        boolean deadlineReached = (new Date().after(loanDemand.getDeadline()));
        model.addAttribute("deadlineReached", deadlineReached);

        return "front/index/index_invest_detail.vm";
    }

    /**
     * 根据userID查询当前用户是否存在可用的体验金
     * @param userId
     * @return
     */
    protected UserGoldExperienceDO queryUserGoldExp(long userId) {
        UserGoldExperienceDO userGoldExp = new UserGoldExperienceDO();
        userGoldExp.setUserId(userId);
        userGoldExp.setStatus("1");//未使用
        logger.info("【用户体验金查询参数】" + userGoldExp);
        List<UserGoldExperienceDO> list = userGoldExperienceService.queryList(userGoldExp);
        logger.info("【用户体验金查询结果】" + list);
        //TODO 暂时取第一条为使用的体验金
        if(null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @RequestMapping("getNotify")
    public String getNotify() {
        return "/test/notify.html";
    }


    @RequestMapping("checkProductDetail.htm")
    public String checkProductDetail(String productId, String detailId, String returnUrl,
                                     HttpSession session, Model model, HttpServletRequest request) throws Exception {
        model.addAttribute("pdfhost", "");
        String secKey = "AESTOyijifu";
        AES aes = new AES(secKey);
        detailId = aes.getDecrypt(detailId);
        productId = aes.getDecrypt(productId);
        if (StringUtil.isBlank(productId)) {
            logger.error("产品号为空");
            return "";
        }
        long tradeId = Long.parseLong(productId);
        String token = UUID.randomUUID().toString();
        Trade trade = tradeService.getByTradeId(tradeId);
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loanDemandId", trade.getDemandId());
        condition.put("authType", "MAKELOANLVTWO");
        long count = loanDemandManager.countLoanAuthRecordByCondition(condition);
        CommonUtil.clearMap(condition);
        if (count > 0) {
            model.addAttribute("audit", "yes");
        }
        model.addAttribute("investableAmount", trade.getAmount() - trade.getLoanedAmount());
        model
                .addAttribute("item", loanDemandManager.queryLoanDemandByDemandId(trade.getDemandId()));
        Map<String, Object> cond = new HashMap<String, Object>();
        cond.put("roleId", 8L);
        cond.put("tradeId", tradeId);
        List<TradeQueryDetail> det = loanDemandManager.getTradeDetailByConditions(cond);
        CommonUtil.clearMap(cond);
        if (det != null && det.size() > 0) {
            TradeFlowCode tf = tradeService.queryInvestFlowCodesByTradeDetailId(det.get(0).getId());
            if (tf != null) {
                model.addAttribute("contractNo", tf.getTradeFlowCode());
            }
        }
        if ((trade.getAmount() - trade.getLoanedAmount()) > 0
                && (trade.getLeastInvestAmount() - (trade.getAmount() - trade.getLoanedAmount())) > 0) {
            model.addAttribute("lastInvestAvailable", "yes");
        }
        Map<String, Object> condDetail = new HashMap<String, Object>();
        condDetail.put("roleId", 12L);
        condDetail.put("tradeId", tradeId);
        condDetail.put("detailId", detailId);
        List<TradeQueryDetail> detailsList = loanDemandManager
                .getTradeDetailByConditions(condDetail);
        CommonUtil.clearMap(condDetail);
        if (detailsList.size() > 0) {
            long userId = detailsList.get(0).getUserId();
            UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserId(userId);

            //获取角色串begin
            List<Role> roleList = null;
            try {
                roleList = authorityService.getRolesByUserId(userBaseInfo.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder roleCodes = new StringBuilder();
            String roleCodesStr = "";
            if(roleList != null || roleList.size()>0){
                for(int i=0;i<roleList.size();i++){
                    roleCodes.append("|").append(roleList.get(i).getCode());
                }
                roleCodesStr = roleCodes.substring(1);
            }
            //获取角色串end

            SessionLocalManager.setSessionLocal(new SessionLocal(authorityService
                    .getPermissionsByUserId(userBaseInfo.getUserId()), userBaseInfo.getUserId(),
                    userBaseInfo.getUserBaseId(), roleCodesStr, userBaseInfo.getAccountId(), userBaseInfo
                    .getAccountName(), userBaseInfo.getRealName(), userBaseInfo.getUserName(), IPUtil.getIpAddr(request)
            ));
        }
        model.addAttribute("tradeId", tradeId);
        model.addAttribute("detailId", detailId);
        model.addAttribute("trade", trade);
        model.addAttribute("showProductContract", "yes");
        model.addAttribute("returnUrl", returnUrl);
        session.setAttribute("token", token);
        return "front/index/ext_index_invest_detail.vm";
    }

    @ResponseBody
    @RequestMapping("refreshAmount.htm")
    public Object refreshAmount(Model model) {
        JSONObject jsonobj = new JSONObject();
        Map<String, Object> params = new HashMap<String, Object>();
        long dealAmount = tradeService.countAmountByParams(params);
        List<Integer> stats = new ArrayList<Integer>();
        stats.add(3);
        stats.add(7);
        params.put("status", stats);
        long repayAmount = tradeService.countAmountByParams(params);
        long interestAmount = tradeService.countInterestAmountByParams(params);
        String dealedAmount = MoneyUtil.getFormatAmount(dealAmount);
        String repaidAmount = MoneyUtil.getFormatAmount(repayAmount + interestAmount);
        jsonobj.put("dealedAmount", dealedAmount.substring(0, dealedAmount.lastIndexOf(".") + 1));
        jsonobj.put("repaidAmount", repaidAmount.substring(0, repaidAmount.lastIndexOf(".") + 1));
        jsonobj.put("dealedSupAmount", dealedAmount.substring(dealedAmount.lastIndexOf(".") + 1));
        jsonobj.put("repaidSupAmount", repaidAmount.substring(repaidAmount.lastIndexOf(".") + 1));
        CommonUtil.clearMap(params);
        return jsonobj;
    }
}
