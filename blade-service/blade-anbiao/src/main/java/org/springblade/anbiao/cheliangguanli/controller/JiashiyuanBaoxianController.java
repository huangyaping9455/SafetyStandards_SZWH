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

import org.springblade.anbiao.cheliangguanli.entity.*;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianMingxiService;
import org.springblade.common.tool.FuncUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.anbiao.cheliangguanli.vo.JiashiyuanBaoxianVO;
import org.springblade.anbiao.cheliangguanli.service.IJiashiyuanBaoxianService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 驾驶员保险信息主表 控制器
 *
 * @author Blade
 * @since 2022-10-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/jiashiyuanbaoxian")
@Api(value = "驾驶员保险信息主表", tags = "驾驶员保险信息主表接口")
public class JiashiyuanBaoxianController extends BladeController {

	private IJiashiyuanBaoxianService jiashiyuanBaoxianService;
	private IJiashiyuanBaoxianMingxiService mingxiService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入jiashiyuanBaoxian")
	public R<JiashiyuanBaoxianInfo> detail(String ajbId) {
//		JiashiyuanBaoxian detail = jiashiyuanBaoxianService.getOne(Condition.getQueryWrapper(jiashiyuanBaoxian));
		JiashiyuanBaoxianInfo detail = jiashiyuanBaoxianService.queryDetail(ajbId);
		return R.data(detail);
	}

//	/**
//	 * 分页 驾驶员保险信息主表
//	 */
//	@GetMapping("/list")
//	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxian")
//	public R<IPage<JiashiyuanBaoxian>> list(JiashiyuanBaoxian jiashiyuanBaoxian, Query query) {
//		IPage<JiashiyuanBaoxian> pages = jiashiyuanBaoxianService.page(Condition.getPage(query), Condition.getQueryWrapper(jiashiyuanBaoxian));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 驾驶员保险信息主表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入jiashiyuanBaoxian")
	public R<IPage<JiashiyuanBaoxianVO>> page(JiashiyuanBaoxianVO jiashiyuanBaoxian, Query query) {
		IPage<JiashiyuanBaoxianVO> pages = jiashiyuanBaoxianService.selectJiashiyuanBaoxianPage(Condition.getPage(query), jiashiyuanBaoxian);
		return R.data(pages);
	}

	/**
	 * 新增 驾驶员保险信息主表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入jiashiyuanBaoxian")
	public R save(@Valid @RequestBody JiashiyuanBaoxianInfo jiashiyuanBaoxian) {
		boolean isSave = jiashiyuanBaoxianService.save(jiashiyuanBaoxian.getBaoxian());
		if(jiashiyuanBaoxian.getBaoxianMingxis() != null && jiashiyuanBaoxian.getBaoxianMingxis().size() > 0) {
			for (JiashiyuanBaoxianMingxi baoxianMingxi: jiashiyuanBaoxian.getBaoxianMingxis()) {
				baoxianMingxi.setAjbmAvbIds(jiashiyuanBaoxian.getBaoxian().getAjbIds());
				mingxiService.save(baoxianMingxi);
			}
		}
		return R.status(isSave);
	}

	/**
	 * 修改 驾驶员保险信息主表
	 */
	@PostMapping("/update")
	public R update(@Valid @RequestBody JiashiyuanBaoxianInfo jiashiyuanBaoxian) {
		boolean isUpdate = jiashiyuanBaoxianService.updateById(jiashiyuanBaoxian.getBaoxian());
		if(jiashiyuanBaoxian.getBaoxianMingxis() != null && jiashiyuanBaoxian.getBaoxianMingxis().size() > 0) {
			for (JiashiyuanBaoxianMingxi baoxianMingxi: jiashiyuanBaoxian.getBaoxianMingxis()) {
				mingxiService.updateById(baoxianMingxi);
			}
		}
		return R.status(isUpdate);
	}

	/**
	 * 新增或修改 驾驶员保险信息主表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入jiashiyuanBaoxian")
	public R submit(@Valid @RequestBody JiashiyuanBaoxian jiashiyuanBaoxian) {
		return R.status(jiashiyuanBaoxianService.saveOrUpdate(jiashiyuanBaoxian));
	}


	/**
	 * 删除 驾驶员保险信息主表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		String[] idsss = ids.split(",");
		List<JiashiyuanBaoxian> deptBaoxians = new ArrayList<>();
		for(String id:idsss) {
			JiashiyuanBaoxian baoxian = new JiashiyuanBaoxian();
			baoxian.setAjbIds(id);
			baoxian.setAjbDelete("1");
			baoxian.setAjbUpdateTime(LocalDateTime.now());
			deptBaoxians.add(baoxian);
		}
		return R.status(jiashiyuanBaoxianService.updateBatchById(deptBaoxians));
	}


}
