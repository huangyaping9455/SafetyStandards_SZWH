package org.springblade.anbiao.guanlijigouherenyuan.feign;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.guanlijigouherenyuan.service.IPersonnelService;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springblade.anbiao.repairs.service.IAnbiaoRepairsPersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @program: SafetyStandards
 * @description: PersonnelClient
 **/
@ApiIgnore
@RestController
@AllArgsConstructor
public class PersonnelClient implements IPersonnelClient{

	private IPersonnelService personnelService;

	private IJiaShiYuanService service;

	private IAnbiaoRepairsPersonService repairsPersonService;

	@Override
	@PostMapping(API_PREFIX + "/saveOrUpdate")
	@ApiOperation(value = "新增or编辑(feign使用)", notes = "传入personnel", position = 1)
	public Boolean saveOrUpdate(@RequestBody Personnel personnel) {
		return personnelService.saveOrUpdate(personnel);
	}

	@Override
	@PostMapping(API_PREFIX + "/updateDelByUserId")
	@ApiOperation(value = "根据人员id修改信息(feign使用)", notes = "传入userId", position = 2)
	public Boolean updateDelByUserId(String userId) {
		return personnelService.updateDelByUserId(userId);
	}

	@Override
	@GetMapping(API_PREFIX + "/selectByUserIdAdnByDeptId")
	@ApiOperation(value = "根据人员id，单位id获取唯一数据(feign使用)", notes = "传入userId,deptId", position = 3)
	public Personnel selectByUserIdAdnByDeptId(String userId, String deptId) {
		return personnelService.selectByUserIdAdnByDeptId(userId,deptId);
	}

	@Override
	public Boolean insertPersonnelSelective(@RequestBody Personnel personnel) {
		return personnelService.insertSelective(personnel);
	}

	@Override
	@GetMapping(API_PREFIX + "/driverLogin")
	public JiaShiYuan driverLogin(String account, String password) {
		return service.getDriver(account, password);
	}

	@Override
	@GetMapping(API_PREFIX + "/getDriverOpenId")
	public JiaShiYuan getDriverOpenId(String openid){
		return service.selectDriverByopenId(openid);
	}

	@Override
	@GetMapping(API_PREFIX + "/bindDriverOpenId")
	public void bindDriverOpenId(String account,String openid){
		service.bindDriverOpenId(account,openid);
	}

	@Override
	@GetMapping(API_PREFIX + "/getPerson")
	public AnbiaoRepairsPerson getPerson(String account, String password) {
		return repairsPersonService.getPerson(account, password);
	}

}
