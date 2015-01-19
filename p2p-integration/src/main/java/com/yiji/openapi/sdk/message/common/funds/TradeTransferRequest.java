package com.yiji.openapi.sdk.message.common.funds;

import com.yiji.openapi.sdk.message.BaseRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public class TradeTransferRequest extends BaseRequest {
	
	@Length(max = 50, message = "交易名称长度不能超过50")
	private String tradeName;
	
	@Length(max = 20, message = "买家长度不能超过20")
	private String buyerUserId;
	
	@Length(max = 20, message = "卖家长度不能超过20")
	private String sellerUserId;
	
	@Length(max = 20, message = "付款方ID长度不能超过20")
	private String payerUserId;
	
	@Length(max = 20, message = "业务产品编号长度不能超过20")
	private String tradeBizProductCode;
	
	@Length(max = 20, message = "平台商户长度不能超过20")
	private String tradeMerchantId;
	
	@Length(max = 20, message = "参与者长度不能超过20")
	private String tradePartnerId;
	
	@Length(max = 20, message = "父交易号长度不能超过20")
	private String parentNo;
	
	@NotEmpty(message = "流程产品不能为空")
	@Length(max = 30, message = "流程产品长度不能超过30")
	private String product;
	
	@NotEmpty(message = "交易类型不能为空")
	@Length(max = 20, message = "交易类型长度不能超过20")
	private String tradeType;
	
	@NotEmpty(message = "交易子类型不能为空")
	@Length(max = 20, message = "交易子类型长度不能超过20")
	private String gatheringType;
	
	@Length(max = 15, message = "交易额长度不能超过15")
	private String tradeAmount;
	
	@Length(max = 10, message = "币种长度不能超过10")
	private String currency;
	
	@Length(max = 1024, message = "交易备注长度不能超过1024")
	private String tradeMemo;
	
	@Length(max = 200, message = "买家备注长度不能超过200")
	private String buyerMarkerMemo;
	
	@Length(max = 200, message = "卖家备注长度不能超过200")
	private String sellerMarkerMemo;
	
	private List<Map<String, String>> goods;
	
	@Length(max = 20, message = "商品的外部ID长度不能超过20")
	private String outId;
	
	@Length(max = 30, message = "商品名称长度不能超过30")
	private String name;
	
	@Length(max = 200, message = "商品详情长度不能超过200")
	private String memo;
	
	@Length(max = 20, message = "商品单价长度不能超过20")
	private String price;
	
	@Length(max = 10, message = "商品数量长度不能超过10")
	private String quantity;
	
	@Length(max = 20, message = "商品其它费用长度不能超过20")
	private String otherFee;
	
	@Length(max = 10, message = "商品单位长度不能超过10")
	private String unit;
	
	@Length(max = 200, message = "商品描述网址长度不能超过200")
	private String detailUrl;
	
	@Length(max = 200, message = "商品来源网址长度不能超过200")
	private String referUrl;
	
	@Length(max = 20, message = "商品类目长度不能超过20")
	private String category;
	
	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public String getBuyerUserId() {
		return buyerUserId;
	}
	
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	
	public String getSellerUserId() {
		return sellerUserId;
	}
	
	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	
	public String getPayerUserId() {
		return payerUserId;
	}
	
	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}
	
	public String getTradeBizProductCode() {
		return tradeBizProductCode;
	}
	
	public void setTradeBizProductCode(String tradeBizProductCode) {
		this.tradeBizProductCode = tradeBizProductCode;
	}
	
	public String getTradeMerchantId() {
		return tradeMerchantId;
	}
	
	public void setTradeMerchantId(String tradeMerchantId) {
		this.tradeMerchantId = tradeMerchantId;
	}
	
	public String getTradePartnerId() {
		return tradePartnerId;
	}
	
	public void setTradePartnerId(String tradePartnerId) {
		this.tradePartnerId = tradePartnerId;
	}
	
	public String getParentNo() {
		return parentNo;
	}
	
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getTradeType() {
		return tradeType;
	}
	
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	public String getGatheringType() {
		return gatheringType;
	}
	
	public void setGatheringType(String gatheringType) {
		this.gatheringType = gatheringType;
	}
	
	public String getTradeAmount() {
		return tradeAmount;
	}
	
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getTradeMemo() {
		return tradeMemo;
	}
	
	public void setTradeMemo(String tradeMemo) {
		this.tradeMemo = tradeMemo;
	}
	
	public String getBuyerMarkerMemo() {
		return buyerMarkerMemo;
	}
	
	public void setBuyerMarkerMemo(String buyerMarkerMemo) {
		this.buyerMarkerMemo = buyerMarkerMemo;
	}
	
	public String getSellerMarkerMemo() {
		return sellerMarkerMemo;
	}
	
	public void setSellerMarkerMemo(String sellerMarkerMemo) {
		this.sellerMarkerMemo = sellerMarkerMemo;
	}
	
	public List<Map<String, String>> getGoods() {
		return goods;
	}
	
	public void setGoods(List<Map<String, String>> goods) {
		this.goods = goods;
	}
	
	public String getOutId() {
		return outId;
	}
	
	public void setOutId(String outId) {
		this.outId = outId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getOtherFee() {
		return otherFee;
	}
	
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getDetailUrl() {
		return detailUrl;
	}
	
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	public String getReferUrl() {
		return referUrl;
	}
	
	public void setReferUrl(String referUrl) {
		this.referUrl = referUrl;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
