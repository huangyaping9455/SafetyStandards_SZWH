/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.system.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Dict;
import org.springblade.system.page.DictPage;
import org.springblade.system.service.IDictService;
import org.springblade.system.vo.DictVO;
import org.springblade.system.wrapper.DictWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springblade.common.cache.CacheNames.DICT_LIST;
import static org.springblade.common.cache.CacheNames.DICT_VALUE;

/**
 * 控制器
 * @author hyp
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends BladeController {

	private IDictService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入dict", position = 1)
	public R<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		DictWrapper dictWrapper = new DictWrapper(dictService);
		return R.data(dictWrapper.entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "列表", notes = "传入dict", position = 2)
	public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		//删除不需要的参数
		dict.remove("deptId");
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		DictWrapper dictWrapper = new DictWrapper();
		return R.data(dictWrapper.listNodeVO(list));
	}

	@PostMapping("/list")
	@ApiLog("分页-列表")
	@ApiOperation(value = "分页-列表", notes = "传入dictPage", position = 10)
	public R<DictPage<Dict>> list(@RequestBody DictPage dictPage) {
		DictPage<Dict> pages = dictService.selectALLPage(dictPage);
		return R.data(pages);
	}

	/**
	 * 获取字典树形结构
	 *
	 * @return
	 */
	@PostMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构", position = 3)
	public R<List<DictVO>> tree(@RequestBody DictPage dictPage) {
		List<DictVO> tree = dictService.tree(dictPage);
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入dict", position = 6)
	public R submit(@Valid @RequestBody Dict dict) {
		return R.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE})
	@ApiOperation(value = "删除", notes = "传入ids", position = 7)
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(dictService.removeByIds(Func.toIntList(ids)));
	}

	/**
	 * 获取字典
	 *
	 * @return
	 */
	@GetMapping("/dictionary")
	@ApiOperation(value = "获取字典", notes = "传入code字典码", position = 8)
	public R<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(tree);
	}

	/**
	 * 根据字典码获取字典
	 *
	 * @return
	 */
	@GetMapping("/getByCode")
	@ApiOperation(value = "根据字典码获取字典", notes = "传入code字典码", position = 9)
	public R<List<Map<String, String>>> getByCode(String code) {
		List<Dict> tree = dictService.selectByCode(code);
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i <tree.size(); i++) {
			Map<String,String> map=new HashMap<>();
			map.put("label",tree.get(i).getDictValue());
			map.put("value",tree.get(i).getDictKey().toString());
			list.add(map);
		}
		return R.data(list);
	}

	@PostMapping("/OtherTreeList")
	@ApiLog("字典-其他/行政区域字典列表")
	@ApiOperation(value = "字典-其他/行政区域字典列表", notes = "传入类型（其他/地区）", position = 11)
	public R<List<DictVO>> OtherTreeList(@RequestBody DictPage dictPage) {
		List<DictVO> pages = dictService.OtherTree(dictPage);
		return R.data(pages);
	}

	@PostMapping("/RegionalismTreeList")
	@ApiLog("字典-根据上级ID获取下级字典列表")
	@ApiOperation(value = "分页-根据上级ID获取下级字典列表", notes = "传入Id", position = 12)
	public R<List<DictVO>> RegionalismTreeList(@ApiParam(value = "主键集合", required = true) @RequestParam Integer id) {
		List<DictVO> pages = dictService.RegionalismTree(id);
		return R.data(pages);
	}

}
