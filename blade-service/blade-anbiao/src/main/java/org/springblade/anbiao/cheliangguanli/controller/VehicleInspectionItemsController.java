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

import org.springblade.anbiao.cheliangguanli.entity.VehicleInspectionDetailed;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleInspectionItems;
import org.springblade.anbiao.cheliangguanli.vo.VehicleInspectionItemsVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleInspectionItemsService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆安全检查项目 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehicleinspectionitems")
@Api(value = "车辆安全检查项目", tags = "车辆安全检查项目接口")
public class VehicleInspectionItemsController extends BladeController {

	private IVehicleInspectionItemsService vehicleInspectionItemsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleInspectionItems")
	public R<VehicleInspectionItems> detail(VehicleInspectionItems vehicleInspectionItems) {
		VehicleInspectionItems detail = vehicleInspectionItemsService.getOne(Condition.getQueryWrapper(vehicleInspectionItems));
		return R.data(detail);
	}

	/**
	 * 分页 车辆安全检查项目
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleInspectionItems")
	public R<IPage<VehicleInspectionItems>> list(VehicleInspectionItems vehicleInspectionItems, Query query) {
		IPage<VehicleInspectionItems> pages = vehicleInspectionItemsService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleInspectionItems));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆安全检查项目
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleInspectionItems")
	public R<IPage<VehicleInspectionItemsVO>> page(VehicleInspectionItemsVO vehicleInspectionItems, Query query) {
		IPage<VehicleInspectionItemsVO> pages = vehicleInspectionItemsService.selectVehicleInspectionItemsPage(Condition.getPage(query), vehicleInspectionItems);
		return R.data(pages);
	}

	/**
	 * 新增 车辆安全检查项目
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleInspectionItems")
	public R save(@Valid @RequestBody VehicleInspectionItems vehicleInspectionItems) {
		return R.status(vehicleInspectionItemsService.save(vehicleInspectionItems));
	}

	/**
	 * 修改 车辆安全检查项目
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleInspectionItems")
	public R update(@Valid @RequestBody VehicleInspectionItems vehicleInspectionItems) {
		return R.status(vehicleInspectionItemsService.updateById(vehicleInspectionItems));
	}

	/**
	 * 新增或修改 车辆安全检查项目
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleInspectionItems")
	public R submit(@Valid @RequestBody VehicleInspectionItems vehicleInspectionItems) {
		return R.status(vehicleInspectionItemsService.saveOrUpdate(vehicleInspectionItems));
	}


	/**
	 * 删除 车辆安全检查项目
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleInspectionItems> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleInspectionItems baoxian = new VehicleInspectionItems();
			baoxian.setIds(new Long(id));
			baoxian.setVitDelete("1");
			baoxian.setVitUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleInspectionItemsService.updateBatchById(deptBaoxians));
	}


}
