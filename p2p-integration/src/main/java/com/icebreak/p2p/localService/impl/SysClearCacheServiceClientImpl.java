package com.icebreak.p2p.localService.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icebreak.p2p.localService.SysClearCacheServiceClient;
import com.icebreak.p2p.ws.service.SysClearCacheWebService;

@Service("sysClearCacheServiceClient")
public class SysClearCacheServiceClientImpl implements SysClearCacheServiceClient {
    protected final Logger logger	= LoggerFactory.getLogger(this.getClass());
    @Autowired
    SysClearCacheWebService sysClearCacheWebServiceClientOne;
    @Autowired
    SysClearCacheWebService sysClearCacheWebServiceClientTwo;
    @Autowired
    SysClearCacheWebService sysClearCacheWebService;

    @Override
    public void clearCache()  {
        try{
            //本地清理两次缓存
            sysClearCacheWebService.clearCache();
            sysClearCacheWebServiceClientOne.clearCache();
            sysClearCacheWebServiceClientTwo.clearCache();
        }catch (Exception ce){
            logger.error("未知异常:e={}", ce.getMessage(), ce);

        }

    }
}
