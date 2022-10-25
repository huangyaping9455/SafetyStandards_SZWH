package org.springblade.anbiao.guanlijigouherenyuan.feign;

import org.springblade.anbiao.guanlijigouherenyuan.entity.Departmentpost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/** upload Feign接口类
*/
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IDepartmentpostClientBack.class
)
public interface IDepartmentpostClient {


	String API_PREFIX = "/anbiao/depback";

	@PostMapping(API_PREFIX + "/insertDepart")
	Boolean insertDepart(@Valid @RequestBody Departmentpost departmentpost);

	@PostMapping(API_PREFIX + "/updateByPostIdDepart")
	Boolean updateByPostIdDepart(@Valid @RequestBody Departmentpost departmentpost);

	/**
	 * 根据岗位id获取岗位信息
	 * @param deptId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/selectByDeptId")
	Departmentpost selectByDeptId(@RequestParam("deptId") String deptId);
}
