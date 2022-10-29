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
import org.springblade.anbiao.cheliangguanli.entity.VehicleJishupingding;
import org.springblade.anbiao.cheliangguanli.vo.VehicleJishupingdingVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleJishupingdingService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车辆技术评定信息 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclejishupingding")
@Api(value = "车辆技术评定信息", tags = "车辆技术评定信息接口")
public class VehicleJishupingdingController extends BladeController {

	private IVehicleJishupingdingService vehicleJishupingdingService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleJishupingding")
	public R<VehicleJishupingding> detail(VehicleJishupingding vehicleJishupingding) {
		VehicleJishupingding detail = vehicleJishupingdingService.getOne(Condition.getQueryWrapper(vehicleJishupingding));
		return R.data(detail);
	}

	/**
	 * 分页 车辆技术评定信息
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleJishupingding")
	public R<IPage<VehicleJishupingding>> list(VehicleJishupingding vehicleJishupingding, Query query) {
		IPage<VehicleJishupingding> pages = vehicleJishupingdingService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleJishupingding));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆技术评定信息
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleJishupingding")
	public R<IPage<VehicleJishupingdingVO>> page(VehicleJishupingdingVO vehicleJishupingding, Query query) {
		IPage<VehicleJishupingdingVO> pages = vehicleJishupingdingService.selectVehicleJishupingdingPage(Condition.getPage(query), vehicleJishupingding);
		return R.data(pages);
	}

	/**
	 * 新增 车辆技术评定信息
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleJishupingding")
	public R save(@Valid @RequestBody VehicleJishupingding vehicleJishupingding) {
		return R.status(vehicleJishupingdingService.save(vehicleJishupingding));
	}

	/**
	 * 修改 车辆技术评定信息
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleJishupingding")
	public R update(@Valid @RequestBody VehicleJishupingding vehicleJishupingding) {
		return R.status(vehicleJishupingdingService.updateById(vehicleJishupingding));
	}

	/**
	 * 新增或修改 车辆技术评定信息
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleJishupingding")
	public R submit(@Valid @RequestBody VehicleJishupingding vehicleJishupingding) {
		return R.status(vehicleJishupingdingService.saveOrUpdate(vehicleJishupingding));
	}


	/**
	 * 删除 车辆技术评定信息
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleJishupingdingService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
