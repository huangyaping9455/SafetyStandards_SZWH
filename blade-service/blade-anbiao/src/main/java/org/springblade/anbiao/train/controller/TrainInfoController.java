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
package org.springblade.anbiao.train.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.anbiao.train.entity.TrainInfo;
import org.springblade.anbiao.train.service.ITrainInfoService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/anbiao/trainInfo")
@Api(value = "安全教育学习", tags = "安全教育学习")
public class TrainInfoController extends BladeController {

	private ITrainInfoService trainService;

	@PostMapping("/getDeptWait")
	@ApiLog("当月未完成安全培训的人员信息")
	@ApiOperation(value = "当月未完成安全培训的人员信息", notes = "传入deptId", position = 8)
	public R getDeptWait(String deptId) {
		R rs = new R();
		List<TrainInfo> list= trainService.getDeptWait(deptId);
		return R.data(list);
	}

}
