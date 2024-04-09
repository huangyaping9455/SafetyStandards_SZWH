package org.springblade.anbiao.jiashiyuan.feign;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springblade.anbiao.jiashiyuan.service.IJiaShiYuanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author Administrator
 * @program: SafetyStandards
 * @description: JiaShiYuanClient接口
 **/
@ApiIgnore
@RestController
@AllArgsConstructor
public class JiaShiYuanClient implements IJiaShiYuanClient {

	private IJiaShiYuanService service;

	@Override
	@GetMapping(API_PREFIX + "/selectJiaShiYuanTrain")
	@ApiOperation(value = "根据单位id进行查询数据", notes = "传入deptId", position = 1)
	public List<JiaShiYuanTrain> selectJiaShiYuanTrain(Integer deptId,String driverId) {
		return service.selectJiaShiYuanTrain(deptId,driverId);
	}
}
