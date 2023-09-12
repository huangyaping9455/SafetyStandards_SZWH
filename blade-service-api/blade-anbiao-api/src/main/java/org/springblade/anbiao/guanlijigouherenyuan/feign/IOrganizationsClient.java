package org.springblade.anbiao.guanlijigouherenyuan.feign;

import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.guanlijigouherenyuan.entity.Organizations;
import org.springblade.anbiao.guanlijigouherenyuan.vo.OrganizationsVO;
import org.springblade.anbiao.qiyeshouye.entity.BaobiaoZhengfuQiye;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IOrganizationsClientBack.class
)
/**
* @Description:
*/
public interface IOrganizationsClient {

	String API_PREFIX = "/anbiao/organizations";

	@PostMapping(API_PREFIX + "/delByDeptId")
	Boolean delByDeptId(@RequestParam("deptId") String deptId);

//	@PostMapping(API_PREFIX + "/selectZFRenyuan")
//	Organization selectZFRenyuan(@RequestParam("id") String id);

	@PostMapping(API_PREFIX + "/getZFQY")
	List<OrganizationsVO> getZFQY();

	@PostMapping(API_PREFIX + "/getZFQYOrderById")
	List<OrganizationsVO> getZFQYOrderById();

	@PostMapping(API_PREFIX + "/insertSelective")
	Boolean insertSelective(OrganizationsVO organizationsVO);

    @PostMapping(API_PREFIX + "/selectByDeptId")
    Organizations selectByDeptId(@RequestParam("deptId") String deptId);

	@PostMapping(API_PREFIX + "/selectByDeptIdInfo")
	Organizations selectByDeptIdInfo(@RequestParam("deptId") String deptId);

	@PostMapping(API_PREFIX + "/selectParentDeptById")
    Organizations selectParentDeptById(@RequestParam("deptId") String deptId);

	@PostMapping(API_PREFIX + "/selectGetZFJB")
	Organization selectGetZFJB(@RequestParam("deptId") String deptId);

	@PostMapping(API_PREFIX + "/getZFQiYe")
	List<BaobiaoZhengfuQiye> getZFQiYe(@RequestParam("province") String province, @RequestParam("city") String city, @RequestParam("country") String country);

}
