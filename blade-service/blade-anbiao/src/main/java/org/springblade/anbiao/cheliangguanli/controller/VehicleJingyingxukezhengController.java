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

import org.springblade.anbiao.cheliangguanli.entity.VehicleInspectionItems;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleJingyingxukezheng;
import org.springblade.anbiao.cheliangguanli.vo.VehicleJingyingxukezhengVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleJingyingxukezhengService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆经营许可证 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclejingyingxukezheng")
@Api(value = "车辆经营许可证", tags = "车辆经营许可证接口")
public class VehicleJingyingxukezhengController extends BladeController {

	private IVehicleJingyingxukezhengService vehicleJingyingxukezhengService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleJingyingxukezheng")
	public R<VehicleJingyingxukezheng> detail(VehicleJingyingxukezheng vehicleJingyingxukezheng) {
		VehicleJingyingxukezheng detail = vehicleJingyingxukezhengService.getOne(Condition.getQueryWrapper(vehicleJingyingxukezheng));
		return R.data(detail);
	}

	/**
	 * 分页 车辆经营许可证
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleJingyingxukezheng")
	public R<IPage<VehicleJingyingxukezheng>> list(VehicleJingyingxukezheng vehicleJingyingxukezheng, Query query) {
		IPage<VehicleJingyingxukezheng> pages = vehicleJingyingxukezhengService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleJingyingxukezheng));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆经营许可证
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleJingyingxukezheng")
	public R<IPage<VehicleJingyingxukezhengVO>> page(VehicleJingyingxukezhengVO vehicleJingyingxukezheng, Query query) {
		IPage<VehicleJingyingxukezhengVO> pages = vehicleJingyingxukezhengService.selectVehicleJingyingxukezhengPage(Condition.getPage(query), vehicleJingyingxukezheng);
		return R.data(pages);
	}

	/**
	 * 新增 车辆经营许可证
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleJingyingxukezheng")
	public R save(@Valid @RequestBody VehicleJingyingxukezheng vehicleJingyingxukezheng) {
		return R.status(vehicleJingyingxukezhengService.save(vehicleJingyingxukezheng));
	}

	/**
	 * 修改 车辆经营许可证
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleJingyingxukezheng")
	public R update(@Valid @RequestBody VehicleJingyingxukezheng vehicleJingyingxukezheng) {
		return R.status(vehicleJingyingxukezhengService.updateById(vehicleJingyingxukezheng));
	}

	/**
	 * 新增或修改 车辆经营许可证
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleJingyingxukezheng")
	public R submit(@Valid @RequestBody VehicleJingyingxukezheng vehicleJingyingxukezheng) {
		return R.status(vehicleJingyingxukezhengService.saveOrUpdate(vehicleJingyingxukezheng));
	}


	/**
	 * 删除 车辆经营许可证
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleJingyingxukezheng> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleJingyingxukezheng baoxian = new VehicleJingyingxukezheng();
			baoxian.setAvjIds(id);
			baoxian.setAvjDelete("1");
			baoxian.setAvjUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleJingyingxukezhengService.updateBatchById(deptBaoxians));
	}


}
