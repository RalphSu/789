package com.yiji.openapi.sdk.message.common.trade;

import com.yiji.openapi.sdk.message.BaseRequest;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class QueryPayChannelApiRequest extends BaseRequest {

		
		    @Length(max=20,message ="Api类型长度不能超过20" )
	    private String strategy;
	
		
	    @NotEmpty(message ="银行简写不能为空" )
		    @Length(max=20,message ="银行简写长度不能超过20" )
	    private String bankCode;
	
		
		    @Length(max=20,message ="银行名称长度不能超过20" )
	    private String bankName;
	
		
	    @NotEmpty(message ="渠道类型不能为空" )
		    @Length(max=20,message ="渠道类型长度不能超过20" )
	    private String apiType;
	
		
		    @Length(max=30,message ="渠道类型长度不能超过30" )
	    private String channelNo;
	
		
	    @NotEmpty(message ="卡类型不能为空" )
		    @Length(max=15,message ="卡类型长度不能超过15" )
	    private String cardType;
	
		
	    @NotEmpty(message ="支付类型不能为空" )
		    @Length(max=10,message ="支付类型长度不能超过10" )
	    private String payType;
	
		
	    @NotEmpty(message ="对公对私不能为空" )
		    @Length(max=2,message ="对公对私长度不能超过2" )
	    private String publicTag;
	
		
		    @Length(max=20,message ="环境长度不能超过20" )
	    private String env;
	
		
		    @Length(max=2,message ="状态长度不能超过2" )
	    private String state;
	
		
	    @NotEmpty(message ="API所属不能为空" )
		    @Length(max=20,message ="API所属长度不能超过20" )
	    private String owner;
	
		
		    @Length(max=2,message ="批量否长度不能超过2" )
	    private String batch;
	
		
		    @Length(max=2,message ="渠道特殊标号长度不能超过2" )
	    private String extFlag;
	
		
		    @Length(max=30,message ="产品子集长度不能超过30" )
	    private String subOwner;
	
		
	
	public String  getStrategy(){
		return strategy;
	}
	
	public void setStrategy(String strategy){
		this.strategy = strategy;
	}
	
	public String  getBankCode(){
		return bankCode;
	}
	
	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}
	
	public String  getBankName(){
		return bankName;
	}
	
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	
	public String  getApiType(){
		return apiType;
	}
	
	public void setApiType(String apiType){
		this.apiType = apiType;
	}
	
	public String  getChannelNo(){
		return channelNo;
	}
	
	public void setChannelNo(String channelNo){
		this.channelNo = channelNo;
	}
	
	public String  getCardType(){
		return cardType;
	}
	
	public void setCardType(String cardType){
		this.cardType = cardType;
	}
	
	public String  getPayType(){
		return payType;
	}
	
	public void setPayType(String payType){
		this.payType = payType;
	}
	
	public String  getPublicTag(){
		return publicTag;
	}
	
	public void setPublicTag(String publicTag){
		this.publicTag = publicTag;
	}
	
	public String  getEnv(){
		return env;
	}
	
	public void setEnv(String env){
		this.env = env;
	}
	
	public String  getState(){
		return state;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public String  getOwner(){
		return owner;
	}
	
	public void setOwner(String owner){
		this.owner = owner;
	}
	
	public String  getBatch(){
		return batch;
	}
	
	public void setBatch(String batch){
		this.batch = batch;
	}
	
	public String  getExtFlag(){
		return extFlag;
	}
	
	public void setExtFlag(String extFlag){
		this.extFlag = extFlag;
	}
	
	public String  getSubOwner(){
		return subOwner;
	}
	
	public void setSubOwner(String subOwner){
		this.subOwner = subOwner;
	}
}
