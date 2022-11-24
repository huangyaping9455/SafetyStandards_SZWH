/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.train.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDriver;
import org.springblade.anbiao.cheliangguanli.feign.IVehiclepostClientBack;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.train.config.RedisUtil;
import org.springblade.train.config.model.AccountVo;
import org.springblade.train.entity.Train;
import org.springblade.train.entity.Unit;
import org.springblade.train.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/train")
@Api(value = "处罚教育学习", tags = "处罚教育学习")
public class TrainController extends BladeController {

	private ITrainService trainService;

	private IVehiclepostClientBack vehiclepostClientBack;

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping("/getRedis")
	public void getRedis() {
		String account = "11111";
		AccountVo accountVo = new AccountVo();
		accountVo.setAccountCode("11111");
		accountVo.setAccountType("1");
		redisUtil.set("account:" + accountVo.getAccountCode(), JSONObject.toJSONString(accountVo), -1);
		if (redisUtil.hasKey("account:" + account)) {
			AccountVo bean = redisUtil.getBean("account:" + account, AccountVo.class);
			System.out.println("-------------->存在" + bean);
		} else {
			System.out.println("-------------->不存在");
		}
	}

	@GetMapping("/getQYCourseList")
	@ApiOperation(value = "教育--根据企业名称、报警类型获取课程列表", notes = "教育--根据企业名称、报警类型获取课程列表", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type", value = "报警类型", required = true),
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R<List<Train>> getQYCourseList(String type,String deptName) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(deptName);
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}
		List<Train> list = trainService.getQYCourseList(type,unit.getId().toString());
		if(list != null){
			rs.setData(list);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取课程列表成功");
		}else{
			rs.setData(null);
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setMsg("获取课程列表成功,暂无数据");
		}
		return rs;
	}

	@GetMapping("/getVehicleDriver")
	@ApiLog("教育--根据车辆ID获取车辆绑定驾驶员信息")
	@ApiOperation(value = "教育--根据车辆ID获取车辆绑定驾驶员信息", notes = "传入vehId", position = 2)
	public R getVehicleDriver(String deptId,String cheliangpaizhao,String chepaiyanse,String type) {
		if("处罚性学习".contains(type)){
			type = "1";
		}else{
			type = "0";
		}
		VehicleDriver detail = vehiclepostClientBack.getVehicleDriver(deptId,cheliangpaizhao,chepaiyanse,type);
		return R.data(detail);
	}

	@GetMapping("/getDriverByDeptIdList")
	@ApiLog("教育--根据企业ID获取驾驶员信息")
	@ApiOperation(value = "根据企业ID获取驾驶员信息", notes = "传入deptId", position = 3)
	public R<List<VehicleDriver>> getDriverByDeptIdList(String deptId,String type) {
		if("处罚性学习".contains(type)){
			type = "1";
		}else{
			type = "0";
		}
		List<VehicleDriver> detail = vehiclepostClientBack.getDriverByDeptIdList(deptId,type);
		return R.data(detail);
	}

}
