package org.springblade.anbiao.guanlijigouherenyuan.feign;

import org.springblade.anbiao.guanlijigouherenyuan.entity.Personnel;
import org.springblade.anbiao.jiashiyuan.entity.JiaShiYuan;
import org.springblade.anbiao.repairs.entity.AnbiaoRepairsPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/** upload Feign接口类
* @author 呵呵哒
*/
@FeignClient(
	//定义Feign指向的service-id
	value = "blade-anbiao",
	fallback = IPersonnelClientBack.class
)
public interface 				IPersonnelClient {

	String API_PREFIX = "/anbiao/perback";

	@PostMapping(API_PREFIX + "/saveOrUpdate")
	Boolean saveOrUpdate(@Valid @RequestBody  Personnel personnel);

	@PostMapping(API_PREFIX + "/updateDelByUserId")
	Boolean updateDelByUserId(@RequestParam("userId") String userId);

	@GetMapping(API_PREFIX + "/selectByUserIdAdnByDeptId")
	Personnel selectByUserIdAdnByDeptId(@RequestParam("userId") String userId, @RequestParam("deptId") String deptId);

	@PostMapping(API_PREFIX + "/insertPersonnelSelective")
	Boolean insertPersonnelSelective(@Valid @RequestBody Personnel personnel);

	@GetMapping(API_PREFIX + "/driverLogin")
	JiaShiYuan driverLogin(@RequestParam("account") String account, @RequestParam("password") String password);

	@GetMapping(API_PREFIX+"/getDriverOpenId")
	JiaShiYuan getDriverOpenId(@RequestParam("openid") String openid);

	/**
	 * 用户绑定openid
	 * @param account
	 * @param openid
	 */
	@GetMapping(API_PREFIX+"/bindDriverOpenId")
	void bindDriverOpenId(@RequestParam("account") String account,@RequestParam("openid") String openid);

	@GetMapping(API_PREFIX + "/getPerson")
	AnbiaoRepairsPerson getPerson(@RequestParam("account") String account, @RequestParam("password") String password);

	@GetMapping(API_PREFIX + "/bindWechatOpenId")
	void bindWechatOpenId(@RequestParam("yhId") String yhId,@RequestParam("openid") String openid,@RequestParam("status") Integer status,@RequestParam("type") Integer type);


}
