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
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBaoxianMingxiVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianMingxiService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车辆保险信息明细 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclebaoxianmingxi")
@Api(value = "车辆保险信息明细", tags = "车辆保险信息明细接口")
public class VehicleBaoxianMingxiController extends BladeController {

	private IVehicleBaoxianMingxiService vehicleBaoxianMingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入vehicleBaoxianMingxi")
	public R<VehicleBaoxianMingxi> detail(VehicleBaoxianMingxi vehicleBaoxianMingxi) {
		VehicleBaoxianMingxi detail = vehicleBaoxianMingxiService.getOne(Condition.getQueryWrapper(vehicleBaoxianMingxi));
		return R.data(detail);
	}

	/**
	 * 分页 车辆保险信息明细
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入vehicleBaoxianMingxi")
	public R<IPage<VehicleBaoxianMingxi>> list(VehicleBaoxianMingxi vehicleBaoxianMingxi, Query query) {
		IPage<VehicleBaoxianMingxi> pages = vehicleBaoxianMingxiService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleBaoxianMingxi));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆保险信息明细
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入vehicleBaoxianMingxi")
	public R<IPage<VehicleBaoxianMingxiVO>> page(VehicleBaoxianMingxiVO vehicleBaoxianMingxi, Query query) {
		IPage<VehicleBaoxianMingxiVO> pages = vehicleBaoxianMingxiService.selectVehicleBaoxianMingxiPage(Condition.getPage(query), vehicleBaoxianMingxi);
		return R.data(pages);
	}

	/**
	 * 新增 车辆保险信息明细
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入vehicleBaoxianMingxi")
	public R save(@Valid @RequestBody VehicleBaoxianMingxi vehicleBaoxianMingxi) {
		return R.status(vehicleBaoxianMingxiService.save(vehicleBaoxianMingxi));
	}

	/**
	 * 修改 车辆保险信息明细
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入vehicleBaoxianMingxi")
	public R update(@Valid @RequestBody VehicleBaoxianMingxi vehicleBaoxianMingxi) {
		return R.status(vehicleBaoxianMingxiService.updateById(vehicleBaoxianMingxi));
	}

	/**
	 * 新增或修改 车辆保险信息明细
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入vehicleBaoxianMingxi")
	public R submit(@Valid @RequestBody VehicleBaoxianMingxi vehicleBaoxianMingxi) {
		return R.status(vehicleBaoxianMingxiService.saveOrUpdate(vehicleBaoxianMingxi));
	}

	
	/**
	 * 删除 车辆保险信息明细
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleBaoxianMingxiService.deleteLogic(Func.toLongList(ids)));
	}

	
}
