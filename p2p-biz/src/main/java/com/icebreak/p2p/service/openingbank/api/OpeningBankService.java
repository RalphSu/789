package com.icebreak.p2p.service.openingbank.api;

import com.icebreak.p2p.service.openingbank.order.OpeningBankByDisNameAndBankIdOrder;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryByDistrictOrder;
import com.icebreak.p2p.service.openingbank.order.OpeningBankQueryOrder;
import com.icebreak.p2p.service.openingbank.result.BankIdRestult;
import com.icebreak.p2p.service.openingbank.result.BankInfoResult;
import com.icebreak.p2p.service.openingbank.result.BankInfosResult;

import javax.jws.WebService;

@WebService
public interface OpeningBankService {
	
	/**
	 * 通过银行卡号查询该卡信息
	 * @param order 卡号
	 * @return 当前银行所有支行机构列表
	 */
	public BankInfoResult findCardByCardNo(OpeningBankQueryOrder order);
	
	/**
	 * 通过银行卡号查询该卡所在银行的所有支行机构信息，以及该卡的相关属性
	 * @param order 卡号
	 * @return 当前银行所有支行机构列表
	 */
	public BankInfoResult findBankInfosByCardNo(OpeningBankQueryOrder order);
	
	/**
	 * 通过地区编号，查询该地区下所有的银行机构列表
	 * @param order  
	 * @return  当查询不到数据时，success=false;resCode=DATABASE_DATE_NULL
	 */
	public BankInfosResult findBankInfoByDistrictNo(OpeningBankQueryByDistrictOrder order);
	
	/**
	 * 查询所有地区信息
	 * @return 当输入districtId为null时返最上层地区下的地区信息（当前是所有的省/直辖市info）
	 */
	public BankInfoResult getAllDistrict();
	
	/**
	 * 通过卡号得到银行英文简称
	 * @param order
	 * @return
	 */
	public BankIdRestult getBankIdByCardNo(OpeningBankQueryOrder order);
	
	/**
	 * 根据银行地区名称和银行ID查询开户行的联行号,（开户行为依据地区信息默认的）
	 * @param order
	 * @return
	 */
	public BankInfoResult getOpeningBankByDistrictNameAndBankId(OpeningBankByDisNameAndBankIdOrder order);


	/**
	 * 根据卡号和银行编码查询
	 * @param order
	 * @return
	 */
	public BankInfoResult findBankInfoByBankInfo(OpeningBankQueryOrder order);

    public void clearCache();
	
}
