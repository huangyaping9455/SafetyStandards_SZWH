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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDriver;
import org.springblade.anbiao.cheliangguanli.feign.IVehiclepostClientBack;
import org.springblade.anbiao.guanlijigouherenyuan.feign.IOrganizationsClient;
import org.springblade.anbiao.qiyeshouye.entity.BaobiaoZhengfuQiye;
import org.springblade.anbiao.qiyeshouye.entity.PersonLearnInfo;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.anbiao.zhengfu.entity.Organization;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.train.config.RedisUtil;
import org.springblade.train.entity.Train;
import org.springblade.train.entity.Unit;
import org.springblade.train.entity.ZFCourseInfo;
import org.springblade.train.service.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/train")
@Api(value = "处罚教育学习", tags = "处罚教育学习")
public class TrainController extends BladeController {

	private ITrainService trainService;

	private IVehiclepostClientBack vehiclepostClientBack;

	private TrainServer trainServer;

	@Autowired
	private RedisUtil redisUtil;

//	@GetMapping("/getRedis")
//	public void getRedis() {
//		String account = "11111";
//		AccountVo accountVo = new AccountVo();
//		accountVo.setAccountCode("11111");
//		accountVo.setAccountType("1");
//		redisUtil.set("account:" + accountVo.getAccountCode(), JSONObject.toJSONString(accountVo), -1);
//		if (redisUtil.hasKey("account:" + account)) {
//			AccountVo bean = redisUtil.getBean("account:" + account, AccountVo.class);
//			System.out.println("-------------->存在" + bean);
//		} else {
//			System.out.println("-------------->不存在");
//		}
//	}

	@GetMapping("/getQYCourseList")
	@ApiOperation(value = "教育--根据企业名称获取课程列表", notes = "教育--根据企业名称获取课程列表", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R<List<Train>> getQYCourseList(String deptName) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = trainService.getUnitByName(deptName);
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
			return rs;
		}
		List<Train> list = trainService.getQYCourseList(null,unit.getId().toString());
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

	private IOrganizationsClient orrganizationsClient;

	@PostMapping(value = "/selectZFPersonLearnCoutAll")
	@ApiLog("政府-获取学习统计列表")
	@ApiOperation(value = "政府-获取学习统计列表", notes = "传入qiYeShouYePage", position = 22)
	public R selectZFPersonLearnCoutAll(@RequestBody QiYeShouYePage qiYeShouYePage) throws IOException {
		R r = new R();
		Organization jb = orrganizationsClient.selectGetZFJB(qiYeShouYePage.getDeptId());
		if (!StringUtils.isBlank(jb.getProvince()) && StringUtils.isBlank(jb.getCity())) {
			qiYeShouYePage.setProvince(qiYeShouYePage.getDeptId());
		}

		if (!StringUtils.isBlank(jb.getCity()) && StringUtils.isBlank(jb.getCountry())) {
			qiYeShouYePage.setCity(qiYeShouYePage.getDeptId());
		}

		if (!StringUtils.isBlank(jb.getCountry())) {
			qiYeShouYePage.setCountry(qiYeShouYePage.getDeptId());
		}

		//排序条件
		if (qiYeShouYePage.getOrderColumns() == null) {
			qiYeShouYePage.setOrderColumn("deptname");
		} else {
			qiYeShouYePage.setOrderColumn(qiYeShouYePage.getOrderColumns());
		}

		List<String> stringList = new ArrayList<>();
		List<BaobiaoZhengfuQiye> baobiaoZhengfuQiyes = orrganizationsClient.getZFQiYe(qiYeShouYePage.getProvince(),qiYeShouYePage.getCity(),qiYeShouYePage.getCountry());
		if(baobiaoZhengfuQiyes != null && baobiaoZhengfuQiyes.size() > 0){
			baobiaoZhengfuQiyes.forEach(item -> {
				stringList.add(item.getQiyemingcheng());
			});
		}
		//返回一个包含所有对象的指定类型的数组
		String[] idss= stringList.toArray(new String[1]);
		qiYeShouYePage.setList(idss);

		QiYeShouYePage<ZFCourseInfo> zfpersonLearnInfo = trainService.selectZFPersonLearnCoutAll(qiYeShouYePage);
		if (zfpersonLearnInfo != null) {
			r.setData(zfpersonLearnInfo);
			r.setSuccess(true);
			r.setMsg("获取成功");
		} else {
			r.setData(null);
			r.setSuccess(false);
			r.setMsg("获取失败");
		}
		return R.data(r);
	}

}
