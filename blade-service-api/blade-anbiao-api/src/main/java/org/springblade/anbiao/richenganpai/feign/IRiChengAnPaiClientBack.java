package org.springblade.anbiao.richenganpai.feign;

import org.springblade.anbiao.richenganpai.entity.Richenganpai;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @program: SafetyStandards
 * @description: IPersonnelClientlBack
 **/
@Component
public class IRiChengAnPaiClientBack implements IRiChengAnPaiClient {

	@Override
	public boolean insertSelective(@Valid Richenganpai richenganpaiVO) {
		return false;
	}

}
