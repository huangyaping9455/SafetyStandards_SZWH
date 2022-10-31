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
import org.springblade.anbiao.cheliangguanli.entity.DeptBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.vo.DeptBaoxianMingxiVO;
import org.springblade.anbiao.cheliangguanli.service.IDeptBaoxianMingxiService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 企业保险信息明细 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/deptbaoxianmingxi")
@Api(value = "企业保险信息明细", tags = "企业保险信息明细接口")
public class DeptBaoxianMingxiController extends BladeController {

	private IDeptBaoxianMingxiService deptBaoxianMingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入deptBaoxianMingxi")
	public R<DeptBaoxianMingxi> detail(DeptBaoxianMingxi deptBaoxianMingxi) {
		DeptBaoxianMingxi detail = deptBaoxianMingxiService.getOne(Condition.getQueryWrapper(deptBaoxianMingxi));
		return R.data(detail);
	}

	/**
	 * 分页 企业保险信息明细
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入deptBaoxianMingxi")
	public R<IPage<DeptBaoxianMingxi>> list(DeptBaoxianMingxi deptBaoxianMingxi, Query query) {
		IPage<DeptBaoxianMingxi> pages = deptBaoxianMingxiService.page(Condition.getPage(query), Condition.getQueryWrapper(deptBaoxianMingxi));
		return R.data(pages);
	}

	/**
	 * 自定义分页 企业保险信息明细
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入deptBaoxianMingxi")
	public R<IPage<DeptBaoxianMingxiVO>> page(DeptBaoxianMingxiVO deptBaoxianMingxi, Query query) {
		IPage<DeptBaoxianMingxiVO> pages = deptBaoxianMingxiService.selectDeptBaoxianMingxiPage(Condition.getPage(query), deptBaoxianMingxi);
		return R.data(pages);
	}

	/**
	 * 新增 企业保险信息明细
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入deptBaoxianMingxi")
	public R save(@Valid @RequestBody DeptBaoxianMingxi deptBaoxianMingxi) {
		return R.status(deptBaoxianMingxiService.save(deptBaoxianMingxi));
	}

	/**
	 * 修改 企业保险信息明细
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入deptBaoxianMingxi")
	public R update(@Valid @RequestBody DeptBaoxianMingxi deptBaoxianMingxi) {
		return R.status(deptBaoxianMingxiService.updateById(deptBaoxianMingxi));
	}

	/**
	 * 新增或修改 企业保险信息明细
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入deptBaoxianMingxi")
	public R submit(@Valid @RequestBody DeptBaoxianMingxi deptBaoxianMingxi) {
		return R.status(deptBaoxianMingxiService.saveOrUpdate(deptBaoxianMingxi));
	}


	/**
	 * 删除 企业保险信息明细
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(deptBaoxianMingxiService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
