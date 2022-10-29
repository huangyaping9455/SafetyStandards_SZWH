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
import org.springblade.anbiao.cheliangguanli.entity.VehicleHegezheng;
import org.springblade.anbiao.cheliangguanli.vo.VehicleHegezhengVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleHegezhengService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车辆合格证书 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclehegezheng")
@Api(value = "车辆合格证书", tags = "车辆合格证书接口")
public class VehicleHegezhengController extends BladeController {

	private IVehicleHegezhengService vehicleHegezhengService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleHegezheng")
	public R<VehicleHegezheng> detail(VehicleHegezheng vehicleHegezheng) {
		VehicleHegezheng detail = vehicleHegezhengService.getOne(Condition.getQueryWrapper(vehicleHegezheng));
		return R.data(detail);
	}

	/**
	 * 分页 车辆合格证书
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleHegezheng")
	public R<IPage<VehicleHegezheng>> list(VehicleHegezheng vehicleHegezheng, Query query) {
		IPage<VehicleHegezheng> pages = vehicleHegezhengService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleHegezheng));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆合格证书
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleHegezheng")
	public R<IPage<VehicleHegezhengVO>> page(VehicleHegezhengVO vehicleHegezheng, Query query) {
		IPage<VehicleHegezhengVO> pages = vehicleHegezhengService.selectVehicleHegezhengPage(Condition.getPage(query), vehicleHegezheng);
		return R.data(pages);
	}

	/**
	 * 新增 车辆合格证书
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleHegezheng")
	public R save(@Valid @RequestBody VehicleHegezheng vehicleHegezheng) {
		return R.status(vehicleHegezhengService.save(vehicleHegezheng));
	}

	/**
	 * 修改 车辆合格证书
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleHegezheng")
	public R update(@Valid @RequestBody VehicleHegezheng vehicleHegezheng) {
		return R.status(vehicleHegezhengService.updateById(vehicleHegezheng));
	}

	/**
	 * 新增或修改 车辆合格证书
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleHegezheng")
	public R submit(@Valid @RequestBody VehicleHegezheng vehicleHegezheng) {
		return R.status(vehicleHegezhengService.saveOrUpdate(vehicleHegezheng));
	}


	/**
	 * 删除 车辆合格证书
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleHegezhengService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
