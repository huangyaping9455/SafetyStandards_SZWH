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

import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.entity.VehicleJishupingding;
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
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingnengbaogao;
import org.springblade.anbiao.cheliangguanli.vo.VehicleXingnengbaogaoVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleXingnengbaogaoService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆综合性能检测报告 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehiclexingnengbaogao")
@Api(value = "车辆综合性能检测报告", tags = "车辆综合性能检测报告接口")
public class VehicleXingnengbaogaoController extends BladeController {

	private IVehicleXingnengbaogaoService vehicleXingnengbaogaoService;

	private IVehicleService vehicleService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleXingnengbaogao")
	public R<VehicleXingnengbaogao> detail(VehicleXingnengbaogao vehicleXingnengbaogao) {
		VehicleXingnengbaogao detail = vehicleXingnengbaogaoService.getOne(Condition.getQueryWrapper(vehicleXingnengbaogao));
		return R.data(detail);
	}

	@GetMapping("/queryByVehicle")
	@ApiOperation(value = "根据车辆ID查询性能报告详情", notes = "根据车辆ID查询性能报告详情")
	public R<VehicleXingnengbaogao> queryByVehicle(String vehicleId) {
		VehicleXingnengbaogao qXlbg = new VehicleXingnengbaogao();
		qXlbg.setAvxAvIds(vehicleId);
		qXlbg.setAvxDelete("0");
		VehicleXingnengbaogao xingnengbaogao= vehicleXingnengbaogaoService.getOne(Condition.getQueryWrapper(qXlbg));
		if(xingnengbaogao != null){
			VehicleVO detail = vehicleService.selectByKey(vehicleId);
			xingnengbaogao.setAvxFileNo(detail.getCheliangpaizhao());
		}
		return R.data(xingnengbaogao);
	}

	/**
	 * 分页 车辆综合性能检测报告
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleXingnengbaogao")
	public R<IPage<VehicleXingnengbaogao>> list(VehicleXingnengbaogao vehicleXingnengbaogao, Query query) {
		IPage<VehicleXingnengbaogao> pages = vehicleXingnengbaogaoService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleXingnengbaogao));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆综合性能检测报告
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleXingnengbaogao")
	public R<IPage<VehicleXingnengbaogaoVO>> page(VehicleXingnengbaogaoVO vehicleXingnengbaogao, Query query) {
		IPage<VehicleXingnengbaogaoVO> pages = vehicleXingnengbaogaoService.selectVehicleXingnengbaogaoPage(Condition.getPage(query), vehicleXingnengbaogao);
		return R.data(pages);
	}

	/**
	 * 新增 车辆综合性能检测报告
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleXingnengbaogao")
	public R save(@Valid @RequestBody VehicleXingnengbaogao vehicleXingnengbaogao) {
		return R.status(vehicleXingnengbaogaoService.save(vehicleXingnengbaogao));
	}

	/**
	 * 修改 车辆综合性能检测报告
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleXingnengbaogao")
	public R update(@Valid @RequestBody VehicleXingnengbaogao vehicleXingnengbaogao) {
		return R.status(vehicleXingnengbaogaoService.updateById(vehicleXingnengbaogao));
	}

	/**
	 * 新增或修改 车辆综合性能检测报告
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleXingnengbaogao")
	public R submit(@Valid @RequestBody VehicleXingnengbaogao vehicleXingnengbaogao) {
		return R.status(vehicleXingnengbaogaoService.saveOrUpdate(vehicleXingnengbaogao));
	}


	/**
	 * 删除 车辆综合性能检测报告
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleXingnengbaogao> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleXingnengbaogao baoxian = new VehicleXingnengbaogao();
			baoxian.setAvxIds(id);
			baoxian.setAvxDelete("1");
			baoxian.setAvxUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleXingnengbaogaoService.updateBatchById(deptBaoxians));
	}


}
