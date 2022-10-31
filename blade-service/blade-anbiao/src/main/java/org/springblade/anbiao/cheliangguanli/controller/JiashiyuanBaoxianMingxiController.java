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
import org.springblade.anbiao.cheliangguanli.entity.JiashiyuanBaoxianMingxi;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianMingxiVO;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianMingxiService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 驾驶员保险信息明细 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/jiashiyuanbaoxianmingxi")
@Api(value = "驾驶员保险信息明细", tags = "驾驶员保险信息明细接口")
public class JiashiyuanBaoxianMingxiController extends BladeController {

	private IJiashiyuanBaoxianMingxiService jiashiyuanBaoxianMingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入jiashiyuanBaoxianMingxi")
	public R<JiashiyuanBaoxianMingxi> detail(JiashiyuanBaoxianMingxi jiashiyuanBaoxianMingxi) {
		JiashiyuanBaoxianMingxi detail = jiashiyuanBaoxianMingxiService.getOne(Condition.getQueryWrapper(jiashiyuanBaoxianMingxi));
		return R.data(detail);
	}

	/**
	 * 分页 驾驶员保险信息明细
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxianMingxi")
	public R<IPage<JiashiyuanBaoxianMingxi>> list(JiashiyuanBaoxianMingxi jiashiyuanBaoxianMingxi, Query query) {
		IPage<JiashiyuanBaoxianMingxi> pages = jiashiyuanBaoxianMingxiService.page(Condition.getPage(query), Condition.getQueryWrapper(jiashiyuanBaoxianMingxi));
		return R.data(pages);
	}

	/**
	 * 自定义分页 驾驶员保险信息明细
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxianMingxi")
	public R<IPage<JiashiyuanBaoxianMingxiVO>> page(JiashiyuanBaoxianMingxiVO jiashiyuanBaoxianMingxi, Query query) {
		IPage<JiashiyuanBaoxianMingxiVO> pages = jiashiyuanBaoxianMingxiService.selectJiashiyuanBaoxianMingxiPage(Condition.getPage(query), jiashiyuanBaoxianMingxi);
		return R.data(pages);
	}

	/**
	 * 新增 驾驶员保险信息明细
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入jiashiyuanBaoxianMingxi")
	public R save(@Valid @RequestBody JiashiyuanBaoxianMingxi jiashiyuanBaoxianMingxi) {
		return R.status(jiashiyuanBaoxianMingxiService.save(jiashiyuanBaoxianMingxi));
	}

	/**
	 * 修改 驾驶员保险信息明细
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入jiashiyuanBaoxianMingxi")
	public R update(@Valid @RequestBody JiashiyuanBaoxianMingxi jiashiyuanBaoxianMingxi) {
		return R.status(jiashiyuanBaoxianMingxiService.updateById(jiashiyuanBaoxianMingxi));
	}

	/**
	 * 新增或修改 驾驶员保险信息明细
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入jiashiyuanBaoxianMingxi")
	public R submit(@Valid @RequestBody JiashiyuanBaoxianMingxi jiashiyuanBaoxianMingxi) {
		return R.status(jiashiyuanBaoxianMingxiService.saveOrUpdate(jiashiyuanBaoxianMingxi));
	}


	/**
	 * 删除 驾驶员保险信息明细
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(jiashiyuanBaoxianMingxiService.deleteLogic(FuncUtil.toLongList(ids)));
	}


}
