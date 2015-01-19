package com.icebreak.p2p.front.controller.bank;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.dataobject.InstitutionsInfoDO;
import com.icebreak.p2p.dataobject.PersonalInfoDO;
import com.icebreak.p2p.dataobject.UserBaseInfoDO;
import com.icebreak.p2p.service.openingbank.enums.CardTypeEnum;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.session.SessionLocalManager;
import com.icebreak.p2p.user.result.InstitutionsReturnEnum;
import com.icebreak.p2p.user.result.PersonalReturnEnum;
import com.icebreak.p2p.util.BusinessNumberUtil;
import com.icebreak.p2p.util.YrdConstants;
import com.icebreak.p2p.ws.base.info.BankBasicInfo;
import com.icebreak.util.lang.util.StringUtil;
import com.yiji.openapi.sdk.message.common.manage.BankCodeBindingAddInfoRequest;
import com.yiji.openapi.sdk.message.common.manage.BankCodeBindingAddInfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("bank")
public class BankCardController extends BaseAutowiredController {
    private final String vm_path = "/front/bank/";

    @RequestMapping("signBankCard")
    public String signBankCard(Model model) throws Exception {
        if (SessionLocalManager.getSessionLocal() == null) {
            return "/help/nopermission";
        }
        return vm_path + "signBankCard.vm";
    }

    @RequestMapping("addBankCard")
    public String addBankCard(String signed, Model model) throws Exception {
        if (SessionLocalManager.getSessionLocal() == null) {
            return "/help/nopermission";
        }
        if (!"yes".equals(signed)) {
            model.addAttribute("fail", "未签约");
            return vm_path + "signBankCard.vm";
        }

        List<BankBasicInfo> bankList = new ArrayList<BankBasicInfo>();
        List<BankBasicInfo> bankListOpenApi = this.bankDataService.getDeductBank();
        if (bankListOpenApi != null && bankListOpenApi.size() > 0) {
            List<String> listBankCode = new ArrayList<String>();
            listBankCode.add("ABC");//农业银行
            listBankCode.add("ICBC");//中国工商银行
            listBankCode.add("CCB");//中国建设银行
            listBankCode.add("CEB");//中国光大银行
            listBankCode.add("CIB");//兴业银行
            listBankCode.add("CMBC");//民生银行
            listBankCode.add("CITIC");//中信银行
            listBankCode.add("CMB");//招商银行
            for (BankBasicInfo bank : bankListOpenApi) {
                if (listBankCode.contains(bank.getBankCode())) {
                    bankList.add(bank);
                }
            }
        } else {
            List<BankBasicInfo> bankListPPM = this.bankDataService.getBankBasicInfos();//获取所有银行
            List<String> listBankCode = new ArrayList<String>();
            listBankCode.add("ABC");//农业银行
            listBankCode.add("ICBC");//中国工商银行
            listBankCode.add("CCB");//中国建设银行
            listBankCode.add("CEB");//中国光大银行
            listBankCode.add("CIB");//兴业银行
            listBankCode.add("CMBC");//民生银行
            listBankCode.add("CITIC");//中信银行
            listBankCode.add("CMB");//招商银行
            for (BankBasicInfo bank : bankListPPM) {
                if (listBankCode.contains(bank.getBankCode())) {
                    bankList.add(bank);
                }
            }
        }

        String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
        UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
        PersonalInfoDO personalInfo = null;
        InstitutionsInfoDO institutionsInfo = null;
        if ("GR".equals(userBaseInfo.getType())) {
            try {
                personalInfo = personalInfoManager.query(userBaseId);
            } catch (Exception e) {
                logger.error("查询用户信息失败", e);
            }
            model.addAttribute("realName", personalInfo.getRealName());
            model.addAttribute("bankType", personalInfo.getBankType());
            String bankCardNo = personalInfo.getBankCardNo();
            if (bankCardNo != null && !"".equals(bankCardNo)) {
                model.addAttribute(
                        "bankCardNo",
                        "**** **** ****"
                                + bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length())
                );
            } else {
                model.addAttribute("bankCardNo", bankCardNo);
            }
            model.addAttribute("province", personalInfo.getBankProvince());
            model.addAttribute("city", personalInfo.getBankCity());
            model.addAttribute("type", "GR");
            if (StringUtil.isNotBlank(personalInfo.getBankOpenName())) {
                model.addAttribute("oldBankOpenName", personalInfo.getBankOpenName());
            }
        } else {
            try {
                institutionsInfo = institutionsInfoManager.query(userBaseId);
            } catch (Exception e) {
                logger.error("查询机构异常", e);
            }
            if (StringUtil.isNotBlank(institutionsInfo.getBankOpenName())) {
                model.addAttribute("oldBankOpenName", institutionsInfo.getBankOpenName());
            }
            model.addAttribute("realName", institutionsInfo.getEnterpriseName());
            model.addAttribute("bankType", institutionsInfo.getBankType());
            String bankCardNo = institutionsInfo.getBankCardNo();
            if (bankCardNo != null && !"".equals(bankCardNo)) {
                model.addAttribute("bankCardNo", "**** **** ****" + bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
            } else {
                model.addAttribute("bankCardNo", bankCardNo);
            }
            model.addAttribute("province", institutionsInfo.getBankProvince());
            model.addAttribute("city", institutionsInfo.getBankCity());
            model.addAttribute("type", "JG");
        }
        model.addAttribute("certNo", this.getCertNo(SessionLocalManager.getSessionLocal().getAccountId()));
        model.addAttribute("bankList", bankList);
        return vm_path + "addBankCard.vm";
    }

