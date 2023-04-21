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
package org.springblade.anbiao.jinritixing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.jinritixing.entity.Jinritixing;
import org.springblade.anbiao.jinritixing.page.JinritixingPage;
import org.springblade.anbiao.jinritixing.service.IJinritixingService;
import org.springblade.anbiao.jinritixing.vo.JinritixingVO;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  控制器
 */
@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/jinritixing")
@Api(value = "今日提醒", tags = "今日提醒")
public class JinritixingController extends BladeController {

	private IJinritixingService jinritixingService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiLog("详情-今日提醒")
	@ApiOperation(value = "详情-今日提醒", notes = "传入id", position = 1)
	public R detail(String id) {
		return R.data(jinritixingService.selectByIds(id));
	}

	/**
	* 自定义分页
	*/
	@PostMapping("/list")
	@ApiLog("分页-今日提醒")
	@ApiOperation(value = "分页-今日提醒", notes = "传入jinritixingPage与query", position = 2)
	public R<Map<String, Object>> list(@RequestBody JinritixingPage jinritixingPage) {

		HashMap map =new HashMap();

		JinritixingPage<JinritixingVO> page = jinritixingService.selectPageList(jinritixingPage);

		jinritixingPage.setTixingleixing("预警");
		 int yujing = jinritixingService.selectNum(jinritixingPage);

		jinritixingPage.setTixingleixing("超期");
		int daoqi = jinritixingService.selectNum(jinritixingPage);

		map.put("pages",page);
		map.put("yujing",yujing);
		map.put("daoqi",daoqi);

		return R.data(map);
	}

	/**
	* 新增
	*/
	@PostMapping("/insert")
	@ApiLog("新增-今日提醒")
	@ApiOperation(value = "新增-今日提醒", notes = "传入jinritixing", position = 3)
	public R insert(@RequestBody Jinritixing jinritixing) {
		return R.status(jinritixingService.save(jinritixing));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiLog("修改-今日提醒")
	@ApiOperation(value = "修改-今日提醒", notes = "传入jinritixing", position = 4)
	public R update(@RequestBody Jinritixing jinritixing) {
		return R.status(jinritixingService.updateById(jinritixing));
	}

	/**
	* 删除
	*/
	@PostMapping("/del")
	@ApiLog("删除-今日提醒")
	@ApiOperation(value = "删除-今日提醒", notes = "传入id", position = 5)
	public R del(@ApiParam(value = "id", required = true) @RequestParam String id) {
		return R.status(jinritixingService.updateDel(id));
	}

	@PostMapping("/listxiangqing")
	@ApiLog("提醒列表-今日提醒")
	@ApiOperation(value = "提醒列表-今日提醒", notes = "传入jinritixingPage与query", position = 6)
	public R<JinritixingPage<JinritixingVO>> listxiangqing(@RequestBody JinritixingPage jinritixingPage) {
		JinritixingPage<JinritixingVO> pages = jinritixingService.selectLists(jinritixingPage);
		return R.data(pages);
	}




}
