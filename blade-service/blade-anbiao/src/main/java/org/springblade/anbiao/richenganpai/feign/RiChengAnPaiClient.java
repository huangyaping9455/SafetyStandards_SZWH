package org.springblade.anbiao.richenganpai.feign;

import lombok.AllArgsConstructor;
import org.springblade.anbiao.richenganpai.entity.Richenganpai;
import org.springblade.anbiao.richenganpai.service.IRichenganpaiService;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @program: SafetyStandards
 * @description: PersonnelClient
 **/
@ApiIgnore
@RestController
@AllArgsConstructor
public class RiChengAnPaiClient implements IRiChengAnPaiClient{

	private IRichenganpaiService richenganpaiService;

	@Override
	public boolean insertSelective(@Valid Richenganpai richenganpaiVO) {
		return richenganpaiService.insertSelective(richenganpaiVO);
	}


}
