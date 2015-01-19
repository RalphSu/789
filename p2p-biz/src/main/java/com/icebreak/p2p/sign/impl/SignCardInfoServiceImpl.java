package com.icebreak.p2p.sign.impl;

import com.icebreak.p2p.daointerface.SignCardInfoDao;
import com.icebreak.p2p.dataobject.SignCardInfo;
import com.icebreak.p2p.sign.SignCardInfoService;
import com.icebreak.util.lang.exception.ResultException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("signCardInfoService")
public class SignCardInfoServiceImpl implements SignCardInfoService {

    @Resource
    private SignCardInfoDao signCardInfoDao;

    @Override
    public void add(SignCardInfo signCardInfo) {
        if(null == signCardInfo) {
            throw new ResultException("参数不能为空");
        }
        signCardInfoDao.add(signCardInfo);
    }

    @Override
    public void delete(String pactNo) {
        signCardInfoDao.delete(pactNo);
    }

    @Override
    public SignCardInfo queryByPactNo(String pactNo) {
        return signCardInfoDao.queryByPactNo(pactNo);
    }

    @Override
    public List<SignCardInfo> queryList(SignCardInfo signCardInfo) {
        if(null == signCardInfo) {
            throw new ResultException("参数不能为空");
        }
        return signCardInfoDao.queryList(signCardInfo);
    }

    @Override
    public SignCardInfo queryOnly(String cardName, String certNo, String cardNo, String userId, String signType) {
        return signCardInfoDao.queryOnly(cardName, certNo, cardNo, userId, signType);
    }
}
