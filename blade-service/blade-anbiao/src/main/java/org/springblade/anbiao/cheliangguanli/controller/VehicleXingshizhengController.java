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
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingshizheng;
import org.springblade.anbiao.cheliangguanli.vo.VehicleXingshizhengVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleXingshizhengService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 车辆行驶证信息 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclexingshizheng")
@Api(value = "车辆行驶证信息", tags = "车辆行驶证信息接口")
public class VehicleXingshizhengController extends BladeController {

	private IVehicleXingshizhengService vehicleXingshizhengService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleXingshizheng")
	public R<VehicleXingshizheng> detail(VehicleXingshizheng vehicleXingshizheng) {
		VehicleXingshizheng detail = vehicleXingshizhengService.getOne(Condition.getQueryWrapper(vehicleXingshizheng));
		return R.data(detail);
	}

	/**
	 * 分页 车辆行驶证信息
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleXingshizheng")
	public R<IPage<VehicleXingshizheng>> list(VehicleXingshizheng vehicleXingshizheng, Query query) {
		IPage<VehicleXingshizheng> pages = vehicleXingshizhengService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleXingshizheng));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆行驶证信息
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleXingshizheng")
	public R<IPage<VehicleXingshizhengVO>> page(VehicleXingshizhengVO vehicleXingshizheng, Query query) {
		IPage<VehicleXingshizhengVO> pages = vehicleXingshizhengService.selectVehicleXingshizhengPage(Condition.getPage(query), vehicleXingshizheng);
		return R.data(pages);
	}

	/**
	 * 新增 车辆行驶证信息
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleXingshizheng")
	public R save(@Valid @RequestBody VehicleXingshizheng vehicleXingshizheng) {
		return R.status(vehicleXingshizhengService.save(vehicleXingshizheng));
	}

	/**
	 * 修改 车辆行驶证信息
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleXingshizheng")
	public R update(@Valid @RequestBody VehicleXingshizheng vehicleXingshizheng) {
		return R.status(vehicleXingshizhengService.updateById(vehicleXingshizheng));
	}

	/**
	 * 新增或修改 车辆行驶证信息
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleXingshizheng")
	public R submit(@Valid @RequestBody VehicleXingshizheng vehicleXingshizheng) {
		return R.status(vehicleXingshizhengService.saveOrUpdate(vehicleXingshizheng));
	}


	/**
	 * 删除 车辆行驶证信息
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleXingshizhengService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
