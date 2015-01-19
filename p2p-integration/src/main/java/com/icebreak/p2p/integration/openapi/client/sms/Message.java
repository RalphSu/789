package com.icebreak.p2p.integration.openapi.client.sms;

import com.icebreak.p2p.util.MD5Util;
import com.yiji.openapi.sdk.Constants;
import com.yiji.openapi.sdk.util.Encodes;

import java.util.UUID;
public class Message {

    private String account="";
    private String password="";
    private String msgid= UUID.randomUUID().toString();
    private String phones;
    private String content;
    private String sign="";
    private String subcode;
    private String sendtime;


    public String toXMLMessage() {
        StringBuffer msg = new StringBuffer();
        msg.append("message=<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<message>")
                .append("<account>")
                .append(account)
                .append("</account>")
                .append("<password>")
                .append(MD5Util.getMD5_32(password))
                .append("</password>")
                .append("<msgid>")
                .append(msgid)
                .append("</msgid>")
                .append("<phones>")
                .append(phones)
                .append("</phones>")
                .append("<content>")
                .append(Encodes.urlEncode(content, Constants.CHARSET))
                .append("</content>")
                .append("<sign>")
                .append(Encodes.urlEncode(sign, Constants.CHARSET))
                .append("</sign>")
                .append("<subcode>")
                .append("</subcode>")
                .append("<sendtime>")
                .append("</sendtime>")
                .append("</message>");
        return msg.toString();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}