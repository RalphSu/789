package com.icebreak.p2p.common.services.impl;

import com.icebreak.p2p.common.services.SysFunctionConfigService;
import com.icebreak.util.env.Env;
import com.icebreak.util.lang.util.StringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("sysFunctionConfigService")
public class SysFunctionConfigServiceImpl implements SysFunctionConfigService, InitializingBean {

	private String			allEconomicMan;


    private String tradeIsCharge;

    private String tradeChangeAccountId;

	private static boolean	isInit				= false;
	private static boolean	_isAllEconomicMan	= false;
    private static boolean _isTradeFeeCharge = false;
    private static String _tradeChangeAccountId = "20140610020000502967";


	
	/**
	 * @return
	 * @see com.icebreak.p2p.common.services.SysFunctionConfigService#isAllEconomicMan()
	 */
	@Override
	public boolean isAllEconomicMan() {
		try {
			afterPropertiesSet();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return _isAllEconomicMan;
	}
	
	/**
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!isInit) {
			isInit = true;
			if (StringUtil.equalsIgnoreCase(allEconomicMan, "Y")) {
				_isAllEconomicMan = true;
			} else {
				_isAllEconomicMan = false;
			}

            if (StringUtil.equalsIgnoreCase(tradeIsCharge, "Y")) {
                _isTradeFeeCharge = true;
            } else {
                _isTradeFeeCharge = false;
            }

            if (!StringUtil.equalsIgnoreCase(tradeChangeAccountId,
                    "")) {
                _tradeChangeAccountId = tradeChangeAccountId;
            }
            if (Env.isOnline()) {

            }
		}
		
	}


    @Override
    public boolean isTradeFeeCharge() {
        return _isTradeFeeCharge;
    }

    @Override
    public String getTradeChargeAccountId() {
        return _tradeChangeAccountId;
    }
}
