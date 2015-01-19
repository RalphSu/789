package com.icebreak.p2p.sign;

import com.icebreak.p2p.dataobject.SignCardInfo;

import java.util.List;

/**
 * Created by asdfasdfa on 2014/11/20.
 */
public interface SignCardInfoService {
    /**
     * 添加以签约银行卡
     * @param signCardInfo
     */
    public void add(SignCardInfo signCardInfo);

    /**
     * 根据流水号解约
     * @param pactNo
     */
    public void delete(String pactNo);

    /**
     * 根据流水号查询
     * @param pactNo
     * @return
     */
    public SignCardInfo queryByPactNo(String pactNo);

    /**
     * 动态查询
     * @param signCardInfo
     * @return
     */
    public List<SignCardInfo> queryList(SignCardInfo signCardInfo);

    public SignCardInfo queryOnly(String cardName, String certNo, String cardNo, String userId, String signType);
}
