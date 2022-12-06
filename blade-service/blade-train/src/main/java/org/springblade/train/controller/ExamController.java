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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springblade.common.configurationBean.TrainServer;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.train.config.BaseController;
import org.springblade.train.entity.*;
import org.springblade.train.page.CourseTestRecordPage;
import org.springblade.train.service.*;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 呵呵哒
 */
@RestController
@AllArgsConstructor
@RequestMapping("/train/exams")
@Api(value = "处罚教育学习--考试", tags = "处罚教育学习--考试")
public class ExamController extends BaseController {

	private IExamService examService;

	private IBizExamService bizExamService;

	private ICourseStudentService courseStudentService;

	private ICourseTestRecordService courseTestRecordService;

	private ITrainService trainService;

	private IStudentService studentService;

	private TrainServer trainServer;

	@GetMapping("/getWaitExamList")
	@ApiOperation(value = "教育--根据驾驶员名称、企业名称获取待考试详情", notes = "教育--根据驾驶员名称、企业名称获取待考试详情", position = 1)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "driverName", value = "驾驶员名称", required = true),
		@ApiImplicitParam(name = "deptName", value = "企业名称", required = true)
	})
	public R getWaitExamList(String driverName,String deptName) throws Exception{
		R rs = new R();
		try {
			//根据学员姓名、企业名称获取培训的学员ID
			Unit unitDeail = trainService.getUnitByName(deptName);
			QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>();
			studentQueryWrapper.lambda().eq(Student::getRealName, driverName);
			studentQueryWrapper.lambda().eq(Student::getUnitId, unitDeail.getId());
			studentQueryWrapper.lambda().eq(Student::getDeleted, "0");
			Student studentDeail = studentService.getBaseMapper().selectOne(studentQueryWrapper);
			if(studentDeail == null){
				rs.setCode(200);
				rs.setMsg("暂无数据");
				rs.setSuccess(true);
				rs.setData(null);
				return rs;
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("studentId", studentDeail.getId());
			List<WaitExamModel> waitExamModelList = examService.getWaitExamList(map);
			if(waitExamModelList != null){
				waitExamModelList.forEach(item-> {
					if(StringUtil.isNotBlank(item.getFullFacePhoto())){
						item.setFullFacePhoto(trainServer.getFileserver()+item.getFullFacePhoto());
					}
				});
				rs.setData(waitExamModelList);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取待考试详情成功");
			}else{
				rs.setData(null);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取待考试详情成功,暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取待考试详情失败");
		}
		return rs;
	}

	@GetMapping("/getApplyStudent")
	@ApiOperation(value = "教育--根据学员ID获取预约考试详情", notes = "教育--根据学员ID获取预约考试详情", position = 2)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true)
	})
	public R getApplyStudent(int studentId) throws Exception{
		R rs = new R();
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("studentId", studentId);
			StudentApplyModel studentApplyModel = new StudentApplyModel();
			List<StudentApplyModel> studentApplyModelList = examService.getApplyStudentList(map);
			if(studentApplyModelList.size()>0){
				HashMap<String, Object> map1 = new HashMap<>();
				map1.put("studentId",studentId);
				List<CourseStudent> courseStudentList = examService.getCourseStudentMapList(map1);
				if(courseStudentList.size()>0){
					boolean isPass = true;
					for(CourseStudent courseStudent : courseStudentList){
						QueryWrapper<CourseTestRecord> queryWrapperTest = new QueryWrapper<>();
						queryWrapperTest.eq("student_id",studentId).eq("rel_unit_course_id",courseStudent.getCourseId()).eq("result",1);
						List<CourseTestRecord> courseTestRecordList = courseTestRecordService.list(queryWrapperTest);
						if(courseTestRecordList.size()>0&&isPass){

						}else{
							isPass =false;
						}
					}
					if(isPass){
						studentApplyModel=studentApplyModelList.get(0);
					}else{
						studentApplyModel.setStatus(5);
						studentApplyModel.setStudentId(studentId);
					}
				}else{
					studentApplyModel.setStatus(5);
					studentApplyModel.setStudentId(studentId);
				}
			}else{
				studentApplyModel.setStatus(5);
				studentApplyModel.setStudentId(studentId);
			}
			if(studentApplyModel != null){
				rs.setData(studentApplyModel);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取预约考试详情成功");
			}else{
				rs.setData(null);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取预约考试详情成功,暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取预约考试详情失败");
		}
		return rs;
	}

	@PostMapping("/setApplyStudent")
	@ApiOperation(value = "教育--预约考试", notes = "教育- -预约考试", position = 3)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "bizExam", value = "数据对象", required = true)
	})
	public R setCourseTestRecord(@RequestBody BizExam bizExam) throws Exception{
		R rs = new R();
		try {
			if(bizExam.getStudentId()>0){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				bizExam.setApplyTime(dateFormat.format(date));
				bizExam.setExamBeginTime(dateFormat.format(date));
				bizExam.setExamEndTime(dateFormat.format(date));
				bizExam.setScore(0);
				bizExam.setStatus(0);
				boolean i = bizExamService.saveOrUpdate(bizExam);
				if(i){
					rs.setCode(200);
					rs.setSuccess(true);
					rs.setMsg("预约考试成功");
				}else{
					rs.setCode(500);
					rs.setSuccess(false);
					rs.setMsg("预约考试失败");
				}
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("学员id必须要传");
			}

		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("设置考试记录失败");
		}
		return rs;
	}

	@GetMapping("/getQuestionList")
	@ApiOperation(value = "教育--获取考题详情", notes = "教育--获取考题详情", position = 4)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = false),
		@ApiImplicitParam(name = "id", value = "考题ID", required = true)
	})
	public R getQuestionList(int studentId, int id) throws Exception{
		R rs = new R();
		try {
			// 小程序不改的情况下，新增一次查询，由 CourseId 找出 relUnitCourseId
			// Integer cousreId = examService.getRelUnitCourseId(user.getUserId(), sourceId);
			HashMap<String, Object> map = new HashMap<>();
			map.put("sourceId", id);
			List<ExamModel> examModelList = new ArrayList<>();
			List<ExamQuestionModel> examQuestionModelList = examService.getQuestionList(map);
			int questionId = 0;
			int order =1;
			ExamModel examModel = new ExamModel();
			for(ExamQuestionModel examQuestionModel:examQuestionModelList){
				if(questionId !=examQuestionModel.getQuestionId()){
					order=1;
					if(questionId != 0){
						examModelList.add(examModel);
						examModel = new ExamModel();
					}
					examModel.setAnalysis(examQuestionModel.getAnalysis());
					examModel.setCategory(examQuestionModel.getCategory());
					examModel.setContent(examQuestionModel.getQuestionContent());
					examModel.setId(examQuestionModel.getQuestionId());
					examModel.setCourseId(examQuestionModel.getId());
					List<ExamAnswerModel> examAnswerModelList = new ArrayList<>();
					ExamAnswerModel examAnswerModel = new ExamAnswerModel();
					examAnswerModel.setChecked(examQuestionModel.getChecked());
					examAnswerModel.setContent(examQuestionModel.getAnswerContent());
					examAnswerModel.setId(examQuestionModel.getAnswerId());
					examAnswerModel.setOrderNumber(order);
					examAnswerModelList.add(examAnswerModel);
					examModel.setExerciseAnswerList(examAnswerModelList);
				}else{
					ExamAnswerModel examAnswerModel = new ExamAnswerModel();
					examAnswerModel.setChecked(examQuestionModel.getChecked());
					examAnswerModel.setContent(examQuestionModel.getAnswerContent());
					examAnswerModel.setId(examQuestionModel.getAnswerId());
					examAnswerModel.setOrderNumber(order);
					examModel.getExerciseAnswerList().add(examAnswerModel);
				}
				order++;
				questionId = examQuestionModel.getQuestionId();
			}
			if(examQuestionModelList.size()>0){
				examModelList.add(examModel);
			}
			if(examModelList != null){
				rs.setData(examModelList);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考题详情成功");
			}else{
				rs.setData(null);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考题详情成功，暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取考题详情失败");
		}
		return rs;
	}

	@PostMapping("/setCourseTestRecord")
	@ApiOperation(value = "教育--保存考试记录", notes = "教育- -保存考试记录", position = 5)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "courseTestRecord", value = "数据对象", required = true)
	})
	public R setCourseTestRecord(@RequestBody CourseTestRecord courseTestRecord) throws Exception{
		R rs = new R();
		try {
			UpdateWrapper<CourseStudent> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("student_id",courseTestRecord.getStudentId()).eq("rel_unit_course_id",courseTestRecord.getCourseId()).set("course_status",courseTestRecord.getResult()==1?3:2);
			boolean i = courseTestRecordService.save(courseTestRecord);
			courseStudentService.getBaseMapper().update(null,updateWrapper);
			if(i){
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("设置考试记录成功");
			}else{
				rs.setCode(500);
				rs.setSuccess(false);
				rs.setMsg("设置考试记录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("设置考试记录失败");
		}
		return rs;
	}

	@PostMapping("/getCourseTestRecord")
	@ApiOperation(value = "教育--获取考试记录详情", notes = "教育- -获取考试记录详情", position = 6)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "courseTestRecordPage", value = "数据对象", required = true)
	})
	public R getCourseTestRecord(@RequestBody CourseTestRecordPage courseTestRecordPage) throws Exception{
		R rs = new R();
		try {
			//根据学员姓名、企业名称获取培训的学员ID
			Unit unitDeail = trainService.getUnitByName(courseTestRecordPage.getDeptName());
			QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<Student>();
			studentQueryWrapper.lambda().eq(Student::getRealName, courseTestRecordPage.getStudentName());
			studentQueryWrapper.lambda().eq(Student::getUnitId, unitDeail.getId());
			studentQueryWrapper.lambda().eq(Student::getDeleted, "0");
			Student studentDeail = studentService.getBaseMapper().selectOne(studentQueryWrapper);
			if(studentDeail == null){
				rs.setCode(200);
				rs.setMsg("暂无数据");
				rs.setSuccess(true);
				rs.setData(null);
				return rs;
			}
			courseTestRecordPage.setStudentId(studentDeail.getId());
			//设置分页查询
			CourseTestRecordPage courseTestRecordList = examService.getCourseTestRecordList(courseTestRecordPage);
//			List<CourseTestRecord> resultList = this.modelMapper.map(courseTestRecordList, new TypeToken<List<CourseTestRecord>>() {}.getType());
			if(courseTestRecordList != null){
				rs.setData(courseTestRecordList);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考试记录详情成功");
			}else{
				rs.setData(null);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考试记录详情成功，暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取考试记录详情失败");
		}
		return rs;
	}

	@GetMapping("/getWaitExamListCount")
	@ApiOperation(value = "教育--根据学员ID获取待考试数量统计", notes = "教育--根据学员ID获取待考试数量统计", position = 7)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "studentId", value = "学员ID", required = true)
	})
	public R getWaitExamListCount(int studentId) throws Exception{
		R rs = new R();
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("studentId", studentId);
			List<WaitExamModel> waitExamModelList = examService.getWaitExamList(map);
			if(waitExamModelList != null){
				rs.setData(waitExamModelList.size());
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("根据学员ID获取待考试数量统计成功");
			}else{
				rs.setData(0);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("根据学员ID获取待考试数量统计成功,暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("根据学员ID获取待考试数量统计失败");
		}
		return rs;
	}

	@PostMapping("/getCourseTestRecordCount")
	@ApiOperation(value = "教育--获取考试记录数量统计", notes = "教育- -获取考试记录数量统计", position = 8)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "courseTestRecordPage", value = "数据对象(current,size 都传0)", required = true)
	})
	public R getCourseTestRecordCount(@RequestBody CourseTestRecordPage courseTestRecordPage) throws Exception{
		R rs = new R();
		try {
			//设置分页查询
			CourseTestRecordPage courseTestRecordList = examService.getCourseTestRecordList(courseTestRecordPage);
			List<CourseTestRecord> resultList = this.modelMapper.map(courseTestRecordList, new TypeToken<List<CourseTestRecord>>() {}.getType());
			if(resultList != null){
				rs.setData(resultList.size());
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考试记录数量统计成功");
			}else{
				rs.setData(0);
				rs.setCode(200);
				rs.setSuccess(true);
				rs.setMsg("获取考试记录数量统计成功，暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(500);
			rs.setSuccess(false);
			rs.setMsg("获取考试记录数量统计失败");
		}
		return rs;
	}

}
