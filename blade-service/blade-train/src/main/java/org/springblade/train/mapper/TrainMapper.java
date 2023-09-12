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
package org.springblade.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springblade.anbiao.qiyeshouye.entity.PersonLearnInfo;
import org.springblade.anbiao.qiyeshouye.page.QiYeShouYePage;
import org.springblade.train.entity.*;
import org.springblade.train.page.CourseInfoPage;

import java.util.HashMap;
import java.util.List;

/**
 * Mapper 接口
 * @author 呵呵哒
 */
public interface TrainMapper extends BaseMapper<Train> {

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
    CourseExt getCourseExt(@Param("alarmType") String alarmType,@Param("deptId") String deptId);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    List<Course> getCourseALLList(int id);

	/**
	 * 学习情况统计
	 * @param unitId
	 * @return
	 */
	StudyCountInfo getStudyInfo(@Param("unitId") Integer unitId);

	/**
	 * 课程情况统计
	 * @param courseInfoPage
	 * @return
	 */
	List<CourseInfo> selectCourseInfoPage(CourseInfoPage courseInfoPage);
	int selectCourseInfoTotal(CourseInfoPage courseInfoPage);

	/**
	 * 查询课件信息
	 * @param courseId
	 * @return
	 */
	List<CoursewareInfo> getCoursewareInfo(@Param("courseId") Integer courseId);

	/**
	 * 查询课件总学时
	 * @param courseId
	 * @return
	 */
	CoursewareInfo getCoursewareDuration(@Param("courseId") int courseId);

	/**
	 * 学员情况统计
	 * @param courseInfoPage
	 * @return
	 */
	List<StudentInfo> selectStudentInfoPage(CourseInfoPage courseInfoPage);
	int selectStudentInfoTotal(CourseInfoPage courseInfoPage);

	/**
	 * 查询学员证明--学员信息
	 * @param unitId
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	List<StudentProve> getStudentProveInfo(@Param("unitId") Integer unitId,@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);

	/**
	 * 查询学员证明--学习记录
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	List<StudentProveDetail> getStudentProveDetailList(@Param("courseId") int courseId,@Param("studentId") int studentId);

	/**
	 * 汇总数据查询
	 * @param unitId
	 * @param courseId
	 * @return
	 */
	List<StudentStatistics> getStudentAllList(@Param("unitId") Integer unitId,@Param("courseId") Integer courseId);

	/**
	 * 获取课程类型下拉框
	 * @param name
	 * @return
	 */
	List<CourseKind> getCourseKindList(@Param("name") String name);

	/**
	 * 政府查询学习统计列表
	 * @param qiYeShouYePage
	 * @return
	 */
	List<ZFCourseInfo> selectZFPersonLearnCoutAll(QiYeShouYePage qiYeShouYePage);
	int selectZFPersonLearnCoutAllTotal(QiYeShouYePage qiYeShouYePage);


}
