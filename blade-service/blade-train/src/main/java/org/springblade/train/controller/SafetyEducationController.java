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
package org.springblade.train.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.common.tool.JSONUtils;
import org.springblade.core.log.annotation.ApiLog;
import org.springblade.core.tool.api.R;
import org.springblade.train.config.BaseController;
import org.springblade.train.entity.*;
import org.springblade.train.page.CourseInfoPage;
import org.springblade.train.service.ITrainService;
import org.springblade.train.service.IWaitCompletedService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/train/safetyEducation")
@Api(value = "企业端--学习情况统计分析", tags = "企业端--学习情况统计分析")
public class SafetyEducationController extends BaseController {

    private TrainServer trainServer;

	private ITrainService iTrainService;

	private IWaitCompletedService waitCompletedService;

    @GetMapping("/getFileServer")
    @ApiOperation(value = "教育--获取文件地址", notes = "教育--获取文件地址", position = 0)
    public R getFileServer() throws Exception {
        R rs = new R();
        String fileServer = trainServer.getFileserver();
        rs.setData(fileServer);
        rs.setCode(200);
        rs.setSuccess(true);
        rs.setMsg("获取地址成功");
        return rs;
    }

	@PostMapping("/getSnapshot")
	@ApiOperation(value = "教育--查询学习记录对应的抓拍图片", notes = "教育--查询学习记录对应的抓拍图片", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "json", value = "数据对象", required = true)
	})
	public R getSnapshot(@RequestBody String json) {
		R rs = new R();
		try {
			//获取参数
			JsonNode node = JSONUtils.string2JsonNode(json);
			//查询信息
			List<Snapshot> snapshotList = iTrainService.getSnapshot(node);
			if(snapshotList != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(snapshotList);
				rs.setMsg("查询抓拍图片成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询抓拍图片成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询抓拍图片失败");
		}
		return rs;
	}

	@GetMapping("/getStudentPhoto")
	@ApiOperation(value = "教育--查询学员头像图片", notes = "教育--查询学员头像图片", position = 2)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true)
	})
	public R getStudentPhoto(Integer studentId) {
		R rs = new R();
		try {
			//查询信息
			Student student = iTrainService.getStudentById(studentId);
			if(student != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(student);
				rs.setMsg("查询学员头像图片成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询学员头像图片成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询学员头像图片失败");
		}
		return rs;
	}

	@PostMapping("/getStudentProvePhoto")
	@ApiOperation(value = "教育--查询学员证明图片", notes = "教育--查询学员证明图片", position = 3)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "json", value = "数据对象", required = true)
	})
	public R getStudentProvePhoto(@RequestBody String json) {
		R rs = new R();
		try {
			//获取参数
			JsonNode node = JSONUtils.string2JsonNode(json);
			//查询信息
			List<Snapshot> snapshotList = iTrainService.getSnapshot(node);
			if(snapshotList != null && snapshotList.size() > 8) {
				Set<Snapshot> set = new HashSet<>(snapshotList);
				snapshotList = new ArrayList<>();
				for (Snapshot snapshot : set) {
					if(snapshotList.size() >= 8) {
						break;
					}
					snapshotList.add(snapshot);
				}
			}
			if(snapshotList != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(snapshotList);
				rs.setMsg("查询学员证明图片成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询学员证明图片成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询学员证明图片失败");
		}
		return rs;
	}

	@GetMapping("/getStudentProveDetailList")
	@ApiOperation(value = "教育--查询学员证明--学习记录", notes = "教育--查询学员证明--学习记录", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "courseId", value = "课程ID", required = true),
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true)
	})
	public R getStudentProveDetailList(Integer courseId, Integer studentId) {
		R rs = new R();
		try {
			//查询信息
			List<StudentProveDetail> studentProveDetailList = iTrainService.getStudentProveDetailList(courseId,studentId);
			if(studentProveDetailList != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(studentProveDetailList);
				rs.setMsg("查询学员证明学习记录成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询学员证明学习记录成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询学员证明学习记录失败");
		}
		return rs;
	}

	@GetMapping("/getStudentProveInfo")
	@ApiOperation(value = "教育--查询学员证明--学员信息", notes = "教育--查询学员证明--学员信息", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "courseId", value = "课程ID", required = true),
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true),
		@ApiImplicitParam(name = "deptId", value = "企业ID（企业端）", required = true)
	})
	public R getStudentProveInfo(Integer unitId, Integer courseId, Integer studentId,Integer deptId) {
		R rs = new R();
		try {
			//查询信息
			List<StudentProve> studentProveInfo = iTrainService.getStudentProveInfo(unitId,courseId,studentId,deptId);
			if(studentProveInfo != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(studentProveInfo);
				rs.setMsg("查询学员证明学员信息成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询学员证明学员信息成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询学员证明学员信息失败");
		}
		return rs;
	}

	@GetMapping("/getStudyInfo")
	@ApiOperation(value = "教育--学习情况统计", notes = "教育--学习情况统计", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitId", value = "企业ID", required = true)
	})
	public R getStudyInfo(Integer unitId) {
		R rs = new R();
		try {
			//查询信息
			StudyCountInfo studyInfo = iTrainService.getStudyInfo(unitId);
			if(studyInfo != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(studyInfo);
				rs.setMsg("查询学习情况统计成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询学习情况统计成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询学习情况统计失败");
		}
		return rs;
	}

	@PostMapping("/getCourseInfoList")
	@ApiLog("教育--课程情况统计")
	@ApiOperation(value = "教育--课程情况统计", notes = "传入CourseInfoPage", position = 6)
	public R<CourseInfoPage<CourseInfo>> getCourseInfoList(@RequestBody CourseInfoPage courseInfoPage) {
		CourseInfoPage<CourseInfo> CourseInfoPage = iTrainService.selectCourseInfoPage(courseInfoPage);
		return R.data(CourseInfoPage);
	}

	@GetMapping("/getCoursewareInfo")
	@ApiOperation(value = "教育--查询课件信息", notes = "教育--查询课件信息", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "courseId", value = "课程ID", required = true)
	})
	public R getCoursewareInfo(Integer courseId) {
		R rs = new R();
		try {
			//查询信息
			List<CoursewareInfo> coursewareInfo = iTrainService.getCoursewareInfo(courseId);
			if(coursewareInfo != null){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setData(coursewareInfo);
				rs.setMsg("查询课件信息成功");
			}else{
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("查询课件信息成功,暂未数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(true);
			rs.setMsg("查询课件信息失败");
		}
		return rs;
	}

	@PostMapping("/getStudentInfoList")
	@ApiLog("教育--学员情况统计")
	@ApiOperation(value = "教育--学员情况统计", notes = "传入CourseInfoPage", position = 8)
	public R<CourseInfoPage<StudentInfo>> getStudentInfoList(@RequestBody CourseInfoPage courseInfoPage) {
		CourseInfoPage<StudentInfo> studentInfoPage = iTrainService.selectStudentInfoPage(courseInfoPage);
		return R.data(studentInfoPage);
	}

	@GetMapping("/getStudentAllList")
	@ApiOperation(value = "教育--课程汇总情况统计", notes = "unitId、courseId", position = 9)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitId", value = "企业ID", required = true),
		@ApiImplicitParam(name = "courseId", value = "课程ID", required = true),
		@ApiImplicitParam(name = "deptId", value = "企业ID（企业端）", required = true)
	})
	public R getStudentAllList(Integer unitId, Integer courseId,Integer deptId) {
		List<StudentStatistics> studentStatisticsList = iTrainService.getStudentAllList(unitId,courseId,deptId);
		return R.data(studentStatisticsList);
	}

	@GetMapping("/getUnitByName")
	@ApiOperation(value = "教育--根据企业名称获取教育企业ID", notes = "教育--根据企业名称获取教育企业ID", position = 10)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R<List<Train>> getUnitByName(String deptName) {
		R rs = new R();
		//根据企业名称查询教育平台是否包含该企业
		Unit unit = iTrainService.getUnitByName(deptName);
		if(unit == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前所属单位未在教育系统中");
		}else{
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(unit);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@GetMapping("/getStudentByName")
	@ApiOperation(value = "教育--根据学员姓名、企业名称获取学员相关信息", notes = "教育--根据学员姓名、企业名称获取学员相关信息", position = 11)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverName", value = "学员姓名", required = true),
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R getStudentByName(String driverName, String deptName) {
		R rs = new R();
		Student student = iTrainService.getStudentByName(driverName,deptName);
		if(student == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前驾驶员未在教育系统中");
		}else{
			rs.setCode(200);
			rs.setSuccess(true);
			rs.setData(student);
			rs.setMsg("获取成功");
		}
		return rs;
	}

	@GetMapping("/getDriverCourseCount")
	@ApiLog("教育--根据学员ID获取学习待办数")
	@ApiOperation(value = "教育--根据学员ID获取学习待办数", notes = "传入studentId", position = 12)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true)
	})
	public R getDriverCourseCount(Integer studentId) {
		R rs = new R();
		int isPay = 3;
		int courseType = 1;
		Integer courseKind = null;
		try {
			//查询信息
			List<WaitCompletedCourse> courseList = waitCompletedService.getCourseList(studentId, isPay, courseType, courseKind,0);
			if (courseList != null) {
				rs.setCode(200);
				rs.setMsg("获取学习待办数成功");
				rs.setSuccess(true);
				rs.setData(courseList.size());
			} else {
				rs.setCode(200);
				rs.setMsg("暂无数据");
				rs.setSuccess(true);
				rs.setData(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setMsg("获取学习待办数失败");
			rs.setSuccess(false);
		}
		return rs;
	}

	@GetMapping("/getStudentCourseCount")
	@ApiOperation(value = "教育--根据学员姓名、企业名称获取学习待办数", notes = "教育--根据学员姓名、企业名称获取学习待办数", position = 13)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverName", value = "学员姓名", required = true),
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R getStudentCourseCount(String driverName, String deptName) {
		R rs = new R();
		int isPay = 3;
		int courseType = 1;
		Integer courseKind = null;
		Student student = iTrainService.getStudentByName(driverName,deptName);
		if(student == null){
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("当前驾驶员未在教育系统中");
		}else{
			//查询信息
			List<WaitCompletedCourse> courseList = waitCompletedService.getCourseList(student.getId(), isPay, courseType, courseKind,0);
			if (courseList != null) {
				student.setNum(courseList.size());
				rs.setCode(200);
				rs.setMsg("获取学习待办数成功");
				rs.setSuccess(true);
				rs.setData(student);
			} else {
				rs.setCode(200);
				rs.setMsg("暂无数据");
				rs.setSuccess(true);
				rs.setData(null);
			}
		}
		return rs;
	}

}
