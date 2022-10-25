package org.springblade.anbiao.guanlijigouherenyuan.feign;

import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @program: SafetyStandards
 * @description: IPersonnelClientlBack
 **/
@Component
public class IPersonnelClientBack implements IPersonnelClient {
	@Override
	public Boolean saveOrUpdate(@Valid Personnel personnel) {
		return null;
	}

	@Override
	public Boolean updateDelByUserId(String userId) {
		return null;
	}

	@Override
	public Personnel selectByUserIdAdnByDeptId(String userId, String deptId) {
		return null;
	}

	@Override
	public Boolean insertPersonnelSelective(Personnel personnel) {
		return null;
	}

	@Override
	public JiaShiYuan driverLogin(String account, String password) {
		return null;
	}

	@Override
	public JiaShiYuan getDriverOpenId(String openid) {
		return null;
	}

	@Override
	public void bindDriverOpenId(String account, String openid) {

	}

}
