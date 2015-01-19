package com.icebreak.p2p.front.controller.loanRequest;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.user.valueobject.InstitutionsContractedInfo;
import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.SendInformation;
import com.icebreak.p2p.web.util.YrdEnumUtil;
import com.icebreak.p2p.ws.enums.*;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("loan")
public class loanRequestController extends BaseAutowiredController {

	@RequestMapping("type")
	public String chooseLoanType(String bizType, String amount, HttpSession session, Model model) throws Exception {
		return "/front/loanRequest/chooseTypeForm.vm";
	}
	
    @RequestMapping("type/{type}")
    public String loanRequesstP(@PathVariable String type, HttpSession session,Model model){
    	String token = UUID.randomUUID().toString();
    	logger.info("用户进入借款请求页面");
    	 model.addAttribute("token", token);
    	 session.setAttribute("token", token);
    	 model.addAttribute("uploadHost", "");
    	 return "/front/loanRequest/"+type+"Request.vm";
    }
    
	@RequestMapping("sendLoanRequestMail/{T}")
	public String sendLoanRequest(@PathVariable String T,String type,String token,HttpSession session, Model model,HttpServletRequest request)
															throws Exception {
		long templateId=555;
		if("2".equalsIgnoreCase(type)){
			templateId=666;
		}
		String getToken = (String) session.getAttribute("token");
		if(""!=token && token.equals(getToken)){ 
			session.removeAttribute("token");
			boolean result=mailService.sendLoanRequestMail(SendInformation.sendLoanRequestMail(AppConstantsUtil.getLoanRequestMail(),
					templateId, request,type),type,session);	
			if(result){
				model.addAttribute("message","true");
				logger.info("用户请求借款的邮件发送成功。。");
			}else{
				model.addAttribute("massage", "false");
				logger.info("用户请求借款的邮件发送失败。。");
			}
		}else{
			model.addAttribute("message","false");
		}
		return  "/front/loanRequest/loanRequest"+T+"Result.vm";
	}

	/**
	 * 初始化融资录入界面参数
	 * @param bizType
	 * @param amount
	 * @param model
	 */
	private void initLoanInfo(String bizType, String amount, Model model) throws Exception {
		model.addAttribute("guarantee", getInfos(8));
		model.addAttribute("sponsor", getInfos(9));
		if (StringUtil.isNotEmpty(bizType)) {
			model.addAttribute("bizType", bizType);
		} else {
			model.addAttribute("bizType", LoanBizTypeEnum.PUBLIC.code());
		}

		if (StringUtil.isNotEmpty(amount)) {
			model.addAttribute("amount", Long.parseLong(amount) / 100);
		}
		List<CommonAttachmentTypeEnum> list = YrdEnumUtil.getAttachmentByIndustry();
		model.addAttribute("enumlist", list);
		model.addAttribute("invest", divisionService.getDivisionTemplatesByPhase(
				DivisionPhaseEnum.INVESET_PHASE.code(), "normal"));
		model.addAttribute("repay", divisionService.getDivisionTemplatesByPhase(
				DivisionPhaseEnum.REPAY_PHASE.code(), "normal"));
		model.addAttribute("uploadHost", "");
		model.addAttribute("divisionWayList", DivisionWayEnum.getAllEnum());
		model.addAttribute("insureWayList", InsureWayEnum.getAllEnum());
	}


	private List<InstitutionsContractedInfo> getInfos(long roleId) throws Exception {
		List<InstitutionsContractedInfo> infos = institutionsInfoManager
				.queryListeEnterpriseNameAndUserId(roleId);
		return infos;
	}

}
