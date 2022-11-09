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

import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianInfo;
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianMingxiService;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxian;
import org.springblade.anbiao.cheliangguanli.vo.DeptBaoxianVO;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 企业保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptbaoxian")
@Api(value = "企业保险信息主表", tags = "企业保险信息主表接口")
public class DeptBaoxianController extends BladeController {

	private IDeptBaoxianService deptBaoxianService;
	private IDeptBaoxianMingxiService deptBaoxianMingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入deptBaoxian")
	public R<DeptBaoxianInfo> detail(String avbId) {
		DeptBaoxianInfo detail = deptBaoxianService.queryDetail(avbId);
		return R.data(detail);
	}

//	/**
//	 * 分页 企业保险信息主表
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入deptBaoxian")
//	public R<IPage<DeptBaoxian>> list(DeptBaoxian deptBaoxian, Query query) {
//		IPage<DeptBaoxian> pages = deptBaoxianService.page(Condition.getPage(query), Condition.getQueryWrapper(deptBaoxian));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 企业保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入deptBaoxian")
	public R<IPage<DeptBaoxianVO>> page(DeptBaoxianVO deptBaoxian, Query query) {
		IPage<DeptBaoxianVO> pages = deptBaoxianService.selectDeptBaoxianPage(Condition.getPage(query), deptBaoxian);
		return R.data(pages);
	}

	/**
	 * 新增 企业保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入deptBaoxian")
	public R save(@Valid @RequestBody DeptBaoxianInfo deptBaoxian) {
		boolean isSave = deptBaoxianService.save(deptBaoxian.getBaoxian());
		if(deptBaoxian.getMingxiList() != null && deptBaoxian.getMingxiList().size() > 0) {
			for(DeptBaoxianMingxi mingxi:deptBaoxian.getMingxiList()) {
				mingxi.setAvbmAvbIds(deptBaoxian.getBaoxian().getAvbIds());
				deptBaoxianMingxiService.save(mingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 企业保险信息主表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入deptBaoxian")
	public R update(@Valid @RequestBody DeptBaoxianInfo deptBaoxian) {
		boolean isUpdate = deptBaoxianService.updateById(deptBaoxian.getBaoxian());
		if(deptBaoxian.getMingxiList() != null && deptBaoxian.getMingxiList().size() > 0) {
			for(DeptBaoxianMingxi mingxi:deptBaoxian.getMingxiList()) {
				deptBaoxianMingxiService.updateById(mingxi);
			}
		}
		return R.status(isUpdate);
	}

	/**
	 * 新增或修改 企业保险信息主表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入deptBaoxian")
	public R submit(@Valid @RequestBody DeptBaoxian deptBaoxian) {
		return R.status(deptBaoxianService.saveOrUpdate(deptBaoxian));
	}


	/**
	 * 删除 企业保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deptBaoxianService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
