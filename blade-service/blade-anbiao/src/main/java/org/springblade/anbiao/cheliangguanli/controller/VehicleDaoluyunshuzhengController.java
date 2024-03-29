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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.cheliangguanli.entity.Vehicle;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDaoluyunshuzheng;
import org.springblade.anbiao.cheliangguanli.entity.VehicleXingshizheng;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDaoluyunshuzhengService;
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleDaoluyunshuzhengVO;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

	private IFileUploadClient fileUploadClient;

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
//		VehicleDaoluyunshuzheng qDlysz = new VehicleDaoluyunshuzheng();
//		qDlysz.setAvdAvIds(vehicleId);
//		qDlysz.setAvdDelete("0");
//		VehicleDaoluyunshuzheng daoluyunshuzheng= vehicleDaoluyunshuzhengService.getOne(Condition.getQueryWrapper(qDlysz));
		QueryWrapper<VehicleDaoluyunshuzheng> daoluyunshuzhengQueryWrapper = new QueryWrapper<>();
		daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdAvIds,vehicleId);
		daoluyunshuzhengQueryWrapper.lambda().eq(VehicleDaoluyunshuzheng::getAvdDelete,"0");
		VehicleDaoluyunshuzheng daoluyunshuzheng = vehicleDaoluyunshuzhengService.getBaseMapper().selectOne(daoluyunshuzhengQueryWrapper);
		if(daoluyunshuzheng != null){
			if(StrUtil.isNotEmpty(daoluyunshuzheng.getAvdEnclosure()) && !daoluyunshuzheng.getAvdEnclosure().contains("http")){
				daoluyunshuzheng.setAvdEnclosure(fileUploadClient.getUrl(daoluyunshuzheng.getAvdEnclosure()));
			}
			VehicleVO detail = vehicleService.selectByKey(vehicleId);
			daoluyunshuzheng.setAvxPlateNo(detail.getCheliangpaizhao());
		}

		QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<Vehicle>();
		vehicleQueryWrapper.lambda().eq(Vehicle::getId, vehicleId);
		vehicleQueryWrapper.lambda().eq(Vehicle::getIsdel, 0);
		Vehicle vehicle = vehicleService.getBaseMapper().selectOne(vehicleQueryWrapper);
//		if(vehicle != null){
//			QueryWrapper<Organizations> organizationsQueryWrapper = new QueryWrapper<Organizations>();
//			organizationsQueryWrapper.lambda().eq(Organizations::getDeptId,vehicle.getDeptId());
//			organizationsQueryWrapper.lambda().eq(Organizations::getIsdelete,0);
//			Organizations organizations = organizationsService.getBaseMapper().selectOne(organizationsQueryWrapper);
//			if(organizations != null){
//				daoluyunshuzheng.setAvdRoadTransportCertificateNo(organizations.getDaoluxukezhenghao());
//			}
//		}
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
		vehicleDaoluyunshuzheng.setAvdUpdateTime(DateUtil.now());
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
			baoxian.setAvdUpdateTime(DateUtil.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleDaoluyunshuzhengService.updateBatchById(deptBaoxians));
	}


}
