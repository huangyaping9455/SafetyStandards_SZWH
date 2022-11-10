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

import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBiangengjilu;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBiangengjiluVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBiangengjiluService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车辆变更记录 控制器
 *
 * @author Blade
 * @since 2022-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclebiangengjilu")
@Api(value = "车辆变更记录", tags = "车辆变更记录接口")
public class VehicleBiangengjiluController extends BladeController {

	private IVehicleBiangengjiluService vehicleBiangengjiluService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleBiangengjilu")
	public R<VehicleBiangengjilu> detail(VehicleBiangengjilu vehicleBiangengjilu) {
		VehicleBiangengjilu detail = vehicleBiangengjiluService.getOne(Condition.getQueryWrapper(vehicleBiangengjilu));
		return R.data(detail);
	}

	/**
	 * 分页 车辆变更记录
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleBiangengjilu")
	public R<IPage<VehicleBiangengjilu>> list(VehicleBiangengjilu vehicleBiangengjilu, Query query) {
		IPage<VehicleBiangengjilu> pages = vehicleBiangengjiluService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleBiangengjilu));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆变更记录
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleBiangengjilu")
	public R<IPage<VehicleBiangengjiluVO>> page(VehicleBiangengjiluVO vehicleBiangengjilu, Query query) {
		IPage<VehicleBiangengjiluVO> pages = vehicleBiangengjiluService.selectVehicleBiangengjiluPage(Condition.getPage(query), vehicleBiangengjilu);
		return R.data(pages);
	}

	/**
	 * 新增 车辆变更记录
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleBiangengjilu")
	public R save(@Valid @RequestBody VehicleBiangengjilu vehicleBiangengjilu) {
		return R.status(vehicleBiangengjiluService.save(vehicleBiangengjilu));
	}

	/**
	 * 修改 车辆变更记录
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleBiangengjilu")
	public R update(@Valid @RequestBody VehicleBiangengjilu vehicleBiangengjilu) {
		return R.status(vehicleBiangengjiluService.updateById(vehicleBiangengjilu));
	}

	/**
	 * 新增或修改 车辆变更记录
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleBiangengjilu")
	public R submit(@Valid @RequestBody VehicleBiangengjilu vehicleBiangengjilu) {
		return R.status(vehicleBiangengjiluService.saveOrUpdate(vehicleBiangengjilu));
	}


	/**
	 * 删除 车辆变更记录
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleBiangengjiluService.removeById(ids));
	}


}
