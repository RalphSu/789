package com.icebreak.p2p.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icebreak.p2p.charge.ChargeService;
import com.icebreak.p2p.dataobject.UserRole;
import com.icebreak.p2p.user.UserService;
import com.icebreak.p2p.ws.enums.SysUserRoleEnum;

public class ChargeTask extends AbstractTask {
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());
	private ChargeService	chargeService;
	
	private UserService		userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setChargeService(ChargeService chargeService) {
		this.chargeService = chargeService;
	}
	
	@Override
	public synchronized void run() {
        if(!canRun()){
            return;
        }

		List<UserRole> userRoles = userService
			.getUserRoles(
				new String[] { "normal", "freeze" },
				new String[] { SysUserRoleEnum.BROKER.getRoleCode(),
						SysUserRoleEnum.GUARANTEE.getRoleCode(),
						SysUserRoleEnum.INVESTOR.getRoleCode(),
						SysUserRoleEnum.LOANER.getRoleCode(),
						SysUserRoleEnum.MARKETING.getRoleCode(),
						SysUserRoleEnum.SPONSOR.getRoleCode() });
		for (UserRole userRole : userRoles) {
			try {
				chargeService.charge(userRole.getUserId(), userRole.getRoleId());
			} catch (Exception e) {
				logger.error("收费出现异常", e);
			}
		}
	}
}
