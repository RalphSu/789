package com.icebreak.p2p.daointerface;

import com.icebreak.p2p.dataobject.SignCardInfo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by asdfasdfa on 2014/11/20.
 */
public interface SignCardInfoDao {

    /**
     * 添加以签约银行卡
     * @param signCardInfo
     * @throws DataAccessException
     */
    public void add(SignCardInfo signCardInfo) throws DataAccessException;

    /**
     * 根据流水号解约
     * @param pactNo
     * @throws DataAccessException
     */
    public void delete(String pactNo) throws DataAccessException;

    /**
     * 根据流水号查询
     * @param pactNo
     * @return
     * @throws DataAccessException
     */
    public SignCardInfo queryByPactNo(String pactNo) throws DataAccessException;

    /**
     * 动态查询
     * @param signCardInfo
     * @return
     * @throws DataAccessException
     */
    public List<SignCardInfo> queryList(SignCardInfo signCardInfo) throws  DataAccessException;

    public SignCardInfo queryOnly(String cardName, String certNo, String cardNo, String userId, String signType) throws DataAccessException;

}
