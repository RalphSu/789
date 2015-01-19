package com.icebreak.p2p.task;

import com.icebreak.p2p.util.AppConstantsUtil;
import com.icebreak.p2p.util.StringUtil;
import com.icebreak.util.lang.ip.IPUtil;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractBaseJob {
    private static boolean isRun = false;


    public AbstractBaseJob(){
        init();
    }

    protected boolean canRun() {
        return isRun;
    }

    private static   void init() {
        String runIp = AppConstantsUtil.getTaskTimerIp();
        if (StringUtil.isEmpty(runIp)) {
            isRun = true;
            return;
        }

        Collection<InetAddress> inetAddresses;
        inetAddresses = IPUtil.getAllHostIPV4Address();
        Iterator<InetAddress> it = inetAddresses.iterator();
        while (it.hasNext()) {
            InetAddress inetAddress = it.next();
            if (StringUtil.equals(runIp, inetAddress.getHostAddress())) {
                isRun = true;
                return;
            }
        }
        isRun = false;
    }

    public static void clearCache(){
        init();
    }

}
