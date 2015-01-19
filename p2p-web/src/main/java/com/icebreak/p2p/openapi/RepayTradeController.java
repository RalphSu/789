package com.icebreak.p2p.openapi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icebreak.p2p.base.BaseAutowiredController;
import com.icebreak.p2p.integration.openapi.result.RepayReturnRequest;
import com.icebreak.p2p.util.MiscUtil;
import com.icebreak.p2p.web.util.WebUtil;

@Controller
@RequestMapping("boot")
public class RepayTradeController extends BaseAutowiredController {



	public static class RepayTradeSimpleInfo {
		private String orderNo;
		private String subTradeNo;
		private String subTradeName;
		private String subTradeStatu;
		private String subTradeStatuDesc;

		private String payerUserId;
		private String payeeUserId;
		private String tradeAmount;
		private String tradeMemo;

		private String transferDate;
		private String transferResult;
		private String transferMessage;

		public String getOrderNo() {
			return this.orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getSubTradeNo() {
			return this.subTradeNo;
		}

		public void setSubTradeNo(String subTradeNo) {
			this.subTradeNo = subTradeNo;
		}

		public String getSubTradeName() {
			return this.subTradeName;
		}

		public void setSubTradeName(String subTradeName) {
			this.subTradeName = subTradeName;
		}

		public String getSubTradeStatu() {
			return this.subTradeStatu;
		}

		public void setSubTradeStatu(String subTradeStatu) {
			this.subTradeStatu = subTradeStatu;
		}

		public String getSubTradeStatuDesc() {
			return this.subTradeStatuDesc;
		}

		public void setSubTradeStatuDesc(String subTradeStatuDesc) {
			this.subTradeStatuDesc = subTradeStatuDesc;
		}

		public String getPayerUserId() {
			return this.payerUserId;
		}

		public void setPayerUserId(String payerUserId) {
			this.payerUserId = payerUserId;
		}

		public String getPayeeUserId() {
			return this.payeeUserId;
		}

		public void setPayeeUserId(String payeeUserId) {
			this.payeeUserId = payeeUserId;
		}

		public String getTradeAmount() {
			return this.tradeAmount;
		}

		public void setTradeAmount(String tradeAmount) {
			this.tradeAmount = tradeAmount;
		}

		public String getTradeMemo() {
			return this.tradeMemo;
		}

		public void setTradeMemo(String tradeMemo) {
			this.tradeMemo = tradeMemo;
		}

		public String getTransferDate() {
			return this.transferDate;
		}

		public void setTransferDate(String transferDate) {
			this.transferDate = transferDate;
		}

		public String getTransferResult() {
			return this.transferResult;
		}

		public void setTransferResult(String transferResult) {
			this.transferResult = transferResult;
		}

		public String getTransferMessage() {
			return this.transferMessage;
		}

		public void setTransferMessage(String transferMessage) {
			this.transferMessage = transferMessage;
		}

	}

	public static class RepayTradeShardInfo {
		private String orderNo;
		private String shardTradeName;
		private String shardTradeStatu;
		private String shardTradeStatuName;
		private String payUserId;
		private String recUserId;
		private String shardAmount;
		private String shardMemo;
		private String transferDate;
		private String transferResult;
		private String transferMessage;

		public String getOrderNo() {
			return this.orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getShardTradeName() {
			return this.shardTradeName;
		}

		public void setShardTradeName(String shardTradeName) {
			this.shardTradeName = shardTradeName;
		}

		public String getShardTradeStatu() {
			return this.shardTradeStatu;
		}

		public void setShardTradeStatu(String shardTradeStatu) {
			this.shardTradeStatu = shardTradeStatu;
		}

		public String getShardTradeStatuName() {
			return this.shardTradeStatuName;
		}

		public void setShardTradeStatuName(String shardTradeStatuName) {
			this.shardTradeStatuName = shardTradeStatuName;
		}

		public String getPayUserId() {
			return this.payUserId;
		}

		public void setPayUserId(String payUserId) {
			this.payUserId = payUserId;
		}

		public String getRecUserId() {
			return this.recUserId;
		}

		public void setRecUserId(String recUserId) {
			this.recUserId = recUserId;
		}

		public String getShardAmount() {
			return this.shardAmount;
		}

		public void setShardAmount(String shardAmount) {
			this.shardAmount = shardAmount;
		}

		public String getShardMemo() {
			return this.shardMemo;
		}

		public void setShardMemo(String shardMemo) {
			this.shardMemo = shardMemo;
		}

		public String getTransferDate() {
			return this.transferDate;
		}

		public void setTransferDate(String transferDate) {
			this.transferDate = transferDate;
		}

		public String getTransferResult() {
			return this.transferResult;
		}

		public void setTransferResult(String transferResult) {
			this.transferResult = transferResult;
		}

		public String getTransferMessage() {
			return this.transferMessage;
		}

		public void setTransferMessage(String transferMessage) {
			this.transferMessage = transferMessage;
		}

	}

}
