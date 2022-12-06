/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.anbiao.cheliangguanli.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxian;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDengjizhengshu;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.vo.VehicleDaoluyunshuzhengVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDaoluyunshuzhengService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆道路运输证 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehicledaoluyunshuzheng")
@Api(value = "车辆道路运输证", tags = "车辆道路运输证接口")
public class VehicleDaoluyunshuzhengController extends BladeController {

	private IVehicleDaoluyunshuzhengService vehicleDaoluyunshuzhengService;

	private IVehicleService vehicleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleDaoluyunshuzheng")
	public R<VehicleDaoluyunshuzheng> detail(VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng) {
		VehicleDaoluyunshuzheng detail = vehicleDaoluyunshuzhengService.getOne(Condition.getQueryWrapper(vehicleDaoluyunshuzheng));
		return R.data(detail);
	}

	@GetMapping("/queryByVehicle")
	@ApiOperation(value = "根据车辆ID查询道路运输证详情", notes = "根据车辆ID查询道路运输证详情")
	public R<VehicleDaoluyunshuzheng> queryByDept(String vehicleId) {
		VehicleDaoluyunshuzheng qDlysz = new VehicleDaoluyunshuzheng();
		qDlysz.setAvdAvIds(vehicleId);
		qDlysz.setAvdDelete("0");
		VehicleDaoluyunshuzheng daoluyunshuzheng= vehicleDaoluyunshuzhengService.getOne(Condition.getQueryWrapper(qDlysz));
		if(daoluyunshuzheng != null){
			VehicleVO detail = vehicleService.selectByKey(vehicleId);
			daoluyunshuzheng.setAvxPlateNo(detail.getCheliangpaizhao());
		}
		return R.data(daoluyunshuzheng);
	}

	/**
	 * 分页 车辆道路运输证
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleDaoluyunshuzheng")
	public R<IPage<VehicleDaoluyunshuzheng>> list(VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng, Query query) {
		IPage<VehicleDaoluyunshuzheng> pages = vehicleDaoluyunshuzhengService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleDaoluyunshuzheng));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆道路运输证
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleDaoluyunshuzheng")
	public R<IPage<VehicleDaoluyunshuzhengVO>> page(VehicleDaoluyunshuzhengVO vehicleDaoluyunshuzheng, Query query) {
		IPage<VehicleDaoluyunshuzhengVO> pages = vehicleDaoluyunshuzhengService.selectVehicleDaoluyunshuzhengPage(Condition.getPage(query), vehicleDaoluyunshuzheng);
		return R.data(pages);
	}

	/**
	 * 新增 车辆道路运输证
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleDaoluyunshuzheng")
	public R save(@Valid @RequestBody VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng) {
		return R.status(vehicleDaoluyunshuzhengService.save(vehicleDaoluyunshuzheng));
	}

	/**
	 * 修改 车辆道路运输证
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleDaoluyunshuzheng")
	public R update(@Valid @RequestBody VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng) {
		return R.status(vehicleDaoluyunshuzhengService.updateById(vehicleDaoluyunshuzheng));
	}

	/**
	 * 新增或修改 车辆道路运输证
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleDaoluyunshuzheng")
	public R submit(@Valid @RequestBody VehicleDaoluyunshuzheng vehicleDaoluyunshuzheng) {
		return R.status(vehicleDaoluyunshuzhengService.saveOrUpdate(vehicleDaoluyunshuzheng));
	}


	/**
	 * 删除 车辆道路运输证
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleDaoluyunshuzheng> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleDaoluyunshuzheng baoxian = new VehicleDaoluyunshuzheng();
			baoxian.setAvdIds(id);
			baoxian.setAvdDelete("1");
			baoxian.setAvdUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleDaoluyunshuzhengService.updateBatchById(deptBaoxians));
	}


}
