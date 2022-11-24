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

import lombok.AllArgsConstructor;
import org.springblade.train.entity.*;
import org.springblade.train.service.ICourseStudentService;
import org.springblade.train.service.ITrainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author hyp
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class TrainClient implements ITrainClient {

	ITrainService trainService;

	ICourseStudentService courseStudentService;

	@Override
	@PostMapping(API_PREFIX + "/insertCourseStudentSelective")
	public Boolean insertCourseStudentSelective(@Valid CourseStudent courseStudent) {
		return courseStudentService.insertCourseStudentSelective(courseStudent);
	}

	@Override
	@GetMapping(API_PREFIX + "/getUnitByName")
	public Unit getUnitByName(String name) {
		return trainService.getUnitByName(name);
	}

	@Override
	@GetMapping(API_PREFIX + "/getStudentByName")
	public Student getStudentByName(String driverName,String deptName) {
		return trainService.getStudentByName(driverName,deptName);
	}

	@Override
	@GetMapping(API_PREFIX + "/getCourseExt")
	public CourseExt getCourseExt(String alarmType, String deptId) {
		return trainService.getCourseExt(alarmType, deptId);
	}

	@Override
	@GetMapping(API_PREFIX + "/getCourseALLList")
	public List<Course> getCourseALLList(int id) {
		return trainService.getCourseALLList(id);
	}


}
