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
import org.springblade.anbiao.cheliangguanli.service.IVehicleService;
import org.springblade.anbiao.cheliangguanli.vo.VehicleVO;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.upload.upload.feign.IFileUploadClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.VehicleDengjizhengshu;
import org.springblade.anbiao.cheliangguanli.vo.VehicleDengjizhengshuVO;
import org.springblade.anbiao.cheliangguanli.service.IVehicleDengjizhengshuService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆登记证书 控制器
 *
 * @author Blade
 * @since 2022-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vehicledengjizhengshu")
@Api(value = "车辆登记证书", tags = "车辆登记证书接口")
public class VehicleDengjizhengshuController extends BladeController {

	private IVehicleDengjizhengshuService vehicleDengjizhengshuService;

	private IVehicleService vehicleService;

	private IFileUploadClient fileUploadClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入vehicleDengjizhengshu")
	public R<VehicleDengjizhengshu> detail(VehicleDengjizhengshu vehicleDengjizhengshu) {
		VehicleDengjizhengshu detail = vehicleDengjizhengshuService.getOne(Condition.getQueryWrapper(vehicleDengjizhengshu));
		return R.data(detail);
	}

	@GetMapping("/queryByVehicle")
	@ApiOperation(value = "根据车辆ID查询登记证书详情", notes = "根据车辆ID查询登记证书详情")
	public R<VehicleDengjizhengshu> queryByVehicle(String vehicleId) {
		VehicleDengjizhengshu qDjzs = new VehicleDengjizhengshu();
		qDjzs.setAvdVehicleIds(vehicleId);
		qDjzs.setAvdDelete("0");
		VehicleDengjizhengshu dengjizhengshu= vehicleDengjizhengshuService.getOne(Condition.getQueryWrapper(qDjzs));
		if(dengjizhengshu != null){
			if(!dengjizhengshu.getAvdEnclosure().contains("http")){
				dengjizhengshu.setAvdEnclosure(fileUploadClient.getUrl(dengjizhengshu.getAvdEnclosure()));
			}
			VehicleVO detail = vehicleService.selectByKey(vehicleId);
			dengjizhengshu.setAvxFileNo(detail.getCheliangpaizhao());
		}
		return R.data(dengjizhengshu);
	}

	/**
	 * 分页 车辆登记证书
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入vehicleDengjizhengshu")
	public R<IPage<VehicleDengjizhengshu>> list(VehicleDengjizhengshu vehicleDengjizhengshu, Query query) {
		IPage<VehicleDengjizhengshu> pages = vehicleDengjizhengshuService.page(Condition.getPage(query), Condition.getQueryWrapper(vehicleDengjizhengshu));
		return R.data(pages);
	}

	/**
	 * 自定义分页 车辆登记证书
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入vehicleDengjizhengshu")
	public R<IPage<VehicleDengjizhengshuVO>> page(VehicleDengjizhengshuVO vehicleDengjizhengshu, Query query) {
		IPage<VehicleDengjizhengshuVO> pages = vehicleDengjizhengshuService.selectVehicleDengjizhengshuPage(Condition.getPage(query), vehicleDengjizhengshu);
		return R.data(pages);
	}

	/**
	 * 新增 车辆登记证书
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入vehicleDengjizhengshu")
	public R save(@Valid @RequestBody VehicleDengjizhengshu vehicleDengjizhengshu) {
		return R.status(vehicleDengjizhengshuService.save(vehicleDengjizhengshu));
	}

	/**
	 * 修改 车辆登记证书
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入vehicleDengjizhengshu")
	public R update(@Valid @RequestBody VehicleDengjizhengshu vehicleDengjizhengshu) {
		return R.status(vehicleDengjizhengshuService.updateById(vehicleDengjizhengshu));
	}

	/**
	 * 新增或修改 车辆登记证书
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入vehicleDengjizhengshu")
	public R submit(@Valid @RequestBody VehicleDengjizhengshu vehicleDengjizhengshu) {
		return R.status(vehicleDengjizhengshuService.saveOrUpdate(vehicleDengjizhengshu));
	}


	/**
	 * 删除 车辆登记证书
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<VehicleDengjizhengshu> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			VehicleDengjizhengshu baoxian = new VehicleDengjizhengshu();
			baoxian.setAvdIds(id);
			baoxian.setAvdDelete("1");
			baoxian.setAvdUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(vehicleDengjizhengshuService.updateBatchById(deptBaoxians));
	}


}
