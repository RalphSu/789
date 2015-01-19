package com.icebreak.p2p.before.sign;

import com.icebreak.p2p.base.UserAccountInfoBaseController;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.integration.openapi.SignService;
import com.icebreak.p2p.integration.openapi.order.SignOrder;
import com.icebreak.p2p.integration.openapi.result.SignResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.sign.SignCardInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/usercenter/sign/")
public class SignController extends UserAccountInfoBaseController {

    @Resource
    private SignService signService;

    @Resource
    private SignCardInfoService signCardInfoService;

	@RequestMapping("redirect")
	public String redirect(HttpServletResponse response) {
		String accountId = SessionLocalManager.getSessionLocal().getAccountId();
        try {
            UserBaseInfoDO   userBaseInfoDO = userBaseInfoManager.queryByUserBaseId(SessionLocalManager.getSessionLocal().getUserBaseId());
            if(userBaseInfoDO != null){
                if (!"IS".equals(userBaseInfoDO.getRealNameAuthentication())) {
                        return "/before/topup/signbank.vm";
                }
            }
            SignOrder signOrder = new SignOrder();
            signOrder.setUserId(accountId);
            SignResult result = signService.sign(signOrder);
            if(result.isSuccess()) {
                response.sendRedirect(result.getUrl());
            }
		} catch (Exception e) {
			logger.error("" + e.getMessage(), e);
		}
		return null;
	}

}
