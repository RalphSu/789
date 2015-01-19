package com.icebreak.p2p.util;

public class SystemConfig {
	String platformName;
	String productName;
	String outBizNumber;
	String yrdPrefixion;
	String yrdUploadFolder;
	String imageServerUrl;
	String customerServicePhone;
	String customerServiceMobile;
	String yrdTransferLimit;
	String hostUrl;
	String exchangeAccount;
	String profitSharingAccount;
	String tradeBizProductCode;
	String hostHttpUrl;
	String defaultBrokerUserName;
	String yrdFopFontFolder; //fop 字体库
	String withdrawChargeCode;
	String bankB2CChargeCode;
	String platformAddress;
	String customerServiceEmail;

    String brokerIsNumbered ;
	
	String mailServer; //  邮件发送服务器
	String mailServerport; //  邮件发送服务端口
	String mailUsername; //  发件人地址
	String mailPassword; //  发件人邮箱密码
	String mailNickName; //   发件人别名
	String mailSenderName; // 发件人地址
	String productICP;   //ICP号
	String productQQ;   //QQ号
	String loanRequestMail;//接收借款请求邮箱地址
	String AllCommon;//通用参数
	String deafaultBrokerNO;//默认经纪人编号

    String taskTimerIp;

	String projectDefaultThumbnailUrl;  //项目默认缩略图路径（当项目图片不存在时使用默认缩略图）


	public String getDeafaultBrokerNO() {
		return deafaultBrokerNO;
	}

	public void setDeafaultBrokerNO(String deafaultBrokerNO) {
		this.deafaultBrokerNO = deafaultBrokerNO;
	}

	public String getAllCommon() {
		return AllCommon;
	}

	public void setAllCommon(String allCommon) {
		this.AllCommon = allCommon;
	}

	String currentIndustry = "P2P";// 当前行业
	public String getProductICP() {
		return productICP;
	}

	public void setProductICP(String productICP) {
		this.productICP = productICP;
	}
	
	public String getProductQQ() {
		return productQQ;
	}

	public void setProductQQ(String productQQ) {
		this.productQQ = productQQ;
	}

	public String getLoanRequestMail() {
		return loanRequestMail;
	}

	public void setLoanRequestMail(String loanRequestMail) {
		this.loanRequestMail = loanRequestMail;
	}
	public String getCustomerServiceEmail() {
		return this.customerServiceEmail;
	}
	
	public void setCustomerServiceEmail(String customerServiceEmail) {
		this.customerServiceEmail = customerServiceEmail;
	}
	
	public String getYrdFopFontFolder() {
		return this.yrdFopFontFolder;
	}
	
	public void setYrdFopFontFolder(String yrdFopFontFolder) {
		this.yrdFopFontFolder = yrdFopFontFolder;
	}

	public String getPlatformName() {
		return platformName;
	}
	
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getOutBizNumber() {
		return outBizNumber;
	}
	
	public void setOutBizNumber(String outBizNumber) {
		this.outBizNumber = outBizNumber;
	}
	
	public String getYrdPrefixion() {
		return yrdPrefixion;
	}
	
	public void setYrdPrefixion(String yrdPrefixion) {
		this.yrdPrefixion = yrdPrefixion;
	}
	
	public String getYrdUploadFolder() {
		return yrdUploadFolder;
	}
	
	public void setYrdUploadFolder(String yrdUploadFolder) {
		this.yrdUploadFolder = yrdUploadFolder;
	}
	
	public String getImageServerUrl() {
		return imageServerUrl;
	}
	
	public void setImageServerUrl(String imageServerUrl) {
		this.imageServerUrl = imageServerUrl;
	}
	
	public String getCustomerServicePhone() {
		return customerServicePhone;
	}
	
	public void setCustomerServicePhone(String customerServicePhone) {
		this.customerServicePhone = customerServicePhone;
	}
	
	public String getYrdTransferLimit() {
		return yrdTransferLimit;
	}
	
	public void setYrdTransferLimit(String yrdTransferLimit) {
		this.yrdTransferLimit = yrdTransferLimit;
	}
	
	public String getHostUrl() {
		return hostUrl;
	}
	
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	public String getExchangeAccount() {
		return exchangeAccount;
	}
	
	public void setExchangeAccount(String exchangeAccount) {
		this.exchangeAccount = exchangeAccount;
	}
	
	public String getProfitSharingAccount() {
		return profitSharingAccount;
	}
	
	public void setProfitSharingAccount(String profitSharingAccount) {
		this.profitSharingAccount = profitSharingAccount;
	}
	
	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getHostHttpUrl() {
		return hostHttpUrl;
	}
	
	public void setHostHttpUrl(String hostHttpUrl) {
		this.hostHttpUrl = hostHttpUrl;
	}
	
	public String getDefaultBrokerUserName() {
		return defaultBrokerUserName;
	}
	
	public void setDefaultBrokerUserName(String defaultBrokerUserName) {
		this.defaultBrokerUserName = defaultBrokerUserName;
	}
	
	public String getWithdrawChargeCode() {
		return withdrawChargeCode;
	}
	
	public void setWithdrawChargeCode(String withdrawChargeCode) {
		this.withdrawChargeCode = withdrawChargeCode;
	}
	
	public String getBankB2CChargeCode() {
		return bankB2CChargeCode;
	}
	
	public void setBankB2CChargeCode(String bankB2CChargeCode) {
		this.bankB2CChargeCode = bankB2CChargeCode;
	}
	
	public String getPlatformAddress() {
		return platformAddress;
	}
	
	public void setPlatformAddress(String platformAddress) {
		this.platformAddress = platformAddress;
	}
	
	public String getMailServer() {
		return this.mailServer;
	}
	
	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
	
	public String getMailServerport() {
		return this.mailServerport;
	}
	
	public void setMailServerport(String mailServerport) {
		this.mailServerport = mailServerport;
	}
	
	public String getMailUsername() {
		return this.mailUsername;
	}
	
	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}
	
	public String getMailPassword() {
		return this.mailPassword;
	}
	
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	
	public String getMailNickName() {
		return this.mailNickName;
	}
	
	public void setMailNickName(String mailNickName) {
		this.mailNickName = mailNickName;
	}
	
	public String getMailSenderName() {
		return this.mailSenderName;
	}
	
	public void setMailSenderName(String mailSenderName) {
		this.mailSenderName = mailSenderName;
	}
	public String getCurrentIndustry() {
		return this.currentIndustry;
	}
	
	public void setCurrentIndustry(String currentIndustry) {
		this.currentIndustry = currentIndustry;
	}

    public String getBrokerIsNumbered() {
        return brokerIsNumbered;
    }

    public void setBrokerIsNumbered(String brokerIsNumbered) {
        this.brokerIsNumbered = brokerIsNumbered;
    }


    public String getTaskTimerIp() {
        return taskTimerIp;
    }

    public void setTaskTimerIp(String taskTimerIp) {
        this.taskTimerIp = taskTimerIp;
    }

    public String getCustomerServiceMobile() {
		return customerServiceMobile;
	}

	public void setCustomerServiceMobile(String customerServiceMobile) {
		this.customerServiceMobile = customerServiceMobile;
	}

	public String getProjectDefaultThumbnailUrl() {
		return projectDefaultThumbnailUrl;
	}

	public void setProjectDefaultThumbnailUrl(String projectDefaultThumbnailUrl) {
		this.projectDefaultThumbnailUrl = projectDefaultThumbnailUrl;
	}
}
