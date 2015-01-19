package com.icebreak.p2p.backstage.controller.OperationJournal;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.page.PageParam;
import com.icebreak.p2p.web.util.PageUtil;
import com.icebreak.p2p.ws.info.OperationJournalInfo;
import com.icebreak.p2p.ws.service.OperationJournalService;
import com.icebreak.p2p.ws.service.query.order.OperationJournalQueryOrder;
import com.icebreak.p2p.ws.service.query.result.QueryBaseBatchResult;

@Controller
@RequestMapping("backstage")
public class OperationJournalController extends BaseAutowiredController {
    private final String vm_path = "/backstage/OperationJournal/";
    @Autowired
    OperationJournalService operationJournalService;


    /**
     * 日志管理页面
     */
    @RequestMapping("OperationJournal")
    public String queryOperationJournalPageList(OperationJournalQueryOrder queryOrder, PageParam pageParam, HttpServletResponse response, Model model) {
        try {
            queryOrder.setPageSize(pageParam.getPageSize());
            queryOrder.setPageNumber(pageParam.getPageNo());
            QueryBaseBatchResult<OperationJournalInfo> queryBaseBatchResult = operationJournalService.queryOperationJournalInfo(queryOrder);
            model.addAttribute("page", PageUtil.getCovertPage(queryBaseBatchResult));
            model.addAttribute("queryConditions", queryOrder);
        } catch (Exception e) {
               logger.error("查询短信出错",e.getMessage(),e);
        }


        return vm_path + "OperationJournalManage.vm";
    }


    protected String[] getDateInputNameArray() {
        return new String[]{"operatorTimeStart","operatorTimeEnd"};
    }


}
