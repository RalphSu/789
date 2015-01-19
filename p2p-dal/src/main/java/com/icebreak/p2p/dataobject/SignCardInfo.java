package com.icebreak.p2p.dataobject;

import java.io.Serializable;
import java.util.Date;

public class SignCardInfo implements Serializable {

    private int id;

    /** 签约号 */
    private String pactNo;
    /** 签约类型 */
    private String signType;
    /** 会员号 */
    private String userId;
    /**
     * 商户号
     */
    private String upUserNo;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡类型
     */
    private String cardType;
    /**
     * 持卡人姓名
     */
    private String cardName;
    /**
     * 证件号
     */
    private String certNo;
    /**
     * 手机号
     */
    private String userPhoneNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行简码
     */
    private String bankShort;

    /** 签约状态 */
    private String status;

    private String province;

    private String city;
    /**
     * 所属银行url
     */
    private String				bankGifUrl;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpUserNo() {
        return upUserNo;
    }

    public void setUpUserNo(String upUserNo) {
        this.upUserNo = upUserNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankShort() {
        return bankShort;
    }

    public void setBankShort(String bankShort) {
        this.bankShort = bankShort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankGifUrl() {
        return bankGifUrl;
    }

    public void setBankGifUrl(String bankGifUrl) {
        this.bankGifUrl = bankGifUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