    @RequestMapping("addBankCardSubmit")
    public String addBankCardSubmit(String bankOpenName, String bankCardNo, String bankType,
                                    String bankKey, String bankProvince, String bankCity,
                                    String bankAddress, Model model) throws Exception {
        if (SessionLocalManager.getSessionLocal() == null) {
            return "/help/nopermission";
        }
        if (StringUtil.isEmpty(bankOpenName) || StringUtil.isEmpty(bankCardNo)
                || StringUtil.isEmpty(bankType) || StringUtil.isEmpty(bankProvince)
                || StringUtil.isEmpty(bankCity)) {
            model.addAttribute("reason", "绑定银行卡信息不全，请检查重新填写！");
            return vm_path + "addBankCardFail.vm";
        }
        String userBaseId = SessionLocalManager.getSessionLocal().getUserBaseId();
        UserBaseInfoDO userBaseInfo = userBaseInfoManager.queryByUserBaseId(userBaseId);
        PersonalInfoDO personalInfo = null;
        InstitutionsInfoDO institutionsInfo = null;
        boolean bool = false;
        if ("GR".equals(userBaseInfo.getType())) {
            personalInfo = personalInfoManager.query(userBaseId);
            if ("".equals(personalInfo.getCertNo())) {
                model.addAttribute("reason", "处理绑卡失败,未进行实名认证申请！");
                return vm_path + "addBankCardFail.vm";
            }
            if (YrdConstants.BankCodes.filteredBankCodes.indexOf(bankType) < 0) {
                OpeningBankQueryOrder queryOrder = new OpeningBankQueryOrder();
                queryOrder.setCardNumber(bankCardNo);
                queryOrder.setCardNumber(bankCardNo);
                queryOrder.setAccountName(bankOpenName);
                queryOrder.setBankCode(bankType);
                queryOrder.setCardType(CardTypeEnum.JJ);
                queryOrder.setCertNo(personalInfo.getCertNo());
                BankInfoResult bankInfoResult = openingBankService.findCardByCardNo(queryOrder);
                boolean isValidate = false;
                if (bankInfoResult.getCardInfo() != null) {
                    if (bankInfoResult.getCardInfo().getCardType() == CardTypeEnum.JJ) {
                        isValidate = true;
                    }
                }
                if (!isValidate) {
                    logger.error("绑定银行卡失败原因:" + bankInfoResult);
                    model.addAttribute("reason", "验证银行卡信息失败！");
                    return vm_path + "addBankCardFail.vm";
                }
            }
        } else {
            institutionsInfo = institutionsInfoManager.query(userBaseId);
        }

        if ("GR".equals(userBaseInfo.getType())) {
            personalInfo.setBankOpenName(bankOpenName);
            personalInfo.setBankCardNo(bankCardNo);
            personalInfo.setBankType(bankType);
            personalInfo.setBankKey(bankKey);
            personalInfo.setBankProvince(bankProvince);
            personalInfo.setBankCity(bankCity);
            personalInfo.setBankAddress(bankAddress);
            //bool = updatePersonal(personalInfo, userBaseInfo);
            //TODO test by dong
            BankCodeBindingAddInfoRequest request = new BankCodeBindingAddInfoRequest();
            request.setUserId(userBaseInfo.getAccountId());
            request.setBankCardNo(bankCardNo);
            request.setBankType(bankType);
            request.setProvince(bankProvince);
            request.setCity(bankCity);
            request.setOrderNo(BusinessNumberUtil.gainOutBizNoNumber());
            
            BankCodeBindingAddInfoResponse result = openApiGatewayService.bankCodeBindingAddInfo(request);
            
        }

        if ("JG".equals(userBaseInfo.getType())) {
            institutionsInfo.setBankOpenName(bankOpenName);
            institutionsInfo.setBankCardNo(bankCardNo);
            institutionsInfo.setBankType(bankType);
            institutionsInfo.setBankKey(bankKey);
            institutionsInfo.setBankProvince(bankProvince);
            institutionsInfo.setBankCity(bankCity);
            institutionsInfo.setBankAddress(bankAddress);
            bool = updateInstitutions(institutionsInfo, userBaseInfo);
        }
        if (StringUtil.isNotBlank(bankCardNo)) {
            model.addAttribute("bankCardNo", bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length()));
        }
        if (bool) {
            String sign = this.sign(userBaseInfo.getAccountId(), bankType, bankCardNo, bankOpenName, userBaseInfo.getRealName(), userBaseInfo.getMobile());
          return vm_path + "addBankCardSuccess.vm";
        } else {
            model.addAttribute("reason", "处理绑卡失败！");
            return vm_path + "addBankCardFail.vm";
        }
    }

    private boolean updatePersonal(PersonalInfoDO personalInfo, UserBaseInfoDO userBaseInfo) throws Exception {
        PersonalReturnEnum personalReturnEnum = personalInfoManager.updatePersonalInfo(personalInfo, userBaseInfo, false, new int[]{});
        if (personalReturnEnum == PersonalReturnEnum.EXECUTE_SUCCESS) {
            return true;
        } else {
            return false;
        }
    }

    private boolean updateInstitutions(InstitutionsInfoDO institutionsInfo, UserBaseInfoDO userBaseInfo) throws Exception {
        InstitutionsReturnEnum institutionsReturnEnum = institutionsInfoManager.updateInstitutionsInfo(institutionsInfo, userBaseInfo, false, new int[]{});
        if (institutionsReturnEnum == InstitutionsReturnEnum.EXECUTE_SUCCESS) {
            return true;
        } else {
            return false;
        }
    }

    public String getCertNo(String accountId) {
        String certNo = "fail";
        if (!accountId.equals("") && accountId != null) {
            certNo = personalInfoManager.getCertNoByAccountId(accountId);
        }
        String certNoMask = "";
        if (StringUtil.isNotBlank(certNo)) {
            certNoMask = certNo.substring(0, 2) + "**************" + certNo.subSequence(certNo.length() - 2, certNo.length());
        }
        return certNoMask;
    }


    private String sign(String userId, String bankNo, String cardNo, String cardName, String certNo, String userPhoneNo) {
        StringBuilder builder = new StringBuilder();
        builder.append("?upUserNo=").append("yzz");
        builder.append("&userId=").append(userId);
        builder.append("&protocalName=").append("《用户协议》");
        builder.append("&protocalUrl=").append("http://127.0.0.1:8083/usercenter/home");
        builder.append("&bankNo=").append(bankNo);
        builder.append("&cardNo=").append(cardNo);
        builder.append("&cardName=").append(cardName);
        builder.append("&certNo=").append(certNo);
        builder.append("&userPhoneNo=").append(userPhoneNo);
        return builder.toString();
    }
}
