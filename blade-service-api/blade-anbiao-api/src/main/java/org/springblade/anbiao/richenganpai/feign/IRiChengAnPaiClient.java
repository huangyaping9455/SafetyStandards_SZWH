package org.springblade.anbiao.richenganpai.feign;

import org.springblade.anbiao.richenganpai.entity.Richenganpai;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/** upload Feign接口类
* @author 呵呵哒
*/
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IRiChengAnPaiClientBack.class
)
public interface IRiChengAnPaiClient {

	String API_PREFIX = "/anbiao/richengback";

	/**
	 * 新增日程待办
	 * @param richenganpaiVO
	 * @return
	 */
	@PostMapping(API_PREFIX + "/insertSelective")
	boolean insertSelective(@Valid @RequestBody Richenganpai richenganpaiVO);

}
