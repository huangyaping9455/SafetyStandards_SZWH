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

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianMingxiService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleBaoxianService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleBaoxianVO;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 车辆保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/anbiao/vehiclebaoxian")
@Api(value = "车辆保险信息主表", tags = "车辆保险信息主表接口")
public class VehicleBaoxianController extends BladeController {

	private IVehicleBaoxianService vehicleBaoxianService;
	private IVehicleBaoxianMingxiService vehicleBaoxianMingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleBaoxian")
	public R<VehicleBaoxianInfo> detail(String avbId) {
//		VehicleBaoxian detail = vehicleBaoxianService.getOne(Condition.getQueryWrapper(vehicleBaoxian));
		VehicleBaoxianInfo detail = vehicleBaoxianService.queryDetail(avbId);
		return R.data(detail);
	}

	@GetMapping("/queryByVehicle")
	@ApiOperation(value = "根据被保险车辆ID查询保险详情", notes = "根据被保险车辆ID查询保险详情")
	public R<List<VehicleBaoxian>> queryByDept(String vehicleId) {
		R r = new R();
		VehicleBaoxian vehicleBaoxian = new VehicleBaoxian();
		vehicleBaoxian.setAvbAvIds(vehicleId);
		vehicleBaoxian.setAvbDelete(0);
		List<VehicleBaoxian> baoxian = vehicleBaoxianService.getBaseMapper().selectList(Condition.getQueryWrapper(vehicleBaoxian));
		if(baoxian != null) {
			baoxian.forEach(item-> {
				VehicleBaoxianMingxi mingxi = new VehicleBaoxianMingxi();
				mingxi.setAvbmAvbIds(item.getAvbIds());
				List<VehicleBaoxianMingxi> mingxiList = vehicleBaoxianMingxiService.getBaseMapper().selectList(Condition.getQueryWrapper(mingxi));
				item.setBaoxianMingxis(mingxiList);
			});
//			VehicleBaoxianInfo detail = vehicleBaoxianService.queryDetail(baoxian.getAvbIds());
			return R.data(baoxian);
		} else {
			r.setCode(500);
			r.setMsg("未查询到车辆保险信息！");
			return r;
		}
	}

//	/**
//	 * 分页 车辆保险信息主表
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入vehicleBaoxian")
//	public R<IPage<VehicleBaoxian>> list(VehicleBaoxian vehicleBaoxian, Query query) {
//		IPage<VehicleBaoxian> pages = vehicleBaoxianService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleBaoxian));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 车辆保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleBaoxian")
	public R<IPage<VehicleBaoxianVO>> page(VehicleBaoxianVO vehicleBaoxian, Query query) {
		IPage<VehicleBaoxianVO> pages = vehicleBaoxianService.selectVehicleBaoxianPage(Condition.getPage(query), vehicleBaoxian);
		return R.data(pages);
	}

	/**
	 * 新增 车辆保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleBaoxian")
	public R save(@Valid @RequestBody VehicleBaoxianInfo vehicleBaoxian) {
		boolean isSave = vehicleBaoxianService.save(vehicleBaoxian.getBaoxian());
		if(vehicleBaoxian.getBaoxianMingxis() != null && vehicleBaoxian.getBaoxianMingxis().size() > 0) {
			for (VehicleBaoxianMingxi baoxianMingxi: vehicleBaoxian.getBaoxianMingxis()) {
				baoxianMingxi.setAvbmAvbIds(vehicleBaoxian.getBaoxian().getAvbIds());
				vehicleBaoxianMingxiService.save(baoxianMingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 车辆保险信息主表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleBaoxian")
	public R update(@Valid @RequestBody VehicleBaoxianInfo vehicleBaoxian) {
		boolean isUpdate = vehicleBaoxianService.updateById(vehicleBaoxian.getBaoxian());
		if(vehicleBaoxian.getBaoxianMingxis() != null && vehicleBaoxian.getBaoxianMingxis().size() > 0) {
			for (VehicleBaoxianMingxi baoxianMingxi: vehicleBaoxian.getBaoxianMingxis()) {
				vehicleBaoxianMingxiService.updateById(baoxianMingxi);
			}
		}
		return R.status(isUpdate);
	}

//	/**
//	 * 新增或修改 车辆保险信息主表
//	 */
//	@PostMapping("/submit")
//	@ApiOperation(value = "新增或修改", notes = "传入vehicleBaoxian")
//	public R submit(@Valid @RequestBody VehicleBaoxian vehicleBaoxian) {
//		return R.status(vehicleBaoxianService.saveOrUpdate(vehicleBaoxian));
//	}


	/**
	 * 删除 车辆保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vehicleBaoxianService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
