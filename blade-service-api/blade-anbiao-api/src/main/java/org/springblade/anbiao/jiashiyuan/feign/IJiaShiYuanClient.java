package org.springblade.anbiao.jiashiyuan.feign;

import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuanTrain;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/** upload Feign接口类
*/
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IJiaShiYuanClientBack.class
)
public interface IJiaShiYuanClient {


	String API_PREFIX = "/anbiao/jiashiyuan";

	/**
	 * 根据企业ID获取驾驶员签名
	 * @param deptId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/selectJiaShiYuanTrain")
	List<JiaShiYuanTrain> selectJiaShiYuanTrain(@RequestParam("deptId") Integer deptId);
}
