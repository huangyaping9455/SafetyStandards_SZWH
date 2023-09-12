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
package org.springblade.train.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.train.entity.*;
import org.springblade.train.page.CourseInfoPage;
import org.springblade.train.page.StudyRecordPage;

import java.util.HashMap;
import java.util.List;

/**
 * 服务类
 *
 * @author hyp
 */
public interface ITrainService extends IService<Train> {

	/**
	 * 根据课程类型、企业ID获取对应的课程列表
	 * @param name
	 * @param id
	 * @return
	 */
	List<Train> getQYCourseList(@Param("name") String name,@Param("id") String id);

	/**
	 * 根据企业名称获取企业相关信息
	 * @param name
	 * @return
	 */
	Unit getUnitByName(String name);

	/**
	 * 根据学员姓名、企业名称获取学员相关信息
	 * @param driverName、deptName
	 * @return
	 */
	Student getStudentByName(@Param("driverName") String driverName,@Param("deptName") String deptName);


	/**
	 * 根据报警类型、企业ID获取课程相关信息
	 * @param alarmType
	 * @param deptId
	 * @return
	 */
	CourseExt getCourseExt(@Param("alarmType") String alarmType, @Param("deptId") String deptId);

	/**
	 * 获取课程信息
	 * @param id
	 * @return
	 */
	List<Course> getCourseALLList(int id);

	/**
	 * 添加抓拍图片
	 * @param snapshot
	 * @return
	 */
	boolean insertSnapshotSelective(Snapshot snapshot);

	/**
	 * 添加学习记录
	 * @param studyRecord
	 * @return
	 */
	boolean insertStudyRecordSelective(StudyRecord studyRecord);

	/**
	 * 根据学员ID获取学习记录
	 * @param studyRecordPage
	 * @return
	 */
	StudyRecordPage getAppStudyRecordList(StudyRecordPage studyRecordPage);

	/**
	 * 查询上次学习记录
	 * @param studentId
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	StudyRecord getStudyRecordList(int studentId,int courseId,int coursewareId);

	/**
	 * 查询学习抓拍图片记录
	 * @param object
	 * @return
	 */
	List<Snapshot> getSnapshot(JsonNode object);

	/**
	 * 查询学员头像图片
	 * @param id
	 * @return
	 */
	Student getStudentById(Integer id);

	/**
	 * 学习情况统计
	 * @param unitId
	 * @return
	 */
	StudyCountInfo getStudyInfo(Integer unitId);

	/**
	 * 课程情况统计
	 * @param courseInfoPage
	 * @return
	 */
	CourseInfoPage<CourseInfo> selectCourseInfoPage(CourseInfoPage courseInfoPage);

	/**
	 * 查询课件信息
	 * @param courseId
	 * @return
	 */
	List<CoursewareInfo> getCoursewareInfo(Integer courseId);

	/**
	 * 学员情况统计
	 * @param courseInfoPage
	 * @return
	 */
	CourseInfoPage<StudentInfo> selectStudentInfoPage(CourseInfoPage courseInfoPage);

	/**
	 * 查询学员证明--学员信息
	 * @param unitId
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	List<StudentProve> getStudentProveInfo(Integer unitId, Integer courseId, Integer studentId,Integer deptId);

	/**
	 * 查询学员证明--学习记录
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	List<StudentProveDetail> getStudentProveDetailList(int courseId, int studentId);

	/**
	 * 汇总数据查询
	 * @param unitId
	 * @param courseId
	 * @return
	 */
	List<StudentStatistics> getStudentAllList(Integer unitId, Integer courseId,Integer deptId);

	/**
	 * 获取课程类型下拉框
	 * @param name
	 * @return
	 */
	List<CourseKind> getCourseKindList(String name);

	/**
	 * 政府查询学习统计列表
	 * @param qiYeShouYePage
	 * @return
	 */
	QiYeShouYePage<ZFCourseInfo> selectZFPersonLearnCoutAll(QiYeShouYePage qiYeShouYePage);


}
