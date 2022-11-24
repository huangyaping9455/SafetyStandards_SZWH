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
package org.springblade.train.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.train.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Feign接口类
 *
 * @author hyp
 */
@FeignClient(
	value = "blade-train",
	fallback = ITrainClientFallback.class
)
public interface ITrainClient {

	String API_PREFIX = "/train";

	@PostMapping(API_PREFIX + "/insertCourseStudentSelective")
	Boolean insertCourseStudentSelective(@Valid @RequestBody CourseStudent courseStudent);

	/**
	 * 根据企业名称获取企业相关信息
	 * @param name
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getUnitByName")
	Unit getUnitByName(@RequestParam("name") String name);

	/**
	 * 根据学员姓名、企业名称获取学员相关信息
	 * @param driverName、deptName
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getStudentByName")
	Student getStudentByName(@RequestParam("driverName") String driverName, @RequestParam("deptName") String deptName);

	/**
	 * 根据报警类型、企业ID获取课程相关信息
	 * @param alarmType
	 * @param deptId
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getCourseExt")
	CourseExt getCourseExt(@RequestParam("alarmType") String alarmType, @RequestParam("deptId") String deptId);

	/**
	 * 获取课程信息
	 * @param id
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getCourseALLList")
	List<Course> getCourseALLList(@RequestParam("id") int id);

}
